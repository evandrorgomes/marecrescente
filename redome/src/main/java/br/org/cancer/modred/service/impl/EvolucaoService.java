package br.org.cancer.modred.service.impl;

import static java.time.temporal.ChronoUnit.SECONDS;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import br.org.cancer.modred.controller.dto.EvolucaoDto;
import br.org.cancer.modred.controller.dto.EvolucaoListaDto;
import br.org.cancer.modred.exception.BusinessException;
import br.org.cancer.modred.exception.ValidationException;
import br.org.cancer.modred.model.Busca;
import br.org.cancer.modred.model.Cid;
import br.org.cancer.modred.model.CondicaoPaciente;
import br.org.cancer.modred.model.Configuracao;
import br.org.cancer.modred.model.EstagioDoenca;
import br.org.cancer.modred.model.Evolucao;
import br.org.cancer.modred.model.Motivo;
import br.org.cancer.modred.model.Paciente;
import br.org.cancer.modred.model.Pendencia;
import br.org.cancer.modred.model.RespostaPendencia;
import br.org.cancer.modred.model.annotations.log.CreateLog;
import br.org.cancer.modred.model.domain.TipoLogEvolucao;
import br.org.cancer.modred.model.domain.TiposBuscaChecklist;
import br.org.cancer.modred.persistence.IEvolucaoRepository;
import br.org.cancer.modred.persistence.IRepository;
import br.org.cancer.modred.service.IBuscaChecklistService;
import br.org.cancer.modred.service.ICondicaoPacienteService;
import br.org.cancer.modred.service.IConfiguracaoService;
import br.org.cancer.modred.service.IEstagioDoencaService;
import br.org.cancer.modred.service.IEvolucaoService;
import br.org.cancer.modred.service.IMotivoService;
import br.org.cancer.modred.service.IPacienteService;
import br.org.cancer.modred.service.IRespostaPendenciaService;
import br.org.cancer.modred.service.IStorageService;
import br.org.cancer.modred.service.ITipoTransplanteService;
import br.org.cancer.modred.service.impl.invocation.AbstractLoggingService;
import br.org.cancer.modred.util.AppUtil;
import br.org.cancer.modred.util.ArquivoUtil;
import br.org.cancer.modred.util.CampoMensagem;
import br.org.cancer.modred.util.ConstraintViolationTransformer;

/**
 * Classe para de negócios para Evolucao.
 * 
 * @author Rafael Pizão
 *
 */
@Service
@Transactional
public class EvolucaoService extends AbstractLoggingService<Evolucao, Long> implements IEvolucaoService {

	private static final Logger LOGGER = LoggerFactory.getLogger(EvolucaoService.class);
	
		
	private ICondicaoPacienteService condicaoPacienteService;

	private IPacienteService pacienteService;

	private IEstagioDoencaService estagioDoencaService;

	private IMotivoService motivoService;

	private ITipoTransplanteService tipoTransplanteService;

	@Autowired
	private IEvolucaoRepository evolucaoRepository;

	private IRespostaPendenciaService respostaPendenciaService;
	
	private IConfiguracaoService configuracaoService;
	
	@Autowired
	private IStorageService storageService;

	private IBuscaChecklistService buscaChecklistService;
	

	@Override
	public IRepository<Evolucao, Long> getRepository() {
		return evolucaoRepository;
	}
	
	/**
	 * Método para validação de evolução.
	 * 
	 * @param evolucao Parametro que deve sofrer a validação
	 * @param campos Lista de campos de erro, após validação.
	 */
	@Override
	public void validar(Cid cid, List<Evolucao> evolucoes, List<CampoMensagem> campos) {
		if (evolucoes != null && !evolucoes.isEmpty()) {
			validarEvolucaoInicial(cid, evolucoes.get(0), campos);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void validarEvolucaoInicial(Cid cid, Evolucao evolucao, List<CampoMensagem> campos) {
		validarCondicaoPaciente(evolucao, campos);
		validarMotivo(evolucao, campos);
		validarEstagioDoenca(cid, evolucao, campos);
		validarTipoTransplanteAnterior(evolucao, campos);
		validarExameAnticorpo(evolucao, campos);
	}

	/**
	 * Verificar se o motivo da evolução existe na base de dados.
	 * 
	 * @param evolucao Referência associada ao motivo
	 * @param campos Lista de erros a ser prrenchida, caso exista.
	 */
	private void validarMotivo(Evolucao evolucao, List<CampoMensagem> campos) {
		if (!isMotivoValido(evolucao)) {
			campos.add(new CampoMensagem("motivo.invalido", AppUtil.getMensagem(messageSource,
					"motivo.invalido")));
		}
		else {
			final Motivo motivo = motivoService.obterMotivo(evolucao.getMotivo().getId());

			if (motivo == null) {
				campos.add(new CampoMensagem("motivo.invalido", messageSource.getMessage("motivo.invalido",
						null, LocaleContextHolder.getLocale())));
			}
		}
	}

	private void validarEstagioDoenca(Cid cid, Evolucao evolucao, List<CampoMensagem> campos) {
		if (!isEstagioDoencaValido(evolucao)) {
			List<EstagioDoenca> estagios = estagioDoencaService.listarEstagiosDoencaPorCid(cid
					.getId());

			if (estagios != null && !estagios.isEmpty()) {
				campos.add(new CampoMensagem("estagiodoenca.invalido", AppUtil.getMensagem(messageSource,
						"estagiodoenca.invalido")));
			}

		}
		else {
			Long estagioDoencaId = evolucao.getEstagioDoenca().getId();
			Boolean isEstagioInvalido = estagioDoencaService.isEstagioInvalidoParaCid(cid.getId(),
					estagioDoencaId);

			if (isEstagioInvalido) {
				campos.add(new CampoMensagem("estagioDoenca", messageSource.getMessage(
						"estagiodoenca.invalido", null,
						LocaleContextHolder.getLocale())));
			}
		}
	}

	/**
	 * Verificar se a condição do paciente na evolução existe na base de dados.
	 * 
	 * @param evolucao Referência associada ao motivo
	 * @param campos Lista de erros a ser prrenchida, caso exista.
	 */
	private void validarCondicaoPaciente(Evolucao evolucao, List<CampoMensagem> campos) {
		if (!isCondicaoPacienteValida(evolucao)) {
			campos.add(new CampoMensagem("evolucao", AppUtil.getMensagem(messageSource,
					"erro.paciente.parametros.evolucao.invalidos")));

		}
		else {
			final CondicaoPaciente condicaoPaciente = condicaoPacienteService.obterCondicaoPaciente(
					evolucao.getCondicaoPaciente().getId());

			if (condicaoPaciente == null) {
				campos.add(
						new CampoMensagem(
								"evolucao.condicaoPaciente",
								messageSource.getMessage("condicaopaciente.invalido", null,
										LocaleContextHolder
												.getLocale())));
			}
		}

	}

	/**
	 * Testa se há alguma inconsistência (nulo, por exemplo) com o objeto. passado como paraâmetro.
	 * 
	 * @param evolucao objeto a ser verificado
	 * @return se estiver válido retornar TRUE, senão FALSE.
	 */
	private boolean isCondicaoPacienteValida(Evolucao evolucao) {
		return evolucao != null &&
				evolucao.getCondicaoPaciente() != null &&
				evolucao.getCondicaoPaciente().getId() != null;
	}

	/**
	 * Testa se há alguma inconsistência (nulo, por exemplo) com o objeto. passado como parâmetro.
	 * 
	 * @param evolucao objeto a ser verificado
	 * @return se estiver válido retornar TRUE, senão FALSE.
	 */
	private boolean isMotivoValido(Evolucao evolucao) {
		return evolucao != null &&
				evolucao.getMotivo() != null &&
				evolucao.getMotivo().getId() != null;
	}

	/**
	 * Testa se há alguma inconsistência (nulo, por exemplo) com o objeto. passado como parâmetro.
	 * 
	 * @param evolucao objeto a ser verificado
	 * @return se estiver válido retornar TRUE, senão FALSE.
	 */
	private boolean isEstagioDoencaValido(Evolucao evolucao) {
		return evolucao != null &&
				evolucao.getEstagioDoenca() != null &&
				evolucao.getEstagioDoenca().getId() != null;
	}


	private void validarTipoTransplanteAnterior(Evolucao evolucao, List<CampoMensagem> campos) {
		if(evolucao.getTiposTransplante() != null && evolucao.getTiposTransplante().isEmpty()) {
			evolucao.getTiposTransplante().forEach(t ->{
				if(tipoTransplanteService.obterTipoTransplante(t.getId()) == null) {
					campos.add(new CampoMensagem("evolucao.tipoTransplanteAnterior.id",
							messageSource.getMessage("tipo.transplante.invalido", null, LocaleContextHolder
									.getLocale())));
					return;
				}
			});				
		}
	}
	
	private void validarExameAnticorpo(Evolucao evolucao, List<CampoMensagem> campos) {
		if (evolucao != null && evolucao.getExameAnticorpo() == null) {
			campos.add(new CampoMensagem("evolucao.exameAnticorpo", AppUtil.getMensagem(
					messageSource, "evolucao.exameanticorpo.invalido")));
		}
		else if (evolucao != null && evolucao.getExameAnticorpo()) {
			if (evolucao.getDataExameAnticorpo() == null) {
				campos.add(new CampoMensagem("evolucao.dataExameAnticorpo", AppUtil.getMensagem(
						messageSource, "evolucao.data.exameanticorpo.invalido")));
			}
			if (evolucao.getResultadoExameAnticorpo() == null) {
				campos.add(new CampoMensagem("evolucao.resultadoExameAnticorpo", AppUtil.getMensagem(
						messageSource, "evolucao.resultado.exameanticorpo.invalido")));
			}
		}
	}

	/**
	 * Devolve uma listagem ordenada pela data da evolução de forma decrescente.
	 * 
	 * @param rmr do paciente
	 * @return List<Evolucao> listagem de estagios
	 */
	public List<Evolucao> buscarEvolucaoPorRMR(Long rmr) {
		return evolucaoRepository.findByPacienteRmrOrderByDataCriacaoDesc(rmr);
	}

	@Override
	public Evolucao obterEvolucaoPorId(Long evolucaoId) {
		if (evolucaoId == null) {
			throw new BusinessException("parametro.evolucao.nao.informada");
		}
		return evolucaoRepository.findById(evolucaoId).orElseThrow(() -> new BusinessException("parametro.evolucao.nao.existente")); 
	}

	/**
	 * {@inheritDoc}
	 * @throws IOException 
	 */
	@CreateLog(TipoLogEvolucao.NOVA_EVOLUCAO_PARA_PACIENTE)
	@Override
	public Evolucao salvar(Evolucao evolucao, List<MultipartFile> arquivos) throws IOException {
		
		AppUtil.percorrerObjetoNulandoEntidadesVazias(evolucao);
		
		pacienteService.verificarPacienteEmObito(evolucao.getPaciente().getRmr(), true);

		List<CampoMensagem> campos = new ArrayList<CampoMensagem>();

		Paciente paciente = pacienteService.findById(evolucao.getPaciente().getRmr());
		evolucao.setPaciente(paciente);

		String diretorioEvolucao = AppUtil.gerarStringAleatoriaAlfanumerica();

		Instant instant = Instant.now();
		long timeStampMillis = instant.toEpochMilli();
		
		
		if(evolucao.getArquivosEvolucao() != null){
			// Seta o caminho do arquivo enviado ao storage
			evolucao.getArquivosEvolucao().forEach(arquivo -> {
				arquivo.setCaminhoArquivo(diretorioEvolucao + "/"
						+ ArquivoUtil.obterNomeArquivo(timeStampMillis, arquivo.getCaminhoArquivo()));
				arquivo.setEvolucao(evolucao);
			});			
		}
		
		validarNovaEvolucao(evolucao, campos);

		if (!campos.isEmpty()) {
			throw new ValidationException("erro.validacao", campos);
		}

		LOGGER.info("Inicio do upload de arquivos no storage.");
		if(arquivos != null && !arquivos.isEmpty()){
			ArquivoUtil.validarArquivoEvolucao(arquivos, messageSource, configuracaoService);
			uploadArquivos(arquivos, evolucao.getPaciente().getRmr(), diretorioEvolucao, timeStampMillis);			
		}
		LOGGER.info("Fim do upload de arquivos no storage.");
		
		save(evolucao);
		
		Busca buscaAtiva = paciente.getBuscaAtiva();		
		if(buscaAtiva != null &&  buscaChecklistService.existeBuscaChecklistEmAberto(buscaAtiva.getId(), TiposBuscaChecklist.NOVA_EVOLUCAO_CLINICA.getId())) {
			buscaChecklistService.criarItemCheckList(buscaAtiva, TiposBuscaChecklist.NOVA_EVOLUCAO_CLINICA);
		}
		
		return evolucao;

	}
	
	private void validarNovaEvolucao(Evolucao evolucao, List<CampoMensagem> campos) {
		campos.addAll(new ConstraintViolationTransformer(validator.validate(
				evolucao)).transform());
		validarCondicaoPaciente(evolucao, campos);
		validarMotivo(evolucao, campos);
		validarTipoTransplanteAnterior(evolucao, campos);

		if (isCidInexistente(evolucao)) {
			campos.add(new CampoMensagem("estagioDoenca", messageSource.getMessage(
					"erro.cid.nao.enviada", null,
					LocaleContextHolder.getLocale())));
		}
		else {
			validarEstagioDoenca(evolucao.getPaciente().getDiagnostico().getCid(), evolucao,
					campos);
		}
		
		validarExameAnticorpo(evolucao, campos);

	} 

	private boolean isCidInexistente(Evolucao evolucao) {
		return evolucao.getPaciente() == null || evolucao.getPaciente().getDiagnostico() == null
				|| evolucao.getPaciente().getDiagnostico().getCid() == null;
	}

	/**
	 * Carrega VO para tela de evoluções.
	 * 
	 * @param rmr
	 * @return EvolucaoVo
	 */
	@Override
	public EvolucaoDto carregarEvolucaoPorRMR(Long rmr) {
		EvolucaoDto evolucao = new EvolucaoDto();

		List<Evolucao> evolucoes = evolucaoRepository.findByPacienteRmrOrderByDataCriacaoDesc(rmr);
		if (evolucoes == null) {
			throw new BusinessException("evolucao.erro.obter.ultimo.registro");
		}
		evolucao.setEvolucoes(gerarListaEvolucoesListaDto(evolucoes));
		evolucao.setEvolucaoSelecionada(evolucoes.get(0));
		evolucao.setPacienteEmObito(pacienteService.verificarPacienteEmObito(rmr, false));
		return evolucao;
	}

	/**
	 * Gerar lista de DTOs a serem exibidos no front-end. (combo de evoluções por paciente na tela de consulta).
	 * 
	 * @param evolucoes
	 * @return
	 */
	private List<EvolucaoListaDto> gerarListaEvolucoesListaDto(List<Evolucao> evolucoes) {
		List<EvolucaoListaDto> evolucoesLista = new ArrayList<EvolucaoListaDto>();
		evolucoes.stream().forEach(e -> {
			EvolucaoListaDto evolucaoListDto = new EvolucaoListaDto();
			evolucaoListDto.setId(e.getId());
			evolucaoListDto.setDataCriacao(e.getDataCriacao());
			evolucaoListDto.setMotivo(e.getMotivo().getDescricao());
			evolucaoListDto.setCondicaoPaciente(e.getCondicaoPaciente().getDescricao());
			evolucoesLista.add(evolucaoListDto);
		});
		return evolucoesLista;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Evolucao obterUltimaEvolucaoDoPaciente(Long rmr) {

		final Evolucao evolucao = evolucaoRepository.findFirstByPacienteRmrOrderByDataCriacaoDesc(rmr);
		if (evolucao == null) {
			throw new BusinessException("evolucao.erro.obter.ultimo.registro", rmr.toString());
		}

		return evolucao;
	}

	/**
	 * {@inheritDoc}
	 */
	@CreateLog(TipoLogEvolucao.NOVA_EVOLUCAO_PARA_PACIENTE)
	@Override
	public void salvar(Evolucao evolucao, List<Pendencia> pendencias, String resposta, Boolean respondePendencia, List<MultipartFile> arquivos) throws IOException {
		if (pendencias == null || pendencias.isEmpty()) {
			throw new BusinessException("evolucao.erro.lista.pendencia");
		}
		if (respondePendencia == null) {
			throw new BusinessException("evolucao.erro.respondePendencia");
		}
		
		Evolucao evolucaoSalvo = salvar(evolucao, arquivos);

		RespostaPendencia respostaPendencia = new RespostaPendencia();
		respostaPendencia.setEvolucao(evolucaoSalvo);
		respostaPendencia.setResposta("\"\"".equals(resposta) ? "" : resposta);
		respostaPendencia.setPendencias(pendencias);
		respostaPendencia.setRespondePendencia(respondePendencia);

		respostaPendenciaService.responder(respostaPendencia);

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Boolean isUltimaEvolucaoAtualizada(Long rmr) {
		Evolucao evolucao = obterUltimaEvolucaoDoPaciente(rmr);
		Configuracao configuracao = configuracaoService.obterConfiguracao(Configuracao.TEMPO_MINIMO_INCLUSAO_EVOLUCAO_EM_SEGUNDOS);
		Long segundos = SECONDS.between(evolucao.getDataCriacao(), LocalDateTime.now());
		Long valorConfiguracao = new Long(configuracao.getValor());
		return segundos <= valorConfiguracao;
	}
	
	@Override
	public Evolucao obterUltimaEvolucaoAtualizadaComVerificacaoDePeriodoMaximoSemAtualizacao(Long rmr) {
		return isUltimaEvolucaoAtualizada(rmr)? obterUltimaEvolucaoDoPaciente(rmr): null;
	}
	

	@Override
	public Paciente obterPaciente(Evolucao evolucao) {
		return evolucao.getPaciente();
	}

	@Override
	public String[] obterParametros(Evolucao evolucao) {
		return evolucao.getPaciente().getRmr().toString().split(";");
	}

	@Override
	public void salvarArquivoEvolucao(List<MultipartFile> listaArquivos, Evolucao evolucao) {

		try {
			ArquivoUtil.validarArquivoExame(listaArquivos, messageSource, configuracaoService);

			Paciente paciente = pacienteService.findById(evolucao.getPaciente().getRmr());
			evolucao.setPaciente(paciente);

			String diretorioEvolucao = AppUtil.gerarStringAleatoriaAlfanumerica();

			Instant instant = Instant.now();
			long timeStampMillis = instant.toEpochMilli();

			// Seta o caminho do arquivo enviado ao storage
			evolucao.getArquivosEvolucao().forEach(arquivo -> {
				arquivo.setCaminhoArquivo(diretorioEvolucao + "/"
						+ ArquivoUtil.obterNomeArquivo(timeStampMillis, arquivo.getCaminhoArquivo()));
			});
			uploadArquivos(listaArquivos, evolucao.getPaciente().getRmr(), diretorioEvolucao, timeStampMillis);
			save(evolucao);
		}
		catch (IOException e) {
			throw new BusinessException("arquivo.erro.upload.arquivo", e);
		}
	}
	
	/**
	 * Método para fazer o upload dos arquivos de laudo para o storage.
	 * 
	 * @param listaArquivos
	 * @param timeStampMillis
	 * @return
	 * @throws IOException
	 */
	private List<CampoMensagem> uploadArquivos(List<MultipartFile> listaArquivos, long rmr,
			String diretorioEvolucao, long timeStampMillis)
			throws IOException {

		for (MultipartFile arquivo : listaArquivos) {
			String diretorio = ArquivoUtil.obterDiretorioArquivosEvolucaoPorPaciente(arquivo, rmr, diretorioEvolucao);
			storageService.upload(ArquivoUtil.obterNomeArquivo(timeStampMillis, arquivo), diretorio,
					arquivo);
		}
		return null;
	}

	@Override
	public boolean verificarSeEvolucaoAtualizada(Paciente paciente) {
		final Evolucao evolucao = 
				obterUltimaEvolucaoDoPaciente(paciente.getRmr());
		
		boolean dentroPrazo = configuracaoService.verificarDentroPrazo(
				evolucao.getDataCriacao(), Configuracao.TEMPO_MAXIMO_CONSIDERAR_EVOLUCAO_ATUALIZADA);
		return dentroPrazo;
	}

	/**
	 * @param condicaoPacienteService the condicaoPacienteService to set
	 */
	@Autowired
	public void setCondicaoPacienteService(ICondicaoPacienteService condicaoPacienteService) {
		this.condicaoPacienteService = condicaoPacienteService;
	}

	/**
	 * @param pacienteService the pacienteService to set
	 */
	@Autowired
	public void setPacienteService(IPacienteService pacienteService) {
		this.pacienteService = pacienteService;
	}

	/**
	 * @param estagioDoencaService the estagioDoencaService to set
	 */
	@Autowired
	public void setEstagioDoencaService(IEstagioDoencaService estagioDoencaService) {
		this.estagioDoencaService = estagioDoencaService;
	}

	/**
	 * @param motivoService the motivoService to set
	 */
	@Autowired
	public void setMotivoService(IMotivoService motivoService) {
		this.motivoService = motivoService;
	}

	/**
	 * @param tipoTransplanteService the tipoTransplanteService to set
	 */
	@Autowired
	public void setTipoTransplanteService(ITipoTransplanteService tipoTransplanteService) {
		this.tipoTransplanteService = tipoTransplanteService;
	}

	/**
	 * @param respostaPendenciaService the respostaPendenciaService to set
	 */
	@Autowired
	public void setRespostaPendenciaService(IRespostaPendenciaService respostaPendenciaService) {
		this.respostaPendenciaService = respostaPendenciaService;
	}

	/**
	 * @param configuracaoService the configuracaoService to set
	 */
	@Autowired
	public void setConfiguracaoService(IConfiguracaoService configuracaoService) {
		this.configuracaoService = configuracaoService;
	}

	/**
	 * @param buscaChecklistService the buscaChecklistService to set
	 */
	@Autowired
	public void setBuscaChecklistService(IBuscaChecklistService buscaChecklistService) {
		this.buscaChecklistService = buscaChecklistService;
	}

	
	

}

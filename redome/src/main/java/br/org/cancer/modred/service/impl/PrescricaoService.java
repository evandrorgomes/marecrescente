package br.org.cancer.modred.service.impl;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import br.org.cancer.modred.controller.dto.SolicitacaoDTO;
import br.org.cancer.modred.exception.BusinessException;
import br.org.cancer.modred.exception.ValidationException;
import br.org.cancer.modred.feign.dto.TarefaDTO;
import br.org.cancer.modred.helper.JsonViewPage;
import br.org.cancer.modred.model.ArquivoPrescricao;
import br.org.cancer.modred.model.Busca;
import br.org.cancer.modred.model.CentroTransplante;
import br.org.cancer.modred.model.Doador;
import br.org.cancer.modred.model.Evolucao;
import br.org.cancer.modred.model.FonteCelula;
import br.org.cancer.modred.model.IDoador;
import br.org.cancer.modred.model.Medico;
import br.org.cancer.modred.model.Paciente;
import br.org.cancer.modred.model.PedidoLogistica;
import br.org.cancer.modred.model.Prescricao;
import br.org.cancer.modred.model.Solicitacao;
import br.org.cancer.modred.model.StatusPedidoLogistica;
import br.org.cancer.modred.model.TipoAmostraPrescricao;
import br.org.cancer.modred.model.annotations.log.CreateLog;
import br.org.cancer.modred.model.domain.FontesCelulas;
import br.org.cancer.modred.model.domain.StatusTarefa;
import br.org.cancer.modred.model.domain.TipoLogEvolucao;
import br.org.cancer.modred.model.domain.TiposTarefa;
import br.org.cancer.modred.model.security.Usuario;
import br.org.cancer.modred.persistence.IPrescricaoRepository;
import br.org.cancer.modred.persistence.IRepository;
import br.org.cancer.modred.service.IArquivoPrescricaoService;
import br.org.cancer.modred.service.IAvaliacaoPrescricaoService;
import br.org.cancer.modred.service.ICentroTransplanteService;
import br.org.cancer.modred.service.IConfiguracaoService;
import br.org.cancer.modred.service.IEvolucaoService;
import br.org.cancer.modred.service.IMedicoService;
import br.org.cancer.modred.service.IPacienteService;
import br.org.cancer.modred.service.IPedidoLogisticaService;
import br.org.cancer.modred.service.IPrescricaoService;
import br.org.cancer.modred.service.ISolicitacaoService;
import br.org.cancer.modred.service.IStorageService;
import br.org.cancer.modred.service.IUsuarioService;
import br.org.cancer.modred.service.impl.invocation.AbstractLoggingService;
import br.org.cancer.modred.util.AppUtil;
import br.org.cancer.modred.util.ArquivoUtil;
import br.org.cancer.modred.util.CampoMensagem;

/**
 * Classe de acesso as funcionalidades que envolvem a entidade Prescrição.
 * 
 * @author bruno.sousa
 *
 */
@Service
@Transactional(propagation=Propagation.REQUIRED)
public class PrescricaoService extends AbstractLoggingService<Prescricao, Long> implements IPrescricaoService {

	private static final Logger LOG = LoggerFactory.getLogger(PrescricaoService.class);

	private static final Long INTERVALO_DIAS_LIMITE_PRESCRICAO_MEDULA = 30L;
	private static final Long INTERVALO_DIAS_LIMITE_PRESCRICAO_CORDAO = 10L;

	@Autowired
	private IPrescricaoRepository prescricaoRepositorio;

	@Autowired
	private IArquivoPrescricaoService arquivoPrescricaoService;

	@Autowired
	private IAvaliacaoPrescricaoService avaliacaoPrescricaoService;

	@Autowired
	private IConfiguracaoService configuracaoService;

	@Autowired
	private IStorageService storageService;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private IEvolucaoService evolucaoService;

	@Autowired
	private IUsuarioService usuarioService;

	@Autowired
	private ISolicitacaoService solicitacaoService;

	@Autowired
	private IMedicoService medicoService;
	
	@Autowired
	private IPedidoLogisticaService pedidoLogisticaService;
	
	@Autowired
	private ICentroTransplanteService centroTransplanteService;
		
	@Autowired
	private IPacienteService pacienteService;

	@Override
	public IRepository<Prescricao, Long> getRepository() {
		return prescricaoRepositorio;
	}

	@CreateLog(TipoLogEvolucao.PRESCRICAO_CRIADA_PARA_MEDULA)
	@Override
	public Prescricao solicitarPrescricaoMedula(SolicitacaoDTO solicitacaoDTO, MultipartFile arquivoJustificativa,
			List<MultipartFile> listaArquivos) {
		LOG.info("Inicio da criação da prescrição medula.");
		if (verificaSeDataColetaEstaDentroLimite(solicitacaoDTO.getDataColeta1(), INTERVALO_DIAS_LIMITE_PRESCRICAO_MEDULA)
				|| verificaSeDataColetaEstaDentroLimite(solicitacaoDTO.getDataColeta2(),
						INTERVALO_DIAS_LIMITE_PRESCRICAO_MEDULA)) {
			ArquivoUtil.validarArquivoJustificativaPrescricao(arquivoJustificativa, messageSource, configuracaoService);
		}
		final Prescricao prescricao = solicitarPrescricao(solicitacaoDTO, arquivoJustificativa, listaArquivos);
		cancelarTarefasCadastrarPrescricaoCordao(prescricao.getSolicitacao().getMatch().getBusca());
		return prescricao;
	}
	
	private void cancelarTarefasCadastrarPrescricaoCordao(Busca busca) {
				
		Page<TarefaDTO> tarefasEncontradas = TiposTarefa.CADASTRAR_PRESCRICAO_CORDAO.getConfiguracao()
				.listarTarefa()
				.comRmr(busca.getPaciente().getRmr())
				.comStatus(Arrays.asList(StatusTarefa.ABERTA, StatusTarefa.ATRIBUIDA))
				.comParceiros(Arrays.asList(busca.getCentroTransplante().getId()))
				.apply().getValue();
		
		tarefasEncontradas.forEach(tarefa -> {
			TiposTarefa.CADASTRAR_PRESCRICAO_CORDAO.getConfiguracao().cancelarTarefa()
				.comTarefa(tarefa.getId())
				.apply();
		});
				
	}

	@CreateLog(TipoLogEvolucao.PRESCRICAO_CRIADA_PARA_CORDAO)
	@Override
	public Prescricao solicitarPrescricaoCordao(SolicitacaoDTO solicitacaoDTO, MultipartFile arquivoJustificativa,
			List<MultipartFile> listaArquivos) {
		LOG.info("Inicio da criação da prescrição cordão.");
		if (verificaSeDataColetaEstaDentroLimite(solicitacaoDTO.getDataColeta1(), INTERVALO_DIAS_LIMITE_PRESCRICAO_CORDAO)
				|| verificaSeDataColetaEstaDentroLimite(solicitacaoDTO.getDataColeta2(),
						INTERVALO_DIAS_LIMITE_PRESCRICAO_CORDAO)) {
			ArquivoUtil.validarArquivoJustificativaPrescricao(arquivoJustificativa, messageSource, configuracaoService);
		}
		final Prescricao prescricao = solicitarPrescricao(solicitacaoDTO, arquivoJustificativa, listaArquivos);
		cancelarTarefaCadastrarPrescricaoMedula(prescricao.getSolicitacao().getMatch().getBusca());		
		
		return prescricao;
	}
	
	private void cancelarTarefaCadastrarPrescricaoMedula(Busca busca) {
						
		TarefaDTO tarefaEncontrada = TiposTarefa.CADASTRAR_PRESCRICAO_MEDULA.getConfiguracao()
				.obterTarefa()				
				.comRmr(busca.getPaciente().getRmr())				
				.comStatus(Arrays.asList(StatusTarefa.ABERTA, StatusTarefa.ATRIBUIDA))
				.comParceiros(Arrays.asList(busca.getCentroTransplante().getId()))
				.apply();
		
		if (tarefaEncontrada != null) {
			TiposTarefa.CADASTRAR_PRESCRICAO_MEDULA.getConfiguracao().cancelarTarefa()
				.comTarefa(tarefaEncontrada.getId())
				.apply();
		}
				
	}

	/**
	 * Solicita a prescrição para o doador independente se medula ou cordão.
	 * 
	 * @param solicitacaoDTO DTO com as informações para a prescrição.
	 * @param arquivoJustificativa arquivo com a justificativa de coleta com prazo abaixo do limite de tempo considerado ideal.
	 * @param listaArquivos lista de arquivos da prescrição.
	 * @return prescrição salva.
	 */
	private Prescricao solicitarPrescricao(SolicitacaoDTO solicitacaoDTO, MultipartFile arquivoJustificativa,
			List<MultipartFile> listaArquivos) {
		ArquivoUtil.validarArquivosPrescricao(listaArquivos, messageSource, configuracaoService);

		if (!evolucaoService.isUltimaEvolucaoAtualizada(solicitacaoDTO.getRmr())) {
			throw new BusinessException("erro.solicitacao.prescricao.nova.evolucao");
		}

		final boolean isCordaoUmbilical = FontesCelulas.CORDAO_UMBILICAL.getFonteCelulaId().equals(solicitacaoDTO
				.getFonteCelulaOpcao1());
		if (!isCordaoUmbilical) {
			validarDivergenciaVolumePorKg(solicitacaoDTO);
		}

		Prescricao prescricao = criarPrescricao(solicitacaoDTO);
		
		

		salvarArquivosPrescricao(prescricao, listaArquivos, arquivoJustificativa, null);

		avaliacaoPrescricaoService.criarAvaliacaoPrescricao(solicitacaoDTO.getRmr(), prescricao);

		TarefaDTO tarefaCadastrarPrescricao = atribuirTarefaCadastrarPrescricao(prescricao);
		tarefaCadastrarPrescricao = atualizarTarefaCadastrarPrescricao(tarefaCadastrarPrescricao, prescricao);
		fecharTarefaCadastrarPrescricao(tarefaCadastrarPrescricao);
		

		return findById(prescricao.getId());
	}
	
	private TarefaDTO atribuirTarefaCadastrarPrescricao(Prescricao prescricao) {
		final Doador doador = prescricao.getSolicitacao().getMatch().getDoador();
		if (doador.isMedula()) {
			return atribuirTarefaCadastrarPrescricaoMedula(prescricao);
		}
		
		return atribuirTarefaCadastrarPrescricaoCordao(prescricao);
		
	}
	
	private TarefaDTO atribuirTarefaCadastrarPrescricaoMedula(Prescricao prescricao) {
		final Long rmr = prescricao.getSolicitacao().getMatch().getBusca().getPaciente().getRmr();
		final Long idCentroTransplante = prescricao.getSolicitacao().getMatch().getBusca().getCentroTransplante().getId();
				
		return TiposTarefa.CADASTRAR_PRESCRICAO_MEDULA.getConfiguracao().atribuirTarefa()
			.comRmr(rmr)
			.comParceiros(Arrays.asList(idCentroTransplante))
			.comUsuarioLogado()
			.apply();
		
	}
	
	private TarefaDTO atribuirTarefaCadastrarPrescricaoCordao(Prescricao prescricao) {
		final Long rmr = prescricao.getSolicitacao().getMatch().getBusca().getPaciente().getRmr();
		final Long idCentroTransplante = prescricao.getSolicitacao().getMatch().getBusca().getCentroTransplante().getId();
		
		final Page<TarefaDTO> tarefasEncontradas = TiposTarefa.CADASTRAR_PRESCRICAO_CORDAO.getConfiguracao().listarTarefa()
			.comRmr(rmr)
			.comParceiros(Arrays.asList(idCentroTransplante))			
			.apply().getValue();

		final TarefaDTO tarefaEncontrada = tarefasEncontradas.get()
				.findFirst()
				.orElseThrow(() -> new BusinessException("erro.interno"));
		
		return TiposTarefa.CADASTRAR_PRESCRICAO_CORDAO.getConfiguracao().atribuirTarefa()
			.comTarefa(tarefaEncontrada)
			.comUsuarioLogado()
			.apply();
			
	}
	
	public TarefaDTO atualizarTarefaCadastrarPrescricao(TarefaDTO tarefa, Prescricao prescricao) {
		return tarefa.getTipo().getConfiguracao().atualizarTarefa()
				 .comTarefa(tarefa.getId())
				 .comObjetoRelacionado(prescricao.getId())
				 .apply();		
		
	}
	
	public void fecharTarefaCadastrarPrescricao(TarefaDTO tarefa) {
		tarefa.getTipo().getConfiguracao().fecharTarefa()
		 	.comTarefa(tarefa.getId())		 
		 	.apply();		
	}
	

	/**
	 * Salva o arquivo de prescrição.
	 * O arquivo nunca poderá ser ao mesmo tempo do tipo justificativa e autorização do paciene 
	 * 
	 * @param prescricao prescrição salva.
	 * @param justificativa - indica que o aquivo é de justificativa
	 * @param autorizacaoPaciente - indica que o arquivo é  de autorização do paciente
	 * @param arquivo arquivo com a justificativa.
	 */
	private void salvarArquivo(Prescricao prescricao, Boolean justificativa, Boolean autorizacaoPaciente, 
			MultipartFile arquivo) {
		
		if (justificativa && autorizacaoPaciente) {
			throw new BusinessException("erro.prescricao.arquivo.justificativa.autorizacao.paciente");
		}
		
		String diretorio = ArquivoUtil.obterDiretorioArquivosPrescricao(prescricao.getId());
		Instant instant = Instant.now();
		final long timeStampMillis = instant.toEpochMilli();

		if (arquivo != null) {
			ArquivoPrescricao arquivoPrescricao = new ArquivoPrescricao();
			arquivoPrescricao.setCaminho(diretorio + "/"
					+ ArquivoUtil.obterNomeArquivo(timeStampMillis, arquivo));
			arquivoPrescricao.setPrescricao(prescricao);
			arquivoPrescricao.setJustificativa(justificativa);
			arquivoPrescricao.setAutorizacaoPaciente(autorizacaoPaciente);
			arquivoPrescricaoService.save(arquivoPrescricao);
			
			Optional.ofNullable(prescricao.getArquivosPrescricao())
				.orElseGet(() -> {
					prescricao.setArquivosPrescricao(new ArrayList<>());
					return prescricao.getArquivosPrescricao();
				})
				.add(arquivoPrescricao);
			
		}

		if (arquivo != null) {
			try {
				storageService.upload(ArquivoUtil.obterNomeArquivo(timeStampMillis, arquivo), diretorio,
						arquivo);
			}
			catch (IOException e) {
				throw new BusinessException("arquivo.erro.upload.arquivo", e);
			}
		}
	}

	/**
	 * Salva os arquivos relacionados a prescrição.
	 * 
	 * @param prescricao prescrição salva no banco.
	 * @param listaArquivos lista de arquivos da prescrição.
	 * @param arquivoJustificativa arquivo de justificativa
	 * @param arquivoAutorizacaoPaciente arquivo de autorização do paciente
	 * @return
	 */
	private void salvarArquivosPrescricao(Prescricao prescricao, List<MultipartFile> listaArquivos, 
			MultipartFile arquivoJustificativa, MultipartFile arquivoAutorizacaoPaciente ) {
		
		Optional.ofNullable(listaArquivos).ifPresent((lista) -> {
			lista.stream().forEach(arquivo -> {
				salvarArquivo(prescricao, false, false, arquivo);
			});	
		});
		
		if (arquivoJustificativa != null) {
			salvarArquivo(prescricao, true, false, arquivoJustificativa);
		}
		
		if (arquivoAutorizacaoPaciente != null) {
			salvarArquivo(prescricao, false, true, arquivoAutorizacaoPaciente);
		}
		
	}

	/**
	 * Instancia e salva uma nova prescrição com as informações passadas pelo DTO. Prescrição pode ser para cordão ou medula,
	 * neste contexto do método.
	 * 
	 * @param solicitacaoDTO DTO contendo as informações a serem incluídas na prescrição.
	 * @return prescrição salva.
	 */
	private Prescricao criarPrescricao(SolicitacaoDTO solicitacaoDTO) {
		Prescricao prescricao = new Prescricao();
		prescricao.setDataColeta1(solicitacaoDTO.getDataColeta1());
		prescricao.setDataColeta2(solicitacaoDTO.getDataColeta2());
		prescricao.setDataResultadoWorkup1(solicitacaoDTO.getDataLimiteWorkup1());
		prescricao.setDataResultadoWorkup2(solicitacaoDTO.getDataLimiteWorkup2());
		prescricao.setFonteCelulaOpcao1(new FonteCelula(solicitacaoDTO.getFonteCelulaOpcao1()));
		prescricao.setQuantidadeTotalOpcao1(solicitacaoDTO.getQuantidadeTotalOpcao1().setScale(2, BigDecimal.ROUND_HALF_EVEN));
		prescricao.setQuantidadePorKgOpcao1(solicitacaoDTO.getQuantidadePorKgOpcao1().setScale(2, BigDecimal.ROUND_HALF_EVEN));
		if (solicitacaoDTO.getFonteCelulaOpcao2() != null) {
			prescricao.setFonteCelulaOpcao2(new FonteCelula(solicitacaoDTO.getFonteCelulaOpcao2()));
		}
		if (solicitacaoDTO.getQuantidadeTotalOpcao2() != null) {
			prescricao.setQuantidadeTotalOpcao2(solicitacaoDTO.getQuantidadeTotalOpcao2().setScale(2,
					BigDecimal.ROUND_HALF_EVEN));
		}
		if (solicitacaoDTO.getQuantidadePorKgOpcao2() != null) {
			prescricao.setQuantidadePorKgOpcao2(solicitacaoDTO.getQuantidadePorKgOpcao2().setScale(2,
					BigDecimal.ROUND_HALF_EVEN));
		}

		if (prescricao.getFonteCelulaOpcao1() != null && prescricao.getFonteCelulaOpcao2() != null) {
			if (prescricao.getFonteCelulaOpcao1().equals(prescricao.getFonteCelulaOpcao2())) {
				throw new BusinessException("erro.solicitacao.prescricao.fonte.celulas.iguais");
			}
		}
		final Evolucao evolucao = evolucaoService.obterUltimaEvolucaoDoPaciente(solicitacaoDTO.getRmr());
		prescricao.setEvolucao(evolucao);

		Solicitacao solicitacao = solicitacaoService.criarSolicitacaoWorkup(solicitacaoDTO.getRmr(),
				solicitacaoDTO.getIdDoador());		

		Medico medico = medicoService.obterMedicoPorUsuario(usuarioService.obterUsuarioLogadoId());
		prescricao.setMedicoResponsavel(medico);
		
		solicitacao = solicitacaoService.save(solicitacao);
		prescricao.setSolicitacao(solicitacao);
		solicitacao.setPrescricao(prescricao);
		
		
		
		
		
		if(!solicitacaoDTO.getTiposAmostraPrescricao().isEmpty()) {
			prescricao.setAmostras(new ArrayList<>());
			solicitacaoDTO.getTiposAmostraPrescricao().forEach(t->{
				prescricao.getAmostras().add(new TipoAmostraPrescricao(null
						, t.getMl()
						, t.getTipoAmostra()
						, prescricao
						, t.getDescricaoOutrosExames()));
			});			
		}
		
		save(prescricao);
		return prescricao;
	}

	/**
	 * Valida a divergência entre peso do paciente e volume da amostra pra conferir se o cálculo bate com o fornecido.
	 * 
	 * @param solicitacaoDTO dados da solitação, inclusive o os valores calculados por KG.
	 * @return
	 */
	private void validarDivergenciaVolumePorKg(SolicitacaoDTO solicitacaoDTO) {
		Evolucao evolucao = evolucaoService.obterUltimaEvolucaoDoPaciente(solicitacaoDTO.getRmr());
		if (solicitacaoDTO.getFonteCelulaOpcao1() != null && solicitacaoDTO.getQuantidadePorKgOpcao1() != null) {
			BigDecimal total = solicitacaoDTO.getQuantidadePorKgOpcao1().setScale(2, BigDecimal.ROUND_HALF_EVEN).multiply(evolucao
					.getPeso()).setScale(2, BigDecimal.ROUND_HALF_EVEN);
			if (!total.equals(solicitacaoDTO.getQuantidadeTotalOpcao1().setScale(2, BigDecimal.ROUND_HALF_EVEN))) {
				throw new ValidationException("erro.validacao", Arrays.asList(
						new CampoMensagem("divergenciaOpcao1", AppUtil.getMensagem(messageSource,
								"erro.solicitacao.prescricao.quantidades.divergentes",
								solicitacaoDTO.getFonteCelulaOpcao1().equals(FontesCelulas.MEDULA_OSSEA.getFonteCelulaId())
										? "TCN" : "CD34",
								solicitacaoDTO.getQuantidadePorKgOpcao1().toString(),
								evolucao.getPeso().toString()))));
			}
		}

		if (solicitacaoDTO.getFonteCelulaOpcao2() != null && solicitacaoDTO.getQuantidadePorKgOpcao2() != null) {
			BigDecimal total = solicitacaoDTO.getQuantidadePorKgOpcao2().setScale(2, BigDecimal.ROUND_HALF_EVEN).multiply(evolucao
					.getPeso()).setScale(2, BigDecimal.ROUND_HALF_EVEN);
			if (!total.equals(solicitacaoDTO.getQuantidadeTotalOpcao2().setScale(2, BigDecimal.ROUND_HALF_EVEN))) {
				throw new ValidationException("erro.validacao", Arrays.asList(
						new CampoMensagem("divergenciaOpcao2", AppUtil.getMensagem(messageSource,
								"erro.solicitacao.prescricao.quantidades.divergentes",
								solicitacaoDTO.getFonteCelulaOpcao2().equals(FontesCelulas.MEDULA_OSSEA.getFonteCelulaId())
										? "TCN" : "CD34",
								solicitacaoDTO.getQuantidadePorKgOpcao2().toString(),
								evolucao.getPeso().toString()))));
			}
		}
	}

	@Override
	protected List<CampoMensagem> validateEntity(Prescricao entity) {
		List<CampoMensagem> mensagens = super.validateEntity(entity);
		if (mensagens.isEmpty()) {
			if (verificaSeDataMenorOuIgualQueDataAtual(entity.getDataResultadoWorkup1())) {
				mensagens.add(new CampoMensagem("dataResultadoWorkup1",
						AppUtil.getMensagem(messageSource, "erro.validacao.data.maior", "Data Limite workup", "Data Atual")));
			}
			if (verificaSeDataMenorOuIgualQueDataAtual(entity.getDataColeta1())) {
				mensagens.add(new CampoMensagem("dataColeta1",
						AppUtil.getMensagem(messageSource, "erro.validacao.data.maior", "Data Coleta", "Data Atual")));
			}
			if (verificaSeDataMenorOuIgualQueDataAtual(entity.getDataResultadoWorkup2())) {
				mensagens.add(new CampoMensagem("dataResultadoWorkup2",
						AppUtil.getMensagem(messageSource, "erro.validacao.data.maior", "Data Limite workup", "Data Atual")));
			}
			if (verificaSeDataMenorOuIgualQueDataAtual(entity.getDataColeta2())) {
				mensagens.add(new CampoMensagem("dataColeta2",
						AppUtil.getMensagem(messageSource, "erro.validacao.data.maior", "Data Coleta", "Data Atual")));
			}

			if (entity.getDataResultadoWorkup1() != null && entity.getDataResultadoWorkup1().isAfter(entity.getDataColeta1())) {
				mensagens.add(new CampoMensagem("dataResultadoWorkup1",
						AppUtil.getMensagem(messageSource, "erro.validacao.data.maior", "Data Limite workup", "Data Coleta")));
			}
			if (entity.getDataResultadoWorkup2() != null && entity.getDataResultadoWorkup2().isAfter(entity.getDataColeta2())) {
				mensagens.add(new CampoMensagem("dataResultadoWorkup2",
						AppUtil.getMensagem(messageSource, "erro.validacao.data.maior", "Data Limite workup", "Data Coleta")));
			}

			if (entity.getDataResultadoWorkup1() != null && entity.getDataResultadoWorkup2() != null) {
				Boolean datasResultadoWorkupIguais = entity.getDataResultadoWorkup1().equals(entity.getDataResultadoWorkup2());
				Boolean datasColetaIguais = entity.getDataColeta1().equals(entity.getDataColeta2());
				if (datasResultadoWorkupIguais && datasColetaIguais) {
					mensagens.add(new CampoMensagem("",
							AppUtil.getMensagem(messageSource, "erro.validacao.data.uma.diferente")));
				}
			}
		}

		return mensagens;
	}

	private Boolean verificaSeDataColetaEstaDentroLimite(LocalDate data, Long diasLimiteColeta) {
		if (data != null && LocalDate.now().isBefore(data)) {
			Long dias = LocalDate.now().until(data, ChronoUnit.DAYS);
			return dias >= 0 && dias <= diasLimiteColeta;
		}
		return false;
	}

	private Boolean verificaSeDataMenorOuIgualQueDataAtual(LocalDate data) {
		if (data != null) {
			return LocalDate.now().isAfter(data) || LocalDate.now().equals(data);
		}
		return false;
	}

	@Override
	public Prescricao obterPrescricao(Solicitacao solicitacao) {
		return prescricaoRepositorio.obterPrescricao(solicitacao);
	}

	@Override
	public Prescricao obterPrescricaoPorBusca(Long idLogistica) {
		return prescricaoRepositorio.obterPrescricaoPorBusca(idLogistica);
	}

	@Override
	public JsonViewPage<TarefaDTO> listarTarefasAutorizacaoPaciente(Long idCentroTransplante, Boolean atribuidoAMin, PageRequest paginacao) {

		final CentroTransplante centroTransplante = centroTransplanteService.obterCentroTransplante(idCentroTransplante);
		final Usuario usuarioLogado = usuarioService.obterUsuarioLogado();
		
		List<StatusTarefa> status = new ArrayList<>();
		status.add(StatusTarefa.ABERTA);
		if (atribuidoAMin) {
			status.add(StatusTarefa.ATRIBUIDA);
		}		
				
		return TiposTarefa.AUTORIZACAO_PACIENTE.getConfiguracao().listarTarefa()
					.comPaginacao(paginacao)
					.comParceiros(Arrays.asList(centroTransplante.getId()))
					.comStatus(status)
					.comUsuario(atribuidoAMin ? usuarioLogado : null)
					.apply();
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override	
	public void salvarArquivoAutorizacaoPaciente(Long idPrescricao, MultipartFile arquivo) {
		Prescricao prescricao = findById(idPrescricao);
		if (prescricao == null) {
			throw new BusinessException("erro.prescricao.invalida");			
		}
		
		ArquivoUtil.validarArquivoAutorizacaoPaciente(arquivo, messageSource, configuracaoService);
		
		salvarArquivosPrescricao(prescricao, null, null, arquivo);
		
		fecharTarefaAutorizacaoPaciente(prescricao);
		
		PedidoLogistica pedidoLogistica = pedidoLogisticaService.obterPedidoLogisticaPeloMatch(prescricao.getSolicitacao().getMatch());
		if (pedidoLogistica != null) {
			pedidoLogistica.setStatus(new StatusPedidoLogistica(StatusPedidoLogistica.ABERTO));
			pedidoLogisticaService.save(pedidoLogistica);
		}
		
	}

	private void fecharTarefaAutorizacaoPaciente(Prescricao prescricao) {
		TiposTarefa.AUTORIZACAO_PACIENTE.getConfiguracao().fecharTarefa()
			.comObjetoRelacionado(prescricao.getId())
			.comStatus(Arrays.asList(StatusTarefa.ATRIBUIDA))
			.comRmr(prescricao.getSolicitacao().getMatch().getBusca().getPaciente().getRmr())
			.comUsuario(usuarioService.obterUsuarioLogado())
			.apply();		
	}
	
	
	@Override
	public File obterArquivoAutorizacaoPaciente(Prescricao prescricao){
		ArquivoPrescricao arquivoAutorizacaoPaciente = 
				arquivoPrescricaoService.obterAutorizacaoPaciente(prescricao.getId());
		return arquivoPrescricaoService.obterArquivoStorage(arquivoAutorizacaoPaciente);
	}
	
	@Override
	public Prescricao obterPrescricoesPorIdentificador(Long idPrescricao) {
		final Prescricao prescricao = findById(idPrescricao);
		if (prescricao == null) {
			throw new BusinessException("mensagem.nenhum.registro.encontrado", "Prescrição");
		}
		return prescricao;
	}

	@Override
	public Paciente obterPaciente(Prescricao prescricao) {		
		return prescricao.getSolicitacao().getMatch().getBusca().getPaciente();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public String[] obterParametros(Prescricao prescricao) {
		IDoador doa = (IDoador) prescricao.getSolicitacao().getMatch().getDoador();
		return doa.getIdentificacao().toString().split(";");
	}
}

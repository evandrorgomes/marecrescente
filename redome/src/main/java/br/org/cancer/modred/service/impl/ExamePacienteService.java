package br.org.cancer.modred.service.impl;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import br.org.cancer.modred.controller.dto.ExameDto;
import br.org.cancer.modred.controller.dto.UltimoPedidoExameDTO;
import br.org.cancer.modred.controller.view.ExameView;
import br.org.cancer.modred.exception.BusinessException;
import br.org.cancer.modred.feign.dto.TarefaDTO;
import br.org.cancer.modred.helper.JsonViewPage;
import br.org.cancer.modred.model.ArquivoExame;
import br.org.cancer.modred.model.Avaliacao;
import br.org.cancer.modred.model.AvaliacaoCamaraTecnica;
import br.org.cancer.modred.model.Busca;
import br.org.cancer.modred.model.Exame;
import br.org.cancer.modred.model.ExamePaciente;
import br.org.cancer.modred.model.Laboratorio;
import br.org.cancer.modred.model.LocusExame;
import br.org.cancer.modred.model.Metodologia;
import br.org.cancer.modred.model.MotivoDescarte;
import br.org.cancer.modred.model.Paciente;
import br.org.cancer.modred.model.Pendencia;
import br.org.cancer.modred.model.Relatorio;
import br.org.cancer.modred.model.RespostaPendencia;
import br.org.cancer.modred.model.StatusAvaliacaoCamaraTecnica;
import br.org.cancer.modred.model.annotations.log.CreateLog;
import br.org.cancer.modred.model.domain.ParametrosRelatorios;
import br.org.cancer.modred.model.domain.StatusExame;
import br.org.cancer.modred.model.domain.StatusPedidosExame;
import br.org.cancer.modred.model.domain.StatusTarefa;
import br.org.cancer.modred.model.domain.TipoLogEvolucao;
import br.org.cancer.modred.model.domain.TiposBuscaChecklist;
import br.org.cancer.modred.model.domain.TiposTarefa;
import br.org.cancer.modred.persistence.IExamePacienteRepository;
import br.org.cancer.modred.persistence.IRepository;
import br.org.cancer.modred.service.IAvaliacaoCamaraTecnicaService;
import br.org.cancer.modred.service.IAvaliacaoService;
import br.org.cancer.modred.service.IBuscaService;
import br.org.cancer.modred.service.IConfiguracaoService;
import br.org.cancer.modred.service.IExamePacienteService;
import br.org.cancer.modred.service.IGenotipoPacienteService;
import br.org.cancer.modred.service.ILocusService;
import br.org.cancer.modred.service.IMetodologiaService;
import br.org.cancer.modred.service.IMotivoDescarteService;
import br.org.cancer.modred.service.IPacienteService;
import br.org.cancer.modred.service.IPedidoExameService;
import br.org.cancer.modred.service.IRelatorioService;
import br.org.cancer.modred.service.IRespostaPendenciaService;
import br.org.cancer.modred.service.IStorageService;
import br.org.cancer.modred.util.AppUtil;
import br.org.cancer.modred.util.ArquivoUtil;
import br.org.cancer.modred.util.CampoMensagem;
import br.org.cancer.modred.vo.report.HtmlReportGenerator;

/**
 * Classe de implementação da interface IExamePacienteService.java. O objetivo é
 * fornecer os acessos ao modelo da entidade ExamePaciente.
 * 
 * @author Pizão
 *
 */
@Service
@Transactional
public class ExamePacienteService extends ExameService<ExamePaciente> implements IExamePacienteService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExamePacienteService.class);

	@Autowired
	private IExamePacienteRepository examePacienteRepository;

	@Autowired
	private IStorageService storageService;

	private IRelatorioService relatorioService;

	private IConfiguracaoService configuracaoService;

	private ILocusService locusService;

	private IRespostaPendenciaService respostaPendenciaService;

	private IMotivoDescarteService motivoDescarteService;

	private IMetodologiaService metodologiaService;

	@Autowired
	private IGenotipoPacienteService genotipoService;

	private IBuscaService buscaService;

	private IPacienteService pacienteService;

	private IPedidoExameService pedidoExameService;

	private BuscaChecklistService buscaChecklistService;

	private IAvaliacaoService avaliacaoService;

	private IAvaliacaoCamaraTecnicaService avaliacaoCamaraTecnicaService;

	/**
	 * Construtor definido para que seja informado as estratégias para os eventos de
	 * criação de notificação.
	 */
	public ExamePacienteService() {
		super();
	}

	@Override
	public IRepository<ExamePaciente, Long> getRepository() {
		return examePacienteRepository;	
	}
	
	@Override
	public List<ExamePaciente> listarExamesPorPaciente(Long pacienteRmr) {
		List<ExamePaciente> exames = examePacienteRepository.findByPacienteRmrAndStatusExameNotOrderByDataExameDesc(pacienteRmr, StatusExame.CONFERIDO.getCodigo());
		exames.forEach(exame -> {
			exame.getArquivosExame().forEach(arquivoExame -> {});
			exame.getLocusExames().forEach(locusExame -> {});
			exame.getMetodologias().forEach(metodologia -> {});
		});
		
		return exames;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ExameDto listarExamesPorRmr(Long rmr) {
		List<ExamePaciente> exames = examePacienteRepository.findByPacienteRmrAndStatusExameNotOrderByDataExameDesc(rmr,
				StatusExame.DESCARTADO.getCodigo());

		ExameDto exameDto = new ExameDto();

		if (!exames.isEmpty()) {
			exameDto.setExameSelecionado(exames.get(0));
			exameDto.setExames(
					exames.stream().map(examePaciente -> (Exame) examePaciente).collect(Collectors.toList()));
			exameDto.setUsernameMedicoResponsavel(
					exames.get(0).getPaciente().getMedicoResponsavel().getUsuario().getUsername());
		}
		exameDto.setBuscaEmMatch(pacienteService.verificarSePacienteProibidoReceberNovosExames(rmr));
		exameDto.setPacienteEmObito(pacienteService.verificarPacienteEmObito(rmr, false));

		Busca busca = buscaService.obterBuscaAtivaPorRmr(rmr);
		if (busca != null) {
			exameDto.setIdBusca(busca.getId());

			UltimoPedidoExameDTO ultimoPedidoExameDTO = pedidoExameService
					.obterUltimoPedidoExamePelaBusca(busca.getId());
			if (ultimoPedidoExameDTO.getPedidoExame() != null && StatusPedidosExame.AGUARDANDO_AMOSTRA.getId()
					.equals(ultimoPedidoExameDTO.getPedidoExame().getStatusPedidoExame().getId())) {
				exameDto.setIdTipoExame(ultimoPedidoExameDTO.getPedidoExame().getTipoExame().getId());
			}
		}

		return exameDto;
	}

	/**
	 * Método para gerar um arquivo zip com os arquivos de laudo de exame.
	 * 
	 * @param idExame id do exame
	 */
	@Override
	public File obterZipArquivosLaudo(Long idExame) throws BusinessException {
		ExamePaciente exame = obterExame(idExame);
		String caminho = null;
		File arquivo = null;
		String[] arquivos = new String[exame.getArquivosExame().size()];
		for (int i = 0; i < exame.getArquivosExame().size(); i++) {
			ArquivoExame arquivoExame = exame.getArquivosExame().get(i);
			arquivoExame.setRmr(exame.getPaciente().getRmr());
			arquivos[i] = arquivoExame.obterNomeArquivo();
		}
		try {
			caminho = exame.getArquivosExame().get(0).obterCaminhoCompletoArquivo();
			arquivo = storageService.obterArquivoZip(caminho, arquivos);
		} catch (IOException e) {
			throw new BusinessException(AppUtil.getMensagem(messageSource, "arquivo.exame.erroaogerararquivo"));
		}
		return arquivo;
	}

	@CreateLog(TipoLogEvolucao.NOVO_EXAME_CADASTRADO_PARA_PACIENTE)
	@Override
	public void salvar(List<MultipartFile> listaArquivosLaudo, ExamePaciente exame) throws Exception {
		salvar(listaArquivosLaudo, exame, Boolean.TRUE);
	}

	@CreateLog(TipoLogEvolucao.NOVO_EXAME_CADASTRADO_PARA_PACIENTE)
	@Override
	public void salvarParaAvaliacao(List<MultipartFile> listaArquivosLaudo, ExamePaciente exame) throws Exception {
		salvar(listaArquivosLaudo, exame, Boolean.FALSE);
	}

	/**
	 * Salva um novo exame para um paciente.
	 * 
	 * @param listaArquivosLaudo
	 * @param exame
	 * @param avaliado           true se este exame não precisa passar por
	 *                           avaliação.
	 * 
	 * @throws Exception
	 */	
	private void salvar(List<MultipartFile> listaArquivosLaudo, ExamePaciente exame, Boolean avaliado)
			throws Exception {
		LOGGER.info("Inicio do salvar um novo exame.");
		// Coloca os atributos vazios como NULL, mas estava dando erro no momento de
		// persistir pois ao alterar
		// a estrutura do objeto o HIBERNATE estava tentando persistir o objeto de forma
		// erronea
		// AppUtil.percorrerObjetoNulandoEntidadesVazias(exame);

		try {
			pacienteService.verificarPacienteEmObito(exame.getPaciente().getRmr(), true);

			validarExame(exame);

			ArquivoUtil.validarArquivoExame(listaArquivosLaudo, messageSource, configuracaoService);

			Paciente paciente = pacienteService.findById(exame.getPaciente().getRmr());
			exame.setPaciente(paciente);

			String diretorioExame = AppUtil.gerarStringAleatoriaAlfanumerica();

			Instant instant = Instant.now();
			long timeStampMillis = instant.toEpochMilli();

			// Seta o caminho do arquivo enviado ao storage
			exame.getArquivosExame().forEach(arquivo -> {
				arquivo.setCaminhoArquivo(diretorioExame + "/"
						+ ArquivoUtil.obterNomeArquivo(timeStampMillis, arquivo.getCaminhoArquivo()));
			});
			LOGGER.info("Inicio do upload de arquivos no storage.");
			uploadArquivos(listaArquivosLaudo, exame.getPaciente().getRmr(), diretorioExame, timeStampMillis);
			LOGGER.info("Fim do upload de arquivos no storage.");
			save(exame);

			if (!avaliado) {
				if (verificaAvaliacoesAprovadas(exame.getPaciente().getRmr())) {
					LOGGER.info("Criando tarefa para o perfil AVALIADOR_EXAME_HLA.");
					TiposTarefa.AVALIAR_EXAME_HLA.getConfiguracao().criarTarefa().comObjetoRelacionado(exame.getId())
							.comRmr(exame.getPaciente().getRmr()).apply();
				}
			}

		} catch (IOException e) {
			throw new BusinessException("arquivo.erro.upload.arquivo", e);
		} finally {
			LOGGER.info("Fim do salvar do novo exame.");
		}
	}

	private Boolean verificaAvaliacoesAprovadas(Long rmr) {
		Boolean avaliacoesAprovadas = false;

		Avaliacao avaliacaoCentroAvaliador = avaliacaoService.obterUltimaAvaliacaoPorPaciente(rmr);
		if (avaliacaoCentroAvaliador != null && avaliacaoCentroAvaliador.aprovada()) {
			avaliacoesAprovadas = true;
		}

		AvaliacaoCamaraTecnica avaliacaoCameraTecnica = avaliacaoCamaraTecnicaService.obterAvaliacaoPor(rmr);
		if (avaliacaoCameraTecnica != null
				&& StatusAvaliacaoCamaraTecnica.APROVADO.equals(avaliacaoCameraTecnica.getStatus().getId())) {
			avaliacoesAprovadas = true;
		}

		return avaliacoesAprovadas;
	}

//	@Override
//	public List<CampoMensagem> validarExame(ExamePaciente exame) {
//		List<CampoMensagem> campos = super.validarExame(exame);
//
//		if (campos.isEmpty() && !exame.getLaboratorioParticular()
//				&& (exame.getLaboratorio() == null || exame.getLaboratorio().getId() == null)) {
//			campos.add(new CampoMensagem("laboratorio",
//					AppUtil.getMensagem(messageSource, "javax.validation.constraints.NotNull.message")));
//		}
//		if (!campos.isEmpty()) {
//			throw new ValidationException("erro.validacao", campos);
//		}
//
//		return campos;
//	}

//	@Override
//	public void validarHla(String codigoLocusId, String valor) {
//		List<CampoMensagem> campos = super.validarHlaGeral(codigoLocusId, valor, false);
//		if (!campos.isEmpty()) {
//			throw new ValidationException(ERRO_VALIDACAO, campos);
//		}		
//	}
//
//	@Override
//	public void validarHlaComAntigeno(String codigoLocusId, String valor) {
//		List<CampoMensagem> campos = super.validarHlaGeral(codigoLocusId, valor, true);
//		if (!campos.isEmpty()) {
//			throw new ValidationException(ERRO_VALIDACAO, campos);
//		}		
//	}

	
	/**
	 * Método para fazer o upload dos arquivos de laudo para o storage.
	 * 
	 * @param listaArquivos
	 * @param timeStampMillis
	 * @return
	 * @throws IOException
	 */
	private List<CampoMensagem> uploadArquivos(List<MultipartFile> listaArquivos, long rmr, String diretorioExame,
			long timeStampMillis) throws IOException {

		for (MultipartFile arquivo : listaArquivos) {
			String diretorio = ArquivoUtil.obterDiretorioArquivosLaudoPorPaciente(arquivo, rmr, diretorioExame);
			storageService.upload(ArquivoUtil.obterNomeArquivo(timeStampMillis, arquivo), diretorio, arquivo);
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws Exception
	 */
	@CreateLog(TipoLogEvolucao.NOVO_EXAME_CADASTRADO_PARA_PACIENTE)
	@Override
	public void salvar(List<MultipartFile> listaArquivosLaudo, ExamePaciente exame, List<Pendencia> pendencias,
			String resposta, Boolean respondePendencia) throws Exception {
		LOGGER.info("Inicio do salvar um novo exame respondendo uma ou mais pendências.");
		try {
			if (pendencias == null || pendencias.isEmpty()) {
				throw new BusinessException("evolucao.erro.lista.pendencia");
			}
			if (respondePendencia == null) {
				throw new BusinessException("evolucao.erro.respondePendencia");
			}

			salvar(listaArquivosLaudo, exame, false);

			RespostaPendencia respostaPendencia = new RespostaPendencia();
			respostaPendencia.setExame(exame);
			respostaPendencia.setResposta("\"\"".equals(resposta) ? "" : resposta);
			respostaPendencia.setPendencias(pendencias);
			respostaPendencia.setRespondePendencia(respondePendencia);

			respostaPendenciaService.responder(respostaPendencia);
		}

		finally {
			LOGGER.info("Fim do salvar do novo exame respondendo uma ou mais pendências.");
		}
	}

	/**
	 * Obtém o exame para conferência.
	 * 
	 * @return Exame
	 */
	/*@Override
	public ExameDto obterExameParaConferencia() {
		ExameDto exameDto = new ExameDto();

		Page<ExamePaciente> pageExame = examePacienteRepository
				.findFirstByStatusExameOrderByDataCriacaoDesc(StatusExame.NAO_VERIFICADO.getCodigo(), null);

		if (pageExame == null || pageExame.getContent() == null || pageExame.getContent().isEmpty()) {
			throw new BusinessException("erro.sem.exame.para.conferencia");
		}

		final List<TarefaDTO> tarefasAtribuidas = processoService.buscarTarefasPor(usuarioService.obterUsuarioLogadoId(),
				null, TiposTarefa.AVALIAR_EXAME_HLA.getId(), StatusTarefa.ATRIBUIDA.getId(),
				Perfis.AVALIADOR_EXAME_HLA.getId(), null, null, null);
		if (!tarefasAtribuidas.isEmpty()) {
			processoService.removerAtribuicaoTarefa(tarefasAtribuidas.get(0));

			List<TarefaDTO> tarefasAutomaticas = processoService.buscarTarefasPor(null,
					tarefasAtribuidas.get(0).getProcesso().getId(), TiposTarefa.TIMEOUT_CONFERENCIA_EXAME.getId(),
					StatusTarefa.ABERTA.getId(), null, null, null, tarefasAtribuidas.get(0).getId());
			if (!tarefasAutomaticas.isEmpty()) {
				processoService.fecharTarefa(tarefasAutomaticas.get(0));
			}
		}

		for (int e = 0; e <= pageExame.getContent().size() - 1; e++) {
			List<TarefaDTO> tarefas = processoService.buscarTarefasPor(null, null, TiposTarefa.AVALIAR_EXAME_HLA.getId(),
					StatusTarefa.ABERTA.getId(), Perfis.AVALIADOR_EXAME_HLA.getId(), null, null,
					pageExame.getContent().get(e).getId());
			if (!tarefas.isEmpty()) {
				exameDto.setExameSelecionado(pageExame.getContent().get(e));
				exameDto.setRmrPaciente(pageExame.getContent().get(e).getPaciente().getRmr());

				processoService.atribuirTarefa(tarefas.get(0), usuarioService.obterUsuarioLogado());

				/*
				 * TarefaDTO tarefaAutomatica = new TarefaDTO(tarefas.get(0).getProcesso(), new
				 * TipoTarefa(TiposTarefa.TIMEOUT_CONFERENCIA_EXAME.getId()), null);
				 * tarefaAutomatica.setRelacaoEntidade(tarefas.get(0).getId());
				 * processoService.criarTarefa(tarefaAutomatica);
				 

				break;
			}
		}

		if (exameDto.getExameSelecionado() == null) {
			throw new BusinessException("erro.sem.exame.para.conferencia");
		}

		return exameDto;
	}
*/
	@Override
	public JsonViewPage<TarefaDTO> listaTarefasDeExamesParaConferencia(Long rmr, String nomePaciente,
			PageRequest pageRequest) {

		JsonViewPage<TarefaDTO> tarefasDeExameParaConferencia = 
				TiposTarefa.AVALIAR_EXAME_HLA.getConfiguracao()
				.listarTarefa()
				.comStatus(Arrays.asList(StatusTarefa.ABERTA, StatusTarefa.ATRIBUIDA))
				.comPaginacao(pageRequest)
				.comFiltro(compararRMRENome(rmr, nomePaciente))
				.apply();
		
		tarefasDeExameParaConferencia.setSerializationView(ExameView.ListaExame.class);
		
		return tarefasDeExameParaConferencia;
	}

	private Predicate<TarefaDTO> compararRMRENome(Long rmr, String nomePaciente) {
		return new Predicate<TarefaDTO>() {

			@Override
			public boolean test(TarefaDTO tarefa) {
				return (rmr == null
						|| ((ExamePaciente) tarefa.getObjetoRelacaoEntidade()).getPaciente().getRmr().equals(rmr))
						&& ((nomePaciente == null || "".trim().equals(nomePaciente))
								|| ((ExamePaciente) tarefa.getObjetoRelacaoEntidade()).getPaciente().getNome().trim()
										.toUpperCase().contains(nomePaciente.trim().toUpperCase()));
			}

		};
	}

	@CreateLog(TipoLogEvolucao.EXAME_ACEITO_PARA_PACIENTE)
	@Override
	public void aceitar(ExamePaciente exameAtualizado) {
		ExamePaciente exameOriginal = examePacienteRepository.findById(exameAtualizado.getId()).get();
		exameOriginal.setEditadoPorAvaliador(false);
		TarefaDTO tarefa = verificarConcorrenciaAoConferirExame(exameOriginal);

		if (isExameEditado(exameOriginal, exameAtualizado)) {
			List<LocusExame> listaDeLocusAseremExcluidos = new ArrayList<LocusExame>();

			atualizarLocusNoExameOriginal(exameAtualizado, exameOriginal, listaDeLocusAseremExcluidos);
			deletarLocusNoExameOriginal(exameOriginal, listaDeLocusAseremExcluidos);
			adicionarNovoLocusNoExameOriginal(exameAtualizado, exameOriginal);

			List<Metodologia> metodologias = exameAtualizado.getMetodologias().stream()
					.map(metodologia -> buscarMetodologiaPorId(metodologia)).collect(Collectors.toList());
			exameOriginal.setMetodologias(metodologias);

			exameOriginal.setDataExame(exameAtualizado.getDataExame());
			exameOriginal.setDataColetaAmostra(exameAtualizado.getDataColetaAmostra());
			exameOriginal.setLaboratorio(exameAtualizado.getLaboratorio());
			exameOriginal.setLaboratorioParticular(exameAtualizado.getLaboratorioParticular());
			exameOriginal.setEditadoPorAvaliador(true);
		}

		Paciente paciente = exameOriginal.getPaciente();
		Busca buscaAtiva = paciente.getBuscaAtiva();

		atualizarStatusExame(exameOriginal, StatusExame.CONFERIDO);

		genotipoService.gerarGenotipo(exameOriginal.getPaciente(), Boolean.FALSE);
		
		fecharTarefaAvaliarExameHla(tarefa.getId());
		
		buscaService.iniciarProcessoDeBusca(exameOriginal.getPaciente().getRmr());

		if (maisDeUmExame(paciente)) {
			buscaChecklistService.criarItemCheckList(buscaAtiva, TiposBuscaChecklist.ALTEROU_GENOTIPO);
		}

	}

	private boolean maisDeUmExame(Paciente paciente) {
		return paciente.getExames().size() > 1;
	}

	private Metodologia buscarMetodologiaPorId(Metodologia metodologia) {
		return metodologiaService.findById(metodologia.getId());
	}

	private void atualizarLocusNoExameOriginal(Exame exameAtualizado, Exame exameOriginal,
			List<LocusExame> listaDeLocusAseremExcluidos) {
		exameOriginal.getLocusExames().forEach(locusExame -> {
			Boolean achouLocusNoExameOriginal = exameAtualizado.getLocusExames().stream()
					.filter(locusExameAtualizado -> locusExameAtualizado.getId().getLocus()
							.equals(locusExame.getId().getLocus()))
					.count() != 0;

			if (achouLocusNoExameOriginal) {
				exameAtualizado.getLocusExames().stream().filter(locusExameAtualizado -> locusExameAtualizado.getId()
						.getLocus().equals(locusExame.getId().getLocus())).forEach(locusExameAtualizado -> {
							locusExame.setPrimeiroAlelo(locusExameAtualizado.getPrimeiroAlelo());
							locusExame.setSegundoAlelo(locusExameAtualizado.getSegundoAlelo());
						});
			} else {
				listaDeLocusAseremExcluidos.add(locusExame);
			}
		});
	}

	private void adicionarNovoLocusNoExameOriginal(Exame exameAtualizado, Exame exameOriginal) {
		exameAtualizado.getLocusExames().forEach(locusExameAtualizado -> {
			Boolean ehNovoLocusExame = exameOriginal.getLocusExames().stream()
					.filter(locusExame -> locusExame.getId().getLocus().equals(locusExameAtualizado.getId().getLocus()))
					.count() == 0;
			if (ehNovoLocusExame) {
				exameOriginal.getLocusExames().add(locusExameAtualizado);
			}
		});
	}

	private void deletarLocusNoExameOriginal(Exame exameOriginal, List<LocusExame> listaDeLocusAseremExcluidos) {
		if (!listaDeLocusAseremExcluidos.isEmpty()) {
			listaDeLocusAseremExcluidos.forEach(locusExameExcluido -> {
				exameOriginal.getLocusExames().remove(locusExameExcluido);
			});
			locusService.delete(listaDeLocusAseremExcluidos);
		}
	}

	@CreateLog(TipoLogEvolucao.EXAME_DESCARTADO_PARA_PACIENTE)
	@Override
	public void descartar(Long exameId, Long motivoDescarteId) {
		ExamePaciente exame = examePacienteRepository.findById(exameId).get();

		TarefaDTO tarefa = verificarConcorrenciaAoConferirExame(exame);

		MotivoDescarte motivoDescarte = motivoDescarteService.obterMotivoDescarte(motivoDescarteId);
		exame.setMotivoDescarte(motivoDescarte);
		atualizarStatusExame(exame, StatusExame.DESCARTADO);
		
		fecharTarefaAvaliarExameHla(tarefa.getId());		
	}
	
	private TarefaDTO fecharTarefaAvaliarExameHla(Long idTarefa) {
		return TiposTarefa.AVALIAR_EXAME_HLA.getConfiguracao().fecharTarefa()
				.comTarefa(idTarefa)
				.apply();
	}

	private TarefaDTO verificarConcorrenciaAoConferirExame(Exame exame) {
		if (!exame.getStatusExame().equals(StatusExame.NAO_VERIFICADO.getCodigo())) {
			throw new BusinessException(HttpStatus.CONFLICT, "exame.ja.conferido");
		} else {
			final Long rmr = ((ExamePaciente) exame).getPaciente().getRmr();
			TarefaDTO tarefa = TiposTarefa.AVALIAR_EXAME_HLA.getConfiguracao().obterTarefa()
				.comRmr(rmr)
				.comStatus(Arrays.asList(StatusTarefa.ABERTA, StatusTarefa.ATRIBUIDA))
				.comObjetoRelacionado(exame.getId())
				.apply();
			
			if (tarefa == null) {
				throw new BusinessException("tarefa.nao.encontrada");
			}
			if (tarefa.getStatusTarefa().equals(StatusTarefa.ATRIBUIDA)) {
				if (!tarefa.getUsuarioResponsavel().equals(usuarioService.obterUsuarioLogadoId())) {
					throw new BusinessException(HttpStatus.CONFLICT, "mensagem.outro.conferente");
				} else {
					return tarefa;
				}
			} else if (tarefa.getStatusTarefa().equals(StatusTarefa.ABERTA)) {
				tarefa = TiposTarefa.AVALIAR_EXAME_HLA.getConfiguracao().atribuirTarefa()
						.comTarefa(tarefa)
						.comUsuarioLogado()
						.apply();
				return tarefa;
			}			
		}
		return null;
	}

	/**
	 * Método para comparar os dados dos exames (original e supostamente editado
	 * pelo conferidor).
	 * 
	 * @param exameOriginal exame orignal sem ediçäo
	 * @param exameEditado  exame supostamente editado
	 * @return isExameEditado
	 */
	private Boolean isExameEditado(ExamePaciente exameOriginal, ExamePaciente exameEditado) {

		return isLaboratoriosDiferentes(exameOriginal.getLaboratorio(), exameEditado.getLaboratorio())
				|| exameOriginal.getLaboratorioParticular().compareTo(exameEditado.getLaboratorioParticular()) != 0
				|| isDatasDiferentes(exameOriginal.getDataColetaAmostra(), exameEditado.getDataColetaAmostra())
				|| isDatasDiferentes(exameOriginal.getDataExame(), exameEditado.getDataExame())
				|| isMetodologiasEditadas(exameOriginal.getId(), exameEditado.getMetodologias(),
						exameOriginal.getMetodologias().size())
				|| isListaLocusDiferente(exameOriginal.getId(), exameEditado.getLocusExames(),
						exameOriginal.getLocusExames().size());
	}

	/**
	 * Método para comparar se o laboratório do exame são diferentes.
	 * 
	 * @param LaboratorioOriginal
	 * @param LaboratorioEditado
	 * @return há ou não diferença
	 */
	private boolean isLaboratoriosDiferentes(Laboratorio laboratorioOriginal, Laboratorio laboratorioEditado) {
		if (laboratorioOriginal != null) {
			return !laboratorioOriginal.equals(laboratorioEditado);
		} else if (laboratorioEditado != null) {
			return !laboratorioEditado.equals(laboratorioOriginal);
		}

		return false;

	}

	/**
	 * Método para comparar de as datas do exame são diferentes.
	 * 
	 * @param dataExameOriginal
	 * @param dataExameEditado
	 * @return há ou não diferença
	 */
	private boolean isDatasDiferentes(LocalDate dataExameOriginal, LocalDate dataExameEditado) {
		return dataExameOriginal.compareTo(dataExameEditado) != 0;
	}

	/**
	 * Método para verificar se há alteração na lista de metodologia.
	 * 
	 * @param exameOriginalId
	 * @param metodologiasEditadas
	 * @param quantidadeMetodologiasExameOriginal
	 * @return há ou não alteração
	 */
	private boolean isMetodologiasEditadas(Long exameOriginalId, List<Metodologia> metodologiasEditadas,
			int quantidadeMetodologiasExameOriginal) {

		Boolean haMetodologiaNova = false;

		// se a nova lista de metodologias tiver um tamanho diferente da original
		// então houve edição do exame
		if (isListasMetodologiaTamanhoDiferente(metodologiasEditadas, quantidadeMetodologiasExameOriginal)) {
			return true;
		}

		// caso não seja encontrado um exame com a sigla
		// informada sabemos que a metodologia foi acrescentada, ou seja,
		// a lista de metodologias foi editada
		for (Metodologia metodologia : metodologiasEditadas) {
			Exame exame = examePacienteRepository.findByIdAndMetodologiasSigla(exameOriginalId, metodologia.getSigla());
			if (exame == null) {
				haMetodologiaNova = true;
				break;
			}
		}
		return haMetodologiaNova;
	}

	/**
	 * Método para comparar as listas de lócus do exame original e do editado.
	 * 
	 * @param exameOriginalId
	 * @param lociEditado
	 * @param quantidadeLocusExameOriginal
	 * @return alteração
	 */
	private boolean isListaLocusDiferente(Long exameOriginalId, List<LocusExame> lociEditado,
			int quantidadeLocusExameOriginal) {

		boolean haLocusNovo = false;

		if (isListaLociTamanhoDiferente(lociEditado, quantidadeLocusExameOriginal)) {
			return true;
		}

		// comparando se o exame original tem o mesmo grupo alélico e lócus
		for (LocusExame locusExameEditado : lociEditado) {
			LocusExame locusExameEncontrado = locusService.obterLocusExamePorExameIdGrupoAlelicoAlelos(exameOriginalId,
					locusExameEditado.getId().getLocus().getCodigo(), locusExameEditado.getPrimeiroAlelo(),
					locusExameEditado.getSegundoAlelo());

			if (locusExameEncontrado == null) {
				haLocusNovo = true;
				break;
			}
		}

		return haLocusNovo;
	}

	/**
	 * Método para comprar os tamanhos da lista de loci.
	 * 
	 * @param lociEditado
	 * @param quantidadeLocusExameOriginal
	 * @return alteração
	 */
	private boolean isListaLociTamanhoDiferente(List<LocusExame> lociEditado, int quantidadeLocusExameOriginal) {
		return lociEditado.size() != quantidadeLocusExameOriginal;
	}

	/**
	 * Método para comparar os tamanhos das listas de metodologia.
	 * 
	 * @param metodologiasEditadas
	 * @param quantidadeMetodologiasExameOriginal
	 * @return alteração
	 */
	private boolean isListasMetodologiaTamanhoDiferente(List<Metodologia> metodologiasEditadas,
			int quantidadeMetodologiasExameOriginal) {
		return (metodologiasEditadas.size() != quantidadeMetodologiasExameOriginal);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void criarProcessoConferenciaExames(Paciente paciente) {
		LOGGER.info("Criando tarefa para o perfil AVALIADOR_EXAME_HLA.");

		paciente.getExames().stream().filter(exame -> StatusExame.NAO_VERIFICADO.getId().equals(exame.getStatusExame())) 
			.forEach(exame -> {
				TiposTarefa.AVALIAR_EXAME_HLA.getConfiguracao().criarTarefa()
					.comObjetoRelacionado(exame.getId())
					.comRmr(exame.getPaciente().getRmr())
					.apply();
		});

	}

	@Override
	public List<ExamePaciente> listarInformacoesExames(Long rmr) {
		List<ExamePaciente> examesPorPaciente = examePacienteRepository.listarExamesConferidos(rmr);
		if (CollectionUtils.isNotEmpty(examesPorPaciente)) {
			examesPorPaciente.forEach(exame -> {
				List<LocusExame> locusExamesPorExame = locusService.listarLocusExames(exame.getId());
				exame.setLocusExames(locusExamesPorExame);
			});
		}
		return examesPorPaciente;
	}

	@Override
	public Paciente obterPaciente(ExamePaciente exame) {
		return exame.getPaciente();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.org.cancer.modred.service.IExameService#obterPedidoExameC()
	 */
	@Override
	@Transactional(readOnly = true)
	public File obterPedidoExame(Long idExame, String codigo) {
		ExamePaciente exame = obterExame(idExame);
		Relatorio relatorio = relatorioService.obterRelatorioPorCodigo(codigo);

		Paciente paciente = exame.getPaciente();
		LocalDate hoje = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		return new HtmlReportGenerator()
				.comParametro(ParametrosRelatorios.UF_PACIENTE,
						paciente.getEnderecosContato().get(0).getMunicipio().getUf().getSigla())
				.comParametro(ParametrosRelatorios.CIDADE_PACIENTE,
						paciente.getEnderecosContato().get(0).getMunicipio().getDescricao())
				.comParametro(ParametrosRelatorios.DATA_ATUAL, hoje.format(formatter))
				.comParametro(ParametrosRelatorios.DATA_NASCIMENTO_PACIENTE,
						paciente.getDataNascimento().format(formatter))
				.comParametro(ParametrosRelatorios.DIAGNOSTICO_PACIENTE,
						paciente.getDiagnostico().getCid().getDescricao())
				.comParametro(ParametrosRelatorios.NOME_MEDICO, paciente.getMedicoResponsavel().getNome())
				.comParametro(ParametrosRelatorios.NOME_PACIENTE, paciente.getNome())
				.comParametro(ParametrosRelatorios.RACA_PACIENTE, paciente.getRaca().getNome())
				.comParametro(ParametrosRelatorios.RMR, paciente.getRmr().toString()).gerarPdf(relatorio);

	}

	/**
	 * @param genotipoService the genotipoService to set
	 */
/*	@Autowired
	public void setGenotipoService(IGenotipoPacienteService genotipoService) {
		this.genotipoService = genotipoService;
	}*/

	/**
	 * @param relatorioService the relatorioService to set
	 */
	@Autowired
	public void setRelatorioService(IRelatorioService relatorioService) {
		this.relatorioService = relatorioService;
	}

	/**
	 * @param configuracaoService the configuracaoService to set
	 */
	@Autowired
	public void setConfiguracaoService(IConfiguracaoService configuracaoService) {
		this.configuracaoService = configuracaoService;
	}

	/**
	 * @param locusService the locusService to set
	 */
	@Autowired
	public void setLocusService(ILocusService locusService) {
		this.locusService = locusService;
	}

	/**
	 * @param respostaPendenciaService the respostaPendenciaService to set
	 */
	@Autowired
	public void setRespostaPendenciaService(IRespostaPendenciaService respostaPendenciaService) {
		this.respostaPendenciaService = respostaPendenciaService;
	}

	/**
	 * @param motivoDescarteService the motivoDescarteService to set
	 */
	@Autowired
	public void setMotivoDescarteService(IMotivoDescarteService motivoDescarteService) {
		this.motivoDescarteService = motivoDescarteService;
	}


	/**
	 * @param metodologiaService the metodologiaService to set
	 */
	@Autowired
	public void setMetodologiaService(IMetodologiaService metodologiaService) {
		this.metodologiaService = metodologiaService;
	}

	/**
	 * @param buscaService the buscaService to set
	 */
	@Autowired
	public void setBuscaService(IBuscaService buscaService) {
		this.buscaService = buscaService;
	}

	/**
	 * @param pacienteService the pacienteService to set
	 */
	@Autowired
	public void setPacienteService(IPacienteService pacienteService) {
		this.pacienteService = pacienteService;
	}

	/**
	 * @param pedidoExameService the pedidoExameService to set
	 */
	@Autowired
	public void setPedidoExameService(IPedidoExameService pedidoExameService) {
		this.pedidoExameService = pedidoExameService;
	}

	/**
	 * @param buscaChecklistService the buscaChecklistService to set
	 */
	@Autowired
	public void setBuscaChecklistService(BuscaChecklistService buscaChecklistService) {
		this.buscaChecklistService = buscaChecklistService;
	}

	/**
	 * @param avaliacaoService the avaliacaoService to set
	 */
	@Autowired
	public void setAvaliacaoService(IAvaliacaoService avaliacaoService) {
		this.avaliacaoService = avaliacaoService;
	}

	/**
	 * @param avaliacaoCamaraTecnicaService the avaliacaoCamaraTecnicaService to set
	 */
	@Autowired
	public void setAvaliacaoCamaraTecnicaService(IAvaliacaoCamaraTecnicaService avaliacaoCamaraTecnicaService) {
		this.avaliacaoCamaraTecnicaService = avaliacaoCamaraTecnicaService;
	}

}

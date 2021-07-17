package br.org.cancer.modred.service.impl;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.ibm.cloud.objectstorage.services.s3.model.S3ObjectSummary;

import br.org.cancer.modred.configuration.ApplicationConfiguration;
import br.org.cancer.modred.controller.dto.DetalheMaterialDTO;
import br.org.cancer.modred.controller.dto.LogisticaDTO;
import br.org.cancer.modred.controller.dto.TransportadoraListaDTO;
import br.org.cancer.modred.exception.BusinessException;
import br.org.cancer.modred.feign.client.ITarefaFeign;
import br.org.cancer.modred.feign.dto.TarefaDTO;
import br.org.cancer.modred.helper.ICriarTarefa;
import br.org.cancer.modred.helper.JsonViewPage;
import br.org.cancer.modred.model.ArquivoVoucherLogistica;
import br.org.cancer.modred.model.BancoSangueCordao;
import br.org.cancer.modred.model.Busca;
import br.org.cancer.modred.model.CentroTransplante;
import br.org.cancer.modred.model.ContatoTelefonicoDoador;
import br.org.cancer.modred.model.CordaoInternacional;
import br.org.cancer.modred.model.CordaoNacional;
import br.org.cancer.modred.model.Doador;
import br.org.cancer.modred.model.DoadorInternacional;
import br.org.cancer.modred.model.DoadorNacional;
import br.org.cancer.modred.model.EmailContatoDoador;
import br.org.cancer.modred.model.EnderecoContatoCentroTransplante;
import br.org.cancer.modred.model.EnderecoContatoDoador;
import br.org.cancer.modred.model.IDoador;
import br.org.cancer.modred.model.LogEvolucao;
import br.org.cancer.modred.model.Match;
import br.org.cancer.modred.model.Paciente;
import br.org.cancer.modred.model.PedidoColeta;
import br.org.cancer.modred.model.PedidoLogistica;
import br.org.cancer.modred.model.PedidoTransporte;
import br.org.cancer.modred.model.PedidoWorkup;
import br.org.cancer.modred.model.Prescricao;
import br.org.cancer.modred.model.Relatorio;
import br.org.cancer.modred.model.Solicitacao;
import br.org.cancer.modred.model.StatusPedidoLogistica;
import br.org.cancer.modred.model.TipoPedidoLogistica;
import br.org.cancer.modred.model.annotations.log.CreateLog;
import br.org.cancer.modred.model.domain.FontesCelulas;
import br.org.cancer.modred.model.domain.OrigemLogistica;
import br.org.cancer.modred.model.domain.ParametrosRelatorios;
import br.org.cancer.modred.model.domain.Perfis;
import br.org.cancer.modred.model.domain.StatusPedidosLogistica;
import br.org.cancer.modred.model.domain.StatusPedidosWorkup;
import br.org.cancer.modred.model.domain.StatusTarefa;
import br.org.cancer.modred.model.domain.TipoAlteracaoPedidoLogistica;
import br.org.cancer.modred.model.domain.TipoLogEvolucao;
import br.org.cancer.modred.model.domain.TiposDoador;
import br.org.cancer.modred.model.domain.TiposPedidoLogistica;
import br.org.cancer.modred.model.domain.TiposTarefa;
import br.org.cancer.modred.model.domain.TiposVoucher;
import br.org.cancer.modred.model.security.Usuario;
import br.org.cancer.modred.persistence.IPedidoLogisticaRepository;
import br.org.cancer.modred.persistence.IRepository;
import br.org.cancer.modred.service.IArquivoVoucherLogisticaService;
import br.org.cancer.modred.service.IBuscaService;
import br.org.cancer.modred.service.ICentroTransplanteService;
import br.org.cancer.modred.service.IConfiguracaoService;
import br.org.cancer.modred.service.IConstanteRelatorioService;
import br.org.cancer.modred.service.IContatoTelefonicoDoadorService;
import br.org.cancer.modred.service.IDoadorNacionalService;
import br.org.cancer.modred.service.IEmailContatoDoadorService;
import br.org.cancer.modred.service.IEnderecoContatoDoadorService;
import br.org.cancer.modred.service.IPacienteService;
import br.org.cancer.modred.service.IPedidoColetaService;
import br.org.cancer.modred.service.IPedidoLogisticaService;
import br.org.cancer.modred.service.IPedidoTransporteService;
import br.org.cancer.modred.service.IPedidoWorkupService;
import br.org.cancer.modred.service.IPrescricaoService;
import br.org.cancer.modred.service.IRelatorioService;
import br.org.cancer.modred.service.IStorageService;
import br.org.cancer.modred.service.ITransporteTerrestreService;
import br.org.cancer.modred.service.IUsuarioService;
import br.org.cancer.modred.service.impl.config.Equals;
import br.org.cancer.modred.service.impl.config.Filter;
import br.org.cancer.modred.service.impl.config.UpdateSet;
import br.org.cancer.modred.service.impl.invocation.AbstractLoggingService;
import br.org.cancer.modred.util.AppUtil;
import br.org.cancer.modred.util.ArquivoUtil;
import br.org.cancer.modred.util.CampoMensagem;
import br.org.cancer.modred.vo.report.HtmlReportGenerator;

/**
 * Classe de funcionalidades envolvendo a entidade PedidoLogistica.
 * 
 * @author Pizão
 *
 */
@Service
@Transactional
public class PedidoLogisticaService extends AbstractLoggingService<PedidoLogistica, Long> implements IPedidoLogisticaService {

	private static final Logger LOGGER = LoggerFactory.getLogger(PedidoLogisticaService.class);

	@Autowired
	private IPedidoLogisticaRepository pedidoLogisticaRepositorio;
	
	@Autowired
	@Lazy(true)
	private ITarefaFeign tarefaFeign;
	
	private IUsuarioService usuarioService;

	private IPacienteService pacienteService;
	
	private IDoadorNacionalService doadorService;
	
	private IContatoTelefonicoDoadorService contatoTelefonicoDoadorService;

	private IEnderecoContatoDoadorService enderecoContatoDoadorService;

	private IEmailContatoDoadorService emailContatoDoadorService;

	private ICentroTransplanteService centroTransplanteService;

	private IConfiguracaoService configuracaoService;

	@SuppressWarnings("rawtypes")
	@Autowired
	private IStorageService storageService;

	private IArquivoVoucherLogisticaService arquivoVoucherLogisticaService;

	private ITransporteTerrestreService transporteTerrestreService;

	private IPedidoTransporteService pedidoTransporteService;

	private IBuscaService buscaService;
	
	private IPedidoColetaService pedidoColetaService;
	
	private IPedidoWorkupService pedidoWorkupService;
	
	private IPrescricaoService prescricaoService;
	
	private IRelatorioService relatorioService;
	
	private IConstanteRelatorioService constanteRelatorioService;
	
	@Override
	public IRepository<PedidoLogistica, Long> getRepository() {
		return pedidoLogisticaRepositorio;
	}

	/**
	 * Criar a tarefa associada ao pedido de logística. Se o usuário logado tiver o perfil de Analista Logística, será atribuído
	 * para ele, mas o status será mantido como ABERTO, no momento da criação.
	 * 
	 * @param pedidoLogistica pedido de logística que originou a criação da tarefa.
	 */
	private void criarTarefaLogistica(PedidoLogistica pedidoLogistica) {
		TiposTarefa tipoTarefa = null;
		Paciente paciente = null;
		if (pedidoLogistica.getPedidoWorkup() != null) {
			paciente = pacienteService.obterPacientePorSolicitacao(pedidoLogistica.getPedidoWorkup().getSolicitacao().getId());
			tipoTarefa = TiposTarefa.INFORMAR_LOGISTICA_DOADOR_WORKUP;
		}
		if (pedidoLogistica.getPedidoColeta() != null) {
			paciente = pacienteService.obterPacientePorSolicitacao(pedidoLogistica.getPedidoColeta().getSolicitacao().getId());
			if (pedidoLogistica.getTipo().getId().equals(TiposPedidoLogistica.DOADOR.getId())) {
				tipoTarefa = TiposTarefa.PEDIDO_LOGISTICA_DOADOR_COLETA;
			}
			else if (pedidoLogistica.getTipo().getId().equals(TiposPedidoLogistica.MATERIAL.getId())) {
				if(OrigemLogistica.NACIONAL.equals(pedidoLogistica.getOrigem())){
					tipoTarefa = TiposTarefa.INFORMAR_DOCUMENTACAO_MATERIAL_AEREO;
				}
				else {
					tipoTarefa = TiposTarefa.PEDIDO_LOGISTICA_MATERIAL_INTERNACIONAL;
				}
			}
		}
		
		ICriarTarefa criarTarefaEvento = 
				tipoTarefa.getConfiguracao()
					.criarTarefa()
					.comObjetoRelacionado(pedidoLogistica.getId())
					.comRmr(paciente.getRmr());
				
		if (usuarioService.usuarioLogadoPossuiPerfil(Perfis.ANALISTA_LOGISTICA)) {
			criarTarefaEvento.comUsuario(usuarioService.obterUsuarioLogado());
		}
		criarTarefaEvento.apply();
	}

	/**
	 * Obtém a tarefa associada ao pedido de logística.
	 * 
	 * @param pedido pedido logística a ser pesquisado.
	 * @return tarefa, caso exista.
	 */
	private TarefaDTO obterTarefaLogistica(PedidoLogistica pedido) {
		
		if (pedido.isLogisticaWorkup()) {
			final Long rmr = pedido.getPedidoWorkup().getSolicitacao().getMatch().getBusca().getPaciente().getRmr();
			return TiposTarefa.INFORMAR_LOGISTICA_DOADOR_WORKUP.getConfiguracao().obterTarefa()
					.comRmr(rmr)
					.comObjetoRelacionado(pedido.getId())
					.comStatus(Arrays.asList(StatusTarefa.ABERTA, StatusTarefa.ATRIBUIDA))
					.apply();
		}
		final Long rmr = pedido.getPedidoColeta().getSolicitacao().getMatch().getBusca().getPaciente().getRmr();
		return TiposTarefa.PEDIDO_LOGISTICA_DOADOR_COLETA.getConfiguracao().obterTarefa()
				.comRmr(rmr)
				.comObjetoRelacionado(pedido.getId())
				.comStatus(Arrays.asList(StatusTarefa.ABERTA, StatusTarefa.ATRIBUIDA))
				.apply();
		
	}

	@Override
	public PedidoLogistica criarLogistica(PedidoWorkup pedido) {
		return criarLogistica(pedido, null, TiposPedidoLogistica.DOADOR);
	}

	@CreateLog
	@Override
	public PedidoLogistica criarLogistica(PedidoColeta pedido, TiposPedidoLogistica tipo) {
		return criarLogistica(null, pedido, tipo);
	}

	/**
	 * Cria pedido de logística, nacional ou internacional, associado a 
	 * pedido de workup (logística para o doador) ou pedido de coleta (todos os demais tipos).
	 * 
	 * @param pedidoWorkup pedido de workup, se for uma logística envolvendo workup.
	 * @param pedidoColeta pedido de coleta, se for uma logística envolvendo coleta.
	 * @param tiposPedidoLogistica tipo da logística que define o que será transportado.
	 * 
	 * @return o pedido de logística criado.
	 */
	private PedidoLogistica criarLogistica(PedidoWorkup pedidoWorkup, PedidoColeta pedidoColeta,
			TiposPedidoLogistica tiposPedidoLogistica) {
		PedidoLogistica logistica = new PedidoLogistica();
		Doador doador = null;
		
		if (pedidoWorkup != null) {
			logistica.setPedidoWorkup(pedidoWorkup);
			doador = pedidoWorkupService.obterDoador(pedidoWorkup);
		}
		else if (pedidoColeta != null) {
			logistica.setPedidoColeta(pedidoColeta);
			doador = pedidoColetaService.obterDoador(pedidoColeta);
		}
		else {
			LOGGER.error("Ao criar logística, pedido de workup e coleta foram passados em branco.");
			throw new IllegalArgumentException("Erro ao tentar criar logística sem associá-la pedido de workup ou coleta.");
		}

		logistica.setOrigem(doador.isNacional() ? OrigemLogistica.NACIONAL : OrigemLogistica.INTERNACIONAL);
		logistica.setTipo(new TipoPedidoLogistica(tiposPedidoLogistica.getId()));
		logistica.setStatus(new StatusPedidoLogistica(StatusPedidosLogistica.ABERTO.getId()));

		if (usuarioService.usuarioLogadoPossuiPerfil(Perfis.ANALISTA_LOGISTICA)) {
			logistica.setUsuarioResponsavel(usuarioService.obterUsuarioLogado());
		}

		PedidoLogistica pedidoLogistica = save(logistica);

		criarTarefaLogistica(pedidoLogistica);

		return pedidoLogistica;
	}

	@Override
	public PedidoLogistica obterLogistica(PedidoWorkup pedido, TiposPedidoLogistica tipoLogistica) {
		Filter<Long> porPedidoWorkup = new Equals<Long>("pedidoWorkup.id", pedido.getId());
		Filter<Long> porTipo = new Equals<Long>("tipo.id", tipoLogistica.getId());
		return findOne(porPedidoWorkup, porTipo);
	}

	@Override
	public Solicitacao obterSolicitacaoWorkup(Long pedidoLogisticaId) {
		return pedidoLogisticaRepositorio.obterSolicitacaoWorkup(pedidoLogisticaId);
	}
	
	@Override
	public PedidoWorkup obterWorkup(Long pedidoLogisticaId) {
		return pedidoLogisticaRepositorio.obterWorkup(pedidoLogisticaId);
	}
	
	@Override
	public PedidoColeta obterColeta(Long pedidoLogisticaId) {
		return pedidoLogisticaRepositorio.obterColeta(pedidoLogisticaId);
	}

	@Override
	public LogisticaDTO obterLogisticaDoador(Long pedidoLogisticaId) {		

		PedidoLogistica pedidoLogistica = findById(pedidoLogisticaId);
		
		Doador doador = obterDoadorPorPedidoLogistica(pedidoLogistica);
		
		TarefaDTO tarefa = obterTarefaLogistica(pedidoLogistica);
		
		List<ContatoTelefonicoDoador> telefones = contatoTelefonicoDoadorService.listarTelefones(doador.getId());
		List<EnderecoContatoDoador> enderecos = enderecoContatoDoadorService.listarEnderecos(doador.getId());
		List<EmailContatoDoador> emails = emailContatoDoadorService.listarEmails(doador.getId());
		CentroTransplante centroTransplante = obterCentroColetaLogistica(pedidoLogistica);
		
		excluirArquivosNaoVinculados(pedidoLogistica);

		LogisticaDTO dto = new LogisticaDTO();
		
//		dto.setHospedagens(pedidoLogistica.get);
		dto.setAereos(pedidoLogistica.getVouchers().stream().filter(p->p.getTipo().equals(TiposVoucher.TRANSPORTE_AEREO.getId())).collect(Collectors.toList()));
		dto.setHospedagens(pedidoLogistica.getVouchers().stream().filter(p->p.getTipo().equals(TiposVoucher.HOSPEDAGEM.getId())).collect(Collectors.toList()));
		dto.setTransporteTerrestre(pedidoLogistica.getTransporteTerrestre());
		
		dto.setIdDoador(doador.getId());
		dto.setTarefaId(tarefa.getId());
		dto.setPedidoLogisticaId(pedidoLogisticaId);

		dto.setDataInicio(obterDataInicialLogistica(pedidoLogistica));
		dto.setDataFinal(obterDataFinalLogistica(pedidoLogistica));
		dto.setCentroColeta(centroTransplante);
		dto.setTelefones(telefones);
		dto.setEnderecos(enderecos);
		dto.setObservacao(pedidoLogistica.getObservacao());
		dto.setEmails(emails);
		dto.setNomeDoador(doador instanceof DoadorNacional? ((DoadorNacional) doador).getNome():null);
		dto.setDmr(doador instanceof DoadorNacional? ((DoadorNacional) doador).getDmr():null);
		dto.setRmr(obterRmrDoPedidoDeLogistica(pedidoLogistica));		
		dto.getCentroColeta().setCentroTransplanteUsuarios(new ArrayList<>());

		return dto;
	}

	private Doador obterDoadorPorPedidoLogistica(PedidoLogistica pedidoLogistica) {
		return pedidoLogistica.getPedidoWorkup() != null ? pedidoLogistica.getPedidoWorkup().getSolicitacao().getMatch().getDoador():pedidoLogistica.getPedidoColeta().getSolicitacao().getMatch().getDoador();
	}
	
	private Long obterRmrDoPedidoDeLogistica(PedidoLogistica pedidoLogistica) {
		return pedidoLogistica.getPedidoWorkup() != null? pedidoLogistica.getPedidoWorkup().getSolicitacao().getPaciente().getRmr() : pedidoLogistica.getPedidoColeta().getSolicitacao().getPaciente().getRmr();
	}

	private LocalDate obterDataInicialLogistica(PedidoLogistica pedidoLogistica) {
		return pedidoLogistica.isLogisticaWorkup() ? pedidoLogistica.getPedidoWorkup().getDataInicioWorkup()
				: pedidoLogistica.getPedidoColeta().getDataDisponibilidadeDoador();
	}

	private LocalDate obterDataFinalLogistica(PedidoLogistica pedidoLogistica) {
		return pedidoLogistica.isLogisticaWorkup() ? pedidoLogistica.getPedidoWorkup().getDataFinalWorkup()
				: pedidoLogistica.getPedidoColeta().getDataLiberacaoDoador();
	}

	private CentroTransplante obterCentroColetaLogistica(PedidoLogistica pedidoLogistica) {
		CentroTransplante coleta = pedidoLogistica.isLogisticaWorkup() ? pedidoLogistica.getPedidoWorkup().getCentroColeta()
				: pedidoLogistica.getPedidoColeta().getCentroColeta();
		return centroTransplanteService.findById(coleta.getId());
	}

	@SuppressWarnings("unchecked")
	@Override
	public void excluirArquivosNaoVinculados(PedidoLogistica pedidoLogistica) {
		
		if (pedidoLogistica != null &&	StatusPedidosLogistica.ABERTO.getId().equals(pedidoLogistica.getStatus().getId())) {
		
			List<S3ObjectSummary> objectsSummary = storageService.localizarArquivosPorDiretorio(
					StorageService.DIRETORIO_PEDIDO_LOGISTICA + "/" + pedidoLogistica.getId());
			
			final List<ArquivoVoucherLogistica> voucherSalvos = arquivoVoucherLogisticaService.listarPorPedidoLogistica(pedidoLogistica.getId());
				
			objectsSummary.forEach(objectFile -> {
				boolean achou = voucherSalvos.stream().anyMatch(voucher -> voucher.getCaminho().equals(objectFile.getKey()));
				if (!achou) {
					storageService.removerArquivo(objectFile.getKey());
				}
			});
			
		}
	}

	@Override
	public String adicionarVoucher(Long pedidoLogisticaId, MultipartFile arquivo) {
		LOGGER.info("Inicio do salvar um novo voucher para o pedido de logistica.");

		try {
			ArquivoUtil.validarArquivoVoucher(arquivo, messageSource, configuracaoService);

			String diretorio = ArquivoUtil.obterDiretorioArquivosVoucherPedidoLogistica(pedidoLogisticaId);

			Instant instant = Instant.now();
			long timeStampMillis = instant.toEpochMilli();

			// Seta o caminho do arquivo enviado ao storage
			String caminho = diretorio + "/" + ArquivoUtil.obterNomeArquivo(timeStampMillis, arquivo);

			LOGGER.info("Inicio do upload de arquivos no storage.");

			storageService.upload(ArquivoUtil.obterNomeArquivo(timeStampMillis, arquivo), diretorio,
					arquivo);

			LOGGER.info("Fim do upload de arquivos no storage.");

			return caminho;

		}
		catch (IOException e) {
			throw new BusinessException("arquivo.erro.upload.arquivo", e);
		}
		finally {
			LOGGER.info("Fim do salvar do novo exame.");
		}
	}

	@Override
	public void salvar(LogisticaDTO logistica, StatusPedidosLogistica status) {
		
		List<UpdateSet<?>> listaUpdates = new ArrayList<>();
		
		listaUpdates.add(new UpdateSet<Usuario>("usuarioResponsavel", usuarioService.obterUsuarioLogado()));
		listaUpdates.add(new UpdateSet<LocalDateTime>("dataAtualizacao", LocalDateTime.now()));
		listaUpdates.add(new UpdateSet<String>("observacao", logistica.getObservacao()));
		
		if (status != null) {
			listaUpdates.add(new UpdateSet<StatusPedidoLogistica>("status", new StatusPedidoLogistica(status) ));
		}
		
		update(listaUpdates,				
				Arrays.asList(new Equals<Long>("id", logistica.getPedidoLogisticaId())));

		
		PedidoLogistica pedidoLogistica = this.findById(logistica.getPedidoLogisticaId());
		
		if (CollectionUtils.isNotEmpty(logistica.getTransporteTerrestre())) {
			logistica.getTransporteTerrestre().stream().forEach(t->t.setPedidoLogistica(pedidoLogistica));
			transporteTerrestreService.saveAll(logistica.getTransporteTerrestre());
		}

		if (CollectionUtils.isNotEmpty(logistica.getAereos())) {
			logistica.getAereos().stream().forEach(t->t.setPedidoLogistica(pedidoLogistica));
			arquivoVoucherLogisticaService.saveAll(logistica.getAereos());
		}

		if (CollectionUtils.isNotEmpty(logistica.getHospedagens())) {
			logistica.getHospedagens().stream().forEach(t->t.setPedidoLogistica(pedidoLogistica));
			arquivoVoucherLogisticaService.saveAll(logistica.getHospedagens());
		}
	}
	
	@CreateLog
	@Override
	public void finalizarLogistica(LogisticaDTO logistica) {
		this.salvar(logistica, StatusPedidosLogistica.FECHADO);
		tarefaFeign.encerrarTarefa(logistica.getTarefaId(), false);
	}


	@Override
	public void atribuirTarefa(Long tarefaId) {
		TarefaDTO tarefa = tarefaFeign.obterTarefa(tarefaId);

		if (!verificarSeUsuarioLogadoPodeAssumirTarefa(tarefa)) {
			throw new BusinessException("tarefa.logistica.indisponivel");
		}
		
		tarefa.getTipo().getConfiguracao().atribuirTarefa()
			.comTarefa(tarefa)
			.comUsuarioLogado()
			.apply();
		
	}

	/**
	 * Verifica se o usuário logado pode receber a atribuíção da tarefa indicada.
	 * 
	 * @param tarefa TarefaDTO que será atribuída.
	 * @return TRUE, se for possível, FALSE caso não.
	 */
	private boolean verificarSeUsuarioLogadoPodeAssumirTarefa(TarefaDTO tarefa) {
		boolean tarefaSemAtribuicao = tarefa.getUsuarioResponsavel() == null;
		boolean tarefaAberta = StatusTarefa.ABERTA.equals(tarefa.getStatus());

		if (tarefaSemAtribuicao || tarefaAberta) {
			return true;
		}
		return validarSeUsuarioResponsavelPelaTarefa(tarefa);
	}

	/**
	 * Verifica se usuário já é o responsável pela tarefa.
	 * 
	 * @param tarefa
	 * @return TRUE, se já é o responsável, FALSE caso contrário.
	 */
	private boolean validarSeUsuarioResponsavelPelaTarefa(TarefaDTO tarefa) {
		Usuario usuarioLogado = usuarioService.obterUsuarioLogado();
		return usuarioLogado.equals(tarefa.getUsuarioResponsavel());
	}

	@Override
	public JsonViewPage<TarefaDTO> listarTarefasPedidoLogistica(PageRequest pageRequest) {

		return TiposTarefa.LOGISTICA.getConfiguracao().listarTarefa()
					.comStatus(Arrays.asList(StatusTarefa.ABERTA, StatusTarefa.ATRIBUIDA))
					.comPaginacao(pageRequest)
					.paraTodosUsuarios()
					.apply();
	}

	@Override
	public void cancelarPedidoLogistica(Long idPedidoColeta) {
		List<PedidoLogistica> pedidosLogistica = pedidoLogisticaRepositorio.findByPedidoColetaId(idPedidoColeta);		
		pedidosLogistica.forEach(pedidoLogistica -> {
			if (!StatusPedidosLogistica.FECHADO.getId().equals(pedidoLogistica.getStatus().getId())) {
				pedidoLogistica.setStatus(new StatusPedidoLogistica(StatusPedidoLogistica.CANCELADO));
				pedidoLogisticaRepositorio.save(pedidoLogistica);
				
				final Paciente paciente = pedidoLogistica.getPedidoWorkup() != null ?
						pedidoLogistica.getPedidoWorkup().getSolicitacao().getMatch().getBusca().getPaciente() :
						pedidoLogistica.getPedidoColeta().getSolicitacao().getMatch().getBusca().getPaciente();
				cancelarTarefasPorLogisticaParaPedidoColeta(pedidoLogistica, paciente);
				
				pedidoTransporteService.cancelarPedidoTransporte(pedidoLogistica.getId());
				
			}
		});
	}

	/**
	 * Cancela as tarefas de logística associadas ao pedido de coleta.
	 * 
	 * @param pedidoLogistica pedido de logística.
	 * @param paciente paciente associado a tarefa.
	 */
	private void cancelarTarefasPorLogisticaParaPedidoColeta(PedidoLogistica pedidoLogistica, Paciente paciente) {
		TiposTarefa.INFORMAR_LOGISTICA_DOADOR_WORKUP.getConfiguracao().fecharTarefa()
			.comObjetoRelacionado(pedidoLogistica.getId())
			.comRmr(paciente.getRmr())
			.apply();
		
		TiposTarefa.INFORMAR_DOCUMENTACAO_MATERIAL_AEREO.getConfiguracao().fecharTarefa()
			.comObjetoRelacionado(pedidoLogistica.getId())
			.comRmr(paciente.getRmr())
			.apply();
		
		TiposTarefa.PEDIDO_LOGISTICA_DOADOR_COLETA.getConfiguracao().fecharTarefa()
			.comObjetoRelacionado(pedidoLogistica.getId())
			.comRmr(paciente.getRmr())
			.apply();
	}

	/**
	 * @param tipoPedidoLogistica
	 */
	@Override
	public void atualizarEvento(Long idPedidoLogistica, TipoPedidoLogistica tipoPedidoLogistica) {
		UpdateSet<TipoPedidoLogistica> realizado = new UpdateSet<TipoPedidoLogistica>("tipo", tipoPedidoLogistica);
		Filter<Long> paraPedido = new Equals<Long>("id", idPedidoLogistica);
		super.update(Arrays.asList(realizado), Arrays.asList(paraPedido));

	}

	@Override
	public DetalheMaterialDTO obterPedidoLogisticaMaterial(Long idPedidoLogistica) {
		PedidoLogistica pedidoLogistica = findById(idPedidoLogistica);
		
		DetalheMaterialDTO detalhe = new DetalheMaterialDTO();
		if (pedidoLogistica.getPedidoTransporte() != null) {
			detalhe.setIdPedidoTransporte(pedidoLogistica.getPedidoTransporte().getId());
		}
		
		detalhe.setIdPedidoLogistica(idPedidoLogistica);
		
		final LocalDate dataColeta = obterDataColeta(idPedidoLogistica);
		detalhe.setDataColeta(dataColeta);

		final Doador doador = obterDoadorPorPedidoLogistica(pedidoLogistica);//  pedidoColetaService.obterDoador(pedidoLogistica.getPedidoColeta());
		detalhe.setIdDoador(doador.getId());
		
		//final Paciente paciente = pedidoColetaService.obterPacientePorPedidoColeta(pedidoLogistica.getPedidoColeta());
		//detalhe.setRmr(paciente.getRmr());
		detalhe.setRmr(23908L);

		final Busca busca = buscaService.obterBuscaAtivaPorRmr(23908L);
		final CentroTransplante centroTransplante = busca.getCentroTransplante();
		detalhe.setNomeCentroTransplante(centroTransplante.getNome());
		
		if(TiposDoador.NACIONAL.getId().equals(doador.getIdTipoDoador())){
			final PedidoColeta pedidoColeta = pedidoLogistica.getPedidoColeta();
			final PedidoWorkup pedidoWorkup = 
					pedidoWorkupService.obterPedidoWorkup(pedidoColeta.getSolicitacao().getId(), StatusPedidosWorkup.REALIZADO);
			
			CentroTransplante centroColeta = pedidoColeta.getCentroColeta();
			detalhe.setNomeLocalRetirada(centroColeta.getNome());
			detalhe.setNomeFonteCelula(pedidoWorkup.getFonteCelula().getDescricao());
			detalhe.setNomeDoador(doador instanceof DoadorNacional? ((DoadorNacional) doador).getNome():null);
			detalhe.setDmr(doador instanceof DoadorNacional? ((DoadorNacional) doador).getDmr():null);
			
			if (CollectionUtils.isNotEmpty(centroColeta.getEnderecos())) {
				detalhe.setEnderecoLocalRetirada(centroColeta.getEnderecos().stream().filter(e->e.isRetirada()).findAny().get().toString());
			}
			detalhe.setContatosLocalRetirada(centroColeta.getContatosFormatadosParaExibicao());
		}
		else if(TiposDoador.CORDAO_NACIONAL.getId().equals(doador.getIdTipoDoador())){
			final CordaoNacional cordao = (CordaoNacional) doador;
			final BancoSangueCordao bancoCordao = cordao.getBancoSangueCordao();
			detalhe.setNomeLocalRetirada(bancoCordao.getNome());
			detalhe.setEnderecoLocalRetirada(bancoCordao.getEndereco());
			detalhe.setContatosLocalRetirada(Arrays.asList(bancoCordao.getContato()));
			// FIXME: Faz sentido manter esse nome no enum?
			detalhe.setNomeFonteCelula(FontesCelulas.CORDAO_UMBILICAL.getDescricao());
		}
		else{
//			Prescricao prescricao = prescricaoService.obterPrescricaoPorBusca(busca.getId());
//			if(!prescricao.temArquivoAutorizacao()){
//				throw new ValidationException(
//						"erro.validacao",
//						Arrays.asList(new CampoMensagem(AppUtil.getMensagem(messageSource, "paciente.sem.autorizacao"))));				
//			}
		}
		
		if (CollectionUtils.isNotEmpty(centroTransplante.getEnderecos())) {
			detalhe.setEnderecoCentroTransplante(centroTransplante.getEnderecos().stream().filter(e->e.isEntrega()).findAny().get().toString());
		}
		detalhe.setContatosCentroTransplante(centroTransplante.getContatosFormatadosParaExibicao());

		PedidoTransporte pedidoTransporte = pedidoLogistica.getPedidoTransporte();
		
		if (pedidoTransporte != null) {
			detalhe.setTransportadora(new TransportadoraListaDTO(
				pedidoTransporte.getTransportadora(),
				null //pedidoTransporte.getTransportadora().getNome()
			));
			
			detalhe.setHoraPrevistaRetirada(pedidoTransporte.getHoraPrevistaRetirada());
			detalhe.setDadosVoo(pedidoTransporte.getDadosVoo());
			
			if(pedidoTransporte.getCourier() != null) {
				/*detalhe.setCourier(new CourierDTO(
						pedidoTransporte.getCourier().getId(), 
						pedidoTransporte.getCourier().getNome(), 
						pedidoTransporte.getCourier().getCpf(),
						pedidoTransporte.getCourier().getRg()
						));
						*/				
			}
		}
		detalhe.setRetiradaIdDoador(pedidoLogistica.getIdentificacaLocalInternacional());
		detalhe.setRetiradaHawb(pedidoLogistica.getHawbInternacional());
		detalhe.setIdTipoDoador(doador.getIdTipoDoador());
		detalhe.setNomeCourier(pedidoLogistica.getNomeCourier());
		detalhe.setPassaporteCourier(pedidoLogistica.getPassaporteCourier());
		detalhe.setDataEmbarque(pedidoLogistica.getDataEmbarque());
		detalhe.setDataChegada(pedidoLogistica.getDataChegada());
		detalhe.setRetiradaLocal(pedidoLogistica.getLocalRetirada());

		return detalhe;
	}

	/**
	 * Obtém a data da coleta que envolveu a necessidade da logística que 
	 * este método está tratando. 
	 * IMPORTANTE: Para coleta de cordão, ainda não está implementado a escolha
	 * da data de coleta. Para este caso, a data não será exibida.
	 * Será tratado posteriormente.
	 * 
	 * @param idPedidoLogistica ID do pedido de logística.
	 * @return data da coleta do workup, se existir.
	 */
	private LocalDate obterDataColeta(final Long idPedidoLogistica) {
		PedidoWorkup pedidoWorkup = obterWorkup(idPedidoLogistica);
		if(pedidoWorkup != null){
			return pedidoWorkup.getDataColeta();
		}
		
		PedidoColeta pedidoColeta = obterColeta(idPedidoLogistica);
		if(pedidoColeta != null){
			return pedidoColeta.getDataColeta();
		}
		
		throw new IllegalStateException(
				"A data da coleta não foi encontrada. Possivelmente, estamos tratando uma logística de cordão "
				+ "e este cenário (de onde virá a data da coleta) ainda não foi implementado.");
	}

	@Override
	public void salvarInformacoesMaterialInternacional(DetalheMaterialDTO detalhe) {
		final Usuario usuarioLogado = usuarioService.obterUsuarioLogado();
		PedidoLogistica pedidoLogistica = findById(detalhe.getIdPedidoLogistica());
		if(pedidoLogistica == null){
			LOGGER.error("Erro ao agendar logística de material. "
					+ "Não foi possível encontrar o pedido de logística com ID: " + detalhe.getIdPedidoLogistica());
			throw new BusinessException("erro.requisicao");
		}
		
		pedidoLogistica.setUsuarioResponsavel(usuarioLogado);
		pedidoLogistica.setIdentificacaLocalInternacional(detalhe.getRetiradaIdDoador());
		pedidoLogistica.setHawbInternacional(detalhe.getRetiradaHawb());
		pedidoLogistica.setNomeCourier(detalhe.getNomeCourier());
		pedidoLogistica.setPassaporteCourier(detalhe.getPassaporteCourier());
		pedidoLogistica.setDataEmbarque(detalhe.getDataEmbarque());
		pedidoLogistica.setDataChegada(detalhe.getDataChegada());
		pedidoLogistica.setLocalRetirada(detalhe.getRetiradaLocal());

		save(pedidoLogistica);
	}
	
	@Override
	public void finalizarLogisticaMaterialInternacional(Long idPedidoLogistica) {
		final Usuario usuarioLogado = usuarioService.obterUsuarioLogado();
		PedidoLogistica pedidoLogistica = findById(idPedidoLogistica);
		Paciente paciente = pacienteService.obterPacientePorPedidoLogistica(pedidoLogistica);
		
		PedidoTransporte pedidoTransporte = new PedidoTransporte();
		pedidoTransporte.setPedidoLogistica(pedidoLogistica);
		pedidoTransporte = pedidoTransporteService.salvar(pedidoTransporte);
		
		pedidoLogistica.setPedidoTransporte(pedidoTransporte);
		save(pedidoLogistica);
		
		TiposTarefa.PEDIDO_LOGISTICA_MATERIAL_INTERNACIONAL.getConfiguracao()
			.fecharTarefa()
			.comRmr(paciente.getRmr())
			.comUsuario(usuarioLogado)
			.comStatus(Arrays.asList(StatusTarefa.ATRIBUIDA))
			.comObjetoRelacionado(pedidoLogistica.getId())
			.apply();
		
		TiposTarefa.RETIRADA_MATERIAL_INTERNACIONAL.getConfiguracao()
			.criarTarefa()
			.comRmr(paciente.getRmr())
			.comObjetoRelacionado(pedidoTransporte.getId())
			.comStatus(StatusTarefa.ABERTA)
			.apply();
	}
	
	@Override
	public void finalizarLogisticaMaterialNacional(Long idPedidoLogistica, DetalheMaterialDTO detalhe) {
		
		PedidoTransporte pedidoTransporte = salvarPedidoLogisticaComTransporte(idPedidoLogistica, detalhe);
		PedidoLogistica pedidoLogistica = pedidoTransporte.getPedidoLogistica();

		final Usuario usuarioLogado = usuarioService.obterUsuarioLogado();
		
		
		TiposTarefa.INFORMAR_DOCUMENTACAO_MATERIAL_AEREO.getConfiguracao()
			.fecharTarefa()
			.comRmr(detalhe.getRmr())
			.comUsuario(usuarioLogado)
			.comStatus(Arrays.asList(StatusTarefa.ATRIBUIDA))
			.comObjetoRelacionado(pedidoLogistica.getId())
			.apply();
		
		
		TiposTarefa.PEDIDO_TRANSPORTE.getConfiguracao()
			.criarTarefa()
			.comRmr(detalhe.getRmr())
			.comObjetoRelacionado(pedidoTransporte.getId())
			.comParceiro(pedidoTransporte.getTransportadora())
			.comStatus(StatusTarefa.ABERTA)
			.apply();
	}
	
	
	@Override
	public void salvarLogisticaMaterialNacional(Long idPedidoLogistica, DetalheMaterialDTO detalhe) {
		this.salvarPedidoLogisticaComTransporte(idPedidoLogistica, detalhe);
	}
	
	
	private PedidoTransporte salvarPedidoLogisticaComTransporte(Long idPedidoLogistica, DetalheMaterialDTO detalhe) {
		PedidoLogistica pedidoLogistica = obterPedidoLogisticaPorId(idPedidoLogistica);
		
		pedidoLogistica.setDataAtualizacao(LocalDateTime.now());
		pedidoLogistica.setUsuarioResponsavel(usuarioService.obterUsuarioLogado());		
		
		if (pedidoLogistica.getPedidoTransporte() == null) {		
			PedidoTransporte pedidoTransporte  = pedidoTransporteService.criarPedidoTransporte(pedidoLogistica, detalhe);
			pedidoLogistica.setPedidoTransporte(pedidoTransporte);
		}
		else {
			pedidoTransporteService.atualizaPedidoTransporte(pedidoLogistica.getPedidoTransporte(), detalhe);
		}
				
		save(pedidoLogistica);
		
		return pedidoLogistica.getPedidoTransporte();
	}
	
	
	

	private PedidoLogistica obterPedidoLogisticaPorId(Long id) {
		return pedidoLogisticaRepositorio.findById(id).orElseThrow(() -> new BusinessException(""));
	}
	
	@Override
	protected List<CampoMensagem> validateEntity(PedidoLogistica pedidoLogistica){
		List<CampoMensagem> mensagens = super.validateEntity(pedidoLogistica);
		
		if (OrigemLogistica.INTERNACIONAL.equals(pedidoLogistica.getOrigem()) 
			&& pedidoLogistica.getStatus().getId().equals(StatusPedidoLogistica.FECHADO)){
			
			if(StringUtils.isEmpty(pedidoLogistica.getLocalRetirada())){
				mensagens.add(new CampoMensagem("retirada.local",
						AppUtil.getMensagem(messageSource, "erro.pedido.logistica.material.internacional.local")));
			}
		}
		return mensagens;
	}
	
	@Override
	public Page<PedidoLogistica> listarLogisticaTransporteEmAndamento(PageRequest pageRequest) {
		return pedidoLogisticaRepositorio.listarPedidosLogisticaEmAndamento(OrigemLogistica.NACIONAL, pageRequest);
	}
	
	@Override
	public Page<PedidoLogistica> listarLogisticaInternacionalTransporteEmAndamento(PageRequest pageRequest) {
		return pedidoLogisticaRepositorio.listarPedidosLogisticaEmAndamento(OrigemLogistica.INTERNACIONAL, pageRequest);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PedidoLogistica obterPedidoLogisticaPeloMatch(Match match) {
		if (doadorMedula(match.getDoador().getTipoDoador())) {
			return pedidoLogisticaRepositorio.obterPedidoLogisticaComPedidoWorkupPorIdMatch(match.getId());
		}
		else {
			return pedidoLogisticaRepositorio.obterPedidoLogisticaComPedidoColetaPorIdMatch(match.getId());
		}
	}
	
	private Boolean doadorMedula(TiposDoador tipoDoador) {
		return TiposDoador.NACIONAL.getId().equals(tipoDoador.getId()) || 
				TiposDoador.INTERNACIONAL.getId().equals(tipoDoador.getId());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(readOnly = true)
	public File obterDocumento(Long idPedidoLogistica, String codigoRelatorio, boolean docxExtensaoRelatorio) {
		PedidoLogistica pedidoLogistica = findById(idPedidoLogistica);
		Relatorio relatorio = relatorioService.obterRelatorioPorCodigo(codigoRelatorio);

		HtmlReportGenerator htmlReportGenerator = criarReportGeneratorPorCodigoRelatorio(pedidoLogistica, codigoRelatorio);
		
		if(docxExtensaoRelatorio) {
			return htmlReportGenerator.gerarDocx(relatorio);		
		}else {
			return htmlReportGenerator.gerarPdf(relatorio);		
		}
	}
	
	@Transactional(readOnly = true)
	private HtmlReportGenerator criarReportGeneratorPorCodigoRelatorio(PedidoLogistica pedidoLogistica, String codigoRelatorio) {
		
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' yyyy", ApplicationConfiguration.BRASIL_LOCALE);
		DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd'/'MM'/'yyyy", ApplicationConfiguration.BRASIL_LOCALE);
		
		Paciente paciente = null;
		Doador doador = null;
		CentroTransplante centroTransplante = null;
		EnderecoContatoCentroTransplante enderecoCentroTransplante = null;
		if (pedidoLogistica.getPedidoWorkup() != null) {
			paciente = pedidoLogistica.getPedidoWorkup().getSolicitacao().getMatch().getBusca().getPaciente();
			doador = pedidoLogistica.getPedidoWorkup().getSolicitacao().getMatch().getDoador();
			centroTransplante = pedidoLogistica.getPedidoWorkup().getSolicitacao().getMatch().getBusca().getCentroTransplante();
			
			List<EnderecoContatoCentroTransplante> enderecos = pedidoLogistica.getPedidoWorkup().getSolicitacao().getMatch().getBusca().getCentroTransplante().getEnderecos();
			enderecoCentroTransplante = enderecos.stream().filter(endereco -> endereco.isEntrega()).findAny().orElse(null);
		}
		else if (pedidoLogistica.getPedidoColeta() != null) {
			if (pedidoLogistica.getPedidoColeta().getPedidoWorkup() != null) {
				paciente = pedidoLogistica.getPedidoColeta().getPedidoWorkup().getSolicitacao().getMatch().getBusca().getPaciente();
				doador = pedidoLogistica.getPedidoColeta().getPedidoWorkup().getSolicitacao().getMatch().getDoador();
				centroTransplante = pedidoLogistica.getPedidoColeta().getPedidoWorkup().getSolicitacao().getMatch().getBusca().getCentroTransplante();
				
				List<EnderecoContatoCentroTransplante> enderecos = pedidoLogistica.getPedidoColeta().getPedidoWorkup().getSolicitacao().getMatch().getBusca().getCentroTransplante().getEnderecos();
				enderecoCentroTransplante = enderecos.stream().filter(endereco -> endereco.isEntrega()).findAny().orElse(null);
			}
			else {
				paciente = pedidoLogistica.getPedidoColeta().getSolicitacao().getMatch().getBusca().getPaciente();
				doador = pedidoLogistica.getPedidoColeta().getSolicitacao().getMatch().getDoador();
				centroTransplante = pedidoLogistica.getPedidoColeta().getSolicitacao().getMatch().getBusca().getCentroTransplante();
			}
		}
		
		if (centroTransplante != null) {
			List<EnderecoContatoCentroTransplante> enderecos = centroTransplante.getEnderecos();
			enderecoCentroTransplante = enderecos.stream().filter(endereco -> endereco.isEntrega()).findAny().orElse(null);
		}
		
		String identificadorDoador = "";
		if (TiposDoador.NACIONAL.equals(doador.getTipoDoador())) {
			identificadorDoador = ((DoadorNacional) doador).getDmr()+"";
		}
		else if (TiposDoador.INTERNACIONAL.equals(doador.getTipoDoador())) {
			identificadorDoador = ((DoadorInternacional) doador).getIdRegistro();
		}
		else if (TiposDoador.CORDAO_NACIONAL.equals(doador.getTipoDoador())) {
			identificadorDoador = ((CordaoNacional) doador).getIdBancoSangueCordao();
		}
		else if (TiposDoador.CORDAO_INTERNACIONAL.equals(doador.getTipoDoador())) {
			identificadorDoador = ((CordaoInternacional) doador).getIdRegistro();
		}
		
		HtmlReportGenerator reportGenerator = null; 
		if ("Doc001-Carta Solicitação Anvisa".equals(codigoRelatorio) ) {
			reportGenerator = new HtmlReportGenerator()
				.comParametro(ParametrosRelatorios.DATA_ATUAL_POR_EXTENSO, formato.format(LocalDate.now()))
				.comParametro(ParametrosRelatorios.PRESIDENTE_ANVISA, constanteRelatorioService.obterValorConstante(ParametrosRelatorios.PRESIDENTE_ANVISA.toString()))
				.comParametro(ParametrosRelatorios.IDENTIFICADOR_DOADOR_INTERNACIONAL, identificadorDoador)
				.comParametro(ParametrosRelatorios.LOCAL_RETIRADA, pedidoLogistica.getLocalRetirada())
				.comParametro(ParametrosRelatorios.INICIAIS_NOME_PACIENTE, paciente.nomeAbreviado())
				.comParametro(ParametrosRelatorios.RMR, String.valueOf(paciente.getRmr()))
				.comParametro(ParametrosRelatorios.DATA_COLETA_EXTENSO, pedidoLogistica.getPedidoColeta().getDataColeta() != null ? 
						formato.format(pedidoLogistica.getPedidoColeta().getDataColeta()) : null)
				.comParametro(ParametrosRelatorios.DATA_CHEGADA_EXTENSO, pedidoLogistica.getDataChegada() != null ? 
						formato.format(pedidoLogistica.getDataChegada()) : null)	
				.comParametro(ParametrosRelatorios.NOME_CENTRO_TRANSPLANTE, centroTransplante.getNome() )
				.comParametro(ParametrosRelatorios.IDENTIFICADOR_DOADOR_LOGISTICA, pedidoLogistica.getIdentificacaLocalInternacional() )
				
				/* ENDEREÇO DE ENTREGA */
				.comParametro(ParametrosRelatorios.MUNICIPIO_ENDERECO_LOCAL_ENTREGA, enderecoCentroTransplante != null && enderecoCentroTransplante.getMunicipio() != null ? 
						enderecoCentroTransplante.getMunicipio().getDescricao(): null)
				.comParametro(ParametrosRelatorios.CEP_ENDERECO_LOCAL_ENTREGA, enderecoCentroTransplante != null && enderecoCentroTransplante.getCep() != null ?
						enderecoCentroTransplante.getCep(): null)
				.comParametro(ParametrosRelatorios.PAIS_ENDERECO_LOCAL_ENTREGA, enderecoCentroTransplante != null && enderecoCentroTransplante.getPais().getNome() != null ?
						enderecoCentroTransplante.getPais().getNome(): null);
		}
		else if ("Doc002-Declaração Importação Anvisa".equals(codigoRelatorio) ) {
			reportGenerator = new HtmlReportGenerator()
				.comParametro(ParametrosRelatorios.INICIAIS_NOME_PACIENTE, paciente.nomeAbreviado())
				.comParametro(ParametrosRelatorios.DIAGNOSTICO_PACIENTE, paciente.getDiagnostico().getCid().getDescricao())
				.comParametro(ParametrosRelatorios.RMR, String.valueOf(paciente.getRmr()))
				.comParametro(ParametrosRelatorios.PRODUTO, (pedidoLogistica.getPedidoColeta().getPedidoWorkup() != null ? 
						pedidoLogistica.getPedidoColeta().getPedidoWorkup().getFonteCelula().getDescricao() : null))
				.comParametro(ParametrosRelatorios.IDENTIFICADOR_DOADOR_INTERNACIONAL, identificadorDoador)
				.comParametro(ParametrosRelatorios.NOME_CENTRO_TRANSPLANTE, centroTransplante.getNome() )
				.comParametro(ParametrosRelatorios.DATA_ATUAL_POR_EXTENSO, formato.format(LocalDate.now()));
		}
		else if ("Carta Courier REDOME".equals(codigoRelatorio)) {
			
			String marcacaoCelulaTroncoMO = null;
			String marcacaoSanguePeriferico = null;
			
			if(pedidoLogistica.getPedidoColeta().getPedidoWorkup() != null) {
				marcacaoCelulaTroncoMO = FontesCelulas.MEDULA_OSSEA.getSigla().equals(pedidoLogistica
						.getPedidoColeta().getPedidoWorkup().getFonteCelula().getSigla()) ? "X" : "&nbsp;";

				marcacaoSanguePeriferico = FontesCelulas.SANGUE_PERIFERICO.getSigla().equals(pedidoLogistica
						.getPedidoColeta().getPedidoWorkup().getFonteCelula().getSigla()) ? "X" : "&nbsp;";
			}
			
			reportGenerator = new HtmlReportGenerator()
				.comParametro(ParametrosRelatorios.DATA_ATUAL_POR_EXTENSO, formato.format(LocalDate.now()))
				.comParametro(ParametrosRelatorios.NOME_COURIER_INTERNACIONAL, pedidoLogistica.getNomeCourier())
				.comParametro(ParametrosRelatorios.PRODUTO, (pedidoLogistica.getPedidoColeta().getPedidoWorkup() != null ? 
						pedidoLogistica.getPedidoColeta().getPedidoWorkup().getFonteCelula().getDescricao() : null))
				.comParametro(ParametrosRelatorios.LOCAL_RETIRADA, pedidoLogistica.getLocalRetirada())
				.comParametro(ParametrosRelatorios.NOME_CENTRO_TRANSPLANTE, centroTransplante.getNome())
				.comParametro(ParametrosRelatorios.MARCACAO_CELULA_TRONCO_SANGUE_PERIFERICO, marcacaoSanguePeriferico)
				.comParametro(ParametrosRelatorios.MARCACAO_CELULA_TRONCO_MEDULA_OSSEA, marcacaoCelulaTroncoMO);	
			
		}
		else if ("Carta Courier REDOME Inglês".equals(codigoRelatorio)) {
				
			reportGenerator = new HtmlReportGenerator()
			    .comParametro(ParametrosRelatorios.NOME_COURIER_INTERNACIONAL, pedidoLogistica.getNomeCourier())
				.comParametro(ParametrosRelatorios.LOCAL_RETIRADA, pedidoLogistica.getLocalRetirada())
				.comParametro(ParametrosRelatorios.NOME_CENTRO_TRANSPLANTE, centroTransplante.getNome());
		}
		else if ("Doc004-Declaração ao Dep. de Cargas".equals(codigoRelatorio)) {
			
			reportGenerator = new HtmlReportGenerator()
			    .comParametro(ParametrosRelatorios.NOME_CENTRO_TRANSPLANTE, centroTransplante.getNome())
			    
				/* ENDEREÇO DE ENTREGA */
			    .comParametro(ParametrosRelatorios.LOGRADOURO_ENDERECO_LOCAL_ENTREGA, enderecoCentroTransplante.getNomeLogradouro())
				.comParametro(ParametrosRelatorios.NUMERO_ENDERECO_LOCAL_ENTREGA, enderecoCentroTransplante.getNumero())
				.comParametro(ParametrosRelatorios.COMPLEMENTO_ENDERECO_LOCAL_ENTREGA, enderecoCentroTransplante.getComplemento())
				.comParametro(ParametrosRelatorios.BAIRRO_ENDERECO_LOCAL_ENTREGA, enderecoCentroTransplante.getBairro())
				.comParametro(ParametrosRelatorios.MUNICIPIO_ENDERECO_LOCAL_ENTREGA, enderecoCentroTransplante.getMunicipio() != null ? 
						enderecoCentroTransplante.getMunicipio().getDescricao() : null)
				.comParametro(ParametrosRelatorios.UF_ENDERECO_LOCAL_ENTREGA, enderecoCentroTransplante.getMunicipio() != null ?
						enderecoCentroTransplante.getMunicipio().getUf().getSigla() : null)
				.comParametro(ParametrosRelatorios.PAIS_ENDERECO_LOCAL_ENTREGA, enderecoCentroTransplante.getPais().getNome())
				.comParametro(ParametrosRelatorios.CEP_ENDERECO_LOCAL_ENTREGA, enderecoCentroTransplante.getCep())

				/* ENDEREÇO DE RETIRADA */
				.comParametro(ParametrosRelatorios.LOCAL_RETIRADA, pedidoLogistica.getLocalRetirada().replaceAll("\\n", "<br />"));
			
		}
		else if ("Doc005-Anexo XXIX".equals(codigoRelatorio)) {
		
			reportGenerator = new HtmlReportGenerator()
				.comParametro(ParametrosRelatorios.RMR, String.valueOf(paciente.getRmr()))
				.comParametro(ParametrosRelatorios.IDENTIFICADOR_DOADOR_INTERNACIONAL, identificadorDoador)
				.comParametro(ParametrosRelatorios.CARTAO_SUS_PACIENTE, paciente.getCns())
				.comParametro(ParametrosRelatorios.DATA_ATUAL_POR_EXTENSO, formato.format(LocalDate.now()))
				.comParametro(ParametrosRelatorios.DATA_COLETA, formatoData.format(pedidoLogistica.getPedidoColeta().getDataColeta()));
		}
		else if ("Checklist English REDOME".equals(codigoRelatorio)) {

			reportGenerator = new HtmlReportGenerator()
					.comParametro(ParametrosRelatorios.NOME_COURIER_INTERNACIONAL, pedidoLogistica.getNomeCourier())
					.comParametro(ParametrosRelatorios.PASSAPORTE_COURIER_INTERNACIONAL, pedidoLogistica.getPassaporteCourier())
					.comParametro(ParametrosRelatorios.RMR, String.valueOf(paciente.getRmr()))
					.comParametro(ParametrosRelatorios.IDENTIFICADOR_DOADOR_INTERNACIONAL, identificadorDoador)
					.comParametro(ParametrosRelatorios.DATA_COLETA, pedidoLogistica.getPedidoColeta().getDataColeta() != null ?
							formatoData.format(pedidoLogistica.getPedidoColeta().getDataColeta()) : null)
					.comParametro(ParametrosRelatorios.NOME_CENTRO_TRANSPLANTE, centroTransplante.getNome())
					.comParametro(ParametrosRelatorios.MEDICO_PRESCRICAO, pedidoLogistica.getPedidoColeta().getPedidoWorkup() != null ?
							pedidoLogistica.getPedidoColeta().getPedidoWorkup().getSolicitacao().getPrescricao().getMedicoResponsavel().getNome() : null)
					
					/* ENDEREÇO DE ENTREGA */
					.comParametro(ParametrosRelatorios.LOGRADOURO_ENDERECO_LOCAL_ENTREGA, enderecoCentroTransplante.getNomeLogradouro())
					.comParametro(ParametrosRelatorios.NUMERO_ENDERECO_LOCAL_ENTREGA, enderecoCentroTransplante.getNumero())
					.comParametro(ParametrosRelatorios.COMPLEMENTO_ENDERECO_LOCAL_ENTREGA, enderecoCentroTransplante.getComplemento())
					.comParametro(ParametrosRelatorios.BAIRRO_ENDERECO_LOCAL_ENTREGA, enderecoCentroTransplante.getBairro())
					.comParametro(ParametrosRelatorios.MUNICIPIO_ENDERECO_LOCAL_ENTREGA, enderecoCentroTransplante.getMunicipio() != null ? 
							enderecoCentroTransplante.getMunicipio().getDescricao(): null)
					.comParametro(ParametrosRelatorios.UF_ENDERECO_LOCAL_ENTREGA, enderecoCentroTransplante.getMunicipio() != null ?
							enderecoCentroTransplante.getMunicipio().getUf().getSigla() : null)
					.comParametro(ParametrosRelatorios.PAIS_ENDERECO_LOCAL_ENTREGA, enderecoCentroTransplante.getPais().getNome())
					.comParametro(ParametrosRelatorios.CEP_ENDERECO_LOCAL_ENTREGA, enderecoCentroTransplante.getCep());
			
		}
		else if ("Doc001-Cordão".equals(codigoRelatorio)) {

			reportGenerator = new HtmlReportGenerator()
					.comParametro(ParametrosRelatorios.DATA_EMBARQUE_EXTENSO, pedidoLogistica.getDataEmbarque() != null ? 
							formato.format(pedidoLogistica.getDataEmbarque()) : null)
					.comParametro(ParametrosRelatorios.DATA_ATUAL_POR_EXTENSO, formato.format(LocalDate.now()))
					.comParametro(ParametrosRelatorios.PRESIDENTE_ANVISA, constanteRelatorioService.obterValorConstante(ParametrosRelatorios.PRESIDENTE_ANVISA.toString()))
					.comParametro(ParametrosRelatorios.IDENTIFICADOR_DOADOR_INTERNACIONAL, identificadorDoador)
					.comParametro(ParametrosRelatorios.IDENTIFICADOR_DOADOR_LOGISTICA, pedidoLogistica.getIdentificacaLocalInternacional())
					.comParametro(ParametrosRelatorios.INICIAIS_NOME_PACIENTE, paciente.nomeAbreviado())
					.comParametro(ParametrosRelatorios.RMR, String.valueOf(paciente.getRmr()))
					.comParametro(ParametrosRelatorios.NOME_CENTRO_TRANSPLANTE, centroTransplante.getNome() )
					
					/* ENDEREÇO DE ENTREGA */
					.comParametro(ParametrosRelatorios.MUNICIPIO_ENDERECO_LOCAL_ENTREGA, enderecoCentroTransplante.getMunicipio() != null ? 
							enderecoCentroTransplante.getMunicipio().getDescricao(): null)
					.comParametro(ParametrosRelatorios.CEP_ENDERECO_LOCAL_ENTREGA, enderecoCentroTransplante.getCep() )
					.comParametro(ParametrosRelatorios.PAIS_ENDERECO_LOCAL_ENTREGA, enderecoCentroTransplante.getPais().getNome());
					
		}
		else if ("Doc002-Cordão".equals(codigoRelatorio)) {

			reportGenerator = new HtmlReportGenerator()
					.comParametro(ParametrosRelatorios.INICIAIS_NOME_PACIENTE, paciente.nomeAbreviado())
					.comParametro(ParametrosRelatorios.DIAGNOSTICO_PACIENTE, paciente.getDiagnostico().getCid().getDescricao())
					.comParametro(ParametrosRelatorios.RMR, String.valueOf(paciente.getRmr()))
					.comParametro(ParametrosRelatorios.IDENTIFICADOR_DOADOR_INTERNACIONAL, identificadorDoador)
					.comParametro(ParametrosRelatorios.IDENTIFICADOR_DOADOR_LOGISTICA, pedidoLogistica.getIdentificacaLocalInternacional())
					.comParametro(ParametrosRelatorios.NOME_CENTRO_TRANSPLANTE, centroTransplante.getNome() )
					.comParametro(ParametrosRelatorios.DATA_ATUAL_POR_EXTENSO, formato.format(LocalDate.now()))
					
					/* ENDEREÇO DE ENTREGA */
					.comParametro(ParametrosRelatorios.MUNICIPIO_ENDERECO_LOCAL_ENTREGA, enderecoCentroTransplante.getMunicipio() != null ? 
							enderecoCentroTransplante.getMunicipio().getDescricao(): null)
					.comParametro(ParametrosRelatorios.PAIS_ENDERECO_LOCAL_ENTREGA, enderecoCentroTransplante.getPais().getNome())
					.comParametro(ParametrosRelatorios.CEP_ENDERECO_LOCAL_ENTREGA, enderecoCentroTransplante.getCep());
		}

			
		return reportGenerator;
	}

	@Override
	public JsonViewPage<TarefaDTO> listarTarefasPedidoLogisticaInternacional(PageRequest pageRequest) {
		
		return TiposTarefa.PEDIDO_LOGISTICA_MATERIAL_INTERNACIONAL.getConfiguracao().listarTarefa()
				.comStatus(Arrays.asList(StatusTarefa.ABERTA, StatusTarefa.ATRIBUIDA))
				.comPaginacao(pageRequest)
				.paraTodosUsuarios()
				.apply();
	}

	@Override
	public File obterAutorizacaoPaciente(Long idPedidoLogistica) {
		final PedidoLogistica pedidoLogistica = findById(idPedidoLogistica);
		final Prescricao prescricao = 
				prescricaoService.obterPrescricao(pedidoLogistica.getPedidoColeta().getSolicitacao());
		return prescricaoService.obterArquivoAutorizacaoPaciente(prescricao);
	}
	
	@Override
	public void alterarLogisticaMaterialAereo(Long idPedidoLogistica, Long tipoAlteracao, MultipartFile cartaCnt, String descricao, LocalDate data, Long rmr) {
		PedidoLogistica pedidoLogistica = pedidoLogisticaRepositorio.findById(idPedidoLogistica).get();
		PedidoTransporte pedidoTransporte = pedidoLogistica.getPedidoTransporte();
		PedidoColeta pedidoColeta = pedidoLogistica.getPedidoColeta();

		if(TipoAlteracaoPedidoLogistica.DATA.getId().equals(tipoAlteracao) 
			|| TipoAlteracaoPedidoLogistica.AMBOS.getId().equals(tipoAlteracao)) {
			pedidoColeta.setDataColeta(data);
			pedidoColetaService.save(pedidoColeta);
		}
		if(TipoAlteracaoPedidoLogistica.ARQUIVO_CNT.getId().equals(tipoAlteracao) 
				|| TipoAlteracaoPedidoLogistica.AMBOS.getId().equals(tipoAlteracao)) {
			pedidoTransporteService.salvarPedidoComArquivo(pedidoTransporte, Arrays.asList(cartaCnt));
		}
		
		
		TiposTarefa.PEDIDO_TRANSPORTE.getConfiguracao()
		.fecharTarefa()
		.comStatus(Arrays.asList(StatusTarefa.ATRIBUIDA, StatusTarefa.ABERTA))
		.comObjetoRelacionado(pedidoLogistica.getId())
		.apply();
		
		TiposTarefa.PEDIDO_TRANSPORTE.getConfiguracao()
		.criarTarefa()
		.comRmr(rmr)
		.comObjetoRelacionado(pedidoTransporte.getId())
		.comParceiro(pedidoTransporte.getTransportadora())
		.comStatus(StatusTarefa.ABERTA)
		.apply();
		
		
	}	

	/**
	 * @param doadorService the doadorService to set
	 */
	@Autowired
	public void setDoadorService(IDoadorNacionalService doadorService) {
		this.doadorService = doadorService;
	}

	/**
	 * @param contatoTelefonicoDoadorService the contatoTelefonicoDoadorService to set
	 */
	@Autowired
	public void setContatoTelefonicoDoadorService(IContatoTelefonicoDoadorService contatoTelefonicoDoadorService) {
		this.contatoTelefonicoDoadorService = contatoTelefonicoDoadorService;
	}

	/**
	 * @param usuarioService the usuarioService to set
	 */
	@Autowired
	public void setUsuarioService(IUsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	/**
	 * @param pacienteService the pacienteService to set
	 */
	@Autowired
	public void setPacienteService(IPacienteService pacienteService) {
		this.pacienteService = pacienteService;
	}

	/**
	 * @param enderecoContatoDoadorService the enderecoContatoDoadorService to set
	 */
	@Autowired
	public void setEnderecoContatoDoadorService(IEnderecoContatoDoadorService enderecoContatoDoadorService) {
		this.enderecoContatoDoadorService = enderecoContatoDoadorService;
	}

	/**
	 * @param emailContatoDoadorService the emailContatoDoadorService to set
	 */
	@Autowired
	public void setEmailContatoDoadorService(IEmailContatoDoadorService emailContatoDoadorService) {
		this.emailContatoDoadorService = emailContatoDoadorService;
	}

	/**
	 * @param centroTransplanteService the centroTransplanteService to set
	 */
	@Autowired
	public void setCentroTransplanteService(ICentroTransplanteService centroTransplanteService) {
		this.centroTransplanteService = centroTransplanteService;
	}

	/**
	 * @param configuracaoService the configuracaoService to set
	 */
	@Autowired
	public void setConfiguracaoService(IConfiguracaoService configuracaoService) {
		this.configuracaoService = configuracaoService;
	}

	/**
	 * @param arquivoVoucherLogisticaService the arquivoVoucherLogisticaService to set
	 */
	@Autowired
	public void setArquivoVoucherLogisticaService(IArquivoVoucherLogisticaService arquivoVoucherLogisticaService) {
		this.arquivoVoucherLogisticaService = arquivoVoucherLogisticaService;
	}

	/**
	 * @param transporteTerrestreService the transporteTerrestreService to set
	 */
	@Autowired
	public void setTransporteTerrestreService(ITransporteTerrestreService transporteTerrestreService) {
		this.transporteTerrestreService = transporteTerrestreService;
	}

	/**
	 * @param pedidoTransporteService the pedidoTransporteService to set
	 */
	@Autowired
	public void setPedidoTransporteService(IPedidoTransporteService pedidoTransporteService) {
		this.pedidoTransporteService = pedidoTransporteService;
	}

	/**
	 * @param buscaService the buscaService to set
	 */
	@Autowired
	public void setBuscaService(IBuscaService buscaService) {
		this.buscaService = buscaService;
	}

	/**
	 * @param pedidoColetaService the pedidoColetaService to set
	 */
	@Autowired
	public void setPedidoColetaService(IPedidoColetaService pedidoColetaService) {
		this.pedidoColetaService = pedidoColetaService;
	}

	/**
	 * @param pedidoWorkupService the pedidoWorkupService to set
	 */
	@Autowired
	public void setPedidoWorkupService(IPedidoWorkupService pedidoWorkupService) {
		this.pedidoWorkupService = pedidoWorkupService;
	}

	/**
	 * @param prescricaoService the prescricaoService to set
	 */
	@Autowired
	public void setPrescricaoService(IPrescricaoService prescricaoService) {
		this.prescricaoService = prescricaoService;
	}

	/**
	 * @param relatorioService the relatorioService to set
	 */
	@Autowired
	public void setRelatorioService(IRelatorioService relatorioService) {
		this.relatorioService = relatorioService;
	}

	/**
	 * @param constanteRelatorioService the constanteRelatorioService to set
	 */
	@Autowired
	public void setConstanteRelatorioService(IConstanteRelatorioService constanteRelatorioService) {
		this.constanteRelatorioService = constanteRelatorioService;
	}

	@Override
	public Paciente obterPaciente(PedidoLogistica pedidoLogistica) {
		if (pedidoLogistica.getPedidoWorkup() != null) {
			return pedidoLogistica.getPedidoWorkup().getSolicitacao().getMatch().getBusca().getPaciente();
		}
		return pedidoLogistica.getPedidoColeta().getSolicitacao().getMatch().getBusca().getPaciente();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public String[] obterParametros(PedidoLogistica pedidoLogistica) {
		IDoador doador;
		if (pedidoLogistica.getPedidoWorkup() != null) {
			doador = (IDoador) pedidoLogistica.getPedidoWorkup().getSolicitacao().getMatch().getDoador();
		}
		else {
			doador = (IDoador) pedidoLogistica.getPedidoColeta().getSolicitacao().getMatch().getDoador();
		}
		
		return StringUtils.split(doador.getIdentificacao().toString());
	}

	
	@Override
	public LogEvolucao criarLog(PedidoLogistica pedidoLogistica, TipoLogEvolucao tipoLog, Perfis[] perfisExcluidos) {
		
		if (tipoLog == null || TipoLogEvolucao.INDEFINIDO.equals(tipoLog)) {
			
			if (pedidoLogistica.getPedidoWorkup() != null) {
				tipoLog = TipoLogEvolucao.LOGISTICA_DOADOR_WORKUP_CONFIRMADA_PARA_DOADOR;
			}
			else {
				if ( TiposPedidoLogistica.DOADOR.getId().equals(pedidoLogistica.getTipo().getId())) {
					tipoLog = TipoLogEvolucao.LOGISTICA_DOADOR_COLETA_CONFIRMADA_PARA_DOADOR;
				}
				else {
					tipoLog = TipoLogEvolucao.LOGISTICA_MATERIAL_COLETA_CONFIRMADA_PARA_DOADOR;
				}
			}
		}
		
		
		return super.criarLog(pedidoLogistica, tipoLog, perfisExcluidos);
	}

	@Override
	public void fecharPedidoLogistica(Long idPedidoLogistica) {
		PedidoLogistica pedidoLogistica = obterPedidoLogisticaPorId(idPedidoLogistica);
		pedidoLogistica.setStatus(new StatusPedidoLogistica(StatusPedidosLogistica.FECHADO));
		save(pedidoLogistica);
	}
	
	
	
	
			
}

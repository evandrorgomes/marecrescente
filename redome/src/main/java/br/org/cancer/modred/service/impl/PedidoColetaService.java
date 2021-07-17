package br.org.cancer.modred.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.modred.controller.dto.DadosCentroTransplanteDTO;
import br.org.cancer.modred.exception.BusinessException;
import br.org.cancer.modred.feign.dto.TarefaDTO;
import br.org.cancer.modred.helper.JsonViewPage;
import br.org.cancer.modred.model.CentroTransplante;
import br.org.cancer.modred.model.ContatoTelefonico;
import br.org.cancer.modred.model.Disponibilidade;
import br.org.cancer.modred.model.Doador;
import br.org.cancer.modred.model.IDoador;
import br.org.cancer.modred.model.MotivoCancelamentoColeta;
import br.org.cancer.modred.model.Paciente;
import br.org.cancer.modred.model.PedidoColeta;
import br.org.cancer.modred.model.PedidoTransporte;
import br.org.cancer.modred.model.PedidoWorkup;
import br.org.cancer.modred.model.Solicitacao;
import br.org.cancer.modred.model.StatusPedidoColeta;
import br.org.cancer.modred.model.annotations.log.CreateLog;
import br.org.cancer.modred.model.domain.MotivosCancelamentoColeta;
import br.org.cancer.modred.model.domain.OrdenacaoTarefa;
import br.org.cancer.modred.model.domain.StatusPedidosColeta;
import br.org.cancer.modred.model.domain.StatusTarefa;
import br.org.cancer.modred.model.domain.TipoLogEvolucao;
import br.org.cancer.modred.model.domain.TiposPedidoLogistica;
import br.org.cancer.modred.model.domain.TiposTarefa;
import br.org.cancer.modred.model.security.Usuario;
import br.org.cancer.modred.persistence.IPedidoColetaRepository;
import br.org.cancer.modred.persistence.IRepository;
import br.org.cancer.modred.service.ICentroTransplanteService;
import br.org.cancer.modred.service.IDisponibilidadeService;
import br.org.cancer.modred.service.IPedidoColetaService;
import br.org.cancer.modred.service.IPedidoLogisticaService;
import br.org.cancer.modred.service.IPedidoTransporteService;
import br.org.cancer.modred.service.ISolicitacaoService;
import br.org.cancer.modred.service.IUsuarioService;
import br.org.cancer.modred.service.impl.config.Equals;
import br.org.cancer.modred.service.impl.config.UpdateSet;
import br.org.cancer.modred.service.impl.invocation.AbstractLoggingService;
import br.org.cancer.modred.util.AppUtil;
import br.org.cancer.modred.util.CampoMensagem;

/**
 * Classe de implementação das funcionalidades envolvendo a entidade PedidoColeta.
 * 
 * @author Fillipe Queiroz
 *
 */
@Service
@Transactional
public class PedidoColetaService extends AbstractLoggingService<PedidoColeta, Long> implements IPedidoColetaService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PedidoColetaService.class);

	@Autowired
	private IPedidoColetaRepository pedidoColetaRepository;
		
	private IPedidoLogisticaService pedidoLogisticaService;
				
	private IUsuarioService usuarioService;
	
	private IDisponibilidadeService disponibilidadeService;

	private ISolicitacaoService solicitacaoService;
	
	private ICentroTransplanteService centroTransplanteService;
	
	private IPedidoTransporteService pedidoTransporteService;
	
	@Override
	public IRepository<PedidoColeta, Long> getRepository() {
		return pedidoColetaRepository;
	}
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public PedidoColeta obterPedidoColeta(Long pedidoColetaId) {
		PedidoColeta pedidoColeta = findById(pedidoColetaId); 
		if (pedidoColeta == null) {
			throw new BusinessException("pedido.coleta.erro.nao.encontrado");
		}
		final Disponibilidade ultimaDisponibilidade = recuperarUltimaDisponibilidade(pedidoColetaId);
		pedidoColeta.setUltimaDisponibilidade(ultimaDisponibilidade);
		
		
		return pedidoColeta;
	}
	
	/** 
	 * {@inheritDoc}
	 */
	@CreateLog(TipoLogEvolucao.AGENDAMENTO_COLETA_PARA_DOADOR)
	@Override
	public void agendarPedidoColeta(Long idPedidoColeta, PedidoColeta pedidoColeta) {
		PedidoColeta pedidoColetaRecuperado = findById(idPedidoColeta); 
		if (pedidoColetaRecuperado == null) {
			throw new BusinessException("pedido.coleta.erro.nao.encontrado");
		}
		
		final Paciente paciente = pedidoColetaRepository.obterPaciente(idPedidoColeta);
		
		Doador doador = pedidoColetaRecuperado.getPedidoWorkup() != null ?
				 pedidoColetaRecuperado.getPedidoWorkup().getSolicitacao().getMatch().getDoador() :
				 pedidoColetaRecuperado.getSolicitacao().getMatch().getDoador();
		
		pedidoColetaRecuperado.setNecessitaLogisticaDoador(false);
		if (doador.isNacional() && doador.isMedula()) {		
			
			pedidoColetaRecuperado.setCentroColeta(new CentroTransplante(pedidoColeta.getCentroColeta().getId()));
			pedidoColetaRecuperado.setDataDisponibilidadeDoador(pedidoColeta.getDataDisponibilidadeDoador());
			pedidoColetaRecuperado.setDataLiberacaoDoador(pedidoColeta.getDataLiberacaoDoador());
			pedidoColetaRecuperado.setNecessitaLogisticaDoador(pedidoColeta.getNecessitaLogisticaDoador());			
		}
		
		pedidoColetaRecuperado.setDataColeta(pedidoColeta.getDataColeta());
		pedidoColetaRecuperado.setDataUltimoStatus(LocalDate.now());
		pedidoColetaRecuperado.setStatusPedidoColeta(new StatusPedidoColeta(StatusPedidosColeta.AGENDADO.getId()));		
		pedidoColetaRecuperado.setNecessitaLogisticaMaterial(pedidoColeta.getNecessitaLogisticaMaterial());		
		pedidoColetaRecuperado.setUsuario(usuarioService.obterUsuarioLogado());
		save(pedidoColetaRecuperado);
		
		if (Boolean.TRUE.equals(pedidoColetaRecuperado.getNecessitaLogisticaDoador())) {
			pedidoLogisticaService.criarLogistica(pedidoColetaRecuperado, TiposPedidoLogistica.DOADOR);
		}
		
		if (Boolean.TRUE.equals(pedidoColetaRecuperado.getNecessitaLogisticaMaterial())) {
			pedidoLogisticaService.criarLogistica(pedidoColetaRecuperado, TiposPedidoLogistica.MATERIAL);
		}
		else {
			
//			recebimentoColetaService.registrarRecebimentoColeta(doador.getId(), null);
		}
		
		if (doador.isNacional() ) {
			
			TiposTarefa.PEDIDO_COLETA.getConfiguracao()
				.fecharTarefa()
				.comObjetoRelacionado(pedidoColetaRecuperado.getId())
				.comRmr(paciente.getRmr())
				.comStatus(Arrays.asList(StatusTarefa.ATRIBUIDA))
				.comUsuario(usuarioService.obterUsuarioLogado())
				.apply();
		}
		else if (doador.isInternacional()) {
		
			TiposTarefa.PEDIDO_COLETA_INTERNACIONAL.getConfiguracao()
				.fecharTarefa()
				.comObjetoRelacionado(pedidoColetaRecuperado.getId())
				.comRmr(paciente.getRmr())
				.comStatus(Arrays.asList(StatusTarefa.ATRIBUIDA))
				.comUsuario(usuarioService.obterUsuarioLogado())
				.apply();
		}
		
	}
	
	@Override
	protected List<CampoMensagem> validateEntity(PedidoColeta pedidoColeta) {
		List<CampoMensagem> erros = super.validateEntity(pedidoColeta);
		if (!erros.isEmpty()) {
			return erros;
		}
		
		Doador doador = pedidoColeta.getPedidoWorkup() != null ?
				pedidoColeta.getPedidoWorkup().getSolicitacao().getMatch().getDoador() :
					pedidoColeta.getSolicitacao().getMatch().getDoador();
		
		if (doador.isMedula()) {
			if (pedidoColeta.getDataDisponibilidadeDoador() != null && pedidoColeta.getDataLiberacaoDoador() != null) {
				if (pedidoColeta.getDataDisponibilidadeDoador().isAfter(pedidoColeta.getDataLiberacaoDoador())) {
					CampoMensagem campoMensagem = new CampoMensagem("dataDisponibilidadeDoador", 
							AppUtil.getMensagem(messageSource, "agendar.pedido.data.disponibilidade.nao.pode.ser.maior.data.liberacao.doador"));
					erros.add(campoMensagem);
					return erros;
				}
			}
			
			if (pedidoColeta.getDataDisponibilidadeDoador() != null && pedidoColeta.getDataColeta() != null) {
				if (pedidoColeta.getDataLiberacaoDoador().isBefore(pedidoColeta.getDataColeta())) {
					CampoMensagem campoMensagem = new CampoMensagem("dataDisponibilidadeDoador", 
							AppUtil.getMensagem(messageSource, "agendar.pedido.data.coleta.nao.pode.ser.maior.data.liberacao.doador"));
					erros.add(campoMensagem);
					return erros;
				}			
			}
		}
		
		return erros;		
	}
		
	@Override
	public PedidoColeta obterPedidoColeta(Long solicitacaoId, StatusPedidosColeta status){
		return pedidoColetaRepository.obterPedidoColeta(solicitacaoId, status != null? status.getId():null);
	}

	@Override
	public PedidoColeta obterPedidoColetaPor(Long idDoador) {
		return pedidoColetaRepository.findBySolicitacaoMatchDoadorId(idDoador);
	}
	
	@Override
	public Paciente obterPacientePorPedidoColeta(PedidoColeta pedidoColeta) {
		return pedidoColetaRepository.obterPaciente(pedidoColeta.getId());
	}
	
	@Override
	public Doador obterDoador(PedidoColeta pedidoColeta) {
		return pedidoColetaRepository.obterDoador(pedidoColeta.getId());
	}
	
	@Override
	public CentroTransplante obterCentroColeta(PedidoColeta pedidoColeta) {
		return pedidoColetaRepository.obterCentroColeta(pedidoColeta.getId());
	}
	
	
	private PedidoColeta criarPedido(Solicitacao solicitacao, Usuario usuarioResponsavel) {
		PedidoColeta pedidoColetaParaPersistir = new PedidoColeta();		
		pedidoColetaParaPersistir.setDataCriacao(LocalDateTime.now());
		pedidoColetaParaPersistir.setDataUltimoStatus(LocalDate.now());
		pedidoColetaParaPersistir.setUsuario(usuarioResponsavel);
		pedidoColetaParaPersistir.setStatusPedidoColeta(new StatusPedidoColeta(StatusPedidosColeta.INICIAL));
		pedidoColetaParaPersistir.setSolicitacao(solicitacao);
		
		pedidoColetaParaPersistir = save(pedidoColetaParaPersistir);
		
		criarTarefaPedidoColeta(pedidoColetaParaPersistir,usuarioResponsavel);
		
		return pedidoColetaParaPersistir;
		
	}
	
	private void criarTarefaPedidoColeta(PedidoColeta pedidoColeta, Usuario usuarioResponsavel){
		Long rmr = pedidoColeta.getSolicitacao().getMatch().getBusca().getPaciente().getRmr();
		Long idPedidoColeta = pedidoColeta.getId();
		
		Doador doador = pedidoColeta.getSolicitacao().getMatch().getDoador();
		
		if (doador.isNacional()) {
			TiposTarefa.PEDIDO_COLETA.getConfiguracao().criarTarefa()
			.comObjetoRelacionado(idPedidoColeta )
			.comRmr(rmr)
			.comUsuario(usuarioResponsavel)
			.apply();			
		}
		else{
			TiposTarefa.PEDIDO_COLETA_INTERNACIONAL.getConfiguracao().criarTarefa()
			.comObjetoRelacionado(idPedidoColeta )
			.comRmr(rmr)
			.comUsuario(usuarioResponsavel)
			.apply();		
			
		}
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public PedidoColeta criarPedidoColetaCordao(Solicitacao solicitacao) {
		return criarPedido(solicitacao, null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PedidoColeta criarPedidoColetaMedula(PedidoWorkup pedidoWorkup) {
		PedidoColeta pedidoColetaParaPersistir = criarPedido(pedidoWorkup.getSolicitacao(),	
				pedidoWorkup.getUsuarioResponsavel());
		pedidoColetaParaPersistir.setPedidoWorkup(pedidoWorkup);
		pedidoColetaParaPersistir.setDataColeta(pedidoWorkup.getDataColeta());
		
		return save(pedidoColetaParaPersistir);
	}

	@Override
	public PedidoColeta alterarStatusParaAguardandoCT(Long idPedido) {
		PedidoColeta pedidoColeta = pedidoColetaRepository.findById(idPedido).orElse(null);

		if (pedidoColeta == null) {
			throw new BusinessException("pedido.workup.erro.nao.encontrado");
		}

		pedidoColeta.setStatusPedidoColeta(new StatusPedidoColeta(StatusPedidosColeta.AGUARDANDO_CT));

		pedidoColetaRepository.save(pedidoColeta);
		LOGGER.info("Pedido de Coleta alterado status para aguardando CT.");
		return pedidoColeta;
	}
	
	@Override
	public Disponibilidade obterUltimaDisponibilidade(Long idPedido) {
		List<Disponibilidade> disponibilidades = pedidoColetaRepository.listarDisponibilidadesPorPedido(idPedido);
		return CollectionUtils.isNotEmpty(disponibilidades) ? disponibilidades.get(disponibilidades.size() - 1) : null;
	}
	
	/**
	 * Obtém a última disponibilidade sugerida (pelo centro ou pelo analista) para o pedido de coleta
	 * a ser realizado para o doador.
	 * 
	 * @param idPedido ID do pedido de coleta.
	 * @return última disponibilidade, devidamente preenchida, para a coleta.
	 */
	private Disponibilidade recuperarUltimaDisponibilidade(Long idPedido) {
		Disponibilidade ultimaDisponibilidade = disponibilidadeService.obterUltimaDisponibilidadePedidoColeta(idPedido);		
		return ultimaDisponibilidade;
	}

	@Override
	public void responderDisponibilidadeColeta(Long idPedidoColeta, Disponibilidade disponibilidade) {
		final Paciente paciente = pedidoColetaRepository.obterPaciente(idPedidoColeta);
		
		TiposTarefa.SUGERIR_DATA_COLETA.getConfiguracao().fecharTarefa()
			.comObjetoRelacionado(idPedidoColeta)
			.comRmr(paciente.getRmr())
			.apply();
		
		atualizarStatusPedido(idPedidoColeta, StatusPedidosColeta.CONFIRMADO_CT);
		disponibilidadeService.responderDisponibilidade(disponibilidade);
	}
	
	/**
	 * Responde ao analista a disponibilidade do centro de transplante.
	 * Quando o CT responde as sugestões de data feitas pelo analista de workup,
	 * o pedido de coleta entra em status de Em Análise até ser agendado.
	 * 
	 * @param idPedidoColeta ID do pedido de coleta.
	 * @return pedido de coleta atualizado.
	 */
//	private PedidoColeta alterarStatusParaEmAnalise(Long idPedidoColeta) {
//		PedidoColeta pedidoColeta = pedidoColetaRepository.obterPedidoColetaComStatusAguardandoCT(idPedidoColeta);
//
//		if (pedidoColeta == null) {
//			throw new BusinessException("pedido.coleta.nao.encontrado.para.ct");
//		}
//
//		pedidoColeta.setStatusPedidoColeta(new StatusPedidoColeta(StatusPedidosColeta.EM_ANALISE));
//
//		pedidoColetaRepository.save(pedidoColeta);
//		LOGGER.info("Pedido de Coleta alterado status para aguardando CT.");
//		return pedidoColeta;
//	}
	

	public void atualizarStatusPedido(Long pedidoColetaId, StatusPedidosColeta novoStatus) {
		final PedidoColeta pedidoAtual = findById(pedidoColetaId);
		pedidoAtual.setStatusPedidoColeta(new StatusPedidoColeta(novoStatus.getId()));
		pedidoAtual.setDataUltimoStatus(LocalDate.now());
		save(pedidoAtual);
	}
	
	
	@Override
	public void cancelarPedidoColetaPeloCT(Long idPedidoColeta) {
		PedidoColeta pedidoColeta = pedidoColetaRepository.obterPedidoColetaComStatusAguardandoCT(idPedidoColeta);
		if (pedidoColeta == null) {
			throw new BusinessException("pedido.coleta.nao.encontrado.para.ct");
		}
		
		solicitacaoService.cancelarSolicitacao(pedidoColeta.getSolicitacao().getId(), null, null, null, null);
		
	}
	
	@Override
	public void cancelarPedidoColetaPorSolicitacao(Solicitacao solicitacao, MotivosCancelamentoColeta motivoCancelamento) {
		
		PedidoColeta pedidoColeta = obterPedidoColeta(solicitacao.getId(), null);
		if (pedidoColeta != null) {
			pedidoColeta.setStatusPedidoColeta(new StatusPedidoColeta(StatusPedidosColeta.CANCELADO));
			pedidoColeta.setMotivoCancelamento(new MotivoCancelamentoColeta(motivoCancelamento));
			pedidoColetaRepository.save(pedidoColeta);
			
			cancelarTarefas(pedidoColeta);
			
			pedidoLogisticaService.cancelarPedidoLogistica(pedidoColeta.getId());
			
			
		}
	}
	
	private void cancelarTarefas(PedidoColeta pedidoColeta){
		final Long rmr = pedidoColeta.getSolicitacao().getMatch().getBusca().getPaciente().getRmr();		
		final Doador doador = pedidoColeta.getSolicitacao().getMatch().getDoador();
		
		if(doador.isNacional()){
			TarefaDTO tarefaPedidoColeta = TiposTarefa.PEDIDO_COLETA.getConfiguracao().obterTarefa()				
				.comRmr(rmr)
				.comObjetoRelacionado(pedidoColeta.getId())
				.comStatus(Arrays.asList(StatusTarefa.ABERTA, StatusTarefa.ATRIBUIDA))
				.apply();
			if (tarefaPedidoColeta != null) {	
				TiposTarefa.PEDIDO_COLETA.getConfiguracao().cancelarTarefa()
					.comTarefa(tarefaPedidoColeta.getId())
					.apply();
			}
		}
		else{
			TarefaDTO tarefaPedidoColeta = TiposTarefa.PEDIDO_COLETA_INTERNACIONAL.getConfiguracao().obterTarefa()				
					.comRmr(rmr)
					.comObjetoRelacionado(pedidoColeta.getId())
					.comStatus(Arrays.asList(StatusTarefa.ABERTA, StatusTarefa.ATRIBUIDA))
					.apply();
			if (tarefaPedidoColeta != null) {
				TiposTarefa.PEDIDO_COLETA_INTERNACIONAL.getConfiguracao().cancelarTarefa()
					.comTarefa(tarefaPedidoColeta.getId())					
					.apply();
			}
		}
		

		if (doador.isCordao()) {
			TarefaDTO tarefaSugerirDataColeta = TiposTarefa.SUGERIR_DATA_COLETA.getConfiguracao().obterTarefa()
				.comRmr(rmr)
				.comObjetoRelacionado(pedidoColeta.getId())
				.comStatus(Arrays.asList(StatusTarefa.ABERTA, StatusTarefa.ATRIBUIDA))
				.apply();
			
			if (tarefaSugerirDataColeta != null) {
				TiposTarefa.SUGERIR_DATA_COLETA.getConfiguracao().cancelarTarefa()
					.comTarefa(tarefaSugerirDataColeta.getId())
					.apply();	
			}
		}
		
	}
	
	@Override
	public DadosCentroTransplanteDTO listarDadosCT(Long idPedido) {
		DadosCentroTransplanteDTO dadosCTDTO = pedidoColetaRepository.buscarDadosCT(idPedido);
		List<ContatoTelefonico> telefonesCT = pedidoColetaRepository.buscarTelefonesCT(idPedido);
		List<ContatoTelefonico> telefonesMedico = pedidoColetaRepository.buscarTelefonesMedico(idPedido);
		dadosCTDTO.setTelefonesCT(telefonesCT);
		dadosCTDTO.setTelefonesMedico(telefonesMedico);

		return dadosCTDTO;
	}

	@Override
	public JsonViewPage<TarefaDTO> listarDisponibilidadesPorCentroTransplante(Long idCentroTransplante,
			PageRequest pageRequest) {
		CentroTransplante centroTransplante = centroTransplanteService.obterCentroTransplante(idCentroTransplante);
		
		return TiposTarefa.SUGERIR_DATA_COLETA.getConfiguracao()
					.listarTarefa()
					.comPaginacao(pageRequest)
					.comStatus(Arrays.asList(StatusTarefa.ABERTA))
					.comParceiros(Arrays.asList(centroTransplante.getId()))
					.apply();
	}

	
	@Override
	public void atualizarDataColetaTarefaDeCourier(Long idPedidoLogistica, LocalDate data) {
		PedidoTransporte pedidoTransporte = pedidoTransporteService.obterPedidoPorLogistica(idPedidoLogistica);
		PedidoColeta pedidoColeta = pedidoTransporte.getPedidoLogistica().getPedidoColeta();
		
		Paciente paciente = obterPacientePorPedidoColeta(pedidoTransporte.getPedidoLogistica().getPedidoColeta());
		
		this.update(new UpdateSet<LocalDate>("dataColeta", data), new Equals<Long>("id", pedidoColeta.getId()));
		
		fecharTarefaPedidoTransporte(paciente);
		criarTarefaDePedidoDeTransporte(pedidoTransporte);
		
	}
	
	private void fecharTarefaPedidoTransporte(Paciente paciente) {
		if(!TiposTarefa.PEDIDO_TRANSPORTE.getConfiguracao()
				.listarTarefa()
				.comRmr(paciente.getRmr())
				.comStatus(Arrays.asList(StatusTarefa.ATRIBUIDA, StatusTarefa.ABERTA))
				.apply()
				.getValue()
				.isEmpty()) {
			TiposTarefa.PEDIDO_TRANSPORTE.getConfiguracao()
			.fecharTarefa()
			.comStatus(Arrays.asList(StatusTarefa.ATRIBUIDA, StatusTarefa.ABERTA))
			.comRmr(paciente.getRmr())
			.apply();			
		}
	}
	
	private void criarTarefaDePedidoDeTransporte(PedidoTransporte pedidoTransporte) {
		final Paciente paciente = obterPacientePorPedidoColeta(pedidoTransporte.getPedidoLogistica().getPedidoColeta());
		
		TiposTarefa.PEDIDO_TRANSPORTE.getConfiguracao()
			.criarTarefa()
			.comRmr(paciente.getRmr())
			.comObjetoRelacionado(pedidoTransporte.getId())
			.comParceiro(pedidoTransporte.getTransportadora())
			.comStatus(StatusTarefa.ABERTA)
			.apply();
	}
	
	@Override
	public JsonViewPage<TarefaDTO> listarTarefasAgendadasInternacional(PageRequest paginacao) {

		Comparator<TarefaDTO> comparator = OrdenacaoTarefa.getComparator(OrdenacaoTarefa.PEDIDO_COLETA.getChave(), null);

		return TiposTarefa.PEDIDO_COLETA_INTERNACIONAL.getConfiguracao()
			.listarTarefa()
			.comStatus(Arrays.asList(StatusTarefa.ABERTA, StatusTarefa.ATRIBUIDA))
			.comPaginacao(paginacao)
			.comOrdenacao(comparator)
			.paraTodosUsuarios()
			.apply();
	}

	@Override
	public JsonViewPage<TarefaDTO> listarTarefasAgendadas(PageRequest paginacao) {

		Comparator<TarefaDTO> comparator = OrdenacaoTarefa.getComparator(OrdenacaoTarefa.PEDIDO_COLETA.getChave(), null);
		
		return TiposTarefa.PEDIDO_COLETA.getConfiguracao()
			.listarTarefa()
			.comStatus(Arrays.asList(StatusTarefa.ABERTA))
			.comPaginacao(paginacao)
			.comOrdenacao(comparator)
			.paraTodosUsuarios()
			.apply();
		
	}
	
	/**
	 * @param pedidoLogisticaService the pedidoLogisticaService to set
	 */
	@Autowired
	public void setPedidoLogisticaService(IPedidoLogisticaService pedidoLogisticaService) {
		this.pedidoLogisticaService = pedidoLogisticaService;
	}

	/**
	 * @param pedidoColetaRepository the pedidoColetaRepository to set
	 */
	@Autowired
	public void setPedidoColetaRepository(IPedidoColetaRepository pedidoColetaRepository) {
		this.pedidoColetaRepository = pedidoColetaRepository;
	}

	/**
	 * @param usuarioService the usuarioService to set
	 */
	@Autowired
	public void setUsuarioService(IUsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	/**
	 * @param disponibilidadeService the disponibilidadeService to set
	 */
	@Autowired
	public void setDisponibilidadeService(IDisponibilidadeService disponibilidadeService) {
		this.disponibilidadeService = disponibilidadeService;
	}

	/**
	 * @param solicitacaoService the solicitacaoService to set
	 */
	@Autowired
	public void setSolicitacaoService(ISolicitacaoService solicitacaoService) {
		this.solicitacaoService = solicitacaoService;
	}

	/**
	 * @param centroTransplanteService the centroTransplanteService to set
	 */
	@Autowired
	public void setCentroTransplanteService(ICentroTransplanteService centroTransplanteService) {
		this.centroTransplanteService = centroTransplanteService;
	}

	public IPedidoTransporteService getPedidoTransporteService() {
		return pedidoTransporteService;
	}

	@Autowired
	public void setPedidoTransporteService(IPedidoTransporteService pedidoTransporteService) {
		this.pedidoTransporteService = pedidoTransporteService;
	}
	
	@Override
	public Paciente obterPaciente(PedidoColeta pedidoColeta) {
		return pedidoColeta.getSolicitacao().getMatch().getBusca().getPaciente();
	}


	@SuppressWarnings("rawtypes")
	@Override
	public String[] obterParametros(PedidoColeta pedidoColeta) {
		IDoador doador = (IDoador) pedidoColeta.getSolicitacao().getMatch().getDoador();
		return StringUtils.split(doador.getIdentificacao().toString());
	}

	@Override
	public void fecharPedidoColeta(Long id) {
		PedidoColeta pedidoColeta = obterPedidoColeta(id);
		pedidoColeta.setStatusPedidoColeta(new StatusPedidoColeta(StatusPedidosColeta.REALIZADO));
		save(pedidoColeta);
		
		solicitacaoService.fecharSolicitacao(pedidoColeta.getSolicitacao().getId());
	}
	

}

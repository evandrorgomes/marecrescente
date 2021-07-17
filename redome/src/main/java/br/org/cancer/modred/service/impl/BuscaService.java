package br.org.cancer.modred.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.modred.controller.dto.BuscaPaginacaoDTO;
import br.org.cancer.modred.controller.dto.HistoricoBuscaDTO;
import br.org.cancer.modred.exception.BusinessException;
import br.org.cancer.modred.feign.dto.TarefaDTO;
import br.org.cancer.modred.model.Avaliacao;
import br.org.cancer.modred.model.AvaliacaoCamaraTecnica;
import br.org.cancer.modred.model.Busca;
import br.org.cancer.modred.model.CancelamentoBusca;
import br.org.cancer.modred.model.CentroTransplante;
import br.org.cancer.modred.model.Locus;
import br.org.cancer.modred.model.Match;
import br.org.cancer.modred.model.MotivoCancelamentoBusca;
import br.org.cancer.modred.model.Paciente;
import br.org.cancer.modred.model.StatusAvaliacaoCamaraTecnica;
import br.org.cancer.modred.model.StatusBusca;
import br.org.cancer.modred.model.ValorGenotipoPaciente;
import br.org.cancer.modred.model.annotations.log.CreateLog;
import br.org.cancer.modred.model.domain.CategoriasNotificacao;
import br.org.cancer.modred.model.domain.MotivosCancelamentoColeta;
import br.org.cancer.modred.model.domain.MotivosCancelamentoWorkup;
import br.org.cancer.modred.model.domain.Perfis;
import br.org.cancer.modred.model.domain.StatusPacientes;
import br.org.cancer.modred.model.domain.StatusTarefa;
import br.org.cancer.modred.model.domain.TipoLogEvolucao;
import br.org.cancer.modred.model.domain.TiposBuscaChecklist;
import br.org.cancer.modred.model.domain.TiposSolicitacao;
import br.org.cancer.modred.model.domain.TiposTarefa;
import br.org.cancer.modred.persistence.IAvaliacaoRepository;
import br.org.cancer.modred.persistence.IBuscaRepository;
import br.org.cancer.modred.persistence.ICancelamentoBuscaRepository;
import br.org.cancer.modred.persistence.IMotivoCancelamentoBuscaRepository;
import br.org.cancer.modred.persistence.IRepository;
import br.org.cancer.modred.persistence.IStatusBuscaRepository;
import br.org.cancer.modred.persistence.IValorGenotipoRepository;
import br.org.cancer.modred.service.IAvaliacaoCamaraTecnicaService;
import br.org.cancer.modred.service.IAvaliacaoService;
import br.org.cancer.modred.service.IBuscaChecklistService;
import br.org.cancer.modred.service.IBuscaService;
import br.org.cancer.modred.service.ICentroTransplanteService;
import br.org.cancer.modred.service.IHistoricoBuscaService;
import br.org.cancer.modred.service.IHistoricoStatusPacienteService;
import br.org.cancer.modred.service.IMatchService;
import br.org.cancer.modred.service.IPacienteService;
import br.org.cancer.modred.service.IPedidoEnvioEmdisService;
import br.org.cancer.modred.service.ISolicitacaoService;
import br.org.cancer.modred.service.IUsuarioService;
import br.org.cancer.modred.service.impl.invocation.AbstractLoggingService;
import br.org.cancer.modred.util.AppUtil;

/**
 * Implementacao da classe de negócios de Busca.
 * 
 * @author Filipe Paes
 *
 */
@Service
@Transactional(propagation=Propagation.REQUIRED)
public class BuscaService extends AbstractLoggingService<Busca, Long> implements IBuscaService {

	private static final Logger LOGGER = LoggerFactory.getLogger(BuscaService.class);

	@Autowired
	private IBuscaRepository buscaRepository;

	@Autowired
	private IMotivoCancelamentoBuscaRepository motivoCancelamentoRepository;

	@Autowired
	private ICancelamentoBuscaRepository cancelamentoRepository;

	private IUsuarioService usuarioService;

	@Autowired
	private IAvaliacaoRepository avaliacaoRepository;

	private IAvaliacaoService avaliacaoService;

	@Autowired
	private IValorGenotipoRepository genotipoRepository;

	@Autowired
	private IStatusBuscaRepository statusBuscaRepository;

	private ISolicitacaoService solicitacaoService;

	private IHistoricoBuscaService historicoBuscaService;
	
	private IBuscaChecklistService buscaChecklistService;

	private IPedidoEnvioEmdisService pedidoEnvioEmdisService;
	
	private IAvaliacaoCamaraTecnicaService avaliacaoCamaraTecnicaService;
	
	private IHistoricoStatusPacienteService historicoStatusPacienteService;
	
	@Autowired
	private IPacienteService pacienteService;
	
	@Autowired
	private ICentroTransplanteService centroTransplanteService;
	
	private IMatchService matchService;
	
	public BuscaService() {
		super();
	}

	@Override
	public IRepository<Busca, Long> getRepository() {
		return buscaRepository;
	}

	@Override
	public void criarBuscaInicial(Paciente paciente) {
		Busca busca = new Busca(paciente, new StatusBusca(StatusBusca.AGUARDANDO_LIBERACAO), null, paciente
				.getAceiteMismatch());
		save(busca);
		LOGGER.info("Busca criada para o paciente {}", paciente.getRmr());
	}

	@Override
	public Busca obterBusca(Long id) {
		if (id == null) {
			throw new BusinessException("erro.busca.id_nulo");
		}
		return findById(id);
	}

	@Override
	public void alterarStatusDeBusca(Long idBusca, Long idStatus) {
		Busca busca = obterBusca(idBusca);
		if (busca == null) {
			throw new BusinessException("erro.busca.nao_localizada");
		}
		busca.setStatus(new StatusBusca(idStatus));
		save(busca);
	}

	@Override
	public void alterarDataUltimaAnaliseTecnicaDeBusca(Long idBusca) {
		Busca busca = obterBusca(idBusca);
		if (busca == null) {
			throw new BusinessException("erro.busca.nao_localizada");
		}
		busca.setDataUltimaAnalise(LocalDateTime.now());
		save(busca);
	}	
	
	@Override
	public void atribuirBuscaParaAnalistaRedome(Long idBusca) {
		Busca busca = obterBusca(idBusca);
		if (busca == null) {
			throw new BusinessException("erro.busca.nao_localizada");
		}
		busca.setUsuario(usuarioService.obterUsuarioLogado());
		busca.setStatus(new StatusBusca(StatusBusca.EM_AVALIACAO));
		buscaRepository.save(busca);
	}

	@Override
	public void iniciarProcessoDeBusca(Long rmr) {
		if (rmr == null) {
			throw new BusinessException("erro.busca.id_rmr_nulo");
		}

		Avaliacao avaliacao = avaliacaoRepository.findByPacienteRmr(rmr);
		AvaliacaoCamaraTecnica avaliacaoCamaraTecnica = avaliacaoCamaraTecnicaService.obterAvaliacaoPor(rmr);
		
		List<Busca> buscas = buscaRepository.findByPacienteRmrAndStatusIdIn(rmr, Arrays.asList(StatusBusca.LIBERADA
				, StatusBusca.AGUARDANDO_LIBERACAO
				, StatusBusca.EM_AVALIACAO));

		if (buscas == null || buscas.isEmpty()) {
			throw new BusinessException("erro.busca.busca_nao_localizada");
		}

		Busca busca = buscas.get(0);

		if ((avaliacao.getAprovado() && busca.getStatus().getId().equals(StatusBusca.AGUARDANDO_LIBERACAO))
			&& (avaliacaoCamaraTecnica == null || StatusAvaliacaoCamaraTecnica.APROVADO.equals(avaliacaoCamaraTecnica.getStatus().getId()))) {
			
			List<ValorGenotipoPaciente> genotipos = genotipoRepository.obterGenotipo(rmr);
			
			if (existemTodosOsLocusParaBuscaInicial(genotipos)){
				
				alterarStatusDeBusca(busca.getId(), StatusBusca.LIBERADA);
				
				historicoStatusPacienteService.adicionarStatusPaciente(StatusPacientes.APROVADO, avaliacao.getPaciente());

				buscaChecklistService.criarItemCheckList(busca, TiposBuscaChecklist.NOVA_BUSCA);
				
				pedidoEnvioEmdisService.criarPedido(busca.getId());
				
				TarefaDTO tarefa = TiposTarefa.RECEBER_PACIENTE.getConfiguracao().criarTarefa()
					.comRmr(rmr)
					.comStatus(StatusTarefa.ABERTA)
					.comObjetoRelacionado(busca.getId())
					.apply();
				
				TiposTarefa.ENVIAR_DADOS_PACIENTE_WMDA_FOLLOWUP.getConfiguracao().criarTarefa()
					.comRmr(rmr)
					.comProcessoId(tarefa.getProcesso().getId())
					.comObjetoRelacionado(busca.getId())
					.apply();
				
			}
			
		}
	}
	
	private boolean existemTodosOsLocusParaBuscaInicial(List<ValorGenotipoPaciente> genotipos) {
		return (genotipos != null && !genotipos.isEmpty()) && genotipos.stream().anyMatch(g -> Locus.LOCUS_A.equals(g.getId().getLocus().getCodigo())) 
		&& genotipos.stream().anyMatch(g -> Locus.LOCUS_B.equals(g.getId().getLocus().getCodigo()))
		&& genotipos.stream().anyMatch(g -> Locus.LOCUS_DRB1.equals(g.getId().getLocus().getCodigo()));
	}
	
	/*
	 * private Processo criarProcesso(Paciente paciente) { Processo processo = new
	 * Processo(); processo.setPaciente(paciente);
	 * processo.setTipo(TipoProcesso.BUSCA);
	 * processoService.criarProcesso(processo); return processo; }
	 */

	@Override
	public Page<BuscaPaginacaoDTO> listarBuscas(String loginAnalistaBusca, Long idTipoBuscaCheckList, 
			Long rmr, String nome, Pageable pageable) {
		
		
		return buscaRepository.listarBuscas(loginAnalistaBusca, idTipoBuscaCheckList, rmr, nome,						
				Arrays.asList(StatusBusca.LIBERADA, StatusBusca.MATCH_SELECIONADO), 
				pageable);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.org.cancer.modred.service.IBuscaService#cancelarBusca(java.lang.Long)
	 */
	@Override
	public void cancelarBusca(Long rmr, CancelamentoBusca cancelamentoBusca) {
		Busca busca = this.obterBuscaAtivaPorRmr(rmr);
		if (busca == null) {
			throw new BusinessException("erro.busca.nao_localizada");
		}

		busca.setStatus(statusBuscaRepository.findById(StatusBusca.CANCELADA).get());
		buscaRepository.save(busca);

		cancelarProcessosDoPaciente(rmr);

		cancelamentoBusca.setBusca(busca);
		cancelamentoRepository.save(cancelamentoBusca);

		cancelarSolicitacoes(busca, "CANCELAMENTO DA BUSCA", MotivosCancelamentoWorkup.CANCELAMENTO_BUSCA, MotivosCancelamentoColeta.BUSCA_FOI_CANCELADA);
	}

	private void cancelarSolicitacoes(Busca busca, String justificativa, MotivosCancelamentoWorkup motivosCancelamentoWorkup, MotivosCancelamentoColeta motivosCancelamentoColeta) {
		if (busca.getMatchs() != null) {
			busca.getMatchs().forEach(m -> {
				if (m.getSolicitacoes() != null) {
					m.getSolicitacoes().forEach(s -> {
						solicitacaoService.cancelarSolicitacao(s.getId(), justificativa, LocalDate.now(), null, null);
					});
				}
			});
		}
	}

	/**
	 * Para cancelar todo o processo de busca do paciente, deve-se encerrar a avaliação com suas pendêncas e tarefas.
	 * 
	 * @param rmr
	 */
	private void cancelarProcessosDoPaciente(Long rmr) {
		Avaliacao avaliacao = avaliacaoService.obterUltimaAvaliacaoPorPaciente(rmr);
		avaliacaoService.cancelarAvaliacaoEProcessoDeAvaliacao(avaliacao, rmr);
		cancelarProcessoDeBuscaDoPaciente(rmr);
	}

	/**
	 * O processo pode ainda não existir, pois ele é criado depois do paciente ter sido avaliado e seus exames conferidos e que
	 * contenham os locus A,B e DRB1, porém a busca pode ser cancelada antes de ter o processo, por isso verifico se o processo
	 * existe antes de cancelar.
	 * 
	 * @param rmr
	 */
	private void cancelarProcessoDeBuscaDoPaciente(Long rmr) {
		//Processo processo = processoService.obterProcessoAtivo(TipoProcesso.BUSCA, rmr);

		//if (processo != null) {
		Page<TarefaDTO> tarefas = TiposTarefa.RECEBER_PACIENTE.getConfiguracao().listarTarefa()
			.comRmr(rmr)
			.comStatus(Arrays.asList(StatusTarefa.ABERTA, StatusTarefa.ATRIBUIDA))
			.paraTodosUsuarios()
			.apply().getValue();
		
		if (tarefas != null) {
			tarefas.get().forEach(tarefa -> {
				TiposTarefa.RECEBER_PACIENTE.getConfiguracao().cancelarTarefa()
					.comTarefa(tarefa.getId())
					.cancelarProcesso()
					.apply();
			});
		}
		
	}

	@Override
	public Busca obterBuscaAtivaPorRmr(Long rmr) {
		if (rmr == null) {
			throw new BusinessException("erro.busca.id_nulo");
		}
		return buscaRepository.findByPacienteRmrAndStatusBuscaAtiva(rmr, Boolean.TRUE);
	}

	@Override
	public Busca obterBuscaAtivaPorId(Long id) {
		if (id == null) {
			throw new BusinessException("erro.busca.id_nulo");
		}
		return buscaRepository.findById(id).orElse(null);
	}
	
	@Override
	public List<MotivoCancelamentoBusca> listarMotivosCancelamento() {
		return motivoCancelamentoRepository.findAll();
	}

	@Override
	public void atribuirBuscaETarefaParaAnalistaRedome(Long idBusca) {
		Busca busca = buscaRepository.findById(idBusca).get();

		TiposTarefa.RECEBER_PACIENTE.getConfiguracao().atribuirTarefa()
			.comRmr(busca.getPaciente().getRmr())
			.comObjetoRelacionado(busca.getId())
			.comUsuarioLogado()
			.apply();
		
		atribuirBuscaParaAnalistaRedome(busca.getId());
	}

	@Override
	public Busca obterBuscaPorMatch(Long matchId) {
		return buscaRepository.obterBuscaPorMatch(matchId);
	}

	@Override
	public Busca obterBuscaPorPedidoWorkup(Long pedidoWorkupId) {
		return buscaRepository.obterBuscaPorPedidoWorkup(pedidoWorkupId);
	}

	@Override
	public Busca obterBuscaPorPedidoContato(Long pedidoContatoId) {
		return buscaRepository.obterBuscaPorPedidoContato(pedidoContatoId);
	}

	@Override
	public Busca obterBuscaPorPedidoExame(Long pedidoExameId) {
		return buscaRepository.obterBuscaPorPedidoExame(pedidoExameId);
	}

	private void verficarExistenciaCentroTransplanteNaBusca(Busca busca) {
		if (busca.getCentroTransplante() != null) {
			throw new BusinessException("centro.transplantador.ja.cadastrado.para.paciente", String.valueOf(busca.getPaciente()
					.getRmr()));
		}		
	}

	@Override
	public Paciente obterPaciente(Busca busca) {
		return busca.getPaciente();
	}

	@Override
	public String[] obterParametros(Busca busca) {
		return busca.getPaciente().getRmr().toString().split(";");
	}

	private void criarTarefaCadastrarPrescricao(Busca busca) {
		
		final List<Match> matchsDisponibilizados = matchService.listarMatchsAtivosEDisponibilizadosPeloIdentificadorDaBusca(busca.getId());		
		if (CollectionUtils.isNotEmpty(matchsDisponibilizados)) {
			
			matchsDisponibilizados.forEach(match -> {
				matchService.criarTarefaCadastrarPrescricao(match);				
			});
		}
	}
			
	private void cancelarTarefaCadastrarPrescricao(Busca busca) {
		
		Page<TarefaDTO> tarefasEncontrada = TiposTarefa.CADASTRAR_PRESCRICAO.getConfiguracao().listarTarefa()
				.comStatus(Arrays.asList(StatusTarefa.ABERTA, StatusTarefa.ATRIBUIDA))
				.comRmr(busca.getPaciente().getRmr())
				.comParceiros(Arrays.asList(busca.getCentroTransplante().getId()))
				.apply().getValue();
		
		if (!tarefasEncontrada.isEmpty()) {
			tarefasEncontrada.forEach(tarefa -> {
				tarefa.getTipo().getConfiguracao()
					.cancelarTarefa()
					.comTarefa(tarefa.getId())
					.apply();
			});			
			
		}
		
	}
	
	private void criarTarefaEncontrarCentroTransplante(Busca busca) {

		TiposTarefa.ENCONTRAR_CENTRO_TRANSPLANTE.getConfiguracao()
				.criarTarefa()
				.comRmr(busca.getPaciente().getRmr())
				.comObjetoRelacionado(busca.getId())
				.apply();
	}

	private void fecharTarefaEncontrarCentroTransplante(Busca busca) {

		TarefaDTO tarefa = TiposTarefa.ENCONTRAR_CENTRO_TRANSPLANTE.getConfiguracao()
				.obterTarefa()
				.comRmr(busca.getPaciente().getRmr())
				.comStatus(Arrays.asList(StatusTarefa.ATRIBUIDA))
				.comObjetoRelacionado(busca.getId())
				.apply();

		if (tarefa != null) {
			TiposTarefa.ENCONTRAR_CENTRO_TRANSPLANTE.getConfiguracao()
					.fecharTarefa()
					.comProcessoId(tarefa.getProcesso().getId())
					.comStatus(Arrays.asList(StatusTarefa.ATRIBUIDA))
					.comObjetoRelacionado(busca.getId())
					.apply();
		}
	}

	@CreateLog(TipoLogEvolucao.CENTRO_TRANSPLANTE_INDEFINIDO_PARA_PACIENTE)
	@Override
	public void indefinirCentroTransplante(Busca busca) {
		verficarExistenciaCentroTransplanteNaBusca(busca);
		
		busca.setStatus(new StatusBusca(StatusBusca.MATCH_SELECIONADO));
		busca.setCentroTransplante(null);
		
		save(busca);
		
		cancelarTarefaCadastrarPrescricao(busca);
		criarTarefaEncontrarCentroTransplante(busca);

	}

	@CreateLog(TipoLogEvolucao.CENTRO_TRANSPLANTE_CONFIRMADO_PARA_PACIENTE)
	@Override
	public void confirmarCentroTransplate(Busca busca, Long centroTransplanteId) {		
		verficarExistenciaCentroTransplanteNaBusca(busca);		
		
		busca.setStatus(new StatusBusca(StatusBusca.MATCH_SELECIONADO));
		busca.setCentroTransplante(centroTransplanteId == null ? null : new CentroTransplante(centroTransplanteId));
		
		save(busca);
		
		fecharTarefaEncontrarCentroTransplante(busca);		
		criarTarefaCadastrarPrescricao(busca);
		criarNotificacaoParaCentroTransplate(busca);
	}

	private void criarNotificacaoParaCentroTransplate(Busca busca) {
		
		CategoriasNotificacao.MATCH_DISPONIBILIZADO.getConfiguracao().criar()
			.comDescricao(AppUtil.getMensagem(messageSource, "paciente.direcionado.centrotransplante.notificacao", busca.getPaciente().getRmr()))
			.comPaciente(busca.getPaciente().getRmr())
			.paraPerfil(Perfis.MEDICO_TRANSPLANTADOR)
			.comParceiro(busca.getCentroTransplante().getId())
			.apply();
		
	}

	@CreateLog(TipoLogEvolucao.CENTRO_TRANSPLANTE_REDEFINIDO_PARA_PACIENTE)
	@Override
	public void recusarCentroTransplante(Busca busca, String justificativa) {
		historicoBuscaService.registrar(busca, justificativa);
		redefinirCentroTransplante(busca);
	}

	private void redefinirCentroTransplante(Busca busca) {
		criarTarefaEncontrarCentroTransplante(busca);

		busca.setCentroTransplante(null);
		save(busca);
	}

	@Override
	public List<HistoricoBuscaDTO> listarHistoricoBusca(Long rmr) {
		return buscaRepository.listarHistoricoBusca(rmr);
	}

	@Override
	public void criarNovaBuscaInicial(Paciente paciente) {
		// TODOO: IMPLEMENTAR QUANDO NOVA BUSCA APROVADA.
	}

	/**
	 * @param usuarioService the usuarioService to set
	 */
	@Autowired
	public void setUsuarioService(IUsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	/**
	 * @param avaliacaoService the avaliacaoService to set
	 */
	@Autowired
	public void setAvaliacaoService(IAvaliacaoService avaliacaoService) {
		this.avaliacaoService = avaliacaoService;
	}

	/**
	 * @param solicitacaoService the solicitacaoService to set
	 */
	@Autowired
	public void setSolicitacaoService(ISolicitacaoService solicitacaoService) {
		this.solicitacaoService = solicitacaoService;
	}

	/**
	 * @param historicoBuscaService the historicoBuscaService to set
	 */
	@Autowired
	public void setHistoricoBuscaService(IHistoricoBuscaService historicoBuscaService) {
		this.historicoBuscaService = historicoBuscaService;
	}

	/**
	 * @param buscaChecklistService the buscaChecklistService to set
	 */
	@Autowired
	public void setBuscaChecklistService(IBuscaChecklistService buscaChecklistService) {
		this.buscaChecklistService = buscaChecklistService;
	}

	/**
	 * @param pedidoEnvioEmdisService the pedidoEnvioEmdisService to set
	 */
	@Autowired
	public void setPedidoEnvioEmdisService(IPedidoEnvioEmdisService pedidoEnvioEmdisService) {
		this.pedidoEnvioEmdisService = pedidoEnvioEmdisService;
	}

	/**
	 * @param avaliacaoCamaraTecnicaService the avaliacaoCamaraTecnicaService to set
	 */
	@Autowired
	public void setAvaliacaoCamaraTecnicaService(IAvaliacaoCamaraTecnicaService avaliacaoCamaraTecnicaService) {
		this.avaliacaoCamaraTecnicaService = avaliacaoCamaraTecnicaService;
	}

	/**
	 * @param historicoStatusPacienteService the historicoStatusPacienteService to set
	 */
	@Autowired
	public void setHistoricoStatusPacienteService(IHistoricoStatusPacienteService historicoStatusPacienteService) {
		this.historicoStatusPacienteService = historicoStatusPacienteService;
	}

	@Override
	public void alterarStatusDeBuscaParaLiberado(Long rmr) {
		Busca busca = buscaRepository.findByPacienteRmr(rmr);
		busca.setStatus(new StatusBusca(StatusBusca.LIBERADA));
		this.save(busca);
	}

	@Override
	public void desatribuirBuscaDeAnalista(Long rmr) {
		Busca busca = buscaRepository.findByPacienteRmr(rmr);
		busca.setUsuario(null);
		this.save(busca);
	}

	@Autowired
	public void setMatchService(IMatchService matchService) {
		this.matchService = matchService;
	}

	@Override
	public void encerrarBusca(Long id) {
		Busca busca = obterBusca(id);
		if (existeOutraSolicitacaoWorkupCordaoEmAberto(busca.getId())) {
			cancelarSolicitacoes(busca, "BUSCA ENCERRADA", MotivosCancelamentoWorkup.BUSCA_ENCERRADA, MotivosCancelamentoColeta.BUSCA_ENCERRADA);
		
			busca.setStatus(new StatusBusca(StatusBusca.ENCERRADA));
			save(busca);
		}
		
	}
	
	private boolean existeOutraSolicitacaoWorkupCordaoEmAberto(Long idBusca) {
		return !(new Long(0).equals(solicitacaoService.quantidadeSolicitacoesAbertasDosMatchsDaBuscaComTipoSolicitacao(idBusca, Arrays.asList(TiposSolicitacao.WORKUP_CORDAO)))); 
	}
	
	

}


package br.org.cancer.modred.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.modred.controller.dto.ResultadoPedidoInternacionalDTO;
import br.org.cancer.modred.controller.dto.SolicitacaoInternacionalDTO;
import br.org.cancer.modred.controller.page.Paginacao;
import br.org.cancer.modred.exception.BusinessException;
import br.org.cancer.modred.feign.dto.TarefaDTO;
import br.org.cancer.modred.model.Busca;
import br.org.cancer.modred.model.CentroTransplante;
import br.org.cancer.modred.model.Doador;
import br.org.cancer.modred.model.DoadorNacional;
import br.org.cancer.modred.model.ExameDoadorInternacional;
import br.org.cancer.modred.model.ExameDoadorNacional;
import br.org.cancer.modred.model.Laboratorio;
import br.org.cancer.modred.model.Locus;
import br.org.cancer.modred.model.Match;
import br.org.cancer.modred.model.Paciente;
import br.org.cancer.modred.model.PedidoEnriquecimento;
import br.org.cancer.modred.model.PedidoExame;
import br.org.cancer.modred.model.PedidoIdm;
import br.org.cancer.modred.model.PedidoWorkup;
import br.org.cancer.modred.model.Solicitacao;
import br.org.cancer.modred.model.TipoExame;
import br.org.cancer.modred.model.TipoSolicitacao;
import br.org.cancer.modred.model.domain.FasesWorkup;
import br.org.cancer.modred.model.domain.StatusPedidosWorkup;
import br.org.cancer.modred.model.domain.StatusSolicitacao;
import br.org.cancer.modred.model.domain.StatusTarefa;
import br.org.cancer.modred.model.domain.TiposDoador;
import br.org.cancer.modred.model.domain.TiposExame;
import br.org.cancer.modred.model.domain.TiposSolicitacao;
import br.org.cancer.modred.model.domain.TiposTarefa;
import br.org.cancer.modred.model.security.Usuario;
import br.org.cancer.modred.persistence.IRepository;
import br.org.cancer.modred.persistence.ISolicitacaoRepository;
import br.org.cancer.modred.persistence.ITipoSolicitacaoRepository;
import br.org.cancer.modred.service.IBuscaService;
import br.org.cancer.modred.service.ICentroTransplanteService;
import br.org.cancer.modred.service.IGenotipoDoadorService;
import br.org.cancer.modred.service.ILaboratorioService;
import br.org.cancer.modred.service.IMatchService;
import br.org.cancer.modred.service.IPedidoColetaService;
import br.org.cancer.modred.service.IPedidoContatoService;
import br.org.cancer.modred.service.IPedidoEnriquecimentoService;
import br.org.cancer.modred.service.IPedidoExameService;
import br.org.cancer.modred.service.IPedidoIdmService;
import br.org.cancer.modred.service.IPedidoWorkupService;
import br.org.cancer.modred.service.ISolicitacaoService;
import br.org.cancer.modred.service.impl.config.Equals;
import br.org.cancer.modred.service.impl.config.Filter;
import br.org.cancer.modred.service.impl.config.UpdateSet;
import br.org.cancer.modred.service.impl.invocation.AbstractLoggingService;

/**
 * Acesso as funcionalidades para criação de solicitações de pedidos de enriquecimento e de fase 2, 3.
 * 
 * @author Fillipe Queiroz
 *
 */
@Service
@Transactional
public class SolicitacaoService extends AbstractLoggingService<Solicitacao, Long> implements ISolicitacaoService {

	private static final Logger LOG = LoggerFactory.getLogger(SolicitacaoService.class);
	
	private IPedidoContatoService pedidoContatoService;

	@Autowired
	private ISolicitacaoRepository solicitacaoRepository;
	
	private IPedidoWorkupService pedidoWorkupService;

	private IMatchService matchService;

	private IBuscaService buscaService;
	
	private IPedidoEnriquecimentoService pedidoEnriquecimentoService;
	
	private IPedidoColetaService pedidoColetaService;
	
	private IPedidoExameService pedidoExameService;

	private IPedidoIdmService pedidoIdmService;
	
	private IGenotipoDoadorService<ExameDoadorNacional> genotipoDoadorNacionalService;
	
	private IGenotipoDoadorService<ExameDoadorInternacional> genotipoDoadorInternacionalService;
	
	private ILaboratorioService laboratorioService;
	
	@Autowired
	private ITipoSolicitacaoRepository tipoSolicitacaoRepository;
	
	@Autowired
	private ICentroTransplanteService centroTransplanteService;
	
	@Override
	public IRepository<Solicitacao, Long> getRepository() {
		return solicitacaoRepository;
	}

	@Override
	@Deprecated
	public Solicitacao criarSolicitacaoWorkup(Long rmr, Long idDoador) {
		LOG.info("Inicio da criação da solicitação workup.");
		Match match = matchService.obterMatchAtivo(rmr, idDoador);
		if (match == null) {
			throw new BusinessException("erro.solicitacao.match_inexistente");
		}
		
		validarQuantidadeSolicitaoes(match);
		
		Solicitacao solicitacao = new Solicitacao();
		solicitacao.setDataCriacao(LocalDateTime.now());
		if (match.getDoador().isMedula()) {
			solicitacao.setTipoSolicitacao(new TipoSolicitacao(TiposSolicitacao.WORKUP.getId()));
		}
		else {
			solicitacao.setTipoSolicitacao(new TipoSolicitacao(TiposSolicitacao.WORKUP_CORDAO.getId()));
		}
		solicitacao.setMatch(match);
		solicitacao.setStatus(StatusSolicitacao.ABERTA.getId());

		LOG.info("Fim da criação da solicitação workup.");
		return solicitacao;

	}
	
	private void validarQuantidadeSolicitaoes(Match match) {
		
		if (match.getDoador().isMedula()) {
			long quantidadePrescricao = solicitacaoRepository.quantidadeSolicitacoesEmAbertoDosMatchsPelaBuscaETipoSolicitacao(match.getBusca().getId(), 
					Arrays.asList(TiposSolicitacao.WORKUP.getId()));
						
			if (quantidadePrescricao != 0) {
				LOG.error("ERRO AO TENTAR CRIAR SOLICITAÇÃO, SOLICITAÇÃO JÁ EXISTENTE PARA ESTE TIPO");
				throw new BusinessException("erro.solicitacao.ja.existente");
			}
		}
		else {
			long quantidadePrescricao = solicitacaoRepository.quantidadeSolicitacoesEmAbertoDosMatchsPelaBuscaETipoSolicitacao(match.getBusca().getId(), 
					Arrays.asList(TiposSolicitacao.WORKUP_CORDAO.getId()));
						
			if (quantidadePrescricao == 2) {
				LOG.error("ERRO AO TENTAR CRIAR SOLICITAÇÃO, QUANTIDADE MÁXIMA DE SOLICITAÇÕES PARA ESTE TIPO");
				throw new BusinessException("erro.solicitacao.quantidade.excedido");
			}
		}
		
	}

	@Override
	public void cancelarReferenciasDoador(Long idDoador) {
		LOG.debug("CANCELANDO REFERENCIAS DE DOADOR");

		List<Solicitacao> solicitacoes = obterSolicitacoesEmAbertoPorIdDoador(idDoador);
		solicitacoes.forEach(s -> {
			cancelarSolicitacao(s.getId(), "Doador Inátivo", LocalDate.now(), null, null);
		});

		matchService.cancelarMatchsAtivos(idDoador);
	}

	private void cancelarRegistroSolicitacao(Solicitacao solicitacao) {
		LOG.info("CANCELANDO UMA SOLICITAÇÃO DO TIPO: " + solicitacao.getTipoSolicitacao().getDescricao());
		
		criarTarefaCancelarAgendamentoPedidoColetaCasoNecessario(solicitacao);

		UpdateSet<Integer> cancelado = new UpdateSet<Integer>("status", StatusSolicitacao.CANCELADA.getId());
		Filter<Long> porSolicitacao = new Equals<Long>("id", solicitacao.getId());
		int registrosAtualizados = update(cancelado, porSolicitacao);

		if (registrosAtualizados != 1) {
			throw new BusinessException("cancelamento.solicitacao.error");
		}

	}

	@Override
	public Solicitacao obterPorId(Long solicitacaoId) {
		return findById(solicitacaoId);
	}

	/**
	 * Cria a tarefa de notificação após o cancelamento da solicitação. Cenário passíveis de ocorrer são: - Cancelamento de pedido
	 * de coleta em aberto.
	 * 
	 * @param solicitacao solicitação que foi cancelada.
	 */
	public void criarTarefaCancelarAgendamentoPedidoColetaCasoNecessario(Solicitacao solicitacao) {
		if (TiposSolicitacao.WORKUP.getId().equals(solicitacao.getTipoSolicitacao().getId())) {
			LOG.debug("CRIANDO TAREFAS DE PEDIDO DE WORKUP");
			PedidoWorkup pedidoWorkup = pedidoWorkupService.obterPedidoWorkup(solicitacao.getId(), StatusPedidosWorkup.REALIZADO);

			if (pedidoWorkup != null) {
				final Long rmr = solicitacao.getMatch().getBusca().getPaciente().getRmr();
				
				TiposTarefa.CANCELAR_AGENDAMENTO_PEDIDO_COLETA_DOADOR.getConfiguracao().criarTarefa()
					.comRmr(rmr)
					.comUsuario(pedidoWorkup.getUsuarioResponsavel())
					.comStatus(StatusTarefa.ATRIBUIDA)
					.comObjetoRelacionado(pedidoWorkup.getId())
					.apply();
				
				TiposTarefa.CANCELAR_AGENDAMENTO_PEDIDO_COLETA_CENTRO_COLETA.getConfiguracao().criarTarefa()
					.comRmr(rmr)
					.comUsuario(pedidoWorkup.getUsuarioResponsavel())
					.comStatus(StatusTarefa.ATRIBUIDA)
					.comObjetoRelacionado(pedidoWorkup.getId())
					.apply();
			}
		}
	}

	@Override
	public Solicitacao obterSolicitacaoEmAbertoPor(Long idDoador, Long idTipoSolicitacao) {
		return solicitacaoRepository.solicitacaoPorTipoEIdDoadorComStatusEmAberto(idDoador, idTipoSolicitacao);
	}

	@Override
	public Solicitacao obterSolicitacaoEmFechadaPorIdDoador(Long idDoador, Integer idStatus) {
		return solicitacaoRepository.solicitacaoPorIdDoadorEStatus(idDoador, idStatus);
	}
	
	public List<Solicitacao> obterSolicitacoesEmAbertoPorIdDoador(Long idDoador) {
		return solicitacaoRepository.solicitacoesPorIdDoadorEStatus(idDoador, StatusSolicitacao.ABERTA.getId());
	}

	public List<Solicitacao> obterSolicitacoesEmFechadaPorIdDoador(Long idDoador) {
		return solicitacaoRepository.solicitacoesPorIdDoadorEStatus(idDoador, StatusSolicitacao.CONCLUIDA.getId());
	}

	@Override
	public Solicitacao obterSolicitacaoPorIdDoadorComIdStatusConcluidoECancelado(Long idDoador) {
		return solicitacaoRepository.ultimaSolicitacaoCriadaPorIdDoador(idDoador).get(0);
	}	
	
	@Override
	public Solicitacao cancelarSolicitacao(Long idSolicitacao, String justificativa,	LocalDate dataCancelamento, Long motivoStatusId,
			Long timeRetornoInatividade) {
				
		Solicitacao solicitacao = obterPorId(idSolicitacao);
		if (solicitacao == null) {
			LOG.error("SOLICITAÇÃO NÃO LOCALIZADA");
			throw new BusinessException("erro.solicitacao.nao.existente");
		}
		if (TiposSolicitacao.FASE_2.getId().equals(solicitacao.getTipoSolicitacao().getId())) {
			cancelarSolicitacaoFase2Nacional(solicitacao);
		}
		else if (TiposSolicitacao.FASE_3.getId().equals(solicitacao.getTipoSolicitacao().getId())) {
			// TODO - Criar um tipo de solicitação especifica para o paciente. (Bruno 27-11-2018)
			if (solicitacao.getBusca() == null) {
				cancelarSolicitacaoFase3Nacional(solicitacao);
			}
			else {
				cancelarSolicitacaoFase3Paciente(solicitacao, justificativa);
			}
		}
		else if (TiposSolicitacao.FASE_2_INTERNACIONAL.getId().equals(solicitacao.getTipoSolicitacao().getId())) {
			cancelarSolicitacaoFase2Internacional(solicitacao, justificativa, motivoStatusId, timeRetornoInatividade);
		}
		else if (TiposSolicitacao.FASE_3_INTERNACIONAL.getId().equals(solicitacao.getTipoSolicitacao().getId())) {
			cancelarSolicitacaoFase3Internacional(solicitacao, dataCancelamento, motivoStatusId, timeRetornoInatividade);
		}
		cancelarRegistroSolicitacao(solicitacao);
		
		if (TiposSolicitacao.WORKUP_DOADOR_NACIONAL_PACIENTE_NACIONAL.getId().equals(solicitacao.getTipoSolicitacao().getId()) || 
				TiposSolicitacao.WORKUP_DOADOR_INTERNACIONAL.getId().equals(solicitacao.getTipoSolicitacao().getId()) || 
				TiposSolicitacao.CORDAO_NACIONAL_PACIENTE_NACIONAL.getId().equals(solicitacao.getTipoSolicitacao().getId()) || 
				TiposSolicitacao.CORDAO_INTERNACIONAL.getId().equals(solicitacao.getTipoSolicitacao().getId())) {
			recriarTarefaCadastrarPrescricao(solicitacao);
		}
		
		return solicitacao;
		
	}
	
	private boolean medula(TiposDoador tipoDoador) {
		return TiposDoador.NACIONAL.equals(tipoDoador) || TiposDoador.INTERNACIONAL.equals(tipoDoador);
 	}
	
	
	private void cancelarSolicitacaoFase2Nacional(Solicitacao solicitacao) {
		LOG.debug("INICIO DO CANCELAMENTO DE SOLICITAÇÃO de fase 2 nacional para o match: " + solicitacao.getMatch().getId() );
				
		final Doador doador = solicitacao.getMatch().getDoador();
		pedidoEnriquecimentoService.fecharTodosPedidos(doador.getId());
		pedidoContatoService.cancelarPedidoContatoPorSolicitacao(solicitacao);		
		pedidoExameService.cancelarPedido(solicitacao, null, null, null, null);
		
		LOG.debug("FIM DO CANCELAMENTO DE SOLICITAÇÃO de fase 2 nacional para o match: " + solicitacao.getMatch().getId() );
	}
	
	private void cancelarSolicitacaoFase3Paciente(Solicitacao solicitacao, String justificativa) {
		LOG.debug("INICIO DO CANCELAMENTO DE SOLICITAÇÃO de fase 3 para o paciente: " + solicitacao.getBusca().getPaciente().getRmr() );
								
		pedidoExameService.cancelarPedido(solicitacao, justificativa, null, null, null);
		
		LOG.debug("FIM DO CANCELAMENTO DE SOLICITAÇÃO de fase 3 para o paciente: " + solicitacao.getBusca().getPaciente().getRmr() );
	}
	
	
	private void cancelarSolicitacaoFase3Nacional(Solicitacao solicitacao) {
		LOG.debug("INICIO DO CANCELAMENTO DE SOLICITAÇÃO de fase 3 nacional para o match: " + solicitacao.getMatch().getId() );
				
		final Doador doador = solicitacao.getMatch().getDoador();
		pedidoContatoService.cancelarPedidoContatoPorSolicitacao(solicitacao);		
		pedidoExameService.cancelarPedido(solicitacao, null, null, null, null);
				
		LOG.debug("FIM DO CANCELAMENTO DE SOLICITAÇÃO de fase 3 nacional para o match: " + solicitacao.getMatch().getId() );
	}
	
	private void cancelarSolicitacaoFase2Internacional(Solicitacao solicitacao, String justificativa, Long motivoStatusId, Long timeRetornoInatividade) {
		LOG.debug("INICIO DO CANCELAMENTO DE SOLICITAÇÃO de fase 2 internacional para o match: " + solicitacao.getMatch().getId() );
				
		final Doador doador = solicitacao.getMatch().getDoador();
		pedidoExameService.cancelarPedido(solicitacao, justificativa, null, motivoStatusId, timeRetornoInatividade);
		
		genotipoDoadorInternacionalService.atualizarFaseDoador(doador.getId());
		
		LOG.debug("FIM DO CANCELAMENTO DE SOLICITAÇÃO de fase 2 internacional para o match: " + solicitacao.getMatch().getId() );
	}
	
	private void cancelarSolicitacaoFase3Internacional(Solicitacao solicitacao, LocalDate dataCancelamento, Long motivoStatusId, Long timeRetornoInatividade) {
		LOG.debug("INICIO DO CANCELAMENTO DE SOLICITAÇÃO de fase 3 internacional para o match: " + solicitacao.getMatch().getId() );
				
		final Doador doador = solicitacao.getMatch().getDoador();
		
		pedidoExameService.cancelarPedido(solicitacao, null, dataCancelamento, motivoStatusId, timeRetornoInatividade);
		pedidoIdmService.cancelarPedido(solicitacao, null);
		
		genotipoDoadorInternacionalService.atualizarFaseDoador(doador.getId());
		
		LOG.debug("FIM DO CANCELAMENTO DE SOLICITAÇÃO de fase 3 internacional para o match: " + solicitacao.getMatch().getId() );
	}
	
	private void recriarTarefaCadastrarPrescricao(Solicitacao solicitacao) {
		LOG.debug("Recriando tarefa de cadastrar prescrição para o doador: " + solicitacao.getMatch().getDoador().getId() );
		matchService.criarTarefaCadastrarPrescricao(solicitacao.getMatch());
	}
	
	@Override
	public Solicitacao obterSolicitacaoPorPedidoExame(Long pedidoExameId) {
		return solicitacaoRepository.obterSolicitacaoPorPedidoExame(pedidoExameId);
	}

	@Override
	public void solicitarFase3Internacional(Long idMatch) {
		LOG.info("Início da rotina para Solicitar Fase 3 internacional para o Match: " + idMatch);
		try {
			if (idMatch == null) {
				throw new BusinessException("erro.parametros.invalidos");
			}
			Match match = matchService.findById(idMatch);
			if (match == null) {
				LOG.info("Não foi encontrado o match com id: " + idMatch);
				throw new BusinessException("erro.solicitacao.match_inexistente");
			}
			if (verificaSeExisteSolicitacaoEmAberto(match.getDoador())) {
				throw new BusinessException("erro.solicitacao.em.aberto", "doador");
			}
			if (verificaSeExistePedidoCTParaOdoador(match.getDoador().getId())) {
				throw new BusinessException("pedido.exame.ct.com.resultado.ja.existe.doador.falha");
			}
			Solicitacao solicitacao = criarSolicitacaoFase3Internacional(match);
			pedidoExameService.criarPedidoExameCtInternacional(solicitacao);
			if(TiposDoador.CORDAO_INTERNACIONAL.equals(match.getDoador().getTipoDoador())
					|| TiposDoador.INTERNACIONAL.equals(match.getDoador().getTipoDoador())){
				pedidoIdmService.criarPedidoIdmInternacional(solicitacao);
			}
			//matchService.atualizarFaseMatch(match.getId(), FasesMatch.TESTE_CONFIRMATORIO);
			
		}
		finally {
			LOG.info("Fim da rotina para Solicitar Fase 3 internacional para o Match: " + idMatch);
		}
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see br.org.cancer.modred.service.ISolicitacaoService#obterPedidoExamePorSolicitacaoId(java.lang.Long)
	 */
	@Override
	public SolicitacaoInternacionalDTO obterPedidoExamePorSolicitacaoId(Long idSolicitacao) {
		Solicitacao solicitacao = this.solicitacaoRepository.findById(idSolicitacao).orElse(null);
		
		if (solicitacao == null) {
			throw new BusinessException("erro.solicitacao.nao.existente");
		}
		SolicitacaoInternacionalDTO solicitacaoInternacionalDTO = new SolicitacaoInternacionalDTO();
		solicitacaoInternacionalDTO.setPedidoIdm(pedidoIdmService.findBySolicitacaoId(idSolicitacao));
		solicitacaoInternacionalDTO.setPedidoExame(pedidoExameService.obterPedidoExamePorSolicitacaoId(idSolicitacao));
		
		return solicitacaoInternacionalDTO;
	}
	
	/**
	 * Métod para criar uma solicitação de fase 3 internacional.
	 * 
	 * @param match
	 * @return solicitacao criada
	 */
	private Solicitacao criarSolicitacaoFase3Internacional(Match match) {
		Solicitacao solicitacao = new Solicitacao();
		solicitacao.setMatch(match);
		solicitacao.setDataCriacao(LocalDateTime.now());
		solicitacao.setTipoSolicitacao(new TipoSolicitacao(TiposSolicitacao.FASE_3_INTERNACIONAL.getId()));
		solicitacao.setStatus(StatusSolicitacao.ABERTA.getId());
		return save(solicitacao);
	}

	/**
	 * Verifica se existe solicitação em aberto para o doador independente do tipo.
	 * 
	 * @param idDoador - id do doador
	 * @return true se o doador possuir solicitacao e false se não possuir.
	 */
	private boolean verificaSeExisteSolicitacaoEmAberto(Doador doador) {
		List<Solicitacao> solicitacoes =  obterSolicitacoesEmAbertoPorIdDoador(doador.getId());		
		return !solicitacoes.isEmpty();
	}
	
	/*@Override
	public void cancelarSolicitacao(Solicitacao solicitacao, String justificativa, LocalDate dataCancelamentoPedido) {
		
	}*/

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.org.cancer.modred.service.ISolicitacaoService#recuperarIdDoadorPorSolicitacao(java.lang.Long)
	 */
	@Override
	public Long recuperarIdDoadorPorSolicitacao(Long idSolicitacao) {
		return findById(idSolicitacao).getMatch().getDoador().getId();
	}

	@Override
	public ResultadoPedidoInternacionalDTO obterDetalhesPedidoCtPedidoIdmInternacional(Long idSolicitacao) {
		PedidoExame pedidoCT = pedidoExameService.obterPedidoExamePorSolicitacaoId(idSolicitacao);
		ResultadoPedidoInternacionalDTO resultadoPedidoCtIdm = new ResultadoPedidoInternacionalDTO();
		
		if(pedidoCT != null){
			resultadoPedidoCtIdm.setIdPedidoCT(pedidoCT.getId());
			
			TarefaDTO tarefaPedidoCT = pedidoExameService.obterTarefaPorPedidoEmAberto(pedidoCT);
			if(tarefaPedidoCT != null){
				resultadoPedidoCtIdm.setIdTarefaPedidoCT(tarefaPedidoCT.getId());
			}
		}
		
		PedidoIdm pedidoIDM = pedidoIdmService.findBySolicitacaoId(idSolicitacao);
		if(pedidoIDM != null){
			resultadoPedidoCtIdm.setIdPedidoIdm(pedidoIDM.getId());
			
			TarefaDTO tarefaPedidoIdm = pedidoIdmService.obterTarefaPorPedidoEmAberto(pedidoIDM);
			if(tarefaPedidoIdm != null){
				resultadoPedidoCtIdm.setIdTarefaPedidoIdm(tarefaPedidoIdm.getId());
			}
		}
		
		return resultadoPedidoCtIdm;
	}

	@Override
	public Doador obterDoador(Solicitacao solicitacao) {
		return solicitacaoRepository.obterDoador(solicitacao.getId());
	}
	
	/**
	 * Verifica se existe Pedido de CT com resultado cadastrado para o doador.
	 * 
	 * @param idDoador - id do doador
	 * @return true se o doador possuir pedido de exame de ct com resultado cadastrado.
	 */
	private boolean verificaSeExistePedidoCTParaOdoador(Long idDoador) {
		return pedidoExameService.temPedidoExameCtComResultadoCadastrado(idDoador);
	}

//	@CreateLog(TipoLogEvolucao.SOLICITADO_EXAME_FASE2_PARA_DOADOR_NACIONAL)
	@Override
	public void solicitarFase2Nacional(Long idMatch, Long idTipoExame) {
		LOG.info("Início da rotina para Solicitar Fase 2 nacional para o Match: " + idMatch);
		try {
			if (idMatch == null || idTipoExame == null) {
				throw new BusinessException("erro.parametros.invalidos");
			}
			TiposExame tipoExame = TiposExame.get(idTipoExame);
			Match match = matchService.findById(idMatch);
			final DoadorNacional doadorNacional = (DoadorNacional) match.getDoador();
			
			if (verificaSeExisteSolicitacaoEmAberto(doadorNacional)) {
				throw new BusinessException("erro.solicitacao.em.aberto", "doador");
			}
			Solicitacao solicitacao = criarSolicitacaoFase2Nacional(match, tipoExame, doadorNacional.getLaboratorio());
			PedidoEnriquecimento pedidoEnriquecimento  = pedidoEnriquecimentoService.criarPedidoEnriquecimento(solicitacao, match.getBusca().getPaciente().getRmr());
			if (pedidoEnriquecimento == null) {
				pedidoContatoService.criarPedidoContato(solicitacao);
			}
		}
		finally {
			LOG.info("Fim da rotina para Solicitar Fase 2 nacional para o Match: " + idMatch);
		}		
	}

	private Solicitacao criarSolicitacaoFase2Nacional(Match match, TiposExame tipoExame, Laboratorio laboratorio) {
		Solicitacao solicitacao = new Solicitacao();
		solicitacao.setMatch(match);
		solicitacao.setDataCriacao(LocalDateTime.now());
		solicitacao.setTipoSolicitacao(new TipoSolicitacao(TiposSolicitacao.FASE_2.getId()));
		solicitacao.setStatus(StatusSolicitacao.ABERTA.getId());
		solicitacao.setTipoExame(new TipoExame(tipoExame.getId()));
		solicitacao.setLaboratorio(laboratorio);
		return save(solicitacao);
	}

	@Override
	public void solicitarFase2Internacional(Long idMatch, List<Locus> locusSolicitados) {
		LOG.info("Início da rotina para Solicitar Fase 2 internacional para o Match: " + idMatch);
		try {
			if (idMatch == null) {
				throw new BusinessException("erro.parametros.invalidos");
			}
			Match match = matchService.findById(idMatch);
			if (match == null) {
				LOG.info("Não foi encontrado o match com id: " + idMatch);
				throw new BusinessException("erro.solicitacao.match_inexistente");
			}
			if (verificaSeExisteSolicitacaoEmAberto(match.getDoador())) {
				throw new BusinessException("erro.solicitacao.em.aberto", "doador");
			}
			
			Solicitacao solicitacao = criarSolicitacaoFase2Internacional(match);
			
			pedidoExameService.criarPedidoFase2Internacional(solicitacao, locusSolicitados);			
			//matchService.atualizarFaseMatch(match.getId(), FasesMatch.EXAME_EXTENDIDO);
			
		}
		finally {
			LOG.info("Fim da rotina para Solicitar Fase 2 internacional para o Match: " + idMatch);
		}		
	}
	
	private Solicitacao criarSolicitacaoFase2Internacional(Match match) {
		Solicitacao solicitacao = new Solicitacao();		
		solicitacao.setMatch(match);
		solicitacao.setDataCriacao(LocalDateTime.now());
		solicitacao.setTipoSolicitacao(new TipoSolicitacao(TiposSolicitacao.FASE_2_INTERNACIONAL.getId()));
		solicitacao.setStatus(StatusSolicitacao.ABERTA.getId());
		return save(solicitacao);
	}

//	@CreateLog(TipoLogEvolucao.SOLICITADO_EXAME_FASE3_PARA_DOADOR_NACIONAL)
	@Override
	public void solicitarFase3Nacional(Long idMatch, Long idLaboratorio, Boolean resolverDivergencia) {
		LOG.info("Início da rotina para Solicitar Fase 3 nacional para o Match: " + idMatch);
		try {
			if (idMatch == null || idLaboratorio == null) {
				throw new BusinessException("erro.parametros.invalidos");
			}
			Match match = matchService.findById(idMatch);
			if (match == null) {
				LOG.info("Não foi encontrado o match com id: " + idMatch);
				throw new BusinessException("erro.solicitacao.match_inexistente");
			}
			Laboratorio laboratorio = laboratorioService.findById(idLaboratorio);
			if (laboratorio == null) {
				LOG.info("Não foi encontrado o laboratório com id: " + idLaboratorio);
				throw new BusinessException("erro.solicitacao.laboratorio_inexistente");
			}
			if (verificaSeExisteSolicitacaoEmAberto(match.getDoador())) {
				throw new BusinessException("erro.solicitacao.em.aberto", "doador");
			}
			if (verificaSeExistePedidoCTParaOdoador(match.getDoador().getId())) {
				throw new BusinessException("pedido.exame.ct.com.resultado.ja.existe.doador.falha");
			}
			Solicitacao solicitacao = criarSolicitacaoFase3Nacional(match, laboratorio, resolverDivergencia);
			PedidoEnriquecimento pedidoEnriquecimento  = pedidoEnriquecimentoService.criarPedidoEnriquecimento(solicitacao, match.getBusca().getPaciente().getRmr());
			if (pedidoEnriquecimento == null) {
				pedidoContatoService.criarPedidoContato(solicitacao);
			}
		}
		finally {
			LOG.info("Fim da rotina para Solicitar Fase 3 internacional para o Match: " + idMatch);
		}
	}
	
	/**
	 * Métod para criar uma solicitação de fase 3 nacional.
	 * 
	 * @param match
	 * @return solicitacao criada
	 */
	private Solicitacao criarSolicitacaoFase3Nacional(Match match, Laboratorio laboratorio, Boolean resolverDivergencia) {
		Solicitacao solicitacao = new Solicitacao();
		solicitacao.setMatch(match);
		solicitacao.setDataCriacao(LocalDateTime.now());
		solicitacao.setTipoSolicitacao(new TipoSolicitacao(TiposSolicitacao.FASE_3.getId()));
		solicitacao.setStatus(StatusSolicitacao.ABERTA.getId());
		solicitacao.setLaboratorio(laboratorio);
		solicitacao.setResolverDivergencia(resolverDivergencia);
		solicitacao.setTipoExame(new TipoExame(TiposExame.TESTE_CONFIRMATORIO.getId()));
		return save(solicitacao);
	}
	
	@Override
	public void solicitarFase3Paciente(Long idBusca, Long idLaboratorio, Long idTipoExame) {
		LOG.info("Início da rotina para Solicitar Fase 3 paciente para a busca: " + idBusca);
		try {
			if (idBusca == null) {
				throw new BusinessException("erro.parametros.invalidos");
			}
			Busca busca = buscaService.findById(idBusca);
			if (busca == null) {
				LOG.info("Não foi encontrada a busca com id: " + idBusca);
				throw new BusinessException("erro.solicitacao.busca_inexistente");
			}
			if (verificaSeExisteSolcitacaoEmAbertoParaOPaciente(busca.getPaciente().getRmr(), TiposSolicitacao.FASE_3)) {
				throw new BusinessException("erro.solicitacao.em.aberto", "paciente");
			}
			if (verificaSeExistePedidoCTParaOPaciente(busca.getPaciente().getRmr())) {
				throw new BusinessException("pedido.exame.ct.com.resultado.ja.existe.paciente.falha");
			}
			Solicitacao solicitacao = criarSolicitacaoFase3Paciente(busca);
			pedidoExameService.criarPedidoFase3Paciente(solicitacao, idLaboratorio, idTipoExame);

		}
		finally {
			LOG.info("Fim da rotina para Solicitar Fase 3 paciente para a busca: " + idBusca);
		}
	}
	
	/**
	 * Métod para criar uma solicitação de fase 3 para um paciente.
	 * 
	 * @param busca
	 * @return solicitacao criada
	 */
	private Solicitacao criarSolicitacaoFase3Paciente(Busca busca) {
		Solicitacao solicitacao = new Solicitacao();
		solicitacao.setBusca(busca);
		solicitacao.setDataCriacao(LocalDateTime.now());
		solicitacao.setTipoSolicitacao(new TipoSolicitacao(TiposSolicitacao.FASE_3.getId()));
		solicitacao.setStatus(StatusSolicitacao.ABERTA.getId());
		return save(solicitacao);
	}

	/**
	 * Verifica se existe solicitação em aberto para o paciente independente do tipo.
	 * 
	 * @param rmr - identificador do paciente
	 * @return true se o paciente possuir solicitacao e false se não possuir.
	 */
	private boolean verificaSeExisteSolcitacaoEmAbertoParaOPaciente(Long rmr, TiposSolicitacao tipoSolicitacao) {
		List<Solicitacao> solicitacoes =  solicitacaoRepository.solicitacoesEmAbertoPorPacienteETipoSolicitacao(rmr, tipoSolicitacao.getId());		
		return !solicitacoes.isEmpty();
	}
	
	/**
	 * Verifica se existe Pedido de CT com resultado cadastrado para o doador.
	 * 
	 * @param idDoador - id do doador
	 * @return true se o doador possuir pedido de exame de ct com resultado cadastrado.
	 */
	private boolean verificaSeExistePedidoCTParaOPaciente(Long rmr) {
		return pedidoExameService.temPedidoExameCtParaPacienteComResultadoCadastrado(rmr);
	}
	
	@Override
	public Long obterIdSolicitacaoPorIdMatch(Long idMatch) {
		return solicitacaoRepository.obterIdSolicitacaoPorIdMatch(idMatch);
	}

	@Override
	public Long obterQuantidadeSolicitacoesAbertosPorIdBuscaAndTiposDoador(Long idBusca, List<TiposDoador> tiposDoador) {
		List<Long> tipos = tiposDoador.stream().map(tipoDoador -> tipoDoador.getId()).collect(Collectors.toList());		
		
		return solicitacaoRepository
				.obterQuantidadeSolicitacoesAbertosPorIdBuscaAndTiposDoador(idBusca, tipos);
	}

	@Override
	public Solicitacao solicitarPedidoDeEnvioPacienteEmdis(Busca busca) {
		Solicitacao solicitacao = new Solicitacao();
		solicitacao.setBusca(busca);
		solicitacao.setDataCriacao(LocalDateTime.now());
		solicitacao.setTipoSolicitacao(this.tipoSolicitacaoRepository.getOne(TiposSolicitacao.ENVIO_PACIENTE_EMDIS.getId()));
		solicitacao.setStatus(StatusSolicitacao.ABERTA.getId());
		return save(solicitacao);
	}

	/**
	 * @param pedidoContatoService the pedidoContatoService to set
	 */
	@Autowired
	public void setPedidoContatoService(IPedidoContatoService pedidoContatoService) {
		this.pedidoContatoService = pedidoContatoService;
	}

	/**
	 * @param pedidoEnriquecimentoService the pedidoEnriquecimentoService to set
	 */
	@Autowired
	public void setPedidoEnriquecimentoService(IPedidoEnriquecimentoService pedidoEnriquecimentoService) {
		this.pedidoEnriquecimentoService = pedidoEnriquecimentoService;
	}

	/**
	 * @param pedidoColetaService the pedidoColetaService to set
	 */
	@Autowired
	public void setPedidoColetaService(IPedidoColetaService pedidoColetaService) {
		this.pedidoColetaService = pedidoColetaService;
	}

	/**
	 * @param pedidoExameService the pedidoExameService to set
	 */
	@Autowired
	public void setPedidoExameService(IPedidoExameService pedidoExameService) {
		this.pedidoExameService = pedidoExameService;
	}

	/**
	 * @param genotipoDoadorNacionalService the genotipoDoadorNacionalService to set
	 */
	@Autowired
	public void setGenotipoDoadorNacionalService(
			IGenotipoDoadorService<ExameDoadorNacional> genotipoDoadorNacionalService) {
		this.genotipoDoadorNacionalService = genotipoDoadorNacionalService;
	}

	/**
	 * @param genotipoDoadorInternacionalService the genotipoDoadorInternacionalService to set
	 */
	@Autowired
	public void setGenotipoDoadorInternacionalService(
			IGenotipoDoadorService<ExameDoadorInternacional> genotipoDoadorInternacionalService) {
		this.genotipoDoadorInternacionalService = genotipoDoadorInternacionalService;
	}

	/**
	 * @param pedidoWorkupService the pedidoWorkupService to set
	 */
	@Autowired
	public void setPedidoWorkupService(IPedidoWorkupService pedidoWorkupService) {
		this.pedidoWorkupService = pedidoWorkupService;
	}

	/**
	 * @param matchService the matchService to set
	 */
	@Autowired
	public void setMatchService(IMatchService matchService) {
		this.matchService = matchService;
	}

	/**
	 * @param buscaService the buscaService to set
	 */
	@Autowired
	public void setBuscaService(IBuscaService buscaService) {
		this.buscaService = buscaService;
	}

	/**
	 * @param pedidoIdmService the pedidoIdmService to set
	 */
	@Autowired
	public void setPedidoIdmService(IPedidoIdmService pedidoIdmService) {
		this.pedidoIdmService = pedidoIdmService;
	}

	/**
	 * @param laboratorioService the laboratorioService to set
	 */
	@Autowired
	public void setLaboratorioService(ILaboratorioService laboratorioService) {
		this.laboratorioService = laboratorioService;
	}

	@Override
	public String[] obterParametros(Solicitacao solicitacao) {
		return new String[] {((DoadorNacional) solicitacao.getMatch().getDoador()).getDmr().toString()};
	}

	@Override
	public Paciente obterPaciente(Solicitacao solicitacao) {
		if (solicitacao.getMatch() == null) {
			throw new BusinessException("erro.interno");
		}
		return solicitacao.getMatch().getBusca().getPaciente();
	}
	
	@Override
	public Long quantidadeSolicitacoesAbertasDosMatchsDaBuscaComTipoSolicitacao(Long idBusca, List<TiposSolicitacao> tiposSolicitacao) {
		final List<Long> tipos = tiposSolicitacao.stream().map(tipoSolicitacao -> tipoSolicitacao.getId()).collect(Collectors.toList());
		return solicitacaoRepository.quantidadeSolicitacoesEmAbertoDosMatchsPelaBuscaETipoSolicitacao(idBusca, tipos);
	}
	
	@Override
	public List<Solicitacao> listarSolicitacoesAbertasDosMatchsDaBuscaComTipoSolicitacao(Long idBusca, List<TiposSolicitacao> tiposSolicitacao) {
		final List<Long> tipos = tiposSolicitacao.stream().map(tipoSolicitacao -> tipoSolicitacao.getId()).collect(Collectors.toList());
		return solicitacaoRepository.listarSolicitacoesEmAbertoDosMatchsPelaBuscaETipoSolicitacao(idBusca, tipos);
	}

	@Override
	public void fecharSolicitacao(Long idSolicitacao) {
		Solicitacao solicitacao = obterSolicitacaoPorId(idSolicitacao);
		solicitacao.setStatus(StatusSolicitacao.CONCLUIDA.getId());
		save(solicitacao);
	}

	private Solicitacao obterSolicitacaoPorId(Long idSolicitacao) {		
		return solicitacaoRepository.findById(idSolicitacao).orElseThrow(() -> new BusinessException(""));
	}
	
	@Override
	public Solicitacao criarSolicitacaoWorkupDoadorNacionalPacienteNacional(Long idMatch) {
		LOG.info("Inicio da criação da solicitação workup de doador nacional para paciente nacional.");
		Match match = matchService.obterMatchDisponibilizado(idMatch);
				
		if (naoPodeCriarSolicitacaoWorkupMedula(match.getBusca().getId()) ) {
			throw new BusinessException("erro.solicitacao.workup.doador.nacional.paciente.nacional.em.aberto", match.getBusca().getPaciente().getRmr().toString(), match.getDoadorPelaInterface().getIdentificacao().toString() );
		}
		
		LOG.info("Fim da criação da solicitação workup de doador nacional para paciente nacional.");
		return criarSolicitacao(match, TiposSolicitacao.WORKUP_DOADOR_NACIONAL_PACIENTE_NACIONAL);
			
	}
	
	@Override
	public Solicitacao criarSolicitacaoWorkupDoadorInternacional(Long idMatch) {
		LOG.info("Inicio da criação da solicitação workup de doador internacional para paciente nacional.");
		Match match = matchService.obterMatchDisponibilizado(idMatch);
				
		if (naoPodeCriarSolicitacaoWorkupMedula(match.getBusca().getId()) ) {
			throw new BusinessException("erro.solicitacao.workup.doador.nacional.paciente.nacional.em.aberto", match.getBusca().getPaciente().getRmr().toString(), match.getDoadorPelaInterface().getIdentificacao().toString() );
		}
		
		LOG.info("Fim da criação da solicitação workup de doador internacional para paciente nacional.");
		return criarSolicitacao(match, TiposSolicitacao.WORKUP_DOADOR_INTERNACIONAL);
				
	}
	
	private Boolean naoPodeCriarSolicitacaoWorkupMedula(Long idBusca) {
		
		final Long quantidade = quantidadeSolicitacoesAbertasDosMatchsDaBuscaComTipoSolicitacao(idBusca, 
				Arrays.asList(TiposSolicitacao.WORKUP_DOADOR_NACIONAL_PACIENTE_NACIONAL, TiposSolicitacao.CORDAO_NACIONAL_PACIENTE_NACIONAL, 
						TiposSolicitacao.WORKUP_DOADOR_INTERNACIONAL, TiposSolicitacao.CORDAO_INTERNACIONAL));
		return quantidade.intValue() >= 1;
	}
	
	@Override
	public Solicitacao criarSolicitacaoWorkupCordaoNacionalPacienteNacional(Long idMatch) {
		LOG.info("Inicio da criação da solicitação workup de cordão nacional para paciente nacional.");
		Match match = matchService.obterMatchDisponibilizado(idMatch);
				
		if (naoPodeCriarSolicitacaoWorkupCordao(match.getBusca().getId()) ) {
			throw new BusinessException("erro.solicitacao.workup.doador.nacional.paciente.nacional.em.aberto", match.getBusca().getPaciente().getRmr().toString(), match.getDoadorPelaInterface().getIdentificacao().toString() );
		}
		LOG.info("Fim da criação da solicitação workup de cordão nacional para paciente nacional.");
				
		return criarSolicitacao(match, TiposSolicitacao.CORDAO_NACIONAL_PACIENTE_NACIONAL);				
	}
	
	@Override
	public Solicitacao criarSolicitacaoWorkupCordaoInternacional(Long idMatch) {
		LOG.info("Inicio da criação da solicitação workup de cordão internacional para paciente nacional.");
		Match match = matchService.obterMatchDisponibilizado(idMatch);
		
		if (naoPodeCriarSolicitacaoWorkupCordao(match.getBusca().getId()) ) {
			throw new BusinessException("erro.solicitacao.workup.doador.nacional.paciente.nacional.em.aberto", match.getBusca().getPaciente().getRmr().toString(), match.getDoadorPelaInterface().getIdentificacao().toString() );
		}
				
		LOG.info("Fim da criação da solicitação workup de cordão internacional para paciente nacional.");
		return criarSolicitacao(match, TiposSolicitacao.CORDAO_INTERNACIONAL);
	}
	
	private Boolean naoPodeCriarSolicitacaoWorkupCordao(Long idBusca) {
		
		final Long quantidade = quantidadeSolicitacoesAbertasDosMatchsDaBuscaComTipoSolicitacao(idBusca, 
				Arrays.asList(TiposSolicitacao.WORKUP_DOADOR_NACIONAL_PACIENTE_NACIONAL,  
						TiposSolicitacao.WORKUP_DOADOR_INTERNACIONAL));
		if (quantidade.intValue() == 1) {
			return true;
		}
		
		Long quantidadePrescricao = quantidadeSolicitacoesAbertasDosMatchsDaBuscaComTipoSolicitacao(idBusca, Arrays.asList(TiposSolicitacao.CORDAO_NACIONAL_PACIENTE_NACIONAL, TiposSolicitacao.CORDAO_INTERNACIONAL));
		return quantidadePrescricao.intValue() == 2;
		
	}
		
	private Solicitacao criarSolicitacao(Match match, TiposSolicitacao tipoSolicitacao) {
		if (match.getBusca().getCentroTransplante() == null) {
			throw new BusinessException("erro.solicitacao.workup.criar");
		}
		
		Solicitacao solicitacao = new Solicitacao();
		solicitacao.setMatch(match);
		solicitacao.setTipoSolicitacao(new TipoSolicitacao(tipoSolicitacao.getId()));
		solicitacao.setFaseWorkup(FasesWorkup.AGUARDANDO_AVALIACAO_PRESCRICAO.getId());
		solicitacao.setCentroTransplante(match.getBusca().getCentroTransplante());
		
		return save(solicitacao);
	}
	
	@Override
	public Solicitacao obterSolicitacao(Long id) {
		if (id == null) {
			throw new BusinessException("erro.id.nulo");
		}
		return solicitacaoRepository.findById(id).orElseThrow(() -> new BusinessException("erro.solicitacao.nao.existente")); 
	}

	@Override
	public Paginacao<Solicitacao> listarSolicitacoesPorCentroTransplanteEStatusSolicitacao(Long idCentroTransplante, String[] statusSolicitacao, Pageable pageable) {
		if (idCentroTransplante == null) {
			throw new BusinessException("erro.id.nulo");
		}
		List<Integer> listaStatus = Stream.of(statusSolicitacao).map(Integer::valueOf).collect(Collectors.toList());
		
		List<Long> tipos = Arrays.asList(TiposSolicitacao.WORKUP.getId(), TiposSolicitacao.WORKUP_CORDAO.getId());
		
		Page<Solicitacao> pageImpl = solicitacaoRepository.listarSolicitacoesPeloCentroTransplanteETipoEStatusSolicitacao(idCentroTransplante, tipos, listaStatus, pageable);
		
		return  new Paginacao<>(pageImpl, pageImpl.getPageable());
	}
	
	@Override
	public List<Solicitacao> listarSolicitacoesPorTiposSolicitacaoEStatus(String[] tipos, String[] status) {
		if (tipos == null || tipos.length == 0 || status == null || status.length == 0) {
			throw new BusinessException("erro.parametros.invalidos");
		}
		List<Long> tiposSolicitacao = Stream.of(tipos).map(tipo -> Long.parseLong(tipo)).collect(Collectors.toList());
		
		List<Integer> statusSolicitacao = Stream.of(status).map(idStatus -> Integer.parseInt(idStatus)).collect(Collectors.toList());
		
		return solicitacaoRepository.listarSolicitacoesPorTipoSolicitacaoEStatusSolicitacao(tiposSolicitacao, statusSolicitacao);
	}

	@Override
	public List<Solicitacao> listarSolicitacoesPorTiposSolicitacaoPorStatusEFasesWorkup(String[] tipos, String[] status, String[] fases) {
		if (tipos == null || tipos.length == 0 || status == null || status.length == 0 || fases == null || fases.length == 0) {
			throw new BusinessException("erro.parametros.invalidos");
		}
		List<Long> tiposSolicitacao = Stream.of(tipos).map(tipo -> Long.parseLong(tipo)).collect(Collectors.toList());
		
		List<Integer> statusSolicitacao = Stream.of(status).map(idStatus -> Integer.parseInt(idStatus)).collect(Collectors.toList());

		List<Long> fasesWorkup = Stream.of(fases).map(fase -> Long.parseLong(fase)).collect(Collectors.toList());
		
		return solicitacaoRepository.listarSolicitacoesPorTipoSolicitacaoPorStatusSolicitacaoEFasesWorkup(tiposSolicitacao, statusSolicitacao, fasesWorkup);
	}
	
	@Override
	public Solicitacao atribuirUsuarioResponsavel(Long id, Long idUsuario) {
		Solicitacao solicitacao = obterSolicitacao(id);
		Usuario usuario = validarUsuarioResponsavel(idUsuario);
		solicitacao.setUsuarioResponsavel(usuario);
				
		return solicitacaoRepository.save(solicitacao);
	}

	/**
	 * @param idUsuario
	 * @return
	 */
	private Usuario validarUsuarioResponsavel(Long idUsuario) {
		if (idUsuario == null) {
			throw new BusinessException("erro.mensagem.usuario.id.invalido");
		}
		Usuario usuario = usuarioService.obterUsuario(idUsuario);
		if (usuario == null) {
			throw new BusinessException("erro.mensagem.usuario.nao.encontrado");
		}
		return usuario;
	}
	
	@Override
	public Solicitacao atualizarFaseWorkup(Long id, Long idFaseWorkup) {
		Solicitacao solicitacao = obterSolicitacao(id);
		validarFaseWorkup(idFaseWorkup);
		solicitacao.setFaseWorkup(idFaseWorkup);
		return solicitacaoRepository.save(solicitacao);
	}

	private void validarFaseWorkup(Long idFaseWorkup) {
		if (idFaseWorkup == null) {
			throw new BusinessException("erro.id.nulo");
		}
		if (Stream.of(FasesWorkup.values()).noneMatch(faseWorkup -> faseWorkup.getId().equals(idFaseWorkup))) {
			throw new BusinessException("mensagem.nenhuma.registro.encontrado", "fase workup");
		}
		
		
	}
	
	@Override
	public Solicitacao atualizarSolicitacaoPosColeta(Long id, Long posColeta) {
		Solicitacao solicitacao = obterSolicitacao(id);
		validarPosColeta(posColeta);
		solicitacao.setPosColeta(posColeta);		
		return solicitacaoRepository.save(solicitacao);
	}	
	
	
	@Override
	public Solicitacao atribuirCentroColeta(Long id, Long idCentroColeta) {
		Solicitacao solicitacao = obterSolicitacao(id);
		if (solicitacao.getCentroColeta() != null) {
			throw new BusinessException("erro.solicitacao.workup.centro.coleta.ja.definido");
		}
		CentroTransplante centroColeta = centroTransplanteService.obterCentroTransplante(idCentroColeta);		
		solicitacao.setCentroColeta(centroColeta);
		
		return solicitacaoRepository.save(solicitacao);
	}
	
	private void validarPosColeta(Long posColeta) {
		if (posColeta == null) {
			throw new BusinessException("erro.id.nulo");
		}				
		
	}

	@Override
	public Solicitacao atualizarSolicitacaoContagemCelula(Long id) {
		Solicitacao solicitacao = obterSolicitacao(id);
		solicitacao.setContagemCelula(1L);		
		return solicitacaoRepository.save(solicitacao);
	}	
	
	
	

}
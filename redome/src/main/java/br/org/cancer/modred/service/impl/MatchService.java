package br.org.cancer.modred.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.modred.controller.dto.MatchDTO;
import br.org.cancer.modred.controller.dto.MatchWmdaDTO;
import br.org.cancer.modred.exception.BusinessException;
import br.org.cancer.modred.feign.client.ITarefaFeign;
import br.org.cancer.modred.feign.dto.TarefaDTO;
import br.org.cancer.modred.model.Busca;
import br.org.cancer.modred.model.ComentarioMatch;
import br.org.cancer.modred.model.CordaoInternacional;
import br.org.cancer.modred.model.CordaoNacional;
import br.org.cancer.modred.model.Doador;
import br.org.cancer.modred.model.DoadorInternacional;
import br.org.cancer.modred.model.DoadorNacional;
import br.org.cancer.modred.model.Exame;
import br.org.cancer.modred.model.IDoador;
import br.org.cancer.modred.model.Match;
import br.org.cancer.modred.model.Paciente;
import br.org.cancer.modred.model.PedidoExame;
import br.org.cancer.modred.model.StatusDoador;
import br.org.cancer.modred.model.annotations.log.CreateLog;
import br.org.cancer.modred.model.domain.CategoriasNotificacao;
import br.org.cancer.modred.model.domain.FasesMatch;
import br.org.cancer.modred.model.domain.FiltroMatch;
import br.org.cancer.modred.model.domain.Perfis;
import br.org.cancer.modred.model.domain.StatusTarefa;
import br.org.cancer.modred.model.domain.TipoLogEvolucao;
import br.org.cancer.modred.model.domain.TiposBuscaChecklist;
import br.org.cancer.modred.model.domain.TiposDoador;
import br.org.cancer.modred.model.domain.TiposExame;
import br.org.cancer.modred.model.domain.TiposSolicitacao;
import br.org.cancer.modred.model.domain.TiposTarefa;
import br.org.cancer.modred.persistence.IMatchRepository;
import br.org.cancer.modred.persistence.IRepository;
import br.org.cancer.modred.service.IBuscaChecklistService;
import br.org.cancer.modred.service.IBuscaService;
import br.org.cancer.modred.service.IComentarioMatchService;
import br.org.cancer.modred.service.IDoadorNacionalService;
import br.org.cancer.modred.service.IExecutarProcedureMatchService;
import br.org.cancer.modred.service.IMatchService;
import br.org.cancer.modred.service.IPacienteService;
import br.org.cancer.modred.service.IPedidoExameService;
import br.org.cancer.modred.service.ISolicitacaoService;
import br.org.cancer.modred.service.IValorGenotipoDoadorService;
import br.org.cancer.modred.service.impl.config.Equals;
import br.org.cancer.modred.service.impl.config.Projection;
import br.org.cancer.modred.service.impl.invocation.AbstractLoggingService;
import br.org.cancer.modred.util.AppUtil;
import br.org.cancer.modred.util.MatchDTOUtil;

/**
 * Implementação de classe de negócios de Match.
 * 
 * @author Filipe Paes
 *
 */
@Service
@Transactional(propagation=Propagation.REQUIRED)
public class MatchService extends AbstractLoggingService<Match, Long> implements IMatchService {
	
	private static final Logger LOG = LoggerFactory.getLogger(MatchService.class);

	private IBuscaService buscaService;

	@Autowired
	private IMatchRepository matchRepository;

	private IDoadorNacionalService doadorService;
	
	private IComentarioMatchService comentarioMatchService;
	
	private IPacienteService pacienteService;
	
	private IPedidoExameService pedidoExameService;
	
	@Autowired
	@Lazy(true)
	private ITarefaFeign tarefaFeign;
	
	@Autowired
	private IBuscaChecklistService buscaChecklistService;
	
	@Autowired
	private IValorGenotipoDoadorService<Exame> valorGenotipoService;
	
	private ISolicitacaoService solicitacaoService;
	
	private IExecutarProcedureMatchService executarProcedureMatchService;
	
	/**
	 * Construtor definido para que seja informado as estratégias
	 * para os eventos de criação de notificação.
	 */
	public MatchService() {
		super();		
	}
	
	@Override
	public IRepository<Match, Long> getRepository() {
		return matchRepository;
	}

	@CreateLog(TipoLogEvolucao.OCORREU_MATCH)
	@Override
	public void criarMatch(Long rmr, Long idDoador) {
		Busca buscaAtivaPorRmr = buscaService.obterBuscaAtivaPorRmr(rmr);
		if (buscaAtivaPorRmr == null) {
			throw new BusinessException("erro.match.busca.nao_existe_busca_para_este_paciente");
		}
		Match matchBusca = obterMatchAtivo(rmr, idDoador);
							
		
		if (matchBusca != null) {
			throw new BusinessException("erro.match.match_ja_existente");
		}
		Doador doador = doadorService.findById(idDoador);
		Match match = new Match();
		match.setBusca(buscaAtivaPorRmr);
		match.setDoador(doador);
		match.setStatus(true);
		
		save(match);
	}

	/**
	 * {@inheritDoc}
	 */	
	@Override
	public List<MatchDTO> buscarListaMatchPorSituacao(Long rmr, List<FasesMatch> situacoes, FiltroMatch filtro, Boolean matchAtivo, Boolean disponibilizado) {
		List<TiposDoador> tiposDoador = filtro.getTiposDoadorAssociados();
		
		List<Object[]> matchs = matchRepository.listarMatchPorPacienteSituacaoTipoDoadorStatus(rmr, situacoes, tiposDoador, matchAtivo, disponibilizado); 
				
		if (matchs != null && !matchs.isEmpty()) {

			return matchs.stream().map(MatchDTOUtil::popularMatchDTO).collect(Collectors.toList());

		}
		return new ArrayList<>();
	}
	
	@Override
	public MatchDTO obterMatchPorId(Long id) {
	
		Object[] match = matchRepository.obterMatchPorId(id); 
				
		if (match != null) {

			return MatchDTOUtil.popularMatchDTO(match);

		}
		throw new BusinessException("erro.interno");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ComentarioMatch> listarComentarios(Long idMatch) {
		return comentarioMatchService.find(new Equals<Long>("match.id", idMatch));
	}

	@Override
	public Match obterMatchPorPedidoWorkup(Long pedidoWorkupId) {
		return matchRepository.obterMatchPorPedidoWorkup(pedidoWorkupId);
	}

	@Override
	public Match obterMatchPorPedidoContato(Long pedidoContatoId) {
		return matchRepository.obterMatchPorPedidoContato(pedidoContatoId);
	}
	
	@Override
	public List<MatchDTO> listarMatchsAtivosPorRmrAndListaIdsDoador(Long rmr, List<Long> listaIdsDoador) {
		List<Object[]> matchs = matchRepository.listarMatchsPorPacienteStatusIdDoador(rmr,	true, listaIdsDoador);
		return matchs.stream().map(MatchDTOUtil::popularMatchDTO).collect(Collectors.toList());
		
	}

	@Override
	public Match obterMatchPorPedidoExame(Long pedidoExameId) {
		return matchRepository.obterMatchPorPedidoExame(pedidoExameId);
	}
	
	/**
	 * Dependendo da fase que se deseja migrar, verifica se existem mesmo 
	 * pedidos nesta fase em andamento para autorizar ou não a progressão de fase.
	 * 
	 * @param match match associado a lista de pedidos de exame.
	 * @param fase fase que se deseja progredir.
	 * @return TRUE caso seja possível progredir.
	 */
	private boolean verificarSePodeProgredirFase(Match match, FasesMatch fase) {
		if(FasesMatch.FASE_3.getChave().equals(match.getSituacao())){
			return true;
		}
		List<PedidoExame> pedidosExameDoDoador = 
				pedidoExameService.listarPedidoExamePorDoadorEmAndamento(match.getId());
		boolean podeProgredir = false;
		final boolean isDoadorNacional = TiposDoador.NACIONAL.equals(match.getDoador().getTipoDoador());
		if(isDoadorNacional){
			return true;
		}
		
		if(CollectionUtils.isNotEmpty(pedidosExameDoDoador)){
			for (PedidoExame pedidoExame : pedidosExameDoDoador) {
				boolean fase2Andamento = 
						FasesMatch.EXAME_EXTENDIDO.getChave().equals(fase.getChave()) 
							&& TiposExame.TIPIFICACAO_HLA_ALTA_RESOLUCAO.getId().equals(pedidoExame.getTipoExame().getId());
				
				boolean ctAndamento = 
						FasesMatch.TESTE_CONFIRMATORIO.getChave().equals(fase.getChave()) 
							&& TiposExame.TESTE_CONFIRMATORIO.getId().equals(pedidoExame.getTipoExame().getId());
				
				if(fase2Andamento || ctAndamento){
					podeProgredir = true;
				}
			}
		}
		return podeProgredir;
	}
	

	/**
	 * Disponiliza o doador para prescrição.
	 * 
	 * @param solicitacaoDTO
	 * @param match
	 */
	@CreateLog(TipoLogEvolucao.DOADOR_DISPONIBILIZADO)
	@Override
	public void disponibilizarDoador(Long idMatch) {		
		LOG.info("Início da rotina para disponibilizar o Match: " + idMatch);
		try {
			Match match = obterMatch(idMatch);
			match.setDisponibilizado(true);
			
			save(match);
			
			if (match.getBusca().getCentroTransplante() != null) {
				criarTarefaCadastrarPrescricao(match);	
				criarNotificacaoParaCentroTransplate(match);
			}
		}
		finally {
			LOG.info("Fim da rotina para disponibilizar o Match: " + idMatch);
		}
	}

	@SuppressWarnings("rawtypes")
	public void criarNotificacaoParaCentroTransplate(Match match) {

		IDoador doador = (IDoador) match.getDoador();
		
		CategoriasNotificacao.MATCH_DISPONIBILIZADO.getConfiguracao().criar()
			.comDescricao(AppUtil.getMensagem(messageSource, 
					"doasdor.disponibilizado.centrotransplante.notificacao", 
					match.getBusca().getPaciente().getRmr(), doador.getIdentificacao().toString()))
			.comPaciente(match.getBusca().getPaciente().getRmr())
			.paraPerfil(Perfis.MEDICO_TRANSPLANTADOR)
			.comParceiro(match.getBusca().getCentroTransplante().getId())
			.apply();
	}

	@Override
	public void criarTarefaCadastrarPrescricao(Match match) {
		final boolean existeSolicitacaoWorkupEmAberto = solicitacaoService.quantidadeSolicitacoesAbertasDosMatchsDaBuscaComTipoSolicitacao(match.getBusca().getId(), 
				Arrays.asList(TiposSolicitacao.WORKUP_DOADOR_NACIONAL_PACIENTE_NACIONAL, TiposSolicitacao.WORKUP_DOADOR_INTERNACIONAL)).intValue() != 0; 
		final boolean existeSolicitacaoWorkupCordaoEmAberto = solicitacaoService.quantidadeSolicitacoesAbertasDosMatchsDaBuscaComTipoSolicitacao(match.getBusca().getId(), 
				Arrays.asList(TiposSolicitacao.CORDAO_NACIONAL_PACIENTE_NACIONAL, TiposSolicitacao.CORDAO_INTERNACIONAL)).intValue() != 0;
		final Long quantidadeTarefaCadastrarPresricaoCordaoEmAberto = quantidadeTarefasCadastrarPrescricaoCordaoEmAberto(match.getBusca());

				
		if (match.getDoador().isMedula() && !existeSolicitacaoWorkupEmAberto && !existeSolicitacaoWorkupCordaoEmAberto ) {
			if (StatusDoador.isAtivo(match.getDoador().getStatusDoador().getId()) || quantidadeMedulasDisponibilizadosParaBusca(match.getBusca()) != 0) {
				criarTarefaCadastrarPrescricaoMedula(match.getBusca());
			}
		}
		else if (match.getDoador().isCordao() ) {
			if (StatusDoador.isAtivo(match.getDoador().getStatusDoador().getId()) || quantidadeCordoesDisponibilizadosParaBusca(match.getBusca()) != 0) {
				if (!existeSolicitacaoWorkupEmAberto && !existeSolicitacaoWorkupCordaoEmAberto && quantidadeTarefaCadastrarPresricaoCordaoEmAberto.intValue() < 2) {
					criarTarefaCadastrarPrescricaoCordao(match.getBusca());
				}
				else if (!existeSolicitacaoWorkupEmAberto && existeSolicitacaoWorkupCordaoEmAberto && quantidadeTarefaCadastrarPresricaoCordaoEmAberto.intValue() == 0) {
					criarTarefaCadastrarPrescricaoCordao(match.getBusca());
				}
			}
		}
	
		
	}
	
	private int quantidadeMedulasDisponibilizadosParaBusca(Busca busca) {
		List<Match> matchsDisponibilizados = listarMatchsAtivosEDisponibilizadosPeloIdentificadorDaBusca(busca.getId());
		if (CollectionUtils.isNotEmpty(matchsDisponibilizados)) {
			return new Long( matchsDisponibilizados.stream().filter(match -> match.getDoador().isMedula())
				.count()).intValue();
		}
		
		return 0;
	}
	
	private int quantidadeCordoesDisponibilizadosParaBusca(Busca busca) {
		List<Match> matchsDisponibilizados = listarMatchsAtivosEDisponibilizadosPeloIdentificadorDaBusca(busca.getId());
		if (CollectionUtils.isNotEmpty(matchsDisponibilizados)) {
			return new Long( matchsDisponibilizados.stream().filter(match -> match.getDoador().isCordao())
				.count()).intValue();
		}
		
		return 0;
	}

	private Long quantidadeTarefasCadastrarPrescricaoCordaoEmAberto(Busca busca) {
			
		Page<TarefaDTO> tarefasEncontradas = TiposTarefa.CADASTRAR_PRESCRICAO_CORDAO.getConfiguracao().listarTarefa()
				.comRmr(busca.getPaciente().getRmr())
				.comStatus(Arrays.asList(StatusTarefa.ABERTA, StatusTarefa.ATRIBUIDA))
				.comParceiros(Arrays.asList(busca.getCentroTransplante().getId()))
				.apply().getValue();
		return new Long(tarefasEncontradas.getTotalElements());
		
	}
	
	
	private void criarTarefaCadastrarPrescricaoMedula(Busca busca) {
				
		TarefaDTO tarefaEncontrada = TiposTarefa.CADASTRAR_PRESCRICAO_MEDULA.getConfiguracao().obterTarefa()
				.comRmr(busca.getPaciente().getRmr())
				
				.comParceiros(Arrays.asList(busca.getCentroTransplante().getId()))
				.comStatus(Arrays.asList(StatusTarefa.ABERTA, StatusTarefa.ATRIBUIDA))
				.apply();
		if (tarefaEncontrada == null) {
			TiposTarefa.CADASTRAR_PRESCRICAO_MEDULA.getConfiguracao().criarTarefa()
				.comRmr(busca.getPaciente().getRmr())
				.comParceiro(busca.getCentroTransplante().getId())
				.apply();
			
		}
		
	}
	
	private void criarTarefaCadastrarPrescricaoCordao(Busca busca) {
				
		TiposTarefa.CADASTRAR_PRESCRICAO_CORDAO.getConfiguracao().criarTarefa()
			.comRmr(busca.getPaciente().getRmr())
			.comParceiro(busca.getCentroTransplante().getId())
			.apply();
			
	}
	
	@Override
	public Paciente obterPaciente(Match match) {
		return pacienteService.obterPacientePorMatch(match.getId());
	}

	@Override
	public String[] obterParametros(Match match) {
		
		String[] retorno = {null};
		
		if (match.getDoador() != null && match.getDoador() != null) {
			Doador doador = match.getDoador();
			if (doador instanceof DoadorNacional) {
				retorno[0] = ((DoadorNacional) doador).getDmr().toString();
			}
			else if(doador instanceof CordaoNacional) {
				retorno[0] = ((CordaoNacional) doador).obterIdBancoSangueCordaoFormatado();
			}
			else if (doador instanceof CordaoInternacional) {
				retorno[0] = ((CordaoInternacional)doador).getIdRegistro();
			}
			else if(doador instanceof DoadorInternacional) {
				retorno[0] = ((DoadorInternacional)doador).getIdRegistro();
			}
		}
		return retorno;
	}

	
	@Override
	public void executarProcedureMatchDoadorPorIdTarefa(Long idTarefa) {
		TarefaDTO tarefa = tarefaFeign.obterTarefa(idTarefa); 
		this.executarProcedureMatchDoadorPorIdDoador(tarefa.getRelacaoEntidade());
	}
	
	@Override
	public void executarProcedureMatchDoadorPorIdDoador(Long idDoador) {
		matchRepository.flush();		
		matchRepository.executarProcedureMatchDoador(idDoador);
	}
	
	@Override
	public void executarProcedureMatchPacientePorIdTarefa(Long idTarefa) {
		TarefaDTO tarefa = tarefaFeign.obterTarefa(idTarefa); 
		this.executarProcedureMatchPacientePorRmr(tarefa.getRelacaoEntidade());
	}

	@Override
	public void executarProcedureMatchPacientePorRmr(Long rmr) {
		matchRepository.flush();
		matchRepository.executarProcedureMatchPaciente(rmr);
	}
	
	@Override
	public Match obterMatchAtivo(Long rmr, Long idDoador) {
		return matchRepository.obterMatchAtivo(rmr, idDoador);
	}
	
	@Override
	public Doador obterDoador(Long matchId) {
		Match match = findOne(
				Arrays.asList(new Projection("doador")), Arrays.asList(new Equals<Long>("id", matchId)));
		if(match != null){
			return match.getDoador();
		}
		return null;
	}

	@Override
	public List<Match> listarMatchsAtivos(Long idDoador) {
		return matchRepository.listarMatchsAtivos(idDoador);
	}

	@Override
	public boolean cancelarMatchsAtivos(Long idDoador) {
		List<Match> matchsAtivos = listarMatchsAtivos(idDoador);
		
		if(CollectionUtils.isNotEmpty(matchsAtivos)){
			
			matchsAtivos.forEach(match -> match.setStatus(Boolean.FALSE));
			
			saveAll(matchsAtivos);
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}
	
	@Override
	public Long obterQuantidadeMatchsAtivosPorRmrAndTiposDoador(Long rmr, List<TiposDoador> tiposDoador) {
		List<Long> tipos = tiposDoador.stream().map(TiposDoador::getId).collect(Collectors.toList());
		
		return matchRepository
				.countByBuscaPacienteRmrAndDoadorTipoDoadorIsInAndStatus(rmr, tipos, true);
	}

	@Override
	public Integer obterQuantidadeMatchsAtivosPorBuscaIdAndTiposDoador(Long buscaId, List<TiposDoador> tiposDoador) {
		List<Long> tipos = tiposDoador.stream().map(TiposDoador::getId).collect(Collectors.toList());
		
		return matchRepository
				.countByBuscaIdAndDoadorTipoDoadorIsInAndStatus(buscaId, tipos, true);
	}
	
	@Override
	public Match obterMatch(Long id) {
		if (id == null) {
			throw new BusinessException("erro.parametros.invalidos");
		}		
		final Match match = findById(id);
		if (match == null) {
			throw new BusinessException("mensagem.nenhum.registro.encontrado", "match");
		}
		return match;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<MatchDTO> buscarListaMatchInativoComSolicitacao(Long rmr, String situacao, FiltroMatch filtro) {
		List<Object[]> matchs = matchRepository	.listarMatchInativosPorPacienteSituacaoTipoDoadorComSolicitacao(rmr, situacao, filtro.getTiposDoadorAssociados()); 

		if (matchs != null && !matchs.isEmpty()) {
			return matchs.stream().map(MatchDTOUtil::popularMatchDTO).collect(Collectors.toList());
		}
		return new ArrayList<>();
	}

	/**
	 * @param buscaService the buscaService to set
	 */
	@Autowired
	public void setBuscaService(IBuscaService buscaService) {
		this.buscaService = buscaService;
	}

	/**
	 * @param doadorService the doadorService to set
	 */
	@Autowired
	public void setDoadorService(IDoadorNacionalService doadorService) {
		this.doadorService = doadorService;
	}

	/**
	 * @param comentarioMatchService the comentarioMatchService to set
	 */
	@Autowired
	public void setComentarioMatchService(IComentarioMatchService comentarioMatchService) {
		this.comentarioMatchService = comentarioMatchService;
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
 
	@Override
	public Match obterMatchPorPedidoIdm(Long pedidoIdm) {
		return matchRepository.obterMatchPorPedidoIdm(pedidoIdm);
	}

	@Override
	public void criarCheckListParaMatchDivergente(Long idTarefa) { 
		TarefaDTO tarefa = tarefaFeign.obterTarefa(idTarefa); 
		Match match = this.findById(tarefa.getRelacaoEntidade());
		if (valorGenotipoService.existemValoresComDivergencia(match.getDoador().getId())) {		
			buscaChecklistService.criarItemCheckList(match.getBusca(), match, TiposBuscaChecklist.COBRANCA_LABORATORIO_DIVERGENCIA);
		}
		
	}
	
	@Override
	public List<Match> listarMatchsAtivosEDisponibilizadosPeloIdentificadorDaBusca(Long idBusca) {
		return matchRepository.listarMatchsDisponibilizadosPorIdBusca(idBusca);
	}

	/**
	 * @param solicitacaoService the solicitacaoService to set
	 */
	@Autowired
	public void setSolicitacaoService(ISolicitacaoService solicitacaoService) {
		this.solicitacaoService = solicitacaoService;
	}

	@Autowired
	public void setExecutarProcedureMatchService(IExecutarProcedureMatchService executarProcedureMatchService) {
		this.executarProcedureMatchService = executarProcedureMatchService;
	}
	
	@Override
	public Match obterMatchDisponibilizado(Long id) {
		final Match match = obterMatch(id);
		
		if (!match.getDisponibilizado()) {
			throw new BusinessException("erro.match.nao.disponibilizado", match.getBusca().getPaciente().getRmr().toString());
		}

		return match;
	}
	
	@Override
	public Match criarMatchWmda(MatchWmdaDTO matchWmdaDto) {
		
		Busca buscaAtiva = buscaService.obterBuscaAtivaPorId(matchWmdaDto.getIdBusca());
		if (buscaAtiva == null) {
			throw new BusinessException("erro.match.busca.nao_existe_busca_para_este_paciente");
		}

	//	Match matchBusca = obterMatchAtivo(buscaAtiva.getPaciente().getRmr(), matchWmdaDto.getIdDoador());
//		List<MatchDTO> matchBusca = listarMatchsAtivosPorRmrAndListaIdsDoador(buscaAtiva.getPaciente().getRmr(), Arrays.asList(matchWmdaDto.getIdDoador()));
//		if (matchBusca != null) {
//			throw new BusinessException("erro.match.match_ja_existente");
//		}
		
		Doador doador = doadorService.findById(matchWmdaDto.getIdDoador());
		Match novoMatch = new Match();
		novoMatch.setBusca(buscaAtiva);
		novoMatch.setDoador(doador);
		novoMatch.setStatus(true);
		novoMatch.setDataCriacao(LocalDateTime.now());
		novoMatch.setGrade(matchWmdaDto.getMatchGrade());
		novoMatch.setOrdenacaoWmdaMatch(obterValorOrdenacaoWmdaMatch(matchWmdaDto.getIdBusca(), matchWmdaDto.getTipoDoador()));
		novoMatch.setProbabilidade0(matchWmdaDto.getProbabilidade0());
		novoMatch.setProbabilidade1(matchWmdaDto.getProbabilidade1());
		novoMatch.setProbabilidade2(matchWmdaDto.getProbabilidade2());
		
		return save(novoMatch);
	}

	/**
	 * @param matchWmdaDto
	 */
	private Integer obterValorOrdenacaoWmdaMatch(Long buscaId, Long tipoDoador) {
		Integer ordem = 0;
		if(tipoDoador.equals(TiposDoador.NACIONAL.getId()) || 
				tipoDoador.equals(TiposDoador.INTERNACIONAL.getId())) {
			ordem = this.obterQuantidadeMatchsAtivosPorBuscaIdAndTiposDoador(buscaId, 
					Arrays.asList(TiposDoador.NACIONAL, TiposDoador.INTERNACIONAL));
		}else {
			ordem = this.obterQuantidadeMatchsAtivosPorBuscaIdAndTiposDoador(buscaId, 
					Arrays.asList(TiposDoador.CORDAO_NACIONAL, TiposDoador.CORDAO_INTERNACIONAL));
		}
		return ordem++;
	}


	@Override
	public Match atualizarMatchWmda(Match match, MatchWmdaDTO matchWmdaDto) {
		
		Busca buscaAtiva = buscaService.obterBuscaAtivaPorId(matchWmdaDto.getIdBusca());

		match.setBusca(buscaAtiva);
		match.setStatus(true);
		match.setDataAtualizacao(LocalDateTime.now());
		match.setGrade(matchWmdaDto.getMatchGrade());
		match.setOrdenacaoWmdaMatch(matchWmdaDto.getOrdenacaoWmdaMatch());
		match.setProbabilidade0(matchWmdaDto.getProbabilidade0());
		match.setProbabilidade1(matchWmdaDto.getProbabilidade1());
		match.setProbabilidade2(matchWmdaDto.getProbabilidade2());
		
		return save(match);
	}

}

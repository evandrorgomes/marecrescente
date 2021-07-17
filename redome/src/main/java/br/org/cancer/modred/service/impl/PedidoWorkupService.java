package br.org.cancer.modred.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.modred.controller.dto.AgendamentoWorkupDTO;
import br.org.cancer.modred.controller.dto.DadosCentroTransplanteDTO;
import br.org.cancer.modred.controller.page.TarefaJsonPage;
import br.org.cancer.modred.exception.BusinessException;
import br.org.cancer.modred.feign.dto.TarefaDTO;
import br.org.cancer.modred.helper.JsonViewPage;
import br.org.cancer.modred.model.CentroTransplante;
import br.org.cancer.modred.model.ContatoTelefonico;
import br.org.cancer.modred.model.Disponibilidade;
import br.org.cancer.modred.model.Doador;
import br.org.cancer.modred.model.DoadorInternacional;
import br.org.cancer.modred.model.FonteCelula;
import br.org.cancer.modred.model.IDoador;
import br.org.cancer.modred.model.MotivoCancelamentoWorkup;
import br.org.cancer.modred.model.Paciente;
import br.org.cancer.modred.model.PedidoWorkup;
import br.org.cancer.modred.model.Prescricao;
import br.org.cancer.modred.model.ResultadoWorkup;
import br.org.cancer.modred.model.Solicitacao;
import br.org.cancer.modred.model.StatusPedidoWorkup;
import br.org.cancer.modred.model.annotations.log.CreateLog;
import br.org.cancer.modred.model.domain.OrdenacaoTarefa;
import br.org.cancer.modred.model.domain.StatusPedidosWorkup;
import br.org.cancer.modred.model.domain.StatusTarefa;
import br.org.cancer.modred.model.domain.TipoLogEvolucao;
import br.org.cancer.modred.model.domain.TiposDoador;
import br.org.cancer.modred.model.domain.TiposSolicitacao;
import br.org.cancer.modred.model.domain.TiposTarefa;
import br.org.cancer.modred.model.security.Usuario;
import br.org.cancer.modred.persistence.IMotivoCancelamentoWorkupRepository;
import br.org.cancer.modred.persistence.IPedidoWorkupRepository;
import br.org.cancer.modred.persistence.IRepository;
import br.org.cancer.modred.service.ICentroTransplanteService;
import br.org.cancer.modred.service.IDisponibilidadeService;
import br.org.cancer.modred.service.IDoadorNacionalService;
import br.org.cancer.modred.service.IPacienteService;
import br.org.cancer.modred.service.IPedidoLogisticaService;
import br.org.cancer.modred.service.IPedidoWorkupService;
import br.org.cancer.modred.service.IPrescricaoService;
import br.org.cancer.modred.service.IResultadoWorkupService;
import br.org.cancer.modred.service.ISolicitacaoService;
import br.org.cancer.modred.service.IUsuarioService;
import br.org.cancer.modred.service.impl.config.Equals;
import br.org.cancer.modred.service.impl.config.Filter;
import br.org.cancer.modred.service.impl.config.UpdateSet;
import br.org.cancer.modred.service.impl.invocation.AbstractLoggingService;
import br.org.cancer.modred.util.AppUtil;
import br.org.cancer.modred.util.CampoMensagem;
import br.org.cancer.modred.util.PaginacaoUtil;

/**
 * Classe de negócios de para pedido workup.
 * 
 * @author Filipe Paes
 *
 */
@Service
@Transactional
public class PedidoWorkupService extends AbstractLoggingService<PedidoWorkup, Long> implements IPedidoWorkupService {

	private static final Logger LOGGER = LoggerFactory.getLogger(PedidoWorkupService.class);

	@Autowired
	private IPedidoWorkupRepository pedidoWorkupRepository;
	
	private IUsuarioService usuarioService;

	@Autowired
	private IMotivoCancelamentoWorkupRepository motivoCancelamentoWorkupRepository;

	private ISolicitacaoService solicitacaoService;
	
	private IResultadoWorkupService resultadoWorkupService;
	
	private IPedidoLogisticaService pedidoLogisticaService;
	
	private IPacienteService pacienteService;

	private IDoadorNacionalService doadorService;
	
	private IDisponibilidadeService disponibilidadeService;
	
	private IPrescricaoService prescricaoService;
		
	private ICentroTransplanteService centroTransplanteService;
	
	
	@Override
	public IRepository<PedidoWorkup, Long> getRepository() {
		return pedidoWorkupRepository;
	}

	/**
	 * {@inheritDoc}
	 */	
	@Override
	public PedidoWorkup criarPedidoWorkup(Solicitacao solicitacao) {
		Paciente paciente = pacienteService.obterPacientePorSolicitacao(solicitacao.getId());
		Long idDoador = doadorService.obterDoadorPorSolicitacao(solicitacao.getId());
		
		List<PedidoWorkup> pedidosWorkup = 
				pedidoWorkupRepository.listarPedidos(TiposSolicitacao.WORKUP.getId(),
						idDoador, paciente.getRmr(), Arrays.asList(
						StatusPedidosWorkup.AGUARDANDO_CT.getId(), StatusPedidosWorkup.EM_ANALISE.getId(),
						StatusPedidosWorkup.INICIAL.getId()));

		if (!pedidosWorkup.isEmpty()) {
			return null;
		}

		PedidoWorkup pedidoWorkup = new PedidoWorkup();
		pedidoWorkup.setDataUltimoStatus(LocalDateTime.now());
		pedidoWorkup.setSolicitacao(solicitacao);
		pedidoWorkup.setTentativasAgendamento(1);
		pedidoWorkup.setStatusPedidoWorkup(new StatusPedidoWorkup(StatusPedidosWorkup.INICIAL));
		pedidoWorkup.setDataCriacao(LocalDateTime.now());
		
		pedidoWorkup = save(pedidoWorkup);
		criarTarefaWorkup(solicitacao, pedidoWorkup);
		
		return pedidoWorkup;
	}
	
	/**
	 * Cria a tarefa para cadastrar o resultado do pedido de workup do doador.
	 * 
	 * @param solicitacao solicitação que envolve o pedido de workup.
	 * @param pedidoWorkup pedido de workup recém criado no banco.
	 */
	private void criarTarefaWorkup(Solicitacao solicitacao, PedidoWorkup pedidoWorkup) {
		LOGGER.debug("CRIANDO TAREFA DE PEDIDO DE WORKUP");
		final Paciente paciente = pacienteService.obterPacientePorSolicitacao(solicitacao.getId());
		final Doador doador = obterDoador(pedidoWorkup);
		
		if(TiposDoador.NACIONAL.getId().equals(doador.getIdTipoDoador())){
			TiposTarefa.PEDIDO_WORKUP.getConfiguracao()
				.criarTarefa()
				.comRmr(paciente.getRmr())
				.comObjetoRelacionado(pedidoWorkup.getId())
				.apply();
		}
		else if(TiposDoador.INTERNACIONAL.getId().equals(doador.getIdTipoDoador())){
			TiposTarefa.PEDIDO_WORKUP_INTERNACIONAL.getConfiguracao()
			.criarTarefa()
			.comRmr(paciente.getRmr())
			.comObjetoRelacionado(pedidoWorkup.getId())
			.apply();
		}
		else {
			LOGGER.error("Erro ao criar tarefa PEDIDO_WORKUP durante o agendamento.");
			throw new BusinessException("pedido.workup.erro.origem.doador.desconhecida");
		}
		
		LOGGER.info("Criou tarefa de workup para o pedido {}", pedidoWorkup.getId());
	}
	
	
	@Override
	public void cancelarPedidoWorkup(Long idPedido, Long idMotivoCancelamento) {
		PedidoWorkup pedido = obterPedidoWorkup(idPedido);
		solicitacaoService.cancelarSolicitacao(pedido.getSolicitacao().getId(), null, null, null, null);		
	}


	@Override
	public PedidoWorkup obterPedidoWorkup(Long idPedidoWorkup) {
		PedidoWorkup pedidoWorkup = pedidoWorkupRepository.findById(idPedidoWorkup).orElse(null);

		if (pedidoWorkup == null) {
			throw new BusinessException("pedido.workup.erro.nao.encontrado");
		}		
		pedidoWorkup.setUltimaDisponibilidade(disponibilidadeService.obterUltimaDisponibilidade(idPedidoWorkup));
		
		Doador doadorAssociadoWorkup = obterDoador(pedidoWorkup);
		pedidoWorkup.setIsDoadorInternacional(doadorAssociadoWorkup instanceof DoadorInternacional);
		
		return pedidoWorkup;
	}
	
	@Override
	protected List<CampoMensagem> validateEntity(PedidoWorkup pedidoWorkup) {
		List<CampoMensagem> validacoes = super.validateEntity(pedidoWorkup);
		final Doador doador = solicitacaoService.obterDoador(pedidoWorkup.getSolicitacao());
		
		if(StatusPedidosWorkup.AGENDADO.getId().equals(pedidoWorkup.getStatusPedidoWorkup().getId())){
			
			if (pedidoWorkup.getDataFinalWorkup() != null && pedidoWorkup.getDataFinalWorkup().isAfter(pedidoWorkup.getDataLimiteWorkup())) {
				validacoes.add(new CampoMensagem(AppUtil.getMensagem(messageSource, "erro.validacao.data.maior", "Data Realização do Workup", "Data Lim. Resultado")));
			}
			
			if (doador.isNacional()) {
				
				if (pedidoWorkup.getDataFinalWorkup() != null && pedidoWorkup.getDataInicioWorkup().isAfter(pedidoWorkup.getDataFinalWorkup())) {
					validacoes.add(new CampoMensagem(AppUtil.getMensagem(messageSource, "erro.validacao.data.maior", "Data de Início do Workup", "Data Realização do Workup")));
				}
				
				if (pedidoWorkup.getDataPrevistaDisponibilidadeDoador().isAfter(pedidoWorkup.getDataColeta())) {
					validacoes.add(new CampoMensagem(AppUtil.getMensagem(messageSource, "erro.validacao.data.maior", "Data Prevista de Disponibilidade do Daodor", "Data de Realização da Coleta")));
				}
				
				if (pedidoWorkup.getDataPrevistaDisponibilidadeDoador().isAfter(pedidoWorkup.getDataPrevistaLiberacaoDoador())) {
					validacoes.add(new CampoMensagem(AppUtil.getMensagem(messageSource, "erro.validacao.data.maior", "Data Prevista de Disponibilidade do Daodor", "Data de Liberação do Doador")));
				}
				
				if (pedidoWorkup.getCentroColeta() == null) {
					validacoes.add(new CampoMensagem(AppUtil.getMensagem(messageSource, "erro.centro.transplantador.nulo")));
				}
			}
			
			if (pedidoWorkup.getFonteCelula() == null) {
				validacoes.add(new CampoMensagem(AppUtil.getMensagem(messageSource, "erro.fontecelula.nula")));
			}
			else {
				final FonteCelula fonteCelula = pedidoWorkup.getFonteCelula();
				final Prescricao prescricao = prescricaoService.obterPrescricao(pedidoWorkup.getSolicitacao());
				
				final boolean primeiraOpcaoFonteCelulaSelecionada = 
						fonteCelula.getId().equals(prescricao.getFonteCelulaOpcao1().getId());
				final boolean segundaOpcaoFonteCelulaSelecionada = 
						prescricao.getFonteCelulaOpcao2() == null || fonteCelula.getId().equals(prescricao.getFonteCelulaOpcao2().getId());
				
				if (!primeiraOpcaoFonteCelulaSelecionada && !segundaOpcaoFonteCelulaSelecionada) {
					validacoes.add(new CampoMensagem(AppUtil.getMensagem(messageSource, "erro.fontecelula.diferente.prescricao")));
				}
			}
		}
		
		return validacoes;
	}

	@CreateLog(TipoLogEvolucao.PEDIDO_WORKUP_CANCELADO_PARA_DOADOR)
	@Override
	public void agendarPedidoWorkup(Long idPedido, AgendamentoWorkupDTO agendamentoWorkupDTO) {
		PedidoWorkup pedidoRecuperado = pedidoWorkupRepository.findById(idPedido).orElse(null);
		if (pedidoRecuperado == null) {
			throw new BusinessException("pedido.workup.erro.nao.encontrado");
		}

		pedidoRecuperado.setDataLimiteWorkup(agendamentoWorkupDTO.getDataLimiteWorkup());
		
		pedidoRecuperado.setDataPrevistaDisponibilidadeDoador(agendamentoWorkupDTO.getDataPrevistaDisponibilidadeDoador());
		pedidoRecuperado.setDataPrevistaLiberacaoDoador(agendamentoWorkupDTO.getDataPrevistaLiberacaoDoador());
		pedidoRecuperado.setDataColeta(agendamentoWorkupDTO.getDataColeta());
		pedidoRecuperado.setDataInicioWorkup(agendamentoWorkupDTO.getDataInicioResultado());
		if(agendamentoWorkupDTO.getDataFinalResultado() != null) {
			pedidoRecuperado.setDataFinalWorkup(agendamentoWorkupDTO.getDataFinalResultado());
		}
		
		if(agendamentoWorkupDTO.getIdCentroColeta() != null){
			pedidoRecuperado.setCentroColeta(new CentroTransplante(agendamentoWorkupDTO.getIdCentroColeta()));
		}
		if(agendamentoWorkupDTO.getIdFonteCelula() != null){
			pedidoRecuperado.setFonteCelula(new FonteCelula(agendamentoWorkupDTO.getIdFonteCelula()));
		}
		
		pedidoRecuperado.setStatusPedidoWorkup(new StatusPedidoWorkup(StatusPedidosWorkup.AGENDADO));
		
		pedidoRecuperado.setNecessitaLogistica(
				agendamentoWorkupDTO.getNecessitaLogistica() == null ? Boolean.FALSE : agendamentoWorkupDTO.getNecessitaLogistica());
		

		if (usouDataDaPrescricao(agendamentoWorkupDTO, pedidoRecuperado)) {
			pedidoRecuperado.setTipoUtilizado(PedidoWorkup.DATA_DA_PRESCRICAO);
		}
		else {
			pedidoRecuperado.setTipoUtilizado(PedidoWorkup.DATA_DA_DISPONIBILIDADE);
		}
		pedidoRecuperado.setDataUltimoStatus(LocalDateTime.now());

		save(pedidoRecuperado);
		
		criarResultadoWorkup(pedidoRecuperado);
		
		if (agendamentoWorkupDTO.getNecessitaLogistica() != null && agendamentoWorkupDTO.getNecessitaLogistica() == true) {
			pedidoLogisticaService.criarLogistica(pedidoRecuperado);
		}
		
		LOGGER.info("Pedido de workup agendado.");
	}

	/**
	 * Cria a entidade Resultado de Workup que deverá receber o
	 * resultado quando o mesmo estiver pronto e for inserido no sistema.
	 * O objeto é criado neste momento para que seja possível associá-lo a tarefa.
	 * 
	 * @param pedidoRecuperado pedido de workup associado.
	 */
	private void criarResultadoWorkup(PedidoWorkup pedidoRecuperado) {
		ResultadoWorkup resultado = new ResultadoWorkup();
		resultado.setDataCriacao(LocalDateTime.now());
		resultado.setPedidoWorkup(pedidoRecuperado.getId());
		resultadoWorkupService.salvarResultadoWorkup(resultado);
		criarTarefaResultadoWorkup(resultado);
	}

	/**
	 * Cria a tarefa para cadastrar o resultado do workup.
	 * 
	 * @param resultado resultado de workup criado e associado a tarefa.
	 */
	private void criarTarefaResultadoWorkup(ResultadoWorkup resultado) {
		final Paciente paciente = obterPaciente(resultado.getPedidoWorkup());
		
		PedidoWorkup pedidoWorkup = obterPedidoWorkup(resultado.getPedidoWorkup());
		
		TiposDoador tiposDoador = pedidoWorkup.getSolicitacao().getMatch().getDoador().getTipoDoador();
		
		if (TiposDoador.INTERNACIONAL.equals(tiposDoador)) {
			TiposTarefa.CADASTRAR_RESULTADO_WORKUP_INTERNACIONAL.getConfiguracao().criarTarefa()
					.comRmr(paciente.getRmr())
					.comObjetoRelacionado(resultado.getId())
					.apply();
		}
		else {
			TiposTarefa.INFORMAR_RESULTADO_WORKUP_NACIONAL.getConfiguracao().criarTarefa()
					.comRmr(paciente.getRmr())
					.comParceiro(pedidoWorkup.getCentroColeta().getId())
					.comObjetoRelacionado(resultado.getId())
					.apply();
		}
		
		
	}

	/**
	 * Verifica no agendamento se a data de coleta utilizada eh da prescricao.
	 * 
	 * @param agendamentoWorkupDTO
	 * @param pedidoRecuperado
	 * @return
	 */
	private boolean usouDataDaPrescricao(AgendamentoWorkupDTO agendamentoWorkupDTO, PedidoWorkup pedidoRecuperado) {
		return pedidoRecuperado.getSolicitacao().getPrescricao().getDataColeta1().equals(agendamentoWorkupDTO.getDataColeta()) || 
			pedidoRecuperado.getSolicitacao().getPrescricao().getDataColeta2().equals(agendamentoWorkupDTO.getDataColeta());
	}

	private void fecharTarefasPedidoWorkup(Long idPedido) {
		final PedidoWorkup pedidoWorkup = findById(idPedido);
		final Paciente paciente = 
				pacienteService.obterPacientePorSolicitacao(pedidoWorkup.getSolicitacao().getId());
		final Doador doador = obterDoador(pedidoWorkup);
		
		if(TiposDoador.NACIONAL.getId().equals(doador.getIdTipoDoador())){
			TiposTarefa.PEDIDO_WORKUP.getConfiguracao().fecharTarefa()
				.comRmr(paciente.getRmr())
				.comObjetoRelacionado(idPedido)
				.apply();
					
		}
		else if(TiposDoador.INTERNACIONAL.getId().equals(doador.getIdTipoDoador())){
			TiposTarefa.PEDIDO_WORKUP_INTERNACIONAL.getConfiguracao().fecharTarefa()
				.comRmr(paciente.getRmr())
				.comObjetoRelacionado(idPedido)
				.apply();
		}
		else {
			LOGGER.error("Erro ao fechar tarefa PEDIDO_WORKUP durante o agendamento.");
			throw new BusinessException("pedido.workup.erro.origem.doador.desconhecida");
		}
	}
	
	private void cancelarTarefasPedidoWorkup(Long idPedido) {
		final PedidoWorkup pedidoWorkup = findById(idPedido);
		final Paciente paciente = 
				pacienteService.obterPacientePorSolicitacao(pedidoWorkup.getSolicitacao().getId());
		final Doador doador = obterDoador(pedidoWorkup);
		
		final Usuario usuarioLogado = usuarioService.obterUsuarioLogado();
		
		if(TiposDoador.NACIONAL.getId().equals(doador.getIdTipoDoador())){
			
			
			TarefaDTO tarefaPedidoWorkup = TiposTarefa.PEDIDO_WORKUP.getConfiguracao().obterTarefa()
				.comObjetoRelacionado(idPedido)
				.comStatus(Arrays.asList(StatusTarefa.ABERTA, StatusTarefa.ATRIBUIDA))
				.paraOutroUsuario(true)
				.comRmr(paciente.getRmr())
				.apply();
			
			if (tarefaPedidoWorkup != null && tarefaPedidoWorkup.getUsuarioResponsavel() != null 
					&& !tarefaPedidoWorkup.getUsuarioResponsavel().equals(usuarioLogado.getId())) {
				TiposTarefa.PEDIDO_WORKUP.getConfiguracao().atualizarTarefa()
					.comUsuario(usuarioLogado)
					.comTarefa(tarefaPedidoWorkup.getId())
					.apply();
			}
			else if (tarefaPedidoWorkup != null) {
				TiposTarefa.PEDIDO_WORKUP.getConfiguracao().cancelarTarefa()
					.comTarefa(tarefaPedidoWorkup.getId())					
					.apply();
			}
						
			
			TarefaDTO tarefaSugerirDataWorkup = TiposTarefa.SUGERIR_DATA_WORKUP.getConfiguracao().obterTarefa()
					.comObjetoRelacionado(idPedido)
					.comStatus(Arrays.asList(StatusTarefa.ABERTA, StatusTarefa.ATRIBUIDA))
					.paraOutroUsuario(true)
					.comRmr(paciente.getRmr())
					.apply();
			
			if (tarefaSugerirDataWorkup != null && tarefaSugerirDataWorkup.getUltimoUsuarioResponsavel() != null 
					&& !tarefaSugerirDataWorkup.getUsuarioResponsavel().equals(usuarioLogado.getId())) {
				TiposTarefa.SUGERIR_DATA_WORKUP.getConfiguracao().atualizarTarefa()
				    .comTarefa(tarefaSugerirDataWorkup.getId())
					.comUsuario(usuarioLogado)
					.apply();
			}
			else if (tarefaSugerirDataWorkup != null) {
			
				TiposTarefa.SUGERIR_DATA_WORKUP.getConfiguracao().cancelarTarefa()
					.comTarefa(tarefaSugerirDataWorkup.getId())
					.apply();
			}
					
		}
		else if(TiposDoador.INTERNACIONAL.getId().equals(doador.getIdTipoDoador())){
			
			TarefaDTO tarefaPedidoWorkupInternacional = TiposTarefa.PEDIDO_WORKUP_INTERNACIONAL.getConfiguracao().obterTarefa()
					.comObjetoRelacionado(idPedido)
					.comStatus(Arrays.asList(StatusTarefa.ABERTA, StatusTarefa.ATRIBUIDA))
					.paraOutroUsuario(true)
					.comRmr(paciente.getRmr())
					.apply();
			
			if (tarefaPedidoWorkupInternacional != null && tarefaPedidoWorkupInternacional.getUltimoUsuarioResponsavel() != null 
					&& tarefaPedidoWorkupInternacional.getUsuarioResponsavel().equals(usuarioLogado.getId())) {
				TiposTarefa.PEDIDO_WORKUP_INTERNACIONAL.getConfiguracao().atualizarTarefa()
					.comUsuario(usuarioLogado)
					.comTarefa(tarefaPedidoWorkupInternacional.getId())
					.apply();
			}
			
			TiposTarefa.PEDIDO_WORKUP_INTERNACIONAL.getConfiguracao().cancelarTarefa()
				.comRmr(paciente.getRmr())
				.comObjetoRelacionado(idPedido)
				.comStatus(Arrays.asList(StatusTarefa.ABERTA, StatusTarefa.ATRIBUIDA))
				.comUsuario(usuarioLogado)
				.apply();
		}
		else {
			LOGGER.error("Erro ao fechar tarefa PEDIDO_WORKUP durante o agendamento.");
			throw new BusinessException("pedido.workup.erro.origem.doador.desconhecida");
		}
	}
	
	@Override
	public Page<TarefaDTO> listarPedidosWorkupInternacionaisDisponiveis(PageRequest paginacao) {

		return TiposTarefa.PEDIDO_WORKUP_INTERNACIONAL.getConfiguracao()
			.listarTarefa()
			.comStatus(Arrays.asList(StatusTarefa.ABERTA))
			.comPaginacao(paginacao)
			.paraTodosUsuarios()
			.apply().getValue();
	}
	
	@Override
	public Page<TarefaDTO> listarPedidosWorkupInternacionais(PageRequest paginacao) {
		
		Comparator<TarefaDTO> comparator = OrdenacaoTarefa.getComparator(OrdenacaoTarefa.PEDIDO_WORKUP.getChave(), null);
		
		Page<TarefaDTO> pageTarefas = TiposTarefa.PEDIDO_WORKUP_INTERNACIONAL.getConfiguracao()
			.listarTarefa()
			.comStatus(Arrays.asList(StatusTarefa.ATRIBUIDA))
			.comPaginacao(paginacao)
			.comOrdenacao(comparator)
			.paraTodosUsuarios()
			.apply().getValue();
		
		return ordenacaoTarefas(paginacao, comparator, pageTarefas);
		
	}

	private Page<TarefaDTO> ordenacaoTarefas(PageRequest paginacao, Comparator<TarefaDTO> comparator,
			Page<TarefaDTO> pageTarefas) {

		@SuppressWarnings("unchecked")
		List<TarefaDTO> tarefas = (List<TarefaDTO>) pageTarefas.getContent();
		List<TarefaDTO> modifiableListTarefa = new ArrayList<TarefaDTO>(tarefas);

		Collections.sort(modifiableListTarefa, comparator);

		List<TarefaDTO> listaPaginada = PaginacaoUtil.retornarListaPaginada(modifiableListTarefa, paginacao
				.getPageNumber(), paginacao.getPageSize());
		return new TarefaJsonPage<>(listaPaginada, paginacao, pageTarefas.getTotalElements());
	}
	
	@Override
	public Page<TarefaDTO> listarPedidosWorkupDisponiveis(PageRequest paginacao) {
		
		Comparator<TarefaDTO> comparator = OrdenacaoTarefa.getComparator(OrdenacaoTarefa.PEDIDO_WORKUP.getChave(), null);
		
		Page<TarefaDTO> pageTarefas =  TiposTarefa.PEDIDO_WORKUP.getConfiguracao()
					.listarTarefa()
					.comStatus(Arrays.asList(StatusTarefa.ABERTA))
					.comPaginacao(paginacao)
					.comOrdenacao(comparator)
					.paraTodosUsuarios()
					.apply().getValue();
		
		return ordenacaoTarefas(paginacao, comparator, pageTarefas);

	}
	
	@Override
	public Page<TarefaDTO> listarPedidosWorkupAtribuidos(PageRequest paginacao) {
		
		Comparator<TarefaDTO> comparator = OrdenacaoTarefa.getComparator(OrdenacaoTarefa.PEDIDO_WORKUP.getChave(), null);

		Page<TarefaDTO> pageTarefas =  TiposTarefa.PEDIDO_WORKUP.getConfiguracao()
				.listarTarefa()
				.comStatus(Arrays.asList(StatusTarefa.ATRIBUIDA))
				.comPaginacao(paginacao)
				.comOrdenacao(comparator)
				.paraTodosUsuarios()
				.apply().getValue();
		
		return ordenacaoTarefas(paginacao, comparator, pageTarefas);
	}

	@Override
	public void fecharTodosPedidos(Long idDoador) {
		UpdateSet<StatusPedidoWorkup> cancelado = new UpdateSet<StatusPedidoWorkup>(
				"statusPedidoWorkup", new StatusPedidoWorkup(StatusPedidoWorkup.CANCELADO));
		Filter<Long> paraIdDoador = new Equals<Long>("solicitacao.match.doador.id", idDoador);
		super.update(Arrays.asList(cancelado), Arrays.asList(paraIdDoador));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PedidoWorkup alterarStatusParaAguardandoCT(Long idPedido) {
		PedidoWorkup pedidoWorkup = pedidoWorkupRepository.findById(idPedido).orElse(null);

		if (pedidoWorkup == null) {
			throw new BusinessException("pedido.workup.erro.nao.encontrado");
		}

		pedidoWorkup.setStatusPedidoWorkup(new StatusPedidoWorkup(StatusPedidosWorkup.AGUARDANDO_CT));
		pedidoWorkup.setTentativasAgendamento(pedidoWorkup.getTentativasAgendamento() + 1);

		pedidoWorkup.setUsuarioResponsavel(usuarioService.obterUsuarioLogado());
		pedidoWorkupRepository.save(pedidoWorkup);
		LOGGER.info("Pedido de workup alterado status para aguardando CT.");
		return pedidoWorkup; 
	}

	@Override
	public Disponibilidade obtemUltimaDisponibilidade(Long idPedido) {
		List<Disponibilidade> disponibilidades = pedidoWorkupRepository.listarDisponibilidadesPorPedido(idPedido);
		return CollectionUtils.isNotEmpty(disponibilidades) ? disponibilidades.get(disponibilidades.size() - 1) : null;
	}

	@Override
	public void responderDisponibilidadeWorkup(Long pedidoWorkupId, Disponibilidade disponibilidadeAtualizada) {
		fecharTarefaCTRespondendoDisponibilidade(pedidoWorkupId);
		atualizarStatusPedido(pedidoWorkupId, StatusPedidosWorkup.CONFIRMADO_CT);
		disponibilidadeService.responderDisponibilidade(disponibilidadeAtualizada);
	}

	/**
	 * Fecha a tarefa aberta para o CT avaliar a disponibilidade de datas
	 * sugeridas pelo analista workup para o workup do doador.
	 * 
	 * @param pedidoWorkupId ID do pedido de workup.
	 */
	private void fecharTarefaCTRespondendoDisponibilidade(Long pedidoWorkupId) {
		final Paciente paciente = obterPaciente(pedidoWorkupId);
		TiposTarefa.SUGERIR_DATA_WORKUP.getConfiguracao().fecharTarefa()
			.comObjetoRelacionado(pedidoWorkupId)
			.comRmr(paciente.getRmr())
			.apply();
	}

	@Override
	public Paciente obterPaciente(Long pedidoWorkupId) {
		return pedidoWorkupRepository.obterPaciente(pedidoWorkupId);
	}

	@Override
	public void atualizarStatusPedido(Long pedidoWorkupId, StatusPedidosWorkup novoStatus) {
		final PedidoWorkup pedidoAtual = findById(pedidoWorkupId);
		boolean primeiraAtualizacaoPedidoWorkup = StatusPedidosWorkup.INICIAL.getId().equals(pedidoAtual.getStatusPedidoWorkup().getId());
		if (primeiraAtualizacaoPedidoWorkup) {
			pedidoAtual.setUsuarioResponsavel(usuarioService.obterUsuarioLogado());
		}
		pedidoAtual.setStatusPedidoWorkup(new StatusPedidoWorkup(novoStatus.getId()));
		pedidoAtual.setDataUltimoStatus(LocalDateTime.now());
		save(pedidoAtual);
	}
	
	@Override
	public void atribuirPedidoWorkup(Long idPedido) {
		atualizarStatusPedido(idPedido, StatusPedidosWorkup.EM_ANALISE);
	}

	@Override
	public DadosCentroTransplanteDTO listarDadosCT(Long idPedido) {
		DadosCentroTransplanteDTO dadosCTDTO = pedidoWorkupRepository.buscarDadosCT(idPedido);
		List<ContatoTelefonico> telefonesCT = pedidoWorkupRepository.buscarTelefonesCT(idPedido);
		List<ContatoTelefonico> telefonesMedico = pedidoWorkupRepository.buscarTelefonesMedico(idPedido);
		dadosCTDTO.setTelefonesCT(telefonesCT);
		dadosCTDTO.setTelefonesMedico(telefonesMedico);

		return dadosCTDTO;
	}

	@Override
	public void finalizarPedidoWorkup(Long idPedidoWorkup) {
		fecharTarefasPedidoWorkup(idPedidoWorkup);
	}

	@Override
	public void fecharPedidoWorkupComoRealizado(Long idPedido) {
		UpdateSet<StatusPedidoWorkup> realizado = new UpdateSet<StatusPedidoWorkup>(
				"statusPedidoWorkup", new StatusPedidoWorkup(StatusPedidoWorkup.REALIZADO));
		Filter<Long> paraPedido = new Equals<Long>("id", idPedido);
		super.update(Arrays.asList(realizado), Arrays.asList(paraPedido));
	}
	
	@Override
	public PedidoWorkup obterPedidoWorkup(Long solicitacaoId, StatusPedidosWorkup status){
		return pedidoWorkupRepository.obterPedidoWorkup(solicitacaoId, status != null? status.getId(): null);
	}

	@Override
	public Paciente obterPaciente(PedidoWorkup pedidoWorkup) {
		return pedidoWorkupRepository.obterPaciente(pedidoWorkup.getId());
	}
	
	@Override
	public Doador obterDoador(PedidoWorkup pedidoWorkup) {
		return pedidoWorkupRepository.obterDoador(pedidoWorkup.getId());
	}

	@SuppressWarnings("rawtypes")
	@Override
	public String[] obterParametros(PedidoWorkup pedidoWorkup) {
		
		IDoador doador = (IDoador) pedidoWorkup.getSolicitacao().getMatch().getDoador();
		return  StringUtils.split(doador.getIdentificacao().toString());
		
	}

	@Override
	public void fecharTodosPedidosPorMatch(Long idMatch) {
		UpdateSet<StatusPedidoWorkup> cancelado = new UpdateSet<StatusPedidoWorkup>(
				"statusPedidoWorkup", new StatusPedidoWorkup(StatusPedidoWorkup.CANCELADO));
		Filter<Long> paraMatchId = new Equals<Long>("solicitacao.match.id", idMatch);
		super.update(Arrays.asList(cancelado), Arrays.asList(paraMatchId));		
	}

	@Override
	public JsonViewPage<TarefaDTO> listarDisponibilidadesPorCentroTransplante(Long idCentroTransplante,
			PageRequest pageRequest) {
		CentroTransplante centroTransplante = centroTransplanteService.obterCentroTransplante(idCentroTransplante);
		
		return TiposTarefa.SUGERIR_DATA_WORKUP.getConfiguracao()
					.listarTarefa()
					.comPaginacao(pageRequest)
					.comStatus(Arrays.asList(StatusTarefa.ABERTA))
					.comParceiros(Arrays.asList(centroTransplante.getId()))
					.apply();
	}
	
	@CreateLog(TipoLogEvolucao.PEDIDO_WORKUP_CANCELADO_PARA_DOADOR)
	@Override
	public void cancelarPedidoWorkupPorsolicitacao(Solicitacao solicitacao, Long idMotivoCancelamento) {
		PedidoWorkup pedidoWorkup = obterPedidoWorkup(solicitacao.getId(), null);
		if (pedidoWorkup != null) {
			if (!StatusPedidosWorkup.REALIZADO.equals(pedidoWorkup.getStatusPedidoWorkup().getId())) {
				
				PedidoWorkup pedidoRecuperado = pedidoWorkupRepository.findById(pedidoWorkup.getId()).orElse(null);
				if (pedidoRecuperado == null) {
					throw new BusinessException("pedido.workup.erro.nao.encontrado");
				}

				MotivoCancelamentoWorkup motivoCancelamentoWorkup = motivoCancelamentoWorkupRepository.findById(idMotivoCancelamento).orElse(null);
				if (motivoCancelamentoWorkup == null) {
					throw new BusinessException("motivo.cancelamento.workup.erro.nao.encontrado");
				}
				
				pedidoRecuperado.setStatusPedidoWorkup(new StatusPedidoWorkup(StatusPedidosWorkup.CANCELADO));
				pedidoRecuperado.setMotivoCancelamentoWorkup(new MotivoCancelamentoWorkup(idMotivoCancelamento));
				save(pedidoRecuperado);
				
				cancelarTarefasPedidoWorkup(pedidoRecuperado.getId());
				LOGGER.info("Pedido de workup cancelado.");
				
				
			}
		}
	}

	/**
	 * @param usuarioService the usuarioService to set
	 */
	@Autowired
	public void setUsuarioService(IUsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	/**
	 * @param solicitacaoService the solicitacaoService to set
	 */
	@Autowired
	public void setSolicitacaoService(ISolicitacaoService solicitacaoService) {
		this.solicitacaoService = solicitacaoService;
	}

	/**
	 * @param resultadoWorkupService the resultadoWorkupService to set
	 */
	@Autowired
	public void setResultadoWorkupService(IResultadoWorkupService resultadoWorkupService) {
		this.resultadoWorkupService = resultadoWorkupService;
	}

	/**
	 * @param pedidoLogisticaService the pedidoLogisticaService to set
	 */
	@Autowired
	public void setPedidoLogisticaService(IPedidoLogisticaService pedidoLogisticaService) {
		this.pedidoLogisticaService = pedidoLogisticaService;
	}

	/**
	 * @param pacienteService the pacienteService to set
	 */
	@Autowired
	public void setPacienteService(IPacienteService pacienteService) {
		this.pacienteService = pacienteService;
	}

	/**
	 * @param doadorService the doadorService to set
	 */
	@Autowired
	public void setDoadorService(IDoadorNacionalService doadorService) {
		this.doadorService = doadorService;
	}

	/**
	 * @param disponibilidadeService the disponibilidadeService to set
	 */
	@Autowired
	public void setDisponibilidadeService(IDisponibilidadeService disponibilidadeService) {
		this.disponibilidadeService = disponibilidadeService;
	}

	/**
	 * @param prescricaoService the prescricaoService to set
	 */
	@Autowired
	public void setPrescricaoService(IPrescricaoService prescricaoService) {
		this.prescricaoService = prescricaoService;
	}

	/**
	 * @param centroTransplanteService the centroTransplanteService to set
	 */
	@Autowired
	public void setCentroTransplanteService(ICentroTransplanteService centroTransplanteService) {
		this.centroTransplanteService = centroTransplanteService;
	}

	

}

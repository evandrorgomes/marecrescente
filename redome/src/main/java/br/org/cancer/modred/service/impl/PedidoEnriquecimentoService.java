package br.org.cancer.modred.service.impl;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import br.org.cancer.modred.exception.BusinessException;
import br.org.cancer.modred.feign.dto.TarefaDTO;
import br.org.cancer.modred.helper.JsonViewPage;
import br.org.cancer.modred.model.Configuracao;
import br.org.cancer.modred.model.Doador;
import br.org.cancer.modred.model.DoadorNacional;
import br.org.cancer.modred.model.Paciente;
import br.org.cancer.modred.model.PedidoEnriquecimento;
import br.org.cancer.modred.model.Solicitacao;
import br.org.cancer.modred.model.StatusDoador;
import br.org.cancer.modred.model.domain.StatusTarefa;
import br.org.cancer.modred.model.domain.TipoEnriquecimento;
import br.org.cancer.modred.model.domain.TiposSolicitacao;
import br.org.cancer.modred.model.domain.TiposTarefa;
import br.org.cancer.modred.persistence.IPedidoEnriquecimentoRepository;
import br.org.cancer.modred.persistence.IRepository;
import br.org.cancer.modred.service.IConfiguracaoService;
import br.org.cancer.modred.service.IDoadorNacionalService;
import br.org.cancer.modred.service.IPacienteService;
import br.org.cancer.modred.service.IPedidoContatoService;
import br.org.cancer.modred.service.IPedidoEnriquecimentoService;
import br.org.cancer.modred.service.IUsuarioService;
import br.org.cancer.modred.service.impl.config.Equals;
import br.org.cancer.modred.service.impl.config.Filter;
import br.org.cancer.modred.service.impl.config.UpdateSet;
import br.org.cancer.modred.service.impl.invocation.AbstractLoggingService;
import br.org.cancer.modred.service.integracao.IIntegracaoEnriquecimentoRedomeWebService;
import br.org.cancer.modred.vo.ConsultaDoadorNacionalVo;

/**
 * Classe de implementação das funcionalidades envolvendo a entidade PedidoEnriquecimento.
 * 
 * @author Fillipe Queiroz
 *
 */
@Transactional
@Service
public class PedidoEnriquecimentoService extends AbstractLoggingService<PedidoEnriquecimento, Long> 
		implements IPedidoEnriquecimentoService {

	private static final Logger LOG = LoggerFactory.getLogger(PedidoEnriquecimentoService.class);
	
	@Autowired
	private IPedidoEnriquecimentoRepository pedidoEnriquecimentoRepository;

	private IPedidoContatoService pedidoContatoService;
	
	@Autowired
	private IPacienteService pacienteService;
	
	@Autowired
	private IDoadorNacionalService doadorService;
	
	@Autowired
	private IConfiguracaoService configuracaoService;

	@Autowired
	private IIntegracaoEnriquecimentoRedomeWebService integracaoEnriquecimentoService;
	
	@Autowired
	private IUsuarioService usuarioService;
	
	@Override
	public IRepository<PedidoEnriquecimento, Long> getRepository() {
		return pedidoEnriquecimentoRepository;
	}

	@Override
	@Transactional(rollbackOn=Exception.class)
	public void fecharPedidoEnriquecimento(Long idPedidoEnriquecimento) {
		PedidoEnriquecimento pedidoEnriquecimento = pedidoEnriquecimentoRepository.findById(idPedidoEnriquecimento).orElse(null);
		if(pedidoEnriquecimento == null){
			throw new BusinessException("erro.pedido.enriquecimento.inexistente");		
		}	
		
		pedidoEnriquecimento.setAberto(false);
		pedidoEnriquecimentoRepository.save(pedidoEnriquecimento);
		
		fecharTarefaEnriquecimento(pedidoEnriquecimento);

		StatusDoador statusDoador = 
				pedidoEnriquecimento.getSolicitacao().getMatch().getDoador().getStatusDoador();
		
		if(StatusDoador.isAtivo(statusDoador.getId())){
			pedidoContatoService.criarPedidoContato(pedidoEnriquecimento.getSolicitacao());
		}
	}

	/**
	 * Finalizar a tarefa de enriquecimento e a tarefa de timeout aberta, ao associá-la ao perfil Enriquecedor.
	 * 
	 * @param tarefa tarefa referência a ser finalizada (marcada como feita).
	 */
	private void finalizarTarefaEnriquecimento(TarefaDTO tarefa) {
	  	JsonViewPage<TarefaDTO> pagesTarefas = TiposTarefa.TIMEOUT.getConfiguracao().listarTarefa()
			.comFiltro( (tarefaBase -> tarefaBase.getTarefaPai() != null  && tarefaBase.getTarefaPai().getId().equals(tarefa.getId()) ))
			.comStatus(Arrays.asList(StatusTarefa.ABERTA))
			.comProcessoId(tarefa.getProcesso().getId())
			.apply();
		
	  	if (pagesTarefas != null && pagesTarefas.getValue() != null) {
	  		pagesTarefas.getValue().getContent().stream().forEach(tarefaTimeout -> {
	  			TiposTarefa.TIMEOUT.getConfiguracao().fecharTarefa()
	  				.comTarefa(tarefaTimeout.getId())
	  				.comTarefaPai(tarefa.getId())
	  				.comStatus(Arrays.asList(StatusTarefa.ABERTA))
	  				.comProcessoId(tarefa.getProcesso().getId())
	  				.apply();		
	  		});
	  	}
	  	
	}
	
	private void fecharTarefaEnriquecimento(PedidoEnriquecimento pedido) {
		Paciente paciente = pedido.getSolicitacao().getMatch().getBusca().getPaciente();
		TarefaDTO tarefa = TiposTarefa.ENRIQUECER_DOADOR.getConfiguracao()
				.fecharTarefa()
				.comStatus(Arrays.asList(StatusTarefa.ABERTA, StatusTarefa.ATRIBUIDA))
				.comRmr(paciente.getRmr())
				.comObjetoRelacionado(pedido.getId())
				.comUsuario(usuarioService.obterUsuarioLogado())
				.apply();
		finalizarTarefaEnriquecimento(tarefa);
	}
	
	@Override
	public void fecharTodosPedidos(Long idDoador) {
		List<PedidoEnriquecimento> pedidosEnriquecimento = obterPedidosDeEnriquecimentoPor(null, idDoador, Boolean.TRUE);
		pedidosEnriquecimento.forEach(ped ->{
			cancelarTarefasRelacionadasAoPedido(ped);
			UpdateSet<Boolean> setFechado = new UpdateSet<>("aberto", Boolean.FALSE);
			UpdateSet<Boolean> setCancelado = new UpdateSet<>("cancelado", Boolean.TRUE);
			Filter<Long> paraIdDoador = new Equals<>("solicitacao.match.doador.id", idDoador);
			super.update(Arrays.asList(setFechado, setCancelado), Arrays.asList(paraIdDoador));			
		});
	}
	

	private void cancelarTarefasRelacionadasAoPedido(PedidoEnriquecimento pedido) {
		final Long rmr = pedido.getSolicitacao().getMatch().getBusca().getPaciente().getRmr();
		
		Page<TarefaDTO> tarefas = TiposTarefa.ENRIQUECER_DOADOR.getConfiguracao().listarTarefa()
			.comRmr(rmr)
			.comStatus(Arrays.asList(StatusTarefa.ABERTA, StatusTarefa.ATRIBUIDA))
			.comObjetoRelacionado(pedido.getId())
			.apply().getValue();
		
		if (CollectionUtils.isNotEmpty(tarefas.getContent())) {
			tarefas.forEach(tarefa -> {
				TiposTarefa.ENRIQUECER_DOADOR.getConfiguracao().cancelarTarefa()
					.comTarefa(tarefa.getId())
					.apply();				
			});	
		}
		
		
		
		
	}

	@Override
	public List<PedidoEnriquecimento> obterPedidosDeEnriquecimentoPor(Long rmr, Long idDoador, boolean aberto) {
		return pedidoEnriquecimentoRepository.buscarPor(idDoador, rmr, aberto);
	}
	
	@Override
	public PedidoEnriquecimento criarPedidoEnriquecimento(Solicitacao solicitacao, Long rmr) {
		final Doador doador = solicitacao.getMatch().getDoador();
		List<PedidoEnriquecimento> pedidosEnriquecimento = obterPedidosDeEnriquecimentoPor(doador.getId(),
				rmr, true);
		if (!pedidosEnriquecimento.isEmpty()) {
			LOG.error("ERRO AO TENTAR CRIAR PEDIDO : ENRIQUECIMENTO JÁ EXISTENTE!");
			throw new BusinessException("erro.pedido.enriquecimento.ja.existente");
		}
		
		if (isDoadorDesatualizado(doador, solicitacao.getTipoSolicitacao().getId())) {
			LOG.debug("CRIANDO PEDIDO DE ENRIQUECIMENTO");
		
			PedidoEnriquecimento pedidoEnriquecimento = new PedidoEnriquecimento();
			pedidoEnriquecimento.setDataCriacao(LocalDateTime.now());
			pedidoEnriquecimento.setAberto(true);
			pedidoEnriquecimento.setSolicitacao(solicitacao);
			pedidoEnriquecimento.setTipo(TipoEnriquecimento.valueOf(solicitacao.getTipoSolicitacao().getId()));
			save(pedidoEnriquecimento);			
			criarTarefaPedidoEnriquecimento(rmr, pedidoEnriquecimento.getId());
			return pedidoEnriquecimento;
		}
		return null;
	}
	
	private boolean isDoadorDesatualizado(Doador doador, Long fase) {

		Long quantidadeDias = 0L;
		String valTempoMax = TiposSolicitacao.FASE_2.getId() == fase 
				? Configuracao.TEMPO_MAXIMO_ENRIQUECIMENTO_EM_DIAS_FASE2 
				: Configuracao.TEMPO_MAXIMO_ENRIQUECIMENTO_EM_DIAS_FASE3; 
		
		int	tempoMaximoEnriquecimento = Integer.valueOf(configuracaoService.obterConfiguracao(valTempoMax).getValor());

		if (doador.getDataAtualizacao() != null) {
			quantidadeDias = ChronoUnit.DAYS.between(doador.getDataAtualizacao(), LocalDateTime.now());
		}
		else {
			quantidadeDias = ChronoUnit.DAYS.between(doador.getDataCadastro(), LocalDateTime.now());
		}
		
		return quantidadeDias.intValue() > tempoMaximoEnriquecimento;
	}
	
	private void criarTarefaPedidoEnriquecimento(Long rmr, Long pedidoEnriquecimentoId) {
		LOG.debug("CRIANDO TAREFA DE PEDIDO DE ENRIQUECIMENTO");		
		TiposTarefa.ENRIQUECER_DOADOR.getConfiguracao().criarTarefa()
			.comObjetoRelacionado(pedidoEnriquecimentoId)
			.comRmr(rmr)
			.apply();
		
		LOG.info("Criou tarefa de pedido de enriquecimento para o pedido {}", pedidoEnriquecimentoId);
	}

	@Override
	public Paciente obterPaciente(PedidoEnriquecimento enriquecimento) {
		return pacienteService.obterPacientePorSolicitacao(enriquecimento.getSolicitacao().getId());
	}

	@Override
	public String[] obterParametros(PedidoEnriquecimento enriquecimento) {
		return doadorService.obterDmrDoadorPorSolicitacao(enriquecimento.getSolicitacao().getId()).toString().split(";");
	}

	@Override
	public void fecharTodosPedidosPorMatch(Long matchId) {
		UpdateSet<Boolean> setFechado = new UpdateSet<Boolean>("aberto", Boolean.FALSE);
		Filter<Long> paraOMatch = new Equals<Long>("solicitacao.match.id", matchId);
		super.update(Arrays.asList(setFechado), Arrays.asList(paraOMatch));
	}
	

	/**
	 * Formatar cpf para atualizar o REDOMEWEB.
	 * 
	 * @param doador 
	 * @return string.
	 */
	public String recuperarCpf(DoadorNacional doador){ 			
		if (!StringUtils.isBlank(doador.getCpf())) {    
			Object[] params = new Object[]{doador.getCpf().substring(0,3), doador.getCpf().substring(3,6), doador.getCpf().substring(6,9) , doador.getCpf().substring(9,11)};
			String cpf = MessageFormat.format("{0}.{1}.{2}-{3}", params);	    
			return cpf;
		}	
		return "";	
	}
	    
	/**
	 * Formatar cep para atualizar o REDOMEWEB.
	 * 
	 * @param cepEndereco 
	 * @return string.
	 */	
	public String recuperarCep(String cepEndereco){ 			
		if (StringUtils.isBlank(cepEndereco)) { 
			return "";
		}	
		Object[] params = new Object[]{cepEndereco.substring(0,5), cepEndereco.substring(5,8)};
		String cep = MessageFormat.format("{0}-{1}", params);	    
    
		return cep;    
	}	
	
	@Override
	public void atualizarEnriquecimentoDoadorNacionalRedomeWeb(DoadorNacional doador) {
		
// ################ INTEGRAÇÃO ################
//		 	Long idEnriquecimentoRedomeweb = null;
//			try {
//				idEnriquecimentoRedomeweb = integracaoEnriquecimentoService.atualizarDoadorRedomeWeb(doador);
//			} 
//			catch (IOException e) {
//				LOG.error("WEBSERVICE - REDOMENET", e);					
//			}	
//	############################################ 

	}
	
	@Override
	public TarefaDTO obterPrimeiroPedidoEnriquecimentoDaFilaDeTarefas() {
		
		tratarTarefasPedidoContatoJaAtribuidas();
				
		JsonViewPage<TarefaDTO> pageTarefas =  TiposTarefa.ENRIQUECER_DOADOR.getConfiguracao().listarTarefa()			
			.comStatus(Arrays.asList(StatusTarefa.ABERTA))
			.apply();
		
		List<TarefaDTO> tarefas = pageTarefas != null && pageTarefas.getValue() != null ? 
				pageTarefas.getValue().getContent() : null;
		
		if (tarefas == null || tarefas.isEmpty()) {
			throw new BusinessException("erro.sem.pedido.enriquecimento");
		}
		
		return TiposTarefa.ENRIQUECER_DOADOR.getConfiguracao().atribuirTarefa()
			.comTarefa(tarefas.get(0))
			.comUsuarioLogado()
			.apply();
	}
	
	private void tratarTarefasPedidoContatoJaAtribuidas() {
		
		JsonViewPage<TarefaDTO> pageTarefas =  TiposTarefa.ENRIQUECER_DOADOR.getConfiguracao().listarTarefa()
			.comStatus(Arrays.asList(StatusTarefa.ATRIBUIDA))
			.comUsuario(usuarioService.obterUsuarioLogado())
			.apply();
		
		List<TarefaDTO> tarefas = pageTarefas != null && pageTarefas.getValue() != null ? 
				pageTarefas.getValue().getContent() : null;
		
		if (CollectionUtils.isNotEmpty(tarefas)) {
			tarefas.forEach(tarefa -> {
				TiposTarefa.ENRIQUECER_DOADOR.getConfiguracao().cancelarTarefa()
					.comTarefa(tarefa.getId())
					.apply();
			});			
		}
	}
	
	@Override
	public PedidoEnriquecimento obterPedidoDeEnriqueciomentoPorIdDoadorEStatus(Long idDoador, boolean aberto) {
		return pedidoEnriquecimentoRepository.obterPedidoDeEnriqueciomentoPorIdDoadorEStatus(idDoador, aberto);
	}

	@Override
	public ConsultaDoadorNacionalVo fecharPedidoDeEnriquecimentoNaConsultaDoadorNacional(Long idPedidoEnriquecimento,
			Long idDoador) {
		
		atribuirTarefaPedidoEnriquecimento(idPedidoEnriquecimento);		
		fecharPedidoEnriquecimento(idPedidoEnriquecimento);
		return doadorService.obterDadosPedidoContatoPorDoador(new ConsultaDoadorNacionalVo(idDoador));
	}
	
	private void atribuirTarefaPedidoEnriquecimento(Long idPedidoEnriquecimento) {
		
		PedidoEnriquecimento pedido =  pedidoEnriquecimentoRepository.findById(idPedidoEnriquecimento).get();
		Long rmr = pedido.getSolicitacao().getMatch().getBusca().getPaciente().getRmr();
		
		TarefaDTO tarefa = TiposTarefa.ENRIQUECER_DOADOR.getConfiguracao().obterTarefa()
				.comStatus(Arrays.asList(StatusTarefa.ABERTA))
				.comRmr(rmr)
				.comObjetoRelacionado(idPedidoEnriquecimento)
				.apply();

		if(tarefa != null) {
			TiposTarefa.ENRIQUECER_DOADOR.getConfiguracao().atribuirTarefa()
				.comTarefa(tarefa)
				.comUsuarioLogado()
				.apply();
		}
		else {
			throw new BusinessException("erro.mensagem.tarefa.nao.pode.ser.finalizada");
		}
	}

	/**
	 * @param pedidoContatoService the pedidoContatoService to set
	 */
	@Autowired
	public void setPedidoContatoService(IPedidoContatoService pedidoContatoService) {
		this.pedidoContatoService = pedidoContatoService;
	}
	
	
} 

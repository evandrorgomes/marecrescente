package br.org.cancer.modred.service.impl;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.modred.exception.BusinessException;
import br.org.cancer.modred.feign.dto.TarefaDTO;
import br.org.cancer.modred.helper.JsonViewPage;
import br.org.cancer.modred.model.DoadorNacional;
import br.org.cancer.modred.model.Formulario;
import br.org.cancer.modred.model.LogEvolucao;
import br.org.cancer.modred.model.Paciente;
import br.org.cancer.modred.model.PedidoContato;
import br.org.cancer.modred.model.PedidoContatoSms;
import br.org.cancer.modred.model.Solicitacao;
import br.org.cancer.modred.model.StatusDoador;
import br.org.cancer.modred.model.TentativaContatoDoador;
import br.org.cancer.modred.model.annotations.log.CreateLog;
import br.org.cancer.modred.model.domain.AcaoPedidoContato;
import br.org.cancer.modred.model.domain.Perfis;
import br.org.cancer.modred.model.domain.StatusTarefa;
import br.org.cancer.modred.model.domain.TipoLogEvolucao;
import br.org.cancer.modred.model.domain.TiposSolicitacao;
import br.org.cancer.modred.model.domain.TiposTarefa;
import br.org.cancer.modred.persistence.IPedidoContatoRepository;
import br.org.cancer.modred.persistence.IRepository;
import br.org.cancer.modred.service.IAnaliseMedicaService;
import br.org.cancer.modred.service.IDoadorNacionalService;
import br.org.cancer.modred.service.IDoadorNaoContactadoService;
import br.org.cancer.modred.service.IDoadorService;
import br.org.cancer.modred.service.IFormularioService;
import br.org.cancer.modred.service.IHemoEntidadeService;
import br.org.cancer.modred.service.IMotivoStatusDoadorService;
import br.org.cancer.modred.service.IPacienteService;
import br.org.cancer.modred.service.IPedidoContatoService;
import br.org.cancer.modred.service.IPedidoContatoSmsService;
import br.org.cancer.modred.service.IPedidoExameService;
import br.org.cancer.modred.service.ISolicitacaoService;
import br.org.cancer.modred.service.IUsuarioService;
import br.org.cancer.modred.service.impl.config.Equals;
import br.org.cancer.modred.service.impl.config.Filter;
import br.org.cancer.modred.service.impl.config.OrderBy;
import br.org.cancer.modred.service.impl.config.Projection;
import br.org.cancer.modred.service.impl.invocation.AbstractLoggingService;
import br.org.cancer.modred.util.AppUtil;
import br.org.cancer.modred.util.CampoMensagem;
import br.org.cancer.modred.vo.ConsultaDoadorNacionalVo;
import br.org.cancer.modred.vo.PedidoContatoFinalizadoVo;

/**
 * Classe de implementação das funcionalidades envolvendo a entidade PedidoContato.
 * 
 * @author Pizão
 *
 */
@Service
@Transactional
public class PedidoContatoService extends AbstractLoggingService<PedidoContato, Long> implements IPedidoContatoService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PedidoContatoService.class);

	@Autowired
	private IPedidoContatoRepository pedidoContatoRepository;
	
	private TentativaContatoDoadorService tentativaContatoDoadorService;
		
	@Autowired
	private IPacienteService pacienteService;
	
	@Autowired
	private IDoadorNacionalService doadorNacionalService;
	
	@Autowired
	private IDoadorService doadorService;
	
	@Autowired
	private IFormularioService formularioService;
	
	@Autowired
	private IUsuarioService usuarioService;
	
	@Autowired
	private IMotivoStatusDoadorService motivoStatusDoadorService;
		
	private IPedidoExameService pedidoExameService;
	
	@Autowired
	private IPedidoContatoSmsService pedidoContatoSmsService;
		
	private IAnaliseMedicaService analiseMedicaService;
	
	@Autowired
	private IHemoEntidadeService hemoEntidadeService;
	
	@Autowired
	private ISolicitacaoService solicitacaoService;
	
	@Autowired
	private IDoadorNaoContactadoService doadorNaoContactadoService;
	
	@Override
	public IRepository<PedidoContato, Long> getRepository() {
		return pedidoContatoRepository;
	}

	@Override
	public PedidoContato obterPedido(Long idDoador) {
		return pedidoContatoRepository.obterPedido(idDoador);
	}

	@Override
	public PedidoContato obterPedidoEmAberto(Long idDoador) {
		return pedidoContatoRepository.obterPedidoEmAberto(idDoador);
	}
	
	@Override
	public PedidoContato obterPedidoPorSolicitacao(Long solicitacaoId) {
		return pedidoContatoRepository.obterPedidoPorSolicitacao(solicitacaoId);
	
	}	

	/**
	 * Método para criar o pedido de contato, caso não exista.
	 * 
	 * @param solicitacao - solicitacao para criar o pedido de contato
	 * @return PedidoContato novo pedido de contato
	 */
	@CreateLog
	@Override
	public PedidoContato criarPedidoContato(Solicitacao solicitacao) {
		LOGGER.debug("CRIANDO PEDIDO DE CONTATO");
		List<PedidoContato> pedidosContatoFase2 = buscarPedidosPor(TiposSolicitacao.FASE_2.getId(), solicitacao.getMatch().getDoador().getId(), null, true);
		if (!pedidosContatoFase2.isEmpty()) {
			LOGGER.error("ERRO AO TENTAR CRIAR PEDIDO DE FASE 2: PEDIDO DE FASE 2 JÁ EXISTENTE!");
			throw new BusinessException("erro.pedido.fase2.ja.existente");
		}
		
		List<PedidoContato> pedidosContatoFase3 = buscarPedidosPor(TiposSolicitacao.FASE_3.getId(), solicitacao.getMatch().getDoador().getId(), null, true);
		if (!pedidosContatoFase3.isEmpty()) {
			LOGGER.error("ERRO AO TENTAR CRIAR PEDIDO DE FASE 3: PEDIDO DE FASE 3 JÁ EXISTENTE!");
			throw new BusinessException("erro.pedido.fase3.ja.existente");
		}

		PedidoContato pedidoContato = new PedidoContato();
		pedidoContato.setAberto(true);
		pedidoContato.setPassivo(false);
		pedidoContato.setDoador((DoadorNacional) solicitacao.getMatch().getDoador());
		pedidoContato.setTipoContato(solicitacao.getTipoSolicitacao().getId());
		pedidoContato.setDataCriacao(LocalDateTime.now());
		pedidoContato.setSolicitacao(solicitacao);
		pedidoContato.setDoador((DoadorNacional) solicitacao.getMatch().getDoador());
		save(pedidoContato);	
		
		TentativaContatoDoador tentativaGravada = tentativaContatoDoadorService.criarTentativaContato(pedidoContato, null, null, null, null);
		if (TiposSolicitacao.FASE_2.getId().equals(solicitacao.getTipoSolicitacao().getId())) {
			criarTarefaContatoFase2(solicitacao.getMatch().getBusca().getPaciente().getRmr(), tentativaGravada.getId());	
		}
		else if (TiposSolicitacao.FASE_3.getId().equals(solicitacao.getTipoSolicitacao().getId())) {
			criarTarefaContatoFase3(solicitacao.getMatch().getBusca().getPaciente().getRmr(), tentativaGravada.getId());
		}
		return pedidoContato;
	}
	

	/**
	 * Cria a tarefa de contato para a fase 2, caso não exista.
	 * 
	 * @param rmr - identificador do paciente
	 * @param tentativaContatoId - identificador da tentativa de contato.
	 */
	private void criarTarefaContatoFase2(Long rmr, Long tentativaContatoId) {
		LOGGER.debug("CRIANDO TAREFA DE FASE 2");
		TiposTarefa.CONTACTAR_FASE_2.getConfiguracao().criarTarefa()
			.comObjetoRelacionado(tentativaContatoId)
			.comRmr(rmr)
			.apply();		
		LOGGER.info("Criou tarefa de contato de fase 2 para a tentativa de contato {}", tentativaContatoId);
	}
	
	/**
	 * Cria a tarefa de contato para a fase 3, caso não exista.
	 * 
	 * @param rmr - identificador do paciente
	 * @param tentativaContatoId - identificador da tentativa de contato.
	 */
	private void criarTarefaContatoFase3(Long rmr, Long tentativaContatoId) {
		LOGGER.debug("CRIANDO TAREFA DE FASE 3");		
		TiposTarefa.CONTACTAR_FASE_3.getConfiguracao().criarTarefa()
			.comObjetoRelacionado(tentativaContatoId)
			.comRmr(rmr)
			.apply();			
		LOGGER.info("Criou tarefa de contato de fase 2 ");

	}
	
	@CreateLog
	@Override
	public void finalizarPedidoContatoAutomaticamente(Long idPedidoContato) {
		final PedidoContatoFinalizadoVo pedidoContatoFinalizadoVo = new PedidoContatoFinalizadoVo(false, null );
		finalizarPedidoContato(idPedidoContato, true, pedidoContatoFinalizadoVo);		
	}
	
	/**
	 * Método para fechar o pedido de contato.
	 * 
	 * @param pedido - pedido a ser fehado.
	 */
	@CreateLog
	@Override
	public void finalizarPedidoContato(Long idPedidoContato, Boolean finalizacaoAutomatica, PedidoContatoFinalizadoVo pedidoContatoFinalizadoVo) {
		LOGGER.debug("FECHANDO PEDIDO DE CONTATO");
		PedidoContato pedidoContato = obterPedidoContatoPorId(idPedidoContato); 		
		pedidoContato.setAberto(false);
		pedidoContato.setFinalizacaoAutomatica(finalizacaoAutomatica);
		pedidoContato.setDataFinalizacao(LocalDateTime.now());
		pedidoContato.setUsuario(usuarioService.obterUsuarioLogado());
		pedidoContato.setContactado(pedidoContatoFinalizadoVo.getContactado());
		pedidoContato.setContactadoPorTerceiro(pedidoContatoFinalizadoVo.getContactadoPorTerceiro());
		pedidoContato.setAcao(pedidoContatoFinalizadoVo.getAcao());
		if (pedidoContatoFinalizadoVo.getIdMotivoStatusDoador() != null) {
			pedidoContato.setMotivoStatusDoador(motivoStatusDoadorService.obterMotivoPorId(pedidoContatoFinalizadoVo.getIdMotivoStatusDoador()));
		}
		if (pedidoContatoFinalizadoVo.getTempoInativacaoTemporaria() != null) {
			pedidoContato.setTempoInativacaoTemporaria(pedidoContatoFinalizadoVo.getTempoInativacaoTemporaria());
		}
		
		if (pedidoContatoFinalizadoVo.getHemocentro() != null) {
			pedidoContato.setHemoEntidade(hemoEntidadeService.obterPorId(pedidoContatoFinalizadoVo.getHemocentro()));
		}
		
		if (!pedidoContato.getPassivo() && pedidoContatoFinalizadoVo.getContactado() != null && !pedidoContatoFinalizadoVo.getContactado()) {
			pedidoContato.setContactadoPorTerceiro(null);
			pedidoContato.setAcao(null);
			pedidoContato.setMotivoStatusDoador(null);
			pedidoContato.setTempoInativacaoTemporaria(null);
			pedidoContato.setHemoEntidade(null);
		}

		save(pedidoContato);
		
		LOGGER.debug("FECHANDO TENTATIVAS DE CONTATO RELACIONADAS AO PEDIDO DE CONTATO");
		List<TentativaContatoDoador> tentativas = tentativaContatoDoadorService.obterPorPedido(pedidoContato.getId());
		if(pedidoContato.getPassivo()) {
			tentativas.stream().filter(TentativaContatoDoador::getNaoFinalizada)
			.forEach(tentativa -> tentativaContatoDoadorService.finalizarTentaticaContatoPassivo(tentativa));
		}
		else {
			tentativas.stream().filter(TentativaContatoDoador::getNaoFinalizada)
			.forEach(tentativa -> tentativaContatoDoadorService.finalizarTentaticaContato(tentativa));
		}
		
		salvarFormularioCasoContactado(pedidoContato, pedidoContatoFinalizadoVo.getFormulario());
		
		inativarDoadorCasoContactadoENaoProsseguir(pedidoContato);
		
		criarPedidoExameCasoFase2NaoContactado(pedidoContato);
		
		final PedidoContatoSms pedidoContatoSms = criarPedidoContatoSmsCasoFase3NaoContactado(pedidoContato);
		
		criarDoadorNaoContactadoCasoFase3NaoContactadoESemPedidoComtatoSms(pedidoContato, pedidoContatoSms);
		
		criarPedidoExameCasoContactadoEProsseguir(pedidoContato);
		
		criarAnaliseMedicaDoadorCasoProsseguirEAnaliseMedica(pedidoContato);
		
		reativarDoadorContatoPassivo(pedidoContato);
		
		LOGGER.debug("PEDIDOS DE CONTATO FECHADOS COM SUCESSO");
	}
	
	private void reativarDoadorContatoPassivo(PedidoContato pedidoContato) {
		if(pedidoContato.getPassivo()) {
			doadorService.salvarAtualizacaoStatusDoador(pedidoContato.getDoador().getId(), StatusDoador.ATIVO, null, null);
		}
	}
	
	private void salvarFormularioCasoContactado(PedidoContato pedidoContato, Formulario formulario) {
		if (pedidoContato.getContactado() != null) {
			if (pedidoContato.getContactado() && AcaoPedidoContato.NAO_PROSSEGUIR.getId().equals(pedidoContato.getAcao())) {
				formulario.setComValidacao(false);
				formularioService.salvarFormularioComPedidoContato(pedidoContato.getId(), formulario);
			}
			else if (pedidoContato.getContactado() && (AcaoPedidoContato.PROSSEGUIR.getId().equals(pedidoContato.getAcao()) || 
					AcaoPedidoContato.ANALISE_MEDICA.getId().equals(pedidoContato.getAcao()))) {
				formulario.setComValidacao(true);
				formularioService.salvarFormularioComPedidoContato(pedidoContato.getId(), formulario);
			}
		}
	}
	
	private void inativarDoadorCasoContactadoENaoProsseguir(PedidoContato pedidoContato) {
		if (!pedidoContato.getPassivo() && pedidoContato.getContactado() != null && pedidoContato.getContactado() && 
				AcaoPedidoContato.NAO_PROSSEGUIR.getId().equals(pedidoContato.getAcao())) {
			final DoadorNacional doador = (DoadorNacional) pedidoContato.getSolicitacao().getMatch().getDoador();
			doadorService.inativarDoador(doador.getId(), pedidoContato.getMotivoStatusDoador().getId(), pedidoContato.getTempoInativacaoTemporaria());
		}
	}
	
	private void criarPedidoExameCasoFase2NaoContactado(PedidoContato pedidoContato) {
		final Solicitacao solicitacao = pedidoContato.getSolicitacao();
		if (!pedidoContato.getPassivo() && TiposSolicitacao.FASE_2.getId().equals(solicitacao.getTipoSolicitacao().getId()) && pedidoContato.getContactado() != null
				&& !pedidoContato.getContactado()) {
			pedidoExameService.criarPedidoDoadorNacional(pedidoContato.getSolicitacao());			
		}
	}
	
	private PedidoContatoSms criarPedidoContatoSmsCasoFase3NaoContactado(PedidoContato pedidoContato) {
		final Solicitacao solicitacao = pedidoContato.getSolicitacao();
		if (!pedidoContato.getPassivo() && TiposSolicitacao.FASE_3.getId().equals(solicitacao.getTipoSolicitacao().getId()) && pedidoContato.getContactado() != null  && !pedidoContato.getContactado()) {
		   return pedidoContatoSmsService.criarPedidoContatoSms(pedidoContato);
		}
		return null;
	}
	
	private void criarDoadorNaoContactadoCasoFase3NaoContactadoESemPedidoComtatoSms(PedidoContato pedidoContato, PedidoContatoSms pedidoContatoSms) {
		final Solicitacao solicitacao = pedidoContato.getSolicitacao();
		if (!pedidoContato.getPassivo() && TiposSolicitacao.FASE_3.getId().equals(solicitacao.getTipoSolicitacao().getId()) && pedidoContato.getContactado() != null  
				&& !pedidoContato.getContactado() && pedidoContatoSms == null) {
		   doadorNaoContactadoService.criarDoadorNaoContactado(pedidoContato);
		}
	}
	
	private void criarPedidoExameCasoContactadoEProsseguir(PedidoContato pedidoContato) {
		if (!pedidoContato.getPassivo() && pedidoContato.getContactado() != null && pedidoContato.getContactado() && AcaoPedidoContato.PROSSEGUIR.getId().equals(pedidoContato.getAcao())) {
			pedidoExameService.criarPedidoDoadorNacional(pedidoContato.getSolicitacao());
		}	
	}
	
	private void criarAnaliseMedicaDoadorCasoProsseguirEAnaliseMedica(PedidoContato pedidoContato) {
		if (!pedidoContato.getPassivo() && pedidoContato.getContactado() != null && pedidoContato.getContactado() && AcaoPedidoContato.ANALISE_MEDICA.getId().equals(pedidoContato.getAcao())) {
			analiseMedicaService.criarAnaliseMedica(pedidoContato);
		}
	}

	@Override
	public List<PedidoContato> buscarPedidosPor(Long tipoSolicitacao, Long idDoador, Long rmr, boolean aberto) {
		return pedidoContatoRepository.buscarPedidosPor(tipoSolicitacao, idDoador, rmr, aberto);
	}

	private void cancelarPedidoContatoPorPedido(PedidoContato pedido) {		
		LOGGER.debug("CANCELANDO TENTATIVAS DE CONTATO");
		tentativaContatoDoadorService.cancelarTentativasDeContato(pedido);
		LOGGER.debug("CANCELANDO PEDIDO DE CONTATO");
		pedido.setAberto(false);
		pedidoContatoRepository.save(pedido);
		LOGGER.debug("CANCELAMENTO DE PEDIDO DE CONTATO REALIZADO COM SUCESSO");
	}
	
	@CreateLog
	@Override	
	public void cancelarPedidoContatoPorSolicitacao(Solicitacao solicitacao){
		List<PedidoContato> pedidosContato = pedidoContatoRepository.buscarPedidosPor(solicitacao.getTipoSolicitacao().getId()
				, solicitacao.getMatch().getDoador().getId(), null, true);
		
		if(!pedidosContato.isEmpty()){
			pedidosContato.forEach(ped -> cancelarPedidoContatoPorPedido(ped) );
		}
		
	}
	
	@Override
	public Paciente obterPaciente(PedidoContato pedidoContato) {
		if(pedidoContato.getPassivo()) {
			Solicitacao ultimaSolicitacaoConcluida = 
					solicitacaoService.obterSolicitacaoPorIdDoadorComIdStatusConcluidoECancelado(pedidoContato.getDoador().getId());
			if(ultimaSolicitacaoConcluida != null) {
				return pacienteService.obterPacientePorSolicitacao(ultimaSolicitacaoConcluida.getId());
			}
			return null;
		}
		
		Paciente paciente = pacienteService.obterPacientePorPedidoContato(pedidoContato.getId());
		
		return paciente;
	}

	@Override
	public String[] obterParametros(PedidoContato pedidoContato) {
		if(pedidoContato.getPassivo()) {
			return ((DoadorNacional) pedidoContato.getDoador()).getDmr().toString().split(";");
		}
		DoadorNacional doador = doadorNacionalService.obterDoadorPorPedidoContato(pedidoContato.getId());
		return ((DoadorNacional)doador).getDmr().toString().split(";");
	}
	
	@Override
	public LogEvolucao criarLog(PedidoContato pedidoContato, TipoLogEvolucao tipoLog, Perfis[] perfisExcluidos) {

		TipoLogEvolucao tipoLogPedido = null;
		LogEvolucao log = super.criarLog(pedidoContato, tipoLog, perfisExcluidos);
		
		if(pedidoContato.getPassivo()) {

			if(TiposSolicitacao.FASE_2.getId().equals(pedidoContato.getTipoContato())){
				tipoLogPedido = TipoLogEvolucao.PEDIDO_CONTATO_FASE_2_REALIZADO_PARA_DOADOR; 
			}
			else if(TiposSolicitacao.FASE_3.getId().equals(pedidoContato.getTipoContato())){
				tipoLogPedido = TipoLogEvolucao.PEDIDO_CONTATO_FASE_3_REALIZADO_PARA_DOADOR; 
			}
			
		}
		else {
			Solicitacao solicitacao = pedidoContato.getSolicitacao();
			boolean isCancelamento = !pedidoContato.isAberto() && pedidoContato.getContactado() == null && pedidoContato.getDataFinalizacao() == null ;

			if(TiposSolicitacao.FASE_2.getId().equals(solicitacao.getTipoSolicitacao().getId())){
				tipoLogPedido = isCancelamento ? 
						TipoLogEvolucao.PEDIDO_CONTATO_FASE_2_CANCELADO_PARA_DOADOR : 
							TipoLogEvolucao.PEDIDO_CONTATO_FASE_2_REALIZADO_PARA_DOADOR;
			}
			else if(TiposSolicitacao.FASE_3.getId().equals(solicitacao.getTipoSolicitacao().getId())){
				tipoLogPedido = isCancelamento ? 
						TipoLogEvolucao.PEDIDO_CONTATO_FASE_3_CANCELADO_PARA_DOADOR : 
							TipoLogEvolucao.PEDIDO_CONTATO_FASE_3_REALIZADO_PARA_DOADOR;
			}
		}
		
		if(tipoLogPedido == null){
			throw new IllegalArgumentException("Tipo de solicitação para o pedido " + 
												pedidoContato.getId() + " é nulo ou desconhecido.");
		}
		
		log.setTipoEvento(tipoLogPedido);
		return log;
	}
	
	@Override
	protected List<CampoMensagem> validateEntity(PedidoContato pedidoContato) {
		List<CampoMensagem> validacoes = super.validateEntity(pedidoContato);
		
		if (!pedidoContato.getPassivo() && !pedidoContato.isAberto()) {
			if (pedidoContato.getContactado() == null) {
				validacoes.add(new CampoMensagem(AppUtil.getMensagem(messageSource, "erro.pedido.contato.finalizado.sem.status")));
			}
			else if (pedidoContato.getContactado()) {
				if (pedidoContato.getContactadoPorTerceiro() == null) {
					validacoes.add(new CampoMensagem(AppUtil.getMensagem(messageSource, "erro.pedido.contato.finalizado.sem.informacao.terceiro")));
				}
				if (pedidoContato.getAcao() == null) {
					validacoes.add(new CampoMensagem(AppUtil.getMensagem(messageSource, "erro.pedido.contato.finalizado.sem.acao")));
				}
				else if (AcaoPedidoContato.NAO_PROSSEGUIR.getId().equals(pedidoContato.getAcao()) ) {
					if (pedidoContato.getMotivoStatusDoador() == null) {
						validacoes.add(new CampoMensagem(AppUtil.getMensagem(messageSource, "erro.pedido.contato.finalizado.contactado.naoprosseguir.sem.motivo")));
					}
					else {
						if (pedidoContato.getMotivoStatusDoador().getStatusDoador().getTempoObrigatorio() && 
								pedidoContato.getTempoInativacaoTemporaria() == null) {
							validacoes.add(new CampoMensagem(AppUtil.getMensagem(messageSource, "erro.pedido.contato.finalizado.contactado.naoprosseguir.com.motivo.sem.tempo")));
						}
					}
				}								
				else if ( (AcaoPedidoContato.PROSSEGUIR.getId().equals(pedidoContato.getAcao()) || AcaoPedidoContato.ANALISE_MEDICA.getId().equals(pedidoContato.getAcao())) && 
						TiposSolicitacao.FASE_3.getId().equals(pedidoContato.getSolicitacao().getTipoSolicitacao().getId()) ) {
					if (pedidoContato.getHemoEntidade() == null) {
						validacoes.add(new CampoMensagem(AppUtil.getMensagem(messageSource, "erro.pedido.contato.fase3.finalizado.contactado.prosseguir.ou.analisemedica.sem.hemocentro")));
					}
					
				}
			}			
		}
		
		return validacoes;
	}
	

	@Override
	public PedidoContato obterPedidoContatoPorId(Long id) {
		PedidoContato pedidoContato = findById(id);
		if (pedidoContato == null) {
			throw new BusinessException("erro.pedido.nao.encontrado");
		}
		return pedidoContato;
	}

	@Override
	public void salvarFormularioContato(Long pedidoContatoId, Formulario formulario) {
		PedidoContato pedidoContato = obterPedidoContatoPorId(pedidoContatoId);
		formulario.setComValidacao(false);
		formularioService.salvarFormularioComPedidoContato(pedidoContato.getId(), formulario);
	}

	@Override
	public TarefaDTO obterPedidoContatoPoridTarefa(Long tentativaContatoId, Long tarefaId) {
		
		TentativaContatoDoador tentativaContato = tentativaContatoDoadorService.obterTentativaPorId(tentativaContatoId);
		
		return tentativaContatoDoadorService.obterTarefaAssociadaATentativaContatoEStatusTarefa(tentativaContato, 
				Arrays.asList(StatusTarefa.ABERTA,StatusTarefa.ATRIBUIDA), true, true);
	}
	
	@Override
	public TarefaDTO obterPrimeiroPedidoContatoDaFilaDeTarefas(Long tipoTarefaId) {
		
		tratarTarefasPedidoContatoJaAtribuidas(tipoTarefaId);
		
		TiposTarefa tipoTarefa = TiposTarefa.valueOf(tipoTarefaId);
		JsonViewPage<TarefaDTO> pageTarefas =  tipoTarefa.getConfiguracao().listarTarefa()
			.comStatus(Arrays.asList(StatusTarefa.ABERTA))
			.apply();
		
		List<TarefaDTO> tarefas = pageTarefas != null && pageTarefas.getValue() != null ? 
				pageTarefas.getValue().getContent() : null;
		
		if (tarefas == null || tarefas.isEmpty()) {
			throw new BusinessException("tentativacontatodoador.error.nao_existem_tarefas");
		}
		
		return tipoTarefa.getConfiguracao().atribuirTarefa()
			.comTarefa(tarefas.get(0))
			.comUsuarioLogado()
			.apply();
	}

	
	private void tratarTarefasPedidoContatoJaAtribuidas(Long tipoTarefaId) {
		TiposTarefa tipoTarefa = TiposTarefa.valueOf(tipoTarefaId);
		JsonViewPage<TarefaDTO> pageTarefas =  tipoTarefa.getConfiguracao().listarTarefa()
			.comStatus(Arrays.asList(StatusTarefa.ATRIBUIDA))
			.comUsuario(usuarioService.obterUsuarioLogado())
			.apply();
		
		List<TarefaDTO> tarefas = pageTarefas != null && pageTarefas.getValue() != null ? 
				pageTarefas.getValue().getContent() : null;
		
		if (tarefas != null && !tarefas.isEmpty()) {
			tipoTarefa.getConfiguracao().removerAtribuicaoTarefa()
				.comTarefa(tarefas.get(0))
				.apply();
		}
	}
	
	@Override
	public LocalDateTime obterDataUltimoPedidoContatoFechado(Long idDoador) {
		Projection dataFinalizacao = new Projection("dataFinalizacao");
		Filter<Long> doador = new Equals<>("solicitacao.match.doador.id", idDoador);
		Filter<Boolean> aberto = new Equals<>("aberto", false);
		OrderBy order = new OrderBy("dataFinalizacao", false);
					
		List<PedidoContato> pedidos = find(Arrays.asList(dataFinalizacao), Arrays.asList(doador, aberto), Arrays.asList(order));
		
		if (pedidos != null && !pedidos.isEmpty()) {
			return pedidos.get(0).getDataFinalizacao();	
		}
		return null;
	}

	@Override
	public Boolean obterVerificacaoUltimoPedidoContatoContactadoFechado(Long idDoador) {
		Projection contactado = new Projection("contactado");
		Filter<Long> doador = new Equals<>("solicitacao.match.doador.id", idDoador);
		OrderBy order = new OrderBy("dataFinalizacao", false);
					
		List<PedidoContato> pedidos = find(Arrays.asList(contactado), Arrays.asList(doador), Arrays.asList(order));
		
		if (pedidos != null && !pedidos.isEmpty()) {
			return pedidos.get(0).getContactado();	
		}
		return null;
	}

	@Override
	public PedidoContato obterUltimoPedidoContatoFechado(Long idDoador) {
		Projection id = new Projection("id");
		Projection tipoContato = new Projection("tipoContato");
		Filter<Long> doador = new Equals<>("doador.id", idDoador);
		Filter<Boolean> aberto = new Equals<>("aberto", false);
		OrderBy order = new OrderBy("dataFinalizacao", false);
					
		List<PedidoContato> pedidos = find(Arrays.asList(id, tipoContato), Arrays.asList(doador, aberto), Arrays.asList(order));
		
		if (pedidos != null && !pedidos.isEmpty()) {
			return pedidos.get(0);	
		}
		return null;
	}

	@Override
	public PedidoContato obterUltimoPedidoContato(Long idDoador) {
		Projection id = new Projection("id");
		Projection tipoContato = new Projection("tipoContato");
		Projection aberto = new Projection("aberto");
		Projection passivo = new Projection("passivo");
		Projection acao = new Projection("acao");
		Projection contactado = new Projection("contactado");
		Filter<Long> doador = new Equals<>("doador.id", idDoador);
		OrderBy order1 = new OrderBy("aberto", false);
		OrderBy order2 = new OrderBy("dataCriacao", false);
					
		List<PedidoContato> pedidos = find(Arrays.asList(id, tipoContato, aberto, passivo, acao, contactado), Arrays.asList(doador), Arrays.asList(order1,order2));
		
		if (pedidos != null && !pedidos.isEmpty()) {
			return pedidos.get(0);	
		}
		return null;
	}
	
	/**
	 * Método para criar o pedido de contato passivo, caso não exista.
	 * 
	 * @param idDoador - identificação do doador para criar o pedido de contato passivo.
	 * @return ConsultaDoadorNacionalVo novo pedido de contato Passivo vo
	 */
	@CreateLog
	@Override
	public ConsultaDoadorNacionalVo criarPedidoContatoPassivo(Long idDoador) {
		
		LOGGER.debug("CRIANDO PEDIDO DE CONTATO PASSIVO");
		
		TentativaContatoDoador tentativa = null;
		DoadorNacional doador = (DoadorNacional) doadorService.obterDoador(idDoador);
		PedidoContato pedidoContatoPassivo = this.obterPedidoEmAberto(doador.getId());

		if(pedidoContatoPassivo != null) {
			tentativa = tentativaContatoDoadorService.obterUltimaTentativaContatoPorPedidoContatoId(pedidoContatoPassivo.getId());
		}
		else {
			PedidoContato pedido = this.obterUltimoPedidoContatoFechado(doador.getId());
			
			pedidoContatoPassivo = pedido.clone();
			pedidoContatoPassivo.setDoador(doador);
			pedidoContatoPassivo.setAberto(true);
			pedidoContatoPassivo.setDataCriacao(LocalDateTime.now());
			pedidoContatoPassivo.setPassivo(true);
			pedidoContatoPassivo.setMotivoStatusDoador(null);
			save(pedidoContatoPassivo);
			
			tentativa = tentativaContatoDoadorService.criarTentativaContato(pedidoContatoPassivo, null, null, null, null);
		}
		
		return new ConsultaDoadorNacionalVo(tentativa.getId(), pedidoContatoPassivo.getTipoContato());
	}

	/**
	 * @param tentativaContatoDoadorService the tentativaContatoDoadorService to set
	 */
	@Autowired
	public void setTentativaContatoDoadorService(TentativaContatoDoadorService tentativaContatoDoadorService) {
		this.tentativaContatoDoadorService = tentativaContatoDoadorService;
	}

	/**
	 * @param pedidoExameService the pedidoExameService to set
	 */
	@Autowired
	public void setPedidoExameService(IPedidoExameService pedidoExameService) {
		this.pedidoExameService = pedidoExameService;
	}

	/**
	 * @param analiseMedicaService the analiseMedicaService to set
	 */
	@Autowired
	public void setAnaliseMedicaService(IAnaliseMedicaService analiseMedicaService) {
		this.analiseMedicaService = analiseMedicaService;
	}
	
	
	
	
}

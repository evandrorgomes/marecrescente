package br.org.cancer.modred.service.impl;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.org.cancer.modred.exception.BusinessException;
import br.org.cancer.modred.exception.ValidationException;
import br.org.cancer.modred.feign.dto.TarefaDTO;
import br.org.cancer.modred.model.AvaliacaoPedidoColeta;
import br.org.cancer.modred.model.AvaliacaoWorkupDoador;
import br.org.cancer.modred.model.IDoador;
import br.org.cancer.modred.model.LogEvolucao;
import br.org.cancer.modred.model.Paciente;
import br.org.cancer.modred.model.annotations.log.CreateLog;
import br.org.cancer.modred.model.domain.Perfis;
import br.org.cancer.modred.model.domain.StatusTarefa;
import br.org.cancer.modred.model.domain.TipoLogEvolucao;
import br.org.cancer.modred.model.domain.TiposTarefa;
import br.org.cancer.modred.persistence.IAvaliacaoPedidoColetaRepository;
import br.org.cancer.modred.persistence.IRepository;
import br.org.cancer.modred.service.IAvaliacaoPedidoColetaService;
import br.org.cancer.modred.service.IUsuarioService;
import br.org.cancer.modred.service.impl.invocation.AbstractLoggingService;
import br.org.cancer.modred.util.CampoMensagem;
import br.org.cancer.modred.util.ConstraintViolationTransformer;

/**
 * Implementação dos métodos de serviço de Avaliação de Pedido de Coleta.
 * @author Filipe Paes
 *
 */
@Service
@Transactional
public class AvaliacaoPedidoColetaService  extends AbstractLoggingService<AvaliacaoPedidoColeta, Long>  
	implements IAvaliacaoPedidoColetaService {

	private static final Logger LOGGER = LoggerFactory.getLogger(AvaliacaoPedidoColetaService.class);
	
	@Autowired
	private IAvaliacaoPedidoColetaRepository avaliacaoPedidoColetaRepository;
		
	private PedidoColetaService pedidoColetaService;
	
	@Autowired
	private SolicitacaoService solicitacaoService;
		
	@Autowired
	private IUsuarioService usuarioService;
	
	@Autowired
	private AvaliacaoWorkupDoadorService avaliacaoWorkupDoadorService;
	
	@Autowired
	private ResultadoWorkupService resultadoWorkupService;
	

	/**
	 * Construtor definido para que seja informado as estratégias
	 * para os eventos de criação de notificação.
	 */
	public AvaliacaoPedidoColetaService() {
		super();
	}
	
	@Override
	public IRepository<AvaliacaoPedidoColeta, Long> getRepository() {
		return avaliacaoPedidoColetaRepository;
	}

	@CreateLog
	@Override
	public void salvarAvaliacao(Long idAvaliacao, AvaliacaoPedidoColeta avaliacaoPedidoColeta) {	
		LOGGER.info("ENTRADA NO MÉTODO DE GRAVAÇÃO DE AVALIAÇÃO DE PEDIDO DE COLETA");
		List<CampoMensagem> camposMensagem = validar(avaliacaoPedidoColeta);
		if(!camposMensagem.isEmpty()){
			LOGGER.info("ERRO DE VALIDAÇÃO DE AVALIAÇÃO DE PEDIDO DE COLETA");
			throw new ValidationException("erro.avaliacao.pedido_coleta", camposMensagem);							
		}
		AvaliacaoPedidoColeta avaliacaoPersistir = this.findById(idAvaliacao);
		avaliacaoPersistir.setDataAvaliacao(LocalDateTime.now());
		avaliacaoPersistir.setUsuario(usuarioService.obterUsuarioLogado());
		avaliacaoPersistir.setPedidoProssegue(avaliacaoPedidoColeta.getPedidoProssegue());
		avaliacaoPedidoColetaRepository.save(avaliacaoPersistir);
		Long rmr = avaliacaoPersistir.getSolicitacao().getPaciente().getRmr();
		fecharTarefaMedico(avaliacaoPersistir, rmr);
		
		if(avaliacaoPersistir.getPedidoProssegue()){
			pedidoColetaService.criarPedidoColetaMedula(avaliacaoPersistir.getSolicitacao().getPedidoWorkup());
		}
		else{
			criarAvaliacaoDeDoador(avaliacaoPersistir, rmr);
			solicitacaoService.cancelarSolicitacao(avaliacaoPersistir.getSolicitacao().getId(), null, null, null, null);
		}
	}

	private void criarAvaliacaoDeDoador(AvaliacaoPedidoColeta avaliacaoPedidoColeta, Long rmr) {
		LOGGER.info("CRIANDO AVALIACAO DE WORKUP DE DOADOR");
		AvaliacaoWorkupDoador avaliacaoWorkupDoador = new AvaliacaoWorkupDoador();
		avaliacaoWorkupDoador.setDataCriacao(LocalDateTime.now());
		avaliacaoWorkupDoador.setResultadoWorkup(resultadoWorkupService.obterResultadoPorPedidoWorkup(avaliacaoPedidoColeta.getSolicitacao().getPedidoWorkup().getId()));
		avaliacaoWorkupDoadorService.salvarAvaliacaoWorkup(avaliacaoWorkupDoador);
		LOGGER.info("CRIANDO TAREFA DE AVALIACAO DE DOADOR");
		criarTarefaMedicoRedomeParaAvaliarDoador(rmr, avaliacaoWorkupDoador.getId());
	}

	private void fecharTarefaMedico(AvaliacaoPedidoColeta avaliacaoPersistir, Long rmr) {
		LOGGER.info("FECHANDO TAREFA DE AVALIAÇÃO DE PEDIDO DE COLETA");
		TiposTarefa.AVALIAR_PEDIDO_COLETA.getConfiguracao().fecharTarefa()
			.comRmr(rmr)
			.comStatus(Arrays.asList(StatusTarefa.ATRIBUIDA))
			.comObjetoRelacionado(avaliacaoPersistir.getId())
			.apply();
	}
	
	@Override
	public AvaliacaoPedidoColeta obterAvaliacaoDePedidoDeColetaPorId(Long idAvaliacaoPedidoColeta) {
		AvaliacaoPedidoColeta avaliacaoPedidoColeta = this.findById(idAvaliacaoPedidoColeta);
		
		TarefaDTO tarefa = TiposTarefa.AVALIAR_PEDIDO_COLETA.getConfiguracao().obterTarefa()
			.comRmr(avaliacaoPedidoColeta.getSolicitacao().getMatch().getBusca().getPaciente().getRmr())
			.comObjetoRelacionado(idAvaliacaoPedidoColeta)
			.comStatus(Arrays.asList(StatusTarefa.ABERTA, StatusTarefa.ATRIBUIDA))
			.paraOutroUsuario(true)
			.apply();
		
		if(tarefa != null && StatusTarefa.ATRIBUIDA.equals(tarefa.getStatus())){ 
			throw new BusinessException("avaliacao.pedido.coleta.tarefa_atribuida");
		}
		
		TiposTarefa.AVALIAR_PEDIDO_COLETA.getConfiguracao().atribuirTarefa()
			.comTarefa(tarefa)
			.comUsuarioLogado()
			.apply();
		
		return avaliacaoPedidoColeta;
	}
	
	private void criarTarefaMedicoRedomeParaAvaliarDoador(Long rmr, Long avaliacaoResultadoWorkupId) {
		TiposTarefa.ANALISE_MEDICA_DOADOR_COLETA.getConfiguracao()
			.criarTarefa()
			.comRmr(rmr)
			.comObjetoRelacionado(avaliacaoResultadoWorkupId)
			.apply();
	}

	
	private List<CampoMensagem> validar(AvaliacaoPedidoColeta avaliacao){
		return new ConstraintViolationTransformer(validator.validate(avaliacao))
				.transform();
	}

	/**
	 * @param pedidoColetaService the pedidoColetaService to set
	 */
	@Autowired
	public void setPedidoColetaService(PedidoColetaService pedidoColetaService) {
		this.pedidoColetaService = pedidoColetaService;
	}

	@Override
	public Paciente obterPaciente(AvaliacaoPedidoColeta avaliacaoPedidoColeta) {		
		return avaliacaoPedidoColeta.getSolicitacao().getMatch().getBusca().getPaciente();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public String[] obterParametros(AvaliacaoPedidoColeta avaliacaoPedidoColeta) {
		IDoador doador = (IDoador) avaliacaoPedidoColeta.getSolicitacao().getMatch().getDoador();
		return StringUtils.split(doador.getIdentificacao().toString());
	}
	
	@Override
	public LogEvolucao criarLog(AvaliacaoPedidoColeta avaliacaoPedidoColeta, TipoLogEvolucao tipoLog, Perfis[] perfisExcluidos) {
		if (tipoLog == null || TipoLogEvolucao.INDEFINIDO.equals(tipoLog)) {
			
			if (avaliacaoPedidoColeta.getPedidoProssegue()) {
				tipoLog = TipoLogEvolucao.RESULTADO_PEDIDO_WORKUP_INVIAVEL_APROVADO_PARA_DOADOR;
			}
			else {
				tipoLog = TipoLogEvolucao.RESULTADO_PEDIDO_WORKUP_INVIAVEL_NAO_APROVADO_PARA_DOADOR;
			}
			
		}

		return super.criarLog(avaliacaoPedidoColeta, tipoLog, perfisExcluidos);
	}
	
	
}

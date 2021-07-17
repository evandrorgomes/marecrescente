package br.org.cancer.redome.workup.service.impl;

import java.time.LocalDateTime;
import java.util.Arrays;

import com.fasterxml.jackson.core.JsonProcessingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.redome.workup.dto.CriarLogEvolucaoDTO;
import br.org.cancer.redome.workup.dto.DetalheLogisticaInternacionalColetaDTO;
import br.org.cancer.redome.workup.dto.DoadorDTO;
import br.org.cancer.redome.workup.dto.ListaTarefaDTO;
import br.org.cancer.redome.workup.dto.ProcessoDTO;
import br.org.cancer.redome.workup.dto.SolicitacaoDTO;
import br.org.cancer.redome.workup.dto.SolicitacaoWorkupDTO;
import br.org.cancer.redome.workup.dto.TarefaDTO;
import br.org.cancer.redome.workup.dto.TipoTarefaDTO;
import br.org.cancer.redome.workup.exception.BusinessException;
import br.org.cancer.redome.workup.feign.client.ILogEvolucaoFeign;
import br.org.cancer.redome.workup.feign.client.ISolicitacaoFeign;
import br.org.cancer.redome.workup.helper.ITarefaHelper;
import br.org.cancer.redome.workup.model.PedidoColeta;
import br.org.cancer.redome.workup.model.PedidoLogisticaMaterialColetaInternacional;
import br.org.cancer.redome.workup.model.PedidoWorkup;
import br.org.cancer.redome.workup.model.StatusPedidoLogistica;
import br.org.cancer.redome.workup.model.domain.FasesWorkup;
import br.org.cancer.redome.workup.model.domain.Perfis;
import br.org.cancer.redome.workup.model.domain.StatusPedidosLogistica;
import br.org.cancer.redome.workup.model.domain.StatusTarefa;
import br.org.cancer.redome.workup.model.domain.TipoLogEvolucao;
import br.org.cancer.redome.workup.model.domain.TipoProcesso;
import br.org.cancer.redome.workup.model.domain.TiposTarefa;
import br.org.cancer.redome.workup.model.security.Usuario;
import br.org.cancer.redome.workup.persistence.IPedidoLogisticaMaterialColetaInternacionalRepository;
import br.org.cancer.redome.workup.persistence.IRepository;
import br.org.cancer.redome.workup.service.IPedidoColetaService;
import br.org.cancer.redome.workup.service.IPedidoLogisticaMaterialColetaInternacionalService;
import br.org.cancer.redome.workup.service.IPedidoWorkupService;
import br.org.cancer.redome.workup.service.IUsuarioService;
import br.org.cancer.redome.workup.service.impl.custom.AbstractService;

/**
 * Classe de acesso as funcionalidades que envolvem a entidade Pedido Logistica Material Workup.
 * 
 * @author ergomes
 *
 */
@Service
@Transactional
public class PedidoLogisticaMaterialColetaInternacionalService extends AbstractService<PedidoLogisticaMaterialColetaInternacional, Long> implements IPedidoLogisticaMaterialColetaInternacionalService {

	private static final Logger LOGGER = LoggerFactory.getLogger(PedidoLogisticaMaterialColetaInternacionalService.class);
	
	@Autowired
	private IPedidoLogisticaMaterialColetaInternacionalRepository pedidoLogisticaMaterialColetaInternacionalRepository;
	
	@Autowired
	@Lazy(true)
	private ISolicitacaoFeign solicitacaoFeign;

	@Autowired
	@Lazy(true)
	private IPedidoWorkupService pedidoWorkupService;

	@Autowired
	@Lazy(true)
	private IPedidoColetaService pedidoColetaService; 
	
	@Autowired
	@Lazy(true)
	private ITarefaHelper tarefaHelper;

	@Autowired
	@Lazy(true)
	private IUsuarioService usuarioService;
	
	@Autowired
	@Lazy(true)	
	private ILogEvolucaoFeign logEvolucaoFeign;
			
	
	@Override
	public IRepository<PedidoLogisticaMaterialColetaInternacional, Long> getRepository() {
		return pedidoLogisticaMaterialColetaInternacionalRepository;
	}
	
	@Override
	public DetalheLogisticaInternacionalColetaDTO obterPedidoLogisticaMaterialColetaInternacional(Long idPedidoLogistica) {
		
		PedidoLogisticaMaterialColetaInternacional pedidoLogisticaRecuperado = findById(idPedidoLogistica);
		SolicitacaoDTO solicitacao = solicitacaoFeign.obterSolicitacao(pedidoLogisticaRecuperado.getSolicitacao());
		PedidoWorkup pedidoWorkup = pedidoWorkupService.obterPedidoWorkupPorSolicitacao(solicitacao.getId());
		
		return	DetalheLogisticaInternacionalColetaDTO.builder()
			.idPedidoLogistica(idPedidoLogistica)
			.idTipoDoador(solicitacao.getMatch().getDoador().getIdTipoDoador())
			.idCentroTransplante(pedidoWorkup.getCentroTransplante())
			.nomeCentroTransplante(solicitacao.getMatch().getBusca().getCentroTransplante().getNome())
			.nomeCourier(pedidoLogisticaRecuperado.getNomeCourier())
			.dataChegada(pedidoLogisticaRecuperado.getDataChegada())
			.dataEmbarque(pedidoLogisticaRecuperado.getDataEmbarque())
			.retiradaLocal(pedidoLogisticaRecuperado.getLocalRetirada())
			.retiradaHawb(pedidoLogisticaRecuperado.getHawbInternacional())
			.passaporteCourier(pedidoLogisticaRecuperado.getPassaporteCourier())
			.retiradaIdDoador(pedidoLogisticaRecuperado.getIdentificacaLocalInternacional())
			.dataColeta(pedidoWorkup.getDataColeta())
			.build();
	}
	
	private PedidoLogisticaMaterialColetaInternacional obterPedidoLogistica(Long id) {
		if (id == null) {
			throw new BusinessException("erro.id.nulo");
		}
		return pedidoLogisticaMaterialColetaInternacionalRepository.findById(id).orElseThrow(() -> new BusinessException("erro.nao.existe", "Pedido de Logistica"));		
	}
		
	@Override
	public PedidoLogisticaMaterialColetaInternacional obterPedidoLogisticaEmAberto(Long id) {
		PedidoLogisticaMaterialColetaInternacional pedidoLogistica = obterPedidoLogistica(id);
		pedidoLogistica.setSolicitacao(pedidoLogistica.getPedidoColeta().getSolicitacao());
		if (!pedidoLogistica.getStatus().getId().equals(StatusPedidosLogistica.ABERTO.getId())) {
			throw new BusinessException("erro.pedido.logistica.finalizado.ou.cancelado",  
					pedidoLogistica.getStatus().getId().equals(StatusPedidosLogistica.FECHADO.getId()) ? "já realizado" : "cancelado" );
		}
		return pedidoLogistica;
	}
	
	@Override
	public void salvarPedidoLogisticaMaterialColetaInternacional(Long idPedidoLogistica, DetalheLogisticaInternacionalColetaDTO detalhe) {
		final Usuario usuarioLogado = usuarioService.obterUsuarioLogado();
		PedidoLogisticaMaterialColetaInternacional pedidoLogistica = obterPedidoLogisticaEmAberto(idPedidoLogistica);
		if(pedidoLogistica == null){
			LOGGER.error("Erro ao agendar logística de material. "
					+ "Não foi possível encontrar o pedido de logística com ID: " + detalhe.getIdPedidoLogistica());
			throw new BusinessException("erro.requisicao");
		}
		
		pedidoLogistica.setUsuarioResponsavel(usuarioLogado.getId());
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
	public void criarPedidoLogisticaMaterialColetaInternacional(Long idPedidoColeta) {
		
		if(idPedidoColeta == null){
			throw new BusinessException("erro.requisicao");
		}
		PedidoColeta pedidoColeta = pedidoColetaService.obterPedidoColetaPorId(idPedidoColeta);
		
		PedidoLogisticaMaterialColetaInternacional pedidoLogistica = PedidoLogisticaMaterialColetaInternacional.builder()
				.pedidoColeta(pedidoColeta)				
				.status(new StatusPedidoLogistica(StatusPedidosLogistica.ABERTO.getId()))
				.dataCriacao(LocalDateTime.now())
				.build();

		save(pedidoLogistica);
		
		SolicitacaoWorkupDTO solicitacao = solicitacaoFeign.obterSolicitacaoWorkup(pedidoColeta.getSolicitacao());
		
		criarTarefaInformarLogisticaColetaInternacional(pedidoLogistica.getId(), solicitacao);
	}

	
	@Override
	public void finalizarPedidoLogisticaMaterialColetaInternacional(Long idPedidoLogistica) throws JsonProcessingException {
		
		PedidoLogisticaMaterialColetaInternacional pedidoLogistica = obterPedidoLogisticaEmAberto(idPedidoLogistica);
		SolicitacaoWorkupDTO solicitacao = solicitacaoFeign.atualizarFaseWorkup(pedidoLogistica.getSolicitacao(), 
				FasesWorkup.AGUARDANDO_RECEBIMENTO_COLETA.getId());
		
		this.fecharTarefaLogisticaColetaInternacional(idPedidoLogistica, solicitacao);
		this.criarTarefaInformarRecebimentoColeta(pedidoLogistica.getPedidoColeta().getId(), solicitacao);

		criarLogEvolucao(TipoLogEvolucao.RECEBIMENTO_COLETA_PARA_DOADOR, 
				solicitacao.getMatch().getBusca().getPaciente().getRmr(), 
				solicitacao.getMatch().getDoador());
	}

	private void fecharTarefaLogisticaColetaInternacional(Long idPedidoLogistica, SolicitacaoWorkupDTO solicitacao) throws JsonProcessingException {
		Long rmr = solicitacao.getMatch().getBusca().getPaciente().getRmr();
		
		ListaTarefaDTO filtroDTO = ListaTarefaDTO.builder()
				.idsTiposTarefa(Arrays.asList(TiposTarefa.INFORMAR_LOGISTICA_MATERIAL_COLETA_INTERNACIONAL.getId()))
				.statusTarefa(Arrays.asList(StatusTarefa.ATRIBUIDA.getId()))
				.idUsuarioLogado(usuarioService.obterIdUsuarioLogado())
				.rmr(rmr)
				.relacaoEntidadeId(idPedidoLogistica)
				.tipoProcesso(TipoProcesso.BUSCA.getId())
				.build();
		
		tarefaHelper.encerrarTarefa(filtroDTO, false);
	}

	private void criarTarefaInformarRecebimentoColeta(Long idPedidoColeta, SolicitacaoWorkupDTO solicitacao) {
		Long rmr = solicitacao.getMatch().getBusca().getPaciente().getRmr();

		ProcessoDTO processo = new ProcessoDTO(TipoProcesso.BUSCA);
		processo.setRmr(rmr);

		TarefaDTO tarefa = TarefaDTO.builder()
				.processo(processo) 
				.tipoTarefa(new TipoTarefaDTO(TiposTarefa.INFORMAR_RECEBIMENTO_COLETA.getId()))
				.perfilResponsavel(Perfis.MEDICO_TRANSPLANTADOR.getId())
				.status(StatusTarefa.ABERTA.getId())
				.relacaoEntidade(idPedidoColeta)
				.relacaoParceiro(solicitacao.getIdCentroTransplante())
				.build(); 
 		
		tarefaHelper.criarTarefa(tarefa);		
	}

	private void criarTarefaInformarLogisticaColetaInternacional(Long idPedidoLogistica, SolicitacaoWorkupDTO solicitacao) {
		Long rmr = solicitacao.getMatch().getBusca().getPaciente().getRmr();

		ProcessoDTO processo = new ProcessoDTO(TipoProcesso.BUSCA);
		processo.setRmr(rmr);

		TarefaDTO tarefa = TarefaDTO.builder()
				.processo(processo) 
				.tipoTarefa(new TipoTarefaDTO(TiposTarefa.INFORMAR_LOGISTICA_MATERIAL_COLETA_INTERNACIONAL.getId()))
				.perfilResponsavel(Perfis.ANALISTA_WORKUP_INTERNACIONAL.getId())
				.status(StatusTarefa.ATRIBUIDA.getId())
				.relacaoEntidade(idPedidoLogistica)
				.usuarioResponsavel(solicitacao.getUsuarioResponsavel().getId())
				.build(); 
 		
		tarefaHelper.criarTarefa(tarefa);		
	}

	private void criarLogEvolucao(TipoLogEvolucao tipoLogEvolucao,Long rmr, DoadorDTO doador) {
		
		String[] parametros = {doador.getIdentificacao()};
		
		logEvolucaoFeign.criarLogEvolucao(CriarLogEvolucaoDTO.builder()
				.tipo(tipoLogEvolucao.name())
				.rmr(rmr)
				.parametros(parametros )
				.build());
				
	}

}

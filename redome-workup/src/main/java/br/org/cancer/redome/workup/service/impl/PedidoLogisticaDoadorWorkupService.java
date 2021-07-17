package br.org.cancer.redome.workup.service.impl;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.org.cancer.redome.workup.dto.CriarLogEvolucaoDTO;
import br.org.cancer.redome.workup.dto.ListaTarefaDTO;
import br.org.cancer.redome.workup.dto.LogisticaDoadorWorkupDTO;
import br.org.cancer.redome.workup.dto.ProcessoDTO;
import br.org.cancer.redome.workup.dto.SolicitacaoWorkupDTO;
import br.org.cancer.redome.workup.dto.TarefaDTO;
import br.org.cancer.redome.workup.dto.TarefaDTO.TarefaDTOBuilder;
import br.org.cancer.redome.workup.dto.TipoTarefaDTO;
import br.org.cancer.redome.workup.exception.BusinessException;
import br.org.cancer.redome.workup.feign.client.ILogEvolucaoFeign;
import br.org.cancer.redome.workup.feign.client.ISolicitacaoFeign;
import br.org.cancer.redome.workup.helper.ITarefaHelper;
import br.org.cancer.redome.workup.model.ArquivoVoucherLogistica;
import br.org.cancer.redome.workup.model.PedidoLogisticaDoadorWorkup;
import br.org.cancer.redome.workup.model.PedidoWorkup;
import br.org.cancer.redome.workup.model.StatusPedidoLogistica;
import br.org.cancer.redome.workup.model.domain.FasesWorkup;
import br.org.cancer.redome.workup.model.domain.Perfis;
import br.org.cancer.redome.workup.model.domain.StatusPedidosLogistica;
import br.org.cancer.redome.workup.model.domain.StatusTarefa;
import br.org.cancer.redome.workup.model.domain.TipoLogEvolucao;
import br.org.cancer.redome.workup.model.domain.TipoProcesso;
import br.org.cancer.redome.workup.model.domain.TiposTarefa;
import br.org.cancer.redome.workup.model.domain.TiposVoucher;
import br.org.cancer.redome.workup.persistence.IPedidoLogisticaDoadorWorkupRepository;
import br.org.cancer.redome.workup.persistence.IRepository;
import br.org.cancer.redome.workup.service.IArquivoVoucherLogisticaService;
import br.org.cancer.redome.workup.service.IPedidoLogisticaDoadorWorkupService;
import br.org.cancer.redome.workup.service.ITransporteTerrestreService;
import br.org.cancer.redome.workup.service.IUsuarioService;
import br.org.cancer.redome.workup.service.impl.custom.AbstractService;

/**
 * Classe de acesso as funcionalidades que envolvem a entidade DistribuicaoWorkup.
 * 
 * @author bruno.sousa
 *
 */
@Service
@Transactional
public class PedidoLogisticaDoadorWorkupService extends AbstractService<PedidoLogisticaDoadorWorkup, Long> implements IPedidoLogisticaDoadorWorkupService {

	private static final Logger LOGGER = LoggerFactory.getLogger(PedidoLogisticaDoadorWorkupService.class);
	
	@Autowired
	private IPedidoLogisticaDoadorWorkupRepository pedidoLogisticaDoadorWorkupRepository;
	
	@Autowired
	private ITransporteTerrestreService transporteTerrestreService;
	
	@Autowired
	private IArquivoVoucherLogisticaService arquivoVoucherLogisticaService;
	
	@Autowired
	@Lazy(true)
	private ISolicitacaoFeign solicitacaoFeign;
	
	@Autowired
	@Lazy(true)
	private ITarefaHelper tarefaHelper;

	@Autowired
	@Lazy(true)	
	private ILogEvolucaoFeign logEvolucaoFeign;
	
	@Autowired
	private IUsuarioService usuarioService;
			
	
	@Override
	public IRepository<PedidoLogisticaDoadorWorkup, Long> getRepository() {
		return pedidoLogisticaDoadorWorkupRepository;
	}
	
	private PedidoLogisticaDoadorWorkup obterPedidoLogistica(Long id) {
		if (id == null) {
			throw new BusinessException("erro.id.nulo");
		}
		return pedidoLogisticaDoadorWorkupRepository.findById(id).orElseThrow(() -> new BusinessException("erro.nao.existe", "Pedido de Logistica"));		
	}
		
	@Override
	public PedidoLogisticaDoadorWorkup obterPedidoLogisticaEmAberto(Long id) {
		PedidoLogisticaDoadorWorkup pedidoLogistica = obterPedidoLogistica(id);
		pedidoLogistica.setSolicitacao(pedidoLogistica.getPedidoWorkup().getSolicitacao());
		if (!pedidoLogistica.getStatus().getId().equals(StatusPedidosLogistica.ABERTO.getId())) {
			throw new BusinessException("erro.pedido.logistica.finalizado.ou.cancelado",  
					pedidoLogistica.getStatus().getId().equals(StatusPedidosLogistica.FECHADO.getId()) ? "já realizado" : "cancelado" );
		}
		return pedidoLogistica;
	}
		
	@Override
	public void atualizarLogisticaDoadorWorkup(Long id, LogisticaDoadorWorkupDTO logistica) {
		salvarLogisticaDoadorWorkup(id, logistica);
	}
		
	private PedidoLogisticaDoadorWorkup salvarLogisticaDoadorWorkup(Long id, LogisticaDoadorWorkupDTO logistica) {
		PedidoLogisticaDoadorWorkup pedidoLogistica = obterPedidoLogisticaEmAberto(id);
				
		pedidoLogistica.setObservacao(logistica.getObservacao());
		pedidoLogistica.setDataAtualizacao(LocalDateTime.now());
		
		save(pedidoLogistica);
		
		if (CollectionUtils.isNotEmpty(logistica.getTransporteTerrestre())) {
			logistica.getTransporteTerrestre().stream()
			    .filter(transporte -> !transporte.getExcluido() && transporte.getId() == null )
				.forEach(transporte -> {
					transporte.setPedidoLogistica(pedidoLogistica);
					transporteTerrestreService.save(transporte);
				});
			
			logistica.getTransporteTerrestre().stream()
		    	.filter(transporte -> transporte.getExcluido() && transporte.getId() != null )
		    	.forEach(transporte -> transporteTerrestreService.excluirPorId(transporte.getId()));	
		}
		
		if (CollectionUtils.isNotEmpty(logistica.getAereos())) {
			logistica.getAereos().stream()
				.filter(voucher -> !voucher.getExcluido() && voucher.getId() == null )
				.forEach(voucher -> {
					voucher.setPedidoLogistica(pedidoLogistica);
					arquivoVoucherLogisticaService.save(voucher);
				});
			
			logistica.getAereos().stream()
				.filter(voucher -> voucher.getExcluido() && voucher.getId() != null )
				.forEach(voucher -> arquivoVoucherLogisticaService.excluirPorId(voucher.getId()));
		}
		
		if (CollectionUtils.isNotEmpty(logistica.getHospedagens())) {
			logistica.getHospedagens().stream()
				.filter(voucher -> !voucher.getExcluido() && voucher.getId() == null )
				.forEach(voucher -> {
					voucher.setPedidoLogistica(pedidoLogistica);
					arquivoVoucherLogisticaService.save(voucher);
				});
		
			logistica.getHospedagens().stream()
				.filter(voucher -> voucher.getExcluido() && voucher.getId() != null )
				.forEach(voucher -> arquivoVoucherLogisticaService.excluirPorId(voucher.getId()));
		}
		
		return obterPedidoLogisticaEmAberto(id);		
		
	}
	
	
	/**
	 * Cria pedido de logística doador, associado a pedido de workup (logística para o doador).
	 * 
	 * @param pedidoWorkup pedido de workup, se for uma logística envolvendo workup.
	 * 
	 * @return o pedido de logística doador workup criado.
	 */
	@Override
	public PedidoLogisticaDoadorWorkup criarPedidoLogisticaDoadorWorkup(PedidoWorkup pedidoWorkup) {
		
		LOGGER.info("Criar pedido de logistica doador workup após a aprovação do plano de workup");
		
		PedidoLogisticaDoadorWorkup logisticaDoador = PedidoLogisticaDoadorWorkup.builder()
			.pedidoWorkup(pedidoWorkup)
			.status(new StatusPedidoLogistica(StatusPedidosLogistica.ABERTO))
			.dataCriacao(LocalDateTime.now())
			.build();

		return save(logisticaDoador);
	}
	
	@Override
	public void encerrarLogisticaDoadorWorkup(Long id, LogisticaDoadorWorkupDTO logistica) throws Exception{
		PedidoLogisticaDoadorWorkup pedidoLogistica = salvarLogisticaDoadorWorkup(id, logistica);
		
		pedidoLogistica.setStatus(new StatusPedidoLogistica(StatusPedidosLogistica.FECHADO));
		save(pedidoLogistica);
		
		SolicitacaoWorkupDTO solicitacao = solicitacaoFeign.atualizarFaseWorkup(pedidoLogistica.getPedidoWorkup().getSolicitacao(), FasesWorkup.AGUARDANDO_RESULTADO_WORKUP.getId());
				
		fecharTarefaInformarLogisticaDoadorWorkup(pedidoLogistica.getId(), solicitacao);
		criarTarefaResultadoWorkupNacional(pedidoLogistica.getPedidoWorkup().getId(), solicitacao);
		criarLogEvolucao(solicitacao);
		
	}
	
	
	private void criarLogEvolucao(SolicitacaoWorkupDTO solicitacao) {
		final Long rmr = solicitacao.getMatch().getBusca().getPaciente().getRmr();
		final String identificacao = solicitacao.getMatch().getDoador().getIdentificacao();
		final CriarLogEvolucaoDTO logEvolucaoDTO = CriarLogEvolucaoDTO.builder()
				.rmr(rmr)
				.tipo(TipoLogEvolucao.LOGISTICA_DOADOR_WORKUP_CONFIRMADA_PARA_DOADOR.name())
				.parametros(StringUtils.split(identificacao))
				.build();
		
		logEvolucaoFeign.criarLogEvolucao(logEvolucaoDTO);
		
	}

	private void fecharTarefaInformarLogisticaDoadorWorkup(Long idPedidoLogistica, SolicitacaoWorkupDTO solicitacao) throws JsonProcessingException {
		final Long rmr = solicitacao.getMatch().getBusca().getPaciente().getRmr(); 
		
		ListaTarefaDTO filtroDTO = ListaTarefaDTO.builder()
				.idsTiposTarefa(Arrays.asList(TiposTarefa.INFORMAR_LOGISTICA_DOADOR_WORKUP.getId()))
				.statusTarefa(Arrays.asList(StatusTarefa.ATRIBUIDA.getId()))
				.idUsuarioLogado(usuarioService.obterIdUsuarioLogado())
				.rmr(rmr)
				.relacaoEntidadeId(idPedidoLogistica)
				.tipoProcesso(TipoProcesso.BUSCA.getId())
				.build();
		
		tarefaHelper.encerrarTarefa(filtroDTO, false);
		
	}
	
	private void criarTarefaResultadoWorkupNacional(Long idPedidoWorkup, SolicitacaoWorkupDTO solicitacao) {
		
		Long rmr = solicitacao.getMatch().getBusca().getPaciente().getRmr();

		TarefaDTOBuilder tarefa = TarefaDTO.builder()
				.processo(new ProcessoDTO(TipoProcesso.BUSCA, rmr))
				.relacaoEntidade(idPedidoWorkup)		
				.tipoTarefa(new TipoTarefaDTO(TiposTarefa.INFORMAR_RESULTADO_WORKUP_NACIONAL.getId()))
				.perfilResponsavel(Perfis.MEDICO_CENTRO_COLETA.getId())
				.relacaoParceiro(solicitacao.getIdCentroColeta())
				.status(StatusTarefa.ABERTA.getId());
		
		tarefaHelper.criarTarefa(tarefa.build());
	}
	
	@Override
	public LogisticaDoadorWorkupDTO obterPedidoLogisticaCustomizado(Long id) {
		
		PedidoLogisticaDoadorWorkup pedidoLogistica = obterPedidoLogistica(id);
		
		List<ArquivoVoucherLogistica> aereos = pedidoLogistica.getVouchers().stream().filter(aereo -> aereo.getTipo().equals(TiposVoucher.TRANSPORTE_AEREO.getCodigo())).collect(Collectors.toList());
		List<ArquivoVoucherLogistica> hospedagens = pedidoLogistica.getVouchers().stream().filter(hosp -> hosp.getTipo().equals(TiposVoucher.HOSPEDAGEM.getCodigo())).collect(Collectors.toList());
		
		LogisticaDoadorWorkupDTO logistica = LogisticaDoadorWorkupDTO.builder()
			.idPedidoWorkup(pedidoLogistica.getPedidoWorkup().getId())	
			.aereos(aereos)
			.hospedagens(hospedagens)
			.transporteTerrestre(pedidoLogistica.getTransporteTerrestre())
			.observacao(pedidoLogistica.getObservacao())
			.build();
		
		return logistica;
	}
}

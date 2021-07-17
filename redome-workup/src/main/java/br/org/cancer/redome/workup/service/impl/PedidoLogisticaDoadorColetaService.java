package br.org.cancer.redome.workup.service.impl;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
import br.org.cancer.redome.workup.dto.LogisticaDoadorColetaDTO;
import br.org.cancer.redome.workup.dto.ProcessoDTO;
import br.org.cancer.redome.workup.dto.SolicitacaoDTO;
import br.org.cancer.redome.workup.dto.SolicitacaoWorkupDTO;
import br.org.cancer.redome.workup.dto.TarefaDTO;
import br.org.cancer.redome.workup.dto.TarefaDTO.TarefaDTOBuilder;
import br.org.cancer.redome.workup.dto.TipoTarefaDTO;
import br.org.cancer.redome.workup.exception.BusinessException;
import br.org.cancer.redome.workup.feign.client.ICentroTransplanteFeign;
import br.org.cancer.redome.workup.feign.client.ILogEvolucaoFeign;
import br.org.cancer.redome.workup.feign.client.IPedidoTransporteFeign;
import br.org.cancer.redome.workup.feign.client.ISolicitacaoFeign;
import br.org.cancer.redome.workup.helper.ITarefaHelper;
import br.org.cancer.redome.workup.model.ArquivoVoucherLogistica;
import br.org.cancer.redome.workup.model.PedidoColeta;
import br.org.cancer.redome.workup.model.PedidoLogisticaDoadorColeta;
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
import br.org.cancer.redome.workup.persistence.IPedidoLogisticaDoadorColetaRepository;
import br.org.cancer.redome.workup.persistence.IRepository;
import br.org.cancer.redome.workup.service.IPedidoLogisticaDoadorColetaService;
import br.org.cancer.redome.workup.service.IPedidoLogisticaMaterialColetaNacionalService;
import br.org.cancer.redome.workup.service.IUsuarioService;
import br.org.cancer.redome.workup.service.impl.custom.AbstractService;

/**
 * Classe de acesso as funcionalidades que envolvem a entidade PedidoLogisticaMaterial.
 * 
 * @author ergomes
 *
 */
@Service
@Transactional
public class PedidoLogisticaDoadorColetaService extends AbstractService<PedidoLogisticaDoadorColeta, Long> implements IPedidoLogisticaDoadorColetaService {

	private static final Logger LOGGER = LoggerFactory.getLogger(PedidoLogisticaDoadorColetaService.class);
	
	@Autowired
	private IPedidoLogisticaDoadorColetaRepository pedidoLogisticaDoadorColetaRepository;
	
	@Autowired
	@Lazy(true)
	private ITarefaHelper tarefaHelper;

	@Autowired
	@Lazy(true)
	private ISolicitacaoFeign solicitacaoFeign;

	@Autowired
	@Lazy(true)
	private IPedidoTransporteFeign pedidoTransporteFeign;
	
	@Autowired
	@Lazy(true)
	private ICentroTransplanteFeign centroTransplanteFeign;
	
	@Autowired
	@Lazy(true)	
	private ILogEvolucaoFeign logEvolucaoFeign;
	
//	@Autowired
//	private ITransporteTerrestreService transporteTerrestreService;
////	
//	@Autowired
//	private IArquivoVoucherLogisticaService arquivoVoucherLogisticaService;
	
	@Autowired
	private PedidoWorkupService pedidoWorkupService;	
	
	@Autowired
	private IUsuarioService usuarioService;
	
	@Autowired
	private IPedidoLogisticaMaterialColetaNacionalService pedidoLogisticaMaterialColetaNacionalService;
	
	
	@Override
	public IRepository<PedidoLogisticaDoadorColeta, Long> getRepository() {
		return pedidoLogisticaDoadorColetaRepository;
	}
	
	@Override
	public PedidoLogisticaDoadorColeta obterPedidoLogisticaDoadorColetaEmAberto(Long id) {
		PedidoLogisticaDoadorColeta pedidoLogistica = this.obterPedidoLogisticaDoadorColeta(id);
		pedidoLogistica.setSolicitacao(pedidoLogistica.getSolicitacao());
		if (!pedidoLogistica.getStatus().getId().equals(StatusPedidosLogistica.ABERTO.getId())) {
			throw new BusinessException("erro.pedido.logistica.finalizado.ou.cancelado",  
					pedidoLogistica.getStatus().getId().equals(StatusPedidosLogistica.FECHADO.getId()) ? "já realizado" : "cancelado" );
		}
		return pedidoLogistica;
	}

	private PedidoLogisticaDoadorColeta obterPedidoLogisticaDoadorColeta(Long id) {
		if (id == null) {
			throw new BusinessException("erro.id.nulo");
		}
		return pedidoLogisticaDoadorColetaRepository.findById(id).orElseThrow(() -> new BusinessException("erro.nao.existe", "Pedido de Logistica"));		
	}

	
	@Override
	public LogisticaDoadorColetaDTO obterPedidoLogisticaDoadorColetaCustomizado(Long id) {
		
		PedidoLogisticaDoadorColeta pedidoLogistica = this.obterPedidoLogisticaDoadorColeta(id);
		SolicitacaoDTO solicitacao = this.solicitacaoFeign.obterSolicitacao(pedidoLogistica.getSolicitacao());
		PedidoWorkup pedidoWorkup = pedidoWorkupService.obterPedidoWorkupPorSolicitacao(solicitacao.getId());
		
		List<ArquivoVoucherLogistica> aereos = pedidoLogistica.getVouchers().stream().filter(aereo -> aereo.getTipo().equals(TiposVoucher.TRANSPORTE_AEREO.getCodigo())).collect(Collectors.toList());
		List<ArquivoVoucherLogistica> hospedagens = pedidoLogistica.getVouchers().stream().filter(hosp -> hosp.getTipo().equals(TiposVoucher.HOSPEDAGEM.getCodigo())).collect(Collectors.toList());
		
		LogisticaDoadorColetaDTO logistica = LogisticaDoadorColetaDTO.builder()
			.idPedidoColeta(pedidoLogistica.getPedidoColeta().getId())
			.idCentroColeta(solicitacao.getCentroColeta().getId())
			.dataColeta(pedidoWorkup.getDataColeta())
			.aereos(aereos)
			.hospedagens(hospedagens)
			.transporteTerrestre(pedidoLogistica.getTransporteTerrestre())
			.observacao(pedidoLogistica.getObservacao())
			.build();
		
		return logistica;
	}

	@Override
	public void atualizarLogisticaDoadorColeta(Long id, LogisticaDoadorColetaDTO logistica) {
		salvarLogisticaDoadorColeta(id, logistica);
	}
		
	private PedidoLogisticaDoadorColeta salvarLogisticaDoadorColeta(Long id, LogisticaDoadorColetaDTO logistica) {
		
		PedidoLogisticaDoadorColeta pedidoLogistica = this.obterPedidoLogisticaDoadorColeta(id);

		pedidoLogistica.setDataAtualizacao(LocalDateTime.now());
		pedidoLogistica.setObservacao(logistica.getObservacao());
				
		if(!logistica.isProsseguirComPedidoLogistica()) {
			pedidoLogistica.setJustificativa(logistica.getJustificativa());
		}
		
		save(pedidoLogistica);

		/* ESPERANDO A CRIAÇÃO DO VOLUME NO SERVIDOR */
		
//		if (CollectionUtils.isNotEmpty(logistica.getTransporteTerrestre())) {
//			logistica.getTransporteTerrestre().stream()
//			    .filter(transporte -> !transporte.getExcluido() && transporte.getId() == null )
//				.forEach(transporte -> {
//					transporte.setPedidoLogistica(pedidoLogistica);
//					transporteTerrestreService.save(transporte);
//				});
//			
//			logistica.getTransporteTerrestre().stream()
//		    	.filter(transporte -> transporte.getExcluido() && transporte.getId() != null )
//		    	.forEach(transporte -> transporteTerrestreService.excluirPorId(transporte.getId()));	
//		}
		
//		if (CollectionUtils.isNotEmpty(logistica.getAereos())) {
//			logistica.getAereos().stream()
//				.filter(voucher -> !voucher.getExcluido() && voucher.getId() == null )
//				.forEach(voucher -> {
//					voucher.setPedidoLogistica(pedidoLogistica);
//					arquivoVoucherLogisticaService.save(voucher);
//				});
//			
//			logistica.getAereos().stream()
//				.filter(voucher -> voucher.getExcluido() && voucher.getId() != null )
//				.forEach(voucher -> arquivoVoucherLogisticaService.excluirPorId(voucher.getId()));
//		}
		
//		if (CollectionUtils.isNotEmpty(logistica.getHospedagens())) {
//			logistica.getHospedagens().stream()
//				.filter(voucher -> !voucher.getExcluido() && voucher.getId() == null )
//				.forEach(voucher -> {
//					voucher.setPedidoLogistica(pedidoLogistica);
//					arquivoVoucherLogisticaService.save(voucher);
//				});
//		
//			logistica.getHospedagens().stream()
//				.filter(voucher -> voucher.getExcluido() && voucher.getId() != null )
//				.forEach(voucher -> arquivoVoucherLogisticaService.excluirPorId(voucher.getId()));
//		}
		
		return this.obterPedidoLogisticaDoadorColeta(id);		
	}

	@Override
	public PedidoLogisticaDoadorColeta criarPedidoLogisticaDoadorColeta(PedidoColeta pedidoColeta) {
		
		LOGGER.info("Criar pedido de logistica doador coleta");
		
		PedidoLogisticaDoadorColeta logisticaDoador = PedidoLogisticaDoadorColeta.builder()
			.pedidoColeta(pedidoColeta)
			.status(new StatusPedidoLogistica(StatusPedidosLogistica.ABERTO))
			.dataCriacao(LocalDateTime.now())
			.build();

		return save(logisticaDoador);
	}

	
	@Override
	public void encerrarLogisticaDoadorColeta(Long id, LogisticaDoadorColetaDTO logistica) throws Exception{
		
		PedidoLogisticaDoadorColeta pedidoLogistica = this.salvarLogisticaDoadorColeta(id, logistica);
		pedidoLogistica.setStatus(new StatusPedidoLogistica(StatusPedidosLogistica.FECHADO));
		this.save(pedidoLogistica);
		
		/* Verifica se tem logística de material finalizada dizendo que não há necessidade de logística de material, mudar de fase para infusão. */
		if(this.pedidoLogisticaMaterialColetaNacionalService.pedidoLogisticaEstaFinalizadoSemJustificativa(pedidoLogistica.getPedidoColeta().getId())
				&& logistica.isProsseguirComPedidoLogistica()) {
			
			SolicitacaoWorkupDTO solicitacao = this.solicitacaoFeign.atualizarFaseWorkup(pedidoLogistica.getSolicitacao(), FasesWorkup.AGUARDANDO_RESULTADO_DOADOR_COLETA.getId());
			
			this.fecharTarefaInformarLogisticaDoadorColeta(pedidoLogistica.getId(), solicitacao);
			this.criarTarefaInformarResultadoDoadorColeta(pedidoLogistica.getId(), solicitacao);
			this.criarLogEvolucao(solicitacao);
			
		}else {
			/* Se tiver logística de material não finalizada, não muda de fase. */
			SolicitacaoWorkupDTO solicitacao = this.solicitacaoFeign.obterSolicitacaoWorkup(pedidoLogistica.getSolicitacao());
			this.fecharTarefaInformarLogisticaDoadorColeta(pedidoLogistica.getId(), solicitacao);
		}
		
	}

	private void fecharTarefaInformarLogisticaDoadorColeta(Long idPedidoLogistica, SolicitacaoWorkupDTO solicitacao) throws JsonProcessingException {
		final Long rmr = solicitacao.getMatch().getBusca().getPaciente().getRmr(); 
		
		ListaTarefaDTO filtroDTO = ListaTarefaDTO.builder()
				.idsTiposTarefa(Arrays.asList(TiposTarefa.INFORMAR_LOGISTICA_DOADOR_COLETA.getId()))
				.statusTarefa(Arrays.asList(StatusTarefa.ATRIBUIDA.getId()))
				.idUsuarioLogado(usuarioService.obterIdUsuarioLogado())
				.rmr(rmr)
				.relacaoEntidadeId(idPedidoLogistica)
				.tipoProcesso(TipoProcesso.BUSCA.getId())
				.build();
		
		tarefaHelper.encerrarTarefa(filtroDTO, false);
		
	}

	private void criarTarefaInformarResultadoDoadorColeta(Long idPedidoLogistica, SolicitacaoWorkupDTO solicitacao) {
		
		Long rmr = solicitacao.getMatch().getBusca().getPaciente().getRmr();

		TarefaDTOBuilder tarefa = TarefaDTO.builder()
				.processo(new ProcessoDTO(TipoProcesso.BUSCA, rmr))
				.relacaoEntidade(idPedidoLogistica)		
				.tipoTarefa(new TipoTarefaDTO(TiposTarefa.INFORMAR_RESULTADO_DOADOR_COLETA.getId()))
				.perfilResponsavel(Perfis.MEDICO_CENTRO_COLETA.getId())
				.relacaoParceiro(solicitacao.getIdCentroColeta())
				.status(StatusTarefa.ABERTA.getId());
		
		tarefaHelper.criarTarefa(tarefa.build());
	}

	private void criarLogEvolucao(SolicitacaoWorkupDTO solicitacao) {
		final Long rmr = solicitacao.getMatch().getBusca().getPaciente().getRmr();
		final String identificacao = solicitacao.getMatch().getDoador().getIdentificacao();
		final CriarLogEvolucaoDTO logEvolucaoDTO = CriarLogEvolucaoDTO.builder()
				.rmr(rmr)
				.tipo(TipoLogEvolucao.LOGISTICA_DOADOR_COLETA_CONFIRMADA_PARA_DOADOR.name())
				.parametros(StringUtils.split(identificacao))
				.build();
		
		logEvolucaoFeign.criarLogEvolucao(logEvolucaoDTO);
	}

	@Override
	public boolean pedidoLogisticaDoadorEstaFinalizado(Long idPedidoColeta) {
		return this.pedidoLogisticaDoadorColetaRepository.existePedidoLogisticaDoadorFinalizado(idPedidoColeta);	
	}
}

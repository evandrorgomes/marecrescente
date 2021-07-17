package br.org.cancer.redome.workup.service.impl;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.org.cancer.redome.workup.dto.DetalheLogisticaMaterialAereoDTO;
import br.org.cancer.redome.workup.dto.DetalheLogisticaMaterialDTO;
import br.org.cancer.redome.workup.dto.DoadorDTO;
import br.org.cancer.redome.workup.dto.EnderecoDTO;
import br.org.cancer.redome.workup.dto.ListaTarefaDTO;
import br.org.cancer.redome.workup.dto.LogisticaMaterialTransporteDTO;
import br.org.cancer.redome.workup.dto.PedidoTransporteDTO;
import br.org.cancer.redome.workup.dto.ProcessoDTO;
import br.org.cancer.redome.workup.dto.SolicitacaoDTO;
import br.org.cancer.redome.workup.dto.SolicitacaoWorkupDTO;
import br.org.cancer.redome.workup.dto.StatusPedidoTransporteDTO;
import br.org.cancer.redome.workup.dto.TarefaDTO;
import br.org.cancer.redome.workup.dto.TarefaDTO.TarefaDTOBuilder;
import br.org.cancer.redome.workup.dto.TarefaLogisticaMaterialDTO;
import br.org.cancer.redome.workup.dto.TipoTarefaDTO;
import br.org.cancer.redome.workup.dto.TransportadoraDTO;
import br.org.cancer.redome.workup.exception.BusinessException;
import br.org.cancer.redome.workup.feign.client.ICentroTransplanteFeign;
import br.org.cancer.redome.workup.feign.client.IPedidoTransporteFeign;
import br.org.cancer.redome.workup.feign.client.ISolicitacaoFeign;
import br.org.cancer.redome.workup.helper.ITarefaHelper;
import br.org.cancer.redome.workup.helper.TarefaComparator;
import br.org.cancer.redome.workup.model.PedidoColeta;
import br.org.cancer.redome.workup.model.PedidoLogisticaMaterialColetaNacional;
import br.org.cancer.redome.workup.model.ResultadoWorkup;
import br.org.cancer.redome.workup.model.StatusPedidoLogistica;
import br.org.cancer.redome.workup.model.domain.FasesWorkup;
import br.org.cancer.redome.workup.model.domain.Perfis;
import br.org.cancer.redome.workup.model.domain.StatusPedidosLogistica;
import br.org.cancer.redome.workup.model.domain.StatusPedidosTransporte;
import br.org.cancer.redome.workup.model.domain.StatusTarefa;
import br.org.cancer.redome.workup.model.domain.TipoProcesso;
import br.org.cancer.redome.workup.model.domain.TiposDoador;
import br.org.cancer.redome.workup.model.domain.TiposTarefa;
import br.org.cancer.redome.workup.model.security.Usuario;
import br.org.cancer.redome.workup.persistence.IPedidoLogisticaMaterialColetaNacionalRepository;
import br.org.cancer.redome.workup.persistence.IRepository;
import br.org.cancer.redome.workup.service.IPedidoColetaService;
import br.org.cancer.redome.workup.service.IPedidoLogisticaDoadorColetaService;
import br.org.cancer.redome.workup.service.IPedidoLogisticaMaterialColetaNacionalService;
import br.org.cancer.redome.workup.service.IResultadoWorkupService;
import br.org.cancer.redome.workup.service.IUsuarioService;
import br.org.cancer.redome.workup.service.impl.custom.AbstractService;
import br.org.cancer.redome.workup.util.CustomPageImpl;
import br.org.cancer.redome.workup.util.DateUtils;

/**
 * Classe de acesso as funcionalidades que envolvem a entidade PedidoLogisticaMaterial.
 * 
 * @author ergomes
 *
 */
@Service
@Transactional
public class PedidoLogisticaMaterialColetaNacionalService extends AbstractService<PedidoLogisticaMaterialColetaNacional, Long> implements IPedidoLogisticaMaterialColetaNacionalService {

	private static final Logger LOGGER = LoggerFactory.getLogger(PedidoLogisticaMaterialColetaNacionalService.class);
	
	@Autowired
	private IPedidoLogisticaMaterialColetaNacionalRepository pedidoLogisticaMaterialColetaNacionalRepository;
	
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
	private IPedidoColetaService pedidoColetaService;
	
	@Autowired
	private IResultadoWorkupService resultadoWorkupService;
	
	@Autowired
	private IUsuarioService usuarioService;
	
	@Autowired
	private IPedidoLogisticaDoadorColetaService pedidoLogisticaDoadorColetaService; 

	
	@Override
	public IRepository<PedidoLogisticaMaterialColetaNacional, Long> getRepository() {
		return pedidoLogisticaMaterialColetaNacionalRepository;
	}

	@Override
	public Page<TarefaLogisticaMaterialDTO> listarTarefasPedidoLogisticaMaterialColetaNacional(int pagina, int quantidadeRegistros) throws JsonProcessingException {

		Usuario usuarioLogado = usuarioService.obterUsuarioLogado();
		
		ListaTarefaDTO filtro = ListaTarefaDTO.builder()
				.idsTiposTarefa(Arrays.asList(TiposTarefa.INFORMAR_LOGISTICA_MATERIAL_COLETA_NACIONAL.getId()))
				.perfilResponsavel(Arrays.asList(Perfis.ANALISTA_LOGISTICA.getId()))
				.statusTarefa(Arrays.asList(StatusTarefa.ABERTA.getId(), StatusTarefa.ATRIBUIDA.getId()))
				.idUsuarioLogado(usuarioLogado.getId())
				.build();
		
		List<TarefaDTO> tarefas = tarefaHelper.listarTarefas(filtro);
		if (CollectionUtils.isEmpty(tarefas)) {
			return new CustomPageImpl<>();
		}
		
		List<TarefaLogisticaMaterialDTO> lista = tarefas.stream()
				.sorted(new TarefaComparator())
				.skip(pagina * quantidadeRegistros)
				.limit(quantidadeRegistros)
				.map(tarefa -> {

					PedidoLogisticaMaterialColetaNacional pedidoLogistica = this.obterPedidoLogisticaPorId(tarefa.getRelacaoEntidade());
					PedidoColeta pedidoColeta = this.pedidoColetaService.obterPedidoColetaPorId(pedidoLogistica.getPedidoColeta().getId());
					SolicitacaoDTO solicitacao = this.solicitacaoFeign.obterSolicitacao(pedidoColeta.getSolicitacao());
					final Long rmr = solicitacao.getMatch().getBusca().getPaciente().getRmr();
					DoadorDTO doador = solicitacao.getMatch().getDoador();

					return TarefaLogisticaMaterialDTO.builder()
							.idPedidoLogistica(tarefa.getRelacaoEntidade())
							.tipoTarefaLogisticaMaterial(tarefa.getTipoTarefa().getId())
							.tipoLogistica(pedidoLogistica.getTipo())
							.rmr(rmr)
							.identificacaoDoador(doador.getIdentificacao())
							.nomeDoador(doador.getNome())
							.nomeResponsavel(usuarioLogado.getNome())
							.dataColeta(pedidoColeta.getDataColeta())
							.idTipoDoador(TiposDoador.NACIONAL.getId())
							.tipoAereo(pedidoLogistica.getTipoAereo())
							.build();
				})
				.collect(Collectors.toList());

		
		return new CustomPageImpl<TarefaLogisticaMaterialDTO>(lista, PageRequest.of(pagina, quantidadeRegistros), tarefas.size());
	}

//	@Override
//	public Page<TarefaAcompanhamentoLogisticaMaterialDTO> listarTarefasAcompanhamentoLogisticaMaterial(int pagina, int quantidadeRegistros) throws JsonProcessingException {
//
//		Usuario usuarioLogado = usuarioService.obterUsuarioLogado();
//		
//		ListaTarefaDTO filtro = ListaTarefaDTO.builder()
//				.idsTiposTarefa(Arrays.asList(TiposTarefa.INFORMAR_LOGISTICA_MATERIAL_COLETA_NACIONAL.getId()))
//				.perfilResponsavel(Arrays.asList(Perfis.ANALISTA_LOGISTICA.getId()))
//				.statusTarefa(Arrays.asList(StatusTarefa.ABERTA.getId(), StatusTarefa.ATRIBUIDA.getId()))
//				.idUsuarioLogado(usuarioLogado.getId())
//				.build();
//		
//		List<TarefaDTO> tarefas = tarefaHelper.listarTarefas(filtro);
//		if (CollectionUtils.isEmpty(tarefas)) {
//			return new CustomPageImpl<>();
//		}
//
//		List<TarefaAcompanhamentoLogisticaMaterialDTO> lista = tarefas.stream().filter(tarefa -> 
//
//		PedidoLogisticaMaterialColetaNacional pedidoLogistica = this.obterPedidoLogisticaPorId(tarefa.getRelacaoEntidade());
//		PedidoTransporteDTO pedidoTransporte = pedidoTransporteFeign.obterPedidoTransportePorIdLogiticaEmAnalise(pedidoLogistica.getId());
//
//		
//			
//		)
//		.collect(Collectors.toList());
//				
//				
//	
//
//		
//		
//		List<TarefaAcompanhamentoLogisticaMaterialDTO> lista = tarefas.stream()
//				.sorted(new TarefaComparator())
//				.skip(pagina * quantidadeRegistros)
//				.limit(quantidadeRegistros)
//				.map(tarefa -> {
//
//					PedidoLogisticaMaterialColetaNacional pedidoLogistica = this.obterPedidoLogisticaPorId(tarefa.getRelacaoEntidade());
//					PedidoTransporteDTO pedidoTransporte = pedidoTransporteFeign.obterPedidoTransportePorIdLogiticaEmAnalise(pedidoLogistica.getId());
//					
//					SolicitacaoDTO solicitacao = this.solicitacaoFeign.obterSolicitacao(pedidoLogistica.getSolicitacao());
//					final Long rmr = solicitacao.getMatch().getBusca().getPaciente().getRmr();
//					DoadorDTO doador = solicitacao.getMatch().getDoador();
//
//					return TarefaAcompanhamentoLogisticaMaterialDTO.builder()
//							.idPedidoLogistica(tarefa.getRelacaoEntidade())
//							.tipoTarefaLogisticaMaterial(tarefa.getTipoTarefa().getId())
//							.tipoLogistica(pedidoLogistica.getTipo())
//							.rmr(rmr)
//							.identificacaoDoador(doador.getIdentificacao())
//							.nomeDoador(doador.getNome())
//							.nomeResponsavel(usuarioLogado.getNome())
//							.idTipoDoador(TiposDoador.NACIONAL.getId())
//							.nomeTransportadora(pedidoTransporte.getTransportadora().getNome())
//							.horaPrevistaRetirada(pedidoTransporte.getHoraPrevistaRetirada())
//							.status(pedidoLogistica.getStatus().getId())
//							.tipoAereo(pedidoLogistica.getTipo())
//							.build();
//				})
//				.collect(Collectors.toList());
//
//		
//		return new CustomPageImpl<TarefaAcompanhamentoLogisticaMaterialDTO>(lista, PageRequest.of(pagina, quantidadeRegistros), tarefas.size());
//	}
	
	@Override
	public LogisticaMaterialTransporteDTO obterPedidoLogisticaMaterialParaTransportadora(Long id) {
		final PedidoLogisticaMaterialColetaNacional pedidoLogistica = this.obterPedidoLogisticaPorId(id);
		final ResultadoWorkup resultado  = resultadoWorkupService.obterResultadoWorkupPelaSolicitacao(pedidoLogistica.getPedidoColeta().getSolicitacao());
		final SolicitacaoWorkupDTO solicitacao = solicitacaoFeign.obterSolicitacaoWorkup(pedidoLogistica.getPedidoColeta().getSolicitacao());
		final EnderecoDTO enderecoEntrega = centroTransplanteFeign.obterEnderecoDeRetirada(solicitacao.getCentroTransplante().getId());
		
		String nomeLocalRetirada = null;
		EnderecoDTO enderecoRetirada = null;
		String enderecoRetiradaBancoSangueCordao = null;
		if (solicitacao.getCentroColeta() != null) {
			nomeLocalRetirada = solicitacao.getCentroColeta().getNome();
			enderecoRetirada = centroTransplanteFeign.obterEnderecoDeRetirada(solicitacao.getCentroColeta().getId());	
		}
		else if (solicitacao.getMatch().getDoador().getBancoSangueCordao() != null) {
			nomeLocalRetirada = solicitacao.getMatch().getDoador().getBancoSangueCordao().getNome();
			enderecoRetiradaBancoSangueCordao = solicitacao.getMatch().getDoador().getBancoSangueCordao().getEndereco();
		}
		else {
			throw new BusinessException("erro.pedido.logistica.sem.local.retirada");
		}
		
				
		return LogisticaMaterialTransporteDTO.builder()
				.enderecoEntrega(enderecoEntrega)
				.enderecoRetirada(enderecoRetirada)
				.enderecoRetiradaBancoCordao(enderecoRetiradaBancoSangueCordao)
				.identificacaoDoador(solicitacao.getMatch().getDoador().getIdentificacao())
				.nomeCentroTransplante(solicitacao.getCentroTransplante().getNome())
				.nomeLocalRetirada(nomeLocalRetirada)
				.rmr(solicitacao.getMatch().getBusca().getPaciente().getRmr())
				.nomeFonteCelula(resultado.getFonteCelula().getDescricao())
				.build();
	}
	
	
	@Override
	public DetalheLogisticaMaterialDTO obterPedidoLogisticaMaterialColetaNacional(Long idPedidoLogistica) {
		
		final PedidoLogisticaMaterialColetaNacional pedidoLogistica = this.obterPedidoLogisticaPorId(idPedidoLogistica);
		final ResultadoWorkup resultado  = resultadoWorkupService.obterResultadoWorkupPelaSolicitacao(pedidoLogistica.getPedidoColeta().getSolicitacao());
		final SolicitacaoDTO solicitacao = this.solicitacaoFeign.obterSolicitacao(pedidoLogistica.getSolicitacao());
		final DoadorDTO doador = solicitacao.getMatch().getDoador();		
		final EnderecoDTO enderecoEntrega = centroTransplanteFeign.obterEnderecoDeEntrega(solicitacao.getCentroTransplante().getId());
		
		String nomeLocalRetirada = null;
		EnderecoDTO enderecoRetirada = null;
		String enderecoRetiradaBancoSangueCordao = null;
		List<String> contatosLocalRetirada = null;
		if (solicitacao.getCentroColeta() != null) {
			nomeLocalRetirada = solicitacao.getCentroColeta().getNome();
			enderecoRetirada = centroTransplanteFeign.obterEnderecoDeRetirada(solicitacao.getCentroColeta().getId());	
		}
		else if (solicitacao.getMatch().getDoador().getBancoSangueCordao() != null) {
			nomeLocalRetirada = solicitacao.getMatch().getDoador().getBancoSangueCordao().getNome();
			enderecoRetiradaBancoSangueCordao = solicitacao.getMatch().getDoador().getBancoSangueCordao().getEndereco();
			contatosLocalRetirada = Arrays.asList(solicitacao.getMatch().getDoador().getBancoSangueCordao().getContato());
		}
		else {
			throw new BusinessException("erro.pedido.logistica.sem.local.retirada");
		}
		
		
		
		DetalheLogisticaMaterialDTO detalhe = DetalheLogisticaMaterialDTO.builder()
				.idPedidoLogistica(pedidoLogistica.getId())
				.dataColeta(DateUtils.getDataFormatadaSemHoraComAnoCompleto(resultado.getDataColeta())) 
				.idDoador(doador.getId())
				.identificacao(Long.parseLong(doador.getIdentificacao()))
				.nomeDoador(doador.getNome())
				.idTipoDoador(doador.getIdTipoDoador())
				.rmr(solicitacao.getMatch().getBusca().getPaciente().getRmr())
				.nomeCentroTransplante(solicitacao.getCentroTransplante().getNome())
				.nomeLocalRetirada(nomeLocalRetirada)
				.enderecoLocalEntrega(enderecoEntrega)
				.enderecoLocalRetirada(enderecoRetirada)
				.enderecoRetiradaBancoCordao(enderecoRetiradaBancoSangueCordao)
				.nomeFonteCelula(resultado.getFonteCelula().getDescricao())
				.contatosLocalRetirada(contatosLocalRetirada)
				.transportadora(pedidoLogistica.getTransportadora())
				.horaPrevistaRetirada(pedidoLogistica.getHoraPrevistaRetirada())
				.build();
		
		return detalhe;
	}

	
	@Override
	public DetalheLogisticaMaterialDTO obterPedidoLogisticaMaterialColetaAerea(Long idPedidoLogistica) {
		
		PedidoTransporteDTO pedidoTransporte = this.pedidoTransporteFeign.obterPedidoTransportePorIdLogiticaEStatus(idPedidoLogistica, 
				StatusPedidosTransporte.AGUARDANDO_DOCUMENTACAO.getId());

		DetalheLogisticaMaterialAereoDTO  materialAereo = DetalheLogisticaMaterialAereoDTO.builder()
			.idPedidoTransporte(pedidoTransporte.getId())
			.dadosVoo(pedidoTransporte.getDadosVoo())
			.idTransportadora(pedidoTransporte.getTransportadora().getId())
			.horaPrevistaRetirada(pedidoTransporte.getHoraPrevistaRetirada())
			.courier(pedidoTransporte.getCourier())
			.build();

		DetalheLogisticaMaterialDTO detalhe = obterPedidoLogisticaMaterialColetaNacional(idPedidoLogistica);
		detalhe.setMaterialAereo(materialAereo);
			
		return detalhe;
	}

	
	@Override
	public void salvarPedidoLogisticaMaterialColetaNacional(Long idPedidoLogistica, DetalheLogisticaMaterialDTO detalhe) {
		this.salvarPedidoLogisticaMaterialComTransporte(idPedidoLogistica, detalhe);
	}
	
	public PedidoLogisticaMaterialColetaNacional salvarPedidoLogisticaMaterialComTransporte(Long idPedidoLogistica, DetalheLogisticaMaterialDTO detalhe) {

		PedidoLogisticaMaterialColetaNacional pedidoLogistica =  this.obterPedidoLogisticaPorId(idPedidoLogistica);

		this.validarPedidoLogistica(detalhe);
		
		pedidoLogistica.setDataAtualizacao(LocalDateTime.now());
		pedidoLogistica.setUsuarioResponsavel(usuarioService.obterUsuarioLogado().getId());
		
		if(detalhe.getProsseguirComPedidoLogistica()) {
			
			pedidoLogistica.setTransportadora(detalhe.getTransportadora());
			pedidoLogistica.setHoraPrevistaRetirada(detalhe.getHoraPrevistaRetirada());
			
			return save(pedidoLogistica);
		}	
		
		pedidoLogistica.setJustificativa(detalhe.getJustificativa());
		
		return save(pedidoLogistica);
	}

	@Override
	public void finalizarPedidoLogisticaMaterialColetaNacional(Long idPedidoLogistica, DetalheLogisticaMaterialDTO detalhe) throws JsonProcessingException {

		PedidoLogisticaMaterialColetaNacional pedidoLogistica = salvarPedidoLogisticaMaterialComTransporte(idPedidoLogistica, detalhe);
		
		
		/* Ao finalizar a tarefa de logística de material normal, havendo a necessidade de logística de material */
		if(detalhe.getProsseguirComPedidoLogistica()) {
			
			/* Seguir o fluxo que já está codificado hoje, fazendo o pedido de transporte. */

			PedidoTransporteDTO pedidoTransporte = this.criaPedidoTransporte(pedidoLogistica);
			
			SolicitacaoWorkupDTO solicitacao = this.solicitacaoFeign.atualizarFaseWorkup(pedidoLogistica.getSolicitacao(), FasesWorkup.AGUARDANDO_ANALISE_PEDIDO_TRANSPORTE.getId());
			
			this.fecharTarefaInformarLogisticaMaterialColetaNacional(idPedidoLogistica, solicitacao);
			this.criarTarefaAnalisePedidoTransporte(pedidoTransporte.getId(), solicitacao);
			
		}else {
			/* Ao finalizar a tarefa de logística de material se disser que não há necessidade de logística de material */
			
			/* Verifica se a tarefa de logística de doador está finalizada, se sim mudar de fase para infusão */
			if(this.pedidoLogisticaDoadorColetaService.pedidoLogisticaDoadorEstaFinalizado(pedidoLogistica.getPedidoColeta().getId())) {
				
				SolicitacaoWorkupDTO solicitacao = this.solicitacaoFeign.atualizarFaseWorkup(pedidoLogistica.getSolicitacao(), FasesWorkup.AGUARDANDO_RESULTADO_DOADOR_COLETA.getId());
			
				this.fecharTarefaInformarLogisticaMaterialColetaNacional(idPedidoLogistica, solicitacao);
				this.criarTarefaInformarResultadoDoadorColeta(pedidoLogistica.getId(), solicitacao);
				
			}else {
				
				/* Se a logística de doador não estiver finalizada, não muda de fase. */
				SolicitacaoWorkupDTO solicitacao = this.solicitacaoFeign.obterSolicitacaoWorkup(pedidoLogistica.getSolicitacao());

				this.fecharTarefaInformarLogisticaMaterialColetaNacional(idPedidoLogistica, solicitacao);
			}
		}
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


	private PedidoLogisticaMaterialColetaNacional obterPedidoLogisticaPorId(Long id) {
		if(id == null) {
			throw new BusinessException("erro.id.nulo");
		}
		return pedidoLogisticaMaterialColetaNacionalRepository.findById(id).orElseThrow(() -> new BusinessException("erro.nao.existe", "Pedido Logística de Material"));
	}
	
	private void criarTarefaAnalisePedidoTransporte(Long idPedidoTransporte, SolicitacaoWorkupDTO solicitacao) {
		
		Long rmr = solicitacao.getMatch().getBusca().getPaciente().getRmr();

		ProcessoDTO processo = new ProcessoDTO(TipoProcesso.BUSCA);
		processo.setRmr(rmr);

		TarefaDTO tarefa = TarefaDTO.builder()
				.processo(processo) 
				.tipoTarefa(new TipoTarefaDTO(TiposTarefa.ANALISAR_PEDIDO_TRANSPORTE.getId()))
				.perfilResponsavel(Perfis.TRANSPORTADORA.getId())
				.status(StatusTarefa.ABERTA.getId())
				.relacaoEntidade(idPedidoTransporte)
				.build(); 
		
		tarefaHelper.criarTarefa(tarefa);		
	}

	private void fecharTarefaInformarLogisticaMaterialColetaNacional(Long idPedidoLogistica, SolicitacaoWorkupDTO solicitacao) throws JsonProcessingException {

		Long rmr = solicitacao.getMatch().getBusca().getPaciente().getRmr();

		ListaTarefaDTO filtroDTO = ListaTarefaDTO.builder()
				.idsTiposTarefa(Arrays.asList(TiposTarefa.INFORMAR_LOGISTICA_MATERIAL_COLETA_NACIONAL.getId()))
				.statusTarefa(Arrays.asList(StatusTarefa.ATRIBUIDA.getId()))
				.idUsuarioLogado(usuarioService.obterIdUsuarioLogado())
				.rmr(rmr)
				.relacaoEntidadeId(idPedidoLogistica)
				.tipoProcesso(TipoProcesso.BUSCA.getId())
				.build();
		
		tarefaHelper.encerrarTarefa(filtroDTO, false);
	}
	
	@Override
	public PedidoLogisticaMaterialColetaNacional criarPedidoLogisticaMaterialColetaNacional(PedidoColeta pedidoColeta) {
		
		LOGGER.info("Criar pedido de logistica material coleta");
		
		PedidoLogisticaMaterialColetaNacional logisticaMaterial = PedidoLogisticaMaterialColetaNacional.builder()
			.pedidoColeta(pedidoColeta)
			.status(new StatusPedidoLogistica(StatusPedidosLogistica.ABERTO))
			.dataCriacao(LocalDateTime.now())
			.build();

		return save(logisticaMaterial);
	}

	/**
	 * @param detalhe
	 */
	private PedidoTransporteDTO criaPedidoTransporte(PedidoLogisticaMaterialColetaNacional pedidoLogistica) {
		
		ResultadoWorkup resultadoWorkup = resultadoWorkupService.obterResultadoWorkupPelaSolicitacao(pedidoLogistica.getPedidoColeta().getSolicitacao());
		
		PedidoTransporteDTO pedidoTransporteNovo = PedidoTransporteDTO.builder()
				.idPedidoLogistica(pedidoLogistica.getId())
				.horaPrevistaRetirada(pedidoLogistica.getHoraPrevistaRetirada())
				.transportadora(TransportadoraDTO.builder().id(pedidoLogistica.getTransportadora()).build())
				.usuarioResponsavel(usuarioService.obterIdUsuarioLogado())
				.status(StatusPedidoTransporteDTO.builder().id(StatusPedidosTransporte.EM_ANALISE.getId()).build())
				.idFonteCelula(resultadoWorkup.getFonteCelula().getId())
				.build();

		return pedidoTransporteFeign.criarPedidoTransporte(pedidoTransporteNovo);
	}

	private void validarPedidoLogistica(DetalheLogisticaMaterialDTO detalhe) {

		if(detalhe.getProsseguirComPedidoLogistica()) {
		
			if(detalhe.getHoraPrevistaRetirada() == null) {
				throw new BusinessException("erro.validacao.registro.invalido", "Hora prevista de retirada");			
			}
			if(detalhe.getTransportadora() == null) {
				throw new BusinessException("erro.validacao.registro.invalido", "Identificação da Transportadora");			
			}
		}else {
			if(detalhe.getJustificativa() == null) {
				throw new BusinessException("erro.validacao.registro.invalido", "Justificativa do não prosseguimento");			
			}
		}
	}

	@Override
	public void atualizarLogisticaMaterialParaAereo(Long id) {
		PedidoLogisticaMaterialColetaNacional pedido = obterPedidoLogisticaMaterialColetaNacionalEmAberto(id);
		if (pedido.getAereo()) {
			throw new BusinessException("erro.pedido.logistica.ja.atualizado.para.aereo");
		}
		pedido.setAereo(true);
		save(pedido);
	}

	private PedidoLogisticaMaterialColetaNacional obterPedidoLogisticaMaterialColetaNacionalEmAberto(Long id) {
		PedidoLogisticaMaterialColetaNacional pedido = obterPedidoLogisticaPorId(id);
		if (!pedido.getStatus().getId().equals(StatusPedidosLogistica.ABERTO.getId())) {
			throw new BusinessException("erro.pedido.logistica.finalizado.ou.cancelado",  
					pedido.getStatus().getId().equals(StatusPedidosLogistica.FECHADO.getId()) ? "já realizado" : "cancelado" );
		}
		return pedido;
	}

	@Override
	public boolean pedidoLogisticaEstaFinalizadoSemJustificativa(Long idPedidoColeta) {
		return this.pedidoLogisticaMaterialColetaNacionalRepository.existePedidoLogisticaMaterialFinalizadoSemJustificativa(idPedidoColeta);	
	}

}

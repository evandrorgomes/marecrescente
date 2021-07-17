package br.org.cancer.redome.courier.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.cloud.objectstorage.util.Base64;

import br.org.cancer.redome.courier.controller.dto.ConfirmacaoTransporteDTO;
import br.org.cancer.redome.courier.controller.dto.CourierDTO;
import br.org.cancer.redome.courier.controller.dto.DetalhePedidoTransporteDTO;
import br.org.cancer.redome.courier.controller.dto.PedidoTransporteDTO;
import br.org.cancer.redome.courier.controller.dto.TarefasPedidoTransporteDTO;
import br.org.cancer.redome.courier.controller.dto.TransportadoraDTO;
import br.org.cancer.redome.courier.exception.BusinessException;
import br.org.cancer.redome.courier.feign.client.IRelatorioFeign;
import br.org.cancer.redome.courier.feign.client.IWorkupFeign;
import br.org.cancer.redome.courier.feign.client.dto.LogisticaMaterialTransporteDTO;
import br.org.cancer.redome.courier.model.PedidoTransporte;
import br.org.cancer.redome.courier.model.StatusPedidoTransporte;
import br.org.cancer.redome.courier.model.Transportadora;
import br.org.cancer.redome.courier.model.domain.FontesCelulas;
import br.org.cancer.redome.courier.model.domain.Perfis;
import br.org.cancer.redome.courier.model.domain.StatusPedidosTransporte;
import br.org.cancer.redome.courier.model.domain.TipoLogEvolucao;
import br.org.cancer.redome.courier.model.domain.TiposTarefa;
import br.org.cancer.redome.courier.persistence.IPedidoTransporteRepository;
import br.org.cancer.redome.courier.persistence.IRepository;
import br.org.cancer.redome.courier.security.Usuario;
import br.org.cancer.redome.courier.service.IArquivoPedidoTransporteService;
import br.org.cancer.redome.courier.service.ICourierService;
import br.org.cancer.redome.courier.service.IPedidoTransporteService;
import br.org.cancer.redome.courier.service.IUsuarioService;
import br.org.cancer.redome.courier.service.impl.custom.AbstractService;
import br.org.cancer.redome.feign.client.ILogEvolucaoFeign;
import br.org.cancer.redome.feign.client.ISolicitacaoFeign;
import br.org.cancer.redome.feign.client.domain.FasesWorkup;
import br.org.cancer.redome.feign.client.domain.StatusTarefa;
import br.org.cancer.redome.feign.client.domain.TipoProcesso;
import br.org.cancer.redome.feign.client.dto.CriarLogEvolucaoDTO;
import br.org.cancer.redome.feign.client.dto.DoadorDTO;
import br.org.cancer.redome.feign.client.dto.ListaTarefaDTO;
import br.org.cancer.redome.feign.client.dto.ProcessoDTO;
import br.org.cancer.redome.feign.client.dto.SolicitacaoWorkupDTO;
import br.org.cancer.redome.feign.client.dto.TarefaDTO;
import br.org.cancer.redome.feign.client.dto.TipoTarefaDTO;
import br.org.cancer.redome.feign.client.helper.ITarefaHelper;
import br.org.cancer.redome.feign.client.util.CustomPageImpl;
import feign.Response;

/**
 * Implementacao da classe de negócios de PedidoTransporte.
 * 
 * @author Bruno Sousa
 *
 */
@Service
@Transactional
public class PedidoTransporteService extends AbstractService<PedidoTransporte, Long> implements IPedidoTransporteService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PedidoTransporteService.class);
	
	private final String MARCACAO_CELULA_TRONCO_SANGUE_PERIFERICO = "MARCACAO_CELULA_TRONCO_SANGUE_PERIFERICO";
	private final String MARCACAO_CELULA_TRONCO_MEDULA_OSSEA = "MARCACAO_CELULA_TRONCO_MEDULA_OSSEA";
	
	private final String RELATORIO_CARTA_MO = "CARTA_MO";
	private final String RELATORIO_CARTA_TRANSPORTE = "CARTA_TRANSPORTE";

	@Autowired
	private IPedidoTransporteRepository pedidoTransporteRepository;
	
	@Autowired
	private IUsuarioService usuarioService;

	@Autowired
	@Lazy(true)
	private ITarefaHelper tarefaHelper;
	
	@Autowired
	@Lazy(true)
	private IWorkupFeign workupFeign;
	
	@Autowired
	private ICourierService courierService;
	
	@Autowired
	@Lazy(true)
	private ISolicitacaoFeign solicitacaoFeign;
	
	@Autowired
	@Lazy(true)
	private ILogEvolucaoFeign logEvolucaoFeign;
	
	@Autowired
	@Lazy(true)
	private IRelatorioFeign relatorioFeign;
	
	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private IArquivoPedidoTransporteService arquivoPedidoTransporteService;
	
	@Override
	public IRepository<PedidoTransporte, Long> getRepository() {
		return pedidoTransporteRepository;
	}
	
	
	@Override
	public Page<TarefasPedidoTransporteDTO> listarTarefas(PageRequest pageRequest) throws Exception {
		final Usuario usuarioLogado = usuarioService.obterUsuarioLogado();
		
		if(usuarioLogado.getIdTransportadora() == null){
			LOGGER.error("O usuário não está associado a uma transportadora e "
					+ "acessou uma funcionalidade para transportadoras. Possível falha de programação e/ou segurança.");
			throw new BusinessException("erro.requisicao");
		}
		
		ListaTarefaDTO filtro = ListaTarefaDTO.builder()
				.idsTiposTarefa(Arrays.asList(TiposTarefa.ANALISAR_PEDIDO_TRANSPORTE.getId()) )
				.perfilResponsavel(Arrays.asList(Perfis.TRANSPORTADORA.getId()))
				.statusTarefa(Arrays.asList(StatusTarefa.ABERTA.getId(), StatusTarefa.ATRIBUIDA.getId()))				
				.idUsuarioLogado(usuarioLogado.getId())
				.parceiros(Arrays.asList(usuarioLogado.getIdTransportadora()))
				.build();
		
		List<TarefaDTO> tarefas = tarefaHelper.listarTarefas(filtro);
		if (CollectionUtils.isEmpty(tarefas)) {
			return new CustomPageImpl<>();
		}
		
		List<TarefasPedidoTransporteDTO> lista = tarefas.stream()				
				.skip(pageRequest.getPageNumber() * pageRequest.getPageSize())
				.limit(pageRequest.getPageSize())
				.map(tarefa -> {
					PedidoTransporte pedido = obterPedidoTransportePorId(tarefa.getRelacaoEntidade());
					
					LogisticaMaterialTransporteDTO logisitica =  workupFeign.obterLogisitcaMaterial(pedido.getPedidoLogistica());
					
					return TarefasPedidoTransporteDTO.builder()
							.idTarefa(tarefa.getId())
							.idStatusTarefa(tarefa.getStatus())
							.idPedidoTransporte(pedido.getId())
							.descricaoStatusPedidoTransporte(pedido.getStatus().getDescricao())
							.horaPrevistaRetirada(pedido.getHoraPrevistaRetirada())
							.identificacaoDoador(logisitica.getIdentificacaoDoador())
							.nomeLocalRetirada(logisitica.getNomeLocalRetirada())
							.nomeCentroTransplante(logisitica.getNomeCentroTransplante())
							.rmr(logisitica.getRmr())
							.build();
				})
				.collect(Collectors.toList());
		
		return new CustomPageImpl<TarefasPedidoTransporteDTO>(lista, pageRequest, tarefas.size());
	}
	
	@Override
	public PedidoTransporte obterPedidoTransportePorId(Long id) {
		if (id == null) {
			throw new BusinessException("erro.id.nulo");
		}
		return pedidoTransporteRepository.findById(id).orElseThrow(() -> new BusinessException("erro.nao.existe", "Pedido de Transporte"));
	}
	
	@Override
	public Page<TarefasPedidoTransporteDTO> listarPedidosTransporteEmAndamento(PageRequest pageRequest) {
		List<Long> status = Arrays.asList(StatusPedidosTransporte.AGUARDANDO_DOCUMENTACAO.getId(),
				StatusPedidosTransporte.AGUARDANDO_RETIRADA.getId(), StatusPedidosTransporte.AGUARDANDO_ENTREGA.getId());

		Page<PedidoTransporte> lista = pedidoTransporteRepository.listarPedidosTransportesPorIdTransportadoraEStatus(
				usuarioService.obterIdTransportadora(), status, pageRequest);
		if (lista.hasContent()) {
			List<TarefasPedidoTransporteDTO> retorno = lista.getContent().stream()
					.map(pedido -> {
						
						LogisticaMaterialTransporteDTO logisitica =  workupFeign.obterLogisitcaMaterial(pedido.getPedidoLogistica());
						
						return TarefasPedidoTransporteDTO.builder()								
								.idPedidoTransporte(pedido.getId())
								.descricaoStatusPedidoTransporte(pedido.getStatus().getDescricao())
								.horaPrevistaRetirada(pedido.getHoraPrevistaRetirada())
								.identificacaoDoador(logisitica.getIdentificacaoDoador())
								.nomeLocalRetirada(logisitica.getNomeLocalRetirada())
								.nomeCentroTransplante(logisitica.getNomeCentroTransplante())
								.rmr(logisitica.getRmr())
								.build();
						
					})
					.collect(Collectors.toList());
			return new CustomPageImpl<>(retorno, pageRequest, lista.getTotalElements());
		}
				
		return new CustomPageImpl<>();
	}
	
	@Override
	public PedidoTransporte criarPedidoTransporte(PedidoTransporteDTO pedidoTransporteDTO) {

		this.validarPedidoTransporte(pedidoTransporteDTO);
		
		PedidoTransporte pedidoTransporte = PedidoTransporte.builder()
				.pedidoLogistica(pedidoTransporteDTO.getIdPedidoLogistica())
				.solicitacao(pedidoTransporteDTO.getIdSoliciacao())
				.transportadora(Transportadora.builder().id(pedidoTransporteDTO.getTransportadora().getId()).build())
				.status(StatusPedidoTransporte.builder().id(pedidoTransporteDTO.getStatus().getId()).build())
				.horaPrevistaRetirada(pedidoTransporteDTO.getHoraPrevistaRetirada())
				.usuarioResponsavel(pedidoTransporteDTO.getUsuarioResponsavel())
				.dataAtualizacao(LocalDateTime.now())
				.fonteCelula(pedidoTransporteDTO.getIdFonteCelula())
				.build();

		return save(pedidoTransporte);
	}

	@Override
	public PedidoTransporteDTO obterPedidoTransportePorIdLogiticaEStatus(Long idPedidoLogistica, Long idStatusTransporte) {
		if (idPedidoLogistica == null) {
			throw new BusinessException("erro.id.nulo");
		}
		PedidoTransporte pedidoTransporte = pedidoTransporteRepository.findByPedidoLogisticaAndStatusId(idPedidoLogistica, 
				idStatusTransporte).orElseThrow(() -> new BusinessException("erro.validacao.registro.invalido", "Pedido de Transporte"));
		
		CourierDTO courier = null;
		if(pedidoTransporte.getCourier() != null) {
			courier = CourierDTO.builder()
				.id(pedidoTransporte.getCourier().getId())
				.cpf(pedidoTransporte.getCourier().getCpf())
				.nome(pedidoTransporte.getCourier().getNome())
				.rg(pedidoTransporte.getCourier().getRg())
				.build();
		}
		
		PedidoTransporteDTO pedidoDto = PedidoTransporteDTO.builder()
			.id(pedidoTransporte.getId())
			.dadosVoo(pedidoTransporte.getDadosVoo())
			.transportadora(TransportadoraDTO.builder().id(pedidoTransporte.getTransportadora().getId()).build())
			.horaPrevistaRetirada(pedidoTransporte.getHoraPrevistaRetirada())
			.courier(courier)
			.build();
		
		return pedidoDto;
	}

	private void validarPedidoTransporte(PedidoTransporteDTO pedidoTransporteDTO) {

		if(pedidoTransporteDTO.getHoraPrevistaRetirada() == null) {
			throw new BusinessException("erro.validacao.registro.invalido", "Hora prevista de retirada");			
		}
		if(pedidoTransporteDTO.getTransportadora() == null) {
			throw new BusinessException("erro.validacao.registro.invalido", "Identificação da Transportadora");			
		}
		if(pedidoTransporteDTO.getStatus() == null) {
			throw new BusinessException("erro.validacao.registro.invalido", "Status Pedido de Transporte");			
		}
		if(pedidoTransporteDTO.getIdFonteCelula() == null) {
			throw new BusinessException("erro.validacao.registro.invalido", "Fonte de Celula Pedido de Transporte");			
		}
	}
	

	@Override
	public DetalhePedidoTransporteDTO obterDetalhePedidoTransporte(Long idPedidoTransporte) {
		PedidoTransporte pedido = obterPedidoTransportePorId(idPedidoTransporte);
		
		LogisticaMaterialTransporteDTO logistica =  workupFeign.obterLogisitcaMaterial(pedido.getPedidoLogistica());
		
		return DetalhePedidoTransporteDTO.builder()
				.idPedidoTransporte(pedido.getId())
				.dadosVoo(pedido.getDadosVoo())
				.enderecoCentroTransplante(logistica.getEnderecoEntrega())
				.enderecoLocalRetirada(logistica.getEnderecoRetirada())
				.enderecoLocalRetiradaBancoCordao(logistica.getEnderecoRetiradaBancoCordao())
				.horaPrevistaRetirada(pedido.getHoraPrevistaRetirada())
				.identificacaoDoador(logistica.getIdentificacaoDoador())
				.nomeCentroTransplante(logistica.getNomeCentroTransplante())
				.nomeFonteCelula(logistica.getNomeFonteCelula())
				.nomeLocalRetirada(logistica.getNomeLocalRetirada())
				.rmr(logistica.getRmr())
				.status(pedido.getStatus())
				.arquivos(pedido.getArquivoPedidoTransporte())
				.build();		
	}
	
	@Override
	public void confirmarPedidoTransporte(Long id, ConfirmacaoTransporteDTO confirmacaoTransporteDTO) {
		PedidoTransporte pedido = obterPedidoTransportePorId(id);
		validarStatusPedido(pedido.getStatus());		
		final SolicitacaoWorkupDTO solicitacao;
		if (pedido.getStatus().getId().equals(StatusPedidosTransporte.EM_ANALISE.getId())) {
			solicitacao = confirmarPedidoTransporteEmAnalise(pedido, confirmacaoTransporteDTO);
		}
		else {
			solicitacao = confirmarPedidoTransporteAguardandoConfirmacao(pedido, confirmacaoTransporteDTO);
		}
		fecharTarefaAnalizarPedidoTransporte(pedido.getId(), solicitacao);
				
	}
	
	private SolicitacaoWorkupDTO confirmarPedidoTransporteAguardandoConfirmacao(PedidoTransporte pedido,
			ConfirmacaoTransporteDTO confirmacaoTransporteDTO) {
		return confirmarPedidoTransporteParaRetirada(pedido);
	}


	private SolicitacaoWorkupDTO confirmarPedidoTransporteEmAnalise(PedidoTransporte pedido, ConfirmacaoTransporteDTO confirmacaoTransporteDTO) {
		pedido.setCourier(courierService.obterCourierPorId(confirmacaoTransporteDTO.getIdCourier()));
		final SolicitacaoWorkupDTO solicitacao;
		if (confirmacaoTransporteDTO.isVoo()) {
			solicitacao = solicitarDocumentacaoParaTransporteAereo(pedido, confirmacaoTransporteDTO);
		}
		else {
			solicitacao = confirmarPedidoTransporteParaRetirada(pedido);
		}
		criarLogEvolucao(TipoLogEvolucao.PEDIDO_TRANSPORTE_AGENDADO_PARA_DOADOR, solicitacao.getMatch().getBusca().getPaciente().getRmr(),				
				solicitacao.getMatch().getDoador());
		return solicitacao;
	}
	
	private void validarStatusPedido(StatusPedidoTransporte status) {
		if (!status.getId().equals(StatusPedidosTransporte.EM_ANALISE.getId()) && !status.getId().equals(StatusPedidosTransporte.AGUARDANDO_CONFIRMACAO.getId()) ) {
			throw new BusinessException("erro.pedido.transporte.ja.confirmado");
		}
		
	}


	private void criarLogEvolucao(TipoLogEvolucao tipoLogEvolucao, Long rmr, DoadorDTO doador) {
		
		String[] parametros = {doador.getIdentificacao()};
		
		logEvolucaoFeign.criarLogEvolucao(CriarLogEvolucaoDTO.builder()
				.tipo(tipoLogEvolucao.name())
				.rmr(rmr)
				.parametros(parametros )
				.build());
				
	}


	private void fecharTarefaAnalizarPedidoTransporte(Long idPedidoTransporte, SolicitacaoWorkupDTO solicitacao) {
		Long rmr = solicitacao.getMatch().getBusca().getPaciente().getRmr();

		ListaTarefaDTO filtroDTO = ListaTarefaDTO.builder()
				.idsTiposTarefa(Arrays.asList(TiposTarefa.ANALISAR_PEDIDO_TRANSPORTE.getId()))
				.statusTarefa(Arrays.asList(StatusTarefa.ATRIBUIDA.getId()))
				.idUsuarioLogado(usuarioService.obterIdUsuarioLogado())
				.rmr(rmr)
				.relacaoEntidadeId(idPedidoTransporte)
				.tipoProcesso(TipoProcesso.BUSCA.getId())
				.build();
		tarefaHelper.encerrarTarefa(filtroDTO, false);		
	}

	private SolicitacaoWorkupDTO confirmarPedidoTransporteParaRetirada(PedidoTransporte pedido) {
		pedido.setStatusAnterior(pedido.getStatus());
		pedido.setStatus(StatusPedidoTransporte.builder().id(StatusPedidosTransporte.AGUARDANDO_RETIRADA.getId()).build() );
		save(pedido);
		
		SolicitacaoWorkupDTO solicitacao = solicitacaoFeign.atualizarFaseWorkup(pedido.getSolicitacao(), FasesWorkup.AGUARDANDO_RETIRADA_MATERIAL.getId());
		
		criarTarefaTransportadoraRetirarMaterial(pedido.getId(), solicitacao);
		
		return solicitacao;

	}


	private void criarTarefaTransportadoraRetirarMaterial(Long idPedidoTransporte, SolicitacaoWorkupDTO solicitacao) {
		
		final Long rmr = solicitacao.getMatch().getBusca().getPaciente().getRmr();

		final ProcessoDTO processo = new ProcessoDTO(TipoProcesso.BUSCA);
		processo.setRmr(rmr);

		final TarefaDTO tarefa = TarefaDTO.builder()
				.processo(processo) 
				.tipoTarefa(new TipoTarefaDTO(TiposTarefa.INFORMAR_RETIRADA_MATERIAL.getId()))
				.perfilResponsavel(Perfis.TRANSPORTADORA.getId())
				.status(StatusTarefa.ABERTA.getId())
				.relacaoEntidade(idPedidoTransporte)
				.build(); 
		
		tarefaHelper.criarTarefa(tarefa);
		
	}


	private SolicitacaoWorkupDTO solicitarDocumentacaoParaTransporteAereo(PedidoTransporte pedido,
			ConfirmacaoTransporteDTO confirmacaoTransporteDTO) {
		
		
		validarDadosVoo(confirmacaoTransporteDTO.getDadosVoo());
		pedido.setStatusAnterior(pedido.getStatus());
		pedido.setDadosVoo(confirmacaoTransporteDTO.getDadosVoo());
		pedido.setStatus(StatusPedidoTransporte.builder().id(StatusPedidosTransporte.AGUARDANDO_DOCUMENTACAO.getId()).build() );		
		
		save(pedido);
		
		workupFeign.atualizarLogisticaMaterialParaAereo(pedido.getPedidoLogistica());
		
		SolicitacaoWorkupDTO solicitacao = solicitacaoFeign.atualizarFaseWorkup(pedido.getSolicitacao(), FasesWorkup.AGUARDANDO_DOCUMENTACAO_PEDIDO_TRANSPORTE_AEREO.getId());
		
		criarTarefaAnalistaLogisticaInformarDocumentacao(pedido.getPedidoLogistica(), solicitacao);
		
		return solicitacao;
		
	}

	private void criarTarefaAnalistaLogisticaInformarDocumentacao(Long idPedidoLogistica, SolicitacaoWorkupDTO solicitacao) {
		
		final Long rmr = solicitacao.getMatch().getBusca().getPaciente().getRmr();

		final ProcessoDTO processo = new ProcessoDTO(TipoProcesso.BUSCA);
		processo.setRmr(rmr);

		final TarefaDTO tarefa = TarefaDTO.builder()
				.processo(processo) 
				.tipoTarefa(new TipoTarefaDTO(TiposTarefa.INFORMAR_DOCUMENTACAO_MATERIAL_AEREO.getId()))
				.perfilResponsavel(Perfis.ANALISTA_LOGISTICA.getId())				
				.status(StatusTarefa.ATRIBUIDA.getId())
				.usuarioResponsavel(solicitacao.getUsuarioResponsavel().getId())
				.relacaoEntidade(idPedidoLogistica)
				.build(); 
		
		tarefaHelper.criarTarefa(tarefa);		
		
	}


	private void validarDadosVoo(String dadosVoo) {
		if (dadosVoo == null || "".equals(dadosVoo)) {
			throw new BusinessException("erro.dados.voo.obrigatorio");
		}		
		
	}


	@Override
	public File obterCartaTransporte(Long idPedidoTransporte) {
		PedidoTransporte pedido = obterPedidoTransportePorId(idPedidoTransporte);
		
		String marcacaoCelulaTroncoMO = FontesCelulas.MEDULA_OSSEA.getFonteCelulaId().equals(pedido.getFonteCelula()) ? "X" : "&nbsp;";
		String marcacaoSanguePeriferico = FontesCelulas.SANGUE_PERIFERICO.getFonteCelulaId().equals(pedido.getFonteCelula()) ? "X" : "&nbsp;";
		
		HashMap<String, String> parametros = new HashMap<>();
		parametros.put(MARCACAO_CELULA_TRONCO_MEDULA_OSSEA, marcacaoCelulaTroncoMO);
		parametros.put(MARCACAO_CELULA_TRONCO_SANGUE_PERIFERICO,marcacaoSanguePeriferico);
		
		String jsonParam = null;
		try {
			jsonParam = objectMapper.writeValueAsString(parametros);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		final Response response = relatorioFeign.baixarArquivo(RELATORIO_CARTA_MO, Base64.encodeAsString(jsonParam.getBytes()));
		if (response != null && response.status() == 200) {
			final Response.Body body = response.body();
			try {
				final InputStream inputStream = body.asInputStream();
				
				File fileTmp = new File("/tmp/carta.pdf");
								
				FileUtils.copyInputStreamToFile(inputStream, fileTmp);
				
				return fileTmp;
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
		
		return null;
	}
	
	@SuppressWarnings("unused")
	@Override
	public File obterRelatorioTransporte(Long idPedidoTransporte) {		
		PedidoTransporte pedido = obterPedidoTransportePorId(idPedidoTransporte);
		
		final Response response = relatorioFeign.baixarArquivo(RELATORIO_CARTA_TRANSPORTE);
		if (response != null && response.status() == 200) {
			final Response.Body body = response.body();
			try {
				final InputStream inputStream = body.asInputStream();
				
				File fileTmp = new File("/tmp/relatorio.pdf");
								
				FileUtils.copyInputStreamToFile(inputStream, fileTmp);
				
				return fileTmp;
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
		return null;
		
	}
	
	@Override
	public void atualizarInformacoesTransporteAereo(Long idPedidoTransporte, LocalDateTime dataPrevistaRetirada, String descricaoAlteracao, List<MultipartFile> arquivos) {
		
		PedidoTransporte pedidoTransporteRecuperado = obterPedidoTransportePorId(idPedidoTransporte);

		validarTransporteAereo(dataPrevistaRetirada, arquivos);

		this.arquivoPedidoTransporteService.atualizarListaDeArquivosCnt(pedidoTransporteRecuperado, descricaoAlteracao, arquivos);

		PedidoTransporte pedidoTransporte = pedidoTransporteRecuperado.toBuilder()
				.status(StatusPedidoTransporte.builder().id(StatusPedidosTransporte.AGUARDANDO_CONFIRMACAO.getId()).build())
				.dataAtualizacao(LocalDateTime.now())
				.build();

		if(dataPrevistaRetirada != null) {
			pedidoTransporte.setHoraPrevistaRetirada(dataPrevistaRetirada);
		}
		
		save(pedidoTransporte);
	}
	
	private void validarTransporteAereo(LocalDateTime dataPrevistaRetirada, List<MultipartFile> arquivos) {
		if(dataPrevistaRetirada == null && (arquivos == null || arquivos.isEmpty())) {
			throw new BusinessException("erro.validacao.registro.invalido");			
		}
	}

}

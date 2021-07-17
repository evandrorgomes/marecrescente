package br.org.cancer.modred.service.impl;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import br.org.cancer.modred.controller.dto.ConfirmacaoTransporteDTO;
import br.org.cancer.modred.controller.dto.DetalheMaterialDTO;
import br.org.cancer.modred.exception.BusinessException;
import br.org.cancer.modred.exception.ValidationException;
import br.org.cancer.modred.feign.dto.TarefaDTO;
import br.org.cancer.modred.helper.JsonViewPage;
import br.org.cancer.modred.model.ArquivoPedidoTransporte;
import br.org.cancer.modred.model.Configuracao;
import br.org.cancer.modred.model.Doador;
import br.org.cancer.modred.model.IDoador;
import br.org.cancer.modred.model.LogEvolucao;
import br.org.cancer.modred.model.Paciente;
import br.org.cancer.modred.model.PedidoLogistica;
import br.org.cancer.modred.model.PedidoTransporte;
import br.org.cancer.modred.model.Relatorio;
import br.org.cancer.modred.model.StatusPedidoTransporte;
import br.org.cancer.modred.model.TipoPedidoLogistica;
import br.org.cancer.modred.model.annotations.log.CreateLog;
import br.org.cancer.modred.model.domain.FontesCelulas;
import br.org.cancer.modred.model.domain.ParametrosRelatorios;
import br.org.cancer.modred.model.domain.Perfis;
import br.org.cancer.modred.model.domain.Relatorios;
import br.org.cancer.modred.model.domain.StatusPedidosTransporte;
import br.org.cancer.modred.model.domain.StatusTarefa;
import br.org.cancer.modred.model.domain.TipoLogEvolucao;
import br.org.cancer.modred.model.domain.TiposDoador;
import br.org.cancer.modred.model.domain.TiposPedidoLogistica;
import br.org.cancer.modred.model.domain.TiposTarefa;
import br.org.cancer.modred.model.security.Usuario;
import br.org.cancer.modred.persistence.IPedidoTransporteRepository;
import br.org.cancer.modred.persistence.IRepository;
import br.org.cancer.modred.service.IArquivoPedidoTransporteService;
import br.org.cancer.modred.service.IPacienteService;
import br.org.cancer.modred.service.IPedidoColetaService;
import br.org.cancer.modred.service.IPedidoLogisticaService;
import br.org.cancer.modred.service.IPedidoTransporteService;
import br.org.cancer.modred.service.IPedidoWorkupService;
import br.org.cancer.modred.service.IRelatorioService;
import br.org.cancer.modred.service.IStorageService;
import br.org.cancer.modred.service.IUsuarioService;
import br.org.cancer.modred.service.impl.invocation.AbstractLoggingService;
import br.org.cancer.modred.util.ArquivoUtil;
import br.org.cancer.modred.util.CampoMensagem;
import br.org.cancer.modred.vo.report.HtmlReportGenerator;

/**
 * Classe de negocios de pedido de transporte.
 * 
 * @author Filipe Paes
 *
 */
@Service
@Transactional
public class PedidoTransporteService extends AbstractLoggingService<PedidoTransporte, Long> implements IPedidoTransporteService {

	private static final Logger LOGGER = LoggerFactory.getLogger(PedidoTransporteService.class);

	@Autowired
	private IPedidoTransporteRepository pedidoTransporteRepository;

	@Autowired
	private IStorageService storageService;
	
	private ConfiguracaoService configuracaoService;
	
	private IRelatorioService relatorioService;

	//private ICourierService courierService;

	private IUsuarioService usuarioService;

	private IPedidoLogisticaService pedidoLogisticaService;

	private IPacienteService pacienteService;
	
//	private IRecebimentoColetaService recebimentoColetaService;
	
	private IPedidoWorkupService pedidoWorkupService;
	
	private IPedidoColetaService pedidoColetaService;
	
	private IArquivoPedidoTransporteService arquivoPedidoTransporteService;
	
	
	@Override
	public List<String> salvarPedidoComArquivo(PedidoTransporte pedidoTransporte, List<MultipartFile> arquivos) {
		if (pedidoTransporte == null) {
			throw new BusinessException("erro.pedido.transporte.obrigatorio");
		}

		if (arquivos == null) {
			throw new BusinessException("erro.pedido.transporte.arquivo_pedido_transporte_obrigatorio");
		}
		
		PedidoTransporte pedidoTransporteEncontrado = findById(pedidoTransporte.getId());
		
		List<String> caminhosArquivoParaRetorno = enviarArquivoDeTransporteParaStorage(pedidoTransporteEncontrado, arquivos,null);
		
		pedidoTransporteEncontrado.setDataAtualizacao(LocalDateTime.now());
		pedidoTransporteEncontrado.setStatus(new StatusPedidoTransporte(StatusPedidosTransporte.AGUARDANDO_CONFIRMACAO.getId()));
		save(pedidoTransporteEncontrado);
		
		fecharTarefaCnt(pedidoTransporteEncontrado);
		criarTarefaDePedidoDeTransporte(pedidoTransporteEncontrado);
				
		return caminhosArquivoParaRetorno;
	}

	
	@Override
	public void atualizarArquivoCartaCnt(Long idPedidoLogistica,  List<MultipartFile> listaArquivosLaudo, String descricaoAlteracao) {

		if (listaArquivosLaudo == null) {
			throw new BusinessException("erro.pedido.transporte.arquivo_pedido_transporte_obrigatorio");
		}
		
		PedidoTransporte pedidoTransporte = pedidoTransporteRepository.findByPedidoLogisticaId(idPedidoLogistica);
		
		enviarArquivoDeTransporteParaStorage(pedidoTransporte, listaArquivosLaudo, descricaoAlteracao);
		
		pedidoTransporte.setDataAtualizacao(LocalDateTime.now());
		pedidoTransporte.setStatus(new StatusPedidoTransporte(StatusPedidosTransporte.AGUARDANDO_CONFIRMACAO.getId()));
		save(pedidoTransporte);

		Paciente paciente = obterPacientePorPedidoTransporte(pedidoTransporte);
		
		fecharTarefaPedidoTransporte(paciente);
		criarTarefaDePedidoDeTransporte(pedidoTransporte);
	}


	@Override
	public PedidoTransporte obterPedidoPorLogistica(Long idPedidoLogistica) {
		return pedidoTransporteRepository.findByPedidoLogisticaId(idPedidoLogistica);
	}

	
	private void fecharTarefaPedidoTransporte(Paciente paciente) {
		if(!TiposTarefa.PEDIDO_TRANSPORTE.getConfiguracao()
				.listarTarefa()
				.comRmr(paciente.getRmr())
				.comStatus(Arrays.asList(StatusTarefa.ATRIBUIDA, StatusTarefa.ABERTA))
				.apply()
				.getValue()
				.isEmpty()) {
			TiposTarefa.PEDIDO_TRANSPORTE.getConfiguracao()
			.fecharTarefa()
			.comStatus(Arrays.asList(StatusTarefa.ATRIBUIDA, StatusTarefa.ABERTA))
			.comRmr(paciente.getRmr())
			.apply();			
		}
	}
	
	
	private List<String> enviarArquivoDeTransporteParaStorage(PedidoTransporte pedidoTransporte, List<MultipartFile> arquivos, String descricaoAlteracao) {
		if(pedidoTransporte.getArquivoPedidoTransporte() == null) {
			pedidoTransporte.setArquivoPedidoTransporte(new ArrayList<>());
		}
		
		List<String> caminhosArquivoParaRetorno = new ArrayList<String>();
		
		arquivos.forEach(arquivo->{
			validarArquivoTransporte(arquivo);
			
			String caminhoArquivo = null;
			try {
				ArquivoPedidoTransporte arquivoPedidoTransporte = new ArquivoPedidoTransporte();
				arquivoPedidoTransporte.setPedidoTransporte(pedidoTransporte);
				caminhoArquivo = enviarArquivoParaStorage(pedidoTransporte, arquivo);
				
				caminhosArquivoParaRetorno.add(caminhoArquivo);
				
				arquivoPedidoTransporte.setCaminho(caminhoArquivo);
				arquivoPedidoTransporte.setDescricaoAlteracao(descricaoAlteracao);
				
				pedidoTransporte.getArquivoPedidoTransporte().add(arquivoPedidoTransporte);
			}
			catch (IOException e) {
				throw new BusinessException("erro.pedido.transporte.arquivo_nao_enviado");
			}
		});
		return caminhosArquivoParaRetorno;
	}

	private void criarTarefaDePedidoDeTransporte(PedidoTransporte pedidoTransporte) {
		final Paciente paciente = obterPacientePorPedidoTransporte(pedidoTransporte);
		
		TiposTarefa.PEDIDO_TRANSPORTE.getConfiguracao()
			.criarTarefa()
			.comRmr(paciente.getRmr())
			.comObjetoRelacionado(pedidoTransporte.getId())
			.comParceiro(pedidoTransporte.getTransportadora())
			.comStatus(StatusTarefa.ABERTA)
			.apply();
	}

	private void validarArquivoTransporte(MultipartFile arquivo) {
		ArrayList<CampoMensagem> resultadoValidacao = new ArrayList<CampoMensagem>();
		List<Configuracao> configuracoes = configuracaoService.listarConfiguracao(Configuracao.EXTENSAO_ARQUIVO_PEDIDO_TRANSPORTE,
				Configuracao.TAMANHO_ARQUIVO_PEDIDO_TRANSPORTE);
		ArquivoUtil.validarArquivo(arquivo, resultadoValidacao, messageSource, configuracoes.get(0), configuracoes.get(1));
		
		if (!resultadoValidacao.isEmpty()) {
			throw new ValidationException("erro.pedido.transporte.arquivo.invalido", resultadoValidacao);
		}
	}
	
	@Override
	public Paciente obterPacientePorPedidoTransporte(PedidoTransporte pedidoTransporte) {
		final PedidoLogistica pedidoLogistica = pedidoTransporte.getPedidoLogistica();
		
		if(pedidoLogistica.getPedidoWorkup() != null){
			return pedidoWorkupService.obterPaciente(pedidoLogistica.getPedidoWorkup());
		}
		else if(pedidoLogistica.getPedidoColeta() != null){
			return pedidoColetaService.obterPacientePorPedidoColeta(pedidoLogistica.getPedidoColeta());
		}
		throw new IllegalStateException("Pedido de logística " + pedidoLogistica.getId() + 
				" não possui origem relacionada, ou seja, não tem relacionamento com workup ou coleta.");
	}
	
	@Override
	public Doador obterDoador(PedidoTransporte pedidoTransporte) {
		final PedidoLogistica pedidoLogistica = pedidoTransporte.getPedidoLogistica();
		
		if(pedidoLogistica.getPedidoWorkup() != null){
			return pedidoWorkupService.obterDoador(pedidoLogistica.getPedidoWorkup());
		}
		else if(pedidoLogistica.getPedidoColeta() != null){
			return pedidoColetaService.obterDoador(pedidoLogistica.getPedidoColeta());
		}
		throw new IllegalStateException("Pedido de logística " + pedidoLogistica.getId() + 
				" não possui origem relacionada, ou seja, não tem relacionamento com workup ou coleta.");
	}
	
	/**
	 * Fecha tarefa de envio de arquivo de CNT.
	 * 
	 * @param pedidoTransporte - objeto do pedido de transporte.
	 */
	public void fecharTarefaCnt(PedidoTransporte pedidoTransporte) {
		final Paciente paciente = obterPacientePorPedidoTransporte(pedidoTransporte);
		
		TiposTarefa.INFORMAR_DOCUMENTACAO_MATERIAL_AEREO.getConfiguracao().fecharTarefa()
				.comObjetoRelacionado(pedidoTransporte.getPedidoLogistica().getId())
				.comRmr(paciente.getRmr())
				.comStatus(Arrays.asList(StatusTarefa.ATRIBUIDA))
				.comUsuario(usuarioService.obterUsuarioLogado())
				.apply();
	}

	private String enviarArquivoParaStorage(PedidoTransporte pedidoTransporte, MultipartFile arquivo) throws IOException {
		LOGGER.debug("ENVIANDO O ARQUIVO PARA O STORAGE");
		Instant instant = Instant.now();
		long timeStampMillis = instant.toEpochMilli();

		Long idPedidoTransporte = pedidoTransporte.getId();

		String diretorio = StorageService.DIRETORIO_PEDIDO_TRANSPORTE + "/" + idPedidoTransporte;
		String nomeArquivo = ArquivoUtil.obterNomeArquivo(timeStampMillis, arquivo);
		storageService.upload(nomeArquivo, diretorio, arquivo);
		LOGGER.debug("ARQUIVO ENVIADO PARA O STORAGE");
		return diretorio + "/" + nomeArquivo;
	}

	@Override
	public IRepository<PedidoTransporte, Long> getRepository() {
		return pedidoTransporteRepository;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.org.cancer.modred.service.IPedidoTransporteService#confirmarPedidoTransporte(java.lang.Long,
	 * br.org.cancer.modred.controller.dto.ConfirmacaoTransporteDTO)
	 */
	@CreateLog
	@Override
	public PedidoTransporte confirmarPedidoTransporte(Long id, ConfirmacaoTransporteDTO confirmacaoTransporteDTO) {

		PedidoTransporte pedidoTransporte = pedidoTransporteRepository.findById(id).get();
		if (pedidoTransporte == null) {
			throw new BusinessException("erro.pedido.transporte.nao.encontrado");
		}
		
		pedidoTransporte.setStatusAnterior(pedidoTransporte.getStatus());

		fecharTarefaAtual(pedidoTransporte);

		if (confirmacaoTransporteDTO.isVoo()) {
			if (confirmacaoTransporteDTO.getIdCourier() == null) {
				throw new BusinessException("erro.dados.courier.obrigatorio");
			}
			if (confirmacaoTransporteDTO.getDadosVoo() == null || "".equals(confirmacaoTransporteDTO.getDadosVoo())) {
				throw new BusinessException("erro.dados.voo.obrigatorio");
			}

			criarTarefaParaLogisticaAtualizarDocumentoVoo(pedidoTransporte);
			
			pedidoLogisticaService.atualizarEvento(pedidoTransporte.getPedidoLogistica().getId(), new TipoPedidoLogistica(
					TiposPedidoLogistica.MATERIAL_COM_AEREO.getId()));
			
			//Comnetado por remover a entidade courier do projeto
			//Courier courier = courierService.findById(confirmacaoTransporteDTO.getIdCourier());
			//pedidoTransporte.setCourier(courier);
			pedidoTransporte.setDadosVoo(confirmacaoTransporteDTO.getDadosVoo());
			pedidoTransporte.setStatus(new StatusPedidoTransporte(StatusPedidosTransporte.AGUARDANDO_DOCUMENTACAO.getId()));
		}
		else {
			pedidoTransporte.setStatus(new StatusPedidoTransporte(StatusPedidosTransporte.AGUARDANDO_RETIRADA.getId()));
			criarTarefaParaTransportadoraRetirarMaterial(pedidoTransporte);
		}

		pedidoTransporte.setUsuarioResponsavel(usuarioService.obterUsuarioLogado());
		return pedidoTransporteRepository.save(pedidoTransporte);
	}

	/**
	 * Cria tarefa para transportadora retirar o material.
	 * 
	 * @param pedidoTransporte - entidade de pedido de transporte.
	 */
	public void criarTarefaParaTransportadoraRetirarMaterial(PedidoTransporte pedidoTransporte) {
		final Paciente paciente = obterPacientePorPedidoTransporte(pedidoTransporte);
		
		TiposTarefa.INFORMAR_RETIRADA_MATERIAL.getConfiguracao().criarTarefa()
				.comObjetoRelacionado(pedidoTransporte.getId())
				.comParceiro(pedidoTransporte.getTransportadora())
				.comRmr(paciente.getRmr())
				.apply();
	}

	/**
	 * Cria tarefa para o analista de logistica atualizar o documento.
	 * 
	 * @param pedidoTransporte - entidade de pedido de transporte.
	 */
	public void criarTarefaParaLogisticaAtualizarDocumentoVoo(PedidoTransporte pedidoTransporte) {
		final Paciente paciente = obterPacientePorPedidoTransporte(pedidoTransporte);
		
		TiposTarefa.INFORMAR_DOCUMENTACAO_MATERIAL_AEREO.getConfiguracao().criarTarefa()
				.comObjetoRelacionado(pedidoTransporte.getPedidoLogistica().getId())
				.comRmr(paciente.getRmr())
				.apply();
	}

	/**
	 * Fecha a tarefa atual de agendamento de transporte.
	 * 
	 * @param pedidoTransporte - entidade de pedido de transporte.
	 */
	public void fecharTarefaAtual(PedidoTransporte pedidoTransporte) {
		final Paciente paciente = obterPacientePorPedidoTransporte(pedidoTransporte);
		
		TiposTarefa.PEDIDO_TRANSPORTE.getConfiguracao().fecharTarefa()
				.comObjetoRelacionado(pedidoTransporte.getId())
				.comRmr(paciente.getRmr())
				.comStatus(Arrays.asList(StatusTarefa.ATRIBUIDA))
				.comUsuario(usuarioService.obterUsuarioLogado())
				.apply();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.org.cancer.modred.service.IPedidoTransporteService#obterPedidoTransporte(java.lang.Long)
	 */
	@Override
	public PedidoTransporte obterPedidoTransporte(Long id) {
		return pedidoTransporteRepository.findById(id).get();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.org.cancer.modred.service.IPedidoTransporteService#listarPedidosTransportePendentes()
	 */
	@Override
	public List<PedidoTransporte> listarPedidosTransportePendentes() {
		List<Long> status = Arrays.asList(StatusPedidosTransporte.AGUARDANDO_DOCUMENTACAO.getId(),
				StatusPedidosTransporte.AGUARDANDO_RETIRADA.getId(), StatusPedidosTransporte.AGUARDANDO_ENTREGA.getId());

		return pedidoTransporteRepository.listarPedidosTransportes(usuarioService.obterUsuarioLogadoId(), status);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.org.cancer.modred.service.IPedidoTransporteService#obterCartaTransporteMO(java.lang.Long)
	 */
	@Override
	@Transactional(readOnly = true)
	public File obterCartaTransporteMO(Long id) {
		PedidoTransporte pedidoTransporte = findById(id);
		
		Relatorio relatorio = relatorioService.obterRelatorioPorCodigo(Relatorios.CARTA_MO.getCodigo());
		String marcacaoCelulaTroncoMO = FontesCelulas.MEDULA_OSSEA.getSigla().equals(pedidoTransporte.getPedidoLogistica()
				.getPedidoColeta().getPedidoWorkup().getFonteCelula().getSigla()) ? "X" : "&nbsp;";
		
		String marcacaoSanguePeriferico = FontesCelulas.SANGUE_PERIFERICO.getSigla().equals(pedidoTransporte.getPedidoLogistica()
				.getPedidoColeta().getPedidoWorkup().getFonteCelula().getSigla()) ? "X" : "&nbsp;";

		return new HtmlReportGenerator()
				.comParametro(ParametrosRelatorios.MARCACAO_CELULA_TRONCO_MEDULA_OSSEA,marcacaoCelulaTroncoMO)
				.comParametro(ParametrosRelatorios.MARCACAO_CELULA_TRONCO_SANGUE_PERIFERICO,marcacaoSanguePeriferico)
				.gerarPdf(relatorio);
	}

	@Override
	@Transactional(readOnly = true)
	public File obterRelatorioTransporte(Long idPedidoTransporte) {
		Relatorio relatorio = relatorioService.obterRelatorioPorCodigo(Relatorios.CARTA_TRANSPORTE.getCodigo());
		return new HtmlReportGenerator().gerarPdf(relatorio);
	}
	
	@Override
	public File obterVoucherLiberacaoViagemCNT(Long idPedidoTransporte) {
		try {
			return arquivoPedidoTransporteService.listarArquivosTransporteZipados(findById(idPedidoTransporte));
		} 
		catch (IOException e) {
			throw new BusinessException("erro.requisicao", e);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public PedidoTransporte salvar(PedidoTransporte pedidoTransporte) {
		pedidoTransporte.setDataAtualizacao(LocalDateTime.now());
		pedidoTransporte.setUsuarioResponsavel(usuarioService.obterUsuarioLogado());
		if (pedidoTransporte.getStatus() == null) {
			pedidoTransporte.setStatus(new StatusPedidoTransporte(StatusPedidosTransporte.EM_ANALISE.getId()));
		}
		return save(pedidoTransporte);
	}

	@CreateLog(TipoLogEvolucao.PEDIDO_TRANSPORTE_MATERIAL_RETIRADO_PARA_DOADOR)
	@Override
	public PedidoTransporte registrarRetirada(Long pedidoTransporteId, LocalDateTime dataHoraRetirada) {
		PedidoTransporte pedidoTransporte = findById(pedidoTransporteId);
		pedidoTransporte.setStatus(new StatusPedidoTransporte(StatusPedidosTransporte.AGUARDANDO_ENTREGA.getId()));
		pedidoTransporte.setDataRetirada(dataHoraRetirada);

		pedidoTransporte = salvar(pedidoTransporte);

		final Paciente paciente = pacienteService.obterPacientePorPedidoLogistica(pedidoTransporte.getPedidoLogistica());
		
		final Doador doador = pedidoColetaService.obterDoador(pedidoTransporte.getPedidoLogistica().getPedidoColeta());
		
		if(TiposDoador.INTERNACIONAL.getId().equals(doador.getIdTipoDoador()) ||
				TiposDoador.CORDAO_INTERNACIONAL.getId().equals(doador.getIdTipoDoador())){
			TiposTarefa.RETIRADA_MATERIAL_INTERNACIONAL.getConfiguracao().fecharTarefa()
			.comRmr(paciente.getRmr())
			.comObjetoRelacionado(pedidoTransporte.getId())
			.comStatus(Arrays.asList(StatusTarefa.ATRIBUIDA))
			.comUsuario(usuarioService.obterUsuarioLogado())
			.apply();
			
			TiposTarefa.ENTREGA_MATERIAL_INTERNACIONAL.getConfiguracao().criarTarefa()
			.comRmr(paciente.getRmr())
			.comStatus(StatusTarefa.ABERTA)
			.comObjetoRelacionado(pedidoTransporte.getId())
			.apply();			
		}
		else{
			TiposTarefa.INFORMAR_RETIRADA_MATERIAL.getConfiguracao().fecharTarefa()
			.comRmr(paciente.getRmr())
			.comObjetoRelacionado(pedidoTransporte.getId())
			.comStatus(Arrays.asList(StatusTarefa.ATRIBUIDA))
			.comUsuario(usuarioService.obterUsuarioLogado())
			.apply();
			
			TiposTarefa.PEDIDO_TRANSPORTE_ENTREGA.getConfiguracao().criarTarefa()
			.comRmr(paciente.getRmr())
			.comStatus(StatusTarefa.ABERTA)
			.comParceiro(pedidoTransporte.getTransportadora())
			.comObjetoRelacionado(pedidoTransporte.getId())
			.apply();
		}

		return pedidoTransporte;
	}

	@CreateLog(TipoLogEvolucao.PEDIDO_TRANSPORTE_MATERIAL_ENTREGUE_PARA_DOADOR)
	@Override
	public PedidoTransporte registrarEntrega(Long pedidoTransporteId, LocalDateTime dataHoraEntrega) {
		PedidoTransporte pedidoTransporte = findById(pedidoTransporteId);
		pedidoTransporte.setStatus(new StatusPedidoTransporte(StatusPedidosTransporte.ENTREGUE.getId()));
		pedidoTransporte.setDataEntrega(dataHoraEntrega);
		
		pedidoTransporte = salvar(pedidoTransporte);
		
		Paciente paciente = pacienteService.obterPacientePorPedidoLogistica(pedidoTransporte.getPedidoLogistica());		
		final Doador doador = pedidoColetaService.obterDoador(pedidoTransporte.getPedidoLogistica().getPedidoColeta());
		
		
		if(TiposDoador.INTERNACIONAL.getId().equals(doador.getTipoDoador().getId()) 
				||TiposDoador.CORDAO_INTERNACIONAL.get().equals(doador.getTipoDoador().getId())){
			
			TiposTarefa.ENTREGA_MATERIAL_INTERNACIONAL.getConfiguracao().fecharTarefa()
			.comRmr(paciente.getRmr())
			.comObjetoRelacionado(pedidoTransporte.getId())
			.comStatus(Arrays.asList(StatusTarefa.ATRIBUIDA))
			.comUsuario(usuarioService.obterUsuarioLogado())
			.apply();	
		}
		else{
			TiposTarefa.PEDIDO_TRANSPORTE_ENTREGA.getConfiguracao().fecharTarefa()
			.comRmr(paciente.getRmr())
			.comObjetoRelacionado(pedidoTransporte.getId())
			.comStatus(Arrays.asList(StatusTarefa.ATRIBUIDA))
			.comUsuario(usuarioService.obterUsuarioLogado())
			.apply();
		}
		
		//recebimentoColetaService.registrarRecebimentoColeta(doador.getId(), dataHoraEntrega);
				
		pedidoLogisticaService.fecharPedidoLogistica(pedidoTransporte.getPedidoLogistica().getId());
		
		return pedidoTransporte;
	}

	@Override
	public JsonViewPage<TarefaDTO> listarTarefasParaTransportadora(PageRequest pageRequest) {
		final Usuario usuarioLogado = usuarioService.obterUsuarioLogado();
		
		if(usuarioLogado.getTransportadora() == null){
			LOGGER.error("O usuário não está associado a uma transportadora e "
					+ "acessou uma funcionalidade para transportadoras. Possível falha de programação e/ou segurança.");
			throw new BusinessException("erro.requisicao");
		}
		
		JsonViewPage<TarefaDTO> listaTarefas = 
				TiposTarefa.PEDIDO_TRANSPORTE.getConfiguracao().listarTarefa()
					.comPaginacao(pageRequest)
					.comParceiros(Arrays.asList(usuarioLogado.getTransportadora()))
					.comStatus(Arrays.asList(StatusTarefa.ABERTA, StatusTarefa.ATRIBUIDA))
					.apply();
		
		return listaTarefas;
	}

	@Override
	public JsonViewPage<TarefaDTO> listarTarefasRetiradaMaterialInternacional(PageRequest paginacao) {
		JsonViewPage<TarefaDTO> listaTarefas = 
				TiposTarefa.MATERIAL_INTERNACIONAL_RETIRADA_ENTREGA.getConfiguracao().listarTarefa()
					.comPaginacao(paginacao)
					.comStatus(Arrays.asList(StatusTarefa.ABERTA, StatusTarefa.ATRIBUIDA))
					.apply();
		
		return listaTarefas;
	}

	/**
	 * @param configuracaoService the configuracaoService to set
	 */
	@Autowired
	public void setConfiguracaoService(ConfiguracaoService configuracaoService) {
		this.configuracaoService = configuracaoService;
	}

	/**
	 * @param relatorioService the relatorioService to set
	 */
	@Autowired
	public void setRelatorioService(IRelatorioService relatorioService) {
		this.relatorioService = relatorioService;
	}

	/**
	 * @param usuarioService the usuarioService to set
	 */
	@Autowired
	public void setUsuarioService(IUsuarioService usuarioService) {
		this.usuarioService = usuarioService;
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

//	/**
//	 * @param recebimentoColetaService the recebimentoColetaService to set
//	 */
//	@Autowired
//	public void setRecebimentoColetaService(IRecebimentoColetaService recebimentoColetaService) {
//		this.recebimentoColetaService = recebimentoColetaService;
//	}

	/**
	 * @param pedidoWorkupService the pedidoWorkupService to set
	 */
	@Autowired
	public void setPedidoWorkupService(IPedidoWorkupService pedidoWorkupService) {
		this.pedidoWorkupService = pedidoWorkupService;
	}

	/**
	 * @param pedidoColetaService the pedidoColetaService to set
	 */
	@Autowired
	public void setPedidoColetaService(IPedidoColetaService pedidoColetaService) {
		this.pedidoColetaService = pedidoColetaService;
	}

	/**
	 * @param arquivoPedidoTransporteService the arquivoPedidoTransporteService to set
	 */
	@Autowired
	public void setArquivoPedidoTransporteService(IArquivoPedidoTransporteService arquivoPedidoTransporteService) {
		this.arquivoPedidoTransporteService = arquivoPedidoTransporteService;
	}


	@Override
	public Paciente obterPaciente(PedidoTransporte pedidoTransporte) {		
		return pedidoTransporte.getPedidoLogistica().getPedidoWorkup() != null ?
				pedidoTransporte.getPedidoLogistica().getPedidoWorkup().getSolicitacao().getMatch().getBusca().getPaciente() :
				pedidoTransporte.getPedidoLogistica().getPedidoColeta().getSolicitacao().getMatch().getBusca().getPaciente();
	}


	@SuppressWarnings("rawtypes")
	@Override
	public String[] obterParametros(PedidoTransporte pedidoTransporte) {
		IDoador doador =  pedidoTransporte.getPedidoLogistica().getPedidoWorkup() != null ?
				(IDoador) pedidoTransporte.getPedidoLogistica().getPedidoWorkup().getSolicitacao().getMatch().getDoador() :
				(IDoador) pedidoTransporte.getPedidoLogistica().getPedidoColeta().getSolicitacao().getMatch().getDoador();
		return StringUtils.split(doador.getIdentificacao().toString());
	}

	@Override
	public LogEvolucao criarLog(PedidoTransporte pedidoTransporte, TipoLogEvolucao tipoLog, Perfis[] perfisExcluidos) {
		
		if (tipoLog == null || TipoLogEvolucao.INDEFINIDO.equals(tipoLog)) {
			if (StatusPedidosTransporte.EM_ANALISE.getId().equals(pedidoTransporte.getStatus().getId()) ||
					StatusPedidosTransporte.EM_ANALISE.getId().equals(pedidoTransporte.getStatusAnterior().getId())) {
				tipoLog = TipoLogEvolucao.PEDIDO_TRANSPORTE_AGENDADO_PARA_DOADOR;
			}
		}
		
		return super.criarLog(pedidoTransporte, tipoLog, perfisExcluidos);
	}


	@Override
	public void cancelarPedidoTransporte(Long idPedidoLogistica) {
		PedidoTransporte pedidoTransporte = pedidoTransporteRepository.findByPedidoLogisticaId(idPedidoLogistica);
		if (!StatusPedidosTransporte.ENTREGUE.getId().equals(pedidoTransporte.getStatus().getId())) {
			cancelarTarefasPedidoTransporte(pedidoTransporte);
			pedidoTransporte.setStatus(new StatusPedidoTransporte(StatusPedidosTransporte.CANCELADO.getId()));
			save(pedidoTransporte);
			
			
		}
		
	}


	private void cancelarTarefasPedidoTransporte(PedidoTransporte pedidoTransporte) {
		if (StatusPedidosTransporte.EM_ANALISE.getId().equals(pedidoTransporte.getStatus().getId()) || 
				StatusPedidosTransporte.AGUARDANDO_CONFIRMACAO.getId().equals(pedidoTransporte.getStatus().getId())) {
			
		}
		else if (StatusPedidosTransporte.AGUARDANDO_RETIRADA.getId().equals(pedidoTransporte.getStatus().getId())) {
			cancelarTarefaRetiradaMaterial(pedidoTransporte);
		}
		else if (StatusPedidosTransporte.AGUARDANDO_ENTREGA.getId().equals(pedidoTransporte.getStatus().getId())) {
			cancelarTarefaEntregaMaterial(pedidoTransporte);
		}
	}
	
	private void cancelarTarefaPedidoTRansporte(PedidoTransporte pedidoTransporte) {
		final Paciente paciente = obterPacientePorPedidoTransporte(pedidoTransporte);
		
		TarefaDTO tarefa = TiposTarefa.PEDIDO_TRANSPORTE.getConfiguracao().obterTarefa()
			.comObjetoRelacionado(pedidoTransporte.getId())			
			.comParceiros(Arrays.asList(pedidoTransporte.getTransportadora()))
			.comRmr(paciente.getRmr())
			.comStatus(Arrays.asList(StatusTarefa.ABERTA, StatusTarefa.ATRIBUIDA)) 
			.paraOutroUsuario(true)
			.apply();
		
		if (tarefa != null) {
			TiposTarefa.PEDIDO_TRANSPORTE.getConfiguracao().cancelarTarefa()
				.comTarefa(tarefa.getId())
				.apply();
		}
	}
	
	
	private void cancelarTarefaRetiradaMaterial(PedidoTransporte pedidoTransporte) {
		final Paciente paciente = obterPacientePorPedidoTransporte(pedidoTransporte);
		
		TarefaDTO tarefa = TiposTarefa.INFORMAR_RETIRADA_MATERIAL.getConfiguracao().obterTarefa()
			.comObjetoRelacionado(pedidoTransporte.getId())			
			.comParceiros(Arrays.asList(pedidoTransporte.getTransportadora()))
			.comRmr(paciente.getRmr())
			.comStatus(Arrays.asList(StatusTarefa.ABERTA, StatusTarefa.ATRIBUIDA)) 
			.paraOutroUsuario(true)
			.apply();
		
		if (tarefa != null) {
			TiposTarefa.INFORMAR_RETIRADA_MATERIAL.getConfiguracao().cancelarTarefa()
				.comTarefa(tarefa.getId())
				.apply();
		}
	}
	
	private void cancelarTarefaEntregaMaterial(PedidoTransporte pedidoTransporte) {
		final Paciente paciente = obterPacientePorPedidoTransporte(pedidoTransporte);
		
		
		TarefaDTO tarefa = TiposTarefa.PEDIDO_TRANSPORTE_ENTREGA.getConfiguracao().obterTarefa()
			.comObjetoRelacionado(pedidoTransporte.getId())			
			.comParceiros(Arrays.asList(pedidoTransporte.getTransportadora()))
			.comRmr(paciente.getRmr())
			.comStatus(Arrays.asList(StatusTarefa.ABERTA, StatusTarefa.ATRIBUIDA)) 
			.paraOutroUsuario(true)
			.apply();
		
		if (tarefa != null) {
			TiposTarefa.PEDIDO_TRANSPORTE_ENTREGA.getConfiguracao().cancelarTarefa()
				.comTarefa(tarefa.getId())
				.apply();
		}
	}
	
	@Override
	public void atualizaPedidoTransporte(PedidoTransporte pedidoTransporte, DetalheMaterialDTO detalhe) {
		//PedidoTransporte pedidoTransporte = obterPedidoTransporte(idPpedidoTransporte);
		pedidoTransporte.setHoraPrevistaRetirada(detalhe.getHoraPrevistaRetirada());
		
//		Transportadora transportadora = new Transportadora();
//		transportadora.setId(detalhe.getTransportadora().getId());
//		transportadora.setNome(detalhe.getTransportadora().getNome());
		pedidoTransporte.setTransportadora(detalhe.getTransportadora().getId());
		
		pedidoTransporte.setStatus(new StatusPedidoTransporte(StatusPedidosTransporte.EM_ANALISE.getId()));
		salvar(pedidoTransporte);
		
	}

	@Override
	public PedidoTransporte criarPedidoTransporte(PedidoLogistica pedidoLogistica, DetalheMaterialDTO detalhe) {
		PedidoTransporte pedidoTransporte = new PedidoTransporte();
		pedidoTransporte.setHoraPrevistaRetirada(detalhe.getHoraPrevistaRetirada());
		
		//Transportadora transportadora = new Transportadora();
		//transportadora.setId(detalhe.getTransportadora().getId());
		//transportadora.setNome(detalhe.getTransportadora().getNome());
		pedidoTransporte.setTransportadora(detalhe.getTransportadora().getId());

		pedidoTransporte.setPedidoLogistica(pedidoLogistica);
		pedidoTransporte.setStatus(new StatusPedidoTransporte(StatusPedidosTransporte.EM_ANALISE.getId()));
		return salvar(pedidoTransporte);
	}
	
	
}

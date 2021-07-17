package br.org.cancer.modred.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.dto.ConfirmacaoTransporteDTO;
import br.org.cancer.modred.controller.dto.RetornoInclusaoDTO;
import br.org.cancer.modred.controller.view.TransportadoraView;
import br.org.cancer.modred.feign.dto.TarefaDTO;
import br.org.cancer.modred.helper.JsonViewPage;
import br.org.cancer.modred.model.PedidoTransporte;
import br.org.cancer.modred.service.IPedidoTransporteService;
import br.org.cancer.modred.util.AppUtil;
import br.org.cancer.modred.util.CampoMensagem;

/**
 * Classe REST envolvendo a entidade pedido de transporte.
 * 
 * @author Filipe Paes
 *
 */
@RestController
@RequestMapping(value = "/api/pedidostransporte", produces = "application/json;charset=UTF-8")
public class PedidoTransporteController {

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private IPedidoTransporteService pedidoTransporteService;

	/**
	 * Método para salvar pedido de transporte com o arquivo.
	 * 
	 * @param listaArquivosLaudo - arquivo fisico a ser enviado para o servidor.
	 * @param pedidoTransporte - pedido de transporte para ser atualizado com o arquivo.
	 * @return CampoMensagem
	 * @throws Exception Se ocorrer algum erro
	 */
	@RequestMapping(value = "transporte", method = RequestMethod.POST,
					consumes = "multipart/form-data")
	@PreAuthorize("hasPermission('SOLICITAR_LOGISTICA_MATERIAL') ")
	public ResponseEntity<RetornoInclusaoDTO> salvarPedidoTransporte(
			@RequestPart(name = "file", required = true) List<MultipartFile> listaArquivosLaudo,
			@RequestPart(required = true) PedidoTransporte pedidoTransporte) throws Exception {

		pedidoTransporteService.salvarPedidoComArquivo(pedidoTransporte, listaArquivosLaudo);
		return new ResponseEntity<RetornoInclusaoDTO>(new RetornoInclusaoDTO(null, AppUtil.getMensagem(messageSource,
				"pedido.transporte.incluido.sucesso")), HttpStatus.OK);
	}
	
	
	/**
	 * Método para atualizar carta cnt.
	 * 
	 * @param listaArquivosLaudo - arquivo fisico a ser enviado para o servidor.
	 * @param idPedidoLogistica - id do pedido de logistica.
	 * @return CampoMensagem
	 * @throws Exception Se ocorrer algum erro
	 */
	@RequestMapping(value = "transporte/cartacnt", method = RequestMethod.POST,
					consumes = "multipart/form-data")
	@PreAuthorize("hasPermission('SOLICITAR_LOGISTICA_MATERIAL') ")
	public ResponseEntity<RetornoInclusaoDTO> atualizarCartaCNT(
			@RequestPart(name = "file", required = true) List<MultipartFile> listaArquivosLaudo,
			@RequestPart(name=  "id", required = true) Long idPedidoLogistica,
			@RequestPart(name= "descricao")String descricao) throws Exception {

		pedidoTransporteService.atualizarArquivoCartaCnt(idPedidoLogistica, listaArquivosLaudo, descricao);
		return new ResponseEntity<RetornoInclusaoDTO>(new RetornoInclusaoDTO(null, AppUtil.getMensagem(messageSource,
				"pedido.transporte.incluido.sucesso")), HttpStatus.OK);
	}

	/**
	 * Método para confirmar o agendamento de transporte.
	 * 
	 * @param id - identificador do pedido de transporte.
	 * @param confirmacaoTransporteDTO - dto com informações sobre o voo.
	 * @return CampoMensagem - mensagem de sucesso.
	 */
	@RequestMapping(value = "{id}", method = RequestMethod.PUT)
	@PreAuthorize("hasPermission('AGENDAR_TRANSPORTE_MATERIAL')")
	public ResponseEntity<CampoMensagem> confirmarPedidoTransporte(
			@PathVariable(required = true) Long id,
			@RequestBody(required = true) ConfirmacaoTransporteDTO confirmacaoTransporteDTO) {

		pedidoTransporteService.confirmarPedidoTransporte(id, confirmacaoTransporteDTO);
		return new ResponseEntity<CampoMensagem>(new CampoMensagem(AppUtil.getMensagem(messageSource,
				"sucesso.pedido.transporte.confirmado")), HttpStatus.OK);
	}

	/**
	 * Método para confirmar o agendamento de transporte.
	 * 
	 * @param id - identificador do pedido de transporte.
	 * @param confirmacaoTransporteDTO - dto com informações sobre o voo.
	 * @return CampoMensagem - mensagem de sucesso.
	 */
	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	@JsonView(TransportadoraView.AgendarTransporte.class)
	@PreAuthorize("hasPermission('AGENDAR_TRANSPORTE_MATERIAL')")
	public ResponseEntity<PedidoTransporte> obterPedidoTransporte(
			@PathVariable(required = true) Long id) {

		return new ResponseEntity<PedidoTransporte>(pedidoTransporteService.findById(id), HttpStatus.OK);
	}

	/**
	 * Método para confirmar o agendamento de transporte.
	 * 
	 * @param pedidoTransporteId - identificador do pedido de transporte.
	 * @param dataRetirada - Data de Retirada do material.
	 * @return CampoMensagem - mensagem de sucesso.
	 */
	@RequestMapping(value = "{id}/efetuarretirada", method = RequestMethod.PUT)
	public ResponseEntity<CampoMensagem> confirmarRetiradaPedidoTransporte(
			@PathVariable(name = "id", required = true) Long pedidoTransporteId,
			@RequestBody(required = true) LocalDateTime dataRetirada) {

		pedidoTransporteService.registrarRetirada(pedidoTransporteId, dataRetirada);

		return new ResponseEntity<CampoMensagem>(new CampoMensagem(AppUtil.getMensagem(messageSource,
				"pedido.transporte.retirado.sucesso")), HttpStatus.OK);
	}

	/**
	 * Método para listar os pedidos pendentes de ação do analista de logistica a subir os documentos.
	 * 
	 * @return List<PedidoTransporte>
	 */
	@RequestMapping(value = "pendentes", method = RequestMethod.GET)
	@JsonView(TransportadoraView.Listar.class)
	@PreAuthorize("hasPermission('AGENDAR_TRANSPORTE_MATERIAL')")
	public ResponseEntity<List<PedidoTransporte>> listarPedidosTransportePendentes() {

		return new ResponseEntity<List<PedidoTransporte>>(pedidoTransporteService.listarPedidosTransportePendentes(),
				HttpStatus.OK);
	}

	/**
	 * Método para confirmar a entrega do material coletado.
	 * 
	 * @param id - identificador do pedido de transporte.
	 * @param dataEntrega - Data de Retirada do material.
	 * @return CampoMensagem - mensagem de sucesso.
	 */
	@RequestMapping(value = "{id}/efetuarentrega", method = RequestMethod.PUT)
	public ResponseEntity<CampoMensagem> confirmarEntregaPedidoTransporte(
			@PathVariable(required = true) Long id,
			@RequestBody(required = true) LocalDateTime dataEntrega) {
		
		pedidoTransporteService.registrarEntrega(id, dataEntrega);

		return new ResponseEntity<CampoMensagem>(new CampoMensagem(AppUtil.getMensagem(messageSource,
				"pedido.transporte.entrega.sucesso")), HttpStatus.OK);
	}
	


	/**
	 * Método utilizado para executar o download da carta para courier.
	 * 
	 * @param idPedidoTransporte id do pedido de transporte para pegar o tipo de fonte
	 * @param response response
	 * @throws IOException ao ler o arquivo
	 */
	@RequestMapping(value = "{id}/download/carta", method = RequestMethod.GET)
	public void baixarCartaMO(
			@PathVariable(name = "id", required = true) Long idPedidoTransporte,
			HttpServletResponse response) throws IOException {
		File arquivo = pedidoTransporteService.obterCartaTransporteMO(idPedidoTransporte);

		ServletOutputStream stream = response.getOutputStream();

		response.setContentType(Files.probeContentType(arquivo.toPath()));
		response.addHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		response.addHeader("Content-Disposition",
				String.format("inline; filename=\"" + arquivo.getName() + "\""));
		FileCopyUtils.copy(new FileInputStream(arquivo), stream);
		response.flushBuffer();
	}

	/**
	 * Método utilizado para executar o download do relatório de transporte.
	 * 
	 * @param idPedidoTransporte id do pedido de transporte para pegar o tipo de fonte
	 * @param response response
	 * @throws IOException ao ler o arquivo
	 */
	@RequestMapping(value = "{id}/download/transporte", method = RequestMethod.GET)
	public void baixarRelatorioTransporte(
			@PathVariable(name = "id", required = true) Long idPedidoTransporte,
			HttpServletResponse response) throws IOException {
		File arquivo = pedidoTransporteService.obterRelatorioTransporte(idPedidoTransporte);

		ServletOutputStream stream = response.getOutputStream();

		response.setContentType(Files.probeContentType(arquivo.toPath()));
		response.addHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		response.addHeader("Content-Disposition",
				String.format("inline; filename=\"" + arquivo.getName() + "\""));
		FileCopyUtils.copy(new FileInputStream(arquivo), stream);
		response.flushBuffer();
	}
	
	/**
	 * Método utilizado para executar o download do relatório de transporte.
	 * 
	 * @param idPedidoTransporte id do pedido de transporte para pegar o tipo de fonte
	 * @param response response
	 * @throws IOException ao ler o arquivo
	 */
	@RequestMapping(value = "{id}/download", method = RequestMethod.GET)
	public void baixarVoucherAutorizacaoCNT(
			@PathVariable(name = "id", required = true) Long idPedidoTransporte,
			HttpServletResponse response) throws IOException {
		File arquivo = pedidoTransporteService.obterRelatorioTransporte(idPedidoTransporte);

		ServletOutputStream stream = response.getOutputStream();

		response.setContentType(Files.probeContentType(arquivo.toPath()));
		response.addHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		response.addHeader("Content-Disposition",
				String.format("inline; filename=\"" + arquivo.getName() + "\""));
		FileCopyUtils.copy(new FileInputStream(arquivo), stream);
		response.flushBuffer();
	}
	
	/**
	 * Realiza o download do voucher para autorização de viagem da CNT.
	 * 
	 * @param idPedidoTransporte id do pedido de transporte para pegar o tipo de fonte
	 * @param response response
	 * @throws IOException ao ler o arquivo
	 */
	@RequestMapping(value = "{id}/download/voucherCnt", method = RequestMethod.GET)
	public void baixarVoucherCNT(@PathVariable(name = "id", required = true) Long idPedidoTransporte,
			HttpServletResponse response) throws IOException {
		File arquivo = pedidoTransporteService.obterVoucherLiberacaoViagemCNT(idPedidoTransporte);

		ServletOutputStream stream = response.getOutputStream();

		response.setContentType(Files.probeContentType(arquivo.toPath()));
		response.addHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		response.addHeader("Content-Disposition",
				String.format("inline; filename=\"" + arquivo.getName() + "\""));
		FileCopyUtils.copy(new FileInputStream(arquivo), stream);
		response.flushBuffer();
	}

	/**
	 * Lista todos os pedidos de workups internacional disponíveis para atribuição.
	 * 
	 * @param pagina página que o resultado deverá separar para o retorno ao front-end.
	 * @param quantidadeRegistros quantidade de registros por página.
	 * @return lista de tarefas paginadas.
	 */
	@RequestMapping(value = "tarefas/transportadora", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('AGENDAR_TRANSPORTE_MATERIAL')")
	public JsonViewPage<TarefaDTO> listarTarefasTransportadoraParaUsuarioLogado(
			@RequestParam(required = true) int pagina,
			@RequestParam(required = true) int quantidadeRegistros) {
		return pedidoTransporteService.listarTarefasParaTransportadora(new PageRequest(pagina, quantidadeRegistros));
	}
	
	/**
	 * Lista todos os pedidos de workups internacional disponíveis para atribuição.
	 * 
	 * @param pagina página que o resultado deverá separar para o retorno ao front-end.
	 * @param quantidadeRegistros quantidade de registros por página.
	 * @return lista de tarefas paginadas.
	 */
	@RequestMapping(value = "tarefas/transportadora/internacional", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('AGENDAR_TRANSPORTE_MATERIAL') || hasPermission('RECEBER_AMOSTRA_INTERNACIONAL')")
	public JsonViewPage<TarefaDTO> listarTarefasTransportadoraMaterialInternacional(
			@RequestParam(required = true) int pagina,
			@RequestParam(required = true) int quantidadeRegistros) {
		return pedidoTransporteService.listarTarefasRetiradaMaterialInternacional(new PageRequest(pagina, quantidadeRegistros));
	}

}

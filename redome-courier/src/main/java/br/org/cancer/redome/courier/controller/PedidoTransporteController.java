package br.org.cancer.redome.courier.controller;

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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.org.cancer.redome.courier.controller.dto.ConfirmacaoTransporteDTO;
import br.org.cancer.redome.courier.controller.dto.DetalhePedidoTransporteDTO;
import br.org.cancer.redome.courier.controller.dto.PedidoTransporteDTO;
import br.org.cancer.redome.courier.controller.dto.RetiradaEntregaDTO;
import br.org.cancer.redome.courier.controller.dto.TarefasPedidoTransporteDTO;
import br.org.cancer.redome.courier.model.PedidoTransporte;
import br.org.cancer.redome.courier.model.domain.Recursos;
import br.org.cancer.redome.courier.service.IArquivoPedidoTransporteService;
import br.org.cancer.redome.courier.service.IPedidoTransporteService;
import br.org.cancer.redome.courier.util.AppUtil;

/**
 * Classe de REST controller para pedido de transporte.
 * 
 * @author Bruno Sousa
 *
 */
@RestController
@RequestMapping(value = "/api", produces = "application/json;charset=UTF-8")
public class PedidoTransporteController {

	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private IPedidoTransporteService pedidoTransporteService;
	
	@Autowired
	private IArquivoPedidoTransporteService arquivoPedidoTransporteService;
	
	
	/**
	 * Lista todas as tarefas de pedido de transporte de material nacional.
	 * 
	 * @param pagina página que o resultado deverá separar para o retorno ao front-end.
	 * @param quantidadeRegistros quantidade de registros por página.
	 * @return lista de tarefas paginadas.
	 * @throws Exception 
	 */
	@GetMapping(value = "/pedidostransporte/tarefas")
	@PreAuthorize("hasPermission('" + Recursos.AGENDAR_TRANSPORTE_MATERIAL + "')")
	public ResponseEntity<Page<TarefasPedidoTransporteDTO>> listarTarefasTransportadoraParaUsuarioLogado(
			@RequestParam(required = true) int pagina,
			@RequestParam(required = true) int quantidadeRegistros) throws Exception {
		return ResponseEntity.ok(pedidoTransporteService.listarTarefas(PageRequest.of(pagina, quantidadeRegistros)));
	}
	
	/**
	 * Lista todos os pedido de transporte de material do usuário logdo e com os status:
	 * AGUARDANDO_DOCUMENTACAO, AGUARDANDO_RETIRADA, AGUARDANDO_ENTREGA
	 * 
	 * @param pagina página que o resultado deverá separar para o retorno ao front-end.
	 * @param quantidadeRegistros quantidade de registros por página.
	 * @return lista dos pedidos de transporte.
	 * @throws Exception 
	 */
	@PreAuthorize("hasPermission('" + Recursos.AGENDAR_TRANSPORTE_MATERIAL + "') "
			+ "||  hasPermission('" + Recursos.SOLICITAR_LOGISTICA_MATERIAL + "') ")
	@GetMapping(value = "/pedidostransporte/emandamento")
	public ResponseEntity<Page<TarefasPedidoTransporteDTO>> listarPedidosTransporteEmAndamento (
			@RequestParam(required = true) int pagina,
			@RequestParam(required = true) int quantidadeRegistros) {
		return ResponseEntity.ok(pedidoTransporteService.listarPedidosTransporteEmAndamento(PageRequest.of(pagina, quantidadeRegistros)));
	}

	/**
	 * Salva a pedido de transporte nacional.
	 * 
	 * @param pedidoLogisticaId - id da logistica a ser gravada.
	 * @param detalheMaterial - detalhamento a ser persistido.
	 * @return Mensagem de OK, caso não ocorram erros.
	 */
	@PreAuthorize("hasPermission('" + Recursos.AGENDAR_TRANSPORTE_MATERIAL + "') "
			+ "||  hasPermission('" + Recursos.SOLICITAR_LOGISTICA_MATERIAL + "') ")
	@PostMapping(value = "/pedidostransporte")
	public ResponseEntity<PedidoTransporte> criarPedidoTransporte(
			@RequestBody(required = true) PedidoTransporteDTO pedidoTransporteDTO) {

		return ResponseEntity.ok(pedidoTransporteService.criarPedidoTransporte(pedidoTransporteDTO));
	}

	@PreAuthorize("hasPermission('" + Recursos.AGENDAR_TRANSPORTE_MATERIAL + "') "
			+ "||  hasPermission('" + Recursos.SOLICITAR_LOGISTICA_MATERIAL + "') ")
	@GetMapping("/pedidostransporte/detalhestransporte")
	public ResponseEntity<PedidoTransporteDTO> obterPedidoTransportePorIdLogiticaEStatus(
			@RequestParam(required=true) Long idPedidoLogistica,
			@RequestParam(required=true) Long idStatusTransporte) {
		return ResponseEntity.ok(pedidoTransporteService.obterPedidoTransportePorIdLogiticaEStatus(idPedidoLogistica, idStatusTransporte));
	}
	
	/**
	 * Método para confirmar o agendamento de transporte.
	 * 
	 * @param id - identificador do pedido de transporte.
	 * @param confirmacaoTransporteDTO - dto com informações sobre o voo.
	 * @return CampoMensagem - mensagem de sucesso.
	 */
	@GetMapping(value = "/pedidotransporte/{id}")
	@PreAuthorize("hasPermission('AGENDAR_TRANSPORTE_MATERIAL')")
	public ResponseEntity<DetalhePedidoTransporteDTO> obterDetalhePedidoTransporte(
			@PathVariable(required = true) Long id) {

		return ResponseEntity.ok(pedidoTransporteService.obterDetalhePedidoTransporte(id));
	}
	
	
	/**
	 * Método para confirmar o agendamento de transporte.
	 * 
	 * @param id - identificador do pedido de transporte.
	 * @param confirmacaoTransporteDTO - dto com informações sobre o voo.
	 * @return CampoMensagem - mensagem de sucesso.
	 */
	@PostMapping(value = "/pedidotransporte/{id}/confirmaragendamento")
	@PreAuthorize("hasPermission('" + Recursos.AGENDAR_TRANSPORTE_MATERIAL + "') ")
	public ResponseEntity<String> confirmarPedidoTransporte(
			@PathVariable(required = true) Long id,
			@RequestBody(required = true) ConfirmacaoTransporteDTO confirmacaoTransporteDTO) {

		pedidoTransporteService.confirmarPedidoTransporte(id, confirmacaoTransporteDTO);
		return ResponseEntity.ok(AppUtil.getMensagem(messageSource,	"pedido.transporte.sucesso.confirmado"));
	}
	
	
	/**
	 * Realiza o download do voucher de viagem da CNT.
	 * 
	 * @param idPedidoTransporte id do pedido de transporte para pegar o tipo de fonte
	 * @param response response
	 * @throws IOException ao ler o arquivo
	 */
	@GetMapping(value = "/pedidotransporte/{id}/download/voucherCnt")
	@PreAuthorize("hasPermission('" + Recursos.AGENDAR_TRANSPORTE_MATERIAL + "') ")
	public void obterArquivosPedidoTransporteZipados(@PathVariable(name = "id", required = true) Long idPedidoTransporte,
			HttpServletResponse response) throws IOException {
		File arquivo = arquivoPedidoTransporteService.obterArquivosPedidoTransporteZipados(idPedidoTransporte);

		ServletOutputStream stream = response.getOutputStream();

		response.setContentType(Files.probeContentType(arquivo.toPath()));
		response.addHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		response.addHeader("Content-Disposition",
				String.format("inline; filename=\"" + arquivo.getName() + "\""));
		FileCopyUtils.copy(new FileInputStream(arquivo), stream);
		response.flushBuffer();
	}

	@PostMapping(value = "/pedidotransporte/{id}/atualizaraereo", consumes = "multipart/form-data")
	@PreAuthorize("hasPermission('" + Recursos.SOLICITAR_LOGISTICA_MATERIAL + "')")
	public ResponseEntity<String> atualizarPedidoTransporteComArquivosCntEDataPrevistaRetirada(
			@PathVariable(required = true) Long id,
			@RequestPart(required = false) LocalDateTime dataPrevistaRetirada,
			@RequestPart(required = false) String descricaoAlteracao,
			@RequestPart(name = "file", required = false) List<MultipartFile> arquivos) throws Exception {
		
		pedidoTransporteService.atualizarInformacoesTransporteAereo(id, dataPrevistaRetirada, descricaoAlteracao, arquivos);
		
		return ResponseEntity.ok(AppUtil.getMensagem(messageSource,	"pedido.transporte.sucesso.confirmado"));
	}
	
	@PreAuthorize("hasPermission('" + Recursos.SALVAR_TRANSPORTADORA + "')")
	@GetMapping(value = "/pedidostransporte/tarefas/retiradaentrega")
	public ResponseEntity<List<RetiradaEntregaDTO>> listarRetiradaEEntrega(@RequestParam Integer pagina,
			@RequestParam Integer quantidadeRegistros) {
		
		return null;
		
	}
	
}

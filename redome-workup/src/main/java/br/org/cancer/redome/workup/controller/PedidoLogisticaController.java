package br.org.cancer.redome.workup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.org.cancer.redome.workup.dto.DetalheLogisticaInternacionalColetaDTO;
import br.org.cancer.redome.workup.dto.DetalheLogisticaMaterialDTO;
import br.org.cancer.redome.workup.dto.LogisticaDoadorColetaDTO;
import br.org.cancer.redome.workup.dto.LogisticaDoadorWorkupDTO;
import br.org.cancer.redome.workup.dto.LogisticaMaterialTransporteDTO;
import br.org.cancer.redome.workup.dto.TarefaLogisticaMaterialDTO;
import br.org.cancer.redome.workup.model.domain.Recursos;
import br.org.cancer.redome.workup.service.IArquivoVoucherLogisticaService;
import br.org.cancer.redome.workup.service.IPedidoLogisticaDoadorColetaService;
import br.org.cancer.redome.workup.service.IPedidoLogisticaDoadorWorkupService;
import br.org.cancer.redome.workup.service.IPedidoLogisticaMaterialColetaInternacionalService;
import br.org.cancer.redome.workup.service.IPedidoLogisticaMaterialColetaNacionalService;
import br.org.cancer.redome.workup.util.AppUtil;

@RestController
@RequestMapping(value = "/api", produces = "application/json;charset=UTF-8")
public class PedidoLogisticaController {
	
	@Autowired
	private IArquivoVoucherLogisticaService arquivoVoucherLogisticaService ;
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private IPedidoLogisticaDoadorWorkupService pedidoLogisticaDoadorWorkupService; 

	@Autowired
	private IPedidoLogisticaDoadorColetaService pedidoLogisticaDoadorColetaService; 

	@Autowired
	private IPedidoLogisticaMaterialColetaNacionalService pedidoLogisticaMaterialColetaNacionalService; 
	
	@Autowired
	private IPedidoLogisticaMaterialColetaInternacionalService pedidoLogisticaMaterialColetaInternacionalService; 
	

	/* ############################################################################### */
	/* ########################### LOGISTICA DOADOR WORKUP ########################### */

	
	@PostMapping(value = "/pedidologistica/{id}/voucher", consumes = "multipart/form-data")
	@PreAuthorize("hasPermission('" + Recursos.EFETUAR_LOGISTICA + "')"
			+ "||  hasPermission('" + Recursos.EFETUAR_LOGISTICA_DOADOR_COLETA + "')")
	public ResponseEntity<String> salvarVoucher(
			@PathVariable(required = true) Long id,
			@RequestPart(name = "file", required = true) MultipartFile arquivo) throws Exception {
		
		return ResponseEntity.ok(arquivoVoucherLogisticaService.enviarArquivoVoucherLogisticaParaStorage(id, arquivo));
	}
	
	@SuppressWarnings("rawtypes")
	@DeleteMapping(value = "/pedidologistica/{id}/voucher")
	@PreAuthorize("hasPermission('" + Recursos.EFETUAR_LOGISTICA + "')"
			+ "||  hasPermission('" + Recursos.EFETUAR_LOGISTICA_DOADOR_COLETA + "')")
	public ResponseEntity excluirArquivo(			
			@PathVariable(required = true) Long id,
			@RequestBody(required = true) String caminho) throws Exception {

		arquivoVoucherLogisticaService.excluirArquivoVoucherLogisticaDoStorage(id, caminho);
		return ResponseEntity.ok().build();
	}
	
	/**
	 * Método de gravação de logística.
	 * 
	 * @param pedidoLogisticaId - id da logistica a ser atualizada
	 * @param logistica - objeto a ser persistido.
	 * @return CampoMensagem contendo retorno da gravação.
	 * @throws Exception - caso haja erro na gravação de logística.
	 */
	@PutMapping(value = "/pedidologistica/{id}")
	@PreAuthorize("hasPermission('" + Recursos.EFETUAR_LOGISTICA + "')")
	public ResponseEntity<String> salvarLogisticaDoadorWorkup(
			@PathVariable(name="id", required = true) Long id,
			@RequestBody(required = true) LogisticaDoadorWorkupDTO logistica) throws Exception {
		
		pedidoLogisticaDoadorWorkupService.atualizarLogisticaDoadorWorkup(id, logistica);
		
		return ResponseEntity.ok(AppUtil.getMensagem(messageSource, "pedido.logistica.atualizada.sucesso"));
	}
	
	/**
	 * Método de gravação de logística.
	 * 
	 * @param pedidoLogisticaId - id da logistica a ser atualizada
	 * @param logistica - objeto a ser persistido.
	 * @return CampoMensagem contendo retorno da gravação.
	 * @throws Exception - caso haja erro na gravação de logística.
	 */
	@PostMapping(value = "/pedidologistica/{id}")
	@PreAuthorize("hasPermission('" + Recursos.EFETUAR_LOGISTICA + "')")
	public ResponseEntity<String> encerrarLogisticaDoadorWorkup(
			@PathVariable(name="id", required = true) Long id,
			@RequestBody(required = true) LogisticaDoadorWorkupDTO logistica) throws Exception {
		
		pedidoLogisticaDoadorWorkupService.encerrarLogisticaDoadorWorkup(id, logistica);
		
		return ResponseEntity.ok(AppUtil.getMensagem(messageSource, "pedido.logistica.atualizada.sucesso"));
	}

	/**
	 * Obtém dados envolvendo o pedido de logística informado.
	 * 
	 * @param pedidoId identificador do pedido de logística.
	 * @return DTO contendo as informações da logística do workup.
	 */
	@GetMapping(value = "/pedidologistica/{id}")
	@PreAuthorize("hasPermission('" + Recursos.EFETUAR_LOGISTICA + "')")
	public ResponseEntity<LogisticaDoadorWorkupDTO> obterPedidoLogisticaCustomizado(
			@PathVariable(required = true) Long id) {

		return ResponseEntity.ok(this.pedidoLogisticaDoadorWorkupService.obterPedidoLogisticaCustomizado(id));
	}
	
	/* ######################################################################################## */
	/* ########################### LOGISTICA MATERIAL INTERNACIONAL ########################### */
	
	/**
	 * Obtém dados envolvendo pedido de logística de material. 
	 * É retornado um DTO de material populado com os dados necessários para a tela.
	 * 
	 * @param idPedidoLogistica - identificador do pedido de logística.
	 * @return DTO contendo as informações da logística do material.
	 */
	@GetMapping(value = "/pedidologistica/{idPedidoLogistica}/material/internacional")
	@PreAuthorize("hasPermission('" + Recursos.EFETUAR_LOGISTICA_INTERNACIONAL + "') ")
	public ResponseEntity<DetalheLogisticaInternacionalColetaDTO> obterPedidoLogisticaMaterialColetaInternacional(
			@PathVariable(required = true) Long idPedidoLogistica) {
		
		return ResponseEntity.ok(pedidoLogisticaMaterialColetaInternacionalService.obterPedidoLogisticaMaterialColetaInternacional(idPedidoLogistica));
	}

	
	/**
	 * Salvar logistica de coleta internacional.
	 * 
	 * @param pedidoLogisticaId - id da logistica a ser gravada.
	 * @param detalheMaterial - detalhamento a ser persistido.
	 * @return Mensagem de OK, caso não ocorram erros.
	 */
	@PostMapping(value = "/pedidologistica/{idPedidoLogistica}/coleta/internacional")
	@PreAuthorize("hasPermission('" + Recursos.EFETUAR_LOGISTICA_INTERNACIONAL + "') ")
	public ResponseEntity<String> salvarPedidoLogisticaMaterialColetaInternacional(
			@PathVariable(required = true) Long idPedidoLogistica, 
			@RequestBody(required = true) DetalheLogisticaInternacionalColetaDTO detalhe) {
		pedidoLogisticaMaterialColetaInternacionalService.salvarPedidoLogisticaMaterialColetaInternacional(idPedidoLogistica, detalhe);
		return ResponseEntity.ok(AppUtil.getMensagem(messageSource, "pedido.logistica.coleta.internacional.salvo_sucesso"));
	}

	/**
	 * Finaliza a logística de material internacional.
	 * 
	 * @param pedidoLogisticaId - id da logistica a ser finalizada.
	 * 
	 * @return Mensagem de OK, caso não ocorram erros.
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "/pedidologistica/{idPedidoLogistica}/coleta/internacional/finalizar", method = RequestMethod.POST)
	@PreAuthorize("hasPermission('" + Recursos.EFETUAR_LOGISTICA_INTERNACIONAL + "') ")
	public ResponseEntity<String> finalizarPedidoLogisticaMaterialColetaInternacional(
			@PathVariable(required = true) Long idPedidoLogistica) throws JsonProcessingException {
		pedidoLogisticaMaterialColetaInternacionalService.finalizarPedidoLogisticaMaterialColetaInternacional(idPedidoLogistica);
		return ResponseEntity.ok(AppUtil.getMensagem(messageSource, "logistica.finalizada.mensagem.sucesso"));
	}
	
	/* ################################################################################### */
	/* ########################### LOGISTICA MATERIAL NACIONAL ########################### */
	
	@GetMapping(value = "/pedidologistica/{id}/material/dadosparatransportadora")
	@PreAuthorize("hasPermission('" + Recursos.AGENDAR_TRANSPORTE_MATERIAL + "')")
	public ResponseEntity<LogisticaMaterialTransporteDTO> obterLogisticaMaterialParaTransportadora(
			@PathVariable(required = true, name = "id") Long id) {
		
		return ResponseEntity.ok(pedidoLogisticaMaterialColetaNacionalService.obterPedidoLogisticaMaterialParaTransportadora(id));
	}
	

	/**
	 * Lista todas as tarefas de pedido de logística (workup e coleta).
	 * 
	 * @param pagina qual a página a ser exibida na paginação.
	 * @param quantidadeRegistros a quantidade de registros que serão exibidos por página.
	 * 
	 * @return lista de tarefas de pedido de logística.
	 * @throws JsonProcessingException 
	 */
	@GetMapping(value = "/pedidologistica/tarefas")
	@PreAuthorize("hasPermission('" + Recursos.EFETUAR_LOGISTICA + "')")
	public ResponseEntity<Page<TarefaLogisticaMaterialDTO>> listarTarefasPedidoLogisticaMaterialColetaNacionalPaginadas(
			@RequestParam(required = true) int pagina,
			@RequestParam(required = true) int quantidadeRegistros) throws JsonProcessingException {
		
		return ResponseEntity.ok(pedidoLogisticaMaterialColetaNacionalService.listarTarefasPedidoLogisticaMaterialColetaNacional(pagina, quantidadeRegistros));
	}

	
	/**
	 * Obtém dados envolvendo pedido de logística de material. É retornado um DTO de material populado com os dados necessários 
	 * para a tela.
	 * @param pedidoId - identificador do pedido de logística.
	 * @return DTO contendo as informações da logística do material.
	 */
	@GetMapping(value = "/pedidologistica/{pedidoId}/material/nacional")
	@PreAuthorize("hasPermission('" + Recursos.SOLICITAR_LOGISTICA_MATERIAL + "')")
	public ResponseEntity<DetalheLogisticaMaterialDTO> obterPedidoLogisticaMaterialColetaNacional(
			@PathVariable(required = true) Long pedidoId) {

		return ResponseEntity.ok(pedidoLogisticaMaterialColetaNacionalService.obterPedidoLogisticaMaterialColetaNacional(pedidoId));
	}

	/**
	 * Obtém dados envolvendo pedido de logística de material. É retornado um DTO de material populado com os dados necessários 
	 * para a tela.
	 * @param pedidoId - identificador do pedido de logística.
	 * @return DTO contendo as informações da logística do material.
	 */
	@GetMapping(value = "/pedidologistica/{pedidoId}/material/aereo")
	@PreAuthorize("hasPermission('" + Recursos.SOLICITAR_LOGISTICA_MATERIAL + "')")
	public ResponseEntity<DetalheLogisticaMaterialDTO> obterPedidoLogisticaMaterialColetaAereo(
			@PathVariable(required = true) Long pedidoId) {

		return ResponseEntity.ok(pedidoLogisticaMaterialColetaNacionalService.obterPedidoLogisticaMaterialColetaAerea(pedidoId));
	}
	
	
	/**
	 * Salva a logística de material nacional.
	 * 
	 * @param pedidoLogisticaId - id da logistica a ser gravada.
	 * @param detalheMaterial - detalhamento a ser persistido.
	 * @return Mensagem de OK, caso não ocorram erros.
	 */
	@PostMapping(value = "/pedidologistica/{pedidoId}/material/nacional/salvar")
	@PreAuthorize("hasPermission('" + Recursos.SOLICITAR_LOGISTICA_MATERIAL + "') ")
	public ResponseEntity<String> salvarPedidoLogisticaMaterialColetaNacional(
			@PathVariable(required = true) Long pedidoId, 
			@RequestBody(required = true) DetalheLogisticaMaterialDTO detalheMaterial) {

		pedidoLogisticaMaterialColetaNacionalService.salvarPedidoLogisticaMaterialColetaNacional(pedidoId, detalheMaterial);

		return ResponseEntity.ok(AppUtil.getMensagem(messageSource, "pedido.logistica.coleta.nacional.salvo_sucesso"));
	}

	
	/**
	 * Finaliza a logística de material nacional.
	 * Após este processo, é gerado um pedido de transporte que será tratado 
	 * pela transportador escolhida.
	 * 
	 * @param pedidoLogisticaId - id da logistica a ser gravada.
	 * @param detalheMaterial - detalhamento a ser persistido.
	 * @return Mensagem de OK, caso não ocorram erros.
	 * @throws JsonProcessingException 
	 */
	@PostMapping(value = "/pedidologistica/{pedidoId}/material/nacional/finalizar")
	@PreAuthorize("hasPermission('" + Recursos.SOLICITAR_LOGISTICA_MATERIAL + "')")
	public ResponseEntity<String> finalizarLogisticaMaterialNacional(
			@PathVariable(required = true) Long pedidoId, 
			@RequestBody(required = true) DetalheLogisticaMaterialDTO detalheMaterial) throws JsonProcessingException {
		
		pedidoLogisticaMaterialColetaNacionalService.finalizarPedidoLogisticaMaterialColetaNacional(pedidoId, detalheMaterial);

		return ResponseEntity.ok(AppUtil.getMensagem(messageSource, "logistica.finalizada.mensagem.sucesso"));
	}
	
	/**
	 * Lista todas as logisticas de transporte que não tem o status de AGUARDANDO_DOCUMENTACAO
	 * para o acompanhamento do analista.
	 * 
	 * @param pagina qual a página a ser exibida na paginação.
	 * @param quantidadeRegistros a quantidade de registros que serão exibidos por página.
	 * 
	 * @return lista de logisticas de pedido de logística.
	 */
//	@GetMapping(value = "transportes")
//	public ResponseEntity<Page<PedidoLogistica>> listarPedidosLogisticaPorTransporte(
//			@RequestParam(required = true) int pagina,
//			@RequestParam(required = true) int quantidadeRegistros) {
//				
//		return ResponseEntity.ok(pedidoLogisticaMaterialColetaNacionalService.listarTarefasLogisticaTransporteEmAndamento(pagina, quantidadeRegistros));
//	}
	
	/* ############################################################################### */
	/* ########################### LOGISTICA DOADOR COLETA ########################### */
	
	/**
	 * Obtém dados envolvendo o pedido de logística informado.
	 * 
	 * @param pedidoId identificador do pedido de logística.
	 * @return DTO contendo as informações da logística do workup.
	 */
	@GetMapping(value = "/pedidologistica/doador/{id}")
	@PreAuthorize("hasPermission('" + Recursos.EFETUAR_LOGISTICA_DOADOR_COLETA + "')")
	public ResponseEntity<LogisticaDoadorColetaDTO> obterPedidoLogisticaDoadorColetaCustomizado(
			@PathVariable(required = true) Long id) {

		return ResponseEntity.ok(this.pedidoLogisticaDoadorColetaService.obterPedidoLogisticaDoadorColetaCustomizado(id));
	}

	/**
	 * Método de gravação de logística.
	 * 
	 * @param pedidoLogisticaId - id da logistica a ser atualizada
	 * @param logistica - objeto a ser persistido.
	 * @return CampoMensagem contendo retorno da gravação.
	 * @throws Exception - caso haja erro na gravação de logística.
	 */
	@PutMapping(value = "/pedidologistica/doador/{id}")
	@PreAuthorize("hasPermission('" + Recursos.EFETUAR_LOGISTICA_DOADOR_COLETA + "')")
	public ResponseEntity<String> salvarLogisticaDoadorColeta(
			@PathVariable(name="id", required = true) Long id,
			@RequestBody(required = true) LogisticaDoadorColetaDTO logistica) throws Exception {
		
		pedidoLogisticaDoadorColetaService.atualizarLogisticaDoadorColeta(id, logistica);
		
		return ResponseEntity.ok(AppUtil.getMensagem(messageSource, "pedido.logistica.atualizada.sucesso"));
	}
	
	/**
	 * Método de gravação de logística.
	 * 
	 * @param pedidoLogisticaId - id da logistica a ser atualizada
	 * @param logistica - objeto a ser persistido.
	 * @return CampoMensagem contendo retorno da gravação.
	 * @throws Exception - caso haja erro na gravação de logística.
	 */
	@PostMapping(value = "/pedidologistica/doador/{id}")
	@PreAuthorize("hasPermission('" + Recursos.EFETUAR_LOGISTICA_DOADOR_COLETA + "')")
	public ResponseEntity<String> encerrarLogisticaDoadorColeta(
			@PathVariable(name="id", required = true) Long id,
			@RequestBody(required = true) LogisticaDoadorColetaDTO logistica) throws Exception {
		
		pedidoLogisticaDoadorColetaService.encerrarLogisticaDoadorColeta(id, logistica);
		
		return ResponseEntity.ok(AppUtil.getMensagem(messageSource, "pedido.logistica.atualizada.sucesso"));
	}


	/* ############################################################################### */
	/* ############### LOGISTICA DOADOR COLETA COM TRANSPORTE AÉREO ################## */


	@SuppressWarnings("rawtypes")
	@PatchMapping(value = "/pedidologistica/{id}/material/nacional/aereo")
	@PreAuthorize("hasPermission('" + Recursos.AGENDAR_TRANSPORTE_MATERIAL + "')")
	public ResponseEntity atualizarLogisticaMaterialParaAereo(@PathVariable(required = true) Long id) {
		
		pedidoLogisticaMaterialColetaNacionalService.atualizarLogisticaMaterialParaAereo(id);
		
		return ResponseEntity.ok().build();
	}

	
	
}

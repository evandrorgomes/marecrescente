package br.org.cancer.modred.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.dto.DetalheMaterialDTO;
import br.org.cancer.modred.controller.dto.LogisticaDTO;
import br.org.cancer.modred.controller.page.JsonPage;
import br.org.cancer.modred.controller.view.TarefaLogisticaView;
import br.org.cancer.modred.feign.dto.TarefaDTO;
import br.org.cancer.modred.helper.JsonViewPage;
import br.org.cancer.modred.model.PedidoLogistica;
import br.org.cancer.modred.service.IPedidoLogisticaService;
import br.org.cancer.modred.util.AppUtil;
import br.org.cancer.modred.util.CampoMensagem;

/**
 * Classe REST envolvendo a entidade pedido de logística.
 * 
 * @author Pizão
 *
 */
@RestController
@RequestMapping(value = "/api/pedidoslogistica", produces = "application/json;charset=UTF-8")
public class PedidoLogisticaController {

	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private IPedidoLogisticaService pedidoLogisticaService;

	/**
	 * Obtém dados envolvendo o pedido de logística informado. Funcionalidade utilizada para detalhar os dados de agendamento do
	 * workup que possui necessidade de logística, onde o usuário poderá informar dados de logística para o doador.
	 * 
	 * @param pedidoId identificador do pedido de logística.
	 * @return DTO contendo as informações da logística do workup.
	 */
	@RequestMapping(value = "{pedidoId}", method = RequestMethod.GET)
	public ResponseEntity<LogisticaDTO> obterLogisticaParaPedidoLogistica(
			@PathVariable(required = true) Long pedidoId) {

		LogisticaDTO logisticaDTO = pedidoLogisticaService.obterLogisticaDoador(pedidoId);
		return new ResponseEntity<LogisticaDTO>(logisticaDTO, HttpStatus.OK);
	}
	
	/**
	 * Obtém dados envolvendo pedido de logística de material. É retornado um DTO de material populado com os dados necessários 
	 * para a tela.
	 * @param pedidoId - identificador do pedido de logística.
	 * @return DTO contendo as informações da logística do material.
	 */
	@RequestMapping(value = "{pedidoId}/material", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('SOLICITAR_LOGISTICA_MATERIAL') || "
			+ "hasPermission('EFETUAR_LOGISTICA_INTERNACIONAL')")
	public ResponseEntity<DetalheMaterialDTO> obterLogisticaDeMaterial(
			@PathVariable(required = true) Long pedidoId) {
		DetalheMaterialDTO logiscaDeMaterial = pedidoLogisticaService.obterPedidoLogisticaMaterial(pedidoId);
		return new ResponseEntity<DetalheMaterialDTO>(logiscaDeMaterial, HttpStatus.OK);
	}

	@RequestMapping(value = "{id}/voucher", method = RequestMethod.POST, consumes = "multipart/form-data")
	@PreAuthorize("hasPermission('EFETUAR_LOGISTICA')")
	public ResponseEntity<String> salvarVoucher(
			@PathVariable(required = true) Long id,
			@RequestPart(name = "file", required = true) MultipartFile file) throws Exception {
		
		return new ResponseEntity<String>(pedidoLogisticaService.adicionarVoucher(id, file), HttpStatus.OK);
	}
	
	/**
	 * Método de gravação de logística.
	 * 
	 * @param pedidoLogisticaId - id da logistica a ser atualizada
	 * @param logistica - objeto a ser persistido.
	 * @return CampoMensagem contendo retorno da gravação.
	 * @throws Exception - caso haja erro na gravação de logística.
	 */
	@RequestMapping(value = "{id}", method = RequestMethod.POST)
	@PreAuthorize("hasPermission('EFETUAR_LOGISTICA')")
	public ResponseEntity<CampoMensagem> salvarLogistica(
			@PathVariable(name="id", required = true) Long pedidoLogisticaId,
			@RequestBody(required = true) LogisticaDTO logistica) throws Exception {
		
		pedidoLogisticaService.salvar(logistica, null);
		
		return new ResponseEntity<CampoMensagem>(
				new CampoMensagem(AppUtil.getMensagem(messageSource, "logistica.atualizada.mensagem.sucesso")), HttpStatus.OK);
	}
	
	
	/**
	 * Método de gravação e finalização da tarefa de logística.
	 * 
	 * @param pedidoLogisticaId - id da logistica a ser atualizada
	 * @param logistica - objeto a ser persistido.
	 * @return CampoMensagem contendo retorno da gravação.
	 * @throws Exception - caso haja erro na gravação de logística.
	 */
	@RequestMapping(value = "finalizar/{id}", method = RequestMethod.POST)
	@PreAuthorize("hasPermission('EFETUAR_LOGISTICA')")
	public ResponseEntity<CampoMensagem> finalizarLogistica(
			@PathVariable(name="id", required = true) Long pedidoLogisticaId,
			@RequestBody(required = true) LogisticaDTO logistica) throws Exception {
		
		pedidoLogisticaService.finalizarLogistica(logistica);
		return new ResponseEntity<CampoMensagem>(
				new CampoMensagem(AppUtil.getMensagem(messageSource, "pedido.transporte.agendado.sucesso")), HttpStatus.OK);
	}
	
	/**
	 * Atribui a tarefa para o usuário logado.
	 * 
	 * @param tarefaId tarefa a ser atribuída.
	 * @return Mensagem de OK, caso não ocorram erros.
	 */
	@RequestMapping(value = "atribuir/{tarefaId}", method = RequestMethod.PUT)
	@PreAuthorize("hasPermission('EFETUAR_LOGISTICA')|| "
			+ " hasPermission('EFETUAR_LOGISTICA_INTERNACIONAL')")
	public ResponseEntity<String> atribuirTarefaLogistica(@PathVariable(name="tarefaId", required = true) Long tarefaId) {
		pedidoLogisticaService.atribuirTarefa(tarefaId);
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	/**
	 * Lista todas as tarefas de pedido de logística (workup e coleta).
	 * 
	 * @param pagina qual a página a ser exibida na paginação.
	 * @param quantidadeRegistros a quantidade de registros que serão exibidos por página.
	 * 
	 * @return lista de tarefas de pedido de logística.
	 */
	@RequestMapping(value = "tarefas", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('EFETUAR_LOGISTICA')")
	public JsonViewPage<TarefaDTO> listarPedidosLogistica(
			@RequestParam(required = true) int pagina,
			@RequestParam(required = true) int quantidadeRegistros) {
		JsonViewPage<TarefaDTO> tarefasPedidoLogistica = 
				pedidoLogisticaService.listarTarefasPedidoLogistica(new PageRequest(pagina, quantidadeRegistros));
		return tarefasPedidoLogistica;
	}
	
	
	
	/**
	 * Lista todas as tarefas de pedido de logística internacional.
	 * 
	 * @param pagina qual a página a ser exibida na paginação.
	 * @param quantidadeRegistros a quantidade de registros que serão exibidos por página.
	 * 
	 * @return lista de tarefas de pedido de logística.
	 */
	@RequestMapping(value = "tarefas/internacional", method = RequestMethod.GET)
	public JsonViewPage<TarefaDTO> listarPedidosLogisticaMaterialInternacional(
			@RequestParam(required = true) int pagina,
			@RequestParam(required = true) int quantidadeRegistros) {
		JsonViewPage<TarefaDTO> tarefasPedidoLogistica = 
				pedidoLogisticaService.listarTarefasPedidoLogisticaInternacional(new PageRequest(pagina, quantidadeRegistros));
		return tarefasPedidoLogistica;
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
	@RequestMapping(value = "transportes", method = RequestMethod.GET)
	@JsonView(TarefaLogisticaView.Listar.class)
	public ResponseEntity<JsonPage> listarPedidosLogisticaTransporte(
			@RequestParam(required = true) int pagina,
			@RequestParam(required = true) int quantidadeRegistros) {
		Page<PedidoLogistica> tarefasPedidoLogistica = 
				pedidoLogisticaService.listarLogisticaTransporteEmAndamento(new PageRequest(pagina, quantidadeRegistros));
		return new ResponseEntity<JsonPage>(new JsonPage(TarefaLogisticaView.Listar.class, tarefasPedidoLogistica), HttpStatus.OK);
	}
	
	/**
	 * Salvar logistica de material internacional.
	 * 
	 * @param pedidoLogisticaId - id da logistica a ser gravada.
	 * @param detalheMaterial - detalhamento a ser persistido.
	 * @return Mensagem de OK, caso não ocorram erros.
	 */
	@RequestMapping(value = "{pedidoLogisticaId}/material/internacional", method = RequestMethod.POST)
	@PreAuthorize("hasPermission('EFETUAR_LOGISTICA_INTERNACIONAL')")
	public ResponseEntity<CampoMensagem> salvarLogisticaMaterialInternacional(@PathVariable(required = true) Long pedidoLogisticaId, 
			@RequestBody(required = true) DetalheMaterialDTO detalheMaterial) {
		pedidoLogisticaService.salvarInformacoesMaterialInternacional(detalheMaterial);
		return new ResponseEntity<CampoMensagem>(
				new CampoMensagem(AppUtil.getMensagem(messageSource, "pedido.logistica.material.internacional.salvo_sucesso")), HttpStatus.OK);
	}
	
	/**
	 * Realiza o download do voucher para autorização de viagem da CNT.
	 * 
	 * @param idPedidoLogistica - id do pedido de logistica
	 * @param codigoRelatorio - Código do Relatório
	 * @param response response
	 * @throws IOException ao ler o arquivo
	 */
	@RequestMapping(value = "{id}/download", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('SOLICITAR_LOGISTICA_MATERIAL')")
	public void baixarDocumentos(
			@PathVariable(name = "id", required = true) Long idPedidoLogistica,
			@RequestParam(name = "relatorio", required = true) String codigoRelatorio,
			@RequestParam(name = "docxExtensaoRelatorio", required = true) boolean docxExtensaoRelatorio,
			HttpServletResponse response) throws IOException {
		
		File arquivo = pedidoLogisticaService.obterDocumento(idPedidoLogistica, codigoRelatorio, docxExtensaoRelatorio);

		ServletOutputStream stream = response.getOutputStream();

		response.setContentType(Files.probeContentType(arquivo.toPath()));
		response.addHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		response.addHeader("Content-Disposition",
				String.format("inline; filename=\"" + arquivo.getName() + "\""));
		FileCopyUtils.copy(new FileInputStream(arquivo), stream);
		response.flushBuffer();
	}

	/**
	 * Finaliza a logística de material nacional.
	 * Após este processo, é gerado um pedido de transporte que será tratado 
	 * pela transportador escolhida.
	 * 
	 * @param pedidoLogisticaId - id da logistica a ser gravada.
	 * @param detalheMaterial - detalhamento a ser persistido.
	 * @return Mensagem de OK, caso não ocorram erros.
	 */
	@RequestMapping(value = "{pedidoLogisticaId}/material/nacional/finalizar", method = RequestMethod.POST)
	@PreAuthorize("hasPermission('SOLICITAR_LOGISTICA_MATERIAL')")
	public ResponseEntity<CampoMensagem> finalizarLogisticaMaterialNacional(@PathVariable(required = true) Long pedidoLogisticaId, 
			@RequestBody(required = true) DetalheMaterialDTO detalheMaterial) {
		pedidoLogisticaService.finalizarLogisticaMaterialNacional(pedidoLogisticaId, detalheMaterial);
		return new ResponseEntity<CampoMensagem>(
				new CampoMensagem(AppUtil.getMensagem(messageSource, "pedido.transporte.agendado.sucesso")), HttpStatus.OK);
	}
	
	
	/**
	 * Salva a logística de material nacional.
	 * 
	 * @param pedidoLogisticaId - id da logistica a ser gravada.
	 * @param detalheMaterial - detalhamento a ser persistido.
	 * @return Mensagem de OK, caso não ocorram erros.
	 */
	@RequestMapping(value = "{pedidoLogisticaId}/material/nacional/salvar", method = RequestMethod.POST)
	@PreAuthorize("hasPermission('SOLICITAR_LOGISTICA_MATERIAL')")
	public ResponseEntity<CampoMensagem> salvarLogisticaMaterialNacional(@PathVariable(required = true) Long pedidoLogisticaId, 
			@RequestBody(required = true) DetalheMaterialDTO detalheMaterial) {
		pedidoLogisticaService.salvarLogisticaMaterialNacional(pedidoLogisticaId, detalheMaterial);
		return new ResponseEntity<CampoMensagem>(
				new CampoMensagem(AppUtil.getMensagem(messageSource, "logistica.atualizada.mensagem.sucesso")), HttpStatus.OK);
	}
	

	
	/**
	 * Finaliza a logística de material internacional.
	 * 
	 * @param pedidoLogisticaId - id da logistica a ser finalizada.
	 * 
	 * @return Mensagem de OK, caso não ocorram erros.
	 */
	@RequestMapping(value = "{pedidoLogisticaId}/material/internacional/finalizar", method = RequestMethod.POST)
	@PreAuthorize("hasPermission('EFETUAR_LOGISTICA_INTERNACIONAL')")
	public ResponseEntity<CampoMensagem> finalizarLogisticaMaterialInternacional(@PathVariable(required = true) Long pedidoLogisticaId) {
		pedidoLogisticaService.finalizarLogisticaMaterialInternacional(pedidoLogisticaId);
		return new ResponseEntity<CampoMensagem>(
				new CampoMensagem(AppUtil.getMensagem(messageSource, "logistica.finalizada.mensagem.sucesso")), HttpStatus.OK);
	}
	
	/**
	 * Realiza o download do voucher para autorização de viagem da CNT.
	 * 
	 * @param idPedidoLogistica - id do pedido de logistica
	 * @param response response
	 * @throws IOException ao ler o arquivo
	 */
	@RequestMapping(value = "{id}/autorizacaopaciente/download", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('EFETUAR_LOGISTICA_INTERNACIONAL')")
	public void obterAutorizacaoPacienteAssociadoLogistica(
			@PathVariable(name = "id", required = true) Long idPedidoLogistica, 
			HttpServletResponse response) throws IOException {
		
		File arquivo = pedidoLogisticaService.obterAutorizacaoPaciente(idPedidoLogistica);

		ServletOutputStream stream = response.getOutputStream();

		response.setContentType(Files.probeContentType(arquivo.toPath()));
		response.addHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		response.addHeader("Content-Disposition",
				String.format("inline; filename=\"" + arquivo.getName() + "\""));
		FileCopyUtils.copy(new FileInputStream(arquivo), stream);
		response.flushBuffer();
	}

	/**
	 Altera a logistica de material quando esta for aérea. Através deste método é possível fechar a tarefa do courier atual,
	 * abrir uma nova tarefa com uma nova carta e uma nova data.
	 * 
	 * @param idPedidoLogistica - identificacao do pedido de logística.
	 * @param tipoAlteracao - utilize o enum de TipoAlteracaoPedidoLogistica.
	 * @param cartaCnt - carta cnt para ser enviada para o courier com novos dados da viajem.
	 * @param descricao - descrição explicando o porquê da alteração.
	 * @param data - nova data para o translado do courier.
	 * @param rmr - identificação do doador.
	 * @throws Exception Se ocorrer algum erro
	 * @return caso grave com sucesso uma mensagem de que foi tudo ok.
	 */
	@PostMapping(value = "{id}/material/aereo",consumes = "multipart/form-data")
	@PreAuthorize("hasPermission('SOLICITAR_LOGISTICA_MATERIAL') ")
	public  ResponseEntity<CampoMensagem> alterarLogisticaMaterialAereo(
			@PathVariable(name = "id", required = true) Long idPedidoLogistica
			, @RequestPart(name = "tipo", required = true) Long tipoAlteracao
			, @RequestPart(name = "file", required = false) MultipartFile cartaCnt
			, @RequestPart(name = "descricao", required = false) String descricao
			, @RequestPart(name = "data", required = false) LocalDate data
			, @RequestPart(name = "rmr", required = true) Long rmr
			)throws Exception {

		pedidoLogisticaService.alterarLogisticaMaterialAereo(idPedidoLogistica, tipoAlteracao, cartaCnt, descricao, data, rmr);
		return ResponseEntity.ok().body(new CampoMensagem(AppUtil.getMensagem(messageSource,"pedido.logistica.material.internacional.salvo_sucesso")));
	}
	
	
}

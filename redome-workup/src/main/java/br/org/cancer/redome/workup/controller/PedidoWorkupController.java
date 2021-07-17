package br.org.cancer.redome.workup.controller;

import com.fasterxml.jackson.core.JsonProcessingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.org.cancer.redome.workup.dto.AprovarPlanoWorkupDTO;
import br.org.cancer.redome.workup.dto.ContagemCelulaDTO;
import br.org.cancer.redome.workup.dto.PedidoWorkupDTO;
import br.org.cancer.redome.workup.dto.PlanoWorkupInternacionalDTO;
import br.org.cancer.redome.workup.dto.PlanoWorkupNacionalDTO;
import br.org.cancer.redome.workup.model.domain.Recursos;
import br.org.cancer.redome.workup.service.IContagemCelulaService;
import br.org.cancer.redome.workup.service.IFormularioPosColetaService;
import br.org.cancer.redome.workup.service.IPedidoWorkupService;
import br.org.cancer.redome.workup.util.AppUtil;

@RestController
@RequestMapping(value = "/api", produces = "application/json;charset=UTF-8")
public class PedidoWorkupController {
	
	@Autowired
	private IPedidoWorkupService pedidoWorkupService;
	
	@Autowired
	private IFormularioPosColetaService formularioPosColetaService;
	
	@Autowired
	private IContagemCelulaService contagemCelulaService;

	@Autowired
	private MessageSource messageSource;

	/**
	 * Método para finalizar pedido de workup.
	 * 
	 * @param idPedido - identificador do pedido
	 * @return CampoMensagem - mensagem de sucesso
	 * @throws JsonProcessingException 
	 */
	@PostMapping(value="/pedidoworkup/{idPedidoWorkup}/centrocoleta")
	@PreAuthorize("hasPermission('TRATAR_PEDIDO_WORKUP')")
	public ResponseEntity<String> definirCentroColetaPorPedidoWorkup(
			@PathVariable(required = true) Long idPedidoWorkup,
			@RequestBody(required = true) Long idCentroColeta) throws JsonProcessingException {

		this.pedidoWorkupService.DefinirCentroColetaPorPedidoWorkup(idPedidoWorkup, idCentroColeta);
		return ResponseEntity.ok(AppUtil.getMensagem(messageSource, "centro.coleta.workup.definido.sucesso"));
	}		
	
	/**
	 * Método para obter pedido de workup.
	 * 
	 * @param idPedido - identificador do pedido de workup.
	 * @return PedidoWorkup - pedido do workup.
	 */
	@GetMapping(value="/pedidoworkup/{idPedido}")
	@PreAuthorize("hasPermission('" + Recursos.TRATAR_PEDIDO_WORKUP + "') "
			+ " || hasPermission('" + Recursos.TRATAR_PEDIDO_WORKUP_INTERNACIONAL + "') "
			+ " || hasPermission('" + Recursos.CONSULTAR_PEDIDO_WORKUP_NACIONAL + "') "
			+ " || hasPermission('" + Recursos.APROVAR_PLANO_WORKUP + "') "
			+ " || hasPermission('" + Recursos.EFETUAR_LOGISTICA + "') "
			+ " || hasPermission('" + Recursos.AVALIAR_PEDIDO_COLETA + "') "
			+ " || hasPermission('" + Recursos.FINALIZAR_FORMULARIO_POSCOLETA + "') ") 
	public ResponseEntity<PedidoWorkupDTO> obterPedidoWorkup(
			@PathVariable(required = true) Long idPedido) {

		return new ResponseEntity<PedidoWorkupDTO>(pedidoWorkupService.obterPedidoWorkupDTO(idPedido), HttpStatus.OK);
	}
	
	@PostMapping(value="/pedidoworkup/{id}/planoworkupnacional", consumes = "multipart/form-data")
	@PreAuthorize("hasPermission('" + Recursos.INFORMAR_PLANO_WORKUP_NACIONAL + "')")
	public ResponseEntity<String> informarPlanoDeWorkupNacional(
			@PathVariable(required = true, name = "id") Long idPedidoWorkup,
			@RequestPart(required = true) PlanoWorkupNacionalDTO planoWorkup,
			@RequestPart(name = "file", required = false) MultipartFile arquivo) throws JsonProcessingException {

		this.pedidoWorkupService.informarPlanoWorkupNacional(idPedidoWorkup, planoWorkup, arquivo);
		return ResponseEntity.ok(AppUtil.getMensagem(messageSource, "plano.workup.informado.sucesso"));
	}
	
	@PostMapping(value="/pedidoworkup/{id}/planoworkupinternacional", consumes = "multipart/form-data")
	@PreAuthorize("hasPermission('" + Recursos.INFORMAR_PLANO_WORKUP_INTERNACIONAL + "')")
	public ResponseEntity<String> informarPlanoDeWorkupInternacional(
			@PathVariable(required = true, name = "id") Long idPedidoWorkup,
			@RequestPart(required = true) PlanoWorkupInternacionalDTO planoWorkup,
			@RequestPart(name = "file", required = true) MultipartFile arquivo) throws JsonProcessingException {

		this.pedidoWorkupService.informarPlanoWorkupInternacional(idPedidoWorkup, planoWorkup, arquivo);
		return ResponseEntity.ok(AppUtil.getMensagem(messageSource, "plano.workup.informado.sucesso"));
	}
	

	@PostMapping(value="/pedidoworkup/{id}/aprovacaoplanoworkup")
	@PreAuthorize("hasPermission('" + Recursos.APROVAR_PLANO_WORKUP + "')")
	public ResponseEntity<String> aprovarPlanoDeWorkup(
			@PathVariable(required = true, name = "id") Long idPedidoWorkup,
			@RequestBody(required = true) AprovarPlanoWorkupDTO planoWorkup) throws JsonProcessingException {

		this.pedidoWorkupService.aprovarPlanoWorkup(idPedidoWorkup, planoWorkup);
		return ResponseEntity.ok(AppUtil.getMensagem(messageSource, "plano.workup.aprovado.sucesso"));
	}

	@GetMapping(value="/pedidoworkup/{id}/planoworkupnacional")
	@PreAuthorize("hasPermission('" + Recursos.EFETUAR_LOGISTICA + "')")
	public ResponseEntity<PlanoWorkupNacionalDTO> obterPlanoWorkupNacional(
			@PathVariable(required = true, name = "id") Long idPedidoWorkup){
	
		return ResponseEntity.ok(this.pedidoWorkupService.obterPlanoWorkupNacional(idPedidoWorkup));
	}
	
	@PostMapping(value="/pedidoworkup/{idPedido}/finalizarformularioposcoleta")
	@PreAuthorize("hasPermission('" + Recursos.FINALIZAR_FORMULARIO_POSCOLETA + "')")
	public ResponseEntity<String> finalizarFormularioPosColeta (
			@PathVariable(required = true, name = "idPedido") Long idPedidoWorkup) throws JsonProcessingException {
	
		this.formularioPosColetaService.finalizarFormularioPosColeta(idPedidoWorkup);
		return ResponseEntity.ok(AppUtil.getMensagem(messageSource, "poscoleta.workup.finalizado.sucesso"));

	}	

	
	
	@GetMapping(value="/pedidoworkup/{id}/obtendocontagemcelula")
	@PreAuthorize("hasPermission('" + Recursos.TRATAR_CONTAGEM_CELULA + "')")
	public ResponseEntity<ContagemCelulaDTO> obterContagemCelulaDto(
			@PathVariable(required = true, name = "id") Long idPedidoWorkup){
	
		return ResponseEntity.ok(this.contagemCelulaService.obterContagemCelulaDto(idPedidoWorkup));
	}
	
	@PostMapping(value="/pedidoworkup/{id}/salvandocontagemcelula")
	@PreAuthorize("hasPermission('" + Recursos.TRATAR_CONTAGEM_CELULA + "')")
	public ResponseEntity<String> salvarContagemCelula(
			@PathVariable(required = true, name = "id") Long idPedidoWorkup,
			@RequestBody(required = true) ContagemCelulaDTO contagemCelulaDTO) throws JsonProcessingException {

		this.contagemCelulaService.salvarContagemCelula(idPedidoWorkup, contagemCelulaDTO);
		return ResponseEntity.ok(AppUtil.getMensagem(messageSource, "contagem.celula.salvo.sucesso"));
	}
	
	@PostMapping(value="/pedidoworkup/{id}/finalizandocontagemcelula")
	@PreAuthorize("hasPermission('" + Recursos.TRATAR_CONTAGEM_CELULA + "')")
	public ResponseEntity<String> finalizarContagemCelula(
			@PathVariable(required = true, name = "id") Long idPedidoWorkup,
			@RequestBody(required = true) ContagemCelulaDTO contagemCelulaDTO) throws JsonProcessingException {

		this.contagemCelulaService.finalizarContagemCelula(idPedidoWorkup, contagemCelulaDTO);
		return ResponseEntity.ok(AppUtil.getMensagem(messageSource, "contagem.celula.finalizado.sucesso"));
	}

	
}

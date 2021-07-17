package br.org.cancer.modred.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.dto.ResultadoWorkupNacionalDTO;
import br.org.cancer.modred.controller.view.FormularioView;
import br.org.cancer.modred.model.Formulario;
import br.org.cancer.modred.model.domain.TiposFormulario;
import br.org.cancer.modred.model.security.Recurso;
import br.org.cancer.modred.service.IFormularioService;
import br.org.cancer.modred.util.AppUtil;
import br.org.cancer.modred.util.CampoMensagem;

/**
 * Classe de REST controller para motivo de manipulação de formulários.
 * 
 * @author ergomes
 *
 */
@RestController
@RequestMapping(value = "/api/formularios", produces = "application/json;charset=UTF-8")
public class FormularioController {

	@Autowired
	private IFormularioService formularioService;
	
	@Autowired
	private MessageSource messageSource;

	@PostMapping(value = "{id}/finalizarformulariopedidoworkup")
	@PreAuthorize("hasPermission('" + Recurso.FINALIZAR_FORMULARIO_PEDIDO_WORKUP + "')")
	public ResponseEntity<Formulario> finalizarFormularioPedidoWorkup(
			@PathVariable(required = true) Long id,
			@RequestBody(required = true) Formulario formulario) {

		return ResponseEntity.ok(formularioService.finalizarFormularioComPedidoWorkup(id, formulario));
	}
	
	@PostMapping(value = "{id}/finalizarformularioresultadoworkup", consumes = "multipart/form-data")
	@PreAuthorize("hasPermission('" + Recurso.FINALIZAR_RESULTADO_WORKUP_NACIONAL + "') ")
	public ResponseEntity<Formulario> finalizarFormularioResultadoWorkup(
			@PathVariable(required = true) Long id,
			@RequestPart(required = true) ResultadoWorkupNacionalDTO resultadoWorkupNacionalDTO,
			@RequestPart(required = true) Formulario formulario) {

		return ResponseEntity.ok(formularioService.finalizarFormularioResultadoWorkup(id, resultadoWorkupNacionalDTO, formulario));
	}
	
	@PostMapping(value = "{id}/formulariopedidoworkup")
	@PreAuthorize("hasPermission('" + Recurso.FINALIZAR_RESULTADO_WORKUP_NACIONAL + "')"
			+ "|| hasPermission('" + Recurso.FINALIZAR_FORMULARIO_POSCOLETA +"')")
	public ResponseEntity<CampoMensagem> salvarFormularioComPedidoWorkup(
			@PathVariable(required = true) Long id,
			@RequestBody(required = true) Formulario formulario) {
			formularioService.salvarFormularioComPedidoWorkup(id, formulario);

		return new ResponseEntity<CampoMensagem>(new CampoMensagem(AppUtil.getMensagem(messageSource,
			"formulario.resultado.salvo")), HttpStatus.OK);
	}

	@PostMapping(value = "{id}/formulariopedidocontato")
	@PreAuthorize("hasPermission('" + Recurso.CONTACTAR_FASE_2 + "') "
			+ "|| hasPermission('" + Recurso.CONTACTAR_FASE_3 + "') "
			+ "|| hasPermission('" + Recurso.CADASTRAR_ANALISE_MEDICA_DOADOR +"')")	
	public ResponseEntity<CampoMensagem> salvarFormularioComPedidoContatoEValidacao(
			@RequestParam(required = true) Long id,
			@RequestBody(required = true) Formulario formulario) {
			formularioService.salvarFormularioComPedidoContato(id, formulario);

		return new ResponseEntity<CampoMensagem>(new CampoMensagem(AppUtil.getMensagem(messageSource,
			"formulario.resultado.salvo")), HttpStatus.OK);
	}
	
	@GetMapping(value = "{id}/formulario")
	@PreAuthorize("hasPermission('" + Recurso.CONSULTAR_FORMULARIO + "') "
			+ "|| hasPermission('" + Recurso.CONTACTAR_FASE_2 + "') "
			+ "|| hasPermission('" + Recurso.CONTACTAR_FASE_3 + "') "
			+ "|| hasPermission('" + Recurso.FINALIZAR_RESULTADO_WORKUP_NACIONAL + "') "
			+ "|| hasPermission('" + Recurso.AVALIAR_RESULTADO_WORKUP_NACIONAL + "') "
			+ "|| hasPermission('" + Recurso.CADASTRAR_ANALISE_MEDICA_DOADOR +"')")
	@JsonView(FormularioView.Formulario.class)
	public ResponseEntity<Formulario> obterFormulario(@PathVariable(required = true) Long id, 
			@RequestParam(required = true) Long tipo) {
		return ResponseEntity.ok(formularioService.obterFormulario(id, TiposFormulario.valueOf(tipo)));
	}
	
	@PostMapping(value = "{id}/finalizarformularioposcoleta")
	@PreAuthorize("hasPermission('" + Recurso.FINALIZAR_FORMULARIO_POSCOLETA + "') " )
	public ResponseEntity<Formulario> finalizarFormularioPosColeta(
			@PathVariable(required = true) Long id,		
			@RequestBody(required = true) Formulario formulario) {

		return ResponseEntity.ok(formularioService.finalizarFormularioPosColeta(id, formulario));
	}		
	
}

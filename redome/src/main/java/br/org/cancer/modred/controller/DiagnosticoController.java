package br.org.cancer.modred.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.org.cancer.modred.model.Diagnostico;
import br.org.cancer.modred.model.security.Recurso;
import br.org.cancer.modred.service.IDiagnosticoService;
import br.org.cancer.modred.util.AppUtil;
import br.org.cancer.modred.util.CampoMensagem;

/**
 * Constrolador para diagnostico.
 * 
 * @author brunosousa
 *
 */
@RestController
@RequestMapping(value = "/api/diagnosticos", produces = "application/json;charset=UTF-8")
public class DiagnosticoController {
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private IDiagnosticoService diagnosticoService;

	/**
	 * Método rest para alterar diagnostico.
	 *
	 * @param rmr identificador do diagnostico.
	 * @param diagnostico Diagnostico alterado.
	 * @return ResponseEntity<CampoMensagem> mensagem de sucesso.
	 */
	@RequestMapping(value="{rmr}", method = RequestMethod.POST)
	@PreAuthorize("hasPermission('" + Recurso.EDITAR_DIAGNOSTICO_PACIENTE + "')")
	public ResponseEntity<CampoMensagem> alterarDiagnostico(@PathVariable(required = true) Long rmr,
			@RequestBody(required = true) Diagnostico diagnostico) {
		
		diagnosticoService.alterarDiagnostico(rmr, diagnostico);
		
		final CampoMensagem mensagem = new CampoMensagem("",
				AppUtil.getMensagem(messageSource, "diagnostico.alterado.sucesso"));
		
		return new ResponseEntity<CampoMensagem>(mensagem, HttpStatus.OK);
	}
	
}

package br.org.cancer.modred.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.org.cancer.modred.model.security.Recurso;
import br.org.cancer.modred.service.impl.EmailContatoService;
import br.org.cancer.modred.util.AppUtil;

/**
 * Classe rest para Email de Contato.
 * @author Filipe Paes
 *
 */
@RestController
@RequestMapping(value = "/api/emailscontato", produces = "application/json;charset=UTF-8")
public class EmailContatoController {

	
	@Autowired
	private EmailContatoService emailContatoService;

	@Autowired
	private MessageSource messageSource;
	
	/**
	 * Método rest para excluir email de contato.
	 * 
	 * @param idEmailContato
	 *            - id do contato a ser excluido.
	 * @return ResponseEntity<CampoMensagem> Lista de erros.
	 */
	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	@PreAuthorize("hasPermission('" + Recurso.EXCLUIR_EMAIL_CONTATO + "') || "
			+ "hasPermission('" + Recurso.EXCLUIR_EMAIL_CONTATO_MEDICO + "')")

	public ResponseEntity<String> excluir(@PathVariable("id") Long idEmailContato) {
		emailContatoService.excluir(idEmailContato);
		final String mensagem = AppUtil.getMensagem(messageSource, "emailcontato.excluido.sucesso");
		return new ResponseEntity<String>(mensagem, HttpStatus.OK);
	}
}

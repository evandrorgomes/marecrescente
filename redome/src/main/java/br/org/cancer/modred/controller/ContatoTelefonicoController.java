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

import br.org.cancer.modred.model.ContatoTelefonico;
import br.org.cancer.modred.model.security.Recurso;
import br.org.cancer.modred.service.impl.ContatoTelefonicoService;
import br.org.cancer.modred.util.AppUtil;

/**
 * Classe para testar controlador rest de contato telefonico.
 * 
 * @author Filipe Paes
 *
 */
@RestController
@RequestMapping(value = "/api/contatostelefonicos", produces = "application/json;charset=UTF-8")
public class ContatoTelefonicoController {

	@Autowired
	private ContatoTelefonicoService contatoTelefonicoService;

	@Autowired
	private MessageSource messageSource;

	/**
	 * Método rest para excluir contato telefonico.
	 * 
	 * @param idContatoTelefonico
	 *            - id do contato a ser excluido.
	 * @return ResponseEntity<CampoMensagem> Lista de erros.
	 */
	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	@PreAuthorize("hasPermission('" + Recurso.EXCLUIR_TELEFONE_CONTATO + "') || "
			+ "hasPermission('" + Recurso.EXCLUIR_TELEFONE_CONTATO_CENTRO_TRANSPLANTE + "') || "
			+ "hasPermission('" + Recurso.EXCLUIR_TELEFONE_CONTATO_MEDICO + "') || "
			+ "hasPermission('" + Recurso.SALVAR_COURIER + "')")
	public ResponseEntity<String> excluir(@PathVariable("id") Long idContatoTelefonico) {
		contatoTelefonicoService.excluir(idContatoTelefonico);
		final String mensagem = AppUtil.getMensagem(messageSource, "contatotelefonico.excluido.sucesso");
		return new ResponseEntity<String>(mensagem, HttpStatus.OK);
	}
	
	/**
	 * Método rest para buscar contato telefonico por id.
	 * 
	 * @param idContatoTelefonico
	 *            - id do contato a ser excluido.
	 * @return ResponseEntity<ContatoTelefonico> contato telefonico localizado.
	 */
	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('ATUALIZAR_DADOS_PESSOAIS_DOADOR')")
	public ResponseEntity<ContatoTelefonico> buscarPorId(@PathVariable("id") Long idContatoTelefonico) {
		return new ResponseEntity<ContatoTelefonico>(contatoTelefonicoService.obterContatoTelefonico(idContatoTelefonico), HttpStatus.OK);
	}
	
}

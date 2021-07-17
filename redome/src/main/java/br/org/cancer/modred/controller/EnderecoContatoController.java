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

import br.org.cancer.modred.controller.abstracts.AbstractController;
import br.org.cancer.modred.model.EnderecoContato;
import br.org.cancer.modred.model.security.Recurso;
import br.org.cancer.modred.service.IEnderecoContatoService;
import br.org.cancer.modred.util.AppUtil;

/**
 * Classe rest para Endereco de Contato.
 * @author Filipe Paes
 *
 */
@RestController
@RequestMapping(value = "/api/enderecoscontatos", produces = "application/json;charset=UTF-8")
public class EnderecoContatoController extends AbstractController{

	
	@Autowired
	private IEnderecoContatoService<EnderecoContato> enderecoContatoService;

	@Autowired
	private MessageSource messageSource;
	
	/**
	 * Método rest para excluir endereco contato.
	 * 
	 * @param idEnderecoContato
	 *            - id do contato a ser excluido.
	 * @return ResponseEntity<CampoMensagem> Lista de erros.
	 */
	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	@PreAuthorize("hasPermission('" + Recurso.EXCLUIR_ENDERECO_CONTATO + "') || "
			+ "hasPermission('" + Recurso.EXCLUIR_ENDERECO_CONTATO_CENTRO_TRANSPLANTE + "')")
	public ResponseEntity<String> excluir(@PathVariable("id") Long idEnderecoContato) {
		enderecoContatoService.excluir(idEnderecoContato);
		final String mensagem = AppUtil.getMensagem(messageSource, "enderecocontato.excluido.sucesso");
		return new ResponseEntity<String>(mensagem, HttpStatus.OK);
	}

	
}

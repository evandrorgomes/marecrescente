package br.org.cancer.modred.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.org.cancer.modred.controller.abstracts.AbstractController;
import br.org.cancer.modred.model.EnderecoContatoMedico;
import br.org.cancer.modred.model.security.Recurso;
import br.org.cancer.modred.service.IEnderecoContatoService;
import br.org.cancer.modred.util.CampoMensagem;

/**
 * Classe rest para Endereco de Contato.
 * @author Filipe Paes
 *
 */
@RestController
@RequestMapping(value = "/api/enderecoscontatosmedico", produces = "application/json;charset=UTF-8")
public class EnderecoContatoMedicoController extends AbstractController {

	
	@Autowired
	private IEnderecoContatoService<EnderecoContatoMedico> enderecoContatoService;
	
	/**
	 * Método rest para atualizar o endereco de contato.
	 * 
	 * @param id - id do endereço de contato do centro de transplante a ser alterado.
	 * @param enderecoContato - Endereço de contato a ser atualizado
	 * @return ResponseEntity<CampoMensagem> Lista de erros.
	 */
	@RequestMapping(value = "{id}", method = RequestMethod.PUT)
	@PreAuthorize("hasPermission('" + Recurso.ALTERAR_CADASTRO_MEDICO + "')")
	public ResponseEntity<CampoMensagem> atualizar(@PathVariable("id") Long id,
			@RequestBody(required= true) EnderecoContatoMedico enderecoContato ) {
		
		enderecoContatoService.atualizar(id, enderecoContato);

		return statusOK("endereco.atualizado.com.sucesso");
	}
}

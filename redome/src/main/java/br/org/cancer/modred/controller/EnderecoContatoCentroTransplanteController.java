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

import br.org.cancer.modred.model.EnderecoContatoCentroTransplante;
import br.org.cancer.modred.service.IEnderecoContatoCentroTransplanteService;
import br.org.cancer.modred.util.AppUtil;
import br.org.cancer.modred.util.CampoMensagem;

/**
 * Classe rest para Endereco de Contato.
 * @author Filipe Paes
 *
 */
@RestController
@RequestMapping(value = "/api/enderecoscontatoscentrotransplante", produces = "application/json;charset=UTF-8")
public class EnderecoContatoCentroTransplanteController {

	
	@Autowired
	private IEnderecoContatoCentroTransplanteService enderecoContatoCentroTransplanteService;

	@Autowired
	private MessageSource messageSource;
	
	/**
	 * Método rest para atualizar o endereco de contato.
	 * 
	 * @param id - id do endereço de contato do centro de transplante a ser alterado.
	 * @param enderecoContato - Endereço de contato a ser atualizado
	 * @return ResponseEntity<CampoMensagem> Lista de erros.
	 */
	@RequestMapping(value = "{id}", method = RequestMethod.PUT)
	@PreAuthorize("hasPermission('MANTER_CENTRO_TRANSPLANTE')")
	public ResponseEntity<CampoMensagem> excluir(@PathVariable("id") Long id,
			@RequestBody(required= true) EnderecoContatoCentroTransplante enderecoContato ) {
		
		enderecoContatoCentroTransplanteService.atualizar(id, enderecoContato);
		final CampoMensagem campoMensagem = new CampoMensagem(
				AppUtil.getMensagem(messageSource, "centroTransplante.enderecocontato.atualizado.sucesso"));

		return new ResponseEntity<CampoMensagem>(campoMensagem, HttpStatus.OK);
	}
}

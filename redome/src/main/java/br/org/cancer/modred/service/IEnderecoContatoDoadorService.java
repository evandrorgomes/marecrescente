package br.org.cancer.modred.service;

import java.util.List;

import br.org.cancer.modred.model.EnderecoContatoDoador;
import br.org.cancer.modred.service.custom.IService;

/**
 * Interface para tratar parte de negocios de Endereco de Contato Doador.
 * 
 * @author Pizão
 *
 */
public interface IEnderecoContatoDoadorService extends IService<EnderecoContatoDoador, Long> {

	/**
	 * Lista os endereços para o doador informado.
	 * 
	 * @param idDoador identificação de doador.
	 * @return lista de endereços do doador.
	 */
	List<EnderecoContatoDoador> listarEnderecos(Long idDoador);
}

package br.org.cancer.modred.service;

import br.org.cancer.modred.model.EnderecoContato;

/**
 * Interface para tratar parte de negocios de Endererco de Contato.
 * @param <T> entidade que extende de endereço de contato.
 * 
 * @author Filipe Paes
 *
 */
public interface IEnderecoContatoService<T extends EnderecoContato> {

	/**
	 * Método para excluir endereço de contato por id.
	 * @param idEnderecoContato
	 */
	void excluir(Long idEnderecoContato);
	
	/**
	 * Atualiza os dados do endereço de contato
	 * a partir do novo objeto informado.
	 * 
	 * @param id identificador do endereço
	 * @param endereco endereço com as informações mais atualizadas.
	 */
	void atualizar(Long id, T endereco);
}

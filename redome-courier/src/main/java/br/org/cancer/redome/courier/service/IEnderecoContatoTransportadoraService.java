package br.org.cancer.redome.courier.service;

import br.org.cancer.redome.courier.model.EnderecoContatoTransportadora;
import br.org.cancer.redome.courier.service.custom.IService;

/**
 * Interface de negócios para EnderecoContatoTransportadora. 
 * @author Filipe Paes
 *
 */
public interface IEnderecoContatoTransportadoraService extends IService<EnderecoContatoTransportadora, Long> {

	/**
	 * Atualiza o endereco de contato de acordo com a transportadora.
	 * @param idEndereco identificação do endereco de contato.
	 * @param enderecoContato Endereco de contato a ser atualizado.
	 */
	void atualizarEnderecoContato(Long idEndereco, EnderecoContatoTransportadora enderecoContato);
}

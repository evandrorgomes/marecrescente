package br.org.cancer.redome.courier.service;

import br.org.cancer.redome.courier.model.ContatoTelefonicoTransportadora;
import br.org.cancer.redome.courier.service.custom.IService;

/**
 * Interface de classe de negocios de contato telefonico de transportadora.
 * @author Filipe Paes
 *
 */
public interface IContatoTelefonicoTransportadoraService extends IService<ContatoTelefonicoTransportadora,Long> {

	/**
	 * Método para atualizar registro de contato telefonico de transportadora.
	 * @param id identificação da transportadora.
	 * @param contato objeto a ser atualizado.
	 */
	void atualizar(Long id, ContatoTelefonicoTransportadora contato);
}

package br.org.cancer.redome.courier.service;

import br.org.cancer.redome.courier.model.EmailContatoTransportadora;
import br.org.cancer.redome.courier.service.custom.IService;

/**
 * Interface de negócios de Email de Transportadora.
 * @author Filipe Paes
 *
 */
public interface IEmailContatoTransportadoraService extends IService<EmailContatoTransportadora, Long>{
	
	/**
	 * Atualiza email de contato de transportadora.
	 * @param idEmailContato identificação do email a ser atualizado. 
	 * @param emailContatoTransportadora email a ser atualizado.
	 */
	void atualizar(Long idEmailContato, EmailContatoTransportadora emailContatoTransportadora);

}

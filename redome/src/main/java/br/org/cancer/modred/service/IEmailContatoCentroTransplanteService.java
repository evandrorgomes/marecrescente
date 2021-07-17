package br.org.cancer.modred.service;

import br.org.cancer.modred.model.EmailContatoCentroTransplante;
import br.org.cancer.modred.service.custom.IService;

/**
 * Interface de negócios de Email de contato do centro de transplante.
 * @author brunosousa
 *
 */
public interface IEmailContatoCentroTransplanteService extends IService<EmailContatoCentroTransplante, Long>{

	/**
	 * Método para salvar o email do centro de transplante.
	 * 
	 * @param emailCentroTransplante
	 */
	void salvar(EmailContatoCentroTransplante emailCentroTransplante);
	

}

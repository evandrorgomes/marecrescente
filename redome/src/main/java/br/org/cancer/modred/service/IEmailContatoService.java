package br.org.cancer.modred.service;

import br.org.cancer.modred.model.EmailContato;
import br.org.cancer.modred.service.custom.IService;

/**
 * Interface de negocios para Email de Contato.
 * @param <T> extendendo da classe EmailContato.
 * 
 * @author Filipe Paes
 *
 */
public interface IEmailContatoService<T extends EmailContato> extends IService<T, Long>{

	/**
	 * Método para excluir e-mail de contato por id.
	 * @param idEmailContato
	 */
	void excluir(Long idEmailContato);
}

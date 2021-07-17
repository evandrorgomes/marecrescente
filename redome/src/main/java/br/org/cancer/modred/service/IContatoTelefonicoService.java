package br.org.cancer.modred.service;

import br.org.cancer.modred.model.ContatoTelefonico;
import br.org.cancer.modred.service.custom.IService;

/**
 * Interface para métodos de negócio de contato telefonico.
 * @param <T> classe que define a implementação em cima do contato telefonico.
 * 
 * @author Filipe Paes
 *
 */
public interface IContatoTelefonicoService<T extends ContatoTelefonico> extends IService<T, Long> {

	/**
	 * Método para excluir logicamente um contato telefônico.
	 * @param idContatoTelefonico
	 */
	void excluir(Long idContatoTelefonico);
	
	/**
	 * Validação da classe ContatoTelefonico.
	 * 
	 * @param contato objeto a ser validado.
	 */
	void validar(ContatoTelefonico contato);
	
	/**
	 * Obtem um contato telefonico por id.
	 * @param id
	 * @return contato telefonico localizado
	 */
	ContatoTelefonico obterContatoTelefonico(Long id);
}

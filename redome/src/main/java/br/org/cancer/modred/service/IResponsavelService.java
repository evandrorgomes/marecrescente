package br.org.cancer.modred.service;

/**
 * Interface para metodos de negocio de responsável.
 * 
 * @author bruno.sousa
 *
 */
public interface IResponsavelService {
	
	/**
	 * Método para excluir o responsável.
	 * 
	 * @param id ID da entidade responsável.
	 */
	void apagarResponsavel(Long id);
}

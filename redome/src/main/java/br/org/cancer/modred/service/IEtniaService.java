package br.org.cancer.modred.service;

import java.util.List;

import br.org.cancer.modred.model.Etnia;

/**
 * Interface para metodos de negocio de etnia.
 * 
 * @author Filipe Paes
 *
 */
public interface IEtniaService {
	/**
	 * Lista as etnias.
	 * 
	 * @param List<Etnia>
	 */
	List<Etnia> listarEtnias();

	/**
	 * Método para obter Etnia por ID.
	 * 
	 * @param id
	 * @return Etnia etnia localiza
	 */
	Etnia getEtnia(Long id);
}

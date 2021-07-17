package br.org.cancer.modred.service;

import java.util.List;

import br.org.cancer.modred.model.Raca;

/**
 * Interface para metodos de negocio de raça.
 * 
 * @author Filipe Paes
 *
 */
public interface IRacaService {

	/**
	 * Lista as raças.
	 * 
	 * @param List<Raca>
	 */
	List<Raca> listarRacas();
	
	/**
	 * Método para obter uma raça.
	 * @param Long id da raça 
	 * @return Raca raça localizada
	 */
	Raca obterRaca(Long id);
}

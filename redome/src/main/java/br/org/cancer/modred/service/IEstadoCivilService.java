package br.org.cancer.modred.service;

import java.util.List;

import br.org.cancer.modred.model.EstadoCivil;

/**
 * Interface para metodos de negocio de Estado Civil.
 * 
 * @author ergomes
 *
 */
public interface IEstadoCivilService {

	/**
	 * Lista os Estados Civis.
	 * 
	 * @param List<EstadoCivil>
	 */
	List<EstadoCivil> listarEstadosCivis();
	
	/**
	 * Método para obter um Estado Civil.
	 * @param Long id do estado civil 
	 * @return Estado Civil estadoCivil localizado
	 */
	EstadoCivil obterEstadoCivil(Long id);
}

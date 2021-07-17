package br.org.cancer.modred.service;

import br.org.cancer.modred.model.StatusDoador;

/**
 * Interface de métodos de negócio de status de doador.
 * 
 * @author Fillipe Queiroz
 *
 */
public interface IStatusDoadorService {

	/**
	 * Método para obter um status por id.
	 * @param idStatus - identificador do status
	 * @return entidade de status
	 */
	StatusDoador obterStatusPorId(Long idStatus);
}

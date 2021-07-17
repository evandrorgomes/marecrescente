package br.org.cancer.modred.service;

import java.util.List;

import br.org.cancer.modred.model.DestinoColeta;

/**
 * Inteface de negócios da classe de DestinoColeta.
 * @author Filipe Paes
 *
 */
public interface IDestinoColetaService {
	
	/**
	 * Método para listagem de destinos de coleta.
	 * @return listagem de destinos de Coleta.
	 */ 
	List<DestinoColeta> listarDestinos();

}

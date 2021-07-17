package br.org.cancer.redome.workup.service;

import br.org.cancer.redome.workup.model.TransporteTerrestre;
import br.org.cancer.redome.workup.service.custom.IService;

/**
 * Interface de negocios para ArquivoResultadoWotkup.
 * 
 * @author bruno.sousa
 *
 */
public interface ITransporteTerrestreService extends IService<TransporteTerrestre, Long> {

	/**
	 * Método para excluir transporte terrestre.
	 * 
	 * @param id Identificador do transporte terrestre.
	 */
	void excluirPorId(Long id);

}

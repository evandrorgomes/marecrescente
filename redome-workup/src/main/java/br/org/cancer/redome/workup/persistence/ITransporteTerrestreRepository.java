package br.org.cancer.redome.workup.persistence;

import org.springframework.stereotype.Repository;

import br.org.cancer.redome.workup.model.TransporteTerrestre;

/**
 * Interface de persistencia para ArquivoResultadoWorkup.
 * 
 * @author bruno.sousa
 *
 */
@Repository
public interface ITransporteTerrestreRepository extends IRepository<TransporteTerrestre, Long> {
	
	
	
}

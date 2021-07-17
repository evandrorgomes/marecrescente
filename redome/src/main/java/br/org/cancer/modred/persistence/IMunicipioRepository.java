package br.org.cancer.modred.persistence;

import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.Municipio;

/**
 * Interface de persistencia para a entidade Municipio.
 * 
 * @author brunosousa
 *
 */
@Repository
public interface IMunicipioRepository extends IRepository<Municipio, Long> {
	
}

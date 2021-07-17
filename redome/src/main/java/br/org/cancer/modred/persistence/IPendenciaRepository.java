package br.org.cancer.modred.persistence;

import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.Pendencia;

/**
 * Interface de persistencia de Pendência.
 * 
 * @author Filipe Paes
 *
 */
@Repository
public interface IPendenciaRepository extends IRepository<Pendencia, Long>, IPendenciaRepositoryCustom {

}

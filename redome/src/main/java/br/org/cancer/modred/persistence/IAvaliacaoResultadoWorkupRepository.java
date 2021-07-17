package br.org.cancer.modred.persistence;

import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.AvaliacaoResultadoWorkup;

/**
 * Interface de persistencia para avaliação de resultado de workup.
 * 
 * @author Fillipe Queiroz
 *
 */
@Repository
public interface IAvaliacaoResultadoWorkupRepository extends IRepository<AvaliacaoResultadoWorkup, Long> {
}

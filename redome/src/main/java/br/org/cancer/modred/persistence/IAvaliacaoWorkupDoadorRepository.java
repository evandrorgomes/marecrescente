package br.org.cancer.modred.persistence;

import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.AvaliacaoWorkupDoador;

/**
 * Interface de persistencia para avaliação de doador.
 * 
 * @author Fillipe Queiroz
 *
 */
@Repository
public interface IAvaliacaoWorkupDoadorRepository extends IRepository<AvaliacaoWorkupDoador, Long> {
}

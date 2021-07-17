package br.org.cancer.modred.persistence;

import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.AvaliacaoCamaraTecnica;

/**
 * Interface de persistencia de avaliação de camara técnica.
 * @author Filipe Paes
 *
 */
@Repository
public interface IAvaliacaoCamaraTecnicaRepository  extends IRepository<AvaliacaoCamaraTecnica, Long> {

}

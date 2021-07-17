package br.org.cancer.modred.persistence;

import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.StatusAvaliacaoCamaraTecnica;

/**
 * Interface de persistenca de status de avaliação de camara técnica.
 * @author Filipe Paes
 *
 */
@Repository
public interface IStatusAvaliacaoCamaraTecnicaRepository  extends IRepository<StatusAvaliacaoCamaraTecnica, Long>{

}

package br.org.cancer.modred.persistence;

import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.HistoricoBusca;

/**
 * Camada de acesso a base dados do histórico da busca.
 * 
 * @author Pizão
 *
 */
@Repository
public interface IHistoricoBuscaRepository extends IRepository<HistoricoBusca, Long>{}
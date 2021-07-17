package br.org.cancer.modred.persistence;

import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.TipoBuscaChecklist;

/**
 * Interface de persistencia de TipoBuscaChecklist.
 * @author Filipe Paes
 *
 */
@Repository
public interface ITipoBuscaChecklistRepository  extends IRepository<TipoBuscaChecklist, Long> {

}

package br.org.cancer.modred.persistence;

import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.TipoChecklist;

/**
 * Métodos de acesso a dados de tipo de checklist.
 * @author Filipe Paes
 *
 */
@Repository
public interface ITipoChecklistRepository  extends IRepository<TipoChecklist, Long>{

}

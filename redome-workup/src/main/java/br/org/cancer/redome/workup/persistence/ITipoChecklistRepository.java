package br.org.cancer.redome.workup.persistence;

import org.springframework.stereotype.Repository;

import br.org.cancer.redome.workup.model.TipoChecklist;

/**
 * Métodos de acesso a dados de tipo de checklist.
 * @author ergomes
 *
 */
@Repository
public interface ITipoChecklistRepository  extends IRepository<TipoChecklist, Long>{

}

package br.org.cancer.modred.persistence;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.modred.model.TipoAmostra;

/**
 * Repositorio de tipo de amostra.
 * @author Filipe Paes
 *
 */
@Repository
@Transactional
public interface ITipoAmostraRepository  extends IRepository<TipoAmostra, Long>{

}

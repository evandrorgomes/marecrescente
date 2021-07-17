package br.org.cancer.modred.persistence;

import br.org.cancer.modred.model.HemoEntidade;

/**
 * Repositório da classe HemoEntidade.
 * 
 * @author Pizão.
 *
 */
public interface IHemoEntidadeRepository extends IRepository<HemoEntidade, Long> {
	
	HemoEntidade findByIdHemocentroRedomeWeb(Long id);
}

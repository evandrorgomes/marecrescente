package br.org.cancer.modred.persistence;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.Locus;

/**
 * Interface de persistencia de Locus.
 * 
 * @author filipe.souza
 *
 */
@Repository
public interface ILocusRepository extends IRepository<Locus, String> {

	/**
	 * Método para trazer a lista ordenada de locus.
	 * 
	 * @return List<Locus> listagem de locus
	 */
	@Cacheable(value = "dominio", key = "'Locus'+#root.methodName")
	List<Locus> findByOrderByOrdemAsc();
}

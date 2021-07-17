package br.org.cancer.modred.persistence;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.Pais;

/**
 * 
 * @author Filipe Paes
 *
 */
@Repository
public interface IPaisRepository extends JpaRepository<Pais, Long> {

	/**
	 * Método para obter lista de países, considerando Brasil no topo da lista.
	 *
	 * Return: List<Pais>
	 */
	@Cacheable(value = "dominio", key = "#root.methodName")
	@Query("select p from Pais p order by (case when p.id = 1 then 0 else 1 end)")
	List<Pais> buscarListaPaises();
}

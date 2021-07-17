package br.org.cancer.modred.persistence;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.Etnia;

/**
 * 
 * @author Filipe Paes
 *
 */
@Repository
public interface IEtniaRepository extends JpaRepository<Etnia, Long> {

	/**
	 * Método para obter lista de etnias.
	 *
	 * Return: List<Etnia>
	 */
	@Cacheable(value = "dominio", key = "'Etnia'+#root.methodName")
	List<Etnia> findByOrderByNomeAsc();
}

package br.org.cancer.modred.persistence;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.Raca;

/**
 * 
 * @author Filipe Paes
 *
 */
@Repository
public interface IRacaRepository extends JpaRepository<Raca, Long> {

	/**
	 * Método para obter lista de raças.
	 *
	 * Return: List<Raca>
	 */
	@Cacheable(value = "dominio", key = "'Raca'+#root.methodName")
	List<Raca> findByOrderByNomeAsc();
}
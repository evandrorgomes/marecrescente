package br.org.cancer.modred.persistence;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.Metodologia;

/**
 * 
 * @author Fillipe Queiroz
 *
 */
@Repository
public interface IMetodologiaRepository extends JpaRepository<Metodologia, Long> {

	/**
	 * Método para obter lista de metodologias.
	 *
	 * Return: List<Metodologia>
	 */
	@Cacheable(value = "dominio", key="'Metodologia'+#root.methodName")
	List<Metodologia> findAllByOrderBySiglaAsc();
}
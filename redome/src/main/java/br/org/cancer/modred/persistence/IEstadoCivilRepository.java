package br.org.cancer.modred.persistence;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.EstadoCivil;

/**
 * 
 * @author ergomes
 *
 */
@Repository
public interface IEstadoCivilRepository extends JpaRepository<EstadoCivil, Long> {

	/**
	 * Método para obter lista de Estados Civis.
	 *
	 * Return: List<EstadoCivil>
	 */
	@Cacheable(value = "dominio", key = "'EstadoCivil'+#root.methodName")
	List<EstadoCivil> findByOrderByNomeAsc();
}
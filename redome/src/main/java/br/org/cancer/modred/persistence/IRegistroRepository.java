package br.org.cancer.modred.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.Registro;

/**
 * Interfaces de métodos de persistencia de registro.
 * @author bruno.sousa
 *
 */
@Repository
public interface IRegistroRepository  extends IRepository<Registro, Long> {

	/**
	 * Lista todos os registros ordenados por nome.
	 * @return lista ordenada de registros.
	 */
	List<Registro> findAllByOrderByNomeAsc();

	/**
	 * Obtem registro por donPool.
	 * @return lista ordenada de registros.
	 */
	Optional<Registro> findByDonPool(Long donPool);

}

package br.org.cancer.modred.persistence;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.Uf;

/**
 * 
 * @author Filipe Paes
 *
 */
@Repository
public interface IUfRepository extends JpaRepository<Uf, String> {

	/**
	 * Método para obter lista de UFs.
	 *
	 * Return: List<Uf>
	 */
	@Cacheable(value = "dominio", key = "'Ufs'+#root.methodName")
	List<Uf> findByOrderByNomeAsc();

	/**
	 * Lista toas as ufs sem a silga informada.
	 * 
	 * @param sigla
	 * @return
	 */
	@Cacheable(value = "dominio", key = "'Ufs'+#root.methodName")
	List<Uf> findBySiglaNotOrderByNomeAsc(String silga);

	/**
	 * Método para obter lista de siglas, considerando ordenação a partir do RJ.
	 *
	 * Return: List<Uf>
	 */
	/*
	 * @Query("select unfe from Uf order by (case when sigla = 'RJ' then 0 else 1 end)") List<Uf> buscarListaUfs();
	 */
}

package br.org.cancer.modred.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.EstagioDoenca;

/**
 * Classe que faz consultas pelo spring data de estagio de doenca.
 * 
 * @author Fillipe Queiroz
 *
 */
@Repository
public interface IEstagioDoencaRepository extends JpaRepository<EstagioDoenca, Long> {

	/**
	 * Método para obter lista de estagios doenças.
	 *
	 * Return: List<EstagioDoenca>
	 */
	List<EstagioDoenca> findByCidsId(long idCid);

	/**
	 * 
	 * @param cidId
	 * @param estagioDoencaId
	 * @return
	 */
	@Query(value = "select estagio from EstagioDoenca estagio "
			+ "join fetch estagio.cids cids where cids.id = ?1 and estagio.id = ?2")
	EstagioDoenca obterEstagioReferenteAoCid(Long cidId, Long estagioId);
}

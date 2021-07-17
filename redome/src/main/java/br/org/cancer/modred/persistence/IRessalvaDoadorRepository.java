package br.org.cancer.modred.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.RessalvaDoador;

/**
 * Interface para acesso as funcionalidades de acesso ao banco envolvendo a entidade RessalvaDoador.
 * 
 * @author Pizão.
 *
 */
@Repository
public interface IRessalvaDoadorRepository extends IRepository<RessalvaDoador, Long> {

	/**
	 * Lista as ressalvas de um determinado doador.
	 * 
	 * @param idDoador idDoador do doador utilizado no filtro.
	 * @return lista de ressalvas do doador.
	 */
	List<RessalvaDoador> findByDoadorIdAndExcluido(Long idDoador, boolean excluido);
	
	/**
	 * Busca o idDoador do doador associado ao ID da ressalva informado.
	 * 
	 * @param idRessalva ID da ressalva utilizada no filtro.
	 * @return idDoador do doador.
	 */
	@Query("select r.doador.id from RessalvaDoador r where r.id = :idRessalva")
	Long obterIdDoadorAssociadoARessalva(@Param(value = "idRessalva") Long idRessalva);
	
	/**
	 * Conta o número de registros de ressalva para determinado idDoador.
	 * 
	 * @param idDoador idDoador utilizado no filtro.
	 * @return a quantidade de registros.
	 */
	@Query("select count(1) from RessalvaDoador r where r.doador.id = :idDoador and r.excluido = false")
	Integer contarRessalvasPorDoador(@Param(value = "idDoador") Long idDoador);

	@Modifying
	@Query("update RessalvaDoador set excluido = true where id = :idRessalva")
	int excluirLogicamente(@Param(value = "idRessalva") Long idRessalva);
}

package br.org.cancer.modred.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.modred.model.ValorGenotipoExpandidoDoador;


/**
 * Interface para acesso ao banco envolvendo a entidade ValorGenotipoExpandido.
 * 
 * @author Pizão
 *
 */
@Repository
@Transactional
public interface IValorGenotipoExpandidoDoadorRepository extends IRepository<ValorGenotipoExpandidoDoador, Long> {

	
	/**
	 * Deleta o genotipo expandido por um id de genotipo.
	 * 
	 * @param idGenotipo identificador do genotipo.
	 */
	@Modifying
	@Query(value = "DELETE FROM VALOR_GENOTIPO_EXPAND_DOADOR WHERE GEDO_ID = :idGenotipo", nativeQuery=true)
	void deletarValoresPorGenotipo(@Param("idGenotipo") Long idGenotipo);
	
	List<ValorGenotipoExpandidoDoador> findByGenotipoId(Long idDoador);
	
}

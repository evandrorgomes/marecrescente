package br.org.cancer.modred.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.modred.model.ValorGenotipoExpandidoPaciente;

/**
 * Interface para acesso ao banco envolvendo a entidade ValorGenotipoExpandido.
 * 
 * @author Pizão
 *
 */
@Repository
@Transactional
public interface IValorGenotipoExpandidoRepository extends IRepository<ValorGenotipoExpandidoPaciente, Long> {

	/**
	 * Deleta os registros de valores para busca associado ao paciente informado.
	 * 
	 * @param rmr identificador do paciente.
	 */
	@Modifying
	@Query(value = "DELETE FROM MODRED.VALOR_GENOTIPO_EXPAND_PACIENTE WHERE PACI_NR_RMR = :rmr", nativeQuery=true)
	void deletarValoresPorPaciente(@Param("rmr") Long rmr);
	
	/**
	 * Deleta o genotipo expandido por um id de genotipo.
	 * 
	 * @param idGenotipo identificador do genotipo.
	 */
	@Modifying
	@Query(value = "DELETE FROM MODRED.VALOR_GENOTIPO_EXPAND_PACIENTE WHERE GEPA_ID = :idGenotipo", nativeQuery=true)
	void deletarValoresPorGenotipo(@Param("idGenotipo") Long idGenotipo);
	
	List<ValorGenotipoExpandidoPaciente> findByGenotipoId(Long idGenotipo);
		
}

package br.org.cancer.modred.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.modred.model.ValorGenotipoBuscaPaciente;

/**
 * Interface para acesso ao banco envolvendo a entidade ValorGenotipoBusca.
 * 
 * @author Fillipe Queiroz
 *
 */
@Repository
@Transactional
public interface IValorGenotipoBuscaRepository extends IRepository<ValorGenotipoBuscaPaciente, Long> {

	/**
	 * Deleta os registros de valores para busca associado ao paciente informado.
	 * 
	 * @param rmr identificador do paciente.
	 */
	@Modifying
	@Query(value = "DELETE FROM MODRED.VALOR_GENOTIPO_BUSCA_PACIENTE WHERE GEPA_ID = :genotipoId", nativeQuery=true)
	void deletarValoresPorGenotipo(@Param("genotipoId") Long genotipoId);
	
	
	List<ValorGenotipoBuscaPaciente> findByGenotipoId(Long idGenotipo);
	
}

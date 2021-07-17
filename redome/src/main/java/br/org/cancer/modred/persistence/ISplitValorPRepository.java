package br.org.cancer.modred.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.SplitValorP;
import br.org.cancer.modred.model.SplitValorPPK;

/**
 * Classe de repositorio para Split Valor P.
 * 
 * @author brunosousa
 *
 */
@Repository
public interface ISplitValorPRepository  extends JpaRepository<SplitValorP, SplitValorPPK> {
	
	@Query(nativeQuery = true, 
			value="select distinct spvp_tx_nome_grupo from split_valor_p where locu_id = :codigoLocus and spvp_tx_valor in (:valoresAlelico) and spvp_nr_valido = 1")
	List<String> obterGrupo(@Param("codigoLocus") String codigoLocus, @Param("valoresAlelico") List<String> valoresAlelico);
	
	
}

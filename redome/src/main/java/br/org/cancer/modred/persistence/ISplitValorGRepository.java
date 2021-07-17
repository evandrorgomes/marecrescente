package br.org.cancer.modred.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.SplitValorG;
import br.org.cancer.modred.model.SplitValorGPK;

/**
 * Classe de repositorio para Split Valor G.
 * 
 * @author brunosousa
 *
 */
@Repository
public interface ISplitValorGRepository  extends JpaRepository<SplitValorG, SplitValorGPK> {
	
	@Query(nativeQuery = true, 
			value="select distinct spvg_tx_nome_grupo from split_valor_g where locu_id = :codigoLocus and spvg_tx_valor in (:valoresAlelico) and spvg_nr_valido = 1")
	List<String> obterGrupo(@Param("codigoLocus") String codigoLocus, @Param("valoresAlelico") List<String> valoresAlelico);
	
}

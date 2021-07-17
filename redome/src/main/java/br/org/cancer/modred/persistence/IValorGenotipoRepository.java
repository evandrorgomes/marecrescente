package br.org.cancer.modred.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.modred.model.ValorGenotipoPaciente;
import br.org.cancer.modred.model.ValorGenotipoPacientePK;

/**
 * Interface para acesso ao banco envolvendo a entidade ValorGenotipo.
 * 
 * @author Pizão
 *
 */
@Repository
@Transactional
public interface IValorGenotipoRepository extends IRepository<ValorGenotipoPaciente, ValorGenotipoPacientePK> {
	
	/**
	 * 
	 * @param rmr RMR do paciente a ser pesquisado.
	 * @return a lista contendo o genótipo do paciente.
	 */
	@Query("select new ValorGenotipoPaciente(l.codigo, g.alelo, g.id.posicaoAlelo, e) "
		+  "from ValorGenotipoPaciente g join g.id.exame e join g.id.locus l join e.paciente p where p.rmr = :rmr "
		+  "order by l.ordem")
	List<ValorGenotipoPaciente> obterGenotipo(@Param("rmr") Long rmr);
	
	
	@Modifying
	@Query(value = "DELETE FROM MODRED.VALOR_GENOTIPO_PACIENTE WHERE GEPA_ID = :genotipo", nativeQuery=true)
	void deletarValoresGenotipo(@Param("genotipo") Long genotipo );
}

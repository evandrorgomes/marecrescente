package br.org.cancer.modred.persistence;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.GenotipoPaciente;
import br.org.cancer.modred.model.Locus;

/**
 * Interface para acesso ao banco envolvendo a entidade Genotipo.
 * 
 * @author Fillipe Queiroz
 *
 */
@Repository
public interface IGenotipoRepository extends IRepository<GenotipoPaciente, Long> {

	/**
	 * Método para buscar o genótipo ativo pelo id do genotipo e pela flag.
	 * @param idGenotipo Id do genotipo de um paciente ou doador.
	 * @return entidade de genotipo.
	 */
	GenotipoPaciente findByIdAndExcluido(Long idGenotipo, boolean excluido);
	
	/**
	 * Obtém o genótipo associado ao paciente passado por parâmetro (RMR).
	 * 
	 * @param rmr Identificador do paciente.
	 * @return paciente associado ao genótipo.
	 */
	@Query("select g from GenotipoPaciente g join g.paciente p where p.rmr = :rmr")
	GenotipoPaciente obterGenotipoPorPaciente(@Param("rmr") Long rmr);
	
	
	/**
	 * Verifica se o paciente já possui os locus considerados de classe C, 
	 * necessários para continuação do processo de busca/match.
	 * 
	 * @param rmr identificação do paciente.
	 * @return TRUE, caso possua todos os locus.
	 */
	@Query(	"select true "
		+  	"from ValorGenotipoPaciente g join g.id.exame e join g.id.locus l join e.paciente p "
		+ 	"where p.rmr = :rmr "
		+ 	"and l.codigo in ('" + Locus.LOCUS_C + "', '" + Locus.LOCUS_DQB1 + "', '" + Locus.LOCUS_DRB1 + "')")
	Boolean verificarSePossuiExamesClasseC(@Param("rmr") Long rmr);

}

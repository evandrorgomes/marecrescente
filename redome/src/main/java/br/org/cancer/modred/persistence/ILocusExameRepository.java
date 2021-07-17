package br.org.cancer.modred.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.LocusExame;
import br.org.cancer.modred.model.LocusExamePk;

/**
 * Repositório para acesso ao banco da entidade LocusExame.
 * 
 * @author Pizão
 *
 */
@Repository
public interface ILocusExameRepository extends IRepository<LocusExame, LocusExamePk> {

	/**Método para pesquisar um grupo alélico para um exame.
	 * @param exameId identificador do exame
	 * @param grupoAlelico grupo alélico 
	 * @return
	 */
	@Query("select locusExame from LocusExame locusExame join"
			+ " locusExame.id.exame exame where exame.id = ?1 and locusExame.id.locus.codigo = ?2"
			+ " and locusExame.primeiroAlelo = ?3 and locusExame.segundoAlelo = ?4")
	LocusExame findByExameIdAndGrupoAlelicoAndAlelos(long exameId, String grupoAlelico, String primeiroAlelo, String segundoAlelo);
	
	/**
	 * Lista os locus exames associados ao ID do exame informado.
	 * 
	 * @param exameId ID do exame que será pesquisado.
	 * @return lista de locus exames com os valores de primeiro e segundo alelos.
	 */
	@Query("select new LocusExame(ex, lo.codigo, le.primeiroAlelo, le.segundoAlelo) "
		+  "from LocusExame le join le.id.exame ex join le.id.locus lo "
		+  "where ex.id = :exameId "
		+  "order by lo.ordem")
	List<LocusExame> listarLocusExames(@Param("exameId") Long exameId);
	
	
	/**Método para pesquisar um locus para um exame.
	 * @param exameId identificador do exame
	 * @param grupoAlelico grupo alélico 
	 * @return
	 */
	@Query("select locusExame from LocusExame locusExame join"
			+ " locusExame.id.exame exame where exame.id = ?1 and locusExame.id.locus.codigo = ?2")
	LocusExame findByExameIdAndLocusCodigo(long exameId, String locus);
	
	
	
}
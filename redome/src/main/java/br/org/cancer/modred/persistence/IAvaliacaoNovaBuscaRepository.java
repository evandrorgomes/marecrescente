package br.org.cancer.modred.persistence;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.AvaliacaoNovaBusca;

/**
 * Interface de acesso a base de dados envolvendo a
 * entidade AvaliacaoNovaBusca.
 * 
 * @author Pizão
 *
 */
@Repository
public interface IAvaliacaoNovaBuscaRepository extends IRepository<AvaliacaoNovaBusca, Long> {
	
	/**
	 * Obter avaliação a última avaliação associada ao paciente,
	 * se existir. 
	 * 
	 * @param rmr identificador do paciente.
	 * @return retorna a última avaliação, se existir.
	 */
	@Query(	"select a from AvaliacaoNovaBusca a join a.paciente p "
		+ 	"where p.rmr = :rmr "
		+ 	"and a.dataCriacao = ("
		+ 		"select max(a.dataCriacao) from AvaliacaoNovaBusca a join a.paciente p "
		+ 		"where p.rmr = :rmr"
		+ 	")")
	AvaliacaoNovaBusca obterUltimaAvaliacao(@Param("rmr") Long rmr);
	
}

package br.org.cancer.redome.workup.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.org.cancer.redome.workup.model.AvaliacaoResultadoWorkup;

/**
 * Interface de persistencia para avaliação de resultado de workup.
 * 
 * @author Bruno Sousa
 *
 */
@Repository
public interface IAvaliacaoResultadoWorkupRepository extends IRepository<AvaliacaoResultadoWorkup, Long> {

	Optional<AvaliacaoResultadoWorkup> findByResultadoWorkupId(Long idResultadoWorkup);

	@Query("select arw from AvaliacaoResultadoWorkup arw join arw.resultadoWorkup rw join rw.pedidoWorkup pw where pw.solicitacao = :idSolicitacao")
	Optional<AvaliacaoResultadoWorkup> findBySolicitacaoId(@Param(value = "idSolicitacao") Long idSolicitacao);
}

package br.org.cancer.redome.workup.persistence;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import br.org.cancer.redome.workup.model.ResultadoWorkup;

/**
 * Repositório para acesso a base de dados ligadas a entidade PedidoWorkup.
 * 
 * @author bruno.sousa
 *
 */
@Repository
public interface IResultadoWorkupRepository extends IRepository<ResultadoWorkup, Long> {

	Optional<ResultadoWorkup> findByPedidoWorkupId(Long idPedidoWorkup);

	Optional<ResultadoWorkup> findByPedidoWorkupSolicitacao(Long idSolicitacao);
	
}

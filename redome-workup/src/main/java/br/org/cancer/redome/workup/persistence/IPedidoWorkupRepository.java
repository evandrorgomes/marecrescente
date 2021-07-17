package br.org.cancer.redome.workup.persistence;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import br.org.cancer.redome.workup.model.PedidoWorkup;

/**
 * Repositório para acesso a base de dados ligadas a entidade PedidoWorkup.
 * 
 * @author ergomes
 *
 */
@Repository
public interface IPedidoWorkupRepository extends IRepository<PedidoWorkup, Long>, IPedidoWorkupRepositoryCustom {

	/**
	 * Obtém a pedido workup associada a solicitação passada por parâmetro.
	 * 
	 * @param idSolicitacao Identificador da solicitação a ser utilizada no filtro.
	 * @return Optional<PedidoWorkup>
	 */
	Optional<PedidoWorkup> findBySolicitacao(Long idSolicitacao);
	
	
}

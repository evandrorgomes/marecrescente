package br.org.cancer.modred.persistence;

import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.PedidoIdm;

/**
 * Interface para acesso ao banco de dados envolvendo a classe PedidoExame.
 * 
 * @author Pizão
 *
 */
@Repository
public interface IPedidoIdmRepository extends IRepository<PedidoIdm, Long> {
	/**
	 * Busca o pedidoIdm por uma solicitação.
	 * @param idSolicitacao - identificador da solicitaçao
	 * @return PedidoIdm entidade
	 */
	PedidoIdm findBySolicitacaoId(Long idSolicitacao);
}

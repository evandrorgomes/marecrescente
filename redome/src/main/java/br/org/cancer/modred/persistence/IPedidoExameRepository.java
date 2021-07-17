package br.org.cancer.modred.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.PedidoExame;

/**
 * Interface para acesso ao banco de dados envolvendo a classe PedidoExame.
 * 
 * @author Pizão
 *
 */
@Repository
public interface IPedidoExameRepository extends IRepository<PedidoExame, Long>, IPedidoExameRepositoryCustom {

	/**
	 * Busca pedidos de exame por id de solicitação e status.
	 * 
	 * @param idSolicitacao - id da solicitação de FASE 2 ou FASE 3.
	 * @return pedidos de exame localizados.
	 */
	List<PedidoExame> findBySolicitacaoId(Long idSolicitacao);

	/**
	 * Obtém o ultimo pedido de exame de Fase3 ordernado pela da de criação.
	 * 
	 * @param idTipoSolicitacao
	 * @param buscaId
	 * @return Último pedido de exame
	 */
	PedidoExame findFirstBySolicitacaoTipoSolicitacaoIdAndSolicitacaoBuscaIdOrderByDataCriacaoDesc(Long idTipoSolicitacao,
			Long buscaId);

	
	/**
	 * 
	 * Recupera uma lista de pedidos de exame para o doador, de acordo com o status sugerido. Por definição, se o status for
	 * SOLICITADO, só poderá haver um item na lista.	 
	 * 
	 * @param matchId ID do match relacionado pedido.
	 * @param status status dos pedidos.
	 * @return lista de pedidos de exame associados ao mesmo.
	 */
	@Query("select pe "
			+ "from PedidoExame pe join pe.statusPedidoExame stPe join pe.solicitacao s join s.match m "
			+ "where m.id = :#{#matchId} and stPe.id in ( :#{#status} )")
	List<PedidoExame> listarPedidoExamePorDoador(@Param("matchId") Long matchId, @Param("status") List<Long> status);
	
	/**
	 * Recupera todos os pedidos de exame para o doador. Por definição, se o status for SOLICITADO, só poderá haver um item na
	 * lista.
	 * 
	 * @param matchId ID do match relacionado pedido.
	 * @return lista de pedidos de exame associados ao mesmo.
	 */
	@Query("select new PedidoExame(pe.id) "
			+ "from PedidoExame pe join pe.solicitacao s join s.match m "
			+ "where m.id = :matchId")
	List<PedidoExame> listarPedidoExamePorDoador(@Param("matchId") Long matchId);

	/**
	 * Recupera o id do pedido de exame que está com status Aguardando amostra e Aguardando recebimento.
	 * 
	 * @param idMatch
	 * @return
	 */
	@Query("select pe.id "
			+ "from PedidoExame pe join pe.solicitacao s join s.match m "
			+ "where m.id = :idMatch and (pe.statusPedidoExame.id = 0 or pe.statusPedidoExame.id = 4) "
			+ "and s.status = 1 and m.status = true ")
	Long obterIdPedidoExamePorIdMatch(@Param("idMatch") Long idMatch);
	
}

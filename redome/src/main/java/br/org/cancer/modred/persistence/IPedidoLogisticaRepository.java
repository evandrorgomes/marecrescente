package br.org.cancer.modred.persistence;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.PedidoColeta;
import br.org.cancer.modred.model.PedidoLogistica;
import br.org.cancer.modred.model.PedidoWorkup;
import br.org.cancer.modred.model.Solicitacao;
import br.org.cancer.modred.model.domain.OrigemLogistica;

/**
 * Interface para acesso ao banco de dados envolvendo a classe PedidoLogistica.
 * 
 * @author Pizão
 *
 */
@Repository
public interface IPedidoLogisticaRepository extends IRepository<PedidoLogistica, Long> , IPedidoLogisticaRepositoryCustom {
	
	/**
	 * Obtém a solicitação de workup associada ao pedido de logística.
	 * 
	 * @param pedidoLogisticaId ID do pedido de logística.
	 * @return solicitação associada ao pedido.
	 */
	@Query("select pl.pedidoWorkup.solicitacao from PedidoLogistica pl where pl.id = :id")
	Solicitacao obterSolicitacaoWorkup(@Param("id") Long pedidoLogisticaId);
	
	/**
	 * Obtem pedido de logistica por pedido de coeleta.
	 * @param idPedidoColetaId - id do pedido de coleta
	 * @return lista de pedidoDeLogistica.
	 */
	List<PedidoLogistica> findByPedidoColetaId(Long idPedidoColetaId);
	
	/**
	 * Obtém o pedido de workup associado ao pedido de logística informado.
	 * 
	 * @param pedidoLogisticaId ID do pedido de logística.
	 * @return pedido workup associado.
	 */
	@Query("select pw from PedidoLogistica pl join pl.pedidoWorkup pw where pl.id = :id")
	PedidoWorkup obterWorkup(@Param("id") Long pedidoLogisticaId);
	
	/**
	 * Obtém o pedido de coleta associado ao pedido de logística informado.
	 * 
	 * @param pedidoLogisticaId ID do pedido de logística.
	 * @return pedido de coleta associada.
	 */
	@Query("select pc from PedidoLogistica pl join pl.pedidoColeta pc where pl.id = :id")
	PedidoColeta obterColeta(@Param("id") Long pedidoLogisticaId);
	
	/**
	 * Lista os pedidos de logística em andamento (com pedido de transporte associado).
	 * Podem ser de origem nacional ou internacional.
	 * 
	 * @return lista de pedidos de logística.
	 */
	@Query("select pl from PedidoLogistica pl join pl.pedidoTransporte pt join pt.status s where pl.origem = :origem order by s.id ASC, pt.horaPrevistaRetirada ASC")
	Page<PedidoLogistica> listarPedidosLogisticaEmAndamento(@Param("origem") OrigemLogistica origem, Pageable page);

	
	@Query("select pl from PedidoLogistica pl join pl.pedidoWorkup pw join pw.solicitacao s join s.match m where m.id = :idMatch and pl.status = 3")
	PedidoLogistica obterPedidoLogisticaComPedidoWorkupPorIdMatch(@Param("idMatch") Long idMatch);
	
	@Query("select pl from PedidoLogistica pl join pl.pedidoColeta pc join pc.solicitacao s join s.match m where m.id = :idMatch and pl.status = 3")
	PedidoLogistica obterPedidoLogisticaComPedidoColetaPorIdMatch(@Param("idMatch") Long idMatch);
		
}

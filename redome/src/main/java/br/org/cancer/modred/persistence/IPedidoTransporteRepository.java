package br.org.cancer.modred.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.PedidoTransporte;

/**
 * Interface para acesso ao banco de dados envolvendo a classe PedidoTransporte.
 * 
 * @author Fillipe Queiroz
 *
 */
@Repository
public interface IPedidoTransporteRepository extends IRepository<PedidoTransporte, Long> {

	
	@Query("select pt from PedidoTransporte pt "
		+ " join pt.usuarioResponsavel usu "
		+ " join pt.status status"	
		+ " where usu.id =  :usuario "
		+ " and status.id in (:status)")
	List<PedidoTransporte> listarPedidosTransportes(@Param("usuario") Long usuario, @Param("status") List<Long> status);
	
	
	/**
	 * Obtem pedido de transporte por id de pedido de logistica.
	 * @param idPedidoLogistica do pedido de logistica.
	 * @return pedido de transporte.
	 */
	PedidoTransporte findByPedidoLogisticaId(Long idPedidoLogistica);
}

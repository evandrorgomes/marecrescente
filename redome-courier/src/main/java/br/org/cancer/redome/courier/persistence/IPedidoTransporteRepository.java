package br.org.cancer.redome.courier.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.org.cancer.redome.courier.model.PedidoTransporte;

/**
 * Interface com métodos de persistencia do pedido de transporte.
 * 
 * @author Bruno Sousa
 *
 */
@Repository
public interface IPedidoTransporteRepository extends IRepository<PedidoTransporte, Long>{

	Optional<PedidoTransporte> findByPedidoLogisticaAndStatusId(Long idPedidoLogistica, Long idStatus);
	
	@Query("select pt from PedidoTransporte pt "
			+ " join pt.status status"	
			+ " join pt.transportadora transp"
			+ " where transp.id = :idTransportadora"
			+ " and status.id in (:status)")
	Page<PedidoTransporte> listarPedidosTransportesPorIdTransportadoraEStatus(@Param("idTransportadora") Long idTransportadora, @Param("status") List<Long> status, Pageable page);
}

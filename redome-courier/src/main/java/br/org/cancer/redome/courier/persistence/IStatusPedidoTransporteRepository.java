package br.org.cancer.redome.courier.persistence;

import org.springframework.stereotype.Repository;

import br.org.cancer.redome.courier.model.StatusPedidoTransporte;

/**
 * Interface com métodos de persistencia do status pedido de transporte.
 * 
 * @author Bruno Sousa
 *
 */
@Repository
public interface IStatusPedidoTransporteRepository extends IRepository<StatusPedidoTransporte, Long>{


}

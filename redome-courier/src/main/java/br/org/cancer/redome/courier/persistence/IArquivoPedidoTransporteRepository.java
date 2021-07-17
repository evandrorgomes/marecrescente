package br.org.cancer.redome.courier.persistence;

import org.springframework.stereotype.Repository;

import br.org.cancer.redome.courier.model.ArquivoPedidoTransporte;

/**
 * Interface com métodos de persistencia do arquivo pedido de transporte.
 * 
 * @author Bruno Sousa
 *
 */
@Repository
public interface IArquivoPedidoTransporteRepository extends IRepository<ArquivoPedidoTransporte, Long>{


}

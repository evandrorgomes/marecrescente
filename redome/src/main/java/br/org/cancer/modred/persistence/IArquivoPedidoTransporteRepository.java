package br.org.cancer.modred.persistence;

import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.ArquivoPedidoTransporte;

/**
 * @author Filipe Paes
 *
 */
@Repository
public interface IArquivoPedidoTransporteRepository  extends IRepository<ArquivoPedidoTransporte, Long> {
    
    
}

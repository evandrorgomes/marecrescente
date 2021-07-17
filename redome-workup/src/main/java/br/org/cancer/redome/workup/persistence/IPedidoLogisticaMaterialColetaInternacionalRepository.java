package br.org.cancer.redome.workup.persistence;

import org.springframework.stereotype.Repository;

import br.org.cancer.redome.workup.model.PedidoLogisticaMaterialColetaInternacional;

/**
 * Repositório para acesso a base de dados ligadas a entidade PedidoLogisticaMaterialColeta.
 * 
 * @author ergomes
 *
 */
@Repository
public interface IPedidoLogisticaMaterialColetaInternacionalRepository extends IRepository<PedidoLogisticaMaterialColetaInternacional, Long> {
	
}

package br.org.cancer.redome.workup.persistence;

import java.util.List;

import org.springframework.stereotype.Repository;

import br.org.cancer.redome.workup.model.PedidoAdicionalWorkup;

/**
 * Interface de persistencia para pedido adicional.
 * 
 * @author ergomes
 *
 */
@Repository
public interface IPedidoAdicionalWorkupRepository extends IRepository<PedidoAdicionalWorkup, Long> {

	List<PedidoAdicionalWorkup> findByPedidoWorkup(Long idPedidoWorkup);

}

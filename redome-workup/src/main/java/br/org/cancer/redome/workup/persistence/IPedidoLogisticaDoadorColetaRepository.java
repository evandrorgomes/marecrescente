package br.org.cancer.redome.workup.persistence;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.org.cancer.redome.workup.model.PedidoLogisticaDoadorColeta;

/**
 * Repositório para acesso a base de dados ligadas a entidade PedidoLogisticaMaterial.
 * 
 * @author ergomes
 *
 */
@Repository
public interface IPedidoLogisticaDoadorColetaRepository extends IRepository<PedidoLogisticaDoadorColeta, Long> {

	@Query(value = "SELECT decode(COUNT(pl.pelo_id), 0, 'false', 'true') FROM pedido_logistica pl, pedido_coleta pc WHERE pl.pecl_id = pc.pecl_id AND pl.stpl_id = 2 and pl.pelo_in_tipo = 2 AND pl.pecl_id =:idPedidoColeta", nativeQuery=true)
	Boolean existePedidoLogisticaDoadorFinalizado(@Param("idPedidoColeta") Long idPedidoColeta);
	
}

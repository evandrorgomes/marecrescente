package br.org.cancer.redome.workup.persistence;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.org.cancer.redome.workup.model.PedidoLogisticaMaterialColetaNacional;

/**
 * Repositório para acesso a base de dados ligadas a entidade PedidoLogisticaMaterial.
 * 
 * @author ergomes
 *
 */
@Repository
public interface IPedidoLogisticaMaterialColetaNacionalRepository extends IRepository<PedidoLogisticaMaterialColetaNacional, Long> {

	@Query(value = "SELECT decode(COUNT(pl.pelo_id), 0, 'false', 'true') FROM pedido_logistica pl, pedido_coleta pc WHERE pl.pecl_id = pc.pecl_id AND pl.stpl_id = 2 and pl.pelo_in_tipo in (3,4) AND pl.pecl_id =:idPedidoColeta AND pl.pelo_tx_justifica_nao_prosseguimento IS NOT NULL", nativeQuery=true)
	Boolean existePedidoLogisticaMaterialFinalizadoSemJustificativa(@Param("idPedidoColeta") Long idPedidoColeta);
	
}

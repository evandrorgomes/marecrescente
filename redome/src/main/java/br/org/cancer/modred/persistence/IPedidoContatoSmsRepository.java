package br.org.cancer.modred.persistence;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.PedidoContatoSms;

/**
 * Interface para acesso ao banco de dados envolvendo a classe PedidoContatoSms.
 * 
 * @author bruno.sousa
 *
 */
@Repository
public interface IPedidoContatoSmsRepository extends IRepository<PedidoContatoSms, Long> {

	/**
	 * Realiza a busca do pedido contato sms, a partir do idPedidoContato informado.
	 * 
	 * @param idPedidoContato identificação do pedido de contato.
	 * @return o pedido contato sms relacionado.
	 */
	@Query("select ps from PedidoContatoSms ps join ps.pedidoContato pc "
		 + "where pc.id = :idPedidoContato")
	PedidoContatoSms obterPedidoSmsPorIdPedidoContato(@Param("idPedidoContato") Long idPedidoContato);

}

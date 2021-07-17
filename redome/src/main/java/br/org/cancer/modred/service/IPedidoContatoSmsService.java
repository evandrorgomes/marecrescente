package br.org.cancer.modred.service;

import br.org.cancer.modred.model.PedidoContato;
import br.org.cancer.modred.model.PedidoContatoSms;
import br.org.cancer.modred.service.custom.IService;

/**
 * Interface de acesso as funcionalidades envolvendo a classe PedidoContatoSms.
 * 
 * @author bruno.sousa
 */
public interface IPedidoContatoSmsService extends IService<PedidoContatoSms, Long> {

	/**
	 * Cria o pedido de contato sms.
	 * 
	 * @param pedidoContato
	 */
	PedidoContatoSms criarPedidoContatoSms(PedidoContato pedidoContato);

	void finalizarPedidoContatoSmsInativandoDoador(Long idPedido);

	PedidoContatoSms obterPedidoContatoSmsPorId(Long id);

	Boolean existePedidoContatoSmsDentroDoPrazo(PedidoContato pedido);

	PedidoContatoSms finalizarPedidoContatoSms(Long idPedidoContatoSms);

	/**
	 * Método que atualiza o status de todos os sms enviados do pedido de contato informado pelo identificador.
	 * 
	 * @param id Identificador do pedido de contato sms.
	 */
	void atualizarStatusSmsEnviados(Long id);
}

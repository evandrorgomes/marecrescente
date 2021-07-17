package br.org.cancer.modred.service;

import br.org.cancer.modred.model.PedidoEnvioEmdis;
import br.org.cancer.modred.service.custom.IService;

/**
 * Interface de negócios de pedido de envio de dados para emdis.
 * @author Filipe Paes
 *
 */
public interface IPedidoEnvioEmdisService  extends IService<PedidoEnvioEmdis, Long>{
	
	
	/**
	 * Cria pedido de envio de paciente para o emdis.
	 * @param buscaId id da busca do paciente a ser enviado.
	 */
	void criarPedido(Long buscaId);

}

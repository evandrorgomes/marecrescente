package br.org.cancer.redome.workup.service;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.org.cancer.redome.workup.dto.SolicitacaoWorkupDTO;
import br.org.cancer.redome.workup.model.PedidoColeta;
import br.org.cancer.redome.workup.service.custom.IService;

/**
 * Interface de negócios para Distribuicao do Workup.
 * @author bruno.sousa
 *
 */
public interface IPedidoColetaService extends IService<PedidoColeta, Long> {

	/**
	 * Método para criar o pedido de coleta e a tarefa associada.
	 * 
	 * @param solicitacao Solicitação contendo as informações necessárias para criação do pedido de coleta.
	 */
	PedidoColeta criarPedidoColeta(SolicitacaoWorkupDTO solicitacao);
	    
	/**
	 * Método para obter o pedido de coleta por id do pedido.
	 * 
	 * @param idPedidoColeta - identificador do pedido de coleta.
	 */
	PedidoColeta obterPedidoColetaEDatasWorkupPorId(Long idPedidoColeta);

	/**
	 * Método para agendar o pedido de coleta.
	 * 
	 * @param idPedidoColeta - identificador do pedido de coleta.
	 * @param pedidoColeta - Objeto contendo as informações do pedido de coleta.
	 * 
	 * @return String - mensagem de sucesso da operação.
	 * 	 */
	String agendarPedidoColeta(Long idPedidoColeta, PedidoColeta pedidoColeta) throws JsonProcessingException;

	/**
	 * Método para obter o pedido de coleta por id do pedido.
	 * 
	 * @param idPedidoColeta - identificador do pedido de coleta.
	 */
	PedidoColeta obterPedidoColetaPorId(Long idPedidoColeta);
}

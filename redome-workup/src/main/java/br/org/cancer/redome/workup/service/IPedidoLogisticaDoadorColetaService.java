package br.org.cancer.redome.workup.service;

import br.org.cancer.redome.workup.dto.LogisticaDoadorColetaDTO;
import br.org.cancer.redome.workup.model.PedidoColeta;
import br.org.cancer.redome.workup.model.PedidoLogisticaDoadorColeta;
import br.org.cancer.redome.workup.service.custom.IService;

/**
 * Interface de negócios para Logistica de material.
 * @author ergomes
 *
 */
public interface IPedidoLogisticaDoadorColetaService extends IService<PedidoLogisticaDoadorColeta, Long> {

	/**
	 * Obter pedido logística doador coleta com status em aberto.  
	 * 
	 * @param id - identificação do pedido de logística.
	 * @return PedidoLogisticaDoadorColeta - Objeto do tipo PedidoLogisticaDoadorColeta. 
	 */
	PedidoLogisticaDoadorColeta obterPedidoLogisticaDoadorColetaEmAberto(Long id);

	/**
	 * Obter pedido logística doador coleta customizado.  
	 * 
	 * @param id - identificação do pedido de logística.
	 * @return LogisticaDoadorColetaDTO - Objeto do dto LogisticaDoadorColetaDTO. 
	 */
	LogisticaDoadorColetaDTO obterPedidoLogisticaDoadorColetaCustomizado(Long id);

	/**
	 * Método para atualizar o pedido de logistica de doador para coleta.
	 * 
	 * @param id Identificador do pedido de logistica. 
	 * @param logistica instancia da entidade LogisticaDoadorColetaDTO.
	 */
	void atualizarLogisticaDoadorColeta(Long id, LogisticaDoadorColetaDTO logistica);
	
	/**
	 * Encerra o pedido de logistica de doador para coleta e criar a taref de resultado de coleta para o centro de coleta.
	 * Cria log de evolução.
	 * 
	 * @param id Identificador do pedido de logistica.
	 * @param logistica instancia da entidade LogisticaDoadorColetaDTO.
	 */
	void encerrarLogisticaDoadorColeta(Long id, LogisticaDoadorColetaDTO logistica) throws Exception;
	
	/**
	 * Criar pedido de logistica de doador para coleta a partir do agendamente da coleta.
	 * 
	 * @param objeto identificador do pedidoColeta.  
	 * @return PedidoLogisticaDoadorColeta instância da entidade LogisticaDoadorColeta.
	 */
	PedidoLogisticaDoadorColeta criarPedidoLogisticaDoadorColeta(PedidoColeta pedidoColeta);
	
	
	boolean pedidoLogisticaDoadorEstaFinalizado(Long idPedidoColeta);
}


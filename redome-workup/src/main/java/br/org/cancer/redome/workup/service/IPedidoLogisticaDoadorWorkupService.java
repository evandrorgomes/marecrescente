package br.org.cancer.redome.workup.service;

import br.org.cancer.redome.workup.dto.LogisticaDoadorWorkupDTO;
import br.org.cancer.redome.workup.model.PedidoLogisticaDoadorWorkup;
import br.org.cancer.redome.workup.model.PedidoWorkup;
import br.org.cancer.redome.workup.service.custom.IService;

/**
 * Interface de negócios para Logistica de doador para Workup.
 * @author bruno.sousa
 *
 */
public interface IPedidoLogisticaDoadorWorkupService extends IService<PedidoLogisticaDoadorWorkup, Long> {

	/**
	 * Método para obter o pedido de logistica com status em aberto.
	 * 
	 * @param id identificador do pedido de logistica
	 * @return pedido de logistica encontrado ou throw a exception.
	 */
	PedidoLogisticaDoadorWorkup obterPedidoLogisticaEmAberto(Long id);

	
	/**
	 * Método para atualizar o pedido de logistica de doador para workup.
	 * 
	 * @param id Identificador do pedido de logistica. 
	 * @param logistica instancia da entidade LogisticaDoadorWorkupDTO.
	 */
	void atualizarLogisticaDoadorWorkup(Long id, LogisticaDoadorWorkupDTO logistica);


	/**
	 * Cria pedido de logística doador, associado a pedido de workup (logística para o doador).
	 * 
	 * @param pedidoWorkup pedido de workup, se for uma logística envolvendo workup.
	 * 
	 * @return o pedido de logística doador workup criado.
	 */
	PedidoLogisticaDoadorWorkup criarPedidoLogisticaDoadorWorkup(PedidoWorkup pedidoWorkup);

	/**
	 * Encerra o pedido de logistica de doador para workup e criar a taref de resultado de workup para o centro de coleta.
	 * Cria log de evolução.
	 * 
	 * @param id Identificador do pedido de logistica.
	 * @param logistica instancia da entidade LogisticaDoadorWorkupDTO.
	 */
	void encerrarLogisticaDoadorWorkup(Long id, LogisticaDoadorWorkupDTO logistica) throws Exception;


	/**
	 * Método para obter o pedido de logistica customizado.
	 * 
	 * @param id identificador do pedido de logistica
	 * @return LogisticaDoadorWorkupDTO do pedido de logistica.
	 */
	LogisticaDoadorWorkupDTO obterPedidoLogisticaCustomizado(Long id);

//	PedidoLogistica obterLogistica(Long idPedidoLogistica, TiposPedidoLogistica tipoLogistica);
}


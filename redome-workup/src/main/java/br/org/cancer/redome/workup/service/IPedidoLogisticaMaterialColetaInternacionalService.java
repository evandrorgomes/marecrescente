package br.org.cancer.redome.workup.service;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.org.cancer.redome.workup.dto.DetalheLogisticaInternacionalColetaDTO;
import br.org.cancer.redome.workup.model.PedidoLogisticaMaterialColetaInternacional;
import br.org.cancer.redome.workup.service.custom.IService;

/**
 * Interface de negócios para Logistica de material para Workup.
 * @author ergomes
 *
 */
public interface IPedidoLogisticaMaterialColetaInternacionalService extends IService<PedidoLogisticaMaterialColetaInternacional, Long> {

	/**
	 * Método para obter o pedido de logistica com status em aberto.
	 * 
	 * @param id identificador do pedido de logistica
	 * @return pedido de logistica encontrado ou throw a exception.
	 */
	PedidoLogisticaMaterialColetaInternacional obterPedidoLogisticaEmAberto(Long id);

	/**
	 * Método para obter o pedido de logistica com status em aberto.
	 * 
	 * @param id identificador do pedido de logistica
	 * @return pedido de logistica encontrado ou throw a exception.
	 */
	DetalheLogisticaInternacionalColetaDTO obterPedidoLogisticaMaterialColetaInternacional(Long idPedidoLogistica);

	/**
	 * Método para salvar pedido de logistica.
	 * 
	 * @param id identificador do pedido de logistica
	 * @param pedido de logistica DTO.
	 */
	void salvarPedidoLogisticaMaterialColetaInternacional(Long idPedidoLogistica, DetalheLogisticaInternacionalColetaDTO detalhe);

	/**
	 * Método para finalizar pedido de logistica.
	 * 
	 * @param id identificador do pedido de logistica
	 */
	void finalizarPedidoLogisticaMaterialColetaInternacional(Long idPedidoLogistica) throws JsonProcessingException;

	/**
	 * Método para criar pedido de logistica.
	 * 
	 * @param id identificador do pedido de logistica
	 */
	void criarPedidoLogisticaMaterialColetaInternacional(Long idPedidoColeta);
}


package br.org.cancer.modred.service;

import org.springframework.data.domain.PageRequest;

import br.org.cancer.modred.controller.dto.PedidoTransferenciaCentroDTO;
import br.org.cancer.modred.feign.dto.TarefaDTO;
import br.org.cancer.modred.helper.JsonViewPage;
import br.org.cancer.modred.model.PedidoTransferenciaCentro;
import br.org.cancer.modred.service.custom.IService;

/**
 * Interface de negócios para pedido de transferência de Centro.
 * 
 * @author brunosousa
 *
 */
public interface IPedidoTransferenciaCentroService extends IService<PedidoTransferenciaCentro, Long> {

	/**
	 * Método utilizado para transferir um paciente para outro centro avaliador. 
	 * 
	 * @param rmr - Identificador do paciente
	 * @param idCentroAvaliadorDestino - Identificador do centro avaliador de destino;
	 */
	void solicitarTransferenciaCentroAvaliador(Long rmr, Long idCentroAvaliadorDestino);

	/**
	 * Método utilizado para listar as tarefas de pedido de transferencia de centro avaliador.
	 * 
	 * @param atribuidosAMin
	 * @param idParceiro
	 * @param pageRequest
	 * @return Lista de tarefas de pedido de transferencia de centro.
	 */
	JsonViewPage<TarefaDTO> listarTarefas(Boolean atribuidosAMin, Long idParceiro, PageRequest pageRequest);

	/**
	 * Aceita o pedido de transferência de centro avaliador do paciente.
	 * A aprovação gera log na evolução do paciente, além de notificar o 
	 * médico sobre a aceitação. 
	 * 
	 * @param idPedidoTransferencia ID do pedido de transferência.
	 */
	void aceitarTransferencia(Long idPedidoTransferencia);
	
	/**
	 * Reprova o pedido de transferência de centro avaliador do paciente.
	 * A reprovação gera log na evolução do paciente, além de notificar o 
	 * médico. 
	 * 
	 * @param idPedidoTransferencia ID do pedido de transferência.
	 * @param justificativa - Justificativa do porque está sendo recusada a transferência.
	 */
	void reprovarTransferencia(Long idPedidoTransferencia, String justificativa);
	
	/**
	 * Obtém o pedido de transferência em aberto para o paciente informado.
	 * 
	 * @param rmr identificador do paciente.
	 * @return pedido de transferência em aberto.
	 */
	PedidoTransferenciaCentro obterPedidoNaoAvaliado(Long rmr);

	/**
	 * Método para obter o pedido de transferencia de centro por id.
	 * 
	 * @param idPedidoTransporteCentro
	 * @return PedidoTransferenciaCentro
	 */
	PedidoTransferenciaCentro obterPedidoTransferenciaCentro(Long idPedidoTransporteCentro);
	
	/**
	 * Método para obter o pedido de transferencia de centro DTO por id.
	 * 
	 * @param idPedidoTransporteCentro
	 * @return PedidoTransferenciaCentroDTO
	 */
	PedidoTransferenciaCentroDTO obterPedidoTransferenciaCentroDTO(Long idPedidoTransporteCentro);

}

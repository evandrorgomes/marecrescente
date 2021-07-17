package br.org.cancer.redome.courier.service;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.multipart.MultipartFile;

import br.org.cancer.redome.courier.controller.dto.ConfirmacaoTransporteDTO;
import br.org.cancer.redome.courier.controller.dto.DetalhePedidoTransporteDTO;
import br.org.cancer.redome.courier.controller.dto.PedidoTransporteDTO;
import br.org.cancer.redome.courier.controller.dto.TarefasPedidoTransporteDTO;
import br.org.cancer.redome.courier.model.PedidoTransporte;
import br.org.cancer.redome.courier.service.custom.IService;

/**
 * Interface para métodos de negocio do PedidoTransporte.
 * 
 * @author Bruno Sousa.
 */
public interface IPedidoTransporteService extends IService<PedidoTransporte, Long> {

	/**
	 * Lista as tarefas de agendamento do pedido de transporte.
	 * 
	 * @param pageRequest - paginação
	 * @return Lista com tarefas.
	 */
	Page<TarefasPedidoTransporteDTO> listarTarefas(PageRequest pageRequest) throws Exception;
	
	
	/**
	 * Lista os pedidos de transporte para o usuário logado e com os status:
	 * AGUARDANDO_DOCUMENTACAO, AGUARDANDO_RETIRADA, AGUARDANDO_ENTREGA
	 * 
	 * @param pageRequest - paginação
	 * @return lista dos pedidos de transporte.
	 */
	Page<TarefasPedidoTransporteDTO> listarPedidosTransporteEmAndamento(PageRequest pageRequest);

	
	/**
	 * Criar pedido de transporte.
	 * 
	 * @param pedidoTransporteDTO - dto do PedidoTransporte 
	 * @return PedidoTransporte - Objeto PedidoTransporte
	 */
	PedidoTransporte criarPedidoTransporte(PedidoTransporteDTO pedidoTransporteDTO);
	
	/**
	 * Obtém o detalhe do pedido de transporte.
	 * 
	 * 
	 * @param idPedidoTransporte Identificação do pedido de transporte
	 * @return DetalhePedidoTransporteDTO com os detalhes do pedido de transporte.
	 */
	DetalhePedidoTransporteDTO obterDetalhePedidoTransporte(Long idPedidoTransporte);
	
	/**
	 * Obter pedido de transporte por logistica e status transporte
	 * 
	 * @param idPedidoLogistica - identificador do pedido de logistica.
	 * @param idStatusTransporte - identificador do pedido de transporte.
	 * @return PedidoTransporte - objeto PedidoTransporte. 
	 */
	PedidoTransporteDTO obterPedidoTransportePorIdLogiticaEStatus(Long idPedidoLogistica, Long idStatusTransporte);

	/**
	 * Método para confirmar o agendamento do pedido de transporte
	 * 
	 * @param id Identificador do pedido de transporte
	 * @param confirmacaoTransporteDTO - informações referente se existe necessidade de aereo.
	 */
	void confirmarPedidoTransporte(Long id, ConfirmacaoTransporteDTO confirmacaoTransporteDTO);


	/**
	 * Obtem o pedido de transporte.
	 * 
	 * @param id Identificador do pedido de transporte
	 * @return Pedido de transporte ou exceção se não existir.
	 */
	PedidoTransporte obterPedidoTransportePorId(Long id);


	/**
	 * Obtém o carta para transporte de MO ou Cordão
	 * 
	 * @param idPedidoTransporte Identificador do pedido de transporte.
	 * @return Arquivo em pdf da carta.
	 */
	File obterCartaTransporte(Long idPedidoTransporte);


	/**
	 * Obtém o relatório para transporte de MO ou Cordão
	 * 
	 * @param idPedidoTransporte Identificador do pedido de transporte.
	 * @return Arquivo em pdf do relatório
	 */
	File obterRelatorioTransporte(Long idPedidoTransporte);


	/**
	 * Atualizar o pedido de transporte com arquivos cnts.
	 * 
	 * @param id Identificador do pedido de transporte
	 * @param dataPrevistaRetirada Data prevista para retirada.
	 * @param motivoAlteracaoCartaCnt Motivo da alteração da carta CNT.
	 * @param List<MultipartFile> de arquivos do pedido de transporte.
	 */
	void atualizarInformacoesTransporteAereo(Long idPedidoTransporte, LocalDateTime dataPrevistaRetirada, String descricaoAlteracao, List<MultipartFile> arquivos);

}

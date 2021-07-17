package br.org.cancer.redome.workup.service;

import org.springframework.data.domain.Page;

import br.org.cancer.redome.workup.dto.ConsultaTarefasAvaliacaoPedidoColetaDTO;
import br.org.cancer.redome.workup.model.AvaliacaoPedidoColeta;
import br.org.cancer.redome.workup.service.custom.IService;

/**
 * Interface de funcionalidades ligadas a avaliação de prescrição do paciente.
 * 
 * @author Queiroz
 *
 */
public interface IAvaliacaoPedidoColetaService extends IService<AvaliacaoPedidoColeta, Long> {

	Page<ConsultaTarefasAvaliacaoPedidoColetaDTO> listarTarefasAvaliacaoPedidoColeta(int pagina, int quantidadeRegistros);

	/**
	 * Confirma a avaliação de pedido de coleta, criando a tarefa para o analista de workup agendar o pedido de coleta.
	 * 
	 * @param idAvaliacaoResultadoWorkup Identificador da avaliação do resultado de workup. 
	 */
	void prosseguirPedidoColeta(Long idAvaliacaoResultadoWorkup);

	/**
	 * Confirma a avaliação do pedido de coleta e cancela o pedido de workup e a solicitação.
	 * 
	 * @param idAvaliacaoResultadoWorkup Identificador da avaliação do resultado de workup.
	 * @param justificativa Justificativa do médico do redome para não prosseguir mesmo o centro de transplante informar que deseja prossseguir.
	 */
	void naoProsseguirPedidoColeta(Long idAvaliacaoResultadoWorkup, String justificativa);

	
}

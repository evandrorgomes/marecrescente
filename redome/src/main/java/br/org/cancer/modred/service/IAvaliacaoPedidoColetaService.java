package br.org.cancer.modred.service;

import br.org.cancer.modred.model.AvaliacaoPedidoColeta;

/**
 * Interface de métodos de negócio para avalilação de pedido de coleta.
 * @author Filipe Paes
 *
 */
public interface IAvaliacaoPedidoColetaService {

	/**
	 * Método para gravação da avaliação do pedido de coleta. Caso o pedido seja
	 * reprovado o médico do CC e o analista de workup deve ser notificado e tudo que 
	 * diz respeito a este doador deve ser cancelado. Caso o pedido seja aprovado 
	 * deve ser dada continuidade ao fluxo de pedido de coleta.
	 * @param idAvaliacao - id da avaliação a ser atualizada
	 * @param avaliacaoPedidoColeta - avaliação a ser atualizada.
	 */
	void salvarAvaliacao(Long idAvaliacao, AvaliacaoPedidoColeta avaliacaoPedidoColeta);
	
	
	/**
	 * Obtem avaliação de pedido de coleta por id e atribui a tarefa para o médico do redome.
	 * @param idAvaliacaoPedidoColeta - id da avaliacao do pedido de coleta.
	 * @return avaliacao de pedido de coleta por id.
	 */
	AvaliacaoPedidoColeta obterAvaliacaoDePedidoDeColetaPorId(Long idAvaliacaoPedidoColeta);
}

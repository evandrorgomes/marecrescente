package br.org.cancer.modred.service;

import br.org.cancer.modred.model.Disponibilidade;
import br.org.cancer.modred.service.custom.IService;

/**
 * Interface de funcionalidades customizadas para a entidade Disponibilidade.
 * 
 * @author Pizão.
 *
 */
public interface IDisponibilidadeService extends IService<Disponibilidade, Long>{
	
	/**
	 * Obtém a última disponibilidade informada na comunicação realizada entre
	 * o centro transplantador e o analista de workup afim de fechar uma data para
	 * realização dos exames.
	 * 
	 * @param idPedidoWorkup ID do pedido de workup referente a negociação de datas.
	 * @return disponibilidade mais recente cadastrada.
	 */
	Disponibilidade obterUltimaDisponibilidade(Long idPedidoWorkup);
	
	/**
	 * Responde a disponibilidade sugerida pelo analista de workup para o workup ou coleta de um doador.
	 * O CT, além de concordar ou sugerir novas datas para o workup, pode sugerir ser
	 * o centro de coleta responsável pelo procedimento.
	 * 
	 * @param disponibilidadeAtualizada disponibilidade atualizada pelo centro.
	 * @return disponibilidade atualizada com a resposta.
	 */
	Disponibilidade responderDisponibilidade(Disponibilidade disponibilidadeAtualizada);

	
	/**
	 * Obtém a última disponibilidade informada na comunicação realizada entre
	 * o centro transplantador e o analista de workup afim de fechar uma data para
	 * realização da coelta.
	 * 
	 * @param idPedidoColeta
	 * @return Ultima disponibilidade se existir
	 */
	Disponibilidade obterUltimaDisponibilidadePedidoColeta(Long idPedidoColeta);

	/**
	 * Méto que inclui uma nova disponibilidade.
	 * 
	 * @param idPedidoWorkup - id do pedido de workup quando a disponibilidade for de workup
	 * @param dataSugerida - Texto com as datas sugeridas
	 */
	void incluirDisponibilidadeWorkup(Long idPedidoWorkup, String dataSugerida);
	
	
	/**
	 * Méto que inclui uma nova disponibilidade.
	 * 
	 * @param idPedidoColeta - id do pedido de coleta quando a disponibilidade for de coleta
	 * @param dataSugerida - Texto com as datas sugeridas
	 */
	void incluirDisponibilidadeColeta(Long idPedidoColeta, String dataSugerida);
	
}

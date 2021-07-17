package br.org.cancer.modred.service;

import java.util.List;

import br.org.cancer.modred.model.RespostaChecklist;

/**
 * Interface de métodos de netocio de resposta de Checklist.
 * Esta classe serve para armazenar as respostas de Checklist.
 * @author Filipe Paes
 *
 */
public interface IRespostaChecklistService {

	/**
	 * Obtem uma resposta de checklist de acordo com o item e com o id de pedido de logistica.
	 * @param idItem - item do checklist.
	 * @param idPedidoLogistica - id do pedido de logistica.
	 * @return resposta localizada.
	 */
	RespostaChecklist obterRespostaPor(Long idItem, Long idPedidoLogistica);
	
	/**
	 * Retorna a listagem de respostas de checklist por id de logística.
	 * @param idLogistica - id da logística.
	 * @return listagem de respostas de checklist.
	 */
	List<RespostaChecklist> obterRepostasPorIdPedidoLogistica(Long idLogistica);
}

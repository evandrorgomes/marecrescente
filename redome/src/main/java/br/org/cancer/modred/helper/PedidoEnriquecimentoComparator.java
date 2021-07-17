/**
 * 
 */
package br.org.cancer.modred.helper;

import java.util.Comparator;

import br.org.cancer.modred.feign.dto.TarefaDTO;

/**
 * Classe utilizada para ordernar a lista de tarefas do ENRIQUECEDOR_DOADOR.
 * 
 * @author Bruno sousa
 *
 */
public class PedidoEnriquecimentoComparator implements Comparator<TarefaDTO> {
	
	/**
	 * Regra ordenação: Pela data de criação. 
	 * 
	 */
	@Override
	public int compare(TarefaDTO tarefa1, TarefaDTO tarefa2) {
		if (tarefa2 == null) {
			return -1;
		}
		
		return tarefa1.getDataCriacao().compareTo(tarefa2.getDataCriacao());

	}
	
}

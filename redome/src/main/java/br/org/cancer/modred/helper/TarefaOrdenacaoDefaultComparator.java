/**
 * 
 */
package br.org.cancer.modred.helper;

import java.util.Comparator;

import br.org.cancer.modred.feign.dto.TarefaDTO;
import br.org.cancer.modred.model.domain.StatusTarefa;

/**
 * Classe para ordenação de tarefas considerando apenas o
 * status da tarefa.
 * A ordem considerada é: ATRIBUIDAS, ABERTAS, CONCLUÍDAS e CANCELADAS.
 * Esta é a ordenação default, caso nenhuma tenha sido informada.
 * 
 * @author Pizão
 *
 */
public class TarefaOrdenacaoDefaultComparator implements Comparator<TarefaDTO> {

	public TarefaOrdenacaoDefaultComparator() {
		super();
	}

	@Override
	public int compare(TarefaDTO tarefa1, TarefaDTO tarefa2) {
		final StatusTarefa statusTarefa1 = tarefa1.getStatusTarefa();
		final StatusTarefa statusTarefa2 = tarefa2.getStatusTarefa();
		return obterPrioridadePorStatus(statusTarefa1).compareTo(obterPrioridadePorStatus(statusTarefa2));
	}
	
	private Integer obterPrioridadePorStatus(StatusTarefa statusTarefa){
		switch (statusTarefa) {
		case ATRIBUIDA:
			return 0;
		case ABERTA:
			return 1;
		case FEITA:
			return 2;
		case CANCELADA:
			return 3;
		default:
			return 99;
		}
	}
}

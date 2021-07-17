/**
 * 
 */
package br.org.cancer.redome.workup.helper;

import java.util.Comparator;

import br.org.cancer.redome.workup.dto.TarefaDTO;

/**
 * Classe para comparar listas genéricas de tarefas.
 * 
 * @author Fillipe Queiroz
 *
 */
public class TarefaComparator implements Comparator<TarefaDTO> {

	public TarefaComparator() {
		super();
	}

	@Override
	public int compare(TarefaDTO tarefa1, TarefaDTO tarefa2) {
		return tarefa1.getDataCriacao().compareTo(tarefa2.getDataCriacao());		
	}

}

/**
 * 
 */
package br.org.cancer.redome.workup.helper;

import java.time.LocalDate;
import java.util.Comparator;

import br.org.cancer.redome.workup.dto.TarefaDTO;
import br.org.cancer.redome.workup.model.AvaliacaoPrescricao;

/**
 * Classe para comparar listas genéricas de tarefas.
 * 
 * @author Fillipe Queiroz
 *
 */
public class AvaliacaoPrescricaoComparator implements Comparator<TarefaDTO> {

	public AvaliacaoPrescricaoComparator() {
		super();
	}

	@Override
	public int compare(TarefaDTO tarefa1, TarefaDTO tarefa2) {
		
		AvaliacaoPrescricao avaliacaoPrescricaoTarefa1 = (AvaliacaoPrescricao) tarefa1.getObjetoRelacaoEntidade();
		if(avaliacaoPrescricaoTarefa1 == null){
			return 0;
		}
		LocalDate menorDataTarefa1 = avaliacaoPrescricaoTarefa1.getPrescricao().menorDataColeta(); 

		AvaliacaoPrescricao avaliacaoPrescricaoTarefa2 = (AvaliacaoPrescricao) tarefa2.getObjetoRelacaoEntidade();
		if(avaliacaoPrescricaoTarefa2 == null){
			return 0;
		}
		LocalDate menorDataTarefa2 = avaliacaoPrescricaoTarefa2.getPrescricao().menorDataColeta();

		return menorDataTarefa1.compareTo(menorDataTarefa2);
		
	}

}

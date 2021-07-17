/**
 * 
 */
package br.org.cancer.modred.helper;

import java.time.LocalDate;
import java.util.Comparator;

import br.org.cancer.modred.feign.dto.TarefaDTO;
import br.org.cancer.modred.model.AvaliacaoPrescricao;

/**
 * Classe para comparar listas genéricas de tarefas.
 * 
 * @author Fillipe Queiroz
 *
 */
public class PrescricaoComparator implements Comparator<TarefaDTO> {

	public PrescricaoComparator() {
		super();
	}

	@Override
	public int compare(TarefaDTO tarefa1, TarefaDTO tarefa2) {
		if (tarefa1.getObjetoRelacaoEntidade() instanceof AvaliacaoPrescricao) {
			AvaliacaoPrescricao avaliacaoPrescricaoTarefa1 = (AvaliacaoPrescricao) tarefa1.getObjetoRelacaoEntidade();
			if(avaliacaoPrescricaoTarefa1 == null){
				return 0;
			}
			LocalDate menorDataTarefa1 = recuperarMenorDataResultadoWorkup(avaliacaoPrescricaoTarefa1);

			AvaliacaoPrescricao avaliacaoPrescricaoTarefa2 = (AvaliacaoPrescricao) tarefa2.getObjetoRelacaoEntidade();
			if(avaliacaoPrescricaoTarefa2 == null){
				return 0;
			}
			LocalDate menorDataTarefa2 = recuperarMenorDataResultadoWorkup(avaliacaoPrescricaoTarefa2);

			return menorDataTarefa1.compareTo(menorDataTarefa2);
		}

		return 0;
	}

	private LocalDate recuperarMenorDataResultadoWorkup(AvaliacaoPrescricao avaliacaoPrescricao) {
		if (avaliacaoPrescricao.getPrescricao().getDataResultadoWorkup1().compareTo(avaliacaoPrescricao.getPrescricao()
				.getDataResultadoWorkup2()) < 1) {
			return avaliacaoPrescricao.getPrescricao().getDataResultadoWorkup1();
		}
		return avaliacaoPrescricao.getPrescricao().getDataResultadoWorkup2();
	}

}

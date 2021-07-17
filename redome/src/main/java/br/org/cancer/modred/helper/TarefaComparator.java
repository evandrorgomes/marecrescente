/**
 * 
 */
package br.org.cancer.modred.helper;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang3.builder.CompareToBuilder;

import br.org.cancer.modred.exception.BusinessException;
import br.org.cancer.modred.feign.dto.TarefaDTO;
import br.org.cancer.modred.model.AtributoOrdenacao;
import br.org.cancer.modred.util.AppUtil;

/**
 * Classe para comparar listas genéricas de tarefas.
 * 
 * @author Fillipe Queiroz
 *
 */
public class TarefaComparator implements Comparator<TarefaDTO> {

	private List<AtributoOrdenacao> atributos;

	public TarefaComparator() {
		super();
	}

	public TarefaComparator(AtributoOrdenacao... atributos) {
		super();
		this.atributos = Arrays.asList(atributos);
	}
	
	public TarefaComparator(List<AtributoOrdenacao> atributos) {
		super();
		this.atributos = atributos;
	}

	@Override
	public int compare(TarefaDTO tarefa1, TarefaDTO tarefa2) {
		CompareToBuilder compareToBuilder = new CompareToBuilder();

		for (AtributoOrdenacao atributoOrdenacao : atributos) {
			if (atributoOrdenacao == null || "".equals(atributoOrdenacao)) {
				throw new BusinessException("ordenacao.atributo.invalido");
			}
			Object valorCampo1 = null;
			Object valorCampo2 = null;
			if (atributoOrdenacao.getNomeAtributo().contains(".")) {
				valorCampo1 = recuperarValorDeEntidadeFilha(tarefa1, atributoOrdenacao
						.getNomeAtributo());
				valorCampo2 = recuperarValorDeEntidadeFilha(tarefa2, atributoOrdenacao
						.getNomeAtributo());
			}
			else {
				valorCampo1 = AppUtil.callGetter(tarefa1, atributoOrdenacao.getNomeAtributo());
				valorCampo2 = AppUtil.callGetter(tarefa2, atributoOrdenacao.getNomeAtributo());
			}

			if (atributoOrdenacao.isAsc()) {
				compareToBuilder.append(valorCampo1, valorCampo2);
			}
			else {
				compareToBuilder.append(valorCampo2, valorCampo1);
			}

		}
		return compareToBuilder.toComparison();
	}

	private Object recuperarValorDeEntidadeFilha(TarefaDTO tarefa1, String nomeAtributo) {
		List<String> atributos = AppUtil.splitReturnString(nomeAtributo, "\\.");
		Object objeto = tarefa1;
		int qtdAtributos = atributos.size();
		for (int i = 0; i < atributos.size(); i++) {
			Object valorCampo1 = AppUtil.callGetter(objeto, atributos.get(i));
			if (entidadeNula(qtdAtributos, i, valorCampo1)) {
				throw new BusinessException("ordenacao.atributo.invalido");
			}
			objeto = valorCampo1;
		}
		return objeto;

	}

	private boolean entidadeNula(int qtdAtributos, int posicao, Object valorCampo1) {
		boolean naoEhUltimoNivel = posicao != qtdAtributos;
		return valorCampo1 == null && naoEhUltimoNivel;
	}

}

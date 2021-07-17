package br.org.cancer.modred.model.domain;

import java.util.Comparator;
import java.util.List;

import br.org.cancer.modred.feign.dto.TarefaDTO;
import br.org.cancer.modred.helper.PedidoColetaComparator;
import br.org.cancer.modred.helper.PedidoWorkupComparator;
import br.org.cancer.modred.helper.PrescricaoComparator;
import br.org.cancer.modred.helper.TarefaComparator;
import br.org.cancer.modred.model.AtributoOrdenacao;

/**
 * Enum para descrever campos de ordenação para consulta de buscas.
 * 
 * @author Filipe Paes
 *
 */
public enum OrdenacaoTarefa {
	DEFAULT(0),
	PRESCRICAO(1),
	PEDIDO_WORKUP(2),
	PEDIDO_COLETA(3);

	private Integer chave;

	OrdenacaoTarefa(Integer chave) {
		this.chave = chave;
	}

	/**
	 * @return the chave
	 */
	public Integer getChave() {
		return chave;
	}

	/**
	 * Retorna o comparador.
	 * 
	 * @param chave - chave do comparador
	 * @param atributos - atributos a serem ordenados
	 * @return novo comparador.
	 */
	public static Comparator<TarefaDTO> getComparator(Integer chave, List<AtributoOrdenacao> atributos) {
		if (OrdenacaoTarefa.PRESCRICAO.getChave() == chave) {
			return new PrescricaoComparator();
		}
		else if (OrdenacaoTarefa.PEDIDO_WORKUP.getChave() == chave) {
			return new PedidoWorkupComparator();
		}		
		else if (OrdenacaoTarefa.PEDIDO_COLETA.getChave() == chave) {
			return new PedidoColetaComparator();
		}
		return new TarefaComparator(atributos);

	}

}

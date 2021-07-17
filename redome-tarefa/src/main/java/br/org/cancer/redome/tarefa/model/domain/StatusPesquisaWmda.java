package br.org.cancer.redome.tarefa.model.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Enum para status da pesquisa wmda.
 * 
 * @author ergomes
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum StatusPesquisaWmda {
	ABERTO(1),
	PROCESSADO(2),
	PROCESSADO_COM_ERRO(3),
	CANCELADO(4);

	private Integer id;

	/**
	 * Método estatico para o obter a descrição por id. 
	 * 
	 * @param id id do status da pesquisa wmda
	 * @return string
	 */
	public static String getDescricaoPorId(Integer id) {
		if (id == null) {
			return null;
		}
		for (int contador = 0; contador < StatusPesquisaWmda.values().length; contador++) {
			if (StatusPesquisaWmda.values()[contador].getId().equals(id)) {
				return StatusPesquisaWmda.values()[contador].name();
			}
		}
		
		return null;
		
	}
	
	
}

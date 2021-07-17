package br.org.cancer.redome.feign.client.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Enum para status de solicitacoes.
 * 
 * @author ergomes
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum StatusSolicitacao {
	ABERTA(1),
	CONCLUIDA(2),
	CANCELADA(3);

	private Integer id;

	/**
	 * Método estatico para o obter a descrição por id. 
	 * 
	 * @param id id do status da solicitacao
	 * @return string
	 */
	public static String getDescricaoPorId(Integer id) {
		if (id == null) {
			return null;
		}
		for (int contador = 0; contador < StatusSolicitacao.values().length; contador++) {
			if (StatusSolicitacao.values()[contador].getId().equals(id)) {
				return StatusSolicitacao.values()[contador].name();
			}
		}
		
		return null;
		
	}
	
	
}

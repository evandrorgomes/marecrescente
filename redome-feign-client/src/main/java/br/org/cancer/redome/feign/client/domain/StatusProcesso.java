package br.org.cancer.redome.feign.client.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enum para representar os possíveis estados de um processo de busca de doador compatível.
 * 
 * @author Thiago Moraes
 *
 */
@AllArgsConstructor
public enum StatusProcesso {
	ANDAMENTO(1L),
	PARADO(2L),
	TERMINADO(3L),
	CANCELADO(4L);

	@Getter
	private Long id;


}

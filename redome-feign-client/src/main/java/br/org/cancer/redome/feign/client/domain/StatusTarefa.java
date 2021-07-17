package br.org.cancer.redome.feign.client.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enum para representar os possíveis estados de uma tarefa dentro de um determinado processo de negócio.
 * 
 * @author Thiago Moraes
 *
 */
@AllArgsConstructor
public enum StatusTarefa {
	ABERTA(1L),
	ATRIBUIDA(2L),
	FEITA(3L),
	AGUARDANDO(4L),
	CANCELADA(5L);

	@Getter
	private Long id;

}

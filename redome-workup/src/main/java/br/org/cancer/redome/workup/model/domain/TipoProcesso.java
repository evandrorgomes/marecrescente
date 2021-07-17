package br.org.cancer.redome.workup.model.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enum para representar os tipos de processos dentro da plataforma redome.
 * 
 * @author Thiago Moraes
 *
 */
@AllArgsConstructor
public enum TipoProcesso {
	BUSCA(5L);

	@Getter
	private Long id;



}

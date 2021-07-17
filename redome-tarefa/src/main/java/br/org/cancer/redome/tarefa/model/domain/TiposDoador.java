package br.org.cancer.redome.tarefa.model.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum TiposDoador {

	NACIONAL(0L),
	INTERNACIONAL(1L),
	CORDAO_NACIONAL(2L),
	CORDAO_INTERNACIONAL(3L);

	@Getter
	private Long id;

}

package br.org.cancer.redome.workup.model.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TiposPedidosWorkup {
	
	NACIONAL(0L),
	INTERNACIONAL(1L);
	
	private Long id;

}

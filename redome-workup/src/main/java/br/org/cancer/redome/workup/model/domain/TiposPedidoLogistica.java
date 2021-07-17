package br.org.cancer.redome.workup.model.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TiposPedidoLogistica {
	
	DOADOR_WORKUP(1),
	DOADOR_COLETA(2),
	MATERIAL_NACIONAL(3),
	MATERIAL_INTERNACIONAL(4);
		
	private Integer id;
}
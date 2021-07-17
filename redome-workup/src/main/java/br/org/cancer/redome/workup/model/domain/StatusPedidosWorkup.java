package br.org.cancer.redome.workup.model.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StatusPedidosWorkup {
	
	INICIADO(1L),
	FINALIZADO(2L),
	CANCELADO(3L);
	
	private Long id;

}

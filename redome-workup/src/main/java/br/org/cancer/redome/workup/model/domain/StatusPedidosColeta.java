package br.org.cancer.redome.workup.model.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StatusPedidosColeta {
	
	INICIADO(1L),
	AGENDADO(2L),
	CANCELADO(3L);
	
	private Long id;

}

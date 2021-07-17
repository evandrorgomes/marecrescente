package br.org.cancer.redome.workup.model.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum TiposPrescricao {
	
	PRESCRICAO_MEDULA(0L),
	PRESCRICAO_CORDAO(1l);
	
	@Getter
	private Long id;
	
	

}

package br.org.cancer.redome.workup.model.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum TiposDistribuicaoWorkup {
	
	NACIONAL(0L),
	INTERNACIONAL(1l);
	
	@Getter
	private Long id;
	
	

}

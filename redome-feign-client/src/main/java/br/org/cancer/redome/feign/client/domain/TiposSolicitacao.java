package br.org.cancer.redome.feign.client.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum TiposSolicitacao {
	
	WORKUP_DOADOR_NACIONAL_PACIENTE_NACIONAL(8L),
	WORKUP_DOADOR_INTERNACIONAL(9L),
	CORDAO_NACIONAL_PACIENTE_NACIONAL(10L),
	CORDAO_INTERNACIONAL(11l);
	
	@Getter
	private Long id;

}

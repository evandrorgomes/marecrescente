package br.org.cancer.redome.workup.model.domain;

import java.util.stream.Stream;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enum de perfis do sistema.
 * 
 * @author Cintia Oliveira
 *
 */
@AllArgsConstructor
public enum Perfis {
	
	ANALISTA_WORKUP(9L),
	MEDICO_REDOME(13L),	
	ANALISTA_WORKUP_INTERNACIONAL(20L),
	MEDICO_TRANSPLANTADOR(15L),
	DISTRIBUIDOR_WORKUP_NACIONAL(30L),
	DISTRIBUIDOR_WORKUP_INTERNACIONAL(31L),
	MEDICO_CENTRO_COLETA(32L),
	ANALISTA_LOGISTICA(12L),
	ANALISTA_LOGISTICA_INTERNACIONAL(22L),
	TRANSPORTADORA(17L); 

	@Getter
	private Long id;

	public static Perfis valueOf(Long id) {
		if (id == null) {
			return null;
		}
		return Stream.of(Perfis.values()).filter(perfil -> perfil.getId().equals(id)).findFirst().orElse(null);
	}

}
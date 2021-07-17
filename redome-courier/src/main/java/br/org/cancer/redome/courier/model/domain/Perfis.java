package br.org.cancer.redome.courier.model.domain;

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
	
	TRANSPORTADORA(17L),
	ANALISTA_LOGISTICA(12L);

	@Getter
	private Long id;

	public static Perfis valueOf(Long id) {
		if (id == null) {
			return null;
		}
		return Stream.of(Perfis.values()).filter(perfil -> perfil.getId().equals(id)).findFirst().orElse(null);
	}

}
package br.org.cancer.redome.courier.model.domain;

import java.util.stream.Stream;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum TiposTarefa {
	
	ANALISAR_PEDIDO_TRANSPORTE(55L),
	INFORMAR_RETIRADA_MATERIAL(56L),
	INFORMAR_DOCUMENTACAO_MATERIAL_AEREO(42L);
	
	@Getter
	private Long id;
	
	
	public static TiposTarefa valueOf(Long id) {
		if (id == null) {
			return null;
		}
		return Stream.of(TiposTarefa.values()).filter(tipo -> tipo.getId().equals(id)).findFirst().orElse(null);
	}
	

}

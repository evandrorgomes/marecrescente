package br.org.cancer.redome.courier.controller.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enum para retorno de inclusão.
 * 
 * @author brunosousa
 *
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum StatusRetornoInclusaoDTO {
	
	SUCESSO(1L),
	FALHA(2L);
	
	private Long id;
		
	/**
	 * Método para criar o tipo enum a partir de seu valor numerico.
	 * 
	 * @param value - valor do id do tipo enum
	 * 
	 * @return StatusRetornoInclusaoDTO - a enum correspondente ou nulo caso value estaja fora do range conhecido.
	 */
	public static StatusRetornoInclusaoDTO valueOf(Long value) {

		StatusRetornoInclusaoDTO[] values = StatusRetornoInclusaoDTO.values();
		for (int i = 0; i < values.length; i++) {
			if (values[i].getId().equals(value)) {
				return values[i];
			}
		}
		return null;
	}
	
	@Override
	public String toString() {
		return this.getId()+"";
	}

	
}

package br.org.cancer.modred.model.domain;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Enum para filtro do match.
 * @author bruno.sousa
 *
 */
public enum FiltroMatch {
	
	MEDULA(0L, TiposDoador.NACIONAL, TiposDoador.INTERNACIONAL),
	CORDAO(1L, TiposDoador.CORDAO_NACIONAL, TiposDoador.CORDAO_INTERNACIONAL);
	
	private Long id;
	private List<TiposDoador> tiposDoadorAssociados;
	
	FiltroMatch(Long id, TiposDoador... tiposDoadorAssociados) {
		this.id = id;
		this.tiposDoadorAssociados = Arrays.asList(tiposDoadorAssociados);
	}
	
	FiltroMatch() {}
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	
	/**
	 * Método para criar o tipo enum a partir de seu valor numerico.
	 * 
	 * @param value - valor do id do tipo enum
	 * 
	 * @return FiltroMatch - a enum correspondente ou nulo caso value estaja fora do range conhecido.
	 */
	public static FiltroMatch valueOf(Long value) {

		FiltroMatch[] values = FiltroMatch.values();
		for (int i = 0; i < values.length; i++) {
			if (values[i].getId().equals(value)) {
				return values[i];
			}
		}
		return null;
	}

	/**
	 * Retorna quais são os tipos de doador associados ao filtros de match.
	 * @return lista de tipos doador associados.
	 */
	public List<TiposDoador> getTiposDoadorAssociados() {
		return tiposDoadorAssociados;
	}
	
	/**
	 * Retorna quais são os tipos de doador associados ao filtros de match.
	 * @return lista de tipos doador associados.
	 */
	public List<Long> getIdsTiposDoadorAssociados() {
		return getTiposDoadorAssociados().stream().map(tipo -> tipo.getId()).collect(Collectors.toList());
	}


}

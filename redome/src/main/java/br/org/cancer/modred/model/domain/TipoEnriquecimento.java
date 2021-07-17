package br.org.cancer.modred.model.domain;

/**
 * Enum para representar os tipos de enriquecimento.
 * 
 * @author bruno.sousa
 *
 */
public enum TipoEnriquecimento {
	FASE2(1L),
	FASE3(2L);

	private Long id;

	TipoEnriquecimento(Long id) {
		this.id = id;
	}

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
	 * @return TipoEnriquecimento - a enum correspondente ou nulo caso value estaja fora do range conhecido.
	 */
	public static TipoEnriquecimento valueOf(Long value) {

		TipoEnriquecimento[] values = TipoEnriquecimento.values();
		for (int i = 0; i < values.length; i++) {
			if (values[i].getId().equals(value)) {
				return values[i];
			}
		}
		return null;
	}

	/**
	 * Método para verificar se o id está dentro do range previsto.
	 * 
	 * @return boolean - returna <b>true</b> se o id corresponde a um valor válido da enum, caso contrário, retorna <b>false</b>.
	 */
	public boolean validate() {

		TipoEnriquecimento[] values = TipoEnriquecimento.values();
		for (int i = 0; i < values.length; i++) {
			if (values[i].getId().equals(id)) {
				return true;
			}
		}
		return false;
	}

}

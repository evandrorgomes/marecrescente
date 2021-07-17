package br.org.cancer.modred.model.domain;

/**
 * Classe que representa os tipos de perguntas.
 * 
 * @author bruno.sousa
 *
 */
public enum TiposPergunta {
	
	TEXT(1L),
    TEXTAREA(2L),
    RADIOBUTTON(3L),
    CHECKBUTTON(4L),
    SELECT(5L),
    NUMERIC(6L),
    DATE(7L);
	
	private Long id;
	
	/**
	 * Construtor Sobrecarregado com id.
	 * 
	 * @param id
	 */
	TiposPergunta(Long id) {
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
	 * @return TiposPergunta - a enum correspondente ou nulo caso value estaja fora do range conhecido.
	 */
	public static TiposPergunta valueOf(Long value) {

		TiposPergunta[] values = TiposPergunta.values();
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

		TiposPergunta[] values = TiposPergunta.values();
		for (int i = 0; i < values.length; i++) {
			if (values[i].getId().equals(id)) {
				return true;
			}
		}
		return false;
	}

}

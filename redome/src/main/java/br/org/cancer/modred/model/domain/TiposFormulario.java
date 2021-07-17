package br.org.cancer.modred.model.domain;

/**
 * Enum para representar os tipos de formulários.
 * 
 * @author bruno.sousa
 *
 */
public enum TiposFormulario {
	FASE2(1L,"SEMPAGINACAO"),
	FASE3(2L, "SEMPAGINACAO"),
	RECEPTIVIDADE_WORKUP(3L, "PAGINACAO"),
	RESULTADO_WORKUP(4L, "SECAO"),
	POSCOLETA_MEDULA(5L, "SEMPAGINACAO"),
	POSCOLETA_AFERESE(6L, "SEMPAGINACAO");	
	
	private Long id;
	private String tipoPaginacao;
	
	TiposFormulario(Long id, String tipo) {
		this.id = id;
		this.tipoPaginacao = tipo;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @return the tipoPaginacao
	 */
	public String getTipoPaginacao() {
		return tipoPaginacao;
	}

	/**
	 * Método para criar o tipo enum a partir de seu valor numerico.
	 * 
	 * @param value - valor do id do tipo enum
	 * 
	 * @return TiposFormulario - a enum correspondente ou nulo caso value estaja fora do range conhecido.
	 */
	public static TiposFormulario valueOf(Long value) {

		TiposFormulario[] values = TiposFormulario.values();
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

		TiposFormulario[] values = TiposFormulario.values();
		for (int i = 0; i < values.length; i++) {
			if (values[i].getId().equals(id)) {
				return true;
			}
		}
		return false;
	}
}

package br.org.cancer.modred.model.domain;

/**
 * Representa dos valores possíveis da classe Idioma.
 * 
 * @author Cintia Oliveira
 *
 */
public enum Idiomas {
	PT(1),
	EN(2),
	ES(3);

	private Integer id;

	Idiomas(Integer id) {
		this.id = id;
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

}
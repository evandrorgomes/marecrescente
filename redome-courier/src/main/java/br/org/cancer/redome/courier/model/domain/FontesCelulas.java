package br.org.cancer.redome.courier.model.domain;

/**
 * Enum para fontes de celulas.
 * 
 * @author ergomes
 *
 */
public enum FontesCelulas {
	MEDULA_OSSEA(1L, "MO", "MEDULA OSSEA"),
	SANGUE_PERIFERICO(2L, "SP", "SANGUE PERIFERICO"),
	LINFOCITOS(3L, "DLI", "LINFOCITOS"),
	CORDAO_UMBILICAL(4L, "CO", "CORDAO UMBILICAL");

	private Long id;
	private String sigla;
	private String descricao;

	/**
	 * Construtor com parametros.
	 * 
	 * @param id - identificador do registro de fonte celula.
	 * @param sigla - sigla
	 * @param descricao - descricao
	 */
	FontesCelulas(Long id, String sigla, String descricao) {
		this.id = id;
		this.sigla = sigla;
		this.descricao = descricao;
	}

	/**
	 * @return the Fontes Celulas
	 */
	public Long getFonteCelulaId() {
		return id;
	}

	/**
	 * @return the sigla
	 */
	public String getSigla() {
		return sigla;
	}

	/**
	 * @return the descricao
	 */
	public String getDescricao() {
		return descricao;
	}

}

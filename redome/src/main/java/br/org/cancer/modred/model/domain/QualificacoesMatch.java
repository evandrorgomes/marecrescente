package br.org.cancer.modred.model.domain;

import br.org.cancer.modred.model.interfaces.EnumType;

/**
 * Enum para tipos de qualificações de match.
 * 
 * @author Fillipe Queiroz
 *
 */
public enum QualificacoesMatch implements EnumType<String>{

	MISMATCH("M"),
	POTENCIAL("P"),
	MATCH_ALELICO("A"),
	MISMATCH_ALELICO("L");

	private String codigo;

	/**
	 * Construtor com parametros.
	 * 
	 * @param codigo - codigo
	 */
	QualificacoesMatch(String codigo) {
		this.codigo = codigo;
	}

	@Override
	public String getId() {
		return codigo;
	}

}

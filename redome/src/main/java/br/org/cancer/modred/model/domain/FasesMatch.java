package br.org.cancer.modred.model.domain;

import br.org.cancer.modred.model.interfaces.EnumType;

/**
 * Enum para tipos de qualificações de match.
 * 
 * @author Fillipe Queiroz
 *
 */
public enum FasesMatch implements EnumType<String>{

	FASE_1("F1"),
	FASE_2("F2"),
	FASE_3("F3"),
	EXAME_EXTENDIDO("EX"),
	TESTE_CONFIRMATORIO("TC"),
	DISPONIBILIZADO("D");

	private String chave;

	/**
	 * Construtor com parametros.
	 * 
	 * @param chave - chave
	 */
	FasesMatch(String chave) {
		this.chave = chave;
	}

	/**
	 * @return the chave
	 */
	public String getChave() {
		return chave;
	}

	@Override
	public String getId() {
		return chave;
	}
	
	/**
	 * Método para criar o tipo enum a partir de seu valor numerico.
	 * 
	 * @param value - valor do id do tipo enum
	 * 
	 * @return FiltroMatch - a enum correspondente ou nulo caso value estaja fora do range conhecido.
	 */
	public static FasesMatch valueById(String value) {

		FasesMatch[] values = FasesMatch.values();
		for (int i = 0; i < values.length; i++) {
			if (values[i].getId().equals(value)) {
				return values[i];
			}
		}
		return null;
	}

}

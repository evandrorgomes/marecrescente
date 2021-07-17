package br.org.cancer.modred.model.domain;

import br.org.cancer.modred.model.interfaces.EnumType;


/**
 * Enum para status de um exame.
 * 
 * @author Fillipe Queiroz
 *
 */
public enum StatusExame implements EnumType<Integer>{
	NAO_VERIFICADO(0),
	CONFERIDO(1),
	DESCARTADO(2);

	private Integer codigo;

	StatusExame(Integer codigo) {
		this.codigo = codigo;
	}

	/**
	 * @return the id
	 */
	public Integer getCodigo() {
		return codigo;
	}

	@Override
	public Integer getId() {
		return codigo;
	}
}

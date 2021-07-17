package br.org.cancer.modred.model.domain;

import br.org.cancer.modred.model.interfaces.EnumType;

/**
 * Enum para tipos de sexo.
 * 
 * @author Filipe Paes
 *
 */
public enum Sexo implements EnumType<String> {
    MASCULINO("M"),
    FEMININO("F");

    private String sexo;

    Sexo(String sexo) {
        this.sexo = sexo;
    }

    /**
     * @return the sexo
     */
    public String getSexo() {
        return sexo;
    }

	@Override
	public String getId() {
		return sexo;
	}
}

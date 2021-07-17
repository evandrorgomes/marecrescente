package br.org.cancer.modred.model.domain;

import br.org.cancer.modred.model.interfaces.EnumType;

/**
 * Enum para tipos de ABO.
 * 
 * @author Filipe Paes
 *
 */
public enum Abo implements EnumType<String>{
    A_POSITIVO("A+"),
    B_POSITIVO("B+"),
    A_NEGATIVO("A-"),
    B_NEGATIVO("B-"),
    AB_POSITIVO("AB+"),
    AB_NEGATIVO("AB-"),
    O_POSITIVO("O+"),
    O_NEGATIVO("O-"),
    NI("NI");

    private String abo;

    Abo(String abo) {
        this.abo = abo;
    }

    /**
     * @return the abo
     */
    public String getAbo() {
        return abo;
    }

	@Override
	public String getId() {
		return abo;
	}
}

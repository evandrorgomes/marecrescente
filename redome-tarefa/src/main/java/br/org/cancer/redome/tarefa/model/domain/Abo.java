package br.org.cancer.redome.tarefa.model.domain;

/**
 * Enum para tipos de ABO.
 * 
 * @author Filipe Paes
 *
 */
public enum Abo {
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
    
	public static String obterValorAboNormalizado(String rh, String abo) {
		if(abo == null || rh == null) {
			return null;
		}
		switch (abo) {
			case "A": return rh.equalsIgnoreCase("P") ? Abo.A_POSITIVO.getAbo() : Abo.A_NEGATIVO.getAbo(); 
			case "B": return rh.equalsIgnoreCase("P") ? Abo.B_POSITIVO.getAbo() : Abo.B_NEGATIVO.getAbo(); 
			case "AB": return rh.equalsIgnoreCase("P") ? Abo.AB_POSITIVO.getAbo() : Abo.AB_NEGATIVO.getAbo();
			case "O": return rh.equalsIgnoreCase("P") ? Abo.O_POSITIVO.getAbo() : Abo.O_NEGATIVO.getAbo();
			default: return abo;
		}
	}


}

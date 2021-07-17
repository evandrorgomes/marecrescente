package br.org.cancer.modred.model.domain;

/**
 * Enum para tipos de sexo.
 * 
 * @author Filipe Paes
 *
 */
public enum TipoBoolean {
	NAO("N",false),
    SIM("S",true);

	private String sigla;
    private boolean tipo;

    TipoBoolean(String sigla, boolean tipo) {
    	this.sigla = sigla;
        this.tipo = tipo;
    }
    
	/**
	 * @return the sigla
	 */
	public String getSigla() {
		return sigla;
	}

	/**
	 * @return the tipo
	 */
	public boolean isTipo() {
		return tipo;
	}
    
}

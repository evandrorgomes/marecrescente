package br.org.cancer.modred.model.domain;

/**
 * Enum para os tipos de instrução de um laboratório.
 * 
 * 
 * @author Bruno Sousa
 *
 */
public enum TiposInstrucaoColeta {
	EXAME("Sangue"),
	SWAB("Swab Oral");

	private String tipoColeta;

	/**
	 * @param tipoColeta
	 */
	TiposInstrucaoColeta(String tipoColeta) {
		this.tipoColeta = tipoColeta;
	}

	/**
	 * @return the tipoColeta
	 */
	public String getTipoColeta() {
		return tipoColeta;
	}
	
}

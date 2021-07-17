package br.org.cancer.modred.model.domain;

/**
 * Enum para referencia de grupos alélicos.
 * @author Filipe Paes
 */
public enum GruposAlelicos {
	G("G")
	, P("P")
	, NMDP("NMDP"), DNA("DNA"), BLANK("-"),NOVO("NOVO");
	
	private String nomeGrupo;
	
	GruposAlelicos(String grupo) {
		this.nomeGrupo = grupo;
	}
	
	/**
	 * @return the nomeGrupo
	 */
	public String getNomeGrupo() {
		return nomeGrupo;
	}
	
	/**
	 * @param nomeGrupo the nomeGrupo to set
	 */
	public void setNomeGrupo(String nomeGrupo) {
		this.nomeGrupo = nomeGrupo;
	}
	
}

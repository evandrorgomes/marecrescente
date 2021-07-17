/**
 * 
 */
package br.org.cancer.modred.model;

import java.io.Serializable;

/**
 * @author fillipe.queiroz
 *
 */
public class AtributoOrdenacao implements Serializable {

	private static final long serialVersionUID = 5310067617449104108L;
	
	private String nomeAtributo;
	private boolean asc = true;
	

	/**
	 * @param nomeAtributo
	 * @param asc
	 */
	public AtributoOrdenacao(String nomeAtributo, boolean asc) {
		this.nomeAtributo = nomeAtributo;
		this.asc = asc;
	}

	/**
	 * @return the nomeAtributo
	 */
	public String getNomeAtributo() {
		return nomeAtributo;
	}

	/**
	 * @param nomeAtributo the nomeAtributo to set
	 */
	public void setNomeAtributo(String nomeAtributo) {
		this.nomeAtributo = nomeAtributo;
	}

	/**
	 * @return the asc
	 */
	public boolean isAsc() {
		return asc;
	}

	/**
	 * @param asc the asc to set
	 */
	public void setAsc(boolean asc) {
		this.asc = asc;
	}

}

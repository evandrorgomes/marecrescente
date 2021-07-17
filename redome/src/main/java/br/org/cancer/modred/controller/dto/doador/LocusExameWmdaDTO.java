package br.org.cancer.modred.controller.dto.doador;

import java.io.Serializable;

/**
 * Classe que representa a relação entre locus e exame.
 * 
 * @author ergomes
 *
 */
public class LocusExameWmdaDTO implements Serializable {

	private static final long serialVersionUID = -6066677660152520882L;
	
	private String primeiroAlelo;
    private String segundoAlelo;
    private String codigo;
    
	/**
	 * @return the primeiroAlelo
	 */
	public String getPrimeiroAlelo() {
		return primeiroAlelo;
	}
	/**
	 * @param primeiroAlelo the primeiroAlelo to set
	 */
	public void setPrimeiroAlelo(String primeiroAlelo) {
		this.primeiroAlelo = primeiroAlelo;
	}
	/**
	 * @return the segundoAlelo
	 */
	public String getSegundoAlelo() {
		return segundoAlelo;
	}
	/**
	 * @param segundoAlelo the segundoAlelo to set
	 */
	public void setSegundoAlelo(String segundoAlelo) {
		this.segundoAlelo = segundoAlelo;
	}
	/**
	 * @return the codigo
	 */
	public String getCodigo() {
		return codigo;
	}
	/**
	 * @param codigo the codigo to set
	 */
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
}

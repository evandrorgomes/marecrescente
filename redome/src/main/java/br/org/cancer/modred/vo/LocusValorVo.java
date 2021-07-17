package br.org.cancer.modred.vo;

import java.io.Serializable;

/**
 * Classe Vo para locus.
 * @author Filipe Paes
 *
 */
public class LocusValorVo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Nome do locus ao qual haverá o resultado. 
	 * Os valores aceitaveis para este campo são: A, B, CW, DRB1, DRB3, DRB4, DRB5, DQA, DQB e DPB.
	 */
	private String locus;
	/**
	 * Resultado do exame do primeiro alelo.
	 */
	private String primeiroAlelo;
	/**
	 * Resultado do exame do segundo alelo.
	 */
	private String segundoAlelo;

	/**
	 * @return the locus
	 */
	public String getLocus() {
		return locus;
	}

	/**
	 * @param locus
	 *            the locus to set
	 */
	public void setLocus(String locus) {
		this.locus = locus;
	}

	/**
	 * @return the primeiroAlelo
	 */
	public String getPrimeiroAlelo() {
		return primeiroAlelo;
	}

	/**
	 * @param primeiroAlelo
	 *            the primeiroAlelo to set
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
	 * @param segundoAlelo
	 *            the segundoAlelo to set
	 */
	public void setSegundoAlelo(String segundoAlelo) {
		this.segundoAlelo = segundoAlelo;
	}

}

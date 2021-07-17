package br.org.cancer.modred.controller.dto;

import java.io.Serializable;

/**
 * DTO de qualificação de match.
 * 
 * @author ergomes
 * 
 */

public class ProbabilidadeDTO implements Serializable {

	private static final long serialVersionUID = 4838399403748770685L;

    private String locus;
    private String probabilidade;
    
	/**
	 * 
	 */
	public ProbabilidadeDTO() {}

	/**
	 * @param locus
	 * @param percentual
	 */
	public ProbabilidadeDTO(String locus, String probabilidade) {
		this.locus = locus;
		this.probabilidade = probabilidade;
	}
	/**
	 * @return the locus
	 */
	public String getLocus() {
		return locus;
	}
	/**
	 * @param locus the locus to set
	 */
	public void setLocus(String locus) {
		this.locus = locus;
	}

	/**
	 * @return the probabilidade
	 */
	public String getProbabilidade() {
		return probabilidade;
	}

	/**
	 * @param probabilidade the probabilidade to set
	 */
	public void setProbabilidade(String probabilidade) {
		this.probabilidade = probabilidade;
	}

}

package br.org.cancer.modred.controller.dto;

import java.io.Serializable;

/**
 * DTO de qualificação de match.
 * 
 * @author ergomes
 * 
 */
public class QualificacaoMatchDTO implements Serializable {

	private static final long serialVersionUID = -7843670045964732048L;

	private String locus;
	private String qualificacao;
	private ProbabilidadeDTO probabilidade;

	/**
	 * 
	 */
	public QualificacaoMatchDTO() {}

	/**
	 * @param locus
	 * @param qualificacao
	 * @param probabilidade
	 */
	public QualificacaoMatchDTO(String locus, String qualificacao, ProbabilidadeDTO probabilidade) {
		this.locus = locus;
		this.qualificacao = qualificacao;
		this.probabilidade = probabilidade;
	}
	/**
	 * @return the qualificacao
	 */
	public String getQualificacao() {
		return qualificacao;
	}
	/**
	 * @param qualificacao the qualificacao to set
	 */
	public void setQualificacao(String qualificacao) {
		this.qualificacao = qualificacao;
	}
	/**
	 * @return the probabilidade
	 */
	public ProbabilidadeDTO getProbabilidade() {
		return probabilidade;
	}
	/**
	 * @param probabilidade the probabilidade to set
	 */
	public void setProbabilidade(ProbabilidadeDTO probabilidade) {
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
	
}

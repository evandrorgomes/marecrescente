package br.org.cancer.modred.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Classe de chave primária para split valor G.
 * 
 * @author brunosousa
 */
@Embeddable
public class SplitValorGPK implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "LOCU_ID")
	private Locus locus;

	@Column(name = "SPVG_TX_VALOR")
	private String valor;	
	
	@Column(name = "SPVG_TX_NOME_GRUPO")
	private String nomeGrupo;

	/**
	 * @return the locus
	 */
	public Locus getLocus() {
		return locus;
	}

	/**
	 * @param locus
	 *            the locus to set
	 */
	public void setLocus(Locus locus) {
		this.locus = locus;
	}

	/**
	 * @return the nomeGrupo
	 */
	public String getNomeGrupo() {
		return nomeGrupo;
	}

	/**
	 * @param nomeGrupo
	 *            the nomeGrupo to set
	 */
	public void setNomeGrupo(String nomeGrupo) {
		this.nomeGrupo = nomeGrupo;
	}

	/**
	 * @return the valor
	 */
	public String getValor() {
		return valor;
	}

	/**
	 * @param valor the valor to set
	 */
	public void setValor(String valor) {
		this.valor = valor;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((locus == null) ? 0 : locus.hashCode());
		result = prime * result + ((valor == null) ? 0 : valor.hashCode());
		result = prime * result + ((nomeGrupo == null) ? 0 : nomeGrupo.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj){
			return true;
		}
		if (obj == null){
			return false;
		}
		if (getClass() != obj.getClass()){
			return false;
		}
		SplitValorGPK other = (SplitValorGPK) obj;
		if (locus == null) {
			if (other.locus != null){
				return false;
			}
		} 
		else if (!locus.equals(other.locus)){
			return false;
		}
		if (valor == null) {
			if (other.valor != null){
				return false;
			}
		} 
		else if (!valor.equals(other.valor)){
			return false;
		}
		if (nomeGrupo == null) {
			if (other.nomeGrupo != null){
				return false;
			}
		} 
		else if (!nomeGrupo.equals(other.nomeGrupo)){
			return false;
		}
		return true;
	}

}
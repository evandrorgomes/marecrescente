package br.org.cancer.modred.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Classe de chave primária de Valor P.
 * 
 * @author Filipe Paes
 */
@Embeddable
public class ValorPPK implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "LOCU_ID")
	private Locus locus;

	@Column(name = "VALP_TX_NOME_GRUPO")
	private String nomeGrupo;

	public ValorPPK() {}

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
	 * @param valor
	 *            the nomeGrupo to set
	 */
	public void setNomeGrupo(String nomeGrupo) {
		this.nomeGrupo = nomeGrupo;
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
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		ValorPPK other = (ValorPPK) obj;
		if (locus == null) {
			if (other.locus != null) {
				return false;
			}
		}
		else if (!locus.equals(other.locus)) {
			return false;
		}
		if (nomeGrupo == null) {
			if (other.nomeGrupo != null) {
				return false;
			}
		}
		else if (!nomeGrupo.equals(other.nomeGrupo)) {
			return false;
		}
		return true;
	}
}
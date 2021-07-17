package br.org.cancer.modred.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Classe de abo para ordenação por ABO.
 * 
 * @author fillipe.queiroz
 *
 */
@Embeddable
public class ClassificacaoABOPK implements Serializable {

	private static final long serialVersionUID = 220485097686054351L;

	@Column(name = "CLAB_TX_ABO")
	private String abo;

	@Column(name = "CLAB_TX_ABO_RELACAO")
	private String aboRelacionado;

	/**
	 * Construtor padrão da classe.
	 */
	public ClassificacaoABOPK() {}

	/**
	 * @return the abo
	 */
	public String getAbo() {
		return abo;
	}

	/**
	 * @param abo the abo to set
	 */
	public void setAbo(String abo) {
		this.abo = abo;
	}

	/**
	 * @return the aboRelacionado
	 */
	public String getAboRelacionado() {
		return aboRelacionado;
	}

	/**
	 * @param aboRelacionado the aboRelacionado to set
	 */
	public void setAboRelacionado(String aboRelacionado) {
		this.aboRelacionado = aboRelacionado;
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
		result = prime * result + ( ( abo == null ) ? 0 : abo.hashCode() );
		result = prime * result + ( ( aboRelacionado == null ) ? 0 : aboRelacionado.hashCode() );
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
		ClassificacaoABOPK other = (ClassificacaoABOPK) obj;
		if (abo == null) {
			if (other.abo != null) {
				return false;
			}
		}
		else
			if (!abo.equals(other.abo)) {
				return false;
			}
		if (aboRelacionado == null) {
			if (other.aboRelacionado != null) {
				return false;
			}
		}
		else
			if (!aboRelacionado.equals(other.aboRelacionado)) {
				return false;
			}
		return true;
	}

}
package br.org.cancer.modred.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

/**
 * Classe de representa a chave primária para a entidade ValorAntigeno.
 * 
 * @author Pizão.
 */
@Embeddable
public class ValorSorologicoPk implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "LOCU_ID")
	@NotNull
	private Locus locus;

	@Column(name = "VASO_TX_ANTIGENO")
	@NotNull
	private String antigeno;

	public ValorSorologicoPk() {
	}

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
	 * Código do antígeno (chave primária).
	 * 
	 * @return
	 */
	public String getAntigeno() {
		return antigeno;
	}

	public void setAntigeno(String antigeno) {
		this.antigeno = antigeno;
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
		result = prime * result + ((antigeno == null) ? 0 : antigeno.hashCode());
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
		ValorSorologicoPk other = (ValorSorologicoPk) obj;
		if (locus == null) {
			if (other.locus != null) {
				return false;
			}
		}
		else if (!locus.equals(other.locus)) {
			return false;
		}
		if (antigeno == null) {
			if (other.antigeno != null) {
				return false;
			}
		}
		else if (!antigeno.equals(other.antigeno)) {
			return false;
		}
		return true;
	}

}
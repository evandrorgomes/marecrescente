package br.org.cancer.modred.model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Classe que representa a chave composta da tabela CID_IDIOMA.
 * 
 * @author Cintia Oliveira
 *
 */
@Embeddable
public class CidIdiomaPK implements Serializable {

	private static final long serialVersionUID = 4366503221406829047L;

	@ManyToOne
	@JoinColumn(name = "CID_ID")
	private Cid cid;

	@ManyToOne
	@JoinColumn(name = "IDIO_ID")
	private Idioma idioma;

	/**
	 * @return the cid
	 */
	public Cid getCid() {
		return cid;
	}

	/**
	 * @param cid the cid to set
	 */
	public void setCid(Cid cid) {
		this.cid = cid;
	}

	/**
	 * @return the idioma
	 */
	public Idioma getIdioma() {
		return idioma;
	}

	/**
	 * @param idioma the idioma to set
	 */
	public void setIdioma(Idioma idioma) {
		this.idioma = idioma;
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
		result = prime * result + ( ( cid == null ) ? 0 : cid.hashCode() );
		result = prime * result + ( ( idioma == null ) ? 0 : idioma.hashCode() );
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
		CidIdiomaPK other = (CidIdiomaPK) obj;
		if (cid == null) {
			if (other.cid != null) {
				return false;
			}
		}
		else
			if (!cid.equals(other.cid)) {
				return false;
			}
		if (idioma == null) {
			if (other.idioma != null) {
				return false;
			}
		}
		else
			if (!idioma.equals(other.idioma)) {
				return false;
			}
		return true;
	}

}

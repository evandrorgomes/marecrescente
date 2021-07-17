package br.org.cancer.modred.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Classe que representa a tabela IDIOMA, responsável por armazenar os idiomas suportados pela aplicação.
 * 
 * @author Cintia Oliveira
 *
 */
@Entity
@Table(name = "IDIOMA")
public class Idioma implements Serializable {

	private static final long serialVersionUID = -7442865098573206315L;

	@Id
	@Column(name = "IDIO_ID")
	private Integer id;

	@Column(name = "IDIO_TX_SIGLA")
	private String sigla;

	/**
	 * Construtor default.
	 */
	public Idioma() {
		super();
	}

	/**
	 * Construtor com id e sigla.
	 * 
	 * @param id do idioma
	 * @param sigla do idioma
	 */
	public Idioma(Integer id, String sigla) {
		this();
		this.id = id;
		this.sigla = sigla;
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the sigla
	 */
	public String getSigla() {
		return sigla;
	}

	/**
	 * @param sigla the sigla to set
	 */
	public void setSigla(String sigla) {
		this.sigla = sigla;
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
		result = prime * result + ( ( id == null ) ? 0 : id.hashCode() );
		result = prime * result + ( ( sigla == null ) ? 0 : sigla.hashCode() );
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
		Idioma other = (Idioma) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		}
		else
			if (!id.equals(other.id)) {
				return false;
			}
		if (sigla == null) {
			if (other.sigla != null) {
				return false;
			}
		}
		else
			if (!sigla.equals(other.sigla)) {
				return false;
			}
		return true;
	}

	@Override
	public String toString() {
		return sigla;
	}

}

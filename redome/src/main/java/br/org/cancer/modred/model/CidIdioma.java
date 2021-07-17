package br.org.cancer.modred.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Classe que representa a tabela CID_IDIOMA, responsável por armazenar os valores dos campos da Cid internacionalizado.
 * 
 * @author Cintia Oliveira
 *
 */
@Entity
@Table(name = "CID_IDIOMA")
public class CidIdioma implements Serializable {

	private static final long serialVersionUID = -5904300882710496311L;

	@EmbeddedId
	private CidIdiomaPK id;

	@Column(name = "CIID_TX_DESCRICAO", nullable = false)
	private String descricao;

	/**
	 * Construtor default.
	 */
	public CidIdioma() {
		super();
	}

	/**
	 * Construtor com cid, idioma e descrição.
	 * 
	 * @param cid de cid idioma
	 * @param idioma de cid idioma
	 * @param descricao no idioma passado como parametro.
	 */
	public CidIdioma(Cid cid, Idioma idioma, String descricao) {
		this();
		this.id = new CidIdiomaPK();
		this.id.setCid(cid);
		this.id.setIdioma(idioma);
		this.descricao = descricao;
	}

	/**
	 * @return the id
	 */
	public CidIdiomaPK getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(CidIdiomaPK id) {
		this.id = id;
	}

	/**
	 * @return the descricao
	 */
	public String getDescricao() {
		return descricao;
	}

	/**
	 * @param descricao the descricao to set
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
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
		result = prime * result + ( ( descricao == null ) ? 0 : descricao.hashCode() );
		result = prime * result + ( ( id == null ) ? 0 : id.hashCode() );
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
		CidIdioma other = (CidIdioma) obj;
		if (descricao == null) {
			if (other.descricao != null) {
				return false;
			}
		}
		else
			if (!descricao.equals(other.descricao)) {
				return false;
			}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		}
		else
			if (!id.equals(other.id)) {
				return false;
			}
		return true;
	}

}
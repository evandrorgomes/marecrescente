package br.org.cancer.modred.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Classe qu representa um Motivo de descarte.
 * 
 * @author Filipe Paes
 *
 */
@Entity
@Table(name = "MOTIVO_DESCARTE")
public class MotivoDescarte implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static final Long ARQUIVO_CORROMPIDO = 1L;
	public static final Long ARQUIVO_NAO_CORRESPONDE_AO_PACIENTE = 2L;

	/**
	 * Atributo de identificação do motivo de descarte.
	 */
	@Id
	@Column(name = "MODE_ID")
	private Long id;

	/**
	 * Descrição do motivo de descarte.
	 */
	@Column(name = "MODE_TX_DESCRICAO")
	private String descricao;

	public MotivoDescarte() {}
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the descricao
	 */
	public String getDescricao() {
		return descricao;
	}

	/**
	 * @param descricao
	 *            the descricao to set
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	/* (non-Javadoc)
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
		MotivoDescarte other = (MotivoDescarte) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} 
		else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}
}
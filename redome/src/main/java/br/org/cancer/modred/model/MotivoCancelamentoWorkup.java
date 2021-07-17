package br.org.cancer.modred.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Classe bean de persistencia para Motivo de Cancelamento.
 * 
 * @author Filipe Paes
 */
@Entity
@Table(name = "MOTIVO_CANCELAMENTO_WORKUP")
public class MotivoCancelamentoWorkup implements Serializable {

	private static final long serialVersionUID = -3742045112260030474L;

	/**
	 * Id de motivo de cancelamento.
	 */
	@Id
	@Column(name = "MOCW_ID")
	private Long id;

	/**
	 * Descrição de motivo de cancelamento.
	 */
	@Column(name = "MOCW_DESCRICAO")
	private String descricao;

	@Column(name = "MOCW_IN_SELECIONAVEL")
	private boolean selecionavel;

	public MotivoCancelamentoWorkup() {
		super();
	}
	
	/**
	 * Construtor para facilitar a criação do componente somente com o ID.
	 * 
	 * @param id ID do motivo.
	 */
	public MotivoCancelamentoWorkup(Long id) {
		this(id, null);
	}

	/**
	 * Construtor com os dados para criação.
	 * 
	 * @param id - identificador do motivo.
	 * @param descricao - descrição do motivo de cancelamento.
	 */
	public MotivoCancelamentoWorkup(Long id, String descricao) {
		super();
		this.id = id;
		this.descricao = descricao;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
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
	 * @param descricao the descricao to set
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	/**
	 * @return the selecionavel
	 */
	public boolean isSelecionavel() {
		return selecionavel;
	}

	/**
	 * @param selecionavel the selecionavel to set
	 */
	public void setSelecionavel(boolean selecionavel) {
		this.selecionavel = selecionavel;
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
		MotivoCancelamentoWorkup other = (MotivoCancelamentoWorkup) obj;
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
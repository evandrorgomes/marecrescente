package br.org.cancer.modred.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Classe de abo para ordenação por ABO.
 * 
 * @author fillipe.queiroz
 *
 */
@Entity
@Table(name = "CLASSIFICACAO_ABO")
public class ClassificacaoABO implements Serializable {

	private static final long serialVersionUID = 220485097686054351L;

	@EmbeddedId
	@Valid
	@NotNull
	private ClassificacaoABOPK id;

	@Column(name = "CLAB_VL_PRIORIDADE")
	private Integer prioridade;

	/**
	 * Construtor padrão da classe.
	 */
	public ClassificacaoABO() {}

	/**
	 * @return the id
	 */
	public ClassificacaoABOPK getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(ClassificacaoABOPK id) {
		this.id = id;
	}

	/**
	 * @return the prioridade
	 */
	public Integer getPrioridade() {
		return prioridade;
	}

	/**
	 * @param prioridade the prioridade to set
	 */
	public void setPrioridade(Integer prioridade) {
		this.prioridade = prioridade;
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
		result = prime * result + ( ( prioridade == null ) ? 0 : prioridade.hashCode() );
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
		ClassificacaoABO other = (ClassificacaoABO) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		}
		else
			if (!id.equals(other.id)) {
				return false;
			}
		if (prioridade == null) {
			if (other.prioridade != null) {
				return false;
			}
		}
		else
			if (!prioridade.equals(other.prioridade)) {
				return false;
			}
		return true;
	}

}
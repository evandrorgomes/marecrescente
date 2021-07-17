package br.org.cancer.redome.auth.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.sun.istack.NotNull;

/**
 * Classe que representa os recursos da aplicação. Utilizada para dar permissão de acesso as funcionalidades.
 * 
 * @author Cintia Oliveira
 *
 */
@Entity
@Table(name = "PERMISSAO")
public class Permissao implements Serializable {

	private static final long serialVersionUID = -7674556081902633946L;

	@Id
	@Column(name = "RECU_ID")
	@NotNull
	private PermissaoId id;

	@Column(name = "PERM_IN_COM_RESTRICAO")
	@NotNull
	private boolean comRestricao;

	/**
	 * @return the id
	 */
	public PermissaoId getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(PermissaoId id) {
		this.id = id;
	}

	/**
	 * @return the comRestricao
	 */
	public boolean isComRestricao() {
		return comRestricao;
	}

	/**
	 * @param comRestricao the comRestricao to set
	 */
	public void setComRestricao(boolean comRestricao) {
		this.comRestricao = comRestricao;
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
		Permissao other = (Permissao) obj;
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

package br.org.cancer.modred.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.ChecklistView;

/**
 * Tipo de checklist que identifica se o item envolve Medula ou Cordão internacional.
 * 
 * @author Filipe Paes
 */
@Entity
@Table(name = "TIPO_CHECKLIST")
public class TipoChecklist implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "TIPC_ID")
	@JsonView(value = ChecklistView.Detalhe.class)
	private Long id;

	@Column(name = "TIPC_TX_NM_TIPO")
	@JsonView(value = ChecklistView.Detalhe.class)
	private String nome;

	@OneToMany(mappedBy = "tipo")
	@JsonView(value = ChecklistView.Detalhe.class)
	private List<CategoriaChecklist> categorias;

	public TipoChecklist() {
	}

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
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @param nome
	 *            the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * @return the categorias
	 */
	public List<CategoriaChecklist> getCategorias() {
		return categorias;
	}

	/**
	 * @param categorias the categorias to set
	 */
	public void setCategorias(List<CategoriaChecklist> categorias) {
		this.categorias = categorias;
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
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
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
		TipoChecklist other = (TipoChecklist) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} 
		else if (!id.equals(other.id)) {
			return false;
		}
		if (nome == null) {
			if (other.nome != null) {
				return false;
			}
		} 
		else if (!nome.equals(other.nome)) {
			return false;
		}
		return true;
	}

}
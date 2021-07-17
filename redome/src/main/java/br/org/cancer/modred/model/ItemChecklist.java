package br.org.cancer.modred.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.ChecklistView;

/**
 * Itens relacionados ao tipo de checklist para conclusão de tarefas;.
 * 
 * @author Filipe Paes
 */
@Entity
@Table(name = "ITENS_CHECKLIST")
@SequenceGenerator(name = "SQ_ITEC_ID", sequenceName = "SQ_ITEC_ID", allocationSize = 1)
public class ItemChecklist implements Serializable {
	private static final long serialVersionUID = -6543767019695878922L;

	@Id
	@Column(name = "ITEC_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_ITEC_ID")
	@JsonView(value = {ChecklistView.Detalhe.class,ChecklistView.Resposta.class})
	private Long id;

	@Column(name = "ITEC_TX_NM_ITEM")
	@JsonView(value = ChecklistView.Detalhe.class)
	private String nome;

	@ManyToOne
	@JoinColumn(name = "CACH_ID")
	private CategoriaChecklist categoriaChecklist;

	@Transient
	@JsonView(value = ChecklistView.Detalhe.class)
	private Boolean resposta;

	public ItemChecklist() {
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
	 * @param nome the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * @return the categoriaChecklist
	 */
	public CategoriaChecklist getCategoriaChecklist() {
		return categoriaChecklist;
	}

	/**
	 * @param categoriaChecklist
	 *            the categoriaChecklist to set
	 */
	public void setCategoriaChecklist(CategoriaChecklist categoriaChecklist) {
		this.categoriaChecklist = categoriaChecklist;
	}

	/**
	 * @return the respota
	 */
	public Boolean getResposta() {
		return resposta;
	}

	/**
	 * @param respota
	 *            the respota to set
	 */
	public void setResposta(Boolean respota) {
		this.resposta = respota;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((categoriaChecklist == null) ? 0 : categoriaChecklist.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((resposta == null) ? 0 : resposta.hashCode());
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
		ItemChecklist other = (ItemChecklist) obj;
		if (categoriaChecklist == null) {
			if (other.categoriaChecklist != null) {
				return false;
			}
		} 
		else if (!categoriaChecklist.equals(other.categoriaChecklist)) {
			return false;
		}
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
		if (resposta == null) {
			if (other.resposta != null) {
				return false;
			}
		}
		else if (!resposta.equals(other.resposta)) {
			return false;
		}
		return true;
	}

	
}
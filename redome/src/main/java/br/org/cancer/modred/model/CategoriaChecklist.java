package br.org.cancer.modred.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.ChecklistView;

/**
 * Classe para agrupar os itens de checklist.
 * @author Filipe Paes
 */
@Entity
@Table(name = "CATEGORIA_CHECKLIST")
public class CategoriaChecklist implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CACH_ID")
	@JsonView(value = ChecklistView.Detalhe.class)
	private Long id;

	@Column(name = "CACH_TX_NM_CATEGORIA")
	@JsonView(value = ChecklistView.Detalhe.class)
	private String nome;

	@ManyToOne
	@JoinColumn(name = "TIPC_ID")
	private TipoChecklist tipo;

	@OneToMany(mappedBy = "categoriaChecklist")
	@JsonView(value = ChecklistView.Detalhe.class)
	private List<ItemChecklist> itens;

	public CategoriaChecklist() {
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
	 * @return the tipo
	 */
	public TipoChecklist getTipo() {
		return tipo;
	}

	/**
	 * @param tipo
	 *            the tipo to set
	 */
	public void setTipo(TipoChecklist tipo) {
		this.tipo = tipo;
	}

	/**
	 * @return the itens
	 */
	public List<ItemChecklist> getItens() {
		return itens;
	}

	/**
	 * @param itens
	 *            the itens to set
	 */
	public void setItens(List<ItemChecklist> itens) {
		this.itens = itens;
	}

}
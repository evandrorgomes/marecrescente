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

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.ChecklistView;

/**
 * Classe para armazenamento das respostas dos itens de checklist.
 * 
 * @author Filipe Paes
 * 
 */
@Entity
@Table(name = "RESPOSTA_CHECKLIST")
@SequenceGenerator(name = "SQ_RESC_ID", sequenceName = "SQ_RESC_ID", allocationSize = 1)
public class RespostaChecklist implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "RESC_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_RESC_ID")
	@JsonView(ChecklistView.Resposta.class)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "ITEC_ID")
	@JsonView(ChecklistView.Resposta.class)
	private ItemChecklist item;

	@Column(name = "RESC_IN_RESPOSTA")
	@JsonView(ChecklistView.Resposta.class)
	private Boolean resposta;

	@ManyToOne
	@JoinColumn(name = "PELO_ID")
	private PedidoLogistica pedidoLogistica;

	public RespostaChecklist() {
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
	 * @return the item
	 */
	public ItemChecklist getItem() {
		return item;
	}

	/**
	 * @param item
	 *            the item to set
	 */
	public void setItem(ItemChecklist item) {
		this.item = item;
	}

	/**
	 * @return the resposta
	 */
	public Boolean getResposta() {
		return resposta;
	}

	/**
	 * @param resposta
	 *            the resposta to set
	 */
	public void setResposta(Boolean resposta) {
		this.resposta = resposta;
	}

	/**
	 * @return the pedidoLogistica
	 */
	public PedidoLogistica getPedidoLogistica() {
		return pedidoLogistica;
	}

	/**
	 * @param pedidoLogistica
	 *            the pedidoLogistica to set
	 */
	public void setPedidoLogistica(PedidoLogistica pedidoLogistica) {
		this.pedidoLogistica = pedidoLogistica;
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
		result = prime * result + ((item == null) ? 0 : item.hashCode());
		result = prime * result + ((pedidoLogistica == null) ? 0 : pedidoLogistica.hashCode());
		result = prime * result + ((resposta == null) ? 0 : resposta.hashCode());
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
		RespostaChecklist other = (RespostaChecklist) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} 
		else if (!id.equals(other.id)) {
			return false;
		}
		if (item == null) {
			if (other.item != null) {
				return false;
			}
		} 
		else if (!item.equals(other.item)) {
			return false;
		}
		if (pedidoLogistica == null) {
			if (other.pedidoLogistica != null) {
				return false;
			}
		} 
		else if (!pedidoLogistica.equals(other.pedidoLogistica)) {
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
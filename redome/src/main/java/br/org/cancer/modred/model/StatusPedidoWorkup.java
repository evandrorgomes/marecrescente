package br.org.cancer.modred.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.DoadorView;
import br.org.cancer.modred.controller.view.PedidoWorkupView;
import br.org.cancer.modred.controller.view.TarefaView;
import br.org.cancer.modred.model.domain.StatusPedidosWorkup;


/**
 * Classe bean de persistencia para status de pedido de workup. 
 * @author Filipe Paes
 */
@Entity
@Table(name="STATUS_PEDIDO_WORKUP")
public class StatusPedidoWorkup implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static final Long CANCELADO = 3L;
	public static final Long REALIZADO = 5L;

	@Id
	@Column(name="STPW_ID")
	@JsonView(value = { TarefaView.Listar.class, TarefaView.Consultar.class, PedidoWorkupView.DetalheWorkup.class, 
			DoadorView.ContatoPassivo.class, PedidoWorkupView.AgendamentoWorkup.class })
	private Long id;

	@Column(name="STPW_DESCRICAO")
	@JsonView(value = { TarefaView.Listar.class, TarefaView.Consultar.class, PedidoWorkupView.DetalheWorkup.class, 
			DoadorView.ContatoPassivo.class, PedidoWorkupView.AgendamentoWorkup.class })
	private String descricao;

	public StatusPedidoWorkup() {
	}

	public StatusPedidoWorkup(StatusPedidosWorkup status) {
		this(status.getId());
	}
	
	public StatusPedidoWorkup(Long id) {
		this.id = id;
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

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
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
		StatusPedidoWorkup other = (StatusPedidoWorkup) obj;
		if (descricao == null) {
			if (other.descricao != null) {
				return false;
			}
		} 
		else if (!descricao.equals(other.descricao)) {
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
		return true;
	}

	

}
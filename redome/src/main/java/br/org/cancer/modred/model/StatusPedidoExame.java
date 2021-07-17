package br.org.cancer.modred.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.BuscaView;
import br.org.cancer.modred.controller.view.PedidoExameView;
import br.org.cancer.modred.model.domain.StatusPedidosExame;

/**
 * Status do pedido de exame.
 * 
 * @author bruno.sousa
 */
@Entity
@Table(name = "STATUS_PEDIDO_EXAME")
public class StatusPedidoExame implements Serializable {

	private static final long serialVersionUID = 8902076051087871927L;

	@Id
	@Column(name = "STPX_ID")
	@JsonView({BuscaView.UltimoPedidoExame.class, PedidoExameView.ListarTarefas.class})
	private Long id;

	@Column(name = "STPX_TX_DESCRICAO")
	@JsonView({BuscaView.UltimoPedidoExame.class, PedidoExameView.ListarTarefas.class})
	private String descricao;

	public StatusPedidoExame() {}	

	public StatusPedidoExame(StatusPedidosExame status) {
		this(status.getId());
	}

	/**
	 * Construtor recebendo o id do status de pedido.
	 * @param id identifiação do status de pedido.
	 */
	public StatusPedidoExame(Long id) {
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
		StatusPedidoExame other = (StatusPedidoExame) obj;
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
package br.org.cancer.modred.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.TarefaLogisticaView;

/**
 * Referência ao tipo do pedido de logística.
 * A princípio, podemos ter logística de material ou do próprio doador.
 * 
 * @author Pizão.
 */
@Entity
@Table(name = "TIPO_PEDIDO_LOGISTICA")
public class TipoPedidoLogistica implements Serializable {
	private static final long serialVersionUID = -8618728761126685127L;

	@Id
	@Column(name = "TIPL_ID")
	@JsonView(value=TarefaLogisticaView.Listar.class)
	private Long id;

	@Column(name = "TIPL_TX_DESCRICAO")
	@JsonView(value=TarefaLogisticaView.Listar.class)
	private String descricao;

	public TipoPedidoLogistica() {}

	public TipoPedidoLogistica(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ( ( descricao == null ) ? 0 : descricao.hashCode() );
		result = prime * result + ( ( id == null ) ? 0 : id.hashCode() );
		return result;
	}

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
		TipoPedidoLogistica other = (TipoPedidoLogistica) obj;
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
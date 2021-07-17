package br.org.cancer.modred.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.AnaliseMedicaView;
import br.org.cancer.modred.controller.view.DoadorView;
import br.org.cancer.modred.controller.view.PedidoExameView;
import br.org.cancer.modred.controller.view.SolicitacaoView;

/**
 * Classe de persistencia para tipo de solicitação.
 * 
 * @author Fillipe Queiroz
 */
@Entity
@Table(name = "TIPO_SOLICITACAO")
public class TipoSolicitacao implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "TISO_ID")
	@JsonView({ DoadorView.ContatoPassivo.class, PedidoExameView.ListarTarefas.class, AnaliseMedicaView.ListarTarefas.class, AnaliseMedicaView.Detalhe.class, SolicitacaoView.detalhe.class  })
	private Long id;

	@Column(name = "TISO_TX_DESCRICAO")
	@JsonView({ DoadorView.ContatoPassivo.class, AnaliseMedicaView.ListarTarefas.class, AnaliseMedicaView.Detalhe.class })
	private String descricao;

	public TipoSolicitacao() {}

	public TipoSolicitacao(Long id) {
		this.id = id;
	}

	/**
	 * Chave primaria do tipo de solicitação.
	 * 
	 * @return
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Chave primaria do tipo de solicitação.
	 * 
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Descricao do tipo de solicitacao.
	 * 
	 * @return
	 */
	public String getDescricao() {
		return descricao;
	}

	/**
	 * Descricao do tipo de solicitacao.
	 * 
	 * @param descricao
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
		TipoSolicitacao other = (TipoSolicitacao) obj;
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
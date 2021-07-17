package br.org.cancer.modred.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import br.org.cancer.modred.model.domain.TiposSolicitacaoRedomeweb;

/**
 * Classe de persistencia para SolicitacaoRedomweweb.
 * 
 * @author bruno.sousa
 */
@Entity
@SequenceGenerator(name = "SQ_SORE_ID", sequenceName = "SQ_SORE_ID", allocationSize = 1)
@Table(name = "SOLICITACAO_REDOMEWEB")
public class SolicitacaoRedomeweb implements Serializable {

	private static final long serialVersionUID = 1605978295328333866L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_SORE_ID")
	@Column(name = "SORE_ID")
	private Long id;

	@Column(name = "SORE_NR_TIPO")
	@NotNull
	private Long tipoSolicitacaoRedomeweb;

	@Column(name = "SORE_ID_SOLICITACAO_REDOMEWEB")
	@NotNull
	private Long idSolicitacaoRedomeweb;

	@Column(name = "SORE_DT_CRIACAO")
	@NotNull
	private LocalDateTime dataCriacao;

	@Column(name = "SORE_NR_STATUS")
	private Long statusPedidoExame;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "PEEX_ID")
	@NotNull
	private PedidoExame pedidoExame;

	public SolicitacaoRedomeweb() {
	}

	/**
	 * Chave primaria da solicitacao.
	 * 
	 * @return
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Chave primaria da solicitacao.
	 * 
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the tipoSolicitacaoRedomeweb
	 */
	public TiposSolicitacaoRedomeweb getTipoSolicitacaoRedomeweb() {
		return TiposSolicitacaoRedomeweb.valueOf(tipoSolicitacaoRedomeweb);
	}

	/**
	 * @param tipoSolicitacaoRedomeweb the tipoSolicitacaoRedomeweb to set
	 */
	public void setTipoSolicitacaoRedomeweb(TiposSolicitacaoRedomeweb tipoSolicitacaoRedomeweb) {
		if (tipoSolicitacaoRedomeweb != null) {
			this.tipoSolicitacaoRedomeweb = tipoSolicitacaoRedomeweb.getId();
		}
		else { 
			this.tipoSolicitacaoRedomeweb = null;
		}
	}

	/**
	 * @return the idSolicitacaoRedomeweb
	 */
	public Long getIdSolicitacaoRedomeweb() {
		return idSolicitacaoRedomeweb;
	}

	/**
	 * @param idSolicitacaoRedomeweb the idSolicitacaoRedomeweb to set
	 */
	public void setIdSolicitacaoRedomeweb(Long idSolicitacaoRedomeweb) {
		this.idSolicitacaoRedomeweb = idSolicitacaoRedomeweb;
	}

	/**
	 * @return the dataCriacao
	 */
	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}

	/**
	 * @param dataCriacao the dataCriacao to set
	 */
	public void setDataCriacao(LocalDateTime dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	/**
	 * @return the pedidoExame
	 */
	public PedidoExame getPedidoExame() {
		return pedidoExame;
	}

	/**
	 * @param pedidoExame the pedidoExame to set
	 */
	public void setPedidoExame(PedidoExame pedidoExame) {
		this.pedidoExame = pedidoExame;
	}

	/**
	 * @return the statusPedidoExame
	 */
	public Long getStatusPedidoExame() {
		return statusPedidoExame;
	}

	/**
	 * @param statusPedidoExame the statusPedidoExame to set
	 */
	public void setStatusPedidoExame(Long statusPedidoExame) {
		this.statusPedidoExame = statusPedidoExame;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		SolicitacaoRedomeweb other = (SolicitacaoRedomeweb) obj;
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
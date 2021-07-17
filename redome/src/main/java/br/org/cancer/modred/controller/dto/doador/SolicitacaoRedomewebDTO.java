package br.org.cancer.modred.controller.dto.doador;


import java.time.LocalDateTime;

/**
 * Classe de persistencia para SolicitacaoRedomweweb.
 * 
 * @author evandro.gomes
 */
public class SolicitacaoRedomewebDTO {

	private Long id;
	private Long tipoSolicitacaoRedomeweb;
	private Long idSolicitacaoRedomeweb;
	private Long idSolicitacaoRedomewebOrigem;
	private LocalDateTime dataCriacao;
	private PedidoExameDTO pedidoExame;

	public SolicitacaoRedomewebDTO() {
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
	public Long getTipoSolicitacaoRedomeweb() {
		return tipoSolicitacaoRedomeweb;
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
	public PedidoExameDTO getPedidoExame() {
		return pedidoExame;
	}

	/**
	 * @param pedidoExame the pedidoExame to set
	 */
	public void setPedidoExame(PedidoExameDTO pedidoExame) {
		this.pedidoExame = pedidoExame;
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
		SolicitacaoRedomewebDTO other = (SolicitacaoRedomewebDTO) obj;
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

	/**
	 * @return the idSolicitacaoRedomewebOrigem
	 */
	public Long getIdSolicitacaoRedomewebOrigem() {
		return idSolicitacaoRedomewebOrigem;
	}

	/**
	 * @param idSolicitacaoRedomewebOrigem the idSolicitacaoRedomewebOrigem to set
	 */
	public void setIdSolicitacaoRedomewebOrigem(Long idSolicitacaoRedomewebOrigem) {
		this.idSolicitacaoRedomewebOrigem = idSolicitacaoRedomewebOrigem;
	}

	/**
	 * @param tipoSolicitacaoRedomeweb the tipoSolicitacaoRedomeweb to set
	 */
	public void setTipoSolicitacaoRedomeweb(Long tipoSolicitacaoRedomeweb) {
		this.tipoSolicitacaoRedomeweb = tipoSolicitacaoRedomeweb;
	}
}



package br.org.cancer.modred.controller.dto.doador;

import java.io.Serializable;

/**
 * Classe que define as mensagens de erro no retorno da integração com o Redome Web.
 * 
 * @author evandro.gomes
 *
 */
public class MensagemErroIntegracao implements Serializable {

	private static final long serialVersionUID = 5142448160148047906L;

	private Long dmr;
	private Long idSolicitacao;
	private String descricaoErro;

	public MensagemErroIntegracao() {}

	/**
	 * @return the dmr
	 */
	public Long getDmr() {
		return dmr;
	}
	/**
	 * @param dmr the dmr to set
	 */
	public void setDmr(Long dmr) {
		this.dmr = dmr;
	}
	/**
	 * @return the descricaoErro
	 */
	public String getDescricaoErro() {
		return descricaoErro;
	}
	/**
	 * @param descricaoErro the descricaoErro to set
	 */
	public void setDescricaoErro(String descricaoErro) {
		this.descricaoErro = descricaoErro;
	}
	
	/**
	 * @return the idSolicitacao
	 */
	public Long getIdSolicitacao() {
		return idSolicitacao;
	}

	/**
	 * @param idSolicitacao the idSolicitacao to set
	 */
	public void setIdSolicitacao(Long idSolicitacao) {
		this.idSolicitacao = idSolicitacao;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((descricaoErro == null) ? 0 : descricaoErro.hashCode());
		result = prime * result + ((dmr == null) ? 0 : dmr.hashCode());
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
		MensagemErroIntegracao other = (MensagemErroIntegracao) obj;
		if (descricaoErro == null) {
			if (other.descricaoErro != null) {
				return false;
			}
		} 
		else if (!descricaoErro.equals(other.descricaoErro)) {
			return false;
		}
		if (dmr == null) {
			if (other.dmr != null) {
				return false;
			}
		} 
		else if (!dmr.equals(other.dmr)) {
			return false;
		}
		return true;
	}
}

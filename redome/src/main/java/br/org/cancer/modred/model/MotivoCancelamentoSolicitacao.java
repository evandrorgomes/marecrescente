package br.org.cancer.modred.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Classe referente ao motivo de cancelamento da busca do paciente.
 * 
 * @author fillipe.queiroz
 * 
 */
@Entity
@Table(name = "MOTIVO_CANCELAMENTO_SOLICITACAO")
public class MotivoCancelamentoSolicitacao implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "MOCS_ID")
	private Long id;

	@Column(name = "MOCS_TX_DESCRICAO")
	private String descricao;
	

	public MotivoCancelamentoSolicitacao() {}

	public MotivoCancelamentoSolicitacao(String descricao) {
		this.descricao = descricao;
	}

	/**
	 * Id do motivo.
	 * 
	 * @return
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Id do motivo.
	 * 
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Descricao do motivo.
	 * 
	 * @return
	 */
	public String getDescricao() {
		return descricao;
	}

	/**
	 * Descricao do motivo.
	 * 
	 * @param descricao
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
		result = prime * result + ( ( descricao == null ) ? 0 : descricao.hashCode() );
		result = prime * result + ( ( id == null ) ? 0 : id.hashCode() );
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
		if (!( obj instanceof MotivoCancelamentoSolicitacao )) {
			return false;
		}
		MotivoCancelamentoSolicitacao other = (MotivoCancelamentoSolicitacao) obj;
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
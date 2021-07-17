package br.org.cancer.modred.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.AvaliacaoView;

/**
 * Classe de possíveis status de uma pendencia.
 * 
 * @author Fillipe Queiroz
 */
@Entity
@Table(name = "STATUS_PENDENCIA")
public class StatusPendencia implements Serializable {

	private static final long serialVersionUID = 5998108100621241119L;

	@Id
	@Column(name = "STPE_ID")
	@JsonView({ AvaliacaoView.ListarPendencias.class })
	private Long id;

	@Column(name = "STPE_TX_DESCRICAO")
	@JsonView({ AvaliacaoView.ListarPendencias.class })
	private String descricao;

	public StatusPendencia() {
		super();
	}

	public StatusPendencia(Long id) {
		this();
		this.id = id;
	}

	public StatusPendencia(Long id, String descricao) {
		this(id);
		this.descricao = descricao;
	}

	/**
	 * Retorna a chave primaria do status da pendencia.
	 * 
	 * @return id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Seta a chave primaria do status da pendencia.
	 * 
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Retorna a descricao do status da pendencia.
	 * 
	 * @return descricao
	 */
	public String getDescricao() {
		return descricao;
	}

	/**
	 * Seta a descricao do status da pendencia.
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
		result = prime * result + (int) ( id ^ ( id >>> 32 ) );
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
		StatusPendencia other = (StatusPendencia) obj;
		if (descricao == null) {
			if (other.descricao != null) {
				return false;
			}
		}
		else
			if (!descricao.equals(other.descricao)) {
				return false;
			}
		if (id != other.id) {
			return false;
		}
		return true;
	}

}
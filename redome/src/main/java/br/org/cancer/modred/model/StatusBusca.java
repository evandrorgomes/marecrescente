package br.org.cancer.modred.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.BuscaView;
import br.org.cancer.modred.controller.view.PacienteView;

/**
 * Classe de persistencia para Status Busca.
 * 
 * @author Filipe Paes
 */
@Entity
@Table(name = "STATUS_BUSCA")
public class StatusBusca implements Serializable {
	private static final long serialVersionUID = -8463014565656906739L;

	public static final Long AGUARDANDO_LIBERACAO = 1L;
	public static final Long LIBERADA = 2L;
	public static final Long EM_AVALIACAO = 3L;
	public static final Long CANCELADA = 4L;
	public static final Long MATCH_SELECIONADO = 5L;
	public static final Long ENCERRADA = 6L;
	

	@Id
	@Column(name = "STBU_ID")
	@JsonView(value = {PacienteView.Detalhe.class,BuscaView.Busca.class})
	private Long id;

	@Column(name = "STBU_TX_DESCRICAO")
	@JsonView(value = {PacienteView.Detalhe.class,BuscaView.Busca.class})
	private String descricao;

	@Column(name = "STBU_IN_BUSCA_ATIVA")
	@JsonView(value = {PacienteView.Detalhe.class,BuscaView.Busca.class})
	private Boolean buscaAtiva;

	public StatusBusca() {}

	public StatusBusca(Long idStatus) {
		this.id = idStatus;
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

	/**
	 * 0 - para status da busca inativa e 1 - ativa.
	 * 
	 * @return buscaAtiva
	 */
	public Boolean getBuscaAtiva() {
		return buscaAtiva;
	}

	/**
	 * 0 - para status da busca inativa e 1 - ativa.
	 * 
	 * @return buscaAtiva
	 */
	public void setBuscaAtiva(Boolean buscaAtiva) {
		this.buscaAtiva = buscaAtiva;
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
		StatusBusca other = (StatusBusca) obj;
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
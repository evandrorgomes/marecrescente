package br.org.cancer.modred.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.DoadorView;
import br.org.cancer.modred.model.domain.AcaoDoadorInativo;
import br.org.cancer.modred.model.security.Recurso;

/**
 * Classe de persistencia para Motivo Status Doador.
 * 
 * @author Fillipe Queiroz
 */
@Entity
@Table(name = "MOTIVO_STATUS_DOADOR")
public class MotivoStatusDoador implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final Long OBITO = 1L;
	public static final Long PRESIDIARIO = 2L;
	public static final Long GRAVIDEZ = 3L;
	public static final Long NAO_CONTACTADO = 4L;

	@Id
	@Column(name = "MOSD_ID")
	@JsonView({DoadorView.ContatoPassivo.class})
	private Long id;

	@Column(name = "MOSD_TX_DESCRICAO")
	private String descricao;

	@ManyToOne
	@JoinColumn(name = "STDO_ID")
	@Fetch(FetchMode.JOIN)
	private StatusDoador statusDoador;
	
	@Column(name = "MOSD_IN_ACAO_DOADOR_INATIVO")
	@JsonView({DoadorView.ContatoPassivo.class})
	private Long acaoDoadorInativo;
	
	@ManyToMany
	@JoinTable(
		name="MOTIVO_STATUS_DOADOR_RECURSO"
		, joinColumns={
			@JoinColumn(name="MOSD_ID")
			}
		, inverseJoinColumns={
			@JoinColumn(name="RECU_ID")
			}
		)
	private List<Recurso> recursos;
	
	
	public MotivoStatusDoador() {}

	public MotivoStatusDoador(Long idStatus) {
		this.id = idStatus;
	}

	/**
	 * Chave primaria de motivo de status do doador.
	 * 
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Chave primaria de motivo de status do doador.
	 * 
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Descricao do status.
	 * 
	 * @return the descricao
	 */
	public String getDescricao() {
		return descricao;
	}

	/**
	 * Descricao do status.
	 * 
	 * @param descricao the descricao to set
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	/**
	 * Entidade de status do doador.
	 * 
	 * @return
	 */
	public StatusDoador getStatusDoador() {
		return statusDoador;
	}

	/**
	 * Entidade de status do doador.
	 * 
	 * @param statusDoador
	 */
	public void setStatusDoador(StatusDoador statusDoador) {
		this.statusDoador = statusDoador;
	}

	
	/**
	 * @return the recursos
	 */
	public List<Recurso> getRecursos() {
		return recursos;
	}

	/**
	 * @param recursos the recursos to set
	 */
	public void setRecursos(List<Recurso> recursos) {
		this.recursos = recursos;
	}
	
	/**
	 * @return the acaoDoadorInativo
	 */
	public AcaoDoadorInativo getAcaoDoadorInativo() {
		if (this.acaoDoadorInativo != null) {
			return AcaoDoadorInativo.valueById(this.acaoDoadorInativo);
		}
		return null;
	}

	/**
	 * @param acaoDoadorInativo the acaoDoadorInativo to set
	 */
	public void setAcaoDoadorInativo(AcaoDoadorInativo acaoDoadorInativo) {
		this.acaoDoadorInativo = null;
		if (acaoDoadorInativo != null) {
			this.acaoDoadorInativo = acaoDoadorInativo.getId();
		}
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
		result = prime * result + ( ( statusDoador == null ) ? 0 : statusDoador.hashCode() );
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
		MotivoStatusDoador other = (MotivoStatusDoador) obj;
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
		if (statusDoador == null) {
			if (other.statusDoador != null) {
				return false;
			}
		}
		else
			if (!statusDoador.equals(other.statusDoador)) {
				return false;
			}
		return true;
	}

	/**
	 * Indica quando o tempo de afastamento precisa ser informado. 
	 * 
	 * @return TRUE se obrigatório.
	 */
	public Boolean isTempoAfastamentoObrigatorio() {
		return statusDoador == null ? null : statusDoador.getTempoObrigatorio();
	}

}
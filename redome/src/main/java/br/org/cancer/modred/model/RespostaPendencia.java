package br.org.cancer.modred.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.AvaliacaoView;
import br.org.cancer.modred.model.annotations.AssertTrueCustom;
import br.org.cancer.modred.model.security.CriacaoAuditavel;
import br.org.cancer.modred.model.security.Usuario;

/**
 * Classe de resposta de uma pendencia.
 * 
 * @author Fillipe Queiroz
 */
@Entity
@SequenceGenerator(name = "SQ_REPE_ID", sequenceName = "SQ_REPE_ID", allocationSize = 1)
@Table(name = "RESPOSTA_PENDENCIA")

public class RespostaPendencia extends CriacaoAuditavel implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_REPE_ID")
	@Column(name = "REPE_ID")
	private Long id;

	/**
	 * Atributo utilizado quando uma resposta é salva com o perfil de médico, idicando que essa resposta responde uma pendência.
	 */
	@Transient
	private Boolean respondePendencia = true;

	@ManyToOne
	@JoinColumn(name = "EVOL_ID")
	@JsonView({ AvaliacaoView.ListarPendencias.class })
	private Evolucao evolucao;

	@ManyToOne
	@JoinColumn(name = "EXAM_ID")
	@JsonView({ AvaliacaoView.ListarPendencias.class })
	private ExamePaciente exame;

	@Column(name = "REPE_DT_DATA")
	@NotNull
	private LocalDateTime dataCriacao;

	@Column(name = "REPE_TX_RESPOSTA")
	@Length(max = 200)
	private String resposta;

	@ManyToOne
	@JoinColumn(name = "USUA_ID")
	@NotNull
	@JsonView({ AvaliacaoView.ListarPendencias.class })
	private Usuario usuario;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(	name = "ASSOCIA_RESPOSTA_PENDENCIA",
				joinColumns =
	{ @JoinColumn(name = "REPE_ID") },
				inverseJoinColumns =
	{ @JoinColumn(name = "PEND_ID") })
	@NotNull
	@JsonProperty(access = Access.WRITE_ONLY)
	private List<Pendencia> pendencias;

	public RespostaPendencia() {}

	/**
	 * Retorna a chave primaria da resposta pendencia.
	 * 
	 * @return
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Seta a chave primaria da resposta pendencia.
	 * 
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Retorna a evolucao.
	 * 
	 * @return
	 */
	public Evolucao getEvolucao() {
		return evolucao;
	}

	/**
	 * Seta a evolucao.
	 * 
	 * @param evolucao
	 */
	public void setEvolucao(Evolucao evolucao) {
		this.evolucao = evolucao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.org.cancer.modred.model.Auditavel#getDataCriacao()
	 */
	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.org.cancer.modred.model.Auditavel#setDataCriacao(java.time. LocalDateTime)
	 */
	public void setDataCriacao(LocalDateTime dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.org.cancer.modred.model.Auditavel#getUsuario()
	 */
	public Usuario getUsuario() {
		return usuario;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.org.cancer.modred.model.Auditavel#setUsuario(br.org.cancer.modred. model.Usuario)
	 */
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	/**
	 * Retorna o exame da resposta pendencia.
	 * 
	 * @return exame
	 */
	public ExamePaciente getExame() {
		return exame;
	}

	/**
	 * Seta o exame da resposta pendencia.
	 * 
	 * @param exame
	 */
	public void setExame(ExamePaciente exame) {
		this.exame = exame;
	}

	/**
	 * Recupera a resposta.
	 * 
	 * @return resposta
	 */
	public String getResposta() {
		return resposta;
	}

	/**
	 * Seta a resposta.
	 * 
	 * @param resposta
	 */
	public void setResposta(String resposta) {
		this.resposta = resposta;
	}

	/**
	 * @return the pendencias
	 */
	public List<Pendencia> getPendencias() {
		return pendencias;
	}

	/**
	 * @param pendencias the pendencias to set
	 */
	public void setPendencias(List<Pendencia> pendencias) {
		this.pendencias = pendencias;
	}

	@AssertTrueCustom(	message = "{javax.validation.constraints.NotNull.message}",
						field = "resposta")
	@JsonIgnore
	public boolean isRespostaValida() {
		return ( ( exame != null && exame.getId() != null ) || ( evolucao != null
				&& evolucao.getId() != null ) || !StringUtils.isEmpty(resposta) );
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
		if (dataCriacao != null) {
			result = prime * result + ( ( dataCriacao == null ) ? 0 : dataCriacao.hashCode() );
		}
		if (id != null) {
			result = prime * result + (int) ( id ^ ( id >>> 32 ) );
		}
		if (resposta != null) {
			result = prime * result + ( ( resposta == null ) ? 0 : resposta.hashCode() );
		}
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
		RespostaPendencia other = (RespostaPendencia) obj;
		if (dataCriacao == null) {
			if (other.dataCriacao != null) {
				return false;
			}
		}
		else
			if (!dataCriacao.equals(other.dataCriacao)) {
				return false;
			}
		if (id != other.id) {
			return false;
		}
		if (resposta == null) {
			if (other.resposta != null) {
				return false;
			}
		}
		else
			if (!resposta.equals(other.resposta)) {
				return false;
			}
		return true;
	}

	/**
	 * Retorna a data criação formatada em dd/mm/yy hh:mm.
	 * 
	 * @return dataFormatada.
	 */
	public String getDataFormatadaDialogo() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm");
		if (dataCriacao != null) {
			return dataCriacao.format(formatter);
		}
		return null;

	}

	/**
	 * @return the respondePendencia
	 */
	public Boolean getRespondePendencia() {
		return respondePendencia;
	}

	/**
	 * @param respondePendencia the respondePendencia to set
	 */
	public void setRespondePendencia(Boolean respondePendencia) {
		this.respondePendencia = respondePendencia;
	}

}
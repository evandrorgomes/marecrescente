package br.org.cancer.modred.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.AvaliacaoView;
import br.org.cancer.modred.model.annotations.EnumValues;
import br.org.cancer.modred.model.domain.StatusAvaliacao;
import br.org.cancer.modred.service.impl.custom.EntityObservable;
import br.org.cancer.modred.service.impl.observers.AvaliacaoPacienteReprovadoObserver;

/**
 * Classe que representa uma avaliação.
 * 
 * @author Fillipe Queiroz
 *
 */
@Entity
@SequenceGenerator(name = "SQ_AVAL_ID", sequenceName = "SQ_AVAL_ID", allocationSize = 1)
@Table(name = "AVALIACAO")
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)

public class Avaliacao extends EntityObservable implements Serializable {

	private static final long serialVersionUID = -308702074383591406L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_AVAL_ID")
	@Column(name = "AVAL_ID")
	@JsonView(AvaliacaoView.Avaliacao.class)
	private Long id;

	@Column(name = "AVAL_DT_RESULTADO")
	private LocalDateTime dataResultado;

	@ManyToOne
	@JoinColumn(name = "PACI_NR_RMR")
	@NotNull
	@JsonView(AvaliacaoView.Avaliacao.class)
	private Paciente paciente;

	@Column(name = "AVAL_IN_RESULTADO")
	@JsonView(AvaliacaoView.Avaliacao.class)
	private Boolean aprovado;

	@ManyToOne
	@JoinColumn(name = "MEDI_ID_AVALIADOR")
	@JsonView(AvaliacaoView.Avaliacao.class)
	private Medico medicoResponsavel;

	@Column(name = "AVAL_TX_OBSERVACAO")
	private String observacao;

	@ManyToOne
	@JoinColumn(name = "CETR_ID")
	@NotNull
	private CentroTransplante centroAvaliador;

	@EnumValues(StatusAvaliacao.class)
	@Column(name = "AVAL_IN_STATUS")
	@NotNull
	@JsonView(AvaliacaoView.Avaliacao.class)
	private Integer status;

	@Column(name = "AVAL_DT_CRIACAO")
	private LocalDateTime dataCriacao;

	@OneToMany(	mappedBy = "avaliacao", fetch = FetchType.LAZY, cascade = { CascadeType.REMOVE, CascadeType.PERSIST },
				orphanRemoval = true)
	@JsonIgnore
	private List<Pendencia> pendencias;
	
	@Column(name = "AVAL_TX_MOTIVO_CANCELAMENTO")
	private String motivoCancelamento;

	
	public Avaliacao() {
		super();
		addObserver(AvaliacaoPacienteReprovadoObserver.class);
	}
	
	/**
	 * Retorna a chave primária da avaliação.
	 * 
	 * @return
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Seta a chave primária da avaliação.
	 * 
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Retorna o a data de inserção do resultado.
	 * 
	 * @return
	 */
	public LocalDateTime getDataResultado() {
		return dataResultado;
	}

	/**
	 * Seta a data de inserção do resultado.
	 * 
	 * @param dataResultado
	 */
	public void setDataResultado(LocalDateTime dataResultado) {
		this.dataResultado = dataResultado;
	}

	/**
	 * Retorna o paciente a ser avaliado.
	 * 
	 * @return
	 */
	public Paciente getPaciente() {
		return paciente;
	}

	/**
	 * Seta o paciente a ser avaliado.
	 * 
	 * @param paciente
	 */
	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	/**
	 * Verifica se a avaliacao foi aprovada.
	 * 
	 * @return
	 */
	public Boolean getAprovado() {
		return aprovado;
	}

	/**
	 * Seta se a avaliacao foi aprovada.
	 * 
	 * @param aprovado
	 */
	public void setAprovado(Boolean aprovado) {
		this.aprovado = aprovado;
	}

	/**
	 * Retorna o medico responsavel pela avaliação.
	 * 
	 * @return
	 */
	public Medico getMedicoResponsavel() {
		return medicoResponsavel;
	}

	/**
	 * Seta o médico responsavel pela avaliacao.
	 * 
	 * @param medico
	 */
	public void setMedicoResponsavel(Medico medico) {
		this.medicoResponsavel = medico;
	}

	/**
	 * Observacao ao fechar a avaliação.
	 * 
	 * @return
	 */
	public String getObservacao() {
		return observacao;
	}

	/**
	 * Seta a Observacao ao fechar a avaliação.
	 * 
	 * @param observacao
	 */
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	/**
	 * Recupera o centro avaliador responsavel pela avaliação.
	 * 
	 * @return
	 */
	public CentroTransplante getCentroAvaliador() {
		return centroAvaliador;
	}

	/**
	 * Seta o centro avaliador responsavel pela avaliação.
	 * 
	 * @param centroAvaliador
	 */
	public void setCentroAvaliador(CentroTransplante centroAvaliador) {
		this.centroAvaliador = centroAvaliador;
	}


	
	/**
	 * Status da avaliação.
	 * @return status -
	 */
	public Integer getStatus() {
		return status;
	}

	
	/**
	 * Status da avaliação.
	 * @param status -
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * @return the dataCriacao
	 */
	public LocalDateTime getDataCriacao() {
		return this.dataCriacao;
	}

	/**
	 * @param dataCriacao the dataCriacao to set
	 */
	public void setDataCriacao(LocalDateTime dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	/**
	 * Retorna as pendencias de uma avaliacao.
	 * 
	 * @return
	 */
	public List<Pendencia> getPendencias() {
		return pendencias;
	}

	/**
	 * Seta as pendencias de uma avaliacao.
	 * 
	 * @param pendencias
	 */
	public void setPendencias(List<Pendencia> pendencias) {
		this.pendencias = pendencias;
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
		result = prime * result + ( ( dataCriacao == null ) ? 0 : dataCriacao.hashCode() );
		result = prime * result + ( ( paciente == null ) ? 0 : paciente.hashCode() );
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
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
		Avaliacao other = (Avaliacao) obj;
		if (dataCriacao == null) {
			if (other.dataCriacao != null) {
				return false;
			}
		}
		else {
			if (!dataCriacao.equals(other.dataCriacao)) {
				return false;
			}
		}
		if (paciente == null) {
			if (other.paciente != null) {
				return false;
			}
		}
		else {
			if (!paciente.equals(other.paciente)) {
				return false;
			}
		}
		return true;
	}

	
	/**
	 * Texto com o motivo do cancelamento.
	 * 
	 * @return
	 */
	public String getMotivoCancelamento() {
		return motivoCancelamento;
	}

	/**
	 * Texto com o motivo do cancelamento.
	 * 
	 * @param motivoCancelamento
	 */
	public void setMotivoCancelamento(String motivoCancelamento) {
		this.motivoCancelamento = motivoCancelamento;
	}
	
	public boolean emAndamento(){
		return this.getMedicoResponsavel() != null && StatusAvaliacao.ABERTA.getId().equals(this.getStatus());
	}
	
	public boolean jaAvaliada(){
		return this.getMedicoResponsavel() != null && this.getAprovado() != null;
	}
	
	public boolean aprovada(){
		return this.getMedicoResponsavel() != null && this.getAprovado() != null && this.getAprovado();
	}

}

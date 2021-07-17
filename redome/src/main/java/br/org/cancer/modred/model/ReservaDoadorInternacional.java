package br.org.cancer.modred.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.org.cancer.modred.model.domain.StatusReservaDoadorInternacional;

/**
 * Classe que representa uma reserva do doador internacional para o paciente. Neste ponto do sistema, somente pode haver um novo
 * doador internacional associado ao paciente.
 * 
 * @author Pizão
 */
@Entity
@Table(name = "RESERVA_DOADOR_INTERNACIONAL")
@SequenceGenerator(name = "SQ_REDI_ID", sequenceName = "SQ_REDI_ID", allocationSize = 1)
public class ReservaDoadorInternacional implements Serializable {

	private static final long serialVersionUID = 2075995350171897884L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_REDI_ID")
	@Column(name = "REDI_ID")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "BUSC_ID")
	private Busca busca;

	@ManyToOne
	@JoinColumn(name = "DOAD_ID")
	private Doador doador;

	@Enumerated(EnumType.STRING)
	@Column(name = "REDI_TX_STATUS")
	private StatusReservaDoadorInternacional status;

	@Column(name = "REDI_DT_ATUALIZACAO")
	private LocalDateTime dataAtualizacao;

	public ReservaDoadorInternacional() {}

	/**
	 * Construtor especializado para instanciar, já com ID, um novo match.
	 * 
	 * @param id ID do match.
	 */
	public ReservaDoadorInternacional(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Busca getBusca() {
		return busca;
	}

	public void setBusca(Busca busca) {
		this.busca = busca;
	}

	public LocalDateTime getDataAtualizacao() {
		return dataAtualizacao;
	}

	public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}

	public StatusReservaDoadorInternacional getStatus() {
		return status;
	}

	public void setStatus(StatusReservaDoadorInternacional status) {
		this.status = status;
	}

	/**
	 * @return the doador
	 */
	public Doador getDoador() {
		return doador;
	}

	/**
	 * @param doador the doador to set
	 */
	public void setDoador(Doador doador) {
		this.doador = doador;
	}

}
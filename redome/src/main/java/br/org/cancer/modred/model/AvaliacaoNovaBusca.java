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
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.AvaliacaoNovaBuscaView;
import br.org.cancer.modred.model.domain.StatusAvaliacaoNovaBusca;
import br.org.cancer.modred.model.security.Usuario;
import br.org.cancer.modred.service.impl.custom.EntityObservable;
import br.org.cancer.modred.service.impl.observers.AvaliacaoNovaBuscaAprovadaObserver;
import br.org.cancer.modred.service.impl.observers.AvaliacaoNovaBuscaReprovadaObserver;
import br.org.cancer.modred.service.impl.observers.AvaliacaoNovaBuscaSolicitadaObserver;

/**
 * Avaliação da nova busca criada para o paciente.
 * Esta é avaliada pela equipe Redome.
 * 
 * @author Pizão
 *
 */
@Entity
@SequenceGenerator(name = "SQ_AVNB_ID", sequenceName = "SQ_AVNB_ID", allocationSize = 1)
@Table(name = "AVALIACAO_NOVA_BUSCA")
public class AvaliacaoNovaBusca extends EntityObservable implements Serializable {
	private static final long serialVersionUID = -3164067807867300311L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_AVNB_ID")
	@Column(name = "AVNB_ID")
	@JsonView(AvaliacaoNovaBuscaView.ListarTarefas.class)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "PACI_NR_RMR")
	@NotNull
	@JsonView(AvaliacaoNovaBuscaView.ListarTarefas.class)
	private Paciente paciente;
	
	@Column(name = "AVNB_DT_CRIACAO")
	@JsonView(AvaliacaoNovaBuscaView.ListarTarefas.class)
	@NotNull
	private LocalDateTime dataCriacao;

	@Enumerated(EnumType.STRING)
	@Column(name = "AVNB_TX_STATUS")
	@NotNull
	@JsonView(AvaliacaoNovaBuscaView.ListarTarefas.class)
	private StatusAvaliacaoNovaBusca status;

	@ManyToOne
	@JoinColumn(name = "USUA_ID_AVALIADOR")
	private Usuario avaliador;
	
	@Column(name = "AVNB_DT_AVALIADO")
	private LocalDateTime dataAvaliado;

	@Column(name = "AVNB_TX_JUSTIFICATIVA")
	private String justificativa;

	/**
	 * Construtor que define os observers envolvidos no
	 * processamento desta entidade.
	 */
	public AvaliacaoNovaBusca() {
		super();
		addObserver(AvaliacaoNovaBuscaSolicitadaObserver.class);
		addObserver(AvaliacaoNovaBuscaAprovadaObserver.class);
		addObserver(AvaliacaoNovaBuscaReprovadaObserver.class);
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public LocalDateTime getDataAvaliado() {
		return dataAvaliado;
	}


	public void setDataAvaliado(LocalDateTime dataAvaliado) {
		this.dataAvaliado = dataAvaliado;
	}


	public Paciente getPaciente() {
		return paciente;
	}


	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}


	public StatusAvaliacaoNovaBusca getStatus() {
		return status;
	}


	public void setStatus(StatusAvaliacaoNovaBusca status) {
		this.status = status;
	}


	public Usuario getAvaliador() {
		return avaliador;
	}


	public void setAvaliador(Usuario avaliador) {
		this.avaliador = avaliador;
	}


	public String getJustificativa() {
		return justificativa;
	}


	public void setJustificativa(String justificativa) {
		this.justificativa = justificativa;
	}


	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}


	public void setDataCriacao(LocalDateTime dataCriacao) {
		this.dataCriacao = dataCriacao;
	}
	
}

package br.org.cancer.modred.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.PedidoTransferenciaCentroView;
import br.org.cancer.modred.model.domain.TiposTransferenciaCentro;
import br.org.cancer.modred.model.security.Usuario;

/**
 * Classe que reprensenta o Pedido de transfêrencia de um paciente.
 * 
 * @author brunosousa
 *
 */
@Entity
@SequenceGenerator(name = "SQ_PETC_ID", sequenceName = "SQ_PETC_ID", allocationSize = 1)
@Table(name = "PEDIDO_TRANSFERENCIA_CENTRO")
public class PedidoTransferenciaCentro implements Serializable {

	private static final long serialVersionUID = 3263136901187790374L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_PETC_ID")
	@Column(name = "PETC_ID")
	@JsonView({PedidoTransferenciaCentroView.ListarTarefas.class, PedidoTransferenciaCentroView.Detalhe.class})
	private Long id;
	
	@Column(name = "PETC_DT_CRIACAO")
	private LocalDateTime dataCriacao;
	
	@Column(name = "PETC_DT_ATUALIZACAO")
	private LocalDateTime dataAtualizacao;
	
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "PETC_IN_TIPO")
	@NotNull
	private TiposTransferenciaCentro tipoTransferenciaCentro;
	
	@Column(name = "PETC_IN_APROVADO")
	private Boolean aprovado;
	
	@ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "PACI_NR_RMR")
	@NotNull
	@JsonView({PedidoTransferenciaCentroView.ListarTarefas.class, PedidoTransferenciaCentroView.Detalhe.class})
	private Paciente paciente;
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "CETR_ID_ORIGEM")
	@NotNull
	@JsonView(PedidoTransferenciaCentroView.Detalhe.class)
	private CentroTransplante centroAvaliadorOrigem;
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "CETR_ID_DESTINO")
	@NotNull
	private CentroTransplante centroAvaliadorDestino;
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "USUA_ID")
	private Usuario usuario;
	
	@Column(name = "PETC_TX_JUSTIFICATIVA")
	private String justificativa;

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
	 * @return the dataCriacao
	 */
	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}

	/**
	 * @return the dataAtualizacao
	 */
	public LocalDateTime getDataAtualizacao() {
		return dataAtualizacao;
	}

	/**
	 * @return the tipoTransferenciaCentro
	 */
	public TiposTransferenciaCentro getTipoTransferenciaCentro() {
		return tipoTransferenciaCentro;
	}

	/**
	 * @param tipoTransferenciaCentro the tipoTransferenciaCentro to set
	 */
	public void setTipoTransferenciaCentro(TiposTransferenciaCentro tipoTransferenciaCentro) {
		this.tipoTransferenciaCentro = tipoTransferenciaCentro;
	}

	/**
	 * @return the aprovado
	 */
	public Boolean isAprovado() {
		return aprovado;
	}

	/**
	 * @param aprovado the aprovado to set
	 */
	public void setAprovado(Boolean aprovado) {
		this.aprovado = aprovado;
	}

	/**
	 * @return the paciente
	 */
	public Paciente getPaciente() {
		return paciente;
	}

	/**
	 * @param paciente the paciente to set
	 */
	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	/**
	 * @return the centroAvaliadorOrigem
	 */
	public CentroTransplante getCentroAvaliadorOrigem() {
		return centroAvaliadorOrigem;
	}

	/**
	 * @param centroAvaliadorOrigem the centroAvaliadorOrigem to set
	 */
	public void setCentroAvaliadorOrigem(CentroTransplante centroAvaliadorOrigem) {
		this.centroAvaliadorOrigem = centroAvaliadorOrigem;
	}

	/**
	 * @return the centroAvaliadorDestino
	 */
	public CentroTransplante getCentroAvaliadorDestino() {
		return centroAvaliadorDestino;
	}

	/**
	 * @param centroAvaliadorDestino the centroAvaliadorDestino to set
	 */
	public void setCentroAvaliadorDestino(CentroTransplante centroAvaliadorDestino) {
		this.centroAvaliadorDestino = centroAvaliadorDestino;
	}

	/**
	 * @return the usuario
	 */
	public Usuario getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	/**
	 * @return the justificativa
	 */
	public String getJustificativa() {
		return justificativa;
	}

	/**
	 * @param justificativa the justificativa to set
	 */
	public void setJustificativa(String justificativa) {
		this.justificativa = justificativa;
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
		PedidoTransferenciaCentro other = (PedidoTransferenciaCentro) obj;
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
	 * Método que é chamado antes de persistir ou alterar o paciente para coloca o nome fonetizado.
	 */
	@PrePersist	
	private void prePersist() {
		this.dataCriacao = LocalDateTime.now();
		this.dataAtualizacao = LocalDateTime.now();
	}
	
	@PreUpdate
	private void preUpdate() {
		this.dataAtualizacao = LocalDateTime.now();
	}

	public Boolean getAprovado() {
		return aprovado;
	}

	public void setDataCriacao(LocalDateTime dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}

	
	
	

}

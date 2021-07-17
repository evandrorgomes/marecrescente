package br.org.cancer.modred.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

/**
 * Dados de transporte terrestre associado a necessidade de logística identificada para determinado doador.
 * 
 * @author Pizão
 */
@Entity
@Table(name = "TRANSPORTE_TERRESTRE")
@SequenceGenerator(name = "SQ_TRTE_ID", sequenceName = "SQ_TRTE_ID", allocationSize = 1)
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
public class TransporteTerrestre implements Serializable {

	private static final long serialVersionUID = -8380808700325833021L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_TRTE_ID")
	@Column(name = "TRTE_ID")
	private Long id;

	@Column(name = "TRTE_DT_DATA")
	@NotNull
	private LocalDateTime dataRealizacao;

	@Column(name = "TRTE_TX_ORIGEM")
	@Length(max = 200)
	private String origem;

	@Column(name = "TRTE_TX_DESTINO")
	@Length(max = 200)
	private String destino;

	@Column(name = "TRTE_TX_OBJETO_TRANSPORTE")
	@Length(max = 200)
	private String objetoTransportado;

	@Column(name = "TRTE_DT_CRIACAO")
	@NotNull
	private LocalDateTime dataCriacao;

	@Column(name = "TRTE_IN_EXCLUIDO")
	@NotNull
	private Boolean excluido;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PELO_ID")
	@JsonProperty(access = Access.WRITE_ONLY)
	private PedidoLogistica pedidoLogistica;

	/**
	 * Construtor já setando a data de criação e o excluido como false.
	 */
	public TransporteTerrestre() {
		super();
		this.dataCriacao = LocalDateTime.now();
		this.excluido = false;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(LocalDateTime dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public LocalDateTime getDataRealizacao() {
		return dataRealizacao;
	}

	public void setDataRealizacao(LocalDateTime dataRealizacao) {
		this.dataRealizacao = dataRealizacao;
	}

	public String getOrigem() {
		return origem;
	}

	public void setOrigem(String origem) {
		this.origem = origem;
	}

	public String getDestino() {
		return destino;
	}

	public void setDestino(String destino) {
		this.destino = destino;
	}

	public String getObjetoTransportado() {
		return objetoTransportado;
	}

	public void setObjetoTransportado(String objetoTransportado) {
		this.objetoTransportado = objetoTransportado;
	}

	public Boolean getExcluido() {
		return excluido;
	}

	public void setExcluido(Boolean excluido) {
		this.excluido = excluido;
	}

	public PedidoLogistica getPedidoLogistica() {
		return pedidoLogistica;
	}

	public void setPedidoLogistica(PedidoLogistica pedidoLogistica) {
		this.pedidoLogistica = pedidoLogistica;
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
		result = prime * result + ( ( dataRealizacao == null ) ? 0 : dataRealizacao.hashCode() );
		result = prime * result + ( ( destino == null ) ? 0 : destino.hashCode() );
		result = prime * result + ( ( excluido == null ) ? 0 : excluido.hashCode() );
		result = prime * result + ( ( id == null ) ? 0 : id.hashCode() );
		result = prime * result + ( ( objetoTransportado == null ) ? 0 : objetoTransportado.hashCode() );
		result = prime * result + ( ( origem == null ) ? 0 : origem.hashCode() );
		result = prime * result + ( ( pedidoLogistica == null ) ? 0 : pedidoLogistica.hashCode() );
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
		if (!( obj instanceof TransporteTerrestre )) {
			return false;
		}
		TransporteTerrestre other = (TransporteTerrestre) obj;
		if (dataCriacao == null) {
			if (other.dataCriacao != null) {
				return false;
			}
		}
		else
			if (!dataCriacao.equals(other.dataCriacao)) {
				return false;
			}
		if (dataRealizacao == null) {
			if (other.dataRealizacao != null) {
				return false;
			}
		}
		else
			if (!dataRealizacao.equals(other.dataRealizacao)) {
				return false;
			}
		if (destino == null) {
			if (other.destino != null) {
				return false;
			}
		}
		else
			if (!destino.equals(other.destino)) {
				return false;
			}
		if (excluido == null) {
			if (other.excluido != null) {
				return false;
			}
		}
		else
			if (!excluido.equals(other.excluido)) {
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
		if (objetoTransportado == null) {
			if (other.objetoTransportado != null) {
				return false;
			}
		}
		else
			if (!objetoTransportado.equals(other.objetoTransportado)) {
				return false;
			}
		if (origem == null) {
			if (other.origem != null) {
				return false;
			}
		}
		else
			if (!origem.equals(other.origem)) {
				return false;
			}
		if (pedidoLogistica == null) {
			if (other.pedidoLogistica != null) {
				return false;
			}
		}
		else
			if (!pedidoLogistica.equals(other.pedidoLogistica)) {
				return false;
			}
		return true;
	}

}
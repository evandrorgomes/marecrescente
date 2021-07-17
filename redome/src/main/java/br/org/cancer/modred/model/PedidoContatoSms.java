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

/**
 * Representa o pedido de contato para determinada solicitação.
 * 
 * @author Pizão
 * 
 */
@Entity
@SequenceGenerator(name = "SQ_PECS_ID", sequenceName = "SQ_PECS_ID", allocationSize = 1)
@Table(name = "PEDIDO_CONTATO_SMS")
public class PedidoContatoSms implements Serializable {

	private static final long serialVersionUID = 7774495959241636184L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_PECS_ID")
	@Column(name = "PECS_ID")	
	private Long id;

	@Column(name = "PECS_DT_CRIACAO")
	@NotNull
	private LocalDateTime dataCriacao;

	@Column(name = "PECS_IN_ABERTO")
	@NotNull
	private boolean aberto;

	@ManyToOne
	@JoinColumn(name = "PECO_ID")
	@NotNull
	private PedidoContato pedidoContato;
		
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "pedidoContatoSms", fetch = FetchType.LAZY)
	@NotNull
	private List<SmsEnviado> smsEnviados;

	public PedidoContatoSms() {
		dataCriacao = LocalDateTime.now();
		aberto = true;
	}

	public PedidoContatoSms(PedidoContato pedidoContato) {
		this();
		this.pedidoContato = pedidoContato;
	}



	/**
	 * Identificador da entidade.
	 * 
	 * @return ID da entidade.
	 */
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Data de realização da tentativa de contato.
	 * 
	 * @return data de criação da tentativa.
	 */
	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(LocalDateTime dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public boolean isAberto() {
		return aberto;
	}

	public void setAberto(boolean aberto) {
		this.aberto = aberto;
	}

	/**
	 * @return the pedidoContato
	 */
	public PedidoContato getPedidoContato() {
		return pedidoContato;
	}

	/**
	 * @param pedidoContato the pedidoContato to set
	 */
	public void setPedidoContato(PedidoContato pedidoContato) {
		this.pedidoContato = pedidoContato;
	}

	/**
	 * @return the smsEnviados
	 */
	public List<SmsEnviado> getSmsEnviados() {
		return smsEnviados;
	}

	/**
	 * @param smsEnviados the smsEnviados to set
	 */
	public void setSmsEnviados(List<SmsEnviado> smsEnviados) {
		this.smsEnviados = smsEnviados;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ( aberto ? 1231 : 1237 );
		result = prime * result + ( ( dataCriacao == null ) ? 0 : dataCriacao.hashCode() );
		result = prime * result + ( ( id == null ) ? 0 : id.hashCode() );
		result = prime * result + ( ( pedidoContato == null ) ? 0 : pedidoContato.hashCode() );
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!( obj instanceof PedidoContatoSms )) {
			return false;
		}
		PedidoContatoSms other = (PedidoContatoSms) obj;
		if (aberto != other.aberto) {
			return false;
		}
		if (dataCriacao == null) {
			if (other.dataCriacao != null) {
				return false;
			}
		}
		else
			if (!dataCriacao.equals(other.dataCriacao)) {
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
		if (pedidoContato == null) {
			if (other.pedidoContato != null) {
				return false;
			}
		}
		else
			if (!pedidoContato.equals(other.pedidoContato)) {
				return false;
			}
		return true;
	}

}

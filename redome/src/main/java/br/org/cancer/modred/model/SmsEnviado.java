package br.org.cancer.modred.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import br.org.cancer.modred.gateway.sms.StatusSms;

/**
 * Classe que representa o envio de sms para os contatos telefonicos do doador.
 * 
 * @author brunosousa
 *
 */
@Entity
@SequenceGenerator(name = "SQ_SMEN_ID", sequenceName = "SQ_SMEN_ID", allocationSize = 1)
@Table(name = "SMS_ENVIADO")
public class SmsEnviado implements Serializable {
	
	private static final long serialVersionUID = -2688167250627304367L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_SMEN_ID")
	@Column(name = "SMEN_ID")	
	private Long id;
	
	@Column(name = "SMEN_DT_CRIACAO")
	@NotNull
	private LocalDateTime dataCriacao;
	
	@ManyToOne
	@JoinColumn(name = "PECS_ID")
	@NotNull
	private PedidoContatoSms pedidoContatoSms;
	
	@ManyToOne
	@JoinColumn(name = "COTE_ID")
	@NotNull
	private ContatoTelefonicoDoador contatoTelefonicoDoador;
	
	@Column(name= "SMEN_TX_IDENTICACA_GATEWAY_SMS")
	private String identificacaoGatewaySms;
	
	@Column(name= "SMEN_IN_IDENTICACA_GATEWAY_SMS")	
	private Integer statusSms;
	
	public SmsEnviado() {
		this.dataCriacao = LocalDateTime.now();
	}

	/**
	 * Método construtor com os atributos obrigatórios.
	 * 
	 * @param pedidoContatoSms Pedido de contato sms do doador.
	 * @param contatoTelefonicoDoador Contato Telefonico do doador. 
	 */
	public SmsEnviado(PedidoContatoSms pedidoContatoSms, ContatoTelefonicoDoador contatoTelefonicoDoador) {
		this();
		this.pedidoContatoSms = pedidoContatoSms;
		this.contatoTelefonicoDoador = contatoTelefonicoDoador;
		this.statusSms = StatusSms.AGUARDANDO_ENVIO.getId();
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
	 * @return the dataCriacao
	 */
	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}

	/**
	 * @param dataCriacao the dataCriacao to set
	 */
	public void setDataCriacao(LocalDateTime dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	/**
	 * @return the pedidoContatoSms
	 */
	public PedidoContatoSms getPedidoContatoSms() {
		return pedidoContatoSms;
	}

	/**
	 * @param pedidoContatoSms the pedidoContatoSms to set
	 */
	public void setPedidoContatoSms(PedidoContatoSms pedidoContatoSms) {
		this.pedidoContatoSms = pedidoContatoSms;
	}

	/**
	 * @return the contatoTelefonicoDoador
	 */
	public ContatoTelefonicoDoador getContatoTelefonicoDoador() {
		return contatoTelefonicoDoador;
	}

	/**
	 * @param contatoTelefonicoDoador the contatoTelefonicoDoador to set
	 */
	public void setContatoTelefonicoDoador(ContatoTelefonicoDoador contatoTelefonicoDoador) {
		this.contatoTelefonicoDoador = contatoTelefonicoDoador;
	}
	
	/**
	 * @return the identificacaoGatewaySms
	 */
	public String getIdentificacaoGatewaySms() {
		return identificacaoGatewaySms;
	}

	/**
	 * @param identificacaoGatewaySms the identificacaoGatewaySms to set
	 */
	public void setIdentificacaoGatewaySms(String identificacaoGatewaySms) {
		this.identificacaoGatewaySms = identificacaoGatewaySms;
	}

	/**
	 * @return the statusSms
	 */
	public StatusSms getStatusSms() {
		return statusSms != null ? StatusSms.get(statusSms) : null;
	}

	/**
	 * @param statusSms the statusSms to set
	 */
	public void setStatusSms(StatusSms statusSms) {
		this.statusSms = statusSms != null ? statusSms.getId() : null;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((contatoTelefonicoDoador == null) ? 0 : contatoTelefonicoDoador.hashCode());
		result = prime * result + ((dataCriacao == null) ? 0 : dataCriacao.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((pedidoContatoSms == null) ? 0 : pedidoContatoSms.hashCode());
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
		SmsEnviado other = (SmsEnviado) obj;
		if (contatoTelefonicoDoador == null) {
			if (other.contatoTelefonicoDoador != null) {
				return false;
			}
		} 
		else if (!contatoTelefonicoDoador.equals(other.contatoTelefonicoDoador)) {
			return false;
		}
		if (dataCriacao == null) {
			if (other.dataCriacao != null) {
				return false;
			}
		} 
		else if (!dataCriacao.equals(other.dataCriacao)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} 
		else if (!id.equals(other.id)) {
			return false;
		}
		if (pedidoContatoSms == null) {
			if (other.pedidoContatoSms != null) {
				return false;
			}
		} 
		else if (!pedidoContatoSms.equals(other.pedidoContatoSms)) {
			return false;
		}
		return true;
	}
	
}

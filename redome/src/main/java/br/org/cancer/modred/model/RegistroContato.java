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

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.RegistroContatoView;
import br.org.cancer.modred.model.security.Usuario;

/**
 * Registro de contatos telefonicos de pedido de contato.
 * 
 * @author Filipe Paes
 *
 */
@Entity
@SequenceGenerator(name = "REGISTRO_CONTATO", sequenceName = "SQ_REGO_ID", allocationSize = 1)
@Table(name = "REGISTRO_CONTATO")
public class RegistroContato implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5389390638340049026L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "REGISTRO_CONTATO")
	@Column(name = "REGO_ID")
	@JsonView(RegistroContatoView.Consulta.class)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "PECO_ID")
	@NotNull
	@JsonView(RegistroContatoView.Consulta.class)
	private PedidoContato pedidoContato;

	@Column(name = "REGO_DT_MOMENTO_CONTATO")
	@NotNull
	@JsonView(RegistroContatoView.Consulta.class)
	private LocalDateTime momentoLigacao;

	@ManyToOne
	@JoinColumn(name = "USUA_ID")
	@NotNull
	@JsonView(RegistroContatoView.Consulta.class)
	private Usuario usuario;

	@ManyToOne
	@JoinColumn(name = "RETO_ID")
	@NotNull
	@JsonView(RegistroContatoView.Consulta.class)
	private RegistroTipoOcorrencia registroTipoOcorrencia;
	
	
	@ManyToOne
	@JoinColumn(name = "COTE_ID")
	@NotNull
	@JsonView(RegistroContatoView.Consulta.class)
	private ContatoTelefonicoDoador contatoTelefonico;

	@Column(name = "REGO_TX_OBSERVACAO")
	@NotNull
	@Length(max = 255)
	@JsonView(RegistroContatoView.Consulta.class)
	private String observacao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public PedidoContato getPedidoContato() {
		return pedidoContato;
	}

	public void setPedidoContato(PedidoContato pedidoContato) {
		this.pedidoContato = pedidoContato;
	}

	public RegistroTipoOcorrencia getRegistroTipoOcorrencia() {
		return registroTipoOcorrencia;
	}

	public void setRegistroTipoOcorrencia(RegistroTipoOcorrencia registroTipoOcorrencia) {
		this.registroTipoOcorrencia = registroTipoOcorrencia;
	}

	public LocalDateTime getMomentoLigacao() {
		return momentoLigacao;
	}

	public void setMomentoLigacao(LocalDateTime momentoLigacao) {
		this.momentoLigacao = momentoLigacao;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	

	public ContatoTelefonicoDoador getContatoTelefonico() {
		return contatoTelefonico;
	}

	public void setContatoTelefonico(ContatoTelefonicoDoador contatoTelefonico) {
		this.contatoTelefonico = contatoTelefonico;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((momentoLigacao == null) ? 0 : momentoLigacao.hashCode());
		result = prime * result + ((pedidoContato == null) ? 0 : pedidoContato.hashCode());
		result = prime * result + ((registroTipoOcorrencia == null) ? 0 : registroTipoOcorrencia.hashCode());
		result = prime * result + ((usuario == null) ? 0 : usuario.hashCode());
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
		if (getClass() != obj.getClass()) {
			return false;
		}
		RegistroContato other = (RegistroContato) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} 
		else if (!id.equals(other.id)) {
			return false;
		}
		if (momentoLigacao == null) {
			if (other.momentoLigacao != null) {
				return false;
			}
		} 
		else if (!momentoLigacao.equals(other.momentoLigacao)) {
			return false;
		}
		if (pedidoContato == null) {
			if (other.pedidoContato != null) {
				return false;
			}
		} 
		else if (!pedidoContato.equals(other.pedidoContato)) {
			return false;
		}
		if (registroTipoOcorrencia == null) {
			if (other.registroTipoOcorrencia != null) {
				return false;
			}
		} 
		else if (!registroTipoOcorrencia.equals(other.registroTipoOcorrencia)) {
			return false;
		}
		if (usuario == null) {
			if (other.usuario != null) {
				return false;
			}
		} 
		else if (!usuario.equals(other.usuario)) {
			return false;
		}
		return true;
	}

}

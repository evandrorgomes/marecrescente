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

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.BuscaView;
import br.org.cancer.modred.model.security.Usuario;


/**
 * Classe para armazenamento de conversas entre médico e analista de busca 
 * na tela de match.
 * @author Filipe Paes
 */
@Entity
@SequenceGenerator(name = "SQ_DIBU_ID", sequenceName = "SQ_DIBU_ID", allocationSize = 1)
@Table(name="DIALOGO_BUSCA")
public class DialogoBusca implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_DIBU_ID")
	@Column(name="DIBU_ID")
	@JsonView(BuscaView.DialogoBusca.class)
	private Long id;

	@NotNull
	@Column(name="DIBU_DT_MOMENTO_MENSAGEM")
	@JsonView(BuscaView.DialogoBusca.class)
	private LocalDateTime dataHoraMensagem;

	@NotEmpty
	@Column(name="DIBU_TX_MENSAGEM")
	@JsonView(BuscaView.DialogoBusca.class)
	private String mensagem;

	@NotNull
	@ManyToOne
	@JoinColumn(name="BUSC_ID")
	@JsonView(BuscaView.DialogoBusca.class)
	private Busca busca;

	@NotNull
	@ManyToOne
	@JoinColumn(name="USUA_ID")
	@JsonView(BuscaView.DialogoBusca.class)
	private Usuario usuario;

	public DialogoBusca() {
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
	 * @return the dataHoraMensagem
	 */
	public LocalDateTime getDataHoraMensagem() {
		return dataHoraMensagem;
	}

	/**
	 * @param dataHoraMensagem the dataHoraMensagem to set
	 */
	public void setDataHoraMensagem(LocalDateTime dataHoraMensagem) {
		this.dataHoraMensagem = dataHoraMensagem;
	}

	/**
	 * @return the mensagem
	 */
	public String getMensagem() {
		return mensagem;
	}

	/**
	 * @param mensagem the mensagem to set
	 */
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	/**
	 * @return the busca
	 */
	public Busca getBusca() {
		return busca;
	}

	/**
	 * @param busca the busca to set
	 */
	public void setBusca(Busca busca) {
		this.busca = busca;
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
		DialogoBusca other = (DialogoBusca) obj;
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

	
	
}
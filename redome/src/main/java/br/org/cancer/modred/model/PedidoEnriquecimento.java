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

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.BuscaView;
import br.org.cancer.modred.controller.view.DoadorView;
import br.org.cancer.modred.controller.view.PedidoExameView;
import br.org.cancer.modred.model.domain.TipoEnriquecimento;
import br.org.cancer.modred.model.security.Usuario;

/**
 * Classe de persistencia para Pedido de enriquecimento.
 * 
 * @author Fillipe Queiroz
 */
@Entity
@SequenceGenerator(name = "SQ_PEEN_ID", sequenceName = "SQ_PEEN_ID", allocationSize = 1)
@Table(name = "PEDIDO_ENRIQUECIMENTO")
public class PedidoEnriquecimento implements Serializable {

	private static final long serialVersionUID = -3581545101029383022L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_PEEN_ID")
	@Column(name = "PEEN_ID")
	@JsonView({ BuscaView.Enriquecimento.class, DoadorView.AtualizacaoFase2.class, DoadorView.ContatoPassivo.class })
	private Long id;

	@Column(name = "PEEN_DT_ENRIQUECIMENTO")
	private LocalDateTime dataEnriquecimento;

	@Column(name = "PEEN_DT_CRIACAO")
	@JsonView(PedidoExameView.ListarTarefas.class)
	private LocalDateTime dataCriacao;

	@Column(name = "PEEN_IN_ABERTO")
	@NotNull
	private boolean aberto;

	@ManyToOne
	@JoinColumn(name = "USUA_ID")
	private Usuario usuario;

	@Column(name = "PEEN_IN_TIPO_ENRIQUECIMENTO")
	private Long tipo;

	@ManyToOne
	@JoinColumn(name = "SOLI_ID")
	@NotNull
	// @Fetch(FetchMode.JOIN)
	@JsonView({ BuscaView.Enriquecimento.class, DoadorView.AtualizacaoFase2.class, DoadorView.ContatoPassivo.class, PedidoExameView.ListarTarefas.class })
	private Solicitacao solicitacao;
	
	@Column(name = "PEEN_IN_CANCELADO")
	@NotNull
	private boolean cancelado = false;

	public PedidoEnriquecimento() {}

	/**
	 * Chave primaria.
	 * 
	 * @return
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Chave primaria.
	 * 
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Data que foi feito o enriquecimento dos dados.
	 * 
	 * @return
	 */
	public LocalDateTime getDataEnriquecimento() {
		return dataEnriquecimento;
	}

	/**
	 * Data que foi feito o enriquecimento dos dados.
	 * 
	 * @param dataEnriquecimento
	 */
	public void setDataEnriquecimento(LocalDateTime dataEnriquecimento) {
		this.dataEnriquecimento = dataEnriquecimento;
	}

	/**
	 * Data de criacao do registro.
	 * 
	 * @return
	 */
	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}

	/**
	 * Data de criacao do registro.
	 * 
	 * @param dataCriacao
	 */
	public void setDataCriacao(LocalDateTime dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	/**
	 * Flag que indica se o pedido de enriquecimento está em aberto.
	 * 
	 * @return
	 */
	public boolean isAberto() {
		return aberto;
	}

	/**
	 * Flag que indica se o pedido de enriquecimento está em aberto.
	 * 
	 * @param aberto
	 */
	public void setAberto(boolean aberto) {
		this.aberto = aberto;
	}

	/**
	 * Usuario que fez o pedido de enriquecimento.
	 * 
	 * @return
	 */
	public Usuario getUsuario() {
		return usuario;
	}

	/**
	 * Usuario que fez o pedido de enriquecimento.
	 * 
	 * @param usuario
	 */
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	/**
	 * @return the tipo
	 */
	public TipoEnriquecimento getTipo() {
		return TipoEnriquecimento.valueOf(tipo);
	}

	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(TipoEnriquecimento tipo) {
		if (tipo != null) {
			this.tipo = tipo.getId();
		}
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
		result = prime * result + ( aberto ? 1231 : 1237 );
		result = prime * result + ( ( dataCriacao == null ) ? 0 : dataCriacao.hashCode() );
		result = prime * result + ( ( dataEnriquecimento == null ) ? 0 : dataEnriquecimento.hashCode() );
		result = prime * result + ( ( id == null ) ? 0 : id.hashCode() );
		result = prime * result + ( ( usuario == null ) ? 0 : usuario.hashCode() );
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
		if (!( obj instanceof PedidoEnriquecimento )) {
			return false;
		}
		PedidoEnriquecimento other = (PedidoEnriquecimento) obj;
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
		if (dataEnriquecimento == null) {
			if (other.dataEnriquecimento != null) {
				return false;
			}
		}
		else
			if (!dataEnriquecimento.equals(other.dataEnriquecimento)) {
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
		if (usuario == null) {
			if (other.usuario != null) {
				return false;
			}
		}
		else
			if (!usuario.equals(other.usuario)) {
				return false;
			}
		return true;
	}

	/**
	 * Solicitacao do pedido de enriquecimento.
	 * 
	 * @return
	 */
	public Solicitacao getSolicitacao() {
		return solicitacao;
	}

	/**
	 * Solicitacao do pedido de enriquecimento.
	 * 
	 * @param solicitacao
	 */
	public void setSolicitacao(Solicitacao solicitacao) {
		this.solicitacao = solicitacao;
	}

	/**
	 * @return the cancelado
	 */
	public boolean isCancelado() {
		return cancelado;
	}

	/**
	 * @param cancelado the cancelado to set
	 */
	public void setCancelado(boolean cancelado) {
		this.cancelado = cancelado;
	}

}
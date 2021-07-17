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
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.AvaliacaoPedidoColetaView;
import br.org.cancer.modred.controller.view.BuscaView;
import br.org.cancer.modred.model.annotations.AssertTrueCustom;
import br.org.cancer.modred.model.security.Usuario;
import br.org.cancer.modred.service.impl.custom.EntityObservable;
import br.org.cancer.modred.service.impl.observers.AvaliacaoPedidoColetaAprovadoObserver;
import br.org.cancer.modred.service.impl.observers.AvaliacaoPedidoColetaReprovadoObserver;


/**
 * Classe de persistência para avaliação do pedido de coleta.
 * Nesta entidade será gravada a avaliação que o médico do REDOME fará
 * em caso de pedido de coleta do doador.
 * @author Filipe Paes
 * 
 */
@Entity
@Table(name="AVALIACAO_PEDIDO_COLETA")
@SequenceGenerator(name = "SQ_AVPC_ID", sequenceName = "SQ_AVPC_ID", allocationSize = 1)
public class AvaliacaoPedidoColeta extends EntityObservable implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_AVPC_ID")
	@Column(name="AVPC_ID")
	@JsonView(value = {BuscaView.AvaliacaoPedidoColeta.class, AvaliacaoPedidoColetaView.Detalhe.class, BuscaView.AvaliacaoPedidoColeta.class})
	private Long id;

	@Transient
	@JsonView(value = {AvaliacaoPedidoColetaView.Detalhe.class})
	private LocalDateTime dataAvaliacao;

	@Column(name="AVPC_DT_CRIACAO")
	@JsonView(value = {AvaliacaoPedidoColetaView.Detalhe.class})
	private LocalDateTime dataCriacao;

	@Column(name="AVPC_IN_PEDIDO_PROSSEGUE")
	@JsonView(value = {AvaliacaoPedidoColetaView.Detalhe.class})
	private Boolean pedidoProssegue;

	@Column(name="AVPC_TX_JUSTIFICATIVA")
	@JsonView(value = {AvaliacaoPedidoColetaView.Detalhe.class})
	private String justificativa;

	@Transient
	@JsonView(value = {BuscaView.AvaliacaoPedidoColeta.class, AvaliacaoPedidoColetaView.Detalhe.class, BuscaView.AvaliacaoPedidoColeta.class})
	private Solicitacao solicitacao;

	@ManyToOne
	@JoinColumn(name="USUA_ID")
	private Usuario usuario;

	/**
	 * Construtor padrão.
	 */
	public AvaliacaoPedidoColeta() {
		addObserver(AvaliacaoPedidoColetaAprovadoObserver.class);
		addObserver(AvaliacaoPedidoColetaReprovadoObserver.class);
		
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
	 * @return the dataAvaliacao
	 */
	public LocalDateTime getDataAvaliacao() {
		return dataAvaliacao;
	}

	/**
	 * @param dataAvaliacao the dataAvaliacao to set
	 */
	public void setDataAvaliacao(LocalDateTime dataAvaliacao) {
		this.dataAvaliacao = dataAvaliacao;
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
	 * @return the pedidoProssegue
	 */
	public Boolean getPedidoProssegue() {
		return pedidoProssegue;
	}

	/**
	 * @param pedidoProssegue the pedidoProssegue to set
	 */
	public void setPedidoProssegue(Boolean pedidoProssegue) {
		this.pedidoProssegue = pedidoProssegue;
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

	/**
	 * @return the solicitacao
	 */
	public Solicitacao getSolicitacao() {
		return solicitacao;
	}

	/**
	 * @param solicitacao the solicitacao to set
	 */
	public void setSolicitacao(Solicitacao solicitacao) {
		this.solicitacao = solicitacao;
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
	 * Método Bean Validation para verificar obrigatoriedade de justificativa de avaliação de pedido de coleta.
	 * 
	 * @return boolean true se o campo de justificativa for preenchido em caso do pedido de coleta for reprovado.
	 */
	@AssertTrueCustom(message = "{javax.validation.constraints.NotNull.message}", field = "justificativa")
	public boolean isJustificativaObrigatoria() {
		return this.dataAvaliacao == null || (this.pedidoProssegue == true || (this.pedidoProssegue == false && StringUtils.isNotEmpty(this.justificativa)));
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dataCriacao == null) ? 0 : dataCriacao.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((solicitacao == null) ? 0 : solicitacao.hashCode());
		result = prime * result + ((usuario == null) ? 0 : usuario.hashCode());
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
		AvaliacaoPedidoColeta other = (AvaliacaoPedidoColeta) obj;
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
		if (solicitacao == null) {
			if (other.solicitacao != null) {
				return false;
			}
		} 
		else if (!solicitacao.equals(other.solicitacao)) {
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
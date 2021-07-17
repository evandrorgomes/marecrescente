package br.org.cancer.modred.model;

import java.io.Serializable;

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

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.AvaliacaoPedidoColetaView;
import br.org.cancer.modred.controller.view.BuscaView;
import br.org.cancer.modred.controller.view.WorkupView;

/**
 * Classe de arquivos de resposta de resultado de workup. Dentro desta estarão as referencias para o storage dos arquivos de
 * resultado de workup inputados pelo centro de coleta.
 * 
 * @author Filipe Paes.
 */
@Entity
@SequenceGenerator(name = "SQ_ARRW_ID", sequenceName = "SQ_ARRW_ID", allocationSize = 1)
@Table(name = "ARQUIVO_RESULTADO_WORKUP")
public class ArquivoResultadoWorkup implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_ARRW_ID")
	@Column(name = "ARRW_ID")
	@JsonView(value = { WorkupView.ResultadoParaAvaliacao.class, AvaliacaoPedidoColetaView.Detalhe.class,
			BuscaView.AvaliacaoWorkupDoador.class })
	private Long id;

	@Transient
	@JsonView(value = { WorkupView.ResultadoParaAvaliacao.class, AvaliacaoPedidoColetaView.Detalhe.class,
			BuscaView.AvaliacaoWorkupDoador.class })
	private Boolean excluido = false;

	@Column(name = "ARRW_TX_CAMINHO")
	@JsonView(value = { WorkupView.ResultadoParaAvaliacao.class, AvaliacaoPedidoColetaView.Detalhe.class,
			BuscaView.AvaliacaoWorkupDoador.class })
	private String caminho;

	@Column(name = "ARRW_TX_DESCRICAO")
	@JsonView(value = { WorkupView.ResultadoParaAvaliacao.class, AvaliacaoPedidoColetaView.Detalhe.class,
			BuscaView.AvaliacaoWorkupDoador.class })
	private String descricao;

	@ManyToOne
	@JoinColumn(name = "REWO_ID")
	private ResultadoWorkup resultadoWorkup;

	public ArquivoResultadoWorkup() {}

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
	 * @return the excluido
	 */
	public Boolean getExcluido() {
		return excluido;
	}

	/**
	 * @param excluido the excluido to set
	 */
	public void setExcluido(Boolean excluido) {
		this.excluido = excluido;
	}

	/**
	 * @return the caminho
	 */
	public String getCaminho() {
		return caminho;
	}

	/**
	 * @param caminho the caminho to set
	 */
	public void setCaminho(String caminho) {
		this.caminho = caminho;
	}

	/**
	 * @return the descricao
	 */
	public String getDescricao() {
		return descricao;
	}

	/**
	 * @param descricao the descricao to set
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	/**
	 * @return the resultadoWorkup
	 */
	public ResultadoWorkup getResultadoWorkup() {
		return resultadoWorkup;
	}

	/**
	 * @param resultadoWorkup the resultadoWorkup to set
	 */
	public void setResultadoWorkup(ResultadoWorkup resultadoWorkup) {
		this.resultadoWorkup = resultadoWorkup;
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
		result = prime * result + ( ( caminho == null ) ? 0 : caminho.hashCode() );
		result = prime * result + ( ( id == null ) ? 0 : id.hashCode() );
		result = prime * result + ( ( resultadoWorkup == null ) ? 0 : resultadoWorkup.hashCode() );
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
		if (getClass() != obj.getClass()) {
			return false;
		}
		ArquivoResultadoWorkup other = (ArquivoResultadoWorkup) obj;
		if (caminho == null) {
			if (other.caminho != null) {
				return false;
			}
		}
		else
			if (!caminho.equals(other.caminho)) {
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
		if (resultadoWorkup == null) {
			if (other.resultadoWorkup != null) {
				return false;
			}
		}
		else
			if (!resultadoWorkup.equals(other.resultadoWorkup)) {
				return false;
			}
		return true;
	}

}
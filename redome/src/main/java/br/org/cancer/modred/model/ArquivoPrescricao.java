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

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.AvaliacaoPrescricaoView;
import br.org.cancer.modred.util.ArquivoUtil;

/**
 * Classe de arquivos da prescricao. Dentro desta estarão as referencias para o storage.
 * 
 * @author bruno.sousa.
 */
@Entity
@SequenceGenerator(name = "SQ_ARPR_ID", sequenceName = "SQ_ARPR_ID", allocationSize = 1)
@Table(name = "ARQUIVO_PRESCRICAO")
public class ArquivoPrescricao implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Variável estática representando o tamanho máximo que o nome do arquivo pode conter.
	 */
	public static final int TAMANHO_MAXIMO_NOME_ARQUIVO = 257;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_ARPR_ID")
	@Column(name = "ARPR_ID")
	@JsonView(value = { AvaliacaoPrescricaoView.Detalhe.class })
	private Long id;

	@Column(name = "ARPR_TX_CAMINHO")
	@JsonView(value = { AvaliacaoPrescricaoView.Detalhe.class })
	private String caminho;

	@ManyToOne
	@JoinColumn(name = "PRES_ID")
	private Prescricao prescricao;

	@Column(name = "ARPR_IN_JUSTIFICATIVA")
	@JsonView(value = { AvaliacaoPrescricaoView.Detalhe.class })
	private Boolean justificativa;
	
	@Column(name = "ARPR_IN_AUTORIZACAO_PACIENTE")
	private Boolean autorizacaoPaciente;

	public ArquivoPrescricao() {}

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
	 * @return the prescricao
	 */
	public Prescricao getPrescricao() {
		return prescricao;
	}

	/**
	 * @param prescricao the prescricao to set
	 */
	public void setPrescricao(Prescricao prescricao) {
		this.prescricao = prescricao;
	}

	/**
	 * @return the justificativa
	 */
	public Boolean getJustificativa() {
		return justificativa;
	}

	/**
	 * @param justificativa the justificativa to set
	 */
	public void setJustificativa(Boolean justificativa) {
		this.justificativa = justificativa;
	}
	

	/**
	 * @return the autorizacaoPaciente
	 */
	public Boolean getAutorizacaoPaciente() {
		return autorizacaoPaciente;
	}

	/**
	 * @param autorizacaoPaciente the autorizacaoPaciente to set
	 */
	public void setAutorizacaoPaciente(Boolean autorizacaoPaciente) {
		this.autorizacaoPaciente = autorizacaoPaciente;
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
		result = prime * result + ( ( justificativa == null ) ? 0 : justificativa.hashCode() );
		result = prime * result + ( ( prescricao == null ) ? 0 : prescricao.hashCode() );
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
		if (!( obj instanceof ArquivoPrescricao )) {
			return false;
		}
		ArquivoPrescricao other = (ArquivoPrescricao) obj;
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
		if (justificativa == null) {
			if (other.justificativa != null) {
				return false;
			}
		}
		else
			if (!justificativa.equals(other.justificativa)) {
				return false;
			}
		if (prescricao == null) {
			if (other.prescricao != null) {
				return false;
			}
		}
		else
			if (!prescricao.equals(other.prescricao)) {
				return false;
			}
		return true;
	}

	/**
	 * Método para obter somente o nome do arquivo de exame.
	 * 
	 * @return nome nome
	 */
	@JsonView(value = { AvaliacaoPrescricaoView.Detalhe.class })
	public String nomeSemTimestamp() {
		return ArquivoUtil.obterNomeArquivoSemTimestampPorCaminhoArquivo(this.getCaminho());
	}

	/**
	 * Retorna o nome do arquivo com timestamp.
	 * 
	 * @return
	 */
	public String nomeComTimestamp() {
		return ArquivoUtil.obterNomeArquivoComTimestampPorCaminhoArquivo(this.getCaminho());
	}

}
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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.AvaliacaoPrescricaoView;
import br.org.cancer.modred.controller.view.PedidoWorkupView;
import br.org.cancer.modred.service.impl.custom.EntityObservable;
import br.org.cancer.modred.service.impl.observers.AvaliacaoPrescricaoReprovadaObserver;

/**
 * Classe que representa a entidade de avaliacao de prescrição.
 * 
 * @author Fillipe.queiroz
 *
 */
@Entity
@Table(name = "AVALIACAO_PRESCRICAO")
@SequenceGenerator(name = "SQ_AVPR_ID", sequenceName = "SQ_AVPR_ID", allocationSize = 1)
public class AvaliacaoPrescricao extends EntityObservable implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "AVPR_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_AVPR_ID")
	@JsonView(value = { AvaliacaoPrescricaoView.ListarAvaliacao.class })
	private long id;

	@Column(name = "AVPR_DT_ATUALIZACAO")
	private LocalDateTime dataAtualizacao;

	@Column(name = "AVPR_DT_CRICAO")
	@JsonView(value = { AvaliacaoPrescricaoView.ListarAvaliacao.class })
	private LocalDateTime dataCriacao;

	@Column(name = "AVPR_TX_JUSTIFICATIVA_CANCEL")
	private String justificativaCancelamento;

	@Column(name = "AVPR_TX_JUSTIFICATIVA_DESCARTE")
	private String justificativaDescarteFonteCelula;

	@ManyToOne
	@JoinColumn(name = "FOCE_ID")
	@JsonView(value = { PedidoWorkupView.DetalheWorkup.class })
	private FonteCelula fonteCelula;

	@Column(name = "AVPR_IN_RESULTADO")
	private Boolean aprovado;

	@OneToOne
	@JoinColumn(name = "PRES_ID")
	@JsonView(value = { AvaliacaoPrescricaoView.ListarAvaliacao.class, AvaliacaoPrescricaoView.Detalhe.class })
	private Prescricao prescricao;

	public AvaliacaoPrescricao() {
		super();
		addObserver(AvaliacaoPrescricaoReprovadaObserver.class);
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the dataAtualizacao
	 */
	public LocalDateTime getDataAtualizacao() {
		return dataAtualizacao;
	}

	/**
	 * @param dataAtualizacao the dataAtualizacao to set
	 */
	public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
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
	 * @return the justificativaCancelamento
	 */
	public String getJustificativaCancelamento() {
		return justificativaCancelamento;
	}

	/**
	 * @param justificativaCancelamento the justificativaCancelamento to set
	 */
	public void setJustificativaCancelamento(String justificativaCancelamento) {
		this.justificativaCancelamento = justificativaCancelamento;
	}

	/**
	 * @return the justificativaDescarteFonteCelula
	 */
	public String getJustificativaDescarteFonteCelula() {
		return justificativaDescarteFonteCelula;
	}

	/**
	 * @param justificativaDescarteFonteCelula the justificativaDescarteFonteCelula to set
	 */
	public void setJustificativaDescarteFonteCelula(String justificativaDescarteFonteCelula) {
		this.justificativaDescarteFonteCelula = justificativaDescarteFonteCelula;
	}

	/**
	 * @return the fonteCelula
	 */
	public FonteCelula getFonteCelula() {
		return fonteCelula;
	}

	/**
	 * @param fonteCelula the fonteCelula to set
	 */
	public void setFonteCelula(FonteCelula fonteCelula) {
		this.fonteCelula = fonteCelula;
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
	 * @return the aprovado
	 */
	public Boolean getAprovado() {
		return aprovado;
	}

	/**
	 * @param aprovado the aprovado to set
	 */
	public void setAprovado(Boolean aprovado) {
		this.aprovado = aprovado;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((prescricao == null) ? 0 : prescricao.hashCode());
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
		AvaliacaoPrescricao other = (AvaliacaoPrescricao) obj;
		if (prescricao == null) {
			if (other.prescricao != null) {
				return false;
			}
		}
		else if (!prescricao.equals(other.prescricao)) {
			return false;
		}
		return true;
	}

}
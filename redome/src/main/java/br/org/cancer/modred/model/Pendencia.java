package br.org.cancer.modred.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.AvaliacaoView;
import br.org.cancer.modred.controller.view.EvolucaoView;
import br.org.cancer.modred.util.DateUtils;

/**
 * Classe que representa uma pendencia.
 * 
 * @author Fillipe Queiroz
 * 
 */
@Entity
@SequenceGenerator(name = "SQ_PEND_ID", sequenceName = "SQ_PEND_ID", allocationSize = 1)
@Table(name = "PENDENCIA")
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
public class Pendencia implements Serializable {

	private static final long serialVersionUID = 6285568189074752933L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_PEND_ID")
	@Column(name = "PEND_ID")
	@JsonView({ EvolucaoView.NovaEvolucao.class, AvaliacaoView.ListarPendencias.class })
	private Long id;

	@Column(name = "PEND_DT_CRIACAO")
	@NotNull
	private LocalDateTime dataCriacao;

	@Column(name = "PEND_TX_DESCRICAO")
	@Length(max = 200)
	@NotNull
	@JsonView({ EvolucaoView.NovaEvolucao.class, AvaliacaoView.ListarPendencias.class })
	private String descricao;

	@ManyToOne
	@JoinColumn(name = "AVAL_ID")
	@NotNull
	@JsonProperty(access = Access.WRITE_ONLY)
	private Avaliacao avaliacao;

	@ManyToOne
	@JoinColumn(name = "STPE_ID")
	@NotNull
	@JsonView({ AvaliacaoView.ListarPendencias.class })
	private StatusPendencia statusPendencia;

	@ManyToOne
	@JoinColumn(name = "TIPE_ID")
	@NotNull
	@JsonView({ AvaliacaoView.ListarPendencias.class })
	private TipoPendencia tipoPendencia;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(	name = "ASSOCIA_RESPOSTA_PENDENCIA",
				joinColumns =
	{ @JoinColumn(name = "PEND_ID") },
				inverseJoinColumns =
	{ @JoinColumn(name = "REPE_ID") })
	@OrderBy("dataCriacao ASC")
	private List<RespostaPendencia> respostas;

	/**
	 * Construtor padrão.
	 */
	public Pendencia() {
		super();
	}

	/**
	 * Construtor com id, data de criação e descrição.
	 * 
	 * @param id da pendencia.
	 * @param dataCriacao data de criação da pendencia.
	 * @param descricao da pendencia.
	 */
	public Pendencia(Long id, LocalDateTime dataCriacao, String descricao) {
		this();
		this.id = id;
		this.dataCriacao = dataCriacao;
		this.descricao = descricao;
	}

	/**
	 * Construtor com id, data de criação, descrição, status e tipo da pendencia.
	 * 
	 * @param id da pendencia
	 * @param dataCriacao da pendencia
	 * @param descricao da pendencia
	 * @param statusPendencia da pendencia
	 * @param tipoPendencia da pendencia
	 */
	public Pendencia(Long id, LocalDateTime dataCriacao, String descricao, StatusPendencia statusPendencia,
			TipoPendencia tipoPendencia) {
		this(id, dataCriacao, descricao);
		this.statusPendencia = statusPendencia;
		this.tipoPendencia = tipoPendencia;
	}

	/**
	 * Retorna a avaliacao da pendencia.
	 * 
	 * @return
	 */
	public Avaliacao getAvaliacao() {
		return this.avaliacao;
	}

	/**
	 * Seta a avaliacao da pendencia.
	 * 
	 * @param avaliacao
	 */
	public void setAvaliacao(Avaliacao avaliacao) {
		this.avaliacao = avaliacao;
	}

	/**
	 * Retorna os status da pendencia.
	 * 
	 * @return
	 */
	public StatusPendencia getStatusPendencia() {
		return this.statusPendencia;
	}

	/**
	 * Seta os status da pendencia.
	 * 
	 * @param statusPendencia
	 */
	public void setStatusPendencia(StatusPendencia statusPendencia) {
		this.statusPendencia = statusPendencia;
	}

	/**
	 * Retorna o tipo da pendencia.
	 * 
	 * @return
	 */
	public TipoPendencia getTipoPendencia() {
		return this.tipoPendencia;
	}

	/**
	 * Seta o tipo da pendencia.
	 * 
	 * @param tipoPendencia
	 */
	public void setTipoPendencia(TipoPendencia tipoPendencia) {
		this.tipoPendencia = tipoPendencia;
	}

	/**
	 * Retorna a chave primaria.
	 * 
	 * @return
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Seta a chave primaria.
	 * 
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Retorna a data da cariacao da pendencia.
	 * 
	 * @return
	 */
	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}

	/**
	 * Seta a data da cariacao da pendencia.
	 * 
	 * @param dataCriacao
	 */
	public void setDataCriacao(LocalDateTime dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	/**
	 * Retorna a descricao da pendencia.
	 * 
	 * @return
	 */
	public String getDescricao() {
		return descricao;
	}

	/**
	 * Seta a descricao da pendencia.
	 * 
	 * @param descricao
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	/**
	 * Método que retorna as respostas de uma pendencia.
	 * 
	 * @return
	 */
	public List<RespostaPendencia> getRespostas() {
		return respostas;
	}

	/**
	 * Seta as respostas de uma pendencia.
	 * 
	 * @param respostas
	 */
	public void setRespostas(List<RespostaPendencia> respostas) {
		this.respostas = respostas;
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
		if (dataCriacao != null) {
			result = prime * result + ( ( dataCriacao == null ) ? 0 : dataCriacao.hashCode() );
		}
		if (descricao != null) {
			result = prime * result + ( ( descricao == null ) ? 0 : descricao.hashCode() );
		}
		if (id != null) {
			result = prime * result + (int) ( id ^ ( id >>> 32 ) );
		}
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
		Pendencia other = (Pendencia) obj;
		if (dataCriacao == null) {
			if (other.dataCriacao != null) {
				return false;
			}
		}
		else
			if (!dataCriacao.equals(other.dataCriacao)) {
				return false;
			}
		if (descricao == null) {
			if (other.descricao != null) {
				return false;
			}
		}
		else
			if (!descricao.equals(other.descricao)) {
				return false;
			}
		if (id != other.id) {
			return false;
		}
		return true;
	}

	/**
	 * @return tempo de criação da pendencia em formato de texto.
	 * @deprecated utilizar a função DateUtils.obterAging(LocalDateTime data).
	 */
	@JsonView(AvaliacaoView.ListarPendencias.class)
	@Deprecated
	public String getTempoCriacao() {
		return DateUtils.obterAging(dataCriacao);
	}

	/**
	 * Retorna a data criação formatada em dd/mm/yy hh:mm.
	 * 
	 * @return dataFormatada.
	 */
	@JsonView({ AvaliacaoView.ListarPendencias.class })
	public String getDataFormatadaDialogo() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm");
		if (dataCriacao != null) {
			return dataCriacao.format(formatter);
		}
		return null;
	}
}
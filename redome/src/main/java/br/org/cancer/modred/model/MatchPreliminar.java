package br.org.cancer.modred.model;

import java.time.LocalDateTime;
import java.util.List;

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

import br.org.cancer.modred.model.interfaces.IMatch;
import br.org.cancer.modred.model.interfaces.IQualificacaoMatch;

/**
 * @author Pizão
 * 
 * Entidade que representa um match preliminar ocorrido após uma consulta preliminar 
 * entre um paciente e os doadores nacionais disponíveis na base do Redome.
 * 
 */
@Entity
@SequenceGenerator(name = "SQ_MAPR_ID", sequenceName = "SQ_MAPR_ID", allocationSize = 1)
@Table(name = "MATCH_PRELIMINAR")
public class MatchPreliminar implements IMatch {
	private static final long serialVersionUID = -5602681850682371120L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_MAPR_ID")
	@Column(name = "MAPR_ID")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "DOAD_ID")
	private Doador doador;
	
	@ManyToOne
	@JoinColumn(name = "BUPR_ID")
	private BuscaPreliminar buscaPreliminar;
	
	@OneToMany(mappedBy = "match", fetch = FetchType.LAZY)
	private List<QualificacaoMatchPreliminar> qualificacoes;
	
	@Column(name = "MAPR_TX_GRADE")
	private String grade;
	
	@Column(name = "MAPR_TX_MISMATCH")
    private String mismatch;
	
	@Column(name = "MAPR_DT_MATCH")
	private LocalDateTime dataCriacao;
	
	@ManyToOne
	@JoinColumn(name = "TIPM_ID")
	@NotNull
	private TipoPermissividade tipoPermissividade;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Doador getDoador() {
		return doador;
	}

	public void setDoador(Doador doador) {
		this.doador = doador;
	}

	public BuscaPreliminar getBuscaPreliminar() {
		return buscaPreliminar;
	}

	public void setBuscaPreliminar(BuscaPreliminar buscaPreliminar) {
		this.buscaPreliminar = buscaPreliminar;
	}

	/**
	 * @return the qualificacoes
	 */
	@Override
	public List<? extends IQualificacaoMatch> getQualificacoes() {
		return qualificacoes;
	}

	/**
	 * @param qualificacoes the qualificacoes to set
	 */
	public void setQualificacoes(List<QualificacaoMatchPreliminar> qualificacoes) {
		this.qualificacoes = qualificacoes;
	}

	/**
	 * @return the grade
	 */
	public String getGrade() {
		return grade;
	}

	/**
	 * @param grade the grade to set
	 */
	public void setGrade(String grade) {
		this.grade = grade;
	}

	/**
	 * @return the mismatch
	 */
	public String getMismatch() {
		return mismatch;
	}

	/**
	 * @param mismatch the mismatch to set
	 */
	public void setMismatch(String mismatch) {
		this.mismatch = mismatch;
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
	 * @return the tipoPermissividade
	 */
	public TipoPermissividade getTipoPermissividade() {
		return tipoPermissividade;
	}

	/**
	 * @param tipoPermissividade the tipoPermissividade to set
	 */
	public void setTipoPermissividade(TipoPermissividade tipoPermissividade) {
		this.tipoPermissividade = tipoPermissividade;
	}
	
}

package br.org.cancer.modred.model;

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

import br.org.cancer.modred.controller.dto.genotipo.ComposicaoAlelo;
import br.org.cancer.modred.model.annotations.EnumValues;
import br.org.cancer.modred.model.domain.PosicaoAlelo;
import br.org.cancer.modred.model.domain.QualificacoesMatch;
import br.org.cancer.modred.model.interfaces.IMatch;
import br.org.cancer.modred.model.interfaces.IQualificacaoMatch;

/**
 * Qualificação de match preliminar gerado.
 * 
 * @author Pizão
 * 
 */
@Entity
@SequenceGenerator(name = "SQ_QUMP_ID", sequenceName = "SQ_QUMP_ID", allocationSize = 1)
@Table(name = "QUALIFICACAO_MATCH_PRELIMINAR")
public class QualificacaoMatchPreliminar implements IQualificacaoMatch {
	private static final long serialVersionUID = 1625490871910776505L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "")
	@Column(name = "QUMP_ID")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "LOCU_ID")
	private Locus locus;

	@EnumValues(PosicaoAlelo.class)
	@Column(name = "QUMP_NR_POSICAO")
	private Integer posicao;

	@EnumValues(QualificacoesMatch.class)
	@Column(name = "QUMP_TX_QUALIFICACAO")
	private String qualificacao;
	
	@Column(name = "QUMP_TX_GENOTIPO")
	private String genotipo;
	
	@EnumValues(ComposicaoAlelo.class)
	@Column(name = "QUMP_NR_TIPO")
	private Integer tipo;
	
	@ManyToOne
	@JoinColumn(name = "MAPR_ID")
	private MatchPreliminar match;

	@Transient
	private String probabilidade;

	
	public QualificacaoMatchPreliminar() {}

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
	 * @return the locus
	 */
	public Locus getLocus() {
		return locus;
	}

	/**
	 * @param locus the locus to set
	 */
	public void setLocus(Locus locus) {
		this.locus = locus;
	}

	/**
	 * @return the posicao
	 */
	public Integer getPosicao() {
		return posicao;
	}

	/**
	 * @param posicao the posicao to set
	 */
	public void setPosicao(Integer posicao) {
		this.posicao = posicao;
	}

	/**
	 * @return the qualificacao
	 */
	public String getQualificacao() {
		return qualificacao;
	}

	/**
	 * @param qualificacao the qualificacao to set
	 */
	public void setQualificacao(String qualificacao) {
		this.qualificacao = qualificacao;
	}


	

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ( ( genotipo == null ) ? 0 : genotipo.hashCode() );
		result = prime * result + ( ( id == null ) ? 0 : id.hashCode() );
		result = prime * result + ( ( locus == null ) ? 0 : locus.hashCode() );
		result = prime * result + ( ( match == null ) ? 0 : match.hashCode() );
		result = prime * result + ( ( posicao == null ) ? 0 : posicao.hashCode() );
		result = prime * result + ( ( qualificacao == null ) ? 0 : qualificacao.hashCode() );
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
		QualificacaoMatchPreliminar other = (QualificacaoMatchPreliminar) obj;
		if (genotipo == null) {
			if (other.genotipo != null) {
				return false;
			}
		}
		else
			if (!genotipo.equals(other.genotipo)) {
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
		if (locus == null) {
			if (other.locus != null) {
				return false;
			}
		}
		else
			if (!locus.equals(other.locus)) {
				return false;
			}
		if (match == null) {
			if (other.match != null) {
				return false;
			}
		}
		else
			if (!match.equals(other.match)) {
				return false;
			}
		if (posicao == null) {
			if (other.posicao != null) {
				return false;
			}
		}
		else
			if (!posicao.equals(other.posicao)) {
				return false;
			}
		if (qualificacao == null) {
			if (other.qualificacao != null) {
				return false;
			}
		}
		else
			if (!qualificacao.equals(other.qualificacao)) {
				return false;
			}
		return true;
	}

	/**
	 * @return the genotipo
	 */
	public String getGenotipo() {
		return genotipo;
	}

	
	/**
	 * @param genotipo the genotipo to set
	 */
	public void setGenotipo(String genotipo) {
		this.genotipo = genotipo;
	}

	
	/**
	 * @return the match
	 */
	public IMatch getMatch() {
		return match;
	}

	
	/**
	 * @param match the match to set
	 */
	public void setMatch(MatchPreliminar match) {
		this.match = match;
	}

	
	/**
	 * @return the tipo
	 */
	public Integer getTipo() {
		return tipo;
	}

	
	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

	/**
	 * @return the probabilidade
	 */
	public String getProbabilidade() {
		return probabilidade;
	}

	/**
	 * @param probabilidade the probabilidade to set
	 */
	public void setProbabilidade(String probabilidade) {
		this.probabilidade = probabilidade;
	}

	

}
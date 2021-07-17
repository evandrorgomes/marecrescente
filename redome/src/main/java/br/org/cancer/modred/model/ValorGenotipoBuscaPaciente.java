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


/**
 * Classe de valores de genótipo reduzidos para busca.
 * @author Filipe Paes
 * 
 */
@Entity
@Table(name="VALOR_GENOTIPO_BUSCA_PACIENTE")
@SequenceGenerator(name = "SQ_VAGB_ID", sequenceName = "SQ_VAGB_ID", allocationSize = 1)
public class ValorGenotipoBuscaPaciente implements IValorGenotipoBusca {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_VAGB_ID")
	@Column(name="VGBP_ID")
	private Long id;

	@Column(name="VGBP_NR_POSICAO")
	private Integer posicao;

	@Column(name="VGBP_NR_TIPO")
	private Integer tipo;

	@Column(name="VGBP_TX_VALOR")
	private String valor;

	@ManyToOne
	@JoinColumn(name="GEPA_ID")
	private GenotipoPaciente genotipo;

	@ManyToOne
	@JoinColumn(name="LOCU_ID")
	private Locus locus;
	

	public ValorGenotipoBuscaPaciente() {
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
	 * @return the valor
	 */
	public String getValor() {
		return valor;
	}

	/**
	 * @param valor the valor to set
	 */
	public void setValor(String valor) {
		this.valor = valor;
	}

	/**
	 * @return the genotipo
	 */
	public IGenotipo getGenotipo() {
		return genotipo;
	}

	/**
	 * @param genotipo the genotipo to set
	 */
	public void setGenotipo(IGenotipo genotipo) {
		this.genotipo = (GenotipoPaciente) genotipo;
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
		ValorGenotipoBuscaPaciente other = (ValorGenotipoBuscaPaciente) obj;
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
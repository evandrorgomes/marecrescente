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
 * Classe de valores de genotipos para resultados numéricos.
 * 
 * @author Filipe Paes
 */
/**
 * @author Usuario
 *
 */
@Entity
@Table(name = "VALOR_GENOTIPO_EXPAND_PACIENTE")
@SequenceGenerator(name = "SQ_VGEE_ID", sequenceName = "SQ_VGEE_ID", allocationSize = 1)
public class ValorGenotipoExpandidoPaciente implements IValorGenotipoExpandido {
	private static final long serialVersionUID = -6064489323162770903L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_VGEE_ID")
	@Column(name = "VGEP_ID")
	private Long id;

	@Column(name = "VGEP_NR_POSICAO")
	private Integer posicao;

	@Column(name = "VGEP_TX_VALOR")
	private Long valor;

	@ManyToOne
	@JoinColumn(name = "GEPA_ID")
	private GenotipoPaciente genotipo;

	@ManyToOne
	@JoinColumn(name = "LOCU_ID")
	private Locus locus;

	@ManyToOne
	@JoinColumn(name = "PACI_NR_RMR")
	private Paciente paciente;


	public ValorGenotipoExpandidoPaciente() {}

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
	 * @return the valor
	 */
	public Long getValor() {
		return valor;
	}

	/**
	 * @param valor the valor to set
	 */
	public void setValor(Long valor) {
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ( ( id == null ) ? 0 : id.hashCode() );
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
		ValorGenotipoExpandidoPaciente other = (ValorGenotipoExpandidoPaciente) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		}
		else
			if (!id.equals(other.id)) {
				return false;
			}
		return true;
	}

	/**
	 * @return the paciente
	 */
	public Paciente getPaciente() {
		return paciente;
	}

	/**
	 * @param paciente the paciente to set
	 */
	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	@Override
	public IProprietarioHla getProprietario() {
		return getPaciente();
	}

	@Override
	public void setProprietario(IProprietarioHla proprietario) {
		this.setPaciente((Paciente) proprietario);
	}

}
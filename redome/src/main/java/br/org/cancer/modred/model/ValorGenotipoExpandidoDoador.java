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
import javax.validation.constraints.NotNull;

import br.org.cancer.modred.model.annotations.EnumValues;
import br.org.cancer.modred.model.domain.TiposDoador;
import br.org.cancer.modred.util.EnumUtil;

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
@Table(name = "VALOR_GENOTIPO_EXPAND_DOADOR")
@SequenceGenerator(name = "SQ_VGED_ID", sequenceName = "SQ_VGED_ID", allocationSize = 1)
public class ValorGenotipoExpandidoDoador implements IValorGenotipoExpandido {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_VGED_ID")
	@Column(name = "VGED_ID")
	private Long id;

	@Column(name = "VGED_NR_POSICAO")
	private Integer posicao;

	@Column(name = "VGED_TX_VALOR")
	private Long valor;

	@ManyToOne
	@JoinColumn(name = "GEDO_ID")
	private GenotipoDoador genotipo;

	@ManyToOne
	@JoinColumn(name = "LOCU_ID")
	private Locus locus;

	@ManyToOne
	@JoinColumn(name = "DOAD_ID")
	private Doador doador;
	
	@NotNull
	@EnumValues(TiposDoador.class)
	@Column(name = "VGED_IN_TIPO_DOADOR")
	private Long tipoDoador;

	public ValorGenotipoExpandidoDoador() {}

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
		ValorGenotipoExpandidoDoador other = (ValorGenotipoExpandidoDoador) obj;
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
	 * @return the genotipo
	 */
	public GenotipoDoador getGenotipo() {
		return genotipo;
	}

	
	/**
	 * @param genotipo the genotipo to set
	 */
	public void setGenotipo(IGenotipo genotipo) {
		this.genotipo = (GenotipoDoador) genotipo;
	}

	/**
	 * @return the doador
	 */
	public Doador getDoador() {
		return doador;
	}

	/**
	 * @param doador the doador to set
	 */
	public void setDoador(Doador doador) {
		this.doador = doador;
	}

	
	/**
	 * @return the tipoDoador
	 */
	public TiposDoador getTipoDoador() {
		if (tipoDoador != null) {
			return (TiposDoador) EnumUtil.valueOf(TiposDoador.class, tipoDoador);
		}
		return null;
	}

	/**
	 * @param tipoDoador the tipoDoador to set
	 */
	public void setTipoDoador(TiposDoador tipoDoador) {
		if (tipoDoador != null) {
			this.tipoDoador = tipoDoador.getId();
		}

	}	


	@Override
	public IProprietarioHla getProprietario() {
		return getDoador();
	}

	@Override
	public void setProprietario(IProprietarioHla proprietario) {
		this.setDoador((Doador) proprietario);		
	}
}
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
 * Classe de valores de genótipo reduzidos para busca.
 * 
 * @author Filipe Paes
 * 
 */
@Entity
@Table(name = "VALOR_GENOTIPO_BUSCA_DOADOR")
@SequenceGenerator(name = "SQ_VGBD_ID", sequenceName = "SQ_VGBD_ID", allocationSize = 1)
public class ValorGenotipoBuscaDoador implements IValorGenotipoBusca {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_VGBD_ID")
	@Column(name = "VGBD_ID")
	private Long id;

	@Column(name = "VGBD_NR_POSICAO")
	private Integer posicao;

	@Column(name = "VGBD_NR_TIPO")
	private Integer tipo;

	@Column(name = "VGBD_TX_VALOR")
	private String valor;

	@ManyToOne
	@JoinColumn(name = "GEDO_ID")
	private GenotipoDoador genotipo;

	@ManyToOne
	@JoinColumn(name = "LOCU_ID")
	private Locus locus;

	@EnumValues(TiposDoador.class)
	@NotNull
	@Column(name = "VGBD_IN_TIPO_DOADOR")
	private Long tipoDoador;

	
	public ValorGenotipoBuscaDoador() {}

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
	public GenotipoDoador getGenotipo() {
		return genotipo;
	}

	/**
	 * @param genotipo the genotipo to set
	 */
	@Override
	public void setGenotipo(IGenotipo genotipo) {
		this.genotipo = (GenotipoDoador) genotipo;
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
		ValorGenotipoBuscaDoador other = (ValorGenotipoBuscaDoador) obj;
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
	
}
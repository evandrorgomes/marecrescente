package br.org.cancer.modred.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import br.org.cancer.modred.model.domain.TiposInstrucaoColeta;


/**
 * Classe de representação das instruções de coleta de um laboratório.
 * 
 * @author bruno.sousa
 * 
 */
@Entity
@Table(name = "INSTRUCAO_COLETA")
@SequenceGenerator(name = "SQ_INCO_ID", sequenceName = "SQ_INCO_ID", allocationSize = 1)
public class InstrucaoColeta implements Serializable {
	
	private static final long serialVersionUID = 6684850929675947762L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_INCO_ID")
	@Column(name="INCO_ID")
	private Long id;

	@Column(name="INCO_TX_DESCRICAO")
	private String descricao;
	
	@ManyToOne
	@JoinColumn(name = "LABO_ID")
	private Laboratorio laboratorio;
	
	@Column(name="INCO_IN_TIPO_INSTRUCAO")
	@NotNull
	@Enumerated(EnumType.STRING)
	private TiposInstrucaoColeta tipo;

	public InstrucaoColeta() {
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
	 * @return the laboratorio
	 */
	public Laboratorio getLaboratorio() {
		return laboratorio;
	}

	
	/**
	 * @param laboratorio the laboratorio to set
	 */
	public void setLaboratorio(Laboratorio laboratorio) {
		this.laboratorio = laboratorio;
	}

	/**
	 * @return the tipo
	 */
	public TiposInstrucaoColeta getTipo() {
		return tipo;
	}

	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(TiposInstrucaoColeta tipo) {
		this.tipo = tipo;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ( ( id == null ) ? 0 : id.hashCode() );
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
		if (!( obj instanceof InstrucaoColeta )) {
			return false;
		}
		InstrucaoColeta other = (InstrucaoColeta) obj;
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


}
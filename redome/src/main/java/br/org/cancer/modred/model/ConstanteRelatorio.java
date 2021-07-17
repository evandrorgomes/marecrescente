package br.org.cancer.modred.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.envers.Audited;

/**
 * Classe que representa os valores constantes para substituição nos relatórios.
 * 
 * @author bruno.sousa
 */
@Entity
@Table(name = "CONSTANTE_RELATORIO")
@Audited
public class ConstanteRelatorio implements Serializable {

	private static final long serialVersionUID = -9220815486425123672L;

	@Id
	@NotNull
	@Column(name = "CORE_ID_CODIGO")
	private String codigo;

	@Column(name = "CORE_TX_VALOR")
	@NotNull
	private String valor;

	/**
	 * Construtor padrão.
	 */
	public ConstanteRelatorio() {
	}

	/**
	 * Construtor passsando o id.
	 * 
	 * @param id
	 */
	public ConstanteRelatorio(String codigo) {
		super();
		this.codigo = codigo;
	}

	/**
	 * @return the codigo
	 */
	public String getCodigo() {
		return "&amp;" + codigo + "&amp;";
	}

	/**
	 * @param codigo the codigo to set
	 */
	public void setCodigo(String codigo) {
		this.codigo = codigo;
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

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
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
		ConstanteRelatorio other = (ConstanteRelatorio) obj;
		if (codigo == null) {
			if (other.codigo != null) {
				return false;
			}
		} 
		else if (!codigo.equals(other.codigo)) {
			return false;
		}
		return true;
	}
	
}
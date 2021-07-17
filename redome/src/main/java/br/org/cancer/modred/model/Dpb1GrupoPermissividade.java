package br.org.cancer.modred.model;

import java.io.Serializable;

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

/**
 * Classe que representa o grupo de permissividade dos valores de dpb1.
 * 
 * @author bruno.sousa
 */
@Entity
@SequenceGenerator(name = "SQ_DPGP_ID", sequenceName = "SQ_DPGP_ID", allocationSize = 1)
@Table(name = "DPB1_GRUPO_PERMISSIVIDADE")
public class Dpb1GrupoPermissividade implements Serializable {

	private static final long serialVersionUID = -6980587314322883679L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_DPGP_ID")
	@Column(name = "DPGP_ID")
	private Long id;
	
	@Column(name="DPGP_TX_VALOR_ALELO")
	private String valorAlelo; 
	
	@ManyToOne
	@JoinColumn(name = "GRPE_ID")
	@NotNull
	private GrupoPermissividade grupoPermissividade;
		
	/**
	 * Retorna a chave primaria do tipo da pendencia.
	 * 
	 * @return id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Seta a chave primaria do tipo da pendencia.
	 * 
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the valorAlelo
	 */
	public String getValorAlelo() {
		return valorAlelo;
	}

	/**
	 * @param valorAlelo the valorAlelo to set
	 */
	public void setValorAlelo(String valorAlelo) {
		this.valorAlelo = valorAlelo;
	}

	/**
	 * @return the grupoPermissividade
	 */
	public GrupoPermissividade getGrupoPermissividade() {
		return grupoPermissividade;
	}

	/**
	 * @param grupoPermissividade the grupoPermissividade to set
	 */
	public void setGrupoPermissividade(GrupoPermissividade grupoPermissividade) {
		this.grupoPermissividade = grupoPermissividade;
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
		Dpb1GrupoPermissividade other = (Dpb1GrupoPermissividade) obj;
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
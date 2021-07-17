package br.org.cancer.modred.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Classe para o tipo de checklist de busca.
 * @author Filipe Paes
 * 
 */
@Entity
@Table(name="TIPO_BUSCA_CHECKLIST")
public class TipoBuscaChecklist implements Serializable {
	private static final long serialVersionUID = -1531033751382829929L;

	@Id
	@Column(name="TIBC_ID")
	private Long id;

	@Column(name="TIBC_NR_AGE_DIAS")
	private Integer ageEmDias;

	@Column(name="TIBC_TX_DESCRICAO")
	private String descricao;


	public TipoBuscaChecklist() {
	}

	/**
	 * Construtor com passagem de id de tipo checklist.
	 * @param id do tipo busca checklist.
	 */
	public TipoBuscaChecklist(Long id){
		this.id = id;
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
	 * @return the ageEmDias
	 */
	public Integer getAgeEmDias() {
		return ageEmDias;
	}


	/**
	 * @param ageEmDias the ageEmDias to set
	 */
	public void setAgeEmDias(Integer ageEmDias) {
		this.ageEmDias = ageEmDias;
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


	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ageEmDias == null) ? 0 : ageEmDias.hashCode());
		result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
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
		TipoBuscaChecklist other = (TipoBuscaChecklist) obj;
		if (ageEmDias == null) {
			if (other.ageEmDias != null) {
				return false;
			}
		} 
		else if (!ageEmDias.equals(other.ageEmDias)) {
			return false;
		}
		if (descricao == null) {
			if (other.descricao != null) {
				return false;
			}
		}
		else if (!descricao.equals(other.descricao)) {
			return false;
		}
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
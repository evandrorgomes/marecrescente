package br.org.cancer.modred.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Classe de persistencia para Split Valor G.
 * 
 * @author brunosousa
 */
@Entity
@Table(name = "SPLIT_VALOR_G")
public class SplitValorG implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private SplitValorGPK id;

	@Column(name = "SPVG_NR_VALIDO")
	private Boolean valido;

	@Column(name = "SPVG_TX_VERSAO")
	private String versao;
	
	public SplitValorG() {
	}
	
	/**
	 * Construtor que já recebe o Locus, Valor e Grupo.
	 * 
	 * @param locus - String do Locus
	 * @param valor - Valor
	 * @param nomeGrupo - Grupo G
	 * @param valido - indica se o valor é valido
	 * @param versao - versão do arquivo baixado
	 */
	public SplitValorG(Locus locus, String valor, String nomeGrupo, Boolean valido, String versao) {
		this.id = new SplitValorGPK();
		this.id.setLocus(locus);
		this.id.setValor(valor);
		this.id.setNomeGrupo(nomeGrupo);
		this.valido = valido;
		this.versao = versao;
	}
	
	/**
	 * @return the id
	 */
	public SplitValorGPK getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(SplitValorGPK id) {
		this.id = id;
	}

	/**
	 * @return the valido
	 */
	public Boolean getValido() {
		return valido;
	}

	/**
	 * @param valido
	 *            the valido to set
	 */
	public void setValido(Boolean valido) {
		this.valido = valido;
	}

	/**
	 * @return the versao
	 */
	public String getVersao() {
		return versao;
	}

	/**
	 * @param versao
	 *            the versao to set
	 */
	public void setVersao(String versao) {
		this.versao = versao;
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
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj){
			return true;			
		}
		if (obj == null){
			return false;
		}
		if (getClass() != obj.getClass()){
			return false;
		}
		SplitValorG other = (SplitValorG) obj;
		if (id == null) {
			if (other.id != null){
				return false;
			}
		} 
		else if (!id.equals(other.id)){
			return false;
		}
		return true;
	}

}
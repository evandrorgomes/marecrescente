package br.org.cancer.modred.controller.dto.doador;

import java.io.Serializable;

/**
 * Classe de persistencia de uf para a tabela UF.
 * 
 * @author ergomes
 * 
 */
public class UfDTO implements Serializable {

	private static final long serialVersionUID = 5290758742021185955L;

	private String sigla;
    private String nome;
	/**
	 * @return the sigla
	 */
	public String getSigla() {
		return sigla;
	}
	/**
	 * @param sigla the sigla to set
	 */
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}
	/**
	 * @param nome the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((sigla == null) ? 0 : sigla.hashCode());
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
		UfDTO other = (UfDTO) obj;
		if (nome == null) {
			if (other.nome != null) {
				return false;
			}
		} 
		else if (!nome.equals(other.nome)) {
			return false;
		}
		if (sigla == null) {
			if (other.sigla != null) {
				return false;
			}
		} 
		else if (!sigla.equals(other.sigla)) {
			return false;
		}
		return true;
	}

}
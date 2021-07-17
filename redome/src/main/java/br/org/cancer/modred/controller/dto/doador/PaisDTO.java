package br.org.cancer.modred.controller.dto.doador;

import java.io.Serializable;

/**
 * Classe de persistência do modelo Pais para a tabela país.
 * 
 * @author ergomes
 * 
 */
public class PaisDTO implements Serializable {

	private static final long serialVersionUID = -7345036443203264589L;

	/**
	 * Constate com o Id do Bradil na base de dados.
	 */
	public static final Long BRASIL = 31L;
    
    private Long id;
    private String nome;
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
	
    /**
     * @return true se o país for o Brasil
     */
    public boolean isBrasil() {
        return PaisDTO.BRASIL.equals(this.getId());
    }
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
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
		PaisDTO other = (PaisDTO) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} 
		else if (!id.equals(other.id)) {
			return false;
		}
		if (nome == null) {
			if (other.nome != null) {
				return false;
			}
		} 
		else if (!nome.equals(other.nome)) {
			return false;
		}
		return true;
	}

}
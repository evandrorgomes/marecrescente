package br.org.cancer.modred.controller.dto.doador;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Classe de persistencia de ContatoTelefonico para a tabela CONTATO_TELEFONICO.
 * 
 * @author ergomes
 */
@JsonTypeInfo(
		  use = JsonTypeInfo.Id.NAME,
		  defaultImpl = Void.class
)
@JsonSubTypes({ 
@Type(value = ContatoTelefonicoDTO.class, name = "contatoTelefonicoDTO"),
@Type(value = ContatoTelefonicoDoadorDTO.class, name = "contatoTelefonicoDoadorDTO"),
})
public abstract class ContatoTelefonicoDTO implements Serializable {

	private static final long serialVersionUID = 652977199821637091L;

	protected Long id;
	protected Boolean principal;
	protected Integer tipo;
	protected Integer codArea;
	protected Integer codInter;
	protected Long numero;
	protected String complemento;
	protected String nome;
	protected boolean excluido;
	
	/**
	 * 
	 */
	public ContatoTelefonicoDTO() {}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the principal
	 */
	public Boolean getPrincipal() {
		return principal;
	}

	/**
	 * @param principal
	 *            the principal to set
	 */
	public void setPrincipal(Boolean principal) {
		this.principal = principal;
	}

	/**
	 * @return the tipo
	 */
	public Integer getTipo() {
		return tipo;
	}

	/**
	 * @param tipo
	 *            the tipo to set
	 */
	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

	/**
	 * @return the codArea
	 */
	public Integer getCodArea() {
		return codArea;
	}

	/**
	 * @param codArea
	 *            the codArea to set
	 */
	public void setCodArea(Integer codArea) {
		this.codArea = codArea;
	}

	/**
	 * @return the codInter
	 */
	public Integer getCodInter() {
		return codInter;
	}

	/**
	 * @param codInter
	 *            the codInter to set
	 */
	public void setCodInter(Integer codInter) {
		this.codInter = codInter;
	}

	/**
	 * @return the numero
	 */
	public Long getNumero() {
		return numero;
	}

	/**
	 * @param numero
	 *            the numero to set
	 */
	public void setNumero(Long numero) {
		this.numero = numero;
	}

	/**
	 * @return the complemento
	 */
	public String getComplemento() {
		return complemento;
	}

	/**
	 * @param complemento
	 *            the complemento to set
	 */
	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @param nome
	 *            the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
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
		result = prime * result + ((codArea == null) ? 0 : codArea.hashCode());
		result = prime * result + ((codInter == null) ? 0 : codInter.hashCode());
		result = prime * result + ((numero == null) ? 0 : numero.hashCode());
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
		ContatoTelefonicoDTO other = (ContatoTelefonicoDTO) obj;
		if (codArea == null) {
			if (other.codArea != null) {
				return false;
			}
		} 
		else if (!codArea.equals(other.codArea)){
			return false;
		}
		if (codInter == null) {
			if (other.codInter != null) {
				return false;
			}
		} 
		else if (!codInter.equals(other.codInter)){
			return false;
		}
		if (numero == null) {
			if (other.numero != null) {
				return false;
			}
		} 
		else if (!numero.equals(other.numero)){
			return false;
		}
		return true;
	}

	/**
	 * Retorna o contato formatado de forma linear, unindo código internacional,
	 * ddd e número.
	 * 
	 * @return contato formatado.
	 */
	public String retornarFormatadoParaSms() {
		return String.valueOf(codInter) + String.valueOf(codArea) + String.valueOf(numero);
	}

}
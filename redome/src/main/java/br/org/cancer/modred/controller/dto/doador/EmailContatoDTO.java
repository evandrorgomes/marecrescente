package br.org.cancer.modred.controller.dto.doador;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Classe de persistencia de EmailContato para a tabela email_contato.
 * 
 * @author ergomes
 */
@JsonTypeInfo(
		  use = JsonTypeInfo.Id.NAME,
		  defaultImpl = Void.class
)
@JsonSubTypes({ 
@Type(value = EmailContatoDTO.class, name = "emailContatoDTO"),
@Type(value = EmailContatoDoadorDTO.class, name = "emailContatoDoadorDTO"),
})
public abstract class EmailContatoDTO implements Serializable {

	private static final long serialVersionUID = -5894838244594629157L;

	protected Long id;
	protected String email;
	protected boolean excluido;
	protected Boolean principal;

	public EmailContatoDTO() {}

	/**
	 * Chave primaria do registro.
	 * 
	 * @return
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Chave primaria do registro.
	 * 
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Email do contato.
	 * 
	 * @return
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Email do contato.
	 * 
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Flag que diz se registro foi excluido.
	 * 
	 * @return
	 */
	public boolean getExcluido() {
		return excluido;
	}

	/**
	 * Flag que diz se registro foi excluido.
	 * 
	 * @param excluido
	 */
	public void setExcluido(boolean excluido) {
		this.excluido = excluido;
	}
	
	/**
	 * @return the principal
	 */
	public Boolean getPrincipal() {
		return principal;
	}
	
	/**
	 * @param principal the principal to set
	 */
	public void setPrincipal(Boolean principal) {
		this.principal = principal;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ( ( email == null ) ? 0 : email.hashCode() );
		result = prime * result + ( excluido ? 1231 : 1237 );
		result = prime * result + ( ( id == null ) ? 0 : id.hashCode() );
		result = prime * result + ( ( principal == null ) ? 0 : principal.hashCode() );
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
		EmailContatoDTO other = (EmailContatoDTO) obj;
		if (email == null) {
			if (other.email != null) {
				return false;
			}
		}
		else
			if (!email.equals(other.email)) {
				return false;
			}
		if (excluido != other.excluido) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		}
		else
			if (!id.equals(other.id)) {
				return false;
			}
		if (principal == null) {
			if (other.principal != null) {
				return false;
			}
		}
		else
			if (!principal.equals(other.principal)) {
				return false;
			}
		return true;
	}

}
package br.org.cancer.modred.controller.dto.doador;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * Classe de persistencia de ContatoTelefonico para a tabela CONTATO_TELEFONICO.
 * 
 * @author ergomes
 */
@JsonTypeName("contatoTelefonicoDoadorDTO")
public class ContatoTelefonicoDoadorDTO extends ContatoTelefonicoDTO implements Serializable {


	private static final long serialVersionUID = 7205833923515489921L;

	private DoadorNacionalDTO doador;

	/**
	 * 
	 */
	public ContatoTelefonicoDoadorDTO() {}

	/**
	 * @return the doador
	 */
	public DoadorNacionalDTO getDoador() {
		return doador;
	}

	/**
	 * @param doador the doador to set
	 */
	public void setDoador(DoadorNacionalDTO doador) {
		this.doador = doador;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((doador == null) ? 0 : doador.hashCode());
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
		if (!super.equals(obj)) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		ContatoTelefonicoDoadorDTO other = (ContatoTelefonicoDoadorDTO) obj;
		if (doador == null) {
			if (other.doador != null) {
				return false;
			}
		} 
		else if (!doador.equals(other.doador)) {
			return false;
		}
		return true;
	}

}
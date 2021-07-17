package br.org.cancer.modred.controller.dto.doador;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * Classe de persistencia de EnderecoContatoDoador.
 * 
 * @author ergomes
 * 
 */
@JsonTypeName("enderecoContatoDoadorDTO")
public class EnderecoContatoDoadorDTO extends EnderecoContatoDTO implements Serializable {

	private static final long serialVersionUID = -8946603090112611523L;

	private DoadorNacionalDTO doador;

	public EnderecoContatoDoadorDTO() {}

	/**
	 * Retorna o doador.
	 * 
	 * @return
	 */
	public DoadorNacionalDTO getDoador() {
		return doador;
	}

	/**
	 * Seta o doador.
	 * 
	 * @param doador
	 */
	public void setDoador(DoadorNacionalDTO doador) {
		this.doador = doador;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.org.cancer.modred.model.EnderecoContato#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ( ( doador == null ) ? 0 : doador.hashCode() );
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.org.cancer.modred.model.EnderecoContato#equals(java.lang.Object)
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
		EnderecoContatoDoadorDTO other = (EnderecoContatoDoadorDTO) obj;
		if (doador == null) {
			if (other.doador != null) {
				return false;
			}
		}
		else
			if (!doador.equals(other.doador)) {
				return false;
			}
		return true;
	}

}
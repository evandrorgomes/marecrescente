package br.org.cancer.modred.model;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Classe de persistencia de EnderecoContatoDoador.
 * 
 * @author Fillipe Queiroz
 * 
 */
@Entity
@DiscriminatorValue(value = "DOAD")
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
@JsonIgnoreProperties(ignoreUnknown = true)
public class EnderecoContatoDoador extends EnderecoContato implements Serializable {

	private static final long serialVersionUID = 5673245262353452348L;

	@ManyToOne
	@JoinColumn(name = "DOAD_ID")
	@JsonIgnore
	private DoadorNacional doador;

	public EnderecoContatoDoador() {}

	/**
	 * Retorna o doador.
	 * 
	 * @return
	 */
	public DoadorNacional getDoador() {
		return doador;
	}

	/**
	 * Seta o doador.
	 * 
	 * @param doador
	 */
	public void setDoador(DoadorNacional doador) {
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
		EnderecoContatoDoador other = (EnderecoContatoDoador) obj;
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
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
 * Classe de persistencia de ContatoTelefonico para a tabela CONTATO_TELEFONICO.
 * 
 * @author Fillipe Queiroz
 */
@Entity
@DiscriminatorValue(value = "DOAD")
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ContatoTelefonicoDoador extends ContatoTelefonico implements Serializable {

	private static final long serialVersionUID = 7540058533735538359L;

	@ManyToOne
	@JoinColumn(name = "DOAD_ID")
	@JsonIgnore
	private DoadorNacional doador;

	public ContatoTelefonicoDoador() {
		super();
	}

	/**
	 * @return the doador
	 */
	public DoadorNacional getDoador() {
		return doador;
	}

	/**
	 * @param doador the doador to set
	 */
	public void setDoador(DoadorNacional doador) {
		this.doador = doador;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
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
		if (!( obj instanceof ContatoTelefonicoDoador )) {
			return false;
		}
		ContatoTelefonicoDoador other = (ContatoTelefonicoDoador) obj;
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
	
	public Boolean iniciaCom7Ou8ou9() {
		final String aux = String.valueOf(numero); 
		return aux.startsWith("7") || aux.startsWith("8") || aux.startsWith("9"); 
	}
	

}
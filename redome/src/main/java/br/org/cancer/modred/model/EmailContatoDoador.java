package br.org.cancer.modred.model;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Classe de persistencia de EmailContato para a tabela email_contato.
 * 
 * @author Fillipe Queiroz
 */
@Entity
@DiscriminatorValue(value = "DOAD")
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
public class EmailContatoDoador extends EmailContato implements Serializable {

	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "DOAD_ID")
	@Fetch(FetchMode.JOIN)
	@JsonIgnore
	private DoadorNacional doador;

	public EmailContatoDoador() {}

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
	 * @see br.org.cancer.modred.model.EmailContato#hashCode()
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
	 * @see br.org.cancer.modred.model.EmailContato#equals(java.lang.Object)
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
		EmailContatoDoador other = (EmailContatoDoador) obj;
		if (doador == null) {
			if (other.doador != null) {
				return false;
			}
		}
		else {
			if (!doador.equals(other.doador)) {
				return false;
			}
		}
		return true;
	}

}
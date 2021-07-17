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
 * Classe que define os atributos da entidade EmailContatoMedico.
 * É uma extensão de e-mail de contato, que possui todos os atributos comuns
 * aos contatos de doador, paciente, etc.
 * 
 * @author Pizão
 */
@Entity
@DiscriminatorValue(value = "CETR")
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
public class EmailContatoCentroTransplante extends EmailContato implements Serializable {
	
	private static final long serialVersionUID = -230546625309863220L;

	@ManyToOne
	@JoinColumn(name = "CETR_ID")
	@Fetch(FetchMode.JOIN)
	@JsonIgnore
	private CentroTransplante centroTransplante;

	/**
	 * @return the centroTransplante
	 */
	public CentroTransplante getCentroTransplante() {
		return centroTransplante;
	}

	/**
	 * @param centroTransplante the centroTransplante to set
	 */
	public void setCentroTransplante(CentroTransplante centroTransplante) {
		this.centroTransplante = centroTransplante;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((centroTransplante == null) ? 0 : centroTransplante.hashCode());
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
		EmailContatoCentroTransplante other = (EmailContatoCentroTransplante) obj;
		if (centroTransplante == null) {
			if (other.centroTransplante != null) {
				return false;
			}
		} 
		else if (!centroTransplante.equals(other.centroTransplante)) {
			return false;
		}
		return true;
	}
	

		
}
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
 * Classe de persistencia de ContatoTelefonico de Medico.
 * 
 * @author Cintia Oliveira
 */
@Entity
@DiscriminatorValue(value = "MEDI")
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ContatoTelefonicoMedico extends ContatoTelefonico implements Serializable {

	private static final long serialVersionUID = -4597798587162843082L;

	@ManyToOne
	@JoinColumn(name = "MEDI_ID")
	@JsonIgnore
	private Medico medico;

	public ContatoTelefonicoMedico() {
		super();
	}

	/**
	 * @return the medico
	 */
	public Medico getMedico() {
		return medico;
	}

	/**
	 * @param medico the medico to set
	 */
	public void setMedico(Medico medico) {
		this.medico = medico;
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
		result = prime * result + ( ( medico == null ) ? 0 : medico.hashCode() );
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
		if (!( obj instanceof ContatoTelefonicoMedico )) {
			return false;
		}
		ContatoTelefonicoMedico other = (ContatoTelefonicoMedico) obj;
		if (medico == null) {
			if (other.medico != null) {
				return false;
			}
		}
		else
			if (!medico.equals(other.medico)) {
				return false;
			}
		return true;
	}

}
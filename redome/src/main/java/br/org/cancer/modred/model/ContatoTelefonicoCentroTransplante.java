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
 * Classe de persistencia de ContatoTelefonico de CentroTransplante.
 * 
 * @author Cintia Oliveira
 */
@Entity
@DiscriminatorValue(value = "CETR")
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ContatoTelefonicoCentroTransplante extends ContatoTelefonico implements Serializable {

	private static final long serialVersionUID = -5398728852103634369L;

	@ManyToOne
	@JoinColumn(name = "CETR_ID")
	@JsonIgnore
	private CentroTransplante centroTransplante;

	public ContatoTelefonicoCentroTransplante() {
		super();
	}

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ( ( centroTransplante == null ) ? 0 : centroTransplante.hashCode() );
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
		if (!( obj instanceof ContatoTelefonicoCentroTransplante )) {
			return false;
		}
		ContatoTelefonicoCentroTransplante other = (ContatoTelefonicoCentroTransplante) obj;
		if (centroTransplante == null) {
			if (other.centroTransplante != null) {
				return false;
			}
		}
		else
			if (!centroTransplante.equals(other.centroTransplante)) {
				return false;
			}
		return true;
	}

}
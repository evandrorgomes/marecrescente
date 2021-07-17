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
 * Classe de persistencia de EnderecoContatoPaciente .
 * 
 * @author Filipe Paes
 * 
 */
@Entity
@DiscriminatorValue(value = "PACI")
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
@JsonIgnoreProperties(ignoreUnknown = true)
public class EnderecoContatoPaciente extends EnderecoContato implements Serializable {

	private static final long serialVersionUID = 5673245262353452348L;

	@ManyToOne
	@JoinColumn(name = "PACI_NR_RMR")
	@JsonIgnore
	private Paciente paciente;

	public EnderecoContatoPaciente() {}

	/**
	 * @return the paciente
	 */
	public Paciente getPaciente() {
		return paciente;
	}

	/**
	 * @param paciente the paciente to set
	 */
	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
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
		result = prime * result + ( ( paciente == null ) ? 0 : paciente.hashCode() );
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
		if (!( obj instanceof EnderecoContatoPaciente )) {
			return false;
		}
		EnderecoContatoPaciente other = (EnderecoContatoPaciente) obj;
		if (paciente == null) {
			if (other.paciente != null) {
				return false;
			}
		}
		else {
			if (!paciente.equals(other.paciente)) {
				return false;
			}
		}
		return true;
	}

}
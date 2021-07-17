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
@DiscriminatorValue(value = "LABO")
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
public class EmailContatoLaboratorio extends EmailContato implements Serializable {
	private static final long serialVersionUID = -230546625309863220L;

	@ManyToOne
	@JoinColumn(name = "LABO_ID")
	@Fetch(FetchMode.JOIN)
	@JsonIgnore
	private Laboratorio laboratorio;

	public EmailContatoLaboratorio() {}

	/**
	 * @return the laboratorio
	 */
	public Laboratorio getLaboratorio() {
		return laboratorio;
	}

	/**
	 * @param laboratorio the laboratorio to set
	 */
	public void setLaboratorio(Laboratorio laboratorio) {
		this.laboratorio = laboratorio;
	}
	
}
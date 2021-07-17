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
@DiscriminatorValue(value = "MEDI")
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
public class EmailContatoMedico extends EmailContato implements Serializable {
	private static final long serialVersionUID = -230546625309863220L;

	@ManyToOne
	@JoinColumn(name = "MEDI_ID")
	@Fetch(FetchMode.JOIN)
	@JsonIgnore
	private Medico medico;

	public EmailContatoMedico() {}

	public Medico getMedico() {
		return medico;
	}

	public void setMedico(Medico medico) {
		this.medico = medico;
	}
	
}
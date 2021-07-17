package br.org.cancer.modred.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Classe que representa a entidade de endereços de contato
 * registrados para o médico no Redome.
 * 
 * @author Pizão
 * 
 */
@Entity
@DiscriminatorValue(value = "MEDI")
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
@JsonIgnoreProperties(ignoreUnknown = true)
public class EnderecoContatoMedico extends EnderecoContato {
	private static final long serialVersionUID = -1677815676104335112L;

	@ManyToOne
	@JoinColumn(name = "MEDI_ID")
	@JsonIgnore
	private Medico medico;

	public EnderecoContatoMedico() {}

	
	public Medico getMedico() {
		return medico;
	}

	public void setMedico(Medico medico) {
		this.medico = medico;
	}
	
}
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
 * Classe de persistencia de EnderecoContatoHemoEntidade.
 * 
 * @author Pizão.
 * 
 */
@Entity
@DiscriminatorValue(value = "HEEN")
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
@JsonIgnoreProperties(ignoreUnknown = true)
public class EnderecoContatoHemoEntidade extends EnderecoContato {
	private static final long serialVersionUID = -7308494320417765855L;

	@ManyToOne
	@JoinColumn(name = "HEEN_ID")
	@JsonIgnore
	private HemoEntidade hemoEntidade;

	/**
	 * @return the hemoEntidade
	 */
	public HemoEntidade getHemoEntidade() {
		return hemoEntidade;
	}

	/**
	 * @param hemoEntidade
	 *            the hemoEntidade to set
	 */
	public void setHemoEntidade(HemoEntidade hemoEntidade) {
		this.hemoEntidade = hemoEntidade;
	}

}
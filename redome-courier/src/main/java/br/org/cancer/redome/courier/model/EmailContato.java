package br.org.cancer.redome.courier.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.DiscriminatorFormula;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;
import org.hibernate.validator.constraints.Email;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe de persistencia de EmailContato para a tabela email_contato.
 * 
 * @author Fillipe Queiroz
 */
@Entity
@SequenceGenerator(name = "SQ_EMCO_ID", sequenceName = "SQ_EMCO_ID", allocationSize = 1)
@Table(name = "EMAIL_CONTATO")
@JsonIgnoreProperties(ignoreUnknown = true)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorFormula(
	    "case when TRAN_ID is not null " +
	    "then 'TRAN' " +
	    "when COUR_ID is not null " +
	    "then 'COUR' " +
	    "else 'Unknown' " +
	    "end "
)
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class EmailContato implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_EMCO_ID")
	@Column(name = "EMCO_ID")
	protected Long id;

	@Column(name = "EMCO_TX_EMAIL")
	@Email
	@NotNull
	protected String email;

	@Column(name = "EMCO_IN_EXCLUIDO")
	protected boolean excluido;
	
	@Column(name = "EMCO_IN_PRINCIPAL")
	protected Boolean principal;


}

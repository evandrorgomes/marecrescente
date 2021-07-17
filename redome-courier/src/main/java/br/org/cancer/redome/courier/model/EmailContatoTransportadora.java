package br.org.cancer.redome.courier.model;

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

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Classe de referencia de emails de transportadoras.
 * @author Filipe Paes
 *
 */
@Entity
@DiscriminatorValue(value = "TRAN")
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class EmailContatoTransportadora extends EmailContato implements Serializable {

	private static final long serialVersionUID = 6759052517194772856L;
	
	@ManyToOne
	@JoinColumn(name = "TRAN_ID")
	@Fetch(FetchMode.JOIN)
	@JsonIgnore
	private Transportadora transportadora;

	@Builder
	protected EmailContatoTransportadora(Long id, String email, boolean excluido, Boolean principal, Transportadora transportadora) {
		super(id, email, excluido, principal);
		this.transportadora = transportadora;
	}
	
	



}

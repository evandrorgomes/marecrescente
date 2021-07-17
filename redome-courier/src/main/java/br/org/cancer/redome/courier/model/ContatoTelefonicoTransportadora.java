package br.org.cancer.redome.courier.model;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;



/**
 * Entidade de contato telefonico de transportadora.
 * @author Filipe Paes
 *
 */
@Entity
@DiscriminatorValue(value = "TRAN")
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class ContatoTelefonicoTransportadora  extends ContatoTelefonico implements Serializable {

	private static final long serialVersionUID = -2799841734443477332L;
	
	
	@ManyToOne
	@JoinColumn(name = "TRAN_ID")
	@JsonIgnore
	private Transportadora transportadora;

	@Builder
	private ContatoTelefonicoTransportadora(Long id, Boolean principal, Integer tipo, Integer codArea, Integer codInter,
			Long numero, String complemento, String nome, boolean excluido, Transportadora transportadora) {
		super(id, principal, tipo, codArea, codInter, numero, complemento, nome, excluido);
		this.transportadora = transportadora;
	}
	
	
	

}

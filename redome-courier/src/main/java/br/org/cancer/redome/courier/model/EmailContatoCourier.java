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

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Entidade de mapeamento para emails de Courier.
 * @author Filipe Paes
 *
 */
@Entity
@DiscriminatorValue(value = "COUR")
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class EmailContatoCourier extends EmailContato  implements Serializable{

	private static final long serialVersionUID = -8701498016497430961L;

	@ManyToOne
	@JoinColumn(name = "COUR_ID")
	@Fetch(FetchMode.JOIN)
	@JsonIgnore
	private Courier courier;

	
	
}

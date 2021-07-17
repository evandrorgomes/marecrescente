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

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Entidade de contato telefonico relacionada ao Courier.
 * 
 * @author Fillipe Queiroz
 */
@Entity
@DiscriminatorValue(value = "COUR")
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class ContatoTelefonicoCourier extends ContatoTelefonico implements Serializable {

	private static final long serialVersionUID = 7540058533735538359L;

	@ManyToOne
	@JoinColumn(name = "COUR_ID")
	@JsonIgnore
	private Courier courier;

	
}
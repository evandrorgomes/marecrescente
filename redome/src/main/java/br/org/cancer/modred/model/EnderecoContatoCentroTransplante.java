package br.org.cancer.modred.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.CentroTransplanteView;
import br.org.cancer.modred.controller.view.TransportadoraView;

/**
 * Classe de persistencia de EnderecoContato para CentroTransplante.
 * 
 * @author Cintia Oliveira
 * 
 */
@Entity
@DiscriminatorValue(value = "CETR")
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
@JsonIgnoreProperties(ignoreUnknown = true)
public class EnderecoContatoCentroTransplante extends EnderecoContato implements Serializable {

	private static final long serialVersionUID = -7539896957336533848L;

	@ManyToOne
	@JoinColumn(name = "CETR_ID")
	@JsonIgnore
	private CentroTransplante centroTransplante;
	
	@Column(name = "ENCO_IN_RETIRADA")
	@JsonView({ CentroTransplanteView.Detalhe.class,TransportadoraView.AgendarTransporte.class })
	private boolean retirada;
	
	@Column(name = "ENCO_IN_ENTREGA")
	@JsonView({ CentroTransplanteView.Detalhe.class,TransportadoraView.AgendarTransporte.class })
	private boolean entrega;

	@Column(name = "ENCO_IN_WORKUP")
	@JsonView({ CentroTransplanteView.Detalhe.class,TransportadoraView.AgendarTransporte.class })
	private boolean workup;

	@Column(name = "ENCO_IN_GCSF")
	@JsonView({ CentroTransplanteView.Detalhe.class,TransportadoraView.AgendarTransporte.class })
	private boolean gcsf;

	@Column(name = "ENCO_IN_INTERNACAO")
	@JsonView({ CentroTransplanteView.Detalhe.class,TransportadoraView.AgendarTransporte.class })
	private boolean internacao;
	
	public EnderecoContatoCentroTransplante() {
		super();
	}

	/**
	 * @return the centroTransplante
	 */
	public CentroTransplante getCentroTransplante() {
		return centroTransplante;
	}

	/**
	 * @param centroTransplante the centroTransplante to set
	 */
	public void setCentroTransplante(CentroTransplante centroTransplante) {
		this.centroTransplante = centroTransplante;
	}
	

	/**
	 * @return the retirada
	 */
	public boolean isRetirada() {
		return retirada;
	}

	/**
	 * @param retirada the retirada to set
	 */
	public void setRetirada(boolean retirada) {
		this.retirada = retirada;
	}

	/**
	 * @return the entrega
	 */
	public boolean isEntrega() {
		return entrega;
	}

	/**
	 * @param entrega the entrega to set
	 */
	public void setEntrega(boolean entrega) {
		this.entrega = entrega;
	}

	/**
	 * @return the workup
	 */
	public boolean isWorkup() {
		return workup;
	}

	/**
	 * @param workup the workup to set
	 */
	public void setWorkup(boolean workup) {
		this.workup = workup;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.org.cancer.modred.model.EnderecoContato#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ( ( centroTransplante == null ) ? 0 : centroTransplante.hashCode() );
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.org.cancer.modred.model.EnderecoContato#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		EnderecoContatoCentroTransplante other = (EnderecoContatoCentroTransplante) obj;
		if (centroTransplante == null) {
			if (other.centroTransplante != null) {
				return false;
			}
		}
		else
			if (!centroTransplante.equals(other.centroTransplante)) {
				return false;
			}
		return true;
	}

}
package br.org.cancer.modred.model;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Classe de persistencia de EnderecoContato para CentroTransplante.
 * 
 * @author Cintia Oliveira
 * 
 */
@Entity
@DiscriminatorValue(value = "LABO")
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
@JsonIgnoreProperties(ignoreUnknown = true)
public class EnderecoContatoLaboratorio extends EnderecoContato implements Serializable {

	private static final long serialVersionUID = 1602238426506121285L;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LABO_ID")
	private Laboratorio laboratorio;

	public EnderecoContatoLaboratorio() {
		super();
	}
	
	public EnderecoContatoLaboratorio(String descricaoMunicipio, String siglaUf) {
		super();
		this.municipio = new Municipio();
		this.municipio.setDescricao(descricaoMunicipio);
		this.municipio.setUf(new Uf(siglaUf, null));
	}
	

	
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


	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ( ( laboratorio == null ) ? 0 : laboratorio.hashCode() );
		return result;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (!( obj instanceof EnderecoContatoLaboratorio )) {
			return false;
		}
		EnderecoContatoLaboratorio other = (EnderecoContatoLaboratorio) obj;
		if (laboratorio == null) {
			if (other.laboratorio != null) {
				return false;
			}
		}
		else
			if (!laboratorio.equals(other.laboratorio)) {
				return false;
			}
		return true;
	}
	
}
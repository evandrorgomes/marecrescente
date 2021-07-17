package br.org.cancer.modred.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import br.org.cancer.modred.model.interfaces.IExameDoador;

/**
 * Classe que representa um exame de cordão nacional.
 * 
 * @author bruno.sousa
 *
 */
@Entity
@DiscriminatorValue(value = "CORD_NACIONAL")
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
@JsonTypeName("exameCordaoNacional")
public class ExameCordaoNacional extends Exame implements IExameDoador<CordaoNacional>, Serializable {
	
	private static final long serialVersionUID = -5993142978016549470L;
	
	@ManyToOne(cascade=CascadeType.PERSIST)
	@JoinColumn(name = "DOAD_ID", referencedColumnName="DOAD_ID")
	@NotNull
	@JsonProperty(access = Access.WRITE_ONLY)
	private Doador doador;
	
	/**
	 * Construtor padrão.
	 */
	public ExameCordaoNacional() {
		super();
	}
	
	/**
	 * Construtor opcional.
	 * Cria uma instância de exame apenas com o ID informado.
	 * 
	 * @param exameId ID do exame.
	 */
	public ExameCordaoNacional(Long exameId) {
		super(exameId);
	}
	
	/**
	 * Construtor opcional.
	 * Cria uma instância de exame com o ID e data do exame.
	 * 
	 * @param exameId ID do exame.
	 * @param dataExame Data da realização do exame.
	 */
	public ExameCordaoNacional(Long exameId, LocalDateTime dataCriacao) {
		super(exameId, dataCriacao);
	}
	
	/**
	 * @return the cordao
	 */
	public CordaoNacional getCordao() {
		return (CordaoNacional) doador;
	}
	
	/**
	 * @param cordao the cordao to set
	 */
	public void setCordao(CordaoNacional cordao) {
		this.doador = cordao;
	}	

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ( ( getCordao() == null ) ? 0 : getCordao().hashCode() );
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
		if (!( obj instanceof ExameCordaoNacional )) {
			return false;
		}
		ExameCordaoNacional other = (ExameCordaoNacional) obj;		
		if (getCordao() == null) {
			if (other.getCordao() != null) {
				return false;
			}
		}
		else
			if (!getCordao().equals(other.getCordao())) {
				return false;
			}
		return true;
	}
	
	@Override
	public CordaoNacional getDoador() {
		return getCordao();
	}
	
	@Override
	public void setDoador(CordaoNacional doador) {
		setCordao(doador);		
	}
	
}

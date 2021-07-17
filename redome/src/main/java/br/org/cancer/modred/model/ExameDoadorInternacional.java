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
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.fasterxml.jackson.annotation.JsonTypeName;

import br.org.cancer.modred.model.interfaces.IExameDoador;

/**
 * Classe qu representa um exame.
 * 
 * @author Rafael Pizão
 *
 */
@Entity
@DiscriminatorValue(value = "DOAD_INTERNACIONAL")
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
@JsonTypeName("exameDoadorInternacional")
public class ExameDoadorInternacional extends Exame implements IExameDoador<DoadorInternacional>, Serializable {

	private static final long serialVersionUID = 1438150876109942924L;
	
	@ManyToOne(cascade=CascadeType.PERSIST)
	@JoinColumn(name = "DOAD_ID", referencedColumnName="DOAD_ID")
	@NotNull
	@JsonProperty(access = Access.WRITE_ONLY)
	private Doador doador;
	
	/**
	 * Construtor padrão.
	 */
	public ExameDoadorInternacional() {
		super();
	}
	
	/**
	 * Construtor opcional.
	 * Cria uma instância de exame apenas com o ID informado.
	 * 
	 * @param exameId ID do exame.
	 */
	public ExameDoadorInternacional(Long exameId) {
		super(exameId);
	}
	
	/**
	 * Construtor opcional.
	 * Cria uma instância de exame com o ID e data do exame.
	 * 
	 * @param exameId ID do exame.
	 * @param dataExame Data da realização do exame.
	 */
	public ExameDoadorInternacional(Long exameId, LocalDateTime dataCriacao) {
		super(exameId, dataCriacao);
	}
	
	/**
	 * @return the doador
	 */
	public DoadorInternacional getDoador() {
		return (DoadorInternacional) doador;
	}
	
	/**
	 * @param doador the doador to set
	 */
	public void setDoador(DoadorInternacional doador) {
		this.doador = doador;
	}

	

	
}

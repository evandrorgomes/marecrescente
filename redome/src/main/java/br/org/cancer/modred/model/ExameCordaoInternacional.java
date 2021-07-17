package br.org.cancer.modred.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.hibernate.envers.RelationTargetAuditMode;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import br.org.cancer.modred.model.interfaces.IExameDoador;

/**
 * Classe qu representa um exame de cordão internacional.
 * 
 * @author bruno.sousa
 *
 */
@Entity
@DiscriminatorValue(value = "CORD_INTERNACIONAL")
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
@JsonTypeName("exameCordaoInternacional")
public class ExameCordaoInternacional extends Exame implements IExameDoador<CordaoInternacional>, Serializable {
	
	private static final long serialVersionUID = -5993142978016549470L;
	
	@ManyToOne(cascade=CascadeType.PERSIST)
	@JoinColumn(name = "DOAD_ID", referencedColumnName="DOAD_ID")
	@NotNull
	@JsonProperty(access = Access.WRITE_ONLY)
	private Doador doador;
	
	@Valid
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "exame", fetch = FetchType.LAZY)
	@NotAudited
	@Fetch(FetchMode.SUBSELECT)
	private List<ArquivoExame> arquivosExame;
		
	/**
	 * Construtor padrão.
	 */
	public ExameCordaoInternacional() {
		super();
	}
	
	/**
	 * Construtor opcional.
	 * Cria uma instância de exame apenas com o ID informado.
	 * 
	 * @param exameId ID do exame.
	 */
	public ExameCordaoInternacional(Long exameId) {
		super(exameId);
	}
	
	/**
	 * Construtor opcional.
	 * Cria uma instância de exame com o ID e data do exame.
	 * 
	 * @param exameId ID do exame.
	 * @param dataExame Data da realização do exame.
	 */
	public ExameCordaoInternacional(Long exameId, LocalDateTime dataCriacao) {
		super(exameId, dataCriacao);
	}
	
	/**
	 * @return the doador
	 */
	public CordaoInternacional getCordao() {
		return (CordaoInternacional) doador;
	}
	
	/**
	 * @param cordao the cordao to set
	 */
	public void setCordao(CordaoInternacional cordao) {
		this.doador = cordao; 
	}
	
	/**
	 * @return the arquivosExame
	 */
	public List<ArquivoExame> getArquivosExame() {
		return arquivosExame;
	}
	
	/**
	 * @param arquivosExame the arquivosExame to set
	 */
	public void setArquivosExame(List<ArquivoExame> arquivosExame) {
		this.arquivosExame = arquivosExame;
	}
	
	@Override
	public CordaoInternacional getDoador() {
		return getCordao();
	}
	
	@Override
	public void setDoador(CordaoInternacional doador) {
		setCordao(doador);		
	}

}

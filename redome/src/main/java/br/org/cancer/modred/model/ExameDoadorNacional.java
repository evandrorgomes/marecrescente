package br.org.cancer.modred.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.envers.AuditJoinTable;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.ExameView;
import br.org.cancer.modred.controller.view.GenotipoView;
import br.org.cancer.modred.controller.view.PacienteView;
import br.org.cancer.modred.model.interfaces.IExameMetodologia;

/**
 * Classe qu representa um exame.
 * 
 * @author Rafael Pizão
 *
 */
@Entity
@DiscriminatorValue(value = "DOAD_NACIONAL")
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
@JsonTypeName("exameDoadorNacional")
public class ExameDoadorNacional extends Exame implements IExameMetodologia, Serializable {
	private static final long serialVersionUID = -308702074383591406L;
	
	@Column(name = "EXAM_DT_EXAME")
	@NotNull
	@JsonView({ ExameView.ListaExame.class, ExameView.ConferirExame.class, GenotipoView.ListaExame.class, GenotipoView.Divergente.class  })
	private LocalDate dataExame;
	
	@ManyToOne
	@NotNull
	@JoinColumn(name = "LABO_ID")
	@JsonView({ ExameView.ListaExame.class, ExameView.ConferirExame.class, GenotipoView.ListaExame.class, GenotipoView.Divergente.class })
	private Laboratorio laboratorio;
	
	@ManyToOne(cascade=CascadeType.PERSIST)
	@JoinColumn(name = "DOAD_ID", referencedColumnName="DOAD_ID")
	@NotNull
	@JsonProperty(access = Access.WRITE_ONLY)
	private Doador doador;
	
	@NotEmpty
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(	name = "METODOLOGIA_EXAME",
				joinColumns =
	{ @JoinColumn(name = "EXAM_ID") },
				inverseJoinColumns =
	{ @JoinColumn(name = "METO_ID") })
	@JsonView({ ExameView.ListaExame.class, ExameView.ConferirExame.class, PacienteView.Rascunho.class, 
		GenotipoView.ListaExame.class, GenotipoView.Divergente.class })
	@AuditJoinTable(name = "METODOLOGIA_EXAME_AUD")
	@Fetch(FetchMode.SUBSELECT)
	private List<Metodologia> metodologias;
	
		
	/**
	 * Construtor padrão.
	 */
	public ExameDoadorNacional() {
		super();
	}
	
	/**
	 * Construtor opcional.
	 * Cria uma instância de exame apenas com o ID informado.
	 * 
	 * @param exameId ID do exame.
	 */
	public ExameDoadorNacional(Long exameId) {
		super(exameId);
	}
	
	/**
	 * Construtor opcional.
	 * Cria uma instância de exame com o ID e data do exame.
	 * 
	 * @param exameId ID do exame.
	 * @param dataExame Data da realização do exame.
	 */
	public ExameDoadorNacional(Long exameId, LocalDateTime dataCriacao) {
		super(exameId, dataCriacao);
	}
	
	
	/**
	 * @return the dataExame
	 */
	public LocalDate getDataExame() {
		return dataExame;
	}
	
	/**
	 * @param dataExame the dataExame to set
	 */
	public void setDataExame(LocalDate dataExame) {
		this.dataExame = dataExame;
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
	
	/**
	 * @return the doador
	 */
	public DoadorNacional getDoador() {
		return (DoadorNacional) doador;
	}

	/**
	 * @param doador the doador to set
	 */
	public void setDoador(DoadorNacional doador) {
		this.doador = doador;
	}
		
	/**
	 * @return the metodologias
	 */
	public List<Metodologia> getMetodologias() {
		return metodologias;
	}

	/**
	 * @param metodologias the metodologias to set
	 */
	public void setMetodologias(List<Metodologia> metodologias) {
		this.metodologias = metodologias;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ( ( dataExame == null ) ? 0 : dataExame.hashCode() );
		result = prime * result + ( ( getDoador() == null ) ? 0 : getDoador().hashCode() );
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
		if (!( obj instanceof ExameDoadorNacional )) {
			return false;
		}
		ExameDoadorNacional other = (ExameDoadorNacional) obj;
		if (dataExame == null) {
			if (other.dataExame != null) {
				return false;
			}
		}
		else
			if (!dataExame.equals(other.dataExame)) {
				return false;
			}
		if (getDoador() == null) {
			if (other.getDoador() != null) {
				return false;
			}
		}
		else
			if (!getDoador().equals(other.getDoador())) {
				return false;
			}
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
	
	public Integer obterMaiorPesoMetodologia(){
		return this.metodologias != null && !this.metodologias.isEmpty()? this.metodologias.stream().mapToInt(m-> m.getPesoGenotipo()).max().getAsInt() :0;
	}
	
	

}

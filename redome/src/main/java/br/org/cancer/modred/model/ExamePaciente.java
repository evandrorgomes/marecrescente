package br.org.cancer.modred.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.BuscaView;
import br.org.cancer.modred.controller.view.ExameView;
import br.org.cancer.modred.controller.view.PacienteView;
import br.org.cancer.modred.model.annotations.AssertTrueCustom;
import br.org.cancer.modred.model.interfaces.IExameMetodologia;
import br.org.cancer.modred.service.impl.observers.ExamePacienteAprovadoAposEdicaoObserver;
import br.org.cancer.modred.service.impl.observers.ExamePacienteReprovadoObserver;

/**
 * Classe qu representa um exame.
 * 
 * @author Rafael Pizão
 *
 */
@Entity
@DiscriminatorValue(value = "PACI")
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
@JsonTypeName("examePaciente")
public class ExamePaciente extends Exame implements IExameMetodologia, Serializable {
	private static final long serialVersionUID = -308702074383591406L;

	@Column(name = "EXAM_DT_COLETA_AMOSTRA")
	@NotNull
	@JsonView({ ExameView.ListaExame.class, ExameView.ConferirExame.class, PacienteView.Rascunho.class })
	private LocalDate dataColetaAmostra;
	
	@Column(name = "EXAM_DT_EXAME")
	@NotNull
	@JsonView({ ExameView.ListaExame.class, ExameView.ConferirExame.class, PacienteView.Rascunho.class })
	private LocalDate dataExame;

	@ManyToOne(cascade=CascadeType.PERSIST)
	@JoinColumn(name = "PACI_NR_RMR", referencedColumnName="PACI_NR_RMR")
	@NotNull
	@JsonView({ ExameView.ListaExame.class})
	private Paciente paciente;
	
	@ManyToOne
	@JoinColumn(name = "LABO_ID")
	@JsonView({ ExameView.ListaExame.class, ExameView.ConferirExame.class, PacienteView.Rascunho.class })
	private Laboratorio laboratorio;

	@Column(name = "EXAM_IN_LABORATORIO_PARTICULAR")
	@JsonView({ ExameView.ListaExame.class, ExameView.ConferirExame.class, PacienteView.Rascunho.class })
	@NotNull	
	private Boolean laboratorioParticular;
	
	@Column( name = "EXAM_IN_EDITADO_AVALIADOR")
	private Boolean editadoPorAvaliador = false;
	
	@NotEmpty
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(	name = "METODOLOGIA_EXAME", 
				joinColumns =
	{ @JoinColumn(name = "EXAM_ID") },
				inverseJoinColumns =
	{ @JoinColumn(name = "METO_ID") })
	@JsonView({ ExameView.ListaExame.class, ExameView.ConferirExame.class, PacienteView.Rascunho.class })
	@AuditJoinTable(name = "METODOLOGIA_EXAME_AUD")
	@Fetch(FetchMode.SUBSELECT)
	private List<Metodologia> metodologias;

	@Column(name = "EXAM_IN_TIPO_AMOSTRA")
	@JsonView({ ExameView.ListaExame.class, ExameView.ConferirExame.class, PacienteView.Rascunho.class, BuscaView.UltimoPedidoExame.class})
	private Long tipoAmostra;
	
	/**
	 * Construtor padrão.
	 */
	public ExamePaciente() {
		super();
		addObserver(ExamePacienteAprovadoAposEdicaoObserver.class);
		addObserver(ExamePacienteReprovadoObserver.class);

	}
	
	/**
	 * Construtor opcional.
	 * Cria uma instância de exame apenas com o ID informado.
	 * 
	 * @param exameId ID do exame.
	 */
	public ExamePaciente(Long exameId) {
		super(exameId);
	}
	
	/**
	 * Construtor opcional.
	 * Cria uma instância de exame com o ID e data do exame.
	 * 
	 * @param exameId ID do exame.
	 * @param dataExame Data da realização do exame.
	 */
	public ExamePaciente(Long exameId, LocalDateTime dataCriacao) {
		super(exameId, dataCriacao);
	}

	/**
	 * Construtor opcional.
	 * 
	 * @param id do exame
	 * @param dataExame do exame
	 * @param locusExames do exame
	 */
	public ExamePaciente(Long id, LocalDate dataExame, List<LocusExame> locusExames) {
		super(id, locusExames);
		this.dataExame = dataExame;
	}

	/**
	 * @return the dataExame.
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
	 * @return the paciente
	 */
	public Paciente getPaciente() {
		return paciente;
	}

	/**
	 * @param paciente the paciente to set
	 */
	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ( ( paciente == null ) ? 0 : paciente.hashCode() );
		return result;
	}

	/**
	 * Implementação do método equals para a classe Exame.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		ExamePaciente other = (ExamePaciente) obj;		
		if (paciente == null) {
			if (other.paciente != null) {
				return false;
			}
		}
		else
			if (!paciente.equals(other.paciente)) {
				return false;
			}
		return true;
	}

	/**
	 * Método Bean Validation para verificar se a data de exame é antes do dia. atual
	 * 
	 * @return boolean true se o valor for válido e false se for inválido
	 */
	@JsonIgnore
	@AssertTrueCustom(message = "{dataExame.invalido}", field = "dataExame")
	public boolean isDataExameValida() {
		return this.dataExame != null && this.dataExame.until(LocalDate.now(),
				ChronoUnit.DAYS) >= 0;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String exame = "";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		if (dataExame != null) {
			exame = dataExame.format(formatter);
		}
		
		if (locusExames != null && !locusExames.isEmpty()) {
			exame = exame + " - " + locusExames.stream().map(locusExame -> {
				return locusExame.getId().getLocus().getCodigo() + "*";
			}).collect(Collectors.joining(", "));
		}
		
		return exame;
	}

	
	/**
	 * @return the dataColetaAmostra
	 */
	public LocalDate getDataColetaAmostra() {
		return dataColetaAmostra;
	}

	
	/**
	 * @param dataColetaAmostra the dataColetaAmostra to set
	 */
	public void setDataColetaAmostra(LocalDate dataColetaAmostra) {
		this.dataColetaAmostra = dataColetaAmostra;
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
	 * @return the laboratorioParticular
	 */
	public Boolean getLaboratorioParticular() {
		return laboratorioParticular;
	}

	
	/**
	 * @param laboratorioParticular the laboratorioParticular to set
	 */
	public void setLaboratorioParticular(Boolean laboratorioParticular) {
		this.laboratorioParticular = laboratorioParticular;
	}
	
	/**
	 * Método Bean Validation para verificar se a data de coleta da amostra é menor que a data do exame.
	 * 
	 * @return boolean true se a data da coleta da amostra for maoir que a data do exame. Caso contrário, deve retornar FALSE.
	 */
	@AssertTrueCustom(message = "{erro.validacao.data.maior.igual}", messageParameters={"Data da Coleta da Amostra", "Data do Exame"},  field = "dataColetaAmostra")
	@JsonIgnore
	public boolean isDataColetaAmostraMenorQueDataExame() {
		if (dataColetaAmostra == null || dataExame == null) {
			return true;
		}
		return dataColetaAmostra.until(dataExame, ChronoUnit.DAYS) >= 0; 
	}
	
	/**
	 * Método Bean Validation para verificar se a data da coleta da amostra é antes do dia atual.
	 * 
	 * @return boolean true se o valor for válido e false se for inválido
	 */
	@JsonIgnore
	@AssertTrueCustom(message = "{dataColetaAmostra.invalido}", field = "dataColetaAmostra")
	public boolean isDataColetaAmostraValida() {
		return this.dataColetaAmostra != null && this.dataColetaAmostra.until(LocalDate.now(),
				ChronoUnit.DAYS) >= 0;
	}

	
	
	/**
	 * Indica se o exame foi ajustado, antes de ser aprovado.
	 * 
	 * @return TRUE, caso tenha sido editado.
	 */
	public Boolean getEditadoPorAvaliador() {
		return editadoPorAvaliador;
	}

	public void setEditadoPorAvaliador(Boolean editadoPorAvaliador) {
		this.editadoPorAvaliador = editadoPorAvaliador;
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

	public Integer obterMaiorPesoMetodologia(){
		return this.metodologias != null && !this.metodologias.isEmpty()? this.metodologias.stream().mapToInt(m-> m.getPesoGenotipo()).max().getAsInt():0;
	}

	/**
	 * @return the tipoAmostra
	 */
	public Long getTipoAmostra() {
		return tipoAmostra;
	}

	/**
	 * @param tipoAmostra the tipoAmostra to set
	 */
	public void setTipoAmostra(Long tipoAmostra) {
		this.tipoAmostra = tipoAmostra;
	}
	
}

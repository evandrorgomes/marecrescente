package br.org.cancer.modred.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.Valid;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.annotations.DiscriminatorFormula;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.hibernate.envers.RelationTargetAuditMode;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonTypeIdResolver;

import br.org.cancer.modred.controller.view.AvaliacaoView;
import br.org.cancer.modred.controller.view.ExameDoadorView;
import br.org.cancer.modred.controller.view.ExameDoadorView.ExameListaCombo;
import br.org.cancer.modred.controller.view.ExameView;
import br.org.cancer.modred.controller.view.GenotipoView;
import br.org.cancer.modred.controller.view.PacienteView;
import br.org.cancer.modred.model.annotations.EnumValues;
import br.org.cancer.modred.model.domain.StatusExame;
import br.org.cancer.modred.model.resolver.ExameJsonTypeResolver;
import br.org.cancer.modred.model.security.CriacaoAuditavel;
import br.org.cancer.modred.model.security.Usuario;

/**
 * Classe qu representa um exame.
 * 
 * @author Rafael Pizão
 *
 */
@Entity
@SequenceGenerator(name = "SQ_EXAM_ID", sequenceName = "SQ_EXAM_ID", allocationSize = 1)
@Table(name = "EXAME")
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
@Inheritance(strategy= InheritanceType.SINGLE_TABLE)
@DiscriminatorFormula(
		"(SELECT DISTINCT " + 
		"		CASE 	WHEN EX.PACI_NR_RMR IS NOT NULL THEN 'PACI' " + 
		"				WHEN DOAD.DOAD_IN_TIPO = 0 THEN 'DOAD_NACIONAL' " + 
        "   			WHEN DOAD.DOAD_IN_TIPO = 1 THEN 'DOAD_INTERNACIONAL' " + 
        "   			WHEN DOAD.DOAD_IN_TIPO = 2 THEN 'CORD_NACIONAL' " + 
        "   			WHEN DOAD.DOAD_IN_TIPO = 3 THEN 'CORD_INTERNACIONAL' END " + 
        "FROM EXAME EX LEFT JOIN DOADOR DOAD ON(EX.DOAD_ID = DOAD.DOAD_ID) " + 
        "WHERE (EX.PACI_NR_RMR IS NOT NULL AND EX.PACI_NR_RMR = PACI_NR_RMR) " + 
        "OR (EX.DOAD_ID IS NOT NULL AND EX.DOAD_ID = DOAD_ID))")

@JsonTypeInfo(
		  use = JsonTypeInfo.Id.NAME,
		  defaultImpl = Void.class
)
/*@JsonSubTypes({ 
  @Type(value = Exame.class, name = "exame"),
  @Type(value = ExamePaciente.class, name = "examePaciente"),   
  @Type(value = ExameDoadorInternacional.class, name = "exameDoadorInternacional"),
  @Type(value = ExameDoadorNacional.class, name = "exameDoadorNacional"),
  @Type(value = ExameCordaoInternacional.class, name = "exameCordaoInternacional"),
  @Type(value = ExameCordaoNacional.class, name = "exameCordaoNacional"),
})*/
@JsonTypeIdResolver(ExameJsonTypeResolver.class)
public abstract class Exame extends CriacaoAuditavel implements Serializable {

	private static final long serialVersionUID = 1068298252873677320L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_EXAM_ID")
	@Column(name = "EXAM_ID")
	@JsonView({ ExameView.ListaExame.class, ExameView.ConferirExame.class, AvaliacaoView.ListarPendencias.class, 
		ExameDoadorView.ExameListaCombo.class, GenotipoView.ListaExame.class, GenotipoView.Divergente.class })
	protected Long id;
	
	@Column(name = "EXAM_DT_CRIACAO")
	@JsonView({ ExameView.ListaExame.class, ExameView.ConferirExame.class, ExameListaCombo.class, GenotipoView.ListaExame.class, GenotipoView.Divergente.class })
	protected LocalDateTime dataCriacao;

	@Column(name = "EXAM_NR_STATUS")
	@EnumValues(StatusExame.class)
	@JsonView({ ExameView.ListaExame.class, GenotipoView.ListaExame.class })
	protected Integer statusExame = 0;

	@ManyToOne
	@JoinColumn(name = "MODE_ID")
	protected MotivoDescarte motivoDescarte;

	@ManyToOne
	@JoinColumn(name = "USUA_ID")
	protected Usuario usuario;
	
	@NotEmpty
	@Valid
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "id.exame", fetch = FetchType.LAZY)
	@JsonView({ ExameView.ListaExame.class, ExameView.ConferirExame.class, PacienteView.Rascunho.class, 
				ExameDoadorView.ExameListaCombo.class, GenotipoView.ListaExame.class, GenotipoView.Divergente.class })
	@Fetch(FetchMode.SUBSELECT)
	protected List<LocusExame> locusExames;
	
	@Valid
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "exame", fetch = FetchType.LAZY)
	@NotAudited
	@Fetch(FetchMode.SUBSELECT)
	@JsonView({ ExameView.ListaExame.class, ExameView.ConferirExame.class, PacienteView.Rascunho.class })
	private List<ArquivoExame> arquivosExame;

	
	/**
	 * Construtor padrão.
	 */
	public Exame() {
		super();
		this.dataCriacao = LocalDateTime.now();
	}
	
	/**
	 * Construtor opcional.
	 * Cria uma instância de exame apenas com o ID informado.
	 * 
	 * @param exameId ID do exame.
	 */
	public Exame(Long exameId) {
		super();
		this.id = exameId;
	}
	
	/**
	 * Construtor opcional.
	 * Cria uma instância de exame com o ID e data do exame.
	 * 
	 * @param exameId ID do exame.
	 * @param dataExame Data da realização do exame.
	 */
	public Exame(Long exameId, LocalDateTime dataCriacao) {
		this(exameId);
		this.dataCriacao = dataCriacao;
	}
	
	/**
	 * Construtor opcional.
	 * 
	 * @param id do exame	 
	 * @param locusExames do exame
	 */
	public Exame(Long id, List<LocusExame> locusExames) {
		this(id);
		this.locusExames = locusExames;
	}

	/**
	 * @return the dataCriacao
	 */
	// @Column(name = "EXAM_DT_CRIACAO")
	public LocalDateTime getDataCriacao() {
		return this.dataCriacao;
	}

	/**
	 * @param dataCriacao the dataCriacao to set
	 */
	@Override
	protected void setDataCriacao(LocalDateTime dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	/**
	 * @return the statusExame
	 */
	public Integer getStatusExame() {
		return statusExame;
	}

	/**
	 * @param statusExame the statusExame to set
	 */
	public void setStatusExame(Integer statusExame) {
		this.statusExame = statusExame;
	}

	/**
	 * @return the motivoDescarte
	 */
	public MotivoDescarte getMotivoDescarte() {
		return motivoDescarte;
	}

	/**
	 * @param motivoDescarte the motivoDescarte to set
	 */
	public void setMotivoDescarte(MotivoDescarte motivoDescarte) {
		this.motivoDescarte = motivoDescarte;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * @return the locusExames
	 */
	public List<LocusExame> getLocusExames() {
		return locusExames;
	}
	

	/**
	 * 
	 * @param locusExames locus
	 */
	public void setLocusExames(List<LocusExame> locusExames) {
		if (Optional.ofNullable(locusExames).isPresent()) {
			locusExames.stream().forEach(loc -> {
				if (loc.getId() == null) {
					loc.setId(new LocusExamePk());
				}
				loc.getId().setExame(this);
			});			
		}
		this.locusExames = locusExames;
	}

	/**
	 * @return Usuario
	 */
	public Usuario getUsuario() {
		return this.usuario;
	}

	/**
	 * @param usuario the usuario to set
	 */
	@Override
	protected void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	/**
	 * Obtém quem o locus exame referente ao código de lócus informado.
	 * 
	 * @param codigoLocus código do locus a ser encontrado.
	 * @return o locus exame, se houver o código de lócus para este exame,
	 * NULL, caso contrário.
	 */
	public LocusExame obterLocusExame(String codigoLocus){
		if(CollectionUtils.isNotEmpty(locusExames)){
			Optional<LocusExame> locExaEncontrado = locusExames.stream().filter(locusExame -> 
				codigoLocus.equals(locusExame.getId().getLocus().getCodigo()) && !locusExame.getInativo() 
			).findFirst();
			return locExaEncontrado.isPresent() ? locExaEncontrado.get() : null;
		}
		return null;
	}

	public List<ArquivoExame> getArquivosExame() {
		return arquivosExame;
	}

	/**
	 * Seta a lista de arquivos de exame no exame e
	 * para, para facilitar na hora de salvar, seta o exame
	 * na lista de arquivos também.
	 * 
	 * @param arquivosExame arquivos de exame.
	 */
	public void setArquivosExame(List<ArquivoExame> arquivosExame) {
		if (Optional.ofNullable(arquivosExame).isPresent()) {
			arquivosExame.stream().forEach(arq -> arq.setExame(this));
		}
		this.arquivosExame = arquivosExame;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dataCriacao == null) ? 0 : dataCriacao.hashCode());
		result = prime * result + ((usuario == null) ? 0 : usuario.hashCode());
		return result;
	}

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
		Exame other = (Exame) obj;
		if (dataCriacao == null) {
			if (other.dataCriacao != null) {
				return false;
			}
		}
		else if (!dataCriacao.equals(other.dataCriacao)) {
			return false;
		}
		if (usuario == null) {
			if (other.usuario != null) {
				return false;
			}
		}
		else if (!usuario.equals(other.usuario)) {
			return false;
		}
		return true;
	}
	
}

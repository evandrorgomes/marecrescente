package br.org.cancer.modred.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.BuscaPreliminarView;
import br.org.cancer.modred.model.annotations.CustomPast;
import br.org.cancer.modred.model.annotations.EnumValues;
import br.org.cancer.modred.model.domain.Abo;
import br.org.cancer.modred.model.security.Usuario;

/**
 * @author Pizão
 * 
 * Entidade que representa uma consulta preliminar, anterior ao cadastro de fato,
 * para fins de verificação de compatibilidade entre o paciente e os doadores nacionais
 * disponíveis na base do Redome.
 * 
 */
@Entity
@SequenceGenerator(name = "SQ_BUPR_ID", sequenceName = "SQ_BUPR_ID", allocationSize = 1)
@Table(name = "BUSCA_PRELIMINAR")
public class BuscaPreliminar implements IProprietarioHla {
	private static final long serialVersionUID = -2029933532827345581L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_BUPR_ID")
	@Column(name = "BUPR_ID")
	@JsonView(BuscaPreliminarView.Listar.class)
	private Long id;
	
	@Length(max = 255)
	@NotNull
	@Column(name = "BUPR_TX_NOME_PACIENTE")
	@JsonView(BuscaPreliminarView.Listar.class)
	private String nomePaciente; 
	
	@CustomPast
	@NotNull
	@Column(name = "BUPR_DT_NASCIMENTO")
	@JsonView(BuscaPreliminarView.Listar.class)
	private LocalDate dataNascimento;
	
	@EnumValues(Abo.class)
	@NotNull
	@Column(name = "BUPR_TX_ABO")
	@JsonView(BuscaPreliminarView.Listar.class)
	private String abo;
	
	@Column(name = "BUPR_VL_PESO", precision = 4, scale = 1)
	@NotNull
	@DecimalMin(value = "0", inclusive = false, message = "paciente.peso.maior.zero")
	@Digits(integer = 3, fraction = 1)
	@JsonView(BuscaPreliminarView.Listar.class)
	private BigDecimal peso;
	
	@NotEmpty
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "buscaPreliminar")
	protected List<LocusExamePreliminar> locusExamePreliminar;

	@NotNull
	@Column(name = "BUPR_DT_CADASTRO")
	private LocalDateTime dataCadastro;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "USUA_ID")
	private Usuario usuario;
	
	public BuscaPreliminar() {
	}
	
	public BuscaPreliminar(Long id) {
		super();
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomePaciente() {
		return nomePaciente;
	}

	public void setNomePaciente(String nomePaciente) {
		this.nomePaciente = nomePaciente;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getAbo() {
		return abo;
	}

	public void setAbo(String abo) {
		this.abo = abo;
	}

	public BigDecimal getPeso() {
		return peso;
	}

	public void setPeso(BigDecimal peso) {
		this.peso = peso;
	}

	public List<LocusExamePreliminar> getLocusExamePreliminar() {
		return locusExamePreliminar;
	}

	public void setLocusExamePreliminar(List<LocusExamePreliminar> locusExamePreliminar) {
		this.locusExamePreliminar = locusExamePreliminar;
	}

	/**
	 * Data em que foi realizada o cadastro da busca, ou seja, quando ela foi realizada.
	 * 
	 * @return data do cadastro do registro.
	 */
	public LocalDateTime getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(LocalDateTime dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	/**
	 * Usuário que realizou a busca.
	 * 
	 * @return usuário do sistema.
	 */
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}

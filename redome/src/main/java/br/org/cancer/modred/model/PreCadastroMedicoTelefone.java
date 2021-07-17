package br.org.cancer.modred.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import br.org.cancer.modred.controller.view.PreCadastroMedicoView;
import br.org.cancer.modred.model.annotations.EnumValues;
import br.org.cancer.modred.model.domain.TipoTelefone;

/**
 * Classe que representa a entidade de pré cadastro de telefones para o médico.
 * 
 * @author Rafael Pizão
 *
 */
@Entity
@SequenceGenerator(name = "SQ_PCMT_ID", sequenceName = "SQ_PCMT_ID", allocationSize = 1)
@Table(name = "PRE_CADASTRO_MEDICO_TELEFONE")
public class PreCadastroMedicoTelefone implements Serializable {
	private static final long serialVersionUID = 8285812888914453758L;


	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_PCMT_ID")
	@Column(name = "PCMT_ID")
	protected Long id;

	 
	@Column(name = "PCMT_IN_PRINCIPAL")
	@NotNull
	@JsonView(PreCadastroMedicoView.Detalhe.class)
	protected Boolean principal = false;

	
	@Column(name = "PCMT_IN_TIPO")
	@NotNull
	@EnumValues(TipoTelefone.class)
	@JsonView(PreCadastroMedicoView.Detalhe.class)
	protected Integer tipo;
	
	
	@Column(name = "PCMT_NR_COD_AREA")
	@NotNull
	@JsonView(PreCadastroMedicoView.Detalhe.class)
	protected Integer codArea;
	
	
	@Column(name = "PCMT_NR_NUMERO")
	@NotNull
	@JsonView(PreCadastroMedicoView.Detalhe.class)
	protected Long numero;


	@Column(name = "PCMT_TX_COMPLEMENTO")
	@Length(max = 20)
	@JsonView(PreCadastroMedicoView.Detalhe.class)
	protected String complemento;

	
	@Column(name = "PCMT_TX_NOME")
	@Length(max = 100)
	@JsonView(PreCadastroMedicoView.Detalhe.class)
	protected String nome;
	
	@NotNull
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "PRCM_ID")
	@JsonProperty(access = Access.WRITE_ONLY)
	private PreCadastroMedico preCadastroMedico;


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Boolean getPrincipal() {
		return principal;
	}


	public void setPrincipal(Boolean principal) {
		this.principal = principal;
	}


	public Integer getTipo() {
		return tipo;
	}


	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}


	public Integer getCodArea() {
		return codArea;
	}


	public void setCodArea(Integer codArea) {
		this.codArea = codArea;
	}


	public Long getNumero() {
		return numero;
	}


	public void setNumero(Long numero) {
		this.numero = numero;
	}


	public String getComplemento() {
		return complemento;
	}


	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public PreCadastroMedico getPreCadastroMedico() {
		return preCadastroMedico;
	}


	public void setPreCadastroMedico(PreCadastroMedico preCadastroMedico) {
		this.preCadastroMedico = preCadastroMedico;
	}


}

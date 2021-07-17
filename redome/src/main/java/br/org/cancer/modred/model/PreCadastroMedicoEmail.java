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

import org.hibernate.validator.constraints.Email;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import br.org.cancer.modred.controller.view.PreCadastroMedicoView;

/**
 * Classe que representa a entidade de pré cadastro de e-mails para o médico.
 * 
 * @author Rafael Pizão
 *
 */
@Entity
@SequenceGenerator(name = "SQ_PCEM_ID", sequenceName = "SQ_PCEM_ID", allocationSize = 1)
@Table(name = "PRE_CADASTRO_MEDICO_EMAIL")
public class PreCadastroMedicoEmail implements Serializable {
	private static final long serialVersionUID = -6288208324720695092L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_PCEM_ID")
	@Column(name = "PCEM_ID")
	protected Long id;

	@Column(name = "PCEM_TX_EMAIL")
	@Email
	@NotNull
	@JsonView(PreCadastroMedicoView.Detalhe.class)
	protected String email;
	
	@Column(name = "PCEM_IN_PRINCIPAL")
	@JsonView(PreCadastroMedicoView.Detalhe.class)
	protected Boolean principal = false;
	
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getPrincipal() {
		return principal;
	}

	public void setPrincipal(Boolean principal) {
		this.principal = principal;
	}

	public PreCadastroMedico getPreCadastroMedico() {
		return preCadastroMedico;
	}

	public void setPreCadastroMedico(PreCadastroMedico preCadastroMedico) {
		this.preCadastroMedico = preCadastroMedico;
	}
	
}

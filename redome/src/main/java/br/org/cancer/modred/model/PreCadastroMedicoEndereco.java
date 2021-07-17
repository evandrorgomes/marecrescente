package br.org.cancer.modred.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.PreCadastroMedicoView;

/**
 * Entidade que representa o endereço sugerido pelo médico 
 * no pré cadastro do Redome. 
 * 
 * @author Rafael Pizão
 *
 */
@Entity
@SequenceGenerator(name = "SQ_PCME_ID", sequenceName = "SQ_PCME_ID", allocationSize = 1)
@Table(name = "PRE_CADASTRO_MEDICO_ENDERECO")
public class PreCadastroMedicoEndereco implements Serializable {
	private static final long serialVersionUID = -8753297940518932521L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_PCME_ID")
	@Column(name = "PCME_ID")
	protected Long id;
	  
	@Column(name = "PCME_TX_CEP")
	@NotNull
	@JsonView(PreCadastroMedicoView.Detalhe.class)
	protected String cep;

	@OneToOne
	@JoinColumn(name = "PCME_ID_PAIS")
	@NotNull
	@JsonView(PreCadastroMedicoView.Detalhe.class)
	protected Pais pais;
	
	@Column(name = "PCME_TX_NUMERO")
	@JsonView(PreCadastroMedicoView.Detalhe.class)
	protected String numero;
	
	@Column(name = "PCME_TX_BAIRRO")
	@NotNull
	@Length(max = 255)
	@JsonView(PreCadastroMedicoView.Detalhe.class)
	protected String bairro;


	@Column(name = "PCME_TX_COMPLEMENTO")
	@Length(max = 50)
	@JsonView(PreCadastroMedicoView.Detalhe.class)
	protected String complemento;
	

	@ManyToOne
	@JoinColumn(name = "MUNI_ID")
	@NotNull
	@JsonView(PreCadastroMedicoView.Detalhe.class)
	protected Municipio municipio;
	

	@Column(name = "PCME_TX_NOME_LOGRADOURO")
	@Length(max = 255)
	@NotNull
	@JsonView(PreCadastroMedicoView.Detalhe.class)
	protected String nomeLogradouro;

	@Column(name = "PCME_TX_TIPO_LOGRADOURO")
	@Length(max = 100)
	@NotNull
	@JsonView(PreCadastroMedicoView.Detalhe.class)
	protected String tipoLogradouro;

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getCep() {
		return cep;
	}


	public void setCep(String cep) {
		this.cep = cep;
	}


	public Pais getPais() {
		return pais;
	}


	public void setPais(Pais pais) {
		this.pais = pais;
	}


	public String getNumero() {
		return numero;
	}


	public void setNumero(String numero) {
		this.numero = numero;
	}


	public String getBairro() {
		return bairro;
	}


	public void setBairro(String bairro) {
		this.bairro = bairro;
	}


	public String getComplemento() {
		return complemento;
	}


	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}


	public Municipio getMunicipio() {
		return municipio;
	}


	public void setMunicipio(Municipio municipio) {
		this.municipio = municipio;
	}


	public String getNomeLogradouro() {
		return nomeLogradouro;
	}


	public void setNomeLogradouro(String nomeLogradouro) {
		this.nomeLogradouro = nomeLogradouro;
	}


	public String getTipoLogradouro() {
		return tipoLogradouro;
	}


	public void setTipoLogradouro(String tipoLogradouro) {
		this.tipoLogradouro = tipoLogradouro;
	}

}

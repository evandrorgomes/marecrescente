package br.org.cancer.redome.courier.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.DiscriminatorFormula;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.org.cancer.redome.courier.model.annotation.AssertTrueCustom;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Classe de endereço contato.
 * 
 * @author Filipe Paes
 * 
 */
@Entity
@SequenceGenerator(name = "SQ_ENCO_ID", sequenceName = "SQ_ENCO_ID", allocationSize = 1)
@Table(name = "ENDERECO_CONTATO")
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
@JsonIgnoreProperties(ignoreUnknown = true)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorFormula("case when TRAN_ID is not null " +
		"   then 'TRAN' " +
		"else 'Unknown' " +
		"end ")
@NoArgsConstructor
@Getter
public abstract class EnderecoContato implements Serializable {

	private static final long serialVersionUID = -8607861467071766963L;

	/**
	 * Chave que identifica com exclusividade uma instância desta classe.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_ENCO_ID")
	@Column(name = "ENCO_ID")
	protected Long id;
	/**
	 * Número do CEP.
	 */
	@Column(name = "ENCO_CEP")
	@Length(max = 9)
	protected String cep;
	/**
	 * País da localidade.
	 */
	@OneToOne
	@JoinColumn(name = "ENCO_ID_PAIS")
	@NotNull
	protected Pais pais;
	/**
	 * Número da localidade.
	 */
	@Column(name = "ENCO_TX_NUMERO")
	protected String numero;
	/**
	 * Bairro da localidade.
	 */
	@Column(name = "ENCO_TX_BAIRRO")
	@Length(max = 255)
	protected String bairro;
	/**
	 * Complemento da localidade.
	 */
	@Column(name = "ENCO_TX_COMPLEMENTO")
	@Length(max = 50)
	protected String complemento;
	
	/**
	 * Descrição do logradouro.
	 */
	@Column(name = "ENCO_TX_NOME")
	@Length(max = 255)
	protected String nomeLogradouro;
	
	/**
	 * Municipio da localidade.
	 */
	
	
	@ManyToOne
	@JoinColumn(name = "MUNI_ID")
	protected Municipio municipio;

	/**
	 * Tipo do logradouro.
	 */
	@Column(name = "ENCO_TX_TIPO_LOGRADOURO")
	@Length(max = 100)
	protected String tipoLogradouro;

	@Column(name = "ENCO_IN_EXCLUIDO")
	protected boolean excluido;

	@Column(name = "ENCO_IN_PRINCIPAL")
	protected boolean principal;

	@Column(name = "ENCO_IN_CORRESPONDENCIA")
	protected boolean correspondencia;
	
	protected EnderecoContato(Long id, @Length(max = 9) String cep, @NotNull Pais pais, String numero,
			@Length(max = 255) String bairro, @Length(max = 50) String complemento,
			@Length(max = 255) String nomeLogradouro, Municipio municipio, @Length(max = 100) String tipoLogradouro,
			boolean excluido, boolean principal, boolean correspondencia) {
		super();
		this.id = id;
		this.cep = cep;
		this.pais = pais;
		this.numero = numero;
		this.bairro = bairro;
		this.complemento = complemento;
		this.nomeLogradouro = nomeLogradouro;
		this.municipio = municipio;
		this.tipoLogradouro = tipoLogradouro;
		this.excluido = excluido;
		this.principal = principal;
		this.correspondencia = correspondencia;
	}
	
	@AssertTrueCustom(message = "{javax.validation.constraints.NotNull.message}", field = "cep")
	@JsonIgnore
	public boolean isPermitidoCepNulo() {
		return permiteNuloPaisBrasil(this.cep);
	}

	@AssertTrueCustom(	message = "{javax.validation.constraints.NotNull.message}",
						field = "tipoLogradouro")
	@JsonIgnore
	public boolean isPermitidoTipoLogradouroNulo() {
		return permiteNuloPaisBrasil(this.tipoLogradouro);
	}

	@AssertTrueCustom(	message = "{javax.validation.constraints.NotNull.message}",
						field = "nomeLogradouro")
	@JsonIgnore
	public boolean isPermitidoNomeLogradouroNulo() {
		return permiteNuloPaisBrasil(this.nomeLogradouro);
	}

	@AssertTrueCustom(message = "{javax.validation.constraints.NotNull.message}", field = "bairro")
	@JsonIgnore
	public boolean isPermitidoBairroNulo() {
		return permiteNuloPaisBrasil(this.bairro);
	}

	@AssertTrueCustom(	message = "{javax.validation.constraints.NotNull.message}",
						field = "municipio")
	@JsonIgnore
	public boolean isPermitidoMunicipioNulo() {
		return permiteNuloPaisBrasil(this.municipio);
	}


	@AssertTrueCustom(message = "{javax.validation.constraints.NotNull.message}", field = "numero")
	@JsonIgnore
	public boolean isPermitidoNumeroNulo() {
		return permiteNuloPaisBrasil(this.numero);
	}

	private boolean permiteNuloPaisBrasil(Object propriedade) {
		if (this.pais == null || !this.pais.isBrasil()) {
			return true;
		}
		else
			if (propriedade == null || ( propriedade != null && propriedade instanceof String
					&& StringUtils.isEmpty((String) propriedade) )) {
				return false;
			}
		return true;
	}

}
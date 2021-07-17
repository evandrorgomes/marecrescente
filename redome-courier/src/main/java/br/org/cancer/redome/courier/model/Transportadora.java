package br.org.cancer.redome.courier.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidade que representa a transportadora que irá realizar o transporte do
 * material coletado (empresa terceirizada de courier).
 * 
 * @author Pizão.
 */
@Entity
@SequenceGenerator(name = "SQ_TRAN_ID", sequenceName = "SQ_TRAN_ID", allocationSize = 1)
@Table(name = "TRANSPORTADORA")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Transportadora implements Serializable {
	private static final long serialVersionUID = -56406142749782205L;

	@Id
	@Column(name = "TRAN_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_TRAN_ID")
	private Long id;

	@Column(name = "TRAN_NOME")
	@NotNull
	private String nome;

	@Column(name = "TRAN_ATIVO")
	@Default
	private Boolean ativo = true;
		
	@OneToOne(mappedBy = "transportadora", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Where(clause="ENCO_IN_EXCLUIDO = 0")
	@NotNull
	private EnderecoContatoTransportadora endereco;

	
	@OneToMany(mappedBy = "transportadora", cascade = CascadeType.ALL)
	@Where(clause="EMCO_IN_EXCLUIDO = 0")
	@NotNull
	private List<EmailContatoTransportadora> emails;
	
	@OneToMany(mappedBy = "transportadora", cascade = CascadeType.ALL)
	@Where(clause="COTE_IN_EXCLUIDO = 0")
	@NotNull
	private List<ContatoTelefonicoTransportadora> telefones;
	
	@Transient
	@JsonProperty(access = Access.WRITE_ONLY)
	private List<Long> usuarios;
	
	
}
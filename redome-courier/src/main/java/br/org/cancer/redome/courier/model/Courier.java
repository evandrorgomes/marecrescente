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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Where;
import org.hibernate.validator.constraints.br.CPF;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe de persistencia de Courier.
 * 
 * @author Fillipe Queiroz
 */
@Entity
@SequenceGenerator(name = "SQ_COUR_ID", sequenceName = "SQ_COUR_ID", allocationSize = 1)
@Table(name = "COURIER")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Courier implements Serializable {

	private static final long serialVersionUID = 6778140134277292133L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_COUR_ID")
	@Column(name = "COUR_ID")
	private Long id;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "TRAN_ID")
	private Transportadora transportadora;

	@NotNull
	@Column(name = "COUR_TX_NOME")
	private String nome;

	@NotNull
	@CPF
	@Column(name = "COUR_TX_CPF")
	private String cpf;

	@NotNull
	@Column(name = "COUR_TX_RG")
	private String rg;

	@OneToMany(cascade = { CascadeType.ALL, CascadeType.MERGE }, fetch = FetchType.LAZY)
	@JoinColumn(name = "COUR_ID")
	@Fetch(FetchMode.SUBSELECT)
	@Where(clause="COTE_IN_EXCLUIDO = 0")
	private List<ContatoTelefonicoCourier> contatosTelefonicos;
	
	@OneToMany(mappedBy = "courier", cascade = CascadeType.ALL)
	@Where(clause="EMCO_IN_EXCLUIDO = 0")
	@NotNull
	private List<EmailContatoCourier> emails;
	
	@Column(name = "COUR_IN_ATIVO")
	private Boolean ativo;



}
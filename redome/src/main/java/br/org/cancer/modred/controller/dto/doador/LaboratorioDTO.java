package br.org.cancer.modred.controller.dto.doador;

import java.io.Serializable;

/**
 * Classe de representação de laboratórios vinculados a doadores.
 * 
 * @author ergomes
 * 
 */
public class LaboratorioDTO implements Serializable {
	
	private static final long serialVersionUID = -4305673461280552803L;

	private Long id;
	private String nome;
	private Boolean fazExameCT;
	private Integer quantidadeExamesCT;
	private String nomeContato;
	private EnderecoContatoLaboratorioDTO endereco;
	private Long idRedomeWeb;
	
	/**
	 * 
	 */
	public LaboratorioDTO() {}

	public LaboratorioDTO(Long idRedomeWeb) {
		this.idRedomeWeb = idRedomeWeb;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @param nome
	 *            the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * @return the fazExameCT
	 */
	public Boolean getFazExameCT() {
		return fazExameCT;
	}

	/**
	 * @param fazExameCT
	 *            the fazExameCT to set
	 */
	public void setFazExameCT(Boolean fazExameCT) {
		this.fazExameCT = fazExameCT;
	}

	/**
	 * @return the quantidadeExamesCT
	 */
	public Integer getQuantidadeExamesCT() {
		return quantidadeExamesCT;
	}

	/**
	 * @param quantidadeExamesCT
	 *            the quantidadeExamesCT to set
	 */
	public void setQuantidadeExamesCT(Integer quantidadeExamesCT) {
		this.quantidadeExamesCT = quantidadeExamesCT;
	}

	/**
	 * @return the nomeContato
	 */
	public String getNomeContato() {
		return nomeContato;
	}

	/**
	 * @param nomeContato
	 *            the nomeContato to set
	 */
	public void setNomeContato(String nomeContato) {
		this.nomeContato = nomeContato;
	}

	/**
	 * @return the idRedomeWeb
	 */
	public Long getIdRedomeWeb() {
		return idRedomeWeb;
	}

	/**
	 * @param idRedomeWeb the idRedomeWeb to set
	 */
	public void setIdRedomeWeb(Long idRedomeWeb) {
		this.idRedomeWeb = idRedomeWeb;
	}

	/**
	 * @return the endereco
	 */
	public EnderecoContatoLaboratorioDTO getEndereco() {
		return endereco;
	}

	/**
	 * @param endereco the endereco to set
	 */
	public void setEndereco(EnderecoContatoLaboratorioDTO endereco) {
		this.endereco = endereco;
	}
	
	

}
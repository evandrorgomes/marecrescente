package br.org.cancer.modred.controller.dto;

import java.io.Serializable;

public class CourierDTO implements Serializable {
	
	private static final long serialVersionUID = 4370145244930132732L;

	private Long id;	
	private String nome;
    private String cpf;
    private String rg;

	/**
	 * @param id
	 * @param nome
	 * @param cpf
	 * @param rg
	 */
	public CourierDTO(Long id, String nome, String cpf, String rg) {
		super();
		this.id = id;
		this.nome = nome;
		this.cpf = cpf;
		this.rg = rg;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
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
	 * @param nome the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * @return the cpf
	 */
	public String getCpf() {
		return cpf;
	}

	/**
	 * @param cpf the cpf to set
	 */
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	/**
	 * @return the rg
	 */
	public String getRg() {
		return rg;
	}

	/**
	 * @param rg the rg to set
	 */
	public void setRg(String rg) {
		this.rg = rg;
	}

}

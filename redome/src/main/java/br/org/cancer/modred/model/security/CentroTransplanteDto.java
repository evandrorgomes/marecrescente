package br.org.cancer.modred.model.security;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import br.org.cancer.modred.model.FuncaoTransplante;

public class CentroTransplanteDto implements Serializable {

	private static final long serialVersionUID = 2261244339679971274L;
	
	private Long id;
	
	private String nome;
	
	private Set<FuncaoTransplante> funcoes;
	
	public CentroTransplanteDto(Long id, String nome) {
		super();
		this.id = id;
		this.nome = nome;
		this.funcoes = new HashSet<>();
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
	 * @return the funcoes
	 */
	public Set<FuncaoTransplante> getFuncoes() {
		return funcoes;
	}

	/**
	 * @param funcoes the funcoes to set
	 */
	public void setFuncoes(Set<FuncaoTransplante> funcoes) {
		this.funcoes = funcoes;
	}
	
	

}

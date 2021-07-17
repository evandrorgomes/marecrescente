package br.org.cancer.modred.controller.dto.doador;

import java.io.Serializable;

/**
 * DTO que representa o Registro do doador internacional.
 * 
 * @author Pizão
 *
 */
public class RegistroDTO implements Serializable {
	
	private static final long serialVersionUID = 8419510999438858554L;

	private Long id;
	private String nome;
	private String sigla;
	
	public RegistroDTO() {}
	
	public RegistroDTO(Long id) {
		super();
		this.id = id;
	}
	
	public RegistroDTO(Long id, String nome, String sigla) {
		super();
		this.id = id;
		this.nome = nome;
		this.sigla = sigla;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	/**
	 * @return the sigla
	 */
	public String getSigla() {
		return sigla;
	}
	/**
	 * @param sigla the sigla to set
	 */
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
	
	
}

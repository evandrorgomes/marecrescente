package br.org.cancer.modred.controller.dto.doador;

import java.io.Serializable;

/**
 * Classe de persistencia para Status Doador.
 * 
 * @author ergomes
 */
public class StatusDoadorDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final Long ATIVO = 1L;
	public static final Long ATIVO_RESSALVA = 2L;
	public static final Long INATIVO_TEMPORARIO = 3L;
	public static final Long INATIVO_PERMANENTE = 4L;

	private Long id;
	private String descricao;
	
	public StatusDoadorDTO() {
	}
	
	public StatusDoadorDTO(Long id) {
		super();
		this.id = id;
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
	 * @return the descricao
	 */
	public String getDescricao() {
		return descricao;
	}
	/**
	 * @param descricao the descricao to set
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
}
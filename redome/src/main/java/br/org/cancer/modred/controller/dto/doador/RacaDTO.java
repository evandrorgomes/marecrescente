package br.org.cancer.modred.controller.dto.doador;

import java.io.Serializable;

/**
 * Classe de persistencia de raça para a tabela RACA.
 * 
 * @author ergomes
 * 
 */
public class RacaDTO implements Serializable {

	private static final long serialVersionUID = -9047311841826478635L;

	/**
	 * Constante que representa a Id da raça Indígina dentro da base de dados.
	 */
	public static final Long INDIGENA = 5L;

    /**
     * Constante que representa a Id da raça PRETA dentro da base de dados.
     */
    public static final Long PRETA = 2L;

    private Long id;
    private String nome;
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

}
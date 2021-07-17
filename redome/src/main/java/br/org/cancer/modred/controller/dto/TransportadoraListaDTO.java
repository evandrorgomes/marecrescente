package br.org.cancer.modred.controller.dto;

public class TransportadoraListaDTO {
	
	private Long id;	
	private String nome;

	public TransportadoraListaDTO() {}
	
	public TransportadoraListaDTO(Long id, String nome) {
		this.id = id;
		this.nome = nome;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}
	

}

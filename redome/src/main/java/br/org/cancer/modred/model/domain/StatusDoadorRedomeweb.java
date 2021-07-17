package br.org.cancer.modred.model.domain;

/**
 * Status do doador no sistema REDOMEWEB baseado 
 * na tabela situacaodoador do sismatch.
 * @author Filipe Paes
 *
 */
public enum StatusDoadorRedomeweb {

	ATIVO(0L),	
	AFASTADO(1L),	
	INDISPONIVEL(2L),	
	OBITO(3L),	
	AFASTADO_TEMPORARIAMENTE(4L),	
	AFASTADO_POR_IDADE(5L),	
	DUPLICIDADE(6L);

	/**
	 * Construtor recebendo o identificador como parametnro.
	 * 
	 * @param id
	 */
	StatusDoadorRedomeweb(Long id) {
		this.id = id;
	}

	private Long id;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	
	
	
}

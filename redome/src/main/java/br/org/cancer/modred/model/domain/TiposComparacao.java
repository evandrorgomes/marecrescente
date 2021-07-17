package br.org.cancer.modred.model.domain;

/**
 * Enum de tipos para a comparação nas condições de exibição de uma pergunta.
 * 
 * @author brunosousa
 *
 */
public enum TiposComparacao {
	
	IGUAL(1L),
	MAIOR(2L),
	MENOR(3L),
	DIFERENTE(4L),
	MAIORIGUAL(5L),
    MENORIGUAL(6L);	
	
	private Long id;
	
	TiposComparacao(Long id) {
		this.id = id;
	}
	
	public Long getId() {
		return id;
	}

}

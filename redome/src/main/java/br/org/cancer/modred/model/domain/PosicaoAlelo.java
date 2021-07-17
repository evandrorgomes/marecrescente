package br.org.cancer.modred.model.domain;

import br.org.cancer.modred.model.interfaces.EnumType;

/**
 * Enum para as posições possíveis do alelo.
 * 
 * @author Pizão
 *
 */
public enum PosicaoAlelo implements EnumType<Integer>{

	PRIMEIRO_ALELO(1),
	SEGUNDO_ALELO(2);

	private Integer posicao;

	/**
	 * Construtor com parametros.
	 * 
	 * @param posicao posição do alelo.
	 */
	PosicaoAlelo(Integer posicao) {
		this.posicao = posicao;
	}

	@Override
	public Integer getId() {
		return posicao;
	}

}

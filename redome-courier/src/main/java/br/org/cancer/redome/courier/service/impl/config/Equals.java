package br.org.cancer.redome.courier.service.impl.config;

/**
 * Classe para definir o equals como comparação do filter.
 * 
 * @author Pizão
 * 
 * @param <T> tipo do valor do filtro.
 */
public class Equals<T> extends Filter<T>{

	/**
	 * Construtor sobrecarregado para facilitar a instanciação do
	 * filtro, além de explicitar a necessidade de preenchimento de ambos 
	 * os valores.
	 * 
	 * @param attributeName
	 * @param value
	 */
	public Equals(String attributeName, T value) {
		super(attributeName, value, FilterComparator.EQUALS);
	}

}

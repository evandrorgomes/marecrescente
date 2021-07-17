package br.org.cancer.modred.service.impl.config;

/**
 * Classe para definir o equals como comparação do filter.
 * 
 * @author Pizão
 * 
 * @param <T> tipo do valor do filtro.
 */
public class GreaterThanOrEquals<T> extends Filter<T>{

	/**
	 * Construtor sobrecarregado para facilitar a instanciação do
	 * filtro, além de explicitar a necessidade de preenchimento de ambos 
	 * os valores.
	 * 
	 * @param attributeName
	 * @param value
	 */
	public GreaterThanOrEquals(String attributeName, T value) {
		super(attributeName, value, FilterComparator.GREATER_THAN_OR_EQUALS);
	}

}

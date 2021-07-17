package br.org.cancer.redome.courier.service.impl.config;

/**
 * Classe para negar determinada comparacao do filter.
 * 
 * @author Filipe Paes
 * @param <T> tipo do valor do filtro.
 */
public class NotEquals<T> extends Filter<T>{

	/**
	 * Construtor sobrecarregado para facilitar a instanciação do
	 * filtro, além de explicitar a necessidade de preenchimento de ambos 
	 * os valores.
	 * 
	 * @param attributeName
	 * @param value
	 */
	public NotEquals(String attributeName, T value) {
		super(attributeName, value, FilterComparator.NOT_EQUALS);
	}

}

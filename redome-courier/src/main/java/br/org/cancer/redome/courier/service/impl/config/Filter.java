package br.org.cancer.redome.courier.service.impl.config;

/**
 * Representa a base do filtro a ser aplicado na consulta.
 * Implementações a partir dessa classe definem os comportamentos
 * necessários.
 * @see {@code Equals, NotEquals, IsNull, IsNotNull}
 * 
 * @author Pizão.
 *
 * @param <T> tipo do valor do filtro.
 */
public abstract class Filter<T> extends AttributeSetter<T>{
	
	private FilterComparator condition;
	
	/**
	 * Construtor sobrecarregado para facilitar a instanciação do
	 * filtro, além de explicitar a necessidade de preenchimento de ambos 
	 * os valores.
	 * 
	 * @param attributeName
	 * @param value
	 * @param condition condição para c
	 */
	public Filter(String attributeName, T value, FilterComparator condition) {
		super(attributeName, value);
		this.condition = condition;
	}

	public FilterComparator getCondition() {
		return condition;
	}
	
	public String getComparator() {
		return condition.getOperation();
	}

}

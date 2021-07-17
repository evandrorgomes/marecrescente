package br.org.cancer.redome.courier.service.impl.config;

/**
 * Representa a comparação com IS NOT NULL na utilização dos filtros.
 * 
 * @author Pizão.
 *
 */
public class IsNotNull extends Filter<String>{
	
	/**
	 * Construtor para testar se o filtro é nulo ou não no banco.
	 * 
	 * @param attributeName nome do atributo a ser testado.
	 */
	public IsNotNull(String attributeName) {
		super(attributeName, null, FilterComparator.IS_NOTNULL);
	}

}

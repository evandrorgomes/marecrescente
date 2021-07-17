package br.org.cancer.redome.courier.service.impl.config;

/**
 * Representa a comparação com IS NULL na utilização dos filtros.
 * 
 * @author Pizão.
 *
 */
public class IsNull extends Filter<String>{
	
	/**
	 * Construtor para testar se o filtro é nulo ou não no banco.
	 * 
	 * @param attributeName nome do atributo a ser testado.
	 */
	public IsNull(String attributeName) {
		super(attributeName, null, FilterComparator.IS_NULL);
	}

}

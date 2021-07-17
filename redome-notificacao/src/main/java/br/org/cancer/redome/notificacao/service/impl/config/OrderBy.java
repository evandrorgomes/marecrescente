package br.org.cancer.redome.notificacao.service.impl.config;

/**
 * Define os parâmetros de ordenação para as consultas utilizando o AbstractService como
 * base para as classes de service.
 * 
 * @author Pizão
 *
 */
public class OrderBy extends Attribute {

	private boolean asc = Boolean.TRUE;
	
	/**
	 * Construtor sobrecarregado para facilitar a instanciação e garantir que 
	 * o atributo nome foi informado.
	 * 
	 * @param attributeName nome do atributo para ordenação.
	 */
	public OrderBy(String attributeName) {
		super(attributeName);
	}
	
	/**
	 * Seguindo a mesma idéia do primeiro construtor, serve para facilitar 
	 * a implementação de consultas e minimizar a quantidade de linhas geradas.
	 * 
	 * @param attributeName nome do atributo para ordenação.
	 * @param asc define a forma de ordenação, se será ASC (TRUE) ou DESC (FALSE).
	 */
	public OrderBy(String attributeName, boolean asc) {
		super(attributeName);
		this.asc = asc;
	}

	public boolean isAsc() {
		return asc;
	}
	
}

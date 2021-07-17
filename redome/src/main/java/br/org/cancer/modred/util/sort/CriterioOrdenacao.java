package br.org.cancer.modred.util.sort;

import java.util.function.Function;

/**
 * @author Pizão
 * 
 * Classe para centralizar as cláusulas de ordenação da função ordenar.
 * @param <T>
 */
@SuppressWarnings("rawtypes")
public class CriterioOrdenacao <T> {

	private Function<T, ? extends Comparable> getterAtributo;
	private boolean decrescente;

	/**
	 * Construtor para critério de ordenação considerando crescente por default.
	 * 
	 * @param getterAtributo atributo que será utilizado para o atributo.
	 */
	public CriterioOrdenacao(Function<T, ? extends Comparable> getterAtributo) {
		this(getterAtributo, Boolean.FALSE);
	}
	
	/**
	 * Construtor para critério de ordenação.
	 * 
	 * @param getterAtributo atributo que será utilizando para o atributo.
	 * @param decrescente se for pra ser descrescente.
	 */
	public CriterioOrdenacao(Function<T, ? extends Comparable> getterAtributo, boolean decrescente) {
		this.getterAtributo = getterAtributo;
		this.decrescente = decrescente;
	}

	/**
	 * Retorna o método getter que irá utilizar para ordenação.
	 * 
	 * @return método para ordenação.
	 */
	public Function<T, ? extends Comparable> getGetterAtributo() {
		return getterAtributo;
	}

	public void setGetterAtributo(Function<T, ? extends Comparable> getterAtributo) {
		this.getterAtributo = getterAtributo;
	}

	/**
	 * Indica se é decrescente ou não.
	 * 
	 * @return retorna um boleano indicando decrescente(TRUE) ou ascendente (FALSE).
	 */
	public boolean isDecrescente() {
		return decrescente;
	}

	public void setDecrescente(boolean decrescente) {
		this.decrescente = decrescente;
	}
}
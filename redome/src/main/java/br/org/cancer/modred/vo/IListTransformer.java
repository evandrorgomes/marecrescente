package br.org.cancer.modred.vo;

import java.util.List;

/**
 * 
 * Interface para ser utilizada no DTO.
 * 
 * @author bruno.sousa
 *
 * @param <E>
 */
public interface IListTransformer<E> {

	/**
	 * @return List<E>
	 */
	List<E> transformar();
}

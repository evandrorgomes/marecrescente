package br.org.cancer.modred.vo;

/**
 * Interface para ser utilizada no DTO.
 *
 * @author bruno.sousa
 *
 * @param <E>
 */
public interface IEntityTransformer<E> {

	/**
	 * @return E
	 */
	E transformar();
}

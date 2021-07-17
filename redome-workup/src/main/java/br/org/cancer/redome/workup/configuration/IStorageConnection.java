package br.org.cancer.redome.workup.configuration;

/**
 * Interface para conexão com o storage.
 * 
 * @author brunosousa
 *
 * @param <T>
 */
public interface IStorageConnection<T> {
	
	/**
	 * Obtém o cliente para utilização.
	 * 
	 * @return
	 */
	T getCliente();

}

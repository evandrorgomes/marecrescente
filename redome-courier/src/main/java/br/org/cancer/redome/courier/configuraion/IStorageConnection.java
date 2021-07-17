package br.org.cancer.redome.courier.configuraion;

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

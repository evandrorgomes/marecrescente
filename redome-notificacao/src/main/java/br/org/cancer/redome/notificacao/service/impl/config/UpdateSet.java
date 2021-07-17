package br.org.cancer.redome.notificacao.service.impl.config;

/**
 * Representa o item passado para UPDATE.
 * 
 * @author Pizão
 *
 * @param <T> tipo do valor recebido.
 */
public class UpdateSet<T> extends AttributeSetter<T> {

	/**
	 * Construtor sobrecarregado como referência ao construtor da classe pai.
	 * 
	 * @param attributeName nome do atributo.
	 * @param value valor a ser setado.
	 */
	public UpdateSet(String attributeName, T value) {
		super(attributeName, value);
	}

}

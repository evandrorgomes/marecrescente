package br.org.cancer.redome.courier.observable;

import java.util.Observable;
import java.util.Observer;

/**
 * Classe para implementação do controle de comportamentos
 * mediante a alguma atualização do objeto <T>.
 * Esta classe fornece métodos auxiliares para que, por exemplo,
 * caso uma classe mude seu status, por exemplo, de ABERTO para FECHADO, 
 * um ou uma série de comportamentos sejam disparados, complementando 
 * a ação inicial.
 * 
 * @param <T> Representa a entidade que, possivelmente, sofreu a 
 * mudança de estado.
 * 
 * @see {@link #condition(Object)}.
 * 
 * @author Pizão.
 *
 */
public abstract class EntityObserver<T extends Observable> implements Observer {
	
	/**
	 * Condição que, se atendida, deverá executar o
	 * script referente a estratégia definida para esta 
	 * mudança de estado.
	 * 
	 * @param entity entidade que está passando por mudança.
	 * @return TRUE, caso tenha sido modificado.
	 */
	protected abstract boolean condition(T entity);
	
	/**
	 * Executa um script, quando a condição é atendida 
	 * (retorna TRUE).
	 * 
	 * @param entity entidade que está passando por mudança.
	 */
	protected abstract void process(T entity);
	

	@Override
	@SuppressWarnings("unchecked")
	public void update(Observable observable, Object arg) {
		if (condition((T) observable)) {
			process((T) observable);
		}		
	}
	
}

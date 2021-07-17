/**
 * 
 */
package br.org.cancer.redome.workup.observable;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Classe que identifica quais são as entidades que deverão ser observadas.
 * As classes que extendem desta passa a ter a função de notificar, sempre
 * que houver algum alteração (independente de qual seja) e associado a ela, 
 * poderá ou não, executar alguma instrução que seja necessária após a 
 * alteração identificada. 
 * 
 * @author Pizão
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class EntityObservable extends Observable {

	private List<Class<? extends EntityObserver<? extends Observable>>> observers = new ArrayList<>();
	
	/**
	 * Adiciona uma nova classe de observer para esta entidade.
	 * Este método foi necessário, pois o método {@link #addObserver(java.util.Observer)}.
	 * 
	 * @param observer observer que deverá ser associado.
	 */
	public void addObserver(Class<? extends EntityObserver<? extends Observable>> observerClass){
		observers.add(observerClass);
	}
	
	public List<Class<? extends EntityObserver<? extends Observable>>> getObservers(){
		return this.observers;
	}
	
	/**
	 * Informa que ocorreu a alteração esperada
	 * e notifica todos os Observers que estiverem
	 * informados nesta entidade (normalmente, adicionados
	 * no construtor).
	 */
	public void notifyChange() {
		super.setChanged();
		super.notifyObservers();
	}
	
}

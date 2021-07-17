package br.org.cancer.modred.model.interfaces;

import br.org.cancer.modred.model.Doador;

/**
 * Interface utilizada nas classes de exames de doadores.
 * 
 * @author brunosousa
 *
 * @param <T> 
 * 		Qualquer Classe que estenda de Doador.
 */
public interface IExameDoador<T extends Doador> {
		
	/**
	 * Obtém o doador do exame.
	 * 
	 * @return
	 */
	T getDoador();	
	
	
	/**
	 * 
	 * @param doador
	 */
	void setDoador(T doador);

}

package br.org.cancer.modred.notificacao;

/**
 * Classe para centralizada para contar as notificações.
 * 
 * @author brunosousa
 *
 */
public interface IContarNotificacao {
	
	IContarNotificacao somenteLidas();
	
	IContarNotificacao somenteNaoLidas();

	IContarNotificacao comRmr(Long rmr);
	
	/**
	 * Executar contagem de notificações.
	 * 
	 * @return quantida de notificações
	 */
	Long apply();

	
}

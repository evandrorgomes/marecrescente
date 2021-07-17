package br.org.cancer.modred.notificacao;

/**
 * Define a interface para os eventos que envolvem as notificações.
 * 
 * @author brunosousa
 *
 */
public interface IConfiguracaoCategoriaNotificacao {
	
	/**
	 * Parceiro da notificação.
	 * 
	 * @return classe do parceiro.
	 */
	Class<?> getParceiro();

	/**
	 * Informa o parceiro da notificação.
	 * 
	 * @param parceiro
	 * @return referência a configuração atualizada.
	 */
	IConfiguracaoCategoriaNotificacao comParceiro(Class<?> parceiro);
	
	/**
	 * Classe responsável pela busca de notificações.
	 * 
	 * @return interface
	 */
	IListarNotificacao listar();

	/**
	 * Classe responsável para obter a notificação.
	 * 
	 * @return interface
	 */
	IObterNotificacao obter();

	/**
	 * Classe responsável por marcar a notificação como lida.
	 * 
	 * @return interface
	 */
	ILerNotificacao ler();
	
	/**
	 * Classe responsável por criar notificação.
	 * 
	 * @return interface
	 */
	ICriarNotificacao criar();
	
	/**
	 * Classe responsável por contar as notificação.
	 * 
	 * @return interface
	 */
	IContarNotificacao contar();

}

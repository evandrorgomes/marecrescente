package br.org.cancer.modred.service.integracao;

/**
 * Interface para alteração de status de doador no REDOMEWEB.
 * @author Filipe Paes
 *
 */
public interface IIntegracaoAlterarStatusDoadorRedomeWebService {
	
	/**
	 * Realiza a chamada de alterarção de status de doador via soap no 
	 * REDOMEWEB passando o parametro de inativação.
	 * @param idDoador doador a ser inativado
	 */
	void inativar(Long idDoador);
	
	/**
	 * Realiza a chamada de alterarção de status de doador via soap no 
	 * REDOMEWEB passando o parametro de inativação. 
	 * @param idDoador doador a ser atvivado
	 */
	void ativar(Long idDoador);
}

package br.org.cancer.redome.tarefa.integracao.client;

/**
 * Interface para obter token em servidores REST.
 * @author Filipe Paes
 *
 */
public interface ITokenRequest {
	
	
		
	/**
	 * Setar o cabeçalho da requisição do token.
	 */
	void configHeader();
	
	/**
	 * Parametros do token para o caso da requisição ser 
	 * do tipo form-encoded. 
	 */
	void setTokenParams();
	
	
	/**
	 * Passe por aqui a url e parametros (caso existam)
	 */
	void configUrlBuilder();

	
	/**
	 * Implementação da requisicao do token.
	 * @return token obtido no servidor REST.
	 */
	String getToken();
	
	
}

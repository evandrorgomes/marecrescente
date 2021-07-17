package br.org.cancer.modred.service.integracao;


/**
 * Interface para chamar o serviço de inativação de doador no redomeweb e executar o método de inativação.
 * @author Filipe Paes
 *
 */
public interface IIntegracaoInativarDoadorRedomeWebService {

	/**
	 * Conecta ao sistema redomeweb e inativa o dmr passado por parâmetro.
	 * @param dmr do doador a ser inativado.
	 */
	void executarInativacao(Long dmr);
}

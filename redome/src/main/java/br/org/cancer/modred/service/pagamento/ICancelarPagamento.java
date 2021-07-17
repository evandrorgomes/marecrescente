package br.org.cancer.modred.service.pagamento;

import br.org.cancer.modred.model.Pagamento;

/**
 * Interface para cancelamento de pagamento.
 * @author Filipe Paes
 *
 */
public interface ICancelarPagamento {
	/**
	 * Executa o a criação do pagamento.
	 */
	Pagamento apply();



	/**
	 * Id do Objeto relacionado.
	 * 
	 * @param objetoRelacionado
	 * @return
	 */
	ICancelarPagamento comObjetoRelacionado(Long objetoRelacionado);

 	
	/**
	 * Seta o match ao novo pagamento.
	 * 
	 * @param idMatch
	 * @return
	 */
	ICancelarPagamento comMatch(Long idMatch);
	
	/**
	 * Seta o registro ao novo pagamento.
	 * 
	 * @param idRegistro
	 * @return
	 */
	ICancelarPagamento comRegistro(Long idRegistro);
	
}

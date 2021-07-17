package br.org.cancer.modred.service.pagamento;

import br.org.cancer.modred.model.Pagamento;
import br.org.cancer.modred.model.domain.StatusPagamentos;

/**
 * Classe responsável por criar pagamento.
 * 
 * @author bruno.sousa
 *
 */
public interface ICriarPagamento {

	/**
	 * Executa o a criação do pagamento.
	 */
	Pagamento apply();

	/**
	 * Seta se o pagamento é de cobrança.
	 * 
	 * @return ICriarPagamento
	 */
	ICriarPagamento comoCobranca();

	/**
	 * Id do Objeto relacionado.
	 * 
	 * @param objetoRelacionado
	 * @return
	 */
	ICriarPagamento comObjetoRelacionado(Long objetoRelacionado);

	/**
	 * Seta o status do novo pagamento.
	 * 
	 * @param statusPagamento
	 * @return
	 */
	ICriarPagamento comStatus(StatusPagamentos statusPagamento);
 	
	/**
	 * Seta o match ao novo pagamento.
	 * 
	 * @param idMatch
	 * @return
	 */
	ICriarPagamento comMatch(Long idMatch);
	
	/**
	 * Seta o registro ao novo pagamento.
	 * 
	 * @param idRegistro
	 * @return
	 */
	ICriarPagamento comRegistro(Long idRegistro);
	

}

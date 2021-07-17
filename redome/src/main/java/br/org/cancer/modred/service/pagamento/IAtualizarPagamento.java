package br.org.cancer.modred.service.pagamento;

import br.org.cancer.modred.model.Pagamento;
import br.org.cancer.modred.model.domain.StatusPagamentos;

/**
 * Interface para cancelamento de pagamento.
 * @author Filipe Paes
 *
 */
public interface IAtualizarPagamento {
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
	IAtualizarPagamento comObjetoRelacionado(Long objetoRelacionado);

 	
	/**
	 * Seta o match do pagamento.
	 * 
	 * @param idMatch
	 * @return
	 */
	IAtualizarPagamento comMatch(Long idMatch);
	
	/**
	 * Seta o registro do pagamento.
	 * 
	 * @param idRegistro
	 * @return
	 */
	IAtualizarPagamento comRegistro(Long idRegistro);

	/**
	 * Seta o status do pagamento.
	 * 
	 * @param statusPagamentos
	 * @return
	 */
	IAtualizarPagamento comStatusPagamentos(StatusPagamentos statusPagamentos);
}

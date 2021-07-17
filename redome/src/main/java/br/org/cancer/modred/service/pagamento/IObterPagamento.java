/**
 * 
 */
package br.org.cancer.modred.service.pagamento;

import java.util.List;

import br.org.cancer.modred.model.Pagamento;
import br.org.cancer.modred.model.domain.StatusPagamentos;

/**
 * Classe responsável por obter um pagamento.
 * 
 * @author bruno.sousa
 *
 */
public interface IObterPagamento {

	/**
	 * Obtém apenas 1 pagamento, lançando erro caso encontre outra, pois deveria retornar apenas 1.
	 * 
	 * @return Pagamento obtido.
	 */
	Pagamento apply();
		
	/**
	 * Seta o id do match.
	 * 
	 * @param idMatch
	 * @return
	 */
	IObterPagamento comMatch(Long idMatch);
	
	/**
	 * Seta o id do registro.
	 * 
	 * @param idRegistro
	 * @return
	 */
	IObterPagamento comRegistro(Long idRegistro);


	/**
	 * Seta o id do objeto relacionado.
	 * 
	 * @param objetoRelacionado
	 * @return ObterPagamento
	 */
	IObterPagamento comObjetoRelacionado(Long objetoRelacionado);

	/**
	 * Seta a lista de status de tarefa a ser consultada.
	 * 
	 * @param List<StatusPagamento> status
	 * @return ObterPagamento
	 */
	IObterPagamento comStatus(List<StatusPagamentos> status);
	

}

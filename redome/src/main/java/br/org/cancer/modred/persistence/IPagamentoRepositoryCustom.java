package br.org.cancer.modred.persistence;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.org.cancer.modred.model.Pagamento;
import br.org.cancer.modred.model.domain.StatusPagamentos;

/**
 * Inteface adicional para serviçoes de pagamento.
 * 
 * @author bruno.sousa
 *
 */
public interface IPagamentoRepositoryCustom {

	/**
	 * Método para recuperar o conjuto de pagamentos disponíveis.
	 * 	 
	 * @param idMatch
	 * @param idRegistro
	 * @param idTiposServico
	 * @param statusPagamento
	 * @param relacaoEntidadeId
	 * @param pageable
	 * @return Page<Pagamento> - um conjunto de pagamentos conforme os parâmetros informados no acionamento deste método.
	 */
	Page<Pagamento> listarPagamentos(Long idMatch, Long idRegistro, Long idTiposServico, 
			List<StatusPagamentos> statusPagamento, 
			Long relacaoEntidadeId, Pageable pageable);
	


}

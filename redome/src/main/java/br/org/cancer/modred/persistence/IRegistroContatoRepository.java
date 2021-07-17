package br.org.cancer.modred.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.RegistroContato;

/**
 * Interface de persistencia para registro de contato.
 * @author Filipe Paes
 *
 */
@Repository
public interface IRegistroContatoRepository extends IRepository<RegistroContato, Long> {

	/**
	 * Lista os registros de contato por id de pedido e ordena de forma decrescente por momento da ligacao.
	 * @param page pagina a ser pesquisada.
	 * @param idPedido id do pedido de contato.
	 * @return lista paginada de registros de contato.
	 */
	Page<RegistroContato> findByPedidoContatoIdOrderByMomentoLigacaoDesc(Pageable page, Long idPedido);
}

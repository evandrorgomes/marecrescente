package br.org.cancer.modred.persistence;

import java.util.List;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.modred.model.TentativaContatoDoador;

/**
 * Interface para acesso ao banco de dados envolvendo a classe TentativaContatoDoador.
 * 
 * @author Pizão
 *
 */
@Repository
@Transactional
public interface ITentativaContatoDoadorRepository extends IRepository<TentativaContatoDoador, Long> {

	/**
	 * Consulta para obter tentativas de contato para um Doador.
	 * 
	 * @param idPedidoContato
	 * @return listagem de tentativas.
	 */
	List<TentativaContatoDoador> findByPedidoContatoIdOrderByIdDesc(@Param("idPedidoContato") Long idPedidoContato);
	
	/**
	 * Lista as tentativas de contato por pedido.
	 * @param idPedido - id do pedido das tentativas.
	 * @return listagem de tentativas.
	 */
	List<TentativaContatoDoador> findByPedidoContatoId(@Param("idPedido") Long idPedido);
	
}

package br.org.cancer.modred.persistence;

import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.AvaliacaoPedidoColeta;

/**
 * Interface de métodos de banco de Avaliação de Pedido de Coleta.
 * @author Filipe Paes
 *
 */
@Repository
public interface IAvaliacaoPedidoColetaRepository  extends IRepository<AvaliacaoPedidoColeta, Long>{

}

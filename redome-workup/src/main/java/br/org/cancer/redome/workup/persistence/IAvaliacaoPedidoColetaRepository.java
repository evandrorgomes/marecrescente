package br.org.cancer.redome.workup.persistence;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import br.org.cancer.redome.workup.model.AvaliacaoPedidoColeta;

/**
 * Interface de persistencia para avaliação de pedido de coleta.
 * 
 * @author Bruno Sousa
 *
 */
@Repository
public interface IAvaliacaoPedidoColetaRepository extends IRepository<AvaliacaoPedidoColeta, Long> {

	Optional<AvaliacaoPedidoColeta> findByAvaliacaoResultadoWorkupId(Long idAvaliacaoResultadoWorkup);
}

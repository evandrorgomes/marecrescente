package br.org.cancer.redome.workup.persistence;

import org.springframework.stereotype.Repository;

import br.org.cancer.redome.workup.model.PedidoColeta;

/**
 * Repositório para acesso a base de dados ligadas a entidade PedidoColeta.
 * 
 * @author bruno.sousa
 *
 */
@Repository
public interface IPedidoColetaRepository extends IRepository<PedidoColeta, Long> {


}

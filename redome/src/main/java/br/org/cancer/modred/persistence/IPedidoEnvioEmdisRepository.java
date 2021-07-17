package br.org.cancer.modred.persistence;

import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.PedidoEnvioEmdis;

/**
 * Interface de persistencia de envio de dados para Emdis.
 * @author Filipe Paes
 *
 */
@Repository
public interface IPedidoEnvioEmdisRepository extends IRepository<PedidoEnvioEmdis, Long>  {

}

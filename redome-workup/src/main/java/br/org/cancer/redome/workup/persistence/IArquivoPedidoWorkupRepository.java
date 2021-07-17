package br.org.cancer.redome.workup.persistence;

import java.util.List;

import org.springframework.stereotype.Repository;

import br.org.cancer.redome.workup.model.ArquivoPedidoWorkup;

/**
 * Interface de persistencia para ArquivoPrescricao.
 * 
 * @author bruno.sousa
 *
 */
@Repository
public interface IArquivoPedidoWorkupRepository extends IRepository<ArquivoPedidoWorkup, Long> {
	List<ArquivoPedidoWorkup> findByPedidoWorkupId(Long idPedido);
}

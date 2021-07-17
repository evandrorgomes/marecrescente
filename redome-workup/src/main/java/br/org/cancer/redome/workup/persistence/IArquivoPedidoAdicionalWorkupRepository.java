package br.org.cancer.redome.workup.persistence;

import java.util.List;

import org.springframework.stereotype.Repository;

import br.org.cancer.redome.workup.model.ArquivoPedidoAdicionalWorkup;

/**
 * Interface de persistencia para Arquivo Adicional.
 * 
 * @author ergomes
 *
 */
@Repository
public interface IArquivoPedidoAdicionalWorkupRepository extends IRepository<ArquivoPedidoAdicionalWorkup, Long> {
	List<ArquivoPedidoAdicionalWorkup> findByPedidoAdicional(Long idPedidoAdicional);
}

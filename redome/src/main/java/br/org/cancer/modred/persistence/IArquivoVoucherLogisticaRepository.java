package br.org.cancer.modred.persistence;

import java.util.List;

import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.ArquivoVoucherLogistica;

/**
 * Interface de persistencia para ArquivoVoucherLogistica.
 * 
 * @author bruno.sousa
 *
 */
@Repository
public interface IArquivoVoucherLogisticaRepository extends IRepository<ArquivoVoucherLogistica, Long> {

	/**
	 * Lista todos os arquivos de voucher por pedido de logistica.
	 * 
	 * @param idPedidoLogistica Identificador do pedido de logistica
	 * @return lista de arquivos de voucher.
	 */
	List<ArquivoVoucherLogistica> findAllByPedidoLogisticaId(Long idPedidoLogistica);

}

package br.org.cancer.modred.service;

import java.io.File;
import java.util.List;

import br.org.cancer.modred.model.ArquivoVoucherLogistica;
import br.org.cancer.modred.service.custom.IService;

/**
 * Interface de negocios para ArquivoVoucherLogistica.
 * 
 * @author bruno.sousa
 *
 */
public interface IArquivoVoucherLogisticaService extends IService<ArquivoVoucherLogistica, Long> {

	/**
	 * Método para excluir voucher de logistica.
	 * @param id
	 */
	void excluir(Long id);

	/**
	 * Método para excluir o voucher do storage.
	 * 
	 * @param pedidoLogisticaId
	 * @param nomeArquivo
	 */
	void excluirVoucherDoStorage(Long pedidoLogisticaId, String nomeArquivo);
	
	/**
	 * Obtém o arquivo no storage para download.
	 * @param id do arquivo.
	 * @return arquivo obtido no storage.
	 */
	File obterArquivoNoStorage(Long idArquivo);
	
	/**
	 * Lista todos os arquivos por pedido de logistica.
	 * 
	 * @param idPedidoLogistica - identificador do pedido de logistica.
	 * @return Lista com os nomes dos arquivos de voucher.
	 */
	List<ArquivoVoucherLogistica> listarPorPedidoLogistica(Long idPedidoLogistica);
	
	
	
}

package br.org.cancer.redome.workup.service;

import org.springframework.web.multipart.MultipartFile;

import br.org.cancer.redome.workup.model.ArquivoVoucherLogistica;
import br.org.cancer.redome.workup.service.custom.IService;

/**
 * Interface de negocios para ArquivoVoucherLogistica
 * 
 * @author bruno.sousa
 *
 */
public interface IArquivoVoucherLogisticaService extends IService<ArquivoVoucherLogistica, Long> {

	/**
	 * Método para enviar arquivo de voucher de logistica para o storage
	 * 
	 * @param idPedidoLogistica Identificador do pedido de logistica.
	 * @param arquivo Arquivo a ser enviado para o storage
	 * @return Caminho do arquivo no storage.
	 */
	String enviarArquivoVoucherLogisticaParaStorage (Long idPedidoLogistica, MultipartFile arquivo);
	
	/**
	 * Método para excluir arquivo de voucher de logistica de doador para workup do storage
	 * 
	 * @param idPedidoLogistica Identificador do pedido de logistica.
	 * @param caminho Caminho do arquivo no storage.
	 */
	void excluirArquivoVoucherLogisticaDoStorage(Long idPedidoLogistica, String caminho);

	void excluirPorId(Long id);

}

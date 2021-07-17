package br.org.cancer.redome.courier.service;

import java.io.File;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import br.org.cancer.redome.courier.model.ArquivoPedidoTransporte;
import br.org.cancer.redome.courier.model.PedidoTransporte;
import br.org.cancer.redome.courier.service.custom.IService;

/**
 * Interface para métodos de negocio do ArquivoPedidoTransporte.
 * 
 * @author Bruno Sousa.
 */
public interface IArquivoPedidoTransporteService extends IService<ArquivoPedidoTransporte, Long> {

	/**
	 * Obtém todos os arquivos do pedido de transporte em um arquivo zipado.
	 * 
	 * @param idPedidoTransporte Identificador do pedido de transporte.
	 * @return Arquivo zipado com todos os aqruivos do pedido de transporte
	 */
	File obterArquivosPedidoTransporteZipados(Long idPedidoTransporte);

	/**
	 * Atualizar lista todos os arquivos CNT do pedido de transporte.
	 * 
	 * @param PedidoTransporte Objeto do pedido de transporte.
	 */
	void atualizarListaDeArquivosCnt(PedidoTransporte pedidoTransporte, String descricaoAlteracao, List<MultipartFile> arquivos);
}

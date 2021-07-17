package br.org.cancer.modred.service;

import java.io.File;
import java.io.IOException;

import br.org.cancer.modred.exception.BusinessException;
import br.org.cancer.modred.model.ArquivoPedidoTransporte;
import br.org.cancer.modred.model.PedidoTransporte;

/**
 * Interface de serviço para Arquivo de pedido de transporte.
 * 
 * @author Queiroz
 *
 */
public interface IArquivoPedidoTransporteService {

	/**
	 * Método para obter um arquivo por id.
	 * 
	 * @param id
	 * @return ArquivoPedidoTransporte
	 */
	ArquivoPedidoTransporte obterArquivo(Long id);

	/**
	 * Método para obter arquivo do storage de acordo com a id.
	 * 
	 * @param id do ArquivoPedidoTransporte
	 * @return DLPayload retorno do arquivo do storage
	 */
	File obterArquivoDoStorage(Long id) throws BusinessException;
	
	/**
	 * Lista os arquivos de transporte associado ao pedido informado.
	 * 
	 * @param pedidoTransporte pedido de transporte a ser filtrado.
	 * @return arquivo zipado contendo todos os arquivos de transporte.
	 * @throws BusinessException caso não seja possível acessar o arquivo.
	 * @throws IOException caso não consigar ler o arquivo.
	 */
	File listarArquivosTransporteZipados(PedidoTransporte pedidoTransporte) throws BusinessException, IOException;

}

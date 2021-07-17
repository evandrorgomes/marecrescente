package br.org.cancer.redome.workup.service;

import java.io.File;

import org.springframework.web.multipart.MultipartFile;

import br.org.cancer.redome.workup.model.ArquivoResultadoWorkup;
import br.org.cancer.redome.workup.service.custom.IService;

/**
 * Interface de negocios para ArquivoResultadoWotkup.
 * 
 * @author bruno.sousa
 *
 */
public interface IArquivoResultadoWorkupService extends IService<ArquivoResultadoWorkup, Long> {

	/**
	 * Método para enviar arquivo de resultado de workup para o storage
	 * 
	 * @param idPedidoWorkup Identificador do pedido de workup.
	 * @param arquivo Arquivo a ser enviado para o storage
	 * @return Caminho do arquivo no storage.
	 */
	String enviarArquivoResultadoWorkupParaStorage(Long idPedidoWorkup, MultipartFile arquivo);

	/**
	 * Método para excluir arquivo de resultado de workup do storage
	 * 
	 * @param idPedidoWorkup Identificador do pedido de workup.
	 * @param caminho Caminho do arquivo no storage.
	 */
	void excluirArquivoResultadoWorkupDoStorage(Long idPedidoWorkup, String caminho);

	/**
	 * Obtém o arquivo do storage para download.
	 * 
	 * @param idArquivo Identificador do arquivo.
	 * @return Arquivo do storage
	 */
	File obterArquivoDoStorage(Long idArquivo);

}

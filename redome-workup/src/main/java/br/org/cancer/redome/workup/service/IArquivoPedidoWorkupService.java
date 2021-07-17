package br.org.cancer.redome.workup.service;

import java.io.File;
import java.util.List;

import br.org.cancer.redome.workup.exception.BusinessException;
import br.org.cancer.redome.workup.model.ArquivoPedidoWorkup;
import br.org.cancer.redome.workup.service.custom.IService;

/**
 * Interface de negocios para ArquivoPrescricao.
 * 
 * @author bruno.sousa
 *
 */
public interface IArquivoPedidoWorkupService extends IService<ArquivoPedidoWorkup, Long> {

	/**
	 * Obtém o arquivo no storage.
	 * 
	 * @param idArquivo
	 * @return arquivo da prescrição
	 */
	File obterArquivoDoStorage(Long idArquivo);
	
	List<ArquivoPedidoWorkup> obterArquivosPorPedidoWorkup(Long idPedido) throws BusinessException;
}

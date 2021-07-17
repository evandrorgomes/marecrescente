package br.org.cancer.redome.workup.service;

import java.io.File;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import br.org.cancer.redome.workup.model.ArquivoPedidoAdicionalWorkup;
import br.org.cancer.redome.workup.service.custom.IService;

/**
 * Interface de negocios para Arquivo Adicional de workup.
 * 
 * @author ergomes
 *
 */
public interface IArquivoPedidoAdicionalWorkupService extends IService<ArquivoPedidoAdicionalWorkup, Long> {

	String enviarArquivoPedidoAdicionalWorkupParaStorage(Long idPedidoWorkup, MultipartFile arquivo);

	void excluirArquivoExameAdicionalWorkupDoStorage(Long idPedidoAdicional, String caminho);
	
	File obterArquivoDoStorage(Long id);
	
	List<ArquivoPedidoAdicionalWorkup> listarArquivosStorage(Long idPedidoAdicional);
	
	List<ArquivoPedidoAdicionalWorkup> salvarArquivosExamesAdicionaisWorkup(Long idPedidoAdicional, List<ArquivoPedidoAdicionalWorkup> listaNovaDeArquivosAdicionais);
	
	List<ArquivoPedidoAdicionalWorkup> listarArquivosExamesAdicionais(Long idPedidoAdicional);
	
}

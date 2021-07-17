package br.org.cancer.modred.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import br.org.cancer.modred.model.ArquivoPedidoIdm;
import br.org.cancer.modred.model.PedidoIdm;
import br.org.cancer.modred.service.custom.IService;

/**
 * Interface de serviço para Arquivo Pedido IDM.
 * 
 * @author Pizão
 *
 */
public interface IArquivoPedidoIdmService extends IService<ArquivoPedidoIdm, Long>{
	
	/**
	 * Salva os arquivos de laudo no storage e gera o registro de cada arquivo carregado.
	 * 
	 * @param listaArquivos lista de arquivos a serem salvos.
	 * @param pedidoIDM ID do pedido de IDM.
	 * @throws IOException exceção em caso de erro ao salvar.
	 */
	void salvarLaudo(List<MultipartFile> listaArquivos, PedidoIdm pedidoIDM) throws IOException;
}

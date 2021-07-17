package br.org.cancer.modred.service;

import java.io.File;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import br.org.cancer.modred.exception.BusinessException;
import br.org.cancer.modred.model.ArquivoRelatorioInternacional;
import br.org.cancer.modred.service.custom.IService;

/**
 * Interface de negócios de Arquivo de relatório internacional.
 * @author Filipe Paes
 *
 */
public interface IArquivoRelatorioInternacionalService extends IService<ArquivoRelatorioInternacional, Long> {

	
	/**
	 * Salva um novo arquivo de relatório internacional com sua referida busca.
	 * @param arquivo arquivo fisico a ser enviado para o storage.
	 * @param arquivoRelatorio objeto que faz referencia ao arquivo de relatório.
	 * @return retorna o nome do arquivo no storage.
	 */
	String salvarArquivo(MultipartFile arquivo, ArquivoRelatorioInternacional arquivoRelatorio);

	/**
	 * Lista os relatórios internaciones de uma busca.
	 * 
	 * @param id - identificador da busca;
	 * @return lista de arquivos
	 */
	List<ArquivoRelatorioInternacional> listarArquivoPorBusca(Long id);

	/**
	 * Exclui o arquivo fisicamente do storage e sua referencia no banco.
	 * @param idArquivoRelatorioInternacional id do registro de relatorio internacional.
	 */
	void excluirArquivo(Long idArquivoRelatorioInternacional);
	
	/**
	 * Obtem um arquivo zipado do relatorio internacional.
	 * @param idArquivoRelatorioInternacional identificacao do arquivo no banco.
	 * @return arquivo zipado recuperado do storage.
	 * @throws BusinessException
	 */
	File obterZip(Long idArquivoRelatorioInternacional) throws BusinessException;
	
	/**
	 * Obtem um arquivo do relatorio internacional.
	 * @param idArquivoRelatorioInternacional identificacao do arquivo no banco.
	 * @return arquivo zipado recuperado do storage.
	 * @throws BusinessException
	 */
	File obterArquivo(Long idArquivoRelatorioInternacional) throws BusinessException;
	
	
	/**
	 * Obtem todos os arquivos de relatorio internacional zipados.
	 * @param idBusca identificacao da busca.
	 * @return arquivo zip com arquivos de relatorio.
	 * @throws BusinessException
	 */
	File obterTodosArquivosZipadosPorBusca(Long idBusca) throws BusinessException;
}

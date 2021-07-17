package br.org.cancer.modred.service;

import org.springframework.web.multipart.MultipartFile;

import br.org.cancer.modred.model.ResultadoWorkup;

/**
 * Interface de negócios para ArquivoResultadoWorkup.
 * @author Filipe Paes
 *
 */
public interface IArquivoResultadoWorkupService {
	
	/**
	 * Método para gravação de arquivo de laudo de exame de 
	 * workup.
	 * @param resultado objeto de resultado para montar o caminho no storage
	 * @param arquivo para ser upado para o storage.
	 * @return caminho do arquivo upado.
	 */
	String salvarArquivoWorkup(ResultadoWorkup resultado, MultipartFile arquivo);
	
	/**
	 * Método para exclusão de arquivo de laudo de exame de workup.
	 * @param caminho caminho do arquivo a ser excluído
	 */
	void excluirArquivoWorkup(String caminho);
}

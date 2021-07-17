package br.org.cancer.modred.service;

import java.io.File;

import org.springframework.web.multipart.MultipartFile;

import br.org.cancer.modred.exception.BusinessException;

/**
 * Interface para métodos de negócios de Arquivo de Evolução.
 * 
 * @author Filipe Paes
 *
 */
public interface IArquivoEvolucaoService {

	/**
	 * Método que guarda o arquivo enviado no storage para depois ser recuperado
	 * e salvo no cadastro do paciente.
	 * 
	 * @param arquivo
	 *            a ser enviado
	 * @return caminho e o nome do arquivo enviado
	 */
	String salvarArquivo(MultipartFile arquivo);

	/**
	 * Método para baixar arquivos de evolução do storage.
	 * 
	 * @param id
	 * @return arquivo a ser baixado.
	 * @throws BusinessException
	 */
	File obterArquivoDoStorage(Long id) throws BusinessException;
}

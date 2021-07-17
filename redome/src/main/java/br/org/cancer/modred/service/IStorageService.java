package br.org.cancer.modred.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import br.org.cancer.modred.exception.BusinessException;

/**
 * Serviço para execução das operações no Storage.
 * 
 * @author Cintia Oliveira
 *
 * @param <T>
 */
public interface IStorageService<T> {

	/**
	 * Método para upload de arquivo no storage.
	 * 
	 * @param nome nome do arquivo com extensão para armazenamento
	 * @param diretorio estrutura de diretório para armazenamento
	 * @param arquivo arquivo para upload
	 * @throws IOException se o ContentType do arquivo não puder ser determinado
	 */
	void upload(String nome, String diretorio,
			MultipartFile arquivo) throws IOException;

	/**
	 * Método para capturar arquivos do storage.
	 * 
	 * @param nome
	 * @param diretorio
	 * @return File arquivo baixado do storage
	 */
	File download(String nome, String diretorio);
	
	/**
	 * Recupera o arquivo do storage, a partir da url completa,
	 * e retorna arquivo pronto para serializar.
	 * 
	 * @param caminhoArquivo caminho completo do arquivo.
	 * @return arquivo pronto para serializar.
	 */
	File download(String caminhoArquivo);

	/**
	 * Método para obter um arquivo de zip de acordo com uma lista de arquivos.
	 * 
	 * @param diretorio
	 * @param arquivos
	 * @return String arquivo de zip com todos os arquivos passados
	 */
	File obterArquivoZip(String diretorio, String... arquivos) throws BusinessException, IOException;

	/**
	 * Método para mover o arquivo de exame do diretório temporário.
	 * 
	 * @param usuarioId id do usuário logado
	 * @param rmr id do paciente salvo
	 * @param caminhoArquivo nome do arquivo que estava salvo como temporario
	 */
	void moverArquivoExame(Long usuarioId, Long rmr, String caminhoArquivo) throws BusinessException;
	
	
	/**
	 * Método para mover o arquivo de evolução do diretório temporário.
	 * 
	 * @param usuarioId id do usuário logado
	 * @param rmr id do paciente salvo
	 * @param caminhoArquivo nome do arquivo que estava salvo como temporario
	 */
	void moverArquivoEvolucao(Long usuarioId, Long rmr, String caminhoArquivo) throws BusinessException;

	/**
	 * Remover todos os arquivos por id de usuario.
	 * 
	 * @param usuarioId
	 */
	void removerArquivosPorIdUsuario(Long usuarioId);

	/**
	 * Método para buscar arquivos por diretorio.
	 * 
	 * @param caminhoPasta string com o nome do diretorio
	 * @return Lista de referencia de arquivos do storage
	 */
	List<T> localizarArquivosPorDiretorio(String caminhoPasta);

	/**
	 * Método para buscar arquivos por diretorio.
	 * 
	 * @param caminhoPasta string com o nome do diretorio
	 * @return Lista de referencia de arquivos do storage
	 */
	List<T> localizarArquivosPorIdUsuario(Long idUsuario);

	/**
	 * Método para remover um arquivo.
	 * 
	 * @param caminhoArquivo caminho do arquivo que será removido
	 */
	void removerArquivo(String caminhoArquivo);

	/**
	 * Verifica a existencia do arquivo no storage.
	 * 
	 * @param caminhoArquivo
	 * @return referência do arquivo no storage
	 */
	Boolean verificarExistenciaArquivo(String caminhoArquivo);

	/**
	 * Verifica a existencia do arquivo no storage.
	 * 
	 * @param idUsuario id do usuario dono do arquivo
	 * @return referência do arquivo no storage
	 */
	Boolean verificarExistenciaArquivoPorIdUsuario(Long idUsuario, String caminhoArquivo);

	/**
	 * Remove arquivo por nome do diretorio de usuario.
	 * 
	 * @param idUsuario id do usuario dono do arquivo
	 * @return referência do arquivo no storage
	 */
	void removerArquivoPorIdUsuario(Long idUsuario, String caminhoArquivo);
	
	String[] obterNomeEDiretorioPorCaminho(String caminho);
	
	/**
	 * Obtém um arquivo zipado a partir da lista de arquivos.
	 * 
	 * @param arquivos arquivos a serem zipados.
	 * @return arquivo zipado.
	 * @throws BusinessException exceção caso lista vazia.
	 * @throws IOException exceção caso não consiga ler o arquivo.
	 */
	File obterArquivoZip(List<File> arquivos) throws BusinessException, IOException;

	/**
	 * Move o arquivo do diretório origem para o destino.
	 * 
	 * @param origem diretório do origem.
	 * @param destino diretório de destino.
	 */
	void moverArquivo(String origem, String destino);
	
}

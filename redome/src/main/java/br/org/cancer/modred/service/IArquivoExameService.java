package br.org.cancer.modred.service;

import java.io.File;

import org.springframework.web.multipart.MultipartFile;

import br.org.cancer.modred.exception.BusinessException;
import br.org.cancer.modred.model.ArquivoExame;

/**
 * Interface de serviço para Arquivo Exame.
 * @author Filipe Paes
 *
 */
public interface IArquivoExameService {
    
    /**
     * Método para obter um arquivo exame por id.
     * @param id
     * @return ArquivoExame
     */
    ArquivoExame obterArquivoExame(Long id);
    
    /**
     * Método para obter arquivo do storage de acordo com a id.
     * do arquivo exame
     * @param id do ArquivoExame
     * @return DLPayload retorno do arquivo do storage
     */
    File obterArquivoDoStorage(Long id) throws BusinessException;
    
    /**
     * Método para obter arquivo zipado do storage de acordo com a id.
     * do arquivo exame
     * @param id do ArquivoExame
     * @return DLPayload retorno do arquivo do storage
     * @throws BusinessException
     */
    File obterArquivoZipadoDoStorage(Long id) throws BusinessException;
    
    /**
     * Retorna o caminho do arquivo e o paciente associado ao exame id informado.
     * 
     * @param arquivoExameId - id do arquivo de exame
     * @return ArquivoExame arquivoExame
     */
    ArquivoExame obterArquivoExameParaDownload(Long arquivoExameId);
    
    /**
     * Método que guarda o arquivo enviado no storage para depois ser
     * recuperado e salvo no cadastro do paciente.
     * 
     * @param arquivo
     * @param idUsuario
     * @return caminho e o nome do arquivo enviado
     */
    String salvarArquivo(MultipartFile arquivo);
        
}

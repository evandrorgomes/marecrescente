package br.org.cancer.modred.service;

import java.io.File;

import br.org.cancer.modred.model.ArquivoPrescricao;
import br.org.cancer.modred.service.custom.IService;

/**
 * Interface de negocios para ArquivoPrescricao.
 * 
 * @author bruno.sousa
 *
 */
public interface IArquivoPrescricaoService extends IService<ArquivoPrescricao, Long> {

	/**
	 * Obtém o arquivo da prescrição no storage.
	 * 
	 * @param idArquivo
	 * @return arquivo da prescrição
	 */
	File obterArquivoDoStorage(Long idArquivo);
	
	/**
	 * Obtém a entidade onde foi registrado a autorização do paciente.
	 * 
	 * @param idPrescricao ID da prescrição.
	 * @return arquivo de prescrição com a autorização.
	 */
	ArquivoPrescricao obterAutorizacaoPaciente(Long idPrescricao);
	
	/**
	 * Obtém o arquivo da prescrição no storage.
	 * 
	 * @param arquivoPrescricao arquivo da prescrição.
	 * @return arquivo da prescrição.
	 */
	File obterArquivoStorage(ArquivoPrescricao arquivoPrescricao);
	
}

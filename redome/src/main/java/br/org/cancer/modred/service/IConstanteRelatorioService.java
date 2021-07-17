package br.org.cancer.modred.service;

import java.util.List;

import br.org.cancer.modred.model.ConstanteRelatorio;
import br.org.cancer.modred.service.custom.IService;

/**
 * Interface para métodos de negócio das constantes para relatório.
 * 
 * @author bruno.sousa
 *
 */
public interface IConstanteRelatorioService extends IService<ConstanteRelatorio, String> {
	
	/**
	 * Método que retorna uma lista contendo somente os códigos das constantes.
	 * 
	 * @return Lista de códigos das constantes.
	 */
	List<String> listarCodigos();

	/**
	 * Método que obtem o valor da costante pelo código dela.
	 * 
	 * @param codigo - código da constante.
	 * @return valor da constante 
	 */
	String obterValorConstante(String codigo);

	/**
	 * @param codigo - Código da constante
	 * @param valor - Novo valor da constante
	 * @return 
	 */
	void atualizarValorConstante(String codigo, String valor);
	
}
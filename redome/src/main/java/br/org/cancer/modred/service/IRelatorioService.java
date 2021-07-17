package br.org.cancer.modred.service;

import java.io.File;
import java.util.List;

import br.org.cancer.modred.controller.dto.RelatorioDTO;
import br.org.cancer.modred.model.Relatorio;

/**
 * Interface de serviço para relatorios.
 * 
 * @author Queiroz
 *
 */
public interface IRelatorioService {

	/**
	 * Metodo para salvar em html um relatorio dinamico.
	 * 
	 * @param relatorio
	 * @return
	 */
	Relatorio salvarRelatorio(RelatorioDTO relatorio);

	/**
	 * Lista todos os relatorios.
	 * 
	 * @return
	 */
	List<Relatorio> listar();

	/**
	 * Obtém o arquivo em pdf dinamico pelo codigo.
	 * 
	 * @param codigo
	 * @param servletOutputStream
	 * @return
	 */
	File obterArquivo(String codigo);

	/**
	 * Relatório obtido por código.
	 * 
	 * @param codigo
	 * @return
	 */
	Relatorio obterRelatorioPorCodigo(String codigo);

	/**
	 * Listar parametros dos relatorios.
	 * 
	 * @return
	 */
	List<String> listarParametros();

	File gerarPdf(String codigo, String parametros);

}

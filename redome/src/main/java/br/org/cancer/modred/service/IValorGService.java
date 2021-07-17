package br.org.cancer.modred.service;

/**
 * Interface para metodos de negocio de ValorG.
 * 
 * @author bruno.sousa
 *
 */
public interface IValorGService {
	
	/**
	 * Método para obter grupo por Locus e nomeGrupo.
	 *
	 * @param locusCodigo
	 * @param grupo
	 * @return string subtipos
	 */
	String obterGrupo(String locusCodigo, String nomeGrupo);
}

package br.org.cancer.modred.service;

/**
 * Interface para metodos de negocio de ValorP.
 * 
 * @author bruno.sousa
 *
 */
public interface IValorPService {
	
	/**
	 * Método para obter grupo por Locus e nomeGrupo.
	 *
	 * @param locusCodigo
	 * @param grupo
	 * @return string subtipos
	 */
	String obterGrupo(String locusCodigo, String nomeGrupo);
}

package br.org.cancer.modred.service;

/**
 * Interface para metodos de negocio de ValorNmdp.
 * 
 * @author bruno.sousa
 *
 */
public interface IValorNmdpService {
	
	/**
	 * Método para obter subTipo por código.
	 * 
	 * @param codigo
	 * @return string subtipos
	 */
	String obterSubTipos(String codigo);
}

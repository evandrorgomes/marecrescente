package br.org.cancer.modred.util;

import java.util.List;

/**
 * Interface para ser utilizada na classe de serviço para validacao.
 * @author Filipe Paes
 *
 * @param <T> Bean a ser validado
 */
public interface IValidacao<T> {
	
	/**
	 * Método de validacao que retorna a lista de erro.
	 * @param bean
	 * @return List<CampoMensagem>
	 */
	List<CampoMensagem> validar(T bean);
}

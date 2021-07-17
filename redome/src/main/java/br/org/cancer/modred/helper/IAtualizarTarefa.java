/**
 * 
 */
package br.org.cancer.modred.helper;

import br.org.cancer.modred.feign.dto.TarefaDTO;
import br.org.cancer.modred.model.security.Usuario;

/**
 * Classe responsável por obter uma tarefa.
 * 
 * @author fillipe.queiroz
 *
 */
public abstract class IAtualizarTarefa extends IOperacaoTarefa{

	/**
	 * Obtém apenas 1 tarefa, lançando erro caso encontre outra, pois deveria retornar apenas 1.
	 * 
	 * @return TarefaDTO obtida.
	 */
	public abstract TarefaDTO apply();

	@Override
	public IAtualizarTarefa comProcessoId(Long idProcesso) {
		return (IAtualizarTarefa) super.comProcessoId(idProcesso);
	}
	
	@Override
	public IAtualizarTarefa comRmr(Long rmr) {
		return (IAtualizarTarefa) super.comRmr(rmr);
	}
	
	/**
	 * Seta o id do objeto relacionado.
	 * 
	 * @param objetoRelacionado
	 * @return ObterTarefa
	 */
	public abstract IAtualizarTarefa comObjetoRelacionado(Long objetoRelacionado);

	/**
	 * Seta o usuario a ser consultado.
	 * 
	 * @param Usuario usuario
	 * @return ObterTarefa
	 */
	public abstract IAtualizarTarefa comUsuario(Usuario usuario);

	/**
	 * Id da tarefa.
	 * 
	 * @param idTarefa
	 * @return
	 */
	public abstract IAtualizarTarefa comTarefa(Long idTarefa);

}

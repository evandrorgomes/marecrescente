/**
 * 
 */
package br.org.cancer.modred.helper;

import java.util.List;

import br.org.cancer.modred.feign.dto.TarefaDTO;
import br.org.cancer.modred.model.security.Usuario;

/**
 * Interface para atribuição de tarefa.
 * 
 * @author fillipe.queiroz
 *
 */
public abstract class IAtribuirTarefa extends IOperacaoTarefa{

	/**
	 * Executar a atribuição de tarefa.
	 */
	public abstract TarefaDTO apply();


	@Override
	public IAtribuirTarefa comProcessoId(Long idProcesso) {
		return (IAtribuirTarefa) super.comProcessoId(idProcesso);
	}
	
	@Override
	public IAtribuirTarefa comRmr(Long rmr) {
		return (IAtribuirTarefa) super.comRmr(rmr);
	}

	/**
	 * Seta o objeto relacionado.
	 * 
	 * @param objetoRelacionado - id do objetoRelacionado
	 * @return AtribuirTarefa
	 */
	public abstract IAtribuirTarefa comObjetoRelacionado(Long objetoRelacionado);

	/**
	 * Seta o usuario a ser atribuido.
	 * 
	 * @param objetoRelacionado - id do objetoRelacionado
	 * @return AtribuirTarefa
	 */
	public abstract IAtribuirTarefa comUsuario(Usuario usuario);

	/**
	 * Lista de parceiros que a tarefa possa fazer parte.
	 * 
	 * @param List<Long> parceiros
	 * @return
	 */
	public abstract IAtribuirTarefa comParceiros(List<Long> parceiros);

	/**
	 * TarefaDTO a ser atribuida a um usuario.
	 * 
	 * @param tarefa
	 * @return
	 */
	public abstract IAtribuirTarefa comTarefa(TarefaDTO tarefa);

	
	/**
	 * utiliza o usuario logado.
	 * 
	 * @return
	 */
	public abstract IAtribuirTarefa comUsuarioLogado();

}

/**
 * 
 */
package br.org.cancer.modred.helper;

import java.util.List;

import br.org.cancer.modred.feign.dto.TarefaDTO;

/**
 * Interface para remover atribuição de tarefa.
 * 
 * @author fillipe.queiroz
 *
 */
public abstract class IRemoverAtribuirTarefa extends IOperacaoTarefa {

	/**
	 * Executar a atribuição de tarefa.
	 */
	public abstract TarefaDTO apply();


	@Override
	public IRemoverAtribuirTarefa comProcessoId(Long idProcesso) {
		return (IRemoverAtribuirTarefa) super.comProcessoId(idProcesso);
	}
	
	@Override
	public IRemoverAtribuirTarefa comRmr(Long rmr) {
		return (IRemoverAtribuirTarefa) super.comRmr(rmr);
	}
	
	public abstract IRemoverAtribuirTarefa comTarefa(Long idTarefa);

	/**
	 * Seta o objeto relacionado.
	 * 
	 * @param objetoRelacionado - id do objetoRelacionado
	 * @return AtribuirTarefa
	 */
	public abstract IRemoverAtribuirTarefa comObjetoRelacionado(Long objetoRelacionado);


	/**
	 * Lista de parceiros que a tarefa possa fazer parte.
	 * 
	 * @param List<Long> parceiros
	 * @return
	 */
	public abstract IRemoverAtribuirTarefa comParceiros(List<Long> parceiros);

	/**
	 * TarefaDTO a ser atribuida a um usuario.
	 * 
	 * @param tarefa
	 * @return
	 */
	public abstract IRemoverAtribuirTarefa comTarefa(TarefaDTO tarefa);

	

}

/**
 * 
 */
package br.org.cancer.modred.helper;

import java.util.List;

import br.org.cancer.modred.feign.dto.TarefaDTO;
import br.org.cancer.modred.model.domain.StatusTarefa;
import br.org.cancer.modred.model.security.Usuario;

/**
 * Classe responsável por obter uma tarefa.
 * 
 * @author fillipe.queiroz
 *
 */
public interface IObterTarefa {

	/**
	 * Obtém apenas 1 tarefa, lançando erro caso encontre outra, pois deveria retornar apenas 1.
	 * 
	 * @return TarefaDTO obtida.
	 */
	public TarefaDTO apply();
	
	public IObterTarefa comTarefa(Long idTarefa);

	/**
	 * Seta o processo id para consulta, obrigatório.
	 * 
	 * @param processoId
	 * @return ObterTarefa
	 */
	//public IObterTarefa comProcessoId(Long idProcesso);
	
	public IObterTarefa comRmr(Long rmr);
	
	public IObterTarefa comIdDoador(Long idDoador);

	/**
	 * Seta o id do objeto relacionado.
	 * 
	 * @param objetoRelacionado
	 * @return ObterTarefa
	 */
	public IObterTarefa comObjetoRelacionado(Long objetoRelacionado);

	/**
	 * Seta a lista de status de tarefa a ser consultada.
	 * 
	 * @param List<StatusTarefa> status
	 * @return ObterTarefa
	 */
	public IObterTarefa comStatus(List<StatusTarefa> status);

	/**
	 * Seta a lista de parceiros a ser consultado.
	 * 
	 * @param List<Long> parceiros
	 * @return ObterTarefa
	 */
	public IObterTarefa comParceiros(List<Long> parceiros);

	/**
	 * Seta o usuario a ser consultado.
	 * 
	 * @param Usuario usuario
	 * @return ObterTarefa
	 */
	public IObterTarefa comUsuario(Usuario usuario);

	public IObterTarefa paraOutroUsuario(Boolean outroUsuario);
	
	public IObterTarefa comTarefaPai(Long tarefaPai);
	
	public IObterTarefa semAgendamento(Boolean semAgendamento);
		
	
}

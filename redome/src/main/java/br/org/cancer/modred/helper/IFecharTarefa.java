package br.org.cancer.modred.helper;

import java.util.List;

import br.org.cancer.modred.model.domain.StatusTarefa;
import br.org.cancer.modred.model.security.Usuario;

/**
 * Classe responsável por fechar tarefas.
 * 
 * @author fillipe.queiroz
 *
 */
public abstract class IFecharTarefa extends IOperacaoTarefa{

	protected boolean finalizarProcesso;
	
	public IFecharTarefa comProcessoId(Long idProcesso) {
		return (IFecharTarefa) super.comProcessoId(idProcesso);
	}
	
	public IFecharTarefa comRmr(Long rmr) {
		return (IFecharTarefa) super.comRmr(rmr);
	}

	/**
	 * Id do Objeto relacionado.
	 * 
	 * @param objetoRelacionado
	 * @return
	 */
	public abstract IFecharTarefa comObjetoRelacionado(Long objetoRelacionado);

	/**
	 * Lista de parceiros que a tarefa possa fazer parte.
	 * 
	 * @param List<Long> parceiros
	 * @return
	 */
	public abstract IFecharTarefa comParceiros(List<Long> parceiros);

	/**
	 * Usuario responsavel pela tarefa.
	 * 
	 * @param usuario
	 * @return
	 */
	public abstract IFecharTarefa comUsuario(Usuario usuario);
	
	/**
	 * Seta a tarefa a ser encerrada.
	 * @param idTarefa
	 * @return
	 */
	public abstract IFecharTarefa comTarefa(Long idTarefa);
	
	/**
	 * Seta a lista de status de tarefa a ser consultada.
	 * 
	 * @param List<StatusTarefa> status
	 * @return ObterTarefa
	 */
	public abstract IFecharTarefa comStatus(List<StatusTarefa> status);
	
	/**
	 * Informa que a tarefa, ao ser fechada, deve fechar o processo
	 * associado a ela.
	 * 
	 */
	public IFecharTarefa finalizarProcesso(){
		this.finalizarProcesso = Boolean.TRUE;
		return this;
	}
	
	public abstract IFecharTarefa comTarefaPai(Long tarefaPai);
	
	public abstract IFecharTarefa semAgendamento(Boolean semAgendamento);
	
}

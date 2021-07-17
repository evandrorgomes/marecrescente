package br.org.cancer.modred.helper;

import java.util.List;

import br.org.cancer.modred.model.domain.StatusTarefa;
import br.org.cancer.modred.model.security.Usuario;

/**
 * Classe responsável por cancelar tarefas.
 * 
 * @author bruno.sousa
 *
 */
public abstract class ICancelarTarefa extends IOperacaoTarefa {
	
	protected boolean canncelarProcesso;

	@Override
	public ICancelarTarefa comProcessoId(Long idProcesso) {
		return (ICancelarTarefa) super.comProcessoId(idProcesso);
	}
	
	@Override
	public ICancelarTarefa comRmr(Long rmr) {
		return (ICancelarTarefa) super.comRmr(rmr);
	}

	/**
	 * Id do Objeto relacionado.
	 * 
	 * @param objetoRelacionado
	 * @return CancelarTarefa
	 */
	public abstract ICancelarTarefa comObjetoRelacionado(Long objetoRelacionado);

	/**
	 * Lista de parceiros que a tarefa possa fazer parte.
	 * 
	 * @param List<Long> parceiros
	 * @return CancelarTarefa
	 */
	public abstract ICancelarTarefa comParceiros(List<Long> parceiros);

	/**
	 * Usuario responsavel pela tarefa.
	 * 
	 * @param usuario
	 * @return CancelarTarefa
	 */
	public abstract ICancelarTarefa comUsuario(Usuario usuario);
	
	/**
	 * Seta a tarefa a ser encerrada.
	 * @param idTarefa
	 * @return CancelarTarefa
	 */
	public abstract ICancelarTarefa comTarefa(Long idTarefa);
	
	/**
	 * Seta a lista de status de tarefa a ser consultada.
	 * 
	 * @param List<StatusTarefa> status
	 * @return CancelarTarefa
	 */
	public abstract ICancelarTarefa comStatus(List<StatusTarefa> status);
	
	/**
	 * Informa que a tarefa, ao ser fechada, deve fechar o processo
	 * associado a ela.
	 * 
	 */
	public ICancelarTarefa cancelarProcesso(){
		this.canncelarProcesso = Boolean.TRUE;
		return this;
	}
	
}

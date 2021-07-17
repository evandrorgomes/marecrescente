package br.org.cancer.modred.helper;

import java.time.LocalDateTime;

import br.org.cancer.modred.feign.dto.TarefaDTO;
import br.org.cancer.modred.model.domain.StatusTarefa;
import br.org.cancer.modred.model.security.Usuario;

/**
 * Classe responsável por fechar tarefas.
 * 
 * @author fillipe.queiroz
 *
 */
public abstract class ICriarTarefa extends IOperacaoTarefa{

	/**
	 * Executa o fechamento da tarefa.
	 */
	public abstract TarefaDTO apply();
	
	@Override
	public ICriarTarefa comProcessoId(Long idProcesso) {
		return (ICriarTarefa) super.comProcessoId(idProcesso);
	}
	
	@Override
	public ICriarTarefa comRmr(Long rmr) {
		return (ICriarTarefa) super.comRmr(rmr);
	}

	/**
	 * Id do Objeto relacionado.
	 * 
	 * @param objetoRelacionado
	 * @return
	 */
	public abstract ICriarTarefa comObjetoRelacionado(Long objetoRelacionado);

	/**
	 * Parceiro responsável que a tarefa possa fazer parte.
	 * 
	 * @param objetoParceiro
	 * @return
	 */
	public abstract ICriarTarefa comParceiro(Long objetoParceiro);

	/**
	 * Usuario responsavel pela tarefa.
	 * 
	 * @param usuario
	 * @return
	 */
	public abstract ICriarTarefa comUsuario(Usuario usuario);

	/**
	 * Seta a descrição da tarefa.
	 * 
	 * @param descricao
	 * @return
	 */
	public abstract ICriarTarefa comDescricao(String descricao);

	/**
	 * Data inicio da tarefa.
	 * 
	 * @param dataInicio
	 * @return
	 */
	public abstract ICriarTarefa comDataInicio(LocalDateTime dataInicio);

	/**
	 * Seta o status da nova tarefa.
	 * 
	 * @param statusTarefa
	 * @return
	 */
	public abstract ICriarTarefa comStatus(StatusTarefa statusTarefa);

	/**
	 * Seta o ultimo usuario responsavel pela tarefa.
	 * 
	 * @param ultimoUsuarioResponsavel
	 * @return
	 */
	public abstract ICriarTarefa comUltimoUsuarioResponsavel(Long ultimoUsuarioResponsavel);
	
	/**
	 * TarefaDTO pai.
	 * 
	 * @param tarefaPai
	 * @return 
	 */
	public abstract ICriarTarefa comTarefaPai(TarefaDTO tarefaPai);
	
	/**
	 * Data final da tarefa.
	 * 
	 * @param dataFinal
	 * @return
	 */
	public abstract ICriarTarefa comDataFinal(LocalDateTime dataFinal);
	
	
	/**
	 * Inclui no período. 
	 * 
	 * @return
	 */
	public abstract ICriarTarefa inclusive();
	
	/**
	 * Exclui do período.
	 * 
	 * @return
	 */
	public abstract ICriarTarefa exclusive();
	
	/**
	 * Hora Inicio da tarefa.
	 * 
	 * @param horaInicio
	 * @return
	 */
	public abstract ICriarTarefa comHoraInicio(LocalDateTime horaInicio);
	
	/**
	 * Hora fim da tarefa.
	 * 
	 * @param horafim
	 * @return
	 */
	public abstract ICriarTarefa comHoraFim(LocalDateTime horaFim);
	
	/**
	 * Marca a tarefa agendada.
	 * 
	 * @return
	 */
	public abstract ICriarTarefa agendada();
	
	/**
	 * Adiciona um usuário como responsável por realizar o agendamento de uma determinada tarefa.
	 * @param objeto do usuário responsável.
	 * @return 
	 */
	public abstract ICriarTarefa comOUsuarioParaAgendamento(Usuario usuario);
	

}

package br.org.cancer.redome.tarefa.service;

import java.util.List;

import org.springframework.data.domain.Page;

import br.org.cancer.redome.tarefa.dto.ListaTarefaDTO;
import br.org.cancer.redome.tarefa.model.Processo;
import br.org.cancer.redome.tarefa.model.Tarefa;

/**
 * Interface que descreve os métodos de negócio que devem ser implementados pelas classes de serviços que operam sobre as
 * entidades Processos e Tarefas. Essas entidades atuam em função do motor de processos da plataforma redome.
 * 
 * @author Thiago Moraes
 *
 */
public interface IProcessoService {

	/**
	 * Método para criar uma nova tarefa vinculada a um processo de negócio da plataforma redome.
	 * 
	 * A instância da classe tarefa precisa estar com os atributos TipoTarefa, Processo e Perfil preenchidos.
	 * 
	 * @param tarefa - a tarefa que será criada.
	 */
	Tarefa criarTarefa(Tarefa tarefa);

	/**
	 * Método para recuperar as informações sobre uma determinada tarefa a partir de sua chave de identificação.
	 * 
	 * @param id - chave de identificação da tarefa.
	 * 
	 * @return TarefaDTO - a instância da classe tarefa identificada pelo id informado como parâmetro do acionamento deste método.
	 */
	Tarefa obterTarefa(Long id);
	
	Tarefa fecharTarefa(Tarefa tarefa);

	/**
	 * Método para sinalizar ao motor de processos da plataforma o encerramento de uma data tarefa.
	 * 
	 * A instância da classe tarefa precisa estar com o atributo id preenchido.
	 * 
	 * @param tarefa - a tarefa que será encerrada.
	 */
	Tarefa fecharTarefa(Tarefa tarefa, Boolean finalizarProcesso);

	/**
	 * Método que lista todas as tarefas em aberto que devem ser processadas pelo motor de processos.
	 * 
	 * @return lista de tarefas em aberto ordenadas pela data de criação.
	 */
	List<Tarefa> listarTarefasAutomaticasEmAberto();
	
	/**
	 * Método para remover atribuição de um usuário responsável por executar a tarefa. 
	 * 
	 * A instância da classe TarefaDTO precisa estar com o atributo id preenchido. 
	 * 
	 * @param tarefa - a tarefa que será atribuída ao usuário.
	 */
	Tarefa removerAtribuicaoTarefa(Tarefa tarefa);
	
	Tarefa atribuirTarefa(Tarefa tarefa, Long idUsuario);
	
	/**
	 * Método que lista todas as tarefas que estão aguardando agendamento que devem ser processadas pelo motor de processos para 
	 * serem exibidas como notificação.
	 * 
	 * @return lista de tarefas em aguardando ordenadas pela data de agendamento.
	 */
	List<Tarefa> listarTarefasNotificacoesEmAguardandoAgendamento();
	
	
	/**
	 * Método para atualizar a tarefa.
	 * 
	 * @param tarefa
	 * @return tarefa
	 */
	Tarefa atualizarTarefa(Tarefa tarefa);

	
	/**
	 * Método que lista todas as tarefas filhas  de um determinado tipo pelo id da tarefa pai.
	 *  
	 * @param idTipoTarefa - Tipo de TarefaDTO
	 * @param idTarefaPai - id da tarefa relacionada
	 * @return lista de tarefas filhas 
	 */
	List<Tarefa> listarTarefasFilhas(Long idTipoTarefa, Long idTarefaPai);
	
	
	Tarefa cancelarTarefa(Tarefa tarefa);
	/**
	 * Método para sinalizar ao motor de processos da plataforma o cancelamento de uma data tarefa.
	 * 
	 * A instância da classe tarefa precisa estar com o atributo id preenchido.
	 * 
	 * @param tarefa - a tarefa que será cancelada.
	 */
	Tarefa cancelarTarefa(Tarefa tarefa, Boolean canncelarProcesso);

	Page<Tarefa> listarTarefas(ListaTarefaDTO parametros);

	Processo obterProcessoEmAndamento(Long rmr, Long idDoador, Long tipoProcesso);

	Processo terminarProcesso(Long id);

	Tarefa atribuirTarefa(Long id, Long idUsuario);

	Tarefa fecharTarefa(Long id, Boolean finalizarProcesso);

	List<Tarefa> listarTarefasFilhas(Long idTarefaPai, Long idTipoTarefa, Long idStatus);

	Tarefa atualizarTarefa(Long id, Tarefa tarefa);

	Tarefa cancelarTarefa(Long id, Boolean canncelarProcesso);

	Tarefa removerAtribuicaoTarefa(Long id);
	
	Processo criarProcesso(Processo processo);
	
	Processo cancelarProcesso(Processo processo);
	
	Processo cancelarProcesso(Long id);

	Tarefa atribuirTarefaUsuarioLogado(Long id);
	
	List<Tarefa> listarTarefasPorTipoTarefaIdEStatus(Long tipoTarefaId, Long statusId);

	
	
}
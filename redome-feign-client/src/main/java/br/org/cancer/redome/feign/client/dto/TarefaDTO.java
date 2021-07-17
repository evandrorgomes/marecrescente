package br.org.cancer.redome.feign.client.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import br.org.cancer.redome.feign.client.domain.StatusTarefa;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Classe base para as tarefas.
 * 
 * @author bruno.sousa
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Getter
public class TarefaDTO implements Serializable {

	/**
	 * Identificador de versão de serialização da classe.
	 */
	private static final long serialVersionUID = 1314559986072976863L;

	/**
	 * Chave que identifica com exclusividade uma instância desta classe.
	 */
	protected Long id;

	/**
	 * Processo para o qual esta tarefa está associada.
	 */
	protected ProcessoDTO processo;

	/**
	 * Data de criação da tarefa.
	 */
	@Default
	protected LocalDateTime dataCriacao = LocalDateTime.now();

	/**
	 * Data da última atualização desta tarefa.
	 */
	@Default
	protected LocalDateTime dataAtualizacao = LocalDateTime.now();

	/**
	 * Identificação do tipo de tarefa a ser executada no processo de negócio.
	 */
	protected TipoTarefaDTO tipoTarefa;

	/**
	 * Estado da tarefa dentro do processo de negócio.
	 */
	@Default
	protected Long status = StatusTarefa.ABERTA.getId();

	/**
	 * Perfil do responsável por executar esta tarefa.
	 */
	protected Long perfilResponsavel;

	/**
	 * Médico para o qual foi conferida esta tarefa, ou seja, trata-se do indivíduo responsável por executar a tarefa.
	 */
	protected Long usuarioResponsavel;

	/**
	 * Centro de transplante para o qual foi conferida esta tarefa, ou seja, trata-se da organização responsável por executar a
	 * tarefa.
	 */
	protected Long relacaoParceiro;
		
	protected Object objetoRelacaoParceiro;

	/**
	 * Descrição da tarefa.
	 */
	protected String descricao;

	/**
	 * Id da entidade relacionada a tarefa.
	 */
	protected Long relacaoEntidade;

	/**
	 * Atribuito para guardar objeto buscado através de relacao entidade.
	 */
	protected Object objetoRelacaoEntidade;

	/**
	 * Data início para execução da tarefa.
	 */
	protected LocalDateTime dataInicio;

	/**
	 * Data fim para execução da tarefa.
	 */
	protected LocalDateTime dataFim;

	/**
	 * Hora início para execução da tarefa.
	 */
	protected LocalDateTime horaInicio;

	/**
	 * Hora fim para execução da tarefa.
	 */
	protected LocalDateTime horaFim;

	/**
	 * Define se a fatia de horário é inclusiva ou exclusiva.
	 */
	protected Boolean inclusivoExclusivo;

	/**
	 * Indica se a tarefa foi agendada ou não.
	 */
	protected boolean agendado;

	/**
	 * Guarda o último usuário responsável pela tarefa associada. Atributo é utilizado para tarefa ROLLBACK, onde o último
	 * responsável volta a ser responsável pela tarefa, após o tempo se esgotar.
	 */
	protected Long ultimoUsuarioResponsavel;
	
	/**
	 * TarefaDTO pai.
	 */
	protected TarefaDTO tarefaPai;
	
	
	/**
	 * Usuário designado para realizar a tarefa em caso de um atendimento pré 
	 * estabelecido para alguém.
	 */
	protected Long usuarioResponsavelAgendamento;
	
	protected String aging;

}

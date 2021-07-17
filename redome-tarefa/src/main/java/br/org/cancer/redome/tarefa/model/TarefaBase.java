package br.org.cancer.redome.tarefa.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * Classe base para as tarefas.
 * 
 * @author bruno.sousa
 *
 */

@MappedSuperclass
@Data @AllArgsConstructor 
@NoArgsConstructor
@Getter @Setter
public class TarefaBase implements Serializable {

	/**
	 * Identificador de versão de serialização da classe.
	 */
	private static final long serialVersionUID = 1314559986072976863L;

	/**
	 * Chave que identifica com exclusividade uma instância desta classe.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_TARE_ID")
	@Column(name = "TARE_ID")
	@JsonProperty
	protected Long id;

	/**
	 * Processo para o qual esta tarefa está associada.
	 */
	@ManyToOne
	@JoinColumn(name = "PROC_ID")
	@NotNull
	protected Processo processo;

	/**
	 * Data de criação da tarefa.
	 */
	@Column(name = "TARE_DT_CRIACAO")
	@NotNull
	protected LocalDateTime dataCriacao;

	/**
	 * Data da última atualização desta tarefa.
	 */
	@Column(name = "TARE_DT_ATUALIZACAO")
	@NotNull
	protected LocalDateTime dataAtualizacao;

	/**
	 * Identificação do tipo de tarefa a ser executada no processo de negócio.
	 */
	@ManyToOne
	@JoinColumn(name = "TITA_ID")
	@NotNull
	protected TipoTarefa tipoTarefa;

	/**
	 * Estado da tarefa dentro do processo de negócio.
	 */
	@Column(name = "TARE_NR_STATUS")
	@NotNull
	protected Long status;

	/**
	 * Perfil do responsável por executar esta tarefa.
	 */
	@Column(name = "PERF_ID_RESPONSAVEL")
	protected Long perfilResponsavel;

	/**
	 * Médico para o qual foi conferida esta tarefa, ou seja, trata-se do indivíduo responsável por executar a tarefa.
	 */
	@Column(name = "USUA_ID_RESPONSAVEL")	
	protected Long usuarioResponsavel;

	/**
	 * Centro de transplante para o qual foi conferida esta tarefa, ou seja, trata-se da organização responsável por executar a
	 * tarefa.
	 */
	@Column(name = "TARE_NR_PARCEIRO")
	protected Long relacaoParceiro;
	
	/**
	 * Descrição da tarefa.
	 */
	
	@Column(name = "TARE_TX_DESCRICAO")
	protected String descricao;

	/**
	 * Id da entidade relacionada a tarefa.
	 */
	@Column(name = "TARE_NR_RELACAO_ENTIDADE")
	protected Long relacaoEntidade;

	/**
	 * Data início para execução da tarefa.
	 */
	@Column(name = "TARE_DT_INICIO")
	protected LocalDateTime dataInicio;

	/**
	 * Data fim para execução da tarefa.
	 */
	@Column(name = "TARE_DT_FIM")
	protected LocalDateTime dataFim;

	/**
	 * Hora início para execução da tarefa.
	 */
	@Column(name = "TARE_DT_HORA_INICIO")
	protected LocalDateTime horaInicio;

	/**
	 * Hora fim para execução da tarefa.
	 */
	@Column(name = "TARE_DT_HORA_FIM")
	protected LocalDateTime horaFim;

	/**
	 * Define se a fatia de horário é inclusiva ou exclusiva.
	 */
	@Column(name = "TARE_IN_INCLUSIVO_EXCLUSIVO")
	protected Boolean inclusivoExclusivo;

	/**
	 * Indica se a tarefa foi agendada ou não.
	 */
	@Column(name = "TARE_IN_AGENDADO", insertable = false, updatable = false)	
	protected Boolean agendado;

	/**
	 * Guarda o último usuário responsável pela tarefa associada. Atributo é utilizado para tarefa ROLLBACK, onde o último
	 * responsável volta a ser responsável pela tarefa, após o tempo se esgotar.
	 */
	@Column(name = "USUA_ID_ULTIMO_RESPONSAVEL")
	protected Long ultimoUsuarioResponsavel;
	
	@ManyToOne
	@JoinColumn(name = "TARE_ID_TAREFA_PAI")
	protected Tarefa tarefaPai;
	
	@Transient
	protected String aging;
	
	
	/**
	 * Usuário designado para realizar a tarefa em caso de um atendimento pré 
	 * estabelecido para alguém.
	 */
	@Column(name = "USUA_ID_AGENDAMENTO")	
	protected Long usuarioResponsavelAgendamento;
	
	

		
}

package br.org.cancer.redome.tarefa.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * Esta classe é uma abstração para representar processos dentro da plataforma redome . Tal processo é compreendido por tarefas
 * executadas de maneira sequencial ou em paralelo, e que representam ações lógicas e claras para gerar seus resultados de forma
 * independente.
 * 
 * Os processos possuem começo e fim determinados.
 * 
 * @author Thiago Moraes
 *
 */
@Entity
@SequenceGenerator(name = "SQ_PROC_ID", sequenceName = "SQ_PROC_ID", allocationSize = 1)
@Table(name = "PROCESSO")
@Data @NoArgsConstructor @Builder @AllArgsConstructor
public class Processo implements Serializable {

	/**
	 * Identificador de versão de serialização da classe.
	 */
	private static final long serialVersionUID = -583157896227858302L;

	/**
	 * Chave que identifica com exclusividade uma instância desta classe.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_PROC_ID")
	@Column(name = "PROC_ID")
	private Long id;

	/**
	 * Data de criação do processo.
	 */
	@Column(name = "PROC_DT_CRIACAO")
	@NotNull
	private LocalDateTime dataCriacao;

	/**
	 * Data da última atualização deste processo.
	 */
	@Column(name = "PROC_DT_ATUALIZACAO")
	@NotNull
	private LocalDateTime dataAtualizacao;

	/**
	 * Identificação do tipo de processo.
	 */
	@Column(name = "PROC_NR_TIPO")
	@NotNull
	private Long tipo;

	/**
	 * Paciente para o qual este processo de busca está associado.
	 */
	@Column(name = "PACI_NR_RMR")
	private Long rmr;
	
	@Column(name = "DOAD_ID")
	private Long idDoador;

	/**
	 * Estado atual do processo.
	 */
	@Column(name = "PROC_NR_STATUS")
	@NotNull
	private Long status;
	
	@Transient
	private String nomePaciente;

	@Transient
	private String nomeDoador;

}

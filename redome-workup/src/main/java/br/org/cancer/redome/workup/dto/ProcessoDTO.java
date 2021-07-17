package br.org.cancer.redome.workup.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import br.org.cancer.redome.workup.model.domain.StatusProcesso;
import br.org.cancer.redome.workup.model.domain.TipoProcesso;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

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
@Data
@Getter
public class ProcessoDTO implements Serializable {

	/**
	 * Identificador de versão de serialização da classe.
	 */
	private static final long serialVersionUID = -583157896227858302L;

	/**
	 * Chave que identifica com exclusividade uma instância desta classe.
	 */
	private Long id;

	/**
	 * Data de criação do processo.
	 */
	private LocalDateTime dataCriacao;

	/**
	 * Data da última atualização deste processo.
	 */
	private LocalDateTime dataAtualizacao;

	/**
	 * Identificação do tipo de processo.
	 */
	private Long tipo;

	private Long rmr;

	private String nomePaciente;
	
	private Long idDoador;
	
	private String nomeDoador;
		
	/**
	 * Estado atual do processo.
	 */
	private Long status;
	
	/**
	 * Construtor da classe.
	 * 
	 * Todo processo deve ter seu o seu tipo identificado. Para determinados processos (ex. processo de busca de doador), é
	 * mandatório a definição do paciente associado.
	 */
	@Builder
	private ProcessoDTO() {
		super();
		this.dataCriacao = LocalDateTime.now();
		this.dataAtualizacao = LocalDateTime.now();
		this.status = StatusProcesso.ANDAMENTO.getId();
	}

	/**
	 * Construtor da classe.
	 * 
	 * Todo processo deve ter seu o seu tipo identificado. Para determinados processos (ex. processo de busca de doador), é
	 * mandatório a definição do paciente associado.
	 */
	@Builder
	public ProcessoDTO(TipoProcesso tipo) {
		this();
		this.tipo = tipo.getId();
	}
	
	@Builder
	public ProcessoDTO(Long id, TipoProcesso tipo) {
		this(tipo);
		this.id = id;
	}

	@Builder
	public ProcessoDTO(TipoProcesso tipo, Long rmr) {
		this(tipo);
		this.rmr = rmr;
	}
	
	/**
	 * Método para mostrar a representação textual das instâncias desta classe.
	 */
	@Override
	public String toString() {
		StringBuffer output = new StringBuffer("processo id");
		output.append("=[").append(id).append("], tipo=[").append(tipo).append("], paciente=[").append(( rmr != null )
				? rmr : null).append("], status=[").append(status).append("], data criação=[").append(dataCriacao)
				.append("]").append("], ultima atualizacao=[").append(dataAtualizacao).append("]");
		return output.toString();
	}

}

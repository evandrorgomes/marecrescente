package br.org.cancer.redome.tarefa.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Value;

/**
 * 
 * Esta classe é uma abstração para representar uma ação, automática ou manual, a ser concluída em determinado prazo ao longo de
 * um processo de negócio do sistema redome.
 * 
 * @author Thiago Moraes
 *
 */
@Entity
@SequenceGenerator(name = "SQ_TARE_ID", sequenceName = "SQ_TARE_ID", allocationSize = 1)
@Table(name = "TAREFA")
@JsonDeserialize(builder = Tarefa.TarefaBuilder.class)
@Value
@NoArgsConstructor
@Getter
@Setter
public class Tarefa extends TarefaBase implements Serializable {

	/**
	 * Identificador de versão de serialização da classe.
	 */
	private static final long serialVersionUID = 1314559986072976863L;
	
	//@Builder(builderClassName = "TarefaBuilder", toBuilder = true)
	//public Tarefa() {
//		super();
	//}
	
	@Builder(builderClassName = "TarefaBuilder", toBuilder = true)
	public Tarefa(@JsonProperty Long id, @NotNull Processo processo, @NotNull LocalDateTime dataCriacao,
			@NotNull LocalDateTime dataAtualizacao, @NotNull TipoTarefa tipoTarefa, @NotNull Long status,
			Long perfilResponsavel, Long usuarioResponsavel, Long relacaoParceiro, String descricao,
			Long relacaoEntidade, LocalDateTime dataInicio, LocalDateTime dataFim, LocalDateTime horaInicio,
			LocalDateTime horaFim, Boolean inclusivoExclusivo, Boolean agendado, Long ultimoUsuarioResponsavel,
			Tarefa tarefaPai, String aging, Long usuarioResponsavelAgendamento) {
		
		super(id, processo, dataCriacao, dataAtualizacao, tipoTarefa, status, perfilResponsavel, usuarioResponsavel, relacaoParceiro, descricao,
				relacaoEntidade, dataInicio, dataFim, horaInicio, horaFim, inclusivoExclusivo, agendado, ultimoUsuarioResponsavel, tarefaPai, 
				aging, usuarioResponsavelAgendamento);

	}
	
	@JsonPOJOBuilder(withPrefix = "")
	public static class TarefaBuilder {
		
	}
		
	
}

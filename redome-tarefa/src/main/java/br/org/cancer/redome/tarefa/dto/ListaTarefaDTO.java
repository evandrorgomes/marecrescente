package br.org.cancer.redome.tarefa.dto;

import java.util.List;

import br.org.cancer.redome.tarefa.util.CustomPageRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ListaTarefaDTO {
	
	private List<Long> perfilResponsavel;
	private Long idUsuarioResponsavel; 
	private List<Long> parceiros;
	private List<Long> idsTiposTarefa; 
	private List<Long> statusTarefa; 
	private Long statusProcesso;
	private Long processoId;
	private Boolean inclusivoExclusivo; 
	private CustomPageRequest pageable;
	private Long relacaoEntidadeId; 
	private Long rmr;
	private Long idDoador;
	private Long tipoProcesso; 
	private Boolean atribuidoQualquerUsuario; 
	private Long tarefaPaiId;
	@Default
	private Boolean tarefaSemAgendamento = true; 
	private Long idUsuarioLogado;
		
}

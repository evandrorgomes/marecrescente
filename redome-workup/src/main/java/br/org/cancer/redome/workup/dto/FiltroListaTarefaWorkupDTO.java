package br.org.cancer.redome.workup.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder(toBuilder = true)
@AllArgsConstructor
@Getter
public class FiltroListaTarefaWorkupDTO {
	
	private Long idUsuarioResponsavel; 
	private Long idUsuario;
	private Long idCentroTransplante;
	private Long idCentroColeta;
	private List<Long> perfilResponsavel;
	private List<Long> statusTarefa;
		
}

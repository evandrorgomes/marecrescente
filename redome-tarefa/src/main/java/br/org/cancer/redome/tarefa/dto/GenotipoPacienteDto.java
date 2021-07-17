package br.org.cancer.redome.tarefa.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Agrupador de valores de genótipo. Esta classe é responsável por unir todos os resultados de exame para cada locus.
 * 
 * @author Filipe Paes
 * 
 */
@Data @NoArgsConstructor @Builder @AllArgsConstructor
public class GenotipoPacienteDto {


	private Long id;

	
	private LocalDateTime dataAlteracao;

	
	private List<ValorGenotipoPacienteDto> valores;

	
	private Boolean excluido;
	
}
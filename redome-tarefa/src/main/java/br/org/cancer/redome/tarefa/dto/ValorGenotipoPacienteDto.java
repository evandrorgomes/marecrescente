package br.org.cancer.redome.tarefa.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe de Dto para ValorGenotipoPaciente
 * @author Filipe Paes
 *
 */
@Data @NoArgsConstructor @Builder @AllArgsConstructor
public class ValorGenotipoPacienteDto{

	private ValorGenotipoPacientePKDto id;

	private Integer tipo;

	private String alelo;

	private Integer posicao;

	private Boolean divergente;

	private Long genotipo;	
}
	
package br.org.cancer.redome.tarefa.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe Dto para ValorGenotipoPacientePK
 * 
 * @author Filipe Paes
 *
 */
@Data @NoArgsConstructor @Builder @AllArgsConstructor
public class ValorGenotipoPacientePKDto{
	
	private String locus;

	private Long idExame;

	private Integer posicaoAlelo;
		
}
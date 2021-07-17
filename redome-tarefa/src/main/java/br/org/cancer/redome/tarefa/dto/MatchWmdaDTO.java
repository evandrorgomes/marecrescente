package br.org.cancer.redome.tarefa.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe de DTO para informações do match.
 * 
 * @author ergomes
 */
@Data @NoArgsConstructor @Builder @AllArgsConstructor
public class MatchWmdaDTO {

	private Long idBusca;
	private Long idDoador;
	private Long rmr;
	private String matchGrade; 
	private Integer ordenacaoWmdaMatch;
	private String probabilidade0;
	private String probabilidade1;
	private String probabilidade2;
	private boolean atualizarMatch;
	private Long tipoDoador;

	private List<QualificacaoMatchDTO> qualificacoes;

}

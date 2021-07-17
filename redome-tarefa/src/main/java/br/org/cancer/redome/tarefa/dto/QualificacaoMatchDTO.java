package br.org.cancer.redome.tarefa.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO de qualificação de match.
 * 
 * @author ergomes
 * 
 */
@Data 
@Builder
@NoArgsConstructor 
@AllArgsConstructor
public class QualificacaoMatchDTO implements Serializable {

	private static final long serialVersionUID = -7843670045964732048L;
	
	private String locus;
	private String qualificacao;
	private ProbabilidadeDTO probabilidade;
}

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
@Builder(toBuilder = true)
@NoArgsConstructor 
@AllArgsConstructor
public class ProbabilidadeDTO implements Serializable {

	private static final long serialVersionUID = -7843670045964732048L;
	
    public static final String LOCUS_A = "A";
    public static final String LOCUS_B = "B";
    public static final String LOCUS_C = "C";
    public static final String LOCUS_DRB1 = "DRB1";
    public static final String LOCUS_DQB1 = "DQB1";
	public static final String ZERO = "0";

    private String locus;
    private String probabilidade;
	
	public static ProbabilidadeDTO parseProbabilidade(String locus, ResultadoDoadorWmdaDTO doadorWmda) {
		
		ProbabilidadeDTO probabilidade = ProbabilidadeDTO.builder()
			.locus(locus)
			.build();

		switch (locus) {
		case LOCUS_A: probabilidade.setProbabilidade(doadorWmda.getPa());
			break;
		case LOCUS_B: probabilidade.setProbabilidade(doadorWmda.getPb());
			break;
		case LOCUS_C: probabilidade.setProbabilidade(doadorWmda.getPc());
			break;
		case LOCUS_DRB1: probabilidade.setProbabilidade(doadorWmda.getPdr());
			break;
		case LOCUS_DQB1: probabilidade.setProbabilidade(doadorWmda.getPdq());
			break;
		default:
			probabilidade.setProbabilidade(ZERO);
			break;
		}
		
		return probabilidade.getProbabilidade() != null ? probabilidade : probabilidade.toBuilder().probabilidade(ZERO).build();
	}

}

package br.org.cancer.redome.workup.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AvaliacaoResultadoWorkupDTO {
		
	private Long idPrescricao;
	private Boolean avaliacaoProsseguir;
	private String justificativaAvaliacaoResultadoWorkup;
	
	private ResultadoWorkupNacionalDTO resultadoWorkupDTO;

}

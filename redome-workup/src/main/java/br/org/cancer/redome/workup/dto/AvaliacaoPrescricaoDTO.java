package br.org.cancer.redome.workup.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AvaliacaoPrescricaoDTO {
	
	private Long idAvaliacaoPrescricao;

	private String justificativaDescarteFonteCelula;
	
	private Long idFonteCelulaDescartada;
	
	private PrescricaoEvolucaoDTO prescricaoEvolucaoDTO;
	
	@Builder
	public AvaliacaoPrescricaoDTO(Long idAvaliacaoPrescricao, PrescricaoEvolucaoDTO prescricaoEvolucaoDTO,
			String justificativaDescarteFonteCelula, Long idFonteCelulaDescartada) {
		
		this.idAvaliacaoPrescricao = idAvaliacaoPrescricao;
		this.prescricaoEvolucaoDTO = prescricaoEvolucaoDTO;
		this.justificativaDescarteFonteCelula = justificativaDescarteFonteCelula;
		this.idFonteCelulaDescartada = idFonteCelulaDescartada;
	}

}



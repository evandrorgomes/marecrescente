package br.org.cancer.redome.workup.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class PrescricaoEvolucaoDTO {

    @Getter
	private PrescricaoDTO prescricao;

    @Getter
	private EvolucaoDTO ultimaEvolucao;
	
}

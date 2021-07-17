package br.org.cancer.redome.workup.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@AllArgsConstructor
@Builder
public class CriarLogEvolucaoDTO {
	
	private Long rmr;
	private String[] parametros;
	private String tipo;
	private List<Long> perfisExcluidos;

}

package br.org.cancer.redome.workup.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class TransportadoraDTO {
	
	private Long id;
	
	private String nome;
	
	private Boolean ativo;

}

package br.org.cancer.redome.workup.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class MedicoDTO {
	
	@Getter
	private Long id;
	
	@Getter
	private String nome;

}

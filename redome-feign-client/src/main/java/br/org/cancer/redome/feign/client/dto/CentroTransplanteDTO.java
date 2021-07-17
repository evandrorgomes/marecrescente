package br.org.cancer.redome.feign.client.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class CentroTransplanteDTO {
	
	private Long id;
	
	private String nome;

}

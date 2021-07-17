package br.org.cancer.redome.feign.client.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class PacienteDTO {
	
	private Long rmr;
	
	private String nome;
	
	private LocalDate dataNascimento;

}

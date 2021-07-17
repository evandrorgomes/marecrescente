package br.org.cancer.redome.workup.dto;

import java.time.LocalDate;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class PrescricaoSemAutorizacaoPacienteDTO {
	
	private Long idPrescricao;
	private Long idTarefa;
	private Long idStatusTarefa;
	private Long rmr;
	private String nomePaciente;
	
	private LocalDate dataNascimento;
	
	private String nomeUsuarioResponsavelTarefa;
	
	private String agingTarefa;
	
	

}

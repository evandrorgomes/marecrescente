package br.org.cancer.redome.workup.dto;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AvaliacoesPrescricaoDTO {
		
	private Long idTarefa;
	private Long idStatusTarefa;
	private Long idAvaliacaoPrescricao;
	private Long rmr;
	private String nome;
	private String identificacaoDoador;
	private String tipoDoador;
	private LocalDate dataColeta;
	

}

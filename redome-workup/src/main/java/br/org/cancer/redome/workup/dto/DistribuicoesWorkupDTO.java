package br.org.cancer.redome.workup.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class DistribuicoesWorkupDTO {
	
	private Long idTarefaDistribuicaoWorkup;
	
	private Long statusTarefaDistribuicaoWorkup;
	
	private Long idDistribuicaoWorkup;
	
	private Long rmr;
	
	private String nomeCentroTransplante;
	
	private String medicoResponsavelPrescricao;
	
	private String tipoPrescricao;
	
	private String identificacaoDoador;
	
	private LocalDateTime dataPrescricao;
	
	private LocalDateTime dataAvaliacaoPrescricao;
	
	

}

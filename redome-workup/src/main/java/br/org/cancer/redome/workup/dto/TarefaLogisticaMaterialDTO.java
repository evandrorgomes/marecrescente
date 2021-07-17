package br.org.cancer.redome.workup.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TarefaLogisticaMaterialDTO {
	
	private Long idPedidoLogistica;
	
	private Long tipoTarefaLogisticaMaterial;
	
	private Integer tipoLogistica;
	
	private Long rmr;
	
	private String identificacaoDoador;
	
	private String nomeDoador;

	private Long idTipoDoador;
	
	private String nomeResponsavel;
	
	private LocalDate dataDistribuicao;
	
	private LocalDateTime dataColeta;
	
	private Boolean tipoAereo;
		
}

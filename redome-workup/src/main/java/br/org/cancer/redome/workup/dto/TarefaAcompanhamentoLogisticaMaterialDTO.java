package br.org.cancer.redome.workup.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder(toBuilder = true)
@Getter
@Setter
public class TarefaAcompanhamentoLogisticaMaterialDTO {

	private Long idPedidoLogistica;
	
	private Long rmr;

	private Long idPedidoTransporte;
	
	private LocalDateTime horaPrevistaRetirada;

	private String nomeTransportadora;
	
	private String identificacaoDoador;
	
	private String nomeDoador;

	private Long idTipoDoador;
	
	private String nomeResponsavel;

	private LocalDate dataRetirada;
	
	private Long status;
	
	private Long tipoTarefaLogisticaMaterial;
	
	private Integer tipoLogistica;
	
	private Integer tipoAereo;
	
}

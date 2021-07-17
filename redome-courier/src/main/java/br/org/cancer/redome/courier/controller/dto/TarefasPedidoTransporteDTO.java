package br.org.cancer.redome.courier.controller.dto;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class TarefasPedidoTransporteDTO {
	
	private Long idTarefa;
	private Long idStatusTarefa;
	private Long idPedidoTransporte;
	private String nomeLocalRetirada;
	private LocalDateTime horaPrevistaRetirada;
	private String identificacaoDoador;
	private Long rmr;
	private String nomeCentroTransplante;
	private String descricaoStatusPedidoTransporte;
	
	

}

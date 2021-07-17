package br.org.cancer.redome.workup.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Classe de DTO para dados especificos da tela de pedido de transporte.
 * 
 * @author Filipe Paes
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Getter
@Setter
public class DetalheLogisticaMaterialAereoDTO {

	private Long idPedidoTransporte;
	private Long idTransportadora;
	private CourierDTO courier;
	private String dadosVoo;
	private LocalDateTime horaPrevistaRetirada;

	
//	private String retiradaHawb;
//	private LocalDate dataEmbarque;
//	private LocalDate dataChegada;
}

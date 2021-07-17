package br.org.cancer.redome.workup.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe de DTO para dados especificos da tela de pedido de logistica de material.
 * 
 * @author ergomes
 *
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DetalheLogisticaInternacionalColetaDTO {

	private Long rmr;	
	private Long idPedidoLogistica;
	private Long idTipoDoador;
	private Long idCentroTransplante;
	private String nomeCentroTransplante;
	private String enderecoCentroTransplante;
	private List<String> contatosCentroTransplante;
	private LocalDate dataColeta; 
	
	private String retiradaLocal;
	private String retiradaIdDoador;
	private String retiradaHawb;
	private String nomeCourier;
	private String passaporteCourier;
	private LocalDate dataEmbarque;
	private LocalDate dataChegada;

	
}

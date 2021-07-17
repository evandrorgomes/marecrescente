package br.org.cancer.redome.courier.controller.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Getter
@Setter
public class PedidoTransporteDTO implements Serializable {
	
	private static final long serialVersionUID = 8546902860396018456L;

	private Long id;
	private Long idPedidoLogistica;
	private Long idSoliciacao;
	private LocalDateTime horaPrevistaRetirada;
	private Long usuarioResponsavel;
	private TransportadoraDTO transportadora;
	private StatusPedidoTransporteDTO status;
	private CourierDTO courier;
	private String dadosVoo;
	private Long idFonteCelula;
	
}

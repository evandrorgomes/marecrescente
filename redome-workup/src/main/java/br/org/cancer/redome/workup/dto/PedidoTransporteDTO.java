package br.org.cancer.redome.workup.dto;

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
	
	private static final long serialVersionUID = 7131173211022465218L;

	private Long id;
	private TransportadoraDTO transportadora;
	private LocalDateTime horaPrevistaRetirada;
	private Long idPedidoLogistica;
	private StatusPedidoTransporteDTO status;
	private Long usuarioResponsavel;
	private CourierDTO courier;
	private String dadosVoo;
	private Long idSolicitacao;
	private Long idFonteCelula;


}

package br.org.cancer.redome.workup.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class StatusPedidoTransporteDTO {
	
	private Long id;
	
	private String descricao;

}

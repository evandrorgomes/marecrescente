package br.org.cancer.redome.workup.feign.client;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import br.org.cancer.redome.workup.dto.PedidoTransporteDTO;

public interface IPedidoTransporteFeign {
			
	@PostMapping("api/pedidostransporte")	
	public PedidoTransporteDTO criarPedidoTransporte(@RequestBody(required=true) PedidoTransporteDTO pedidoTransporteDTO);
	
	@GetMapping("api/pedidostransporte/detalhestransporte")
	public PedidoTransporteDTO obterPedidoTransportePorIdLogiticaEStatus(
			@RequestParam(name="idPedidoLogistica", required=true) Long idPedidoLogistica,
			@RequestParam(name="idStatusTransporte", required=true) Long idStatusTransporte);

}

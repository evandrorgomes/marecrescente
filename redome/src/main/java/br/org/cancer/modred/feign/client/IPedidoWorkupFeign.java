package br.org.cancer.modred.feign.client;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import br.org.cancer.modred.feign.dto.PedidoWorkupDTO;

public interface IPedidoWorkupFeign {
	
	@GetMapping("/api/pedidoworkup/{idPedido}")
	public PedidoWorkupDTO obterPedidoWorkup(@PathVariable(name = "idPedido", required = true) Long idPedido);
	
	@PostMapping("/api/pedidoworkup/{idPedido}/finalizarformularioposcoleta")
	public ResponseEntity<String> finalizarFormularioPosColeta(@PathVariable(name = "idPedido", required = true) Long idPedido);
}
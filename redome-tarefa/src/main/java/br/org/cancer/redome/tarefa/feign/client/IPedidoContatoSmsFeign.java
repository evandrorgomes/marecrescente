package br.org.cancer.redome.tarefa.feign.client;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

/**
 * Interface de acesso aos métodos de pedido de exame do redome. 
 * @author Flipe Paes
 *
 */
public interface IPedidoContatoSmsFeign {
	
	@SuppressWarnings("rawtypes")
	@PutMapping("/api/pedidoscontatosms/{id}/atualizarsmsenviados")
	ResponseEntity atualizaStatusSmsEnviado(@PathVariable(name="id", required=true) Long idPedidoContatoSms);
	
	@SuppressWarnings("rawtypes")
	@PostMapping(value = "{id}/finalizar", consumes = "application/json")
	public ResponseEntity finalizarPedidoContatoSms(@PathVariable(name="id", required=true) Long id);

}

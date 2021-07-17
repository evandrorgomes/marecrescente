package br.org.cancer.redome.tarefa.feign.client;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Interface de acesso aos métodos de pedido de exame do redome. 
 * @author Flipe Paes
 *
 */
public interface ITentativaContatoDoadorFeign {
	
	
	@GetMapping("/api/tentativascontato/{id}/podefinalizar")
	ResponseEntity<Boolean> podeFinalizarTentativaContato(@PathVariable(name = "id", required = true) Long id);
	
	@SuppressWarnings("rawtypes")
	@PostMapping(value = "/api/tentativascontato/{id}/finalizar")
	ResponseEntity finalizarTentativaContato(@PathVariable(name = "id", required = true) Long id);

}

package br.org.cancer.redome.tarefa.feign.client;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Interface de acesso aos métodos de pedido de exame do redome. 
 * @author Flipe Paes
 *
 */
public interface IPedidoExameFeign {
	
	
	@PostMapping("/api/pedidosexame/criarchecklistexamesemresultado30dias")
	ResponseEntity<String> criarCheckListExameSemResultadoMais30Dias(@RequestParam(name = "tarefa", required = true) Long idTarefa);

}

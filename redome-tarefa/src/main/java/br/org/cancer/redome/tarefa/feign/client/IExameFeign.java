package br.org.cancer.redome.tarefa.feign.client;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Interface de acesso aos métodos de exame do redome. 
 * @author Flipe Paes
 *
 */
public interface IExameFeign {

	@PostMapping("/api/exames/notificarresultadoexameinternacional")
	ResponseEntity<String> notificarUsuariosSobreResultadoDeExameDeDoadorInternacional(@RequestParam(name = "tarefa") Long idTarefa);
	
	@PostMapping("/api/exames/notificarctinternacional15dias")
	ResponseEntity<String> notificacarCadastroResultadoExameCtInternacional15(@RequestParam(name = "tarefa") Long idTarefa);

	@PostMapping("/api/exames/notificarctinternacional7dias")
	ResponseEntity<String> notificacarCadastroResultadoExameCtInternacional7(@RequestParam(name = "tarefa") Long idTarefa);
	
	@PostMapping("/api/exames/notificaridminternacional15dias")
	public ResponseEntity<String> notificacarCadastroResultadoExameIdmInternacional15(@RequestParam(name = "tarefa") Long idTarefa);
	
	@PostMapping("/api/exames/notificaridminternacional7dias")
	public ResponseEntity<String> notificacarCadastroResultadoExameIdmInternacional7(@RequestParam(name = "tarefa") Long idTarefa);
}

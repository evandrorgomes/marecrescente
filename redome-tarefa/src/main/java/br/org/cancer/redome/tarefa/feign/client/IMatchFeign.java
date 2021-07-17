package br.org.cancer.redome.tarefa.feign.client;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * Interface de acesso aos métodos de match do redome. 
 * @author Flipe Paes
 *
 */
public interface IMatchFeign {
	
	@PostMapping(value = "api/matchs/criarchecklistdivergenciamatch")
	public ResponseEntity<String> criarCheckListsPorDivergenciasMatch(@RequestParam("tarefa") Long idTarefa);
	
	@PostMapping(value = "api/matchs/executarprocedurematchdoador")
	public ResponseEntity<String> executarProcedureMatchDoador(@RequestParam("tarefa") Long idTarefa);

	@PostMapping(value = "api/matchs/executarprocedurematchpaciente")
	public ResponseEntity<String> executarProcedureMatchPaciente(@RequestParam("tarefa") Long idTarefa);

}

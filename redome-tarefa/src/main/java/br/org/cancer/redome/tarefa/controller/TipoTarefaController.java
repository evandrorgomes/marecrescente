package br.org.cancer.redome.tarefa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.org.cancer.redome.tarefa.model.TipoTarefa;
import br.org.cancer.redome.tarefa.service.ITipoTarefaService;

@RestController
@RequestMapping(produces = "application/json;charset=UTF-8")
public class TipoTarefaController {
		
	@Autowired
	private ITipoTarefaService tipoTarefaService;
		
	@GetMapping("/api/tipotarefa/{id}")
	public ResponseEntity<TipoTarefa> obterTipoTarefa(@PathVariable(name="id", required=true) Long id) {
		return ResponseEntity.ok(tipoTarefaService.obterTipoTarefa(id));
	}


}

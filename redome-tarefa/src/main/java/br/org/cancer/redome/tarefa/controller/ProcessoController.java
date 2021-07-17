package br.org.cancer.redome.tarefa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.org.cancer.redome.tarefa.model.Processo;
import br.org.cancer.redome.tarefa.service.IProcessoService;

@RestController
@RequestMapping(produces = "application/json;charset=UTF-8")
public class ProcessoController {
		
	@Autowired
	private IProcessoService processoService;
	
	@GetMapping("/api/processos/emandamento")
	public ResponseEntity<Processo> obterProcessoemAndamento(
			@RequestParam(required = false, name= "rmr") Long rmr,
			@RequestParam(required = false, name= "idDoador") Long idDoador,
			@RequestParam(required = true, name= "tipo") Long tipoProcesso) {
						
		return ResponseEntity.ok(processoService.obterProcessoEmAndamento(rmr, idDoador, tipoProcesso));		
	}
	
	@PostMapping("/api/processo/{id}/terminar")
	public ResponseEntity<Processo> terminarProcesso(@PathVariable(required = true, name="id") Long id) {
		return ResponseEntity.ok(processoService.terminarProcesso(id));
	}
	
	@PostMapping("/api/processos")
	public ResponseEntity<Processo> criarProcesso(@RequestBody(required = true) Processo processo) {
		return ResponseEntity.ok(processoService.criarProcesso(processo));
	}

	@PostMapping("/api/processo/{id}/cancelar")
	public ResponseEntity<Processo> cancelarProcesso(@PathVariable(required = true, name="id") Long id) {
		return ResponseEntity.ok(processoService.cancelarProcesso(id));
	}

}

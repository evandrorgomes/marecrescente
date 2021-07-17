package br.org.cancer.redome.tarefa.controller;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.org.cancer.redome.tarefa.dto.ListaTarefaDTO;
import br.org.cancer.redome.tarefa.model.Tarefa;
import br.org.cancer.redome.tarefa.model.domain.StatusTarefa;
import br.org.cancer.redome.tarefa.service.IProcessoService;

@RestController
@RequestMapping(produces = "application/json;charset=UTF-8")
public class TarefaController {
	

	@Autowired
	private ObjectMapper mapper;
	
	@Autowired
	private IProcessoService processoService;
	
	@GetMapping("/api/tarefas")
	public ResponseEntity<Page<Tarefa>> listarTarefas(@RequestParam(required = true, name= "filter") String filter) throws JsonParseException, JsonMappingException, IOException {
		if (StringUtils.isEmpty(filter)) {
			return ResponseEntity.badRequest().build();
		}
		
		ListaTarefaDTO parametros = mapper.readValue(Base64Utils.decodeFromString(filter), ListaTarefaDTO.class);
						
		return ResponseEntity.ok(processoService.listarTarefas(parametros));		
	}
	
	@GetMapping("/api/tarefa/{id}")
	public ResponseEntity<Tarefa> obterTarefa(@PathVariable(name="id", required=true) Long id) {
		return ResponseEntity.ok(processoService.obterTarefa(id));
	}

	
	@PostMapping("/api/tarefa/{id}/atribuir")
	public ResponseEntity<Tarefa> atribuirTarefaUsuario(@PathVariable(name="id", required=true) Long id, @RequestBody(required=true) Long idUsuario) {
		
		return ResponseEntity.ok(processoService.atribuirTarefa(id, idUsuario));
	}
	
	@PostMapping("/api/tarefa/{id}/feita")
	public ResponseEntity<Tarefa> encerrarTarefa(@PathVariable(name="id", required=true) Long id, @RequestBody(required = false) Boolean finalizarProcesso) {		
		return ResponseEntity.ok(processoService.fecharTarefa(id, finalizarProcesso == null ? false : finalizarProcesso));
	}
	
	@GetMapping("/api/tarefa/{id}/tarefas")
	public ResponseEntity<List<Tarefa>> listarTarefasFilhasEmAbertoPorTipo(@PathVariable(name="id", required=true) Long id, @RequestParam(required = false, name= "idtipotarefa") Long idTipoTarefa) {
		return ResponseEntity.ok(processoService.listarTarefasFilhas(id,  idTipoTarefa, StatusTarefa.ABERTA.getId()));
	}
	
	@PostMapping("/api/tarefa/{id}/cancelar")
	public ResponseEntity<Tarefa> cancelarTarefa(@PathVariable(name="id", required=true) Long id, @RequestBody(required = false) Boolean canncelarProcesso) {
		return ResponseEntity.ok(processoService.cancelarTarefa(id, canncelarProcesso));
	}
	
	@PutMapping("/api/tarefa/{id}")
	public ResponseEntity<Tarefa> atualizarTarefa(@PathVariable(name="id", required=true) Long id, @RequestBody(required=true) Tarefa tarefa) {
		return ResponseEntity.ok(processoService.atualizarTarefa(id, tarefa));
	}
	
	@PostMapping("/api/tarefas")
	public ResponseEntity<Tarefa> criarTarefa(@RequestBody(required = true) Tarefa tarefa) {
		return ResponseEntity.ok(processoService.criarTarefa(tarefa));
	}
	
	@PostMapping("/api/tarefa/{id}/remveratribuicao")
	public ResponseEntity<Tarefa> removerAtribuicaoTarefa(@PathVariable(name="id", required=true) Long id) {
		return ResponseEntity.ok(processoService.removerAtribuicaoTarefa(id));
	}
	
	@PostMapping("/api/tarefa/{id}/atribuirusuariologado")
	public ResponseEntity<Tarefa> atribuirTarefaUsuarioLogado(@PathVariable(name="id", required=true) Long id) {
		
		return ResponseEntity.ok(processoService.atribuirTarefaUsuarioLogado(id));
	}

}

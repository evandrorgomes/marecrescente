package br.org.cancer.redome.feign.client;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import br.org.cancer.redome.feign.client.dto.TarefaDTO;
import br.org.cancer.redome.feign.client.util.CustomPageImpl;

public interface ITarefaFeign {
	
	@GetMapping("/api/tarefas")
	public CustomPageImpl<TarefaDTO> listarTarefas(@RequestParam(required = true, name= "filter") String filter);

	@PostMapping("/api/tarefa/{id}/atribuir")
	public TarefaDTO atribuirTarefaUsuario(@PathVariable(name="id", required=true) Long id, @RequestBody(required=true) Long idUsuario);

	@GetMapping("/api/tarefa/{id}")
	public TarefaDTO obterTarefa(@PathVariable(name="id", required=true) Long id);
	
	@PostMapping("/api/tarefa/{id}/feita")
	public TarefaDTO encerrarTarefa(@PathVariable(name="id", required=true) Long id, @RequestBody(required = false) Boolean finalizarProcesso);

	@GetMapping("/api/tarefa/{id}/tarefas")
	public List<TarefaDTO> listarTarefasFilhasEmAbertoPorTipo(@PathVariable(name="id", required=true) Long id, @RequestParam(required = false, name= "idtipotarefa") Long idTipoTarefa);

	@PostMapping("/api/tarefa/{id}/cancelar")
	public TarefaDTO cancelarTarefa(@PathVariable(name="id", required=true) Long id, @RequestBody(required = false) Boolean canncelarProcesso);

	@PutMapping("/api/tarefa/{id}")
	public TarefaDTO atualizarTarefa(@PathVariable(name="id", required=true) Long id, @RequestBody(required=true) TarefaDTO tarefa);

	@PostMapping("/api/tarefas")
	public TarefaDTO criarTarefa(@RequestBody(required = true) TarefaDTO tarefa);

	@PostMapping("/api/tarefa/{id}/remveratribuicao")
	public TarefaDTO removerAtribuicaoTarefa(@PathVariable(name="id", required=true) Long id);


}

package br.org.cancer.redome.tarefa.feign.client;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import br.org.cancer.redome.tarefa.dto.PesquisaWmdaDoadorDTO;

public interface IPesquisaWmdaDoadorFeign {
	
	@PostMapping("/api/pesquisaswmdadoador") 
	public PesquisaWmdaDoadorDTO manterRotinaPesquisaWmdaDoador(@RequestBody(required=true) PesquisaWmdaDoadorDTO pesquisaWmdaDoadorDto);

	@GetMapping("/api/pesquisaswmdadoador/{pesquisaWmdaId}") 
	public List<String> obterListaDeIdentificacaoDoadorWmdaExistente(@PathVariable(name="pesquisaWmdaId", required=true) Long pesquisaWmdaId);
}
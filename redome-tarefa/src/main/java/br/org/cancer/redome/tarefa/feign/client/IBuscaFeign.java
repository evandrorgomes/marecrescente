package br.org.cancer.redome.tarefa.feign.client;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import br.org.cancer.redome.tarefa.dto.BuscaDto;

public interface IBuscaFeign {
	
	@PutMapping(value = "/api/buscas/{rmr}/alterarstatusparaliberado")
	ResponseEntity<String> alterarStatusParaLiberado(@PathVariable(value = "rmr") Long rmr);
	
	@PutMapping(value = "/api/buscas/{rmr}/removeratribuicaobusca")
	ResponseEntity<String> removerAtribuicaoDeBusca(@PathVariable(value = "rmr") Long rmr);
	
	@GetMapping(value = "/api/buscas/{rmr}/obtersimplificado")
	BuscaDto obterBuscaDto(@PathVariable(value = "rmr") Long rmr);

	@GetMapping(value = "/api/buscas/{id}")
	BuscaDto obterBuscaDtoPorId(@PathVariable(value = "id") Long id);

}

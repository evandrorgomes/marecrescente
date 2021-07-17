package br.org.cancer.modred.feign.client;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.org.cancer.modred.feign.dto.TipoTarefaDTO;

public interface ITipoTarefaFeign {
	
	@GetMapping("/api/tipotarefa/{id}")
	public TipoTarefaDTO obterTipoTarefa(@PathVariable(name = "id", required = true) Long id);

}

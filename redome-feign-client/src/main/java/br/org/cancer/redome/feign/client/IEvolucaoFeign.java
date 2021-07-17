package br.org.cancer.redome.feign.client;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.org.cancer.redome.feign.client.dto.EvolucaoDTO;

public interface IEvolucaoFeign {
		
	@GetMapping("api/evolucoes/ultima/{rmr}")
	public EvolucaoDTO obterUltimaEvolucaoDoPaciente(@PathVariable(name = "rmr", required = true) Long rmr);

	@GetMapping("api/evolucoes/{id}")
	public EvolucaoDTO obterEvolucaoPorId(@PathVariable(name = "id", required = true) Long id);

}

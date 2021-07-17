package br.org.cancer.redome.workup.feign.client;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import br.org.cancer.redome.workup.dto.CriarLogEvolucaoDTO;


public interface ILogEvolucaoFeign {
	
	@SuppressWarnings("rawtypes")
	@PostMapping("/api/logsevolucao")
	public ResponseEntity criarLogEvolucao(@RequestBody(required = true) CriarLogEvolucaoDTO criarLogEvolucaoDTO);

}

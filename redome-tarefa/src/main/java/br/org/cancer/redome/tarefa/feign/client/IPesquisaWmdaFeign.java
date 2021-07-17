package br.org.cancer.redome.tarefa.feign.client;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import br.org.cancer.redome.tarefa.dto.MatchWmdaDTO;
import br.org.cancer.redome.tarefa.dto.PesquisaWmdaDTO;

public interface IPesquisaWmdaFeign {
	
	@PostMapping("/api/pesquisaswmda")
	public PesquisaWmdaDTO criarPesquisaWmda(@RequestBody(required=true) PesquisaWmdaDTO pesquisaWmdaDTO);
	
	@PostMapping("/api/pesquisaswmda/mantermatch")
	public ResponseEntity<String> manterRotinaMatchWmda(
			@RequestBody(required=true) MatchWmdaDTO matchWmdaDto);

	@PostMapping("/api/pesquisaswmda/{id}")
	public ResponseEntity<String> atualizarRotinaPesquisaWmda(
			@PathVariable(name="id", required=true) Long id, 
			@RequestBody(required=true) PesquisaWmdaDTO pesquisaWmdaDTO);
	
	@GetMapping("/api/pesquisaswmda/{id}")
	public PesquisaWmdaDTO obterPesquisaWmda(@PathVariable(name="id", required=true) Long id);

	@GetMapping("/api/pesquisaswmda/status")
	public PesquisaWmdaDTO obterPesquisaWmdaPorBuscaIdEStatusAbertoEProcessadoErro(
			@RequestParam(name="buscaId", required=true) Long buscaId);
}
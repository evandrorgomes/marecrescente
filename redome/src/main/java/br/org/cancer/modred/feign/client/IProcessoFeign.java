package br.org.cancer.modred.feign.client;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import br.org.cancer.modred.feign.dto.ProcessoDTO;

public interface IProcessoFeign {
	
	@GetMapping("/api/processos/emandamento")
	public ProcessoDTO obterProcessoemAndamento(
			@RequestParam(required = false, name= "rmr") Long rmr,
			@RequestParam(required = false, name= "idDoador") Long idDoador,
			@RequestParam(required = true, name= "tipo") Long tipoProcesso);

	@PostMapping("/api/processo/{id}/terminar")
	public ProcessoDTO terminarProcesso(@PathVariable(required = true, name="id") Long id);

	@PostMapping("/api/processos")
	public ProcessoDTO criarProcesso(@RequestBody(required = true) ProcessoDTO processo);

	@PostMapping("/api/processo/{id}/cancelar")
	public ProcessoDTO cancelarProcesso(@PathVariable(required = true, name="id") Long id);

}



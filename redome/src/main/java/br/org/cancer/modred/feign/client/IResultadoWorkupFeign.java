package br.org.cancer.modred.feign.client;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import br.org.cancer.modred.controller.dto.ResultadoWorkupNacionalDTO;

public interface IResultadoWorkupFeign {
	
	@PutMapping(value = "/api/resultadosworkup/nacional")
	public ResponseEntity<String> criarResultadoWorkupNacional(
			@RequestParam(name="idPedidoWorkup", required = true) Long idPedidoWorkup,
			@RequestBody(required = true) ResultadoWorkupNacionalDTO resultadoWorkupNacionalDTO);
}
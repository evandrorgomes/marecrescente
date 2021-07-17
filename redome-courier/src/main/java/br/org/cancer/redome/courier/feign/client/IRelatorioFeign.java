package br.org.cancer.redome.courier.feign.client;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import feign.Response;

public interface IRelatorioFeign {
	
	@GetMapping(value = "/api/relatorios/{codigo}/pdf")
	public Response baixarArquivo(
			@PathVariable(name = "codigo", required = true) String codigo,
			@RequestParam(name = "parametros", required = false) String parametros);
	
	@GetMapping(value = "/api/relatorios/{codigo}/pdf")
	public Response baixarArquivo(
			@PathVariable(name = "codigo", required = true) String codigo);

}

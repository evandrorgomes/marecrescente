package br.org.cancer.redome.workup.feign.client;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.org.cancer.redome.workup.dto.EnderecoDTO;

public interface ICentroTransplanteFeign {
		
	@GetMapping(value= "/api/centrosTransplante/{id}/enderecoentrega")
	public EnderecoDTO obterEnderecoDeEntrega(@PathVariable(name = "id", required = true) Long id);
	
	
	@GetMapping(value= "/api/centrotransplante/{id}/enderecoretirada")
	public EnderecoDTO obterEnderecoDeRetirada(@PathVariable(name = "id", required = true) Long id);

}

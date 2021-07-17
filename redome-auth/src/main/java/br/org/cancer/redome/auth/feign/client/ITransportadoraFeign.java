package br.org.cancer.redome.auth.feign.client;

import javax.websocket.server.PathParam;

import org.springframework.web.bind.annotation.GetMapping;

import br.org.cancer.redome.auth.controller.dto.TransportadoraDTO;

public interface ITransportadoraFeign {
	
	@GetMapping("/api/trasportadora/{id}")
	public TransportadoraDTO obterTransportadora(@PathParam("id") Long id);

}

package br.org.cancer.redome.feign.client;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import br.org.cancer.redome.feign.client.dto.TransportadoraUsuarioDTO;
import br.org.cancer.redome.feign.client.dto.UsuarioDTO;

public interface IUsuarioFeign {
	
	@GetMapping("api/usuario/{id}")
	public UsuarioDTO obterUsuarios(@PathVariable(required = true) Long id);
	
	@PutMapping("/usuarios/transportadora")
	public String atribuirTransportadora(@RequestBody(required = true) TransportadoraUsuarioDTO transportadoraUsuario);


}

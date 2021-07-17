package br.org.cancer.redome.workup.feign.client;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.org.cancer.redome.workup.dto.UsuarioDTO;

public interface IUsuarioFeign {
	
	@GetMapping("api/usuario/{id}")
	public UsuarioDTO obterUsuarios(@PathVariable(required = true) Long id); 

}

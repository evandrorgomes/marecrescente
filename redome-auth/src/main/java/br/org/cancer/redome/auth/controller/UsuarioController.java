package br.org.cancer.redome.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.org.cancer.redome.auth.controller.dto.TransportadoraUsuarioDTO;
import br.org.cancer.redome.auth.controller.dto.UsuarioDTO;
import br.org.cancer.redome.auth.service.IUsuarioService;

@RestController
@RequestMapping(value = "/api", produces = "application/json;charset=UTF-8")
public class UsuarioController {

	@Autowired
	private IUsuarioService usuarioService;
	
	@GetMapping("/usuario/{id}")
	public ResponseEntity<UsuarioDTO> obterUsuarios(@PathVariable(required = true) Long id) {
		return ResponseEntity.ok(usuarioService.obterUsuarioDTO(id));
	}
	
	@PutMapping("/usuarios/transportadora")	
	public ResponseEntity<String> atribuirTransportadora(@RequestBody(required = true) TransportadoraUsuarioDTO transportadoraUsuario) {
		usuarioService.atribuirUsuariosParaTransportadora(transportadoraUsuario.getIdTransportadora(), transportadoraUsuario.getUsuarios());
		
		return ResponseEntity.ok().build();
	}
	

}

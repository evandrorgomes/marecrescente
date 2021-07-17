package br.org.cancer.redome.auth.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.org.cancer.redome.auth.controller.dto.UsuarioDTO;
import br.org.cancer.redome.auth.service.IPerfilService;

@RestController
@RequestMapping(value = "/api", produces = "application/json;charset=UTF-8")
public class PerfilController {
	
	@Autowired
	private IPerfilService perfilService;

	@GetMapping("/perfil/{id}/usuarios")
	public ResponseEntity<List<UsuarioDTO>> listarUsuarios(@PathVariable(required = true) Long id,
			@RequestParam(name = "parceiro", required = false) String parceiro,
			@RequestParam(name = "identificador", required = false) Long idParceiro) {
		return ResponseEntity.ok(perfilService.listarUsuariosPorPerfil(id, parceiro, idParceiro));
	}
	
	
}

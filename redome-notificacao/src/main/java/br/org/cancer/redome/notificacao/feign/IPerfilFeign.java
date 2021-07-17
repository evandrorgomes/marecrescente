package br.org.cancer.redome.notificacao.feign;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import br.org.cancer.redome.notificacao.dto.UsuarioDTO;

public interface IPerfilFeign {

	@GetMapping("/api/perfil/{id}/usuarios")
	List<UsuarioDTO> listarUsuarios(@PathVariable(required = true) Long id,
			@RequestParam(name = "parceiro", required = false) String parceiro,
			@RequestParam(name = "identificador", required = false) Long idParceiro);
	
}
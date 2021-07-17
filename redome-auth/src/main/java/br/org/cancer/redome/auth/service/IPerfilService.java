package br.org.cancer.redome.auth.service;

import java.util.List;

import br.org.cancer.redome.auth.controller.dto.UsuarioDTO;

public interface IPerfilService {
	
	List<UsuarioDTO> listarUsuariosPorPerfil(Long id, String parceiro, Long idParceiro);

}

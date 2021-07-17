package br.org.cancer.redome.auth.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.redome.auth.controller.dto.UsuarioDTO;
import br.org.cancer.redome.auth.model.CentroTransplante;
import br.org.cancer.redome.auth.model.Laboratorio;
import br.org.cancer.redome.auth.model.Perfil;
import br.org.cancer.redome.auth.model.Usuario;
import br.org.cancer.redome.auth.persistence.IPerfilRepository;
import br.org.cancer.redome.auth.service.IPerfilService;

@Service
@Transactional
public class PerfilService implements IPerfilService {
	
	@Autowired
	private IPerfilRepository perfilRepository;
	
	@Override
	public List<UsuarioDTO> listarUsuariosPorPerfil(Long id, String parceiro, Long idParceiro) {
		Perfil perfil = obterPerfil(id);
		if (parceiro != null && idParceiro != null) {
			return perfil.getUsuarios().stream()
					.filter(usuario -> usuario.isAtivo())
					.filter(usuario -> usuarioPertenceParceiro(usuario, parceiro, idParceiro) )					
					.map(usuario -> new UsuarioDTO(usuario.getId(), usuario.getNome()))
					.collect(Collectors.toList());
		}
		return perfil.getUsuarios().stream()
				.filter(usuario -> usuario.isAtivo()) 
				.map(usuario -> new UsuarioDTO(usuario.getId(), usuario.getNome()))
				.collect(Collectors.toList());
	}
	
	private Perfil obterPerfil(Long id) {
		if (id == null) {
			throw new RuntimeException();
		}
		return perfilRepository.findById(id).orElseThrow(() -> new RuntimeException());
	}
	
	private Boolean usuarioPertenceParceiro(Usuario usuario, String parceiro, Long idParceiro) {
		return usuarioPertenceCentroTransplante(usuario, parceiro, idParceiro) || 
				usuarioPertenceTransportadora(usuario, parceiro, idParceiro) || 
				usuarioPertenceLaboratorio(usuario, parceiro, idParceiro);
	}
	
	
	private Boolean usuarioPertenceCentroTransplante(Usuario usuario, String parceiro, Long idParceiro) {
		return parceiro.equals(CentroTransplante.class.getSimpleName()) && 
				usuario.getCentroTransplanteUsuarios() != null && 
				usuario.getCentroTransplanteUsuarios().stream()
					.anyMatch(centro -> centro.getCentroTransplante().getId().equals(idParceiro));
	}
	
	private Boolean usuarioPertenceTransportadora(Usuario usuario, String parceiro, Long idParceiro) {
		return parceiro.equals("Transportadora") && 
				usuario.getTransportadora() != null &&
				usuario.getTransportadora().equals(idParceiro);
	}
	
	private Boolean usuarioPertenceLaboratorio(Usuario usuario, String parceiro, Long idParceiro) {
		return parceiro.equals(Laboratorio.class.getSimpleName()) && 
				usuario.getLaboratorio() != null &&
				usuario.getLaboratorio().getId().equals(idParceiro);
	}

}

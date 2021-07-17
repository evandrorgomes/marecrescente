package br.org.cancer.modred.test.util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import br.org.cancer.modred.model.CentroTransplante;
import br.org.cancer.modred.model.CentroTransplanteUsuario;
import br.org.cancer.modred.model.security.Perfil;
import br.org.cancer.modred.model.security.Usuario;

public class UsuarioAnswer implements Answer<Usuario> {
	
	private Long idUsuario;
	private List<Perfil> perfis;
	private List<CentroTransplante> centrosTransplantes;
	
	public UsuarioAnswer(Long idUsuario, List<Perfil> perfis, List<CentroTransplante> centrosTransplantes) {
		super();
		this.idUsuario = idUsuario;
		this.perfis = perfis;
		this.centrosTransplantes = centrosTransplantes;
	}


	@Override
	public Usuario answer(InvocationOnMock invocation) throws Throwable {
		Usuario usuario = new Usuario(idUsuario);
		usuario.setPerfis(new ArrayList<>());
		if (perfis != null) {
			usuario.getPerfis().addAll(perfis);
		}
		
		if (centrosTransplantes != null) {
			usuario.setCentroTransplanteUsuarios(new ArrayList<>());
			usuario.getCentroTransplanteUsuarios().addAll(
					centrosTransplantes.stream()
						.map(centroTransplante -> {
								CentroTransplanteUsuario centroTransplanteUsuario = new CentroTransplanteUsuario();
								centroTransplanteUsuario.setCentroTransplante(centroTransplante);
								return centroTransplanteUsuario;
						})
						.collect(Collectors.toList())
			);
		}
		
		return usuario;
	}
	
	

}

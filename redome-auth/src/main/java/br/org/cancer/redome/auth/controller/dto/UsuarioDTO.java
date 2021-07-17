package br.org.cancer.redome.auth.controller.dto;

import br.org.cancer.redome.auth.model.Usuario;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class UsuarioDTO {
	
	private Long id;
	private String nome;

	
	public UsuarioDTO(Long id, String nome) {
		this.id = id;
		this.nome = nome;
	}
	
	public static class UsuarioDTOBuilder {
		
		private Long id;
		private String nome;
		private Usuario usuario;
		
		private UsuarioDTOBuilder() {
			
		}
		
		public UsuarioDTOBuilder id(Long id) {
	        this.id = id;
	        verificarIdNameOuUsuario();
	        return this;
	    }
		
		public UsuarioDTOBuilder nome(String nome) {
	        this.nome = nome;
	        verificarIdNameOuUsuario();
	        return this;
	    }
		
		public UsuarioDTOBuilder usuario(Usuario usuario) {
	        this.usuario = usuario;
	        verificarIdNameOuUsuario();
	        this.id = usuario.getId();
	        this.nome = usuario.getNome();
	        return this;
	    }
		
		private void verificarIdNameOuUsuario() {
	        if ((id != null || nome != null) && usuario != null) {
	            throw new IllegalStateException("Nao é possível atribuir id/Nome e usuário ao mesmo tempo.");
	        }
	    }
		
		
		
		
	}
		
		

}

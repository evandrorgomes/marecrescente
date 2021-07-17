package br.org.cancer.redome.workup.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UsuarioDTO {

	
	private Long id;
	private String nome;
	private String username;
	
	@Builder
	private UsuarioDTO(Long id, String nome, String username) {
		super();
		this.id = id;
		this.nome = nome;
	}
	
	

}

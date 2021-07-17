package br.org.cancer.redome.notificacao.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class UsuarioDTO {
	
	private Long id;
	private String nome;

}

package br.org.cancer.redome.feign.client.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@SuperBuilder
@Getter	
public class SolicitacaoWorkupDTO extends SolicitacaoDTO {
	
	private static final long serialVersionUID = 775720477049280969L;
	
	private UsuarioDTO usuarioResponsavel;
	
	private Long faseWorkup;
	
	private Long idCentroTransplante;
	
	private Long idCentroColeta;
	

}

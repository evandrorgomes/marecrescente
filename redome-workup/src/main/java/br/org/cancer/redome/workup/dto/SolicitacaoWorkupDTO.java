package br.org.cancer.redome.workup.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@Getter	
public class SolicitacaoWorkupDTO extends SolicitacaoDTO {
	
	private static final long serialVersionUID = 775720477049280969L;
	
	private UsuarioDTO usuarioResponsavel;
	
	private Long faseWorkup;
	
	private CentroTransplanteDTO centroTransplante;
	
	private CentroTransplanteDTO centroColeta;
	
	
	public Long getIdCentroTransplante() {
		if (centroTransplante != null) {
			return centroTransplante.getId();
		}
		return null;
	}
	
	public Long getIdCentroColeta() {
		if (centroColeta != null) {
			return centroColeta.getId();
		}
		return null;
	}

	

}

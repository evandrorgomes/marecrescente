package br.org.cancer.redome.workup.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@JsonInclude(Include.NON_NULL)
public class DistribuicaoWorkupPorUsuarioDTO {
	
	@JsonIgnore
	private String nomeUsuario;
	
	
	private DistribuicoesWorkupDTO distribuicoesWorkup;

}

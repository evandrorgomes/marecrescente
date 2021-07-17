package br.org.cancer.redome.feign.client.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class MatchDTO implements Serializable {

	private static final long serialVersionUID = -6623133539555737926L;
	
	private Long id;
	
	private BuscaDTO busca;
	
	private DoadorDTO doador;
	
	
	
	

}

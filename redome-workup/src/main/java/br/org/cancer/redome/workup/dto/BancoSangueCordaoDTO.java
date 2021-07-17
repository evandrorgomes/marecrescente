package br.org.cancer.redome.workup.dto;

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
public class BancoSangueCordaoDTO implements Serializable {

	private static final long serialVersionUID = -6882500594980032230L;

	private Long id;
	
	private String nome;
	
	private String endereco;
	
	private String contato;
	

}

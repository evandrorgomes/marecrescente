package br.org.cancer.redome.feign.client.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RegistroDTO implements Serializable {
	
	private static final long serialVersionUID = -5205464363200538525L;

	private Long id;

	private String nome;
	
	private String sigla;
	
}	
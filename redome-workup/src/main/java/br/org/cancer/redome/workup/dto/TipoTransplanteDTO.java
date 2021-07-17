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
public class TipoTransplanteDTO implements Serializable {

	private static final long serialVersionUID = 6664103020699382272L;
	
	private Long id;
	
	private String descricao;
}

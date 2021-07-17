package br.org.cancer.redome.feign.client.dto;

import java.io.Serializable;
import java.math.BigDecimal;

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
public class DoadorDTO implements Serializable {
	
	private static final long serialVersionUID = -5780783145337679781L;

	private Long id;
	
	private String Identificacao;
	
	private Long idTipoDoador;

	private String tipoDoador;
	
	private String nome;
	
	private String sexo;
	
	private String abo;
	
	private BigDecimal peso;
	
	private RegistroDTO registroOrigem;
	
}

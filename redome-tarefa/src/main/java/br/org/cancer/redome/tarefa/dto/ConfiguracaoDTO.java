package br.org.cancer.redome.tarefa.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@NoArgsConstructor 
@AllArgsConstructor 
@Builder
public class ConfiguracaoDTO {
	
	private String chave;

	private String valor;

}

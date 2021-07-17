package br.org.cancer.redome.tarefa.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO que representa o Registro do doador internacional.
 * 
 * @author ergomes
 *
 */

@Data 
@Builder
@NoArgsConstructor 
@AllArgsConstructor
public class RegistroDTO implements Serializable {
	
	private static final long serialVersionUID = -3944032954490915732L;

	private Long id;
	private String nome;
	private String sigla;
	
}

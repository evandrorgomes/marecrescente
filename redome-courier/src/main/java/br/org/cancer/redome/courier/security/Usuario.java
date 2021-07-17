package br.org.cancer.redome.courier.security;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Classe para representar as características e comportamentos do usuário.
 * 
 * @author bruno.sousa
 *
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(includeFieldNames = true)
public class Usuario implements Serializable {

	private static final long serialVersionUID = 3939069671930132719L;
	
	private Long id;
	
	private String nome;
	
	private String username;
	
	private List<String> authorities;
	
	private Long idTransportadora;


}
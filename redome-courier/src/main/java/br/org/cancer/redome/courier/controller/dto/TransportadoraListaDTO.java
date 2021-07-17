package br.org.cancer.redome.courier.controller.dto;

import br.org.cancer.redome.courier.model.Transportadora;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class TransportadoraListaDTO {
	
	private Long id;	
	private String nome;
	
		
	public TransportadoraListaDTO(Transportadora transportadora) {
		this(transportadora.getId(), transportadora.getNome());
	}
		
		
		
	

}

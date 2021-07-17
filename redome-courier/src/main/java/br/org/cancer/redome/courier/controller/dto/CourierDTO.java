package br.org.cancer.redome.courier.controller.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class CourierDTO implements Serializable {

	private static final long serialVersionUID = -5043631144351317307L;

	private Long id;	
	private String nome;
    private String cpf;
    private String rg;

}

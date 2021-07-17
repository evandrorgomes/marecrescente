package br.org.cancer.redome.workup.dto;

import java.io.Serializable;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PaisDTO implements Serializable {
	
	private static final long serialVersionUID = 4292435273944478083L;
	
	private Long id;    
    private String nome;

}

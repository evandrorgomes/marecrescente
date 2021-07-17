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
public class UfDTO implements Serializable{
	
	private static final long serialVersionUID = -5903943374320847005L;
	
	private String sigla;
    private String nome;

}

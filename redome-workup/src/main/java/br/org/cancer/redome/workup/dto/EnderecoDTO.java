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
public class EnderecoDTO implements Serializable {
	
	private static final long serialVersionUID = -6401827949902338898L;
	
	protected String cep;
	protected PaisDTO pais;
	protected String numero;
	protected String bairro;
	protected String complemento;
	protected String nomeLogradouro;
	protected MunicipioDTO municipio;
	protected String tipoLogradouro;
	protected boolean excluido;
	protected boolean principal;
	protected boolean correspondencia;

}

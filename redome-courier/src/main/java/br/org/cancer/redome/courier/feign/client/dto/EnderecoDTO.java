package br.org.cancer.redome.courier.feign.client.dto;

import java.io.Serializable;

import br.org.cancer.redome.courier.model.Municipio;
import br.org.cancer.redome.courier.model.Pais;
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
	protected Pais pais;
	protected String numero;
	protected String bairro;
	protected String complemento;
	protected String nomeLogradouro;
	protected Municipio municipio;
	protected String tipoLogradouro;
	protected boolean excluido;
	protected boolean principal;
	protected boolean correspondencia;

}

package br.org.cancer.redome.workup.dto;

import java.io.Serializable;

import br.org.cancer.redome.workup.model.ArquivoResultadoWorkup;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
public class ArquivoResultadoWorkupDTO implements Serializable {

	private static final long serialVersionUID = -5048366769781693666L;

	private Long id;
	
	private String caminho;
	
	private String descricao;
	
	public ArquivoResultadoWorkupDTO(ArquivoResultadoWorkup arquivoResultadoWorkup) {
		this.id = arquivoResultadoWorkup.getId();
		this.caminho = arquivoResultadoWorkup.getCaminho();
		this.descricao = arquivoResultadoWorkup.getDescricao();
	}

}
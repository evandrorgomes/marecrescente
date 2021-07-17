package br.org.cancer.modred.controller.dto;

import java.util.List;

import br.org.cancer.modred.model.AtributoOrdenacao;

/**
 * Classe referente aos atributos de ordenação de atributos DTO.
 * 
 * @author Fillipe Queiroz
 *
 */
public class AtributosOrdenacaoDTO {

	private List<AtributoOrdenacao> atributos;

	/**
	 * @return the atributos
	 */
	public List<AtributoOrdenacao> getAtributos() {
		return atributos;
	}

	/**
	 * @param atributos the atributos to set
	 */
	public void setAtributos(List<AtributoOrdenacao> atributos) {
		this.atributos = atributos;
	}

}

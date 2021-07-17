package br.org.cancer.modred.controller.dto.doador;

import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * Classe de persistencia de EnderecoContatoHemoEntidade.
 * 
 * @author ergomes
 * 
 */
@JsonTypeName("enderecoContatoHemoEntidadeDTO")
public class EnderecoContatoHemoEntidadeDTO extends EnderecoContatoDTO {
	
	private static final long serialVersionUID = -4467900674830890726L;

	private HemoEntidadeDTO hemoEntidade;
	
	public EnderecoContatoHemoEntidadeDTO() {
		super();
	}
	
	public EnderecoContatoHemoEntidadeDTO(EnderecoContatoDTO enderecoContato) {
		super(enderecoContato);
	}

	/**
	 * @return the hemoEntidade
	 */
	public HemoEntidadeDTO getHemoEntidade() {
		return hemoEntidade;
	}

	/**
	 * @param hemoEntidade
	 *            the hemoEntidade to set
	 */
	public void setHemoEntidade(HemoEntidadeDTO hemoEntidade) {
		this.hemoEntidade = hemoEntidade;
	}

}
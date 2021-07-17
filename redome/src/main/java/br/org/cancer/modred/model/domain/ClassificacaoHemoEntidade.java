package br.org.cancer.modred.model.domain;

/**
 * Enum para representar as classificações da entidade HemoEntidade no sistema Redome.
 * 
 * @author Pizão.
 *
 */
public enum ClassificacaoHemoEntidade {

	HC("Hemocentro"),
	HN("Hemonucleo");
	
	private String descricao;

	ClassificacaoHemoEntidade() {}
	
	ClassificacaoHemoEntidade(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

}
package br.org.cancer.modred.model.domain;
/**
 * Enum para tipos de pedido.
 * @author Filipe Paes
 *
 */
public enum TiposPedido {
	PEDIDO_FASE_2(1),
	PEDIDO_FASE_3(2),
	WORKUP(3),
	ENRIQUECIMENTO(4);
	
	Integer id;

	TiposPedido(Integer id) {
		this.id = id;
	}
	
	public Integer getId() {
		return id;
	}
}

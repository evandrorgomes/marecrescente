package br.org.cancer.modred.model.domain;
/**
 * Enum para tipos de pedido de logística.
 * 
 * @author Pizão
 */
public enum TiposPedidoLogistica {
	DOADOR(1L),
	MATERIAL(2L),
	MATERIAL_COM_AEREO(3L);
	
	Long id;

	TiposPedidoLogistica(Long id) {
		this.id = id;
	}
	
	public Long getId() {
		return id;
	}
}

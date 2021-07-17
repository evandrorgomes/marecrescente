package br.org.cancer.modred.model.domain;

/**
 * Tipos de alteração para pedidos de logística.
 * @author Filipe Paes 
 *
 */
public enum TipoAlteracaoPedidoLogistica {
	DATA(1L)
	,ARQUIVO_CNT(2L)
	, AMBOS(3L);
	
	private Long id;
	
	private TipoAlteracaoPedidoLogistica(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}
}

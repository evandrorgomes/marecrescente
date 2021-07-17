package br.org.cancer.modred.model.domain;

import br.org.cancer.modred.model.interfaces.EnumType;

/**
 * Enum para definir aceite ou não aceite de Mismatch.
 * @author Filipe Souza
 *
 */
public enum AcaoPedidoContato implements EnumType<Long>{
    NAO_PROSSEGUIR(0L),
    PROSSEGUIR(1L),
    ANALISE_MEDICA(2L);

    private Long id;
    
    AcaoPedidoContato(Long id) {
        this.id = id;
    }
    
	@Override
	public Long getId() {
		return id;
	}
	
	/**
	 * Método para criar o tipo enum a partir de seu valor numerico.
	 * 
	 * @param value - valor do id do tipo enum
	 * 
	 * @return FiltroMatch - a enum correspondente ou nulo caso value estaja fora do range conhecido.
	 */
	public static AcaoPedidoContato valueById(Long value) {

		AcaoPedidoContato[] values = AcaoPedidoContato.values();
		for (int i = 0; i < values.length; i++) {
			if (values[i].getId().equals(value)) {
				return values[i];
			}
		}
		return null;
	}
	
}

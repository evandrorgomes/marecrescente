package br.org.cancer.modred.model.domain;

import br.org.cancer.modred.model.interfaces.EnumType;

/**
 * Enum que indica o tipo do voucher.
 * 1 - Hospedagem
 * 2 - Transporte Aéreo
 * 
 * @author Pizão.
 *
 */
public enum TiposVoucher implements EnumType<Integer>{
    HOSPEDAGEM(1),
    TRANSPORTE_AEREO(2);

    private Integer codigo;

    TiposVoucher(Integer codigo) {
        this.codigo = codigo;
    }

    /**
     * @return the codigo
     */
    public Integer getCodigo() {
        return codigo;
    }
    
    public boolean equals(Integer codigo){
        return this.codigo.equals(codigo);
    }

	@Override
	public Integer getId() {
		return codigo;
	}
}

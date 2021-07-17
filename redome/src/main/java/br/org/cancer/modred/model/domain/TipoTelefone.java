package br.org.cancer.modred.model.domain;

import br.org.cancer.modred.model.interfaces.EnumType;

/**
 * Enum para tipos de telefone.
 * 
 * @author Filipe Paes
 *
 */
public enum TipoTelefone implements EnumType<Integer>{
    FIXO(1),
    CELULAR(2);

    private Integer codigo;

    TipoTelefone(Integer codigo) {
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

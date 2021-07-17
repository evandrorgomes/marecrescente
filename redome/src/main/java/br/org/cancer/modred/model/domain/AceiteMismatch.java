package br.org.cancer.modred.model.domain;

import br.org.cancer.modred.model.interfaces.EnumType;

/**
 * Enum para definir aceite ou não aceite de Mismatch.
 * @author Filipe Souza
 *
 */
public enum AceiteMismatch implements EnumType<Integer>{
    NAO_ACEITA(0),
    ACEITA(1);

    private Integer codigo;
    
    AceiteMismatch(Integer codigo) {
        this.codigo = codigo;
    }
    
    /**
     * @return the codigo
     */
    public Integer getCodigo() {
        return codigo;
    }

	@Override
	public Integer getId() {
		return codigo;
	}
}

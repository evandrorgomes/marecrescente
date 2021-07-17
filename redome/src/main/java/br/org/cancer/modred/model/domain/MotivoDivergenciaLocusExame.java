package br.org.cancer.modred.model.domain;

import br.org.cancer.modred.model.interfaces.EnumType;

/**
 * Enum para definir aceite ou não aceite de Mismatch.
 * @author Filipe Souza
 *
 */
public enum MotivoDivergenciaLocusExame implements EnumType<Integer>{
    DISCREPANTE(0),
    EMPATE(1);

    private Integer codigo;
    
    MotivoDivergenciaLocusExame(Integer codigo) {
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

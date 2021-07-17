package br.org.cancer.redome.courier.model.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enum para tipos de telefone.
 * 
 * @author Filipe Paes
 *
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum TipoTelefone {
    FIXO(1),
    CELULAR(2);

    private Integer codigo;
    
    public boolean equals(Integer codigo){
        return this.codigo.equals(codigo);
    }

}

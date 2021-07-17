package br.org.cancer.redome.courier.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Classe de persistencia de uf para a tabela UF.
 * 
 * @author Filipe Paes
 * 
 */
@Entity
@Table(name = "UF")
@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@ToString(onlyExplicitlyIncluded = true)
public class Uf implements Serializable {

    private static final long serialVersionUID = -5934379585715305879L;

    /**
     * Chave que identifica com exclusividade uma instância desta classe e sigla.
     * da UF.
     */
    @Id
    @Column(name = "UF_SIGLA")
    @ToString.Include
    private String sigla;
    /**
     * Nome da UF.
     */
    @Column(name = "UF_TX_NOME")
    private String nome;

    @Builder
    private Uf(String sigla, String nome) {
        this.sigla = sigla;
        this.nome = nome;
    }

    
}
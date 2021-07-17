package br.org.cancer.redome.courier.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe de persistência do modelo Pais para a tabela país.
 * 
 * @author Filipe Paes
 * 
 */
@Entity
@Table(name = "PAIS")
@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Pais implements Serializable {

    private static final long serialVersionUID = 4853364171386190731L;

    /**
     * Constate com o Id do Bradil na base de dados.
     */
    public static final Long BRASIL = 1L;
    
    /**
     * Chave que identifica com exclusividade uma instância desta classe.
     */
    @Id
    @Column(name = "PAIS_ID")
    private Long id;
    
    /**
     * Nome descritivo do país.
     */
    @Column(name = "PAIS_TX_NOME")
    private String nome;
    
    
    /**
     * Código do País no REDOMEWEB.
     */
    @Column(name = "PAIS_TX_CD_NACIONAL_REDOMEWEB")    
    private String codigoPaisRedomeweb;    
    
    /**
     * Construtor com todos os campos da classe.
     */
    @Builder
    private Pais(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }
    
    /**
     * Construtor com todos os campos da classe.
     */
    @Builder
    private Pais(Long id, String nome, String codigoPaisRedomeweb) {
    	this(id, nome);
        this.codigoPaisRedomeweb = codigoPaisRedomeweb;
    }
    
    @JsonIgnore
    public boolean isBrasil() {
        return Pais.BRASIL.equals(this.getId());
    }


}

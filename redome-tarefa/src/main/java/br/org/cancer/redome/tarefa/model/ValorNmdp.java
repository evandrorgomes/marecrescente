package br.org.cancer.redome.tarefa.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe que representa os valores do nmdp.
 * 
 * @author bruno.sousa
 *
 */
@Entity
@Table(name = "VALOR_NMDP")
@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class ValorNmdp implements Serializable {
    
	private static final long serialVersionUID = -3792566514457235118L;

	@Id
    @Column(name = "VANM_ID_CODIGO")
    private String id;

    @Column(name = "VANM_TX_SUBTIPO")
    @NotNull
    @Lob
    private String subtipo;

    @Column(name = "VANM_IN_AGRUPADO")
    @NotNull
    private Long agrupado;
    
    @Column(name = "VANM_DT_ULTIMA_ATUALIZACAO_ARQ")
    @NotNull
    private LocalDate dataUltimaAtualizacaoArquivo;
    
    @Column(name = "VANM_NR_QUANTIDADE")
    @NotNull
    private Long quantidadeSubTipo;
    
    @Column(name = "VANM_IN_SPLIT_CRIADO")
    @NotNull
    private Long splitCriado;

}

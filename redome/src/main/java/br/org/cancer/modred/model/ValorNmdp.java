package br.org.cancer.modred.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Classe com os valores separados por código NMDP, além de 
 * informações para fins de controle e conferência como data 
 * de atualização, quantidade de subTipos, etc.
 * 
 * @author Pizão
 *
 */
@Entity
@Table(name = "VALOR_NMDP")
public class ValorNmdp implements Serializable {
	private static final long serialVersionUID = -8223830705778608249L;

	@Id
    @Column(name = "VANM_ID_CODIGO")
    @NotNull
    private String codigo;
	
	@Column(name = "VANM_IN_AGRUPADO")
    @NotNull
	private Boolean agrupado;
	
	@Column(name = "VANM_DT_ULTIMA_ATUALIZACAO_ARQ")
	private LocalDate dataUltimaAtualizacao;
	
	@Column(name = "VANM_NR_QUANTIDADE")
    @NotNull
	private Boolean quantidade;

    @Column(name = "VANM_TX_SUBTIPO")
    @NotNull
    private String subTipos;
    
    

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Boolean getAgrupado() {
		return agrupado;
	}

	public void setAgrupado(Boolean agrupado) {
		this.agrupado = agrupado;
	}

	public LocalDate getDataUltimaAtualizacao() {
		return dataUltimaAtualizacao;
	}

	public void setDataUltimaAtualizacao(LocalDate dataUltimaAtualizacao) {
		this.dataUltimaAtualizacao = dataUltimaAtualizacao;
	}

	public Boolean getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Boolean quantidade) {
		this.quantidade = quantidade;
	}

	public String getSubTipos() {
		return subTipos;
	}

	public void setSubTipos(String subTipos) {
		this.subTipos = subTipos;
	}

    
}

package br.org.cancer.modred.vo.dashboard;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Classe vo para envio de dados detalhados de enriquecimento e contato para o front.
 * 
 * @author brunosousa
 *
 */
public class DetalheContatoVo implements Serializable {

	private static final long serialVersionUID = 832818923383990497L;
	
	private Integer fase;
	private String tipoPedido;
	private Long dmr;
	private String nome;
	private LocalDateTime dataCriacao;
	private Integer numeroTentativas;
	private LocalDateTime ultimaTentativa;
	private Boolean aberto;
	
	/**
	 * @return the fase
	 */
	public Integer getFase() {
		return fase;
	}
	
	/**
	 * @param fase the fase to set
	 */
	public void setFase(Integer fase) {
		this.fase = fase;
	}
	
	/**
	 * @return the tipoPedido
	 */
	public String getTipoPedido() {
		return tipoPedido;
	}
	
	/**
	 * @param tipoPedido the tipoPedido to set
	 */
	public void setTipoPedido(String tipoPedido) {
		this.tipoPedido = tipoPedido;
	}
	
	/**
	 * @return the dmr
	 */
	public Long getDmr() {
		return dmr;
	}
	
	/**
	 * @param dmr the dmr to set
	 */
	public void setDmr(Long dmr) {
		this.dmr = dmr;
	}
	
	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}
	
	/**
	 * @param nome the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	/**
	 * @return the dataCriacao
	 */
	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}
	
	/**
	 * @param dataCriacao the dataCriacao to set
	 */
	public void setDataCriacao(LocalDateTime dataCriacao) {
		this.dataCriacao = dataCriacao;
	}
	
	/**
	 * @return the numeroTentativas
	 */
	public Integer getNumeroTentativas() {
		return numeroTentativas;
	}
	
	/**
	 * @param numeroTentativas the numeroTentativas to set
	 */
	public void setNumeroTentativas(Integer numeroTentativas) {
		this.numeroTentativas = numeroTentativas;
	}
	
	/**
	 * @return the ultimaTentativa
	 */
	public LocalDateTime getUltimaTentativa() {
		return ultimaTentativa;
	}
	
	/**
	 * @param ultimaTentativa the ultimaTentativa to set
	 */
	public void setUltimaTentativa(LocalDateTime ultimaTentativa) {
		this.ultimaTentativa = ultimaTentativa;
	}
	
	/**
	 * @return the aberto
	 */
	public Boolean getAberto() {
		return aberto;
	}
	
	/**
	 * @param aberto the aberto to set
	 */
	public void setAberto(Boolean aberto) {
		this.aberto = aberto;
	}
	
	
}

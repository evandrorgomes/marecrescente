package br.org.cancer.modred.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * Classe utilizada para criação da consulta de doador - contato passivo.
 * 
 * @author evandro.gomes
 *
 */
public class ConsultaPedidoExameVo implements Serializable {

	private static final long serialVersionUID = 4860435637249185865L;

	private List<PedidoExameDoadorNacionalVo> pedidosNacionais;
	private List<PedidoExameDoadorInternacionalVo> pedidosInternacionais;
	
	public ConsultaPedidoExameVo() {}

	/**
	 * @return the pedidosNacionais
	 */
	public List<PedidoExameDoadorNacionalVo> getPedidosNacionais() {
		return pedidosNacionais;
	}

	/**
	 * @param pedidosNacionais the pedidosNacionais to set
	 */
	public void setPedidosNacionais(List<PedidoExameDoadorNacionalVo> pedidosNacionais) {
		this.pedidosNacionais = pedidosNacionais;
	}

	/**
	 * @return the pedidosInternacionais
	 */
	public List<PedidoExameDoadorInternacionalVo> getPedidosInternacionais() {
		return pedidosInternacionais;
	}

	/**
	 * @param pedidosInternacionais the pedidosInternacionais to set
	 */
	public void setPedidosInternacionais(List<PedidoExameDoadorInternacionalVo> pedidosInternacionais) {
		this.pedidosInternacionais = pedidosInternacionais;
	}

}

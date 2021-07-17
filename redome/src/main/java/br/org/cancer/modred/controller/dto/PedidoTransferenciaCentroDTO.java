package br.org.cancer.modred.controller.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.PedidoTransferenciaCentroView;
import br.org.cancer.modred.model.Evolucao;
import br.org.cancer.modred.model.PedidoTransferenciaCentro;

/**
 * Classe utilizada na tela de detalhe de transferência de centros.
 * 
 * @author brunosousa
 *
 */
public class PedidoTransferenciaCentroDTO implements Serializable {

	private static final long serialVersionUID = -5943619384819857827L;
	
	@JsonView(PedidoTransferenciaCentroView.Detalhe.class)
	private PedidoTransferenciaCentro pedidoTransferenciaCentro;
	
	@JsonView(PedidoTransferenciaCentroView.Detalhe.class)
	private Evolucao ultimaEvolucao;

	/**
	 * @return the pedidoTransferenciaCentro
	 */
	public PedidoTransferenciaCentro getPedidoTransferenciaCentro() {
		return pedidoTransferenciaCentro;
	}

	/**
	 * @param pedidoTransferenciaCentro the pedidoTransferenciaCentro to set
	 */
	public void setPedidoTransferenciaCentro(PedidoTransferenciaCentro pedidoTransferenciaCentro) {
		this.pedidoTransferenciaCentro = pedidoTransferenciaCentro;
	}

	/**
	 * @return the ultimaEvolucao
	 */
	public Evolucao getUltimaEvolucao() {
		return ultimaEvolucao;
	}

	/**
	 * @param ultimaEvolucao the ultimaEvolucao to set
	 */
	public void setUltimaEvolucao(Evolucao ultimaEvolucao) {
		this.ultimaEvolucao = ultimaEvolucao;
	}
	
	

}

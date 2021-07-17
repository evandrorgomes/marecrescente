package br.org.cancer.modred.controller.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.PedidoExameView;
import br.org.cancer.modred.model.PedidoExame;
import br.org.cancer.modred.model.PedidoIdm;

/**
 * DTO com informações tanto de pedido de exame quanto de pedido de exame de IDM, servirá para obter os dados para o cadastramento
 * do resultado de ambos os exames.
 * 
 * @author Queiroz
 *
 */
public class SolicitacaoInternacionalDTO implements Serializable {

	private static final long serialVersionUID = -485305659577314244L;

	@JsonView({PedidoExameView.ObterParaEditar.class})
	private PedidoExame pedidoExame;
	
	@JsonView({PedidoExameView.ObterParaEditar.class})
	private PedidoIdm pedidoIdm;
	
	/**
	 * @return the pedidoExame
	 */
	public PedidoExame getPedidoExame() {
		return pedidoExame;
	}

	/**
	 * @param pedidoExame the pedidoExame to set
	 */
	public void setPedidoExame(PedidoExame pedidoExame) {
		this.pedidoExame = pedidoExame;
	}

	/**
	 * @return the pedidoIdm
	 */
	public PedidoIdm getPedidoIdm() {
		return pedidoIdm;
	}

	/**
	 * @param pedidoIdm the pedidoIdm to set
	 */
	public void setPedidoIdm(PedidoIdm pedidoIdm) {
		this.pedidoIdm = pedidoIdm;
	}
}

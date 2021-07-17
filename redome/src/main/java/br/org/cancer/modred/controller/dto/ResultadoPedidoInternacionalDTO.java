package br.org.cancer.modred.controller.dto;

import java.io.Serializable;

/**
 * DTO com informações contendo as informações de pedido de CT e IDM para
 * serem utilizadas na tela. As telas são interligadas por funcionalidade e, em alguns casos,
 * uma poderá chamar a outra (ambas com tarefas relacionadas).
 * 
 * @author Pizão
 *
 */
public class ResultadoPedidoInternacionalDTO implements Serializable {
	private static final long serialVersionUID = -207918480511458086L;
	
	private Long idTarefaPedidoCT;
	private Long idPedidoCT;
	private Long idTarefaPedidoIdm;
	private Long idPedidoIdm;
	
	public Long getIdPedidoCT() {
		return idPedidoCT;
	}
	public void setIdPedidoCT(Long idPedidoCT) {
		this.idPedidoCT = idPedidoCT;
	}
	public Long getIdPedidoIdm() {
		return idPedidoIdm;
	}
	public void setIdPedidoIdm(Long idPedidoIdm) {
		this.idPedidoIdm = idPedidoIdm;
	}
	public Long getIdTarefaPedidoCT() {
		return idTarefaPedidoCT;
	}
	public void setIdTarefaPedidoCT(Long idTarefaPedidoCT) {
		this.idTarefaPedidoCT = idTarefaPedidoCT;
	}
	public Long getIdTarefaPedidoIdm() {
		return idTarefaPedidoIdm;
	}
	public void setIdTarefaPedidoIdm(Long idTarefaPedidoIdm) {
		this.idTarefaPedidoIdm = idTarefaPedidoIdm;
	}

}

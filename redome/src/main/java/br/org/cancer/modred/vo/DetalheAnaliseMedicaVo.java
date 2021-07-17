package br.org.cancer.modred.vo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.AnaliseMedicaView;
import br.org.cancer.modred.feign.dto.TarefaDTO;

/**
 * Classe Vo para o envio de dados da analise médica para o front.
 * 
 * @author brunosousa
 *
 */
public class DetalheAnaliseMedicaVo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@JsonView(AnaliseMedicaView.Detalhe.class)
	private Long idAnaliseMedica;	
	
	@JsonView(AnaliseMedicaView.Detalhe.class)
	private Long idTarefaAnaliseMedica;
	
	@JsonView(AnaliseMedicaView.Detalhe.class)
	private TarefaDTO tarefaFinalizadaPedidoContato;

	/**
	 * @return the idAnaliseMedica
	 */
	public Long getIdAnaliseMedica() {
		return idAnaliseMedica;
	}

	/**
	 * @param idAnaliseMedica the idAnaliseMedica to set
	 */
	public void setIdAnaliseMedica(Long idAnaliseMedica) {
		this.idAnaliseMedica = idAnaliseMedica;
	}

	/**
	 * @return the idTarefaAnaliseMedica
	 */
	public Long getIdTarefaAnaliseMedica() {
		return idTarefaAnaliseMedica;
	}

	/**
	 * @param idTarefaAnaliseMedica the idTarefaAnaliseMedica to set
	 */
	public void setIdTarefaAnaliseMedica(Long idTarefaAnaliseMedica) {
		this.idTarefaAnaliseMedica = idTarefaAnaliseMedica;
	}

	/**
	 * @return the tarefaFinalizadaPedidoContato
	 */
	public TarefaDTO getTarefaFinalizadaPedidoContato() {
		return tarefaFinalizadaPedidoContato;
	}

	/**
	 * @param tarefaFinalizadaPedidoContato the tarefaFinalizadaPedidoContato to set
	 */
	public void setTarefaFinalizadaPedidoContato(TarefaDTO tarefaFinalizadaPedidoContato) {
		this.tarefaFinalizadaPedidoContato = tarefaFinalizadaPedidoContato;
	}

}

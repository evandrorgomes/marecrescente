package br.org.cancer.modred.vo;

import java.io.Serializable;

import br.org.cancer.modred.model.Formulario;
import br.org.cancer.modred.model.domain.AcaoPedidoContato;

/**
 * Classe value object para facilitar a finalização do pedido de contato.
 * 
 * @author brunosousa
 *
 */
public class AnaliseMedicaFinalizadaVo implements Serializable {
	
	private static final long serialVersionUID = -3819852902232953497L;
	
	private Long acao;
	private Long idMotivoStatusDoador;
	private Long tempoInativacaoTemporaria;
	private Formulario formulario;
	
	public AnaliseMedicaFinalizadaVo() {}
	
	/**
	 * Método construtor com atributos.
	 * 
	 * @param contactado informa se o doador foi contactado.
	 * @param acao - Informa a ação que deverá ser feita.
	 */
	public AnaliseMedicaFinalizadaVo(AcaoPedidoContato acao) {
		super();
		this.acao = acao.getId();
	}
	
	/**
	 * @return the acao
	 */
	public AcaoPedidoContato getAcao() {
		return AcaoPedidoContato.valueById(acao);
	}
	
	/**
	 * @param acao the acao to set
	 */
	public void setAcao(AcaoPedidoContato acao) {
		this.acao = acao.getId();
	}
	
	/**
	 * @return the idMotivoStatusDoador
	 */
	public Long getIdMotivoStatusDoador() {
		return idMotivoStatusDoador;
	}
	/**
	 * @param idMotivoStatusDoador the idMotivoStatusDoador to set
	 */
	public void setIdMotivoStatusDoador(Long idMotivoStatusDoador) {
		this.idMotivoStatusDoador = idMotivoStatusDoador;
	}
	/**
	 * @return the tempoInativacaoTemporaria
	 */
	public Long getTempoInativacaoTemporaria() {
		return tempoInativacaoTemporaria;
	}
	/**
	 * @param tempoInativacaoTemporaria the tempoInativacaoTemporaria to set
	 */
	public void setTempoInativacaoTemporaria(Long tempoInativacaoTemporaria) {
		this.tempoInativacaoTemporaria = tempoInativacaoTemporaria;
	}

	/**
	 * @return the formulario
	 */
	public Formulario getFormulario() {
		return formulario;
	}

	/**
	 * @param formulario the formulario to set
	 */
	public void setFormulario(Formulario formulario) {
		this.formulario = formulario;
	}
	
}

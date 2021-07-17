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
public class PedidoContatoFinalizadoVo implements Serializable {
	
	private static final long serialVersionUID = -3819852902232953497L;
	
	private Boolean contactado; 
	private Boolean contactadoPorTerceiro; 
	private Long acao;
	private Long idMotivoStatusDoador;
	private Long tempoInativacaoTemporaria;
	private Formulario formulario;
	private Long hemocentro;
	
	public PedidoContatoFinalizadoVo() {}
	
	/**
	 * Método construtor com atributos.
	 * 
	 * @param contactado informa se o doador foi contactado.
	 * @param acao - Informa a ação que deverá ser feita.
	 */
	public PedidoContatoFinalizadoVo(Boolean contactado, AcaoPedidoContato acao) {
		super();
		this.contactado = contactado;
		if(acao != null) {
			this.acao = acao.getId();
		}
	}
	/**
	 * @return the contactado
	 */
	public Boolean getContactado() {
		return contactado;
	}
	/**
	 * @param contactado the contactado to set
	 */
	public void setContactado(Boolean contactado) {
		this.contactado = contactado;
	}
	/**
	 * @return the contactadoPorTerceiro
	 */
	public Boolean getContactadoPorTerceiro() {
		return contactadoPorTerceiro;
	}
	/**
	 * @param contactadoPorTerceiro the contactadoPorTerceiro to set
	 */
	public void setContactadoPorTerceiro(Boolean contactadoPorTerceiro) {
		this.contactadoPorTerceiro = contactadoPorTerceiro;
	}
	//	/**
	//	 * @return the acao
	//	 */
	//	public AcaoPedidoContato getAcao() {
	//		return AcaoPedidoContato.valueById(acao);
	//	}
	//	/**
	//	 * @param acao the acao to set
	//	 */
	//	public void setAcao(AcaoPedidoContato acao) {
	//		this.acao = acao.getId();
	//	}
	/**
	 * @return the idMotivoStatusDoador
	 */
	public Long getIdMotivoStatusDoador() {
		return idMotivoStatusDoador;
	}
	/**
	 * @return the acao
	 */
	public Long getAcao() {
		return acao;
	}

	/**
	 * @param acao the acao to set
	 */
	public void setAcao(Long acao) {
		this.acao = acao;
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

	/**
	 * @return the hemocentro
	 */
	public Long getHemocentro() {
		return hemocentro;
	}

	/**
	 * @param hemocentro the hemocentro to set
	 */
	public void setHemocentro(Long hemocentro) {
		this.hemocentro = hemocentro;
	}
	
}

package br.org.cancer.modred.controller.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.BuscaView;
import br.org.cancer.modred.model.Laboratorio;
import br.org.cancer.modred.model.PedidoExame;

/**
 * Classe referente aos parametros necessários para a visualização do pedido de exame CT de uma paciente.
 * 
 * @author bruno.sousa
 *
 */
public class UltimoPedidoExameDTO implements Serializable {

	private static final long serialVersionUID = -6742576231911356334L;
	
	@JsonView(BuscaView.UltimoPedidoExame.class)
	private Long rmr;
    
	@JsonView(BuscaView.UltimoPedidoExame.class)
	private String municipioEnderecoPaciente;
    
	@JsonView(BuscaView.UltimoPedidoExame.class)
	private String ufEnderecoPaciente;
    
	@JsonView(BuscaView.UltimoPedidoExame.class)
	private Laboratorio laboratorioDePrefencia;
    
	@JsonView(BuscaView.UltimoPedidoExame.class)
	private PedidoExame pedidoExame;

	@JsonView(BuscaView.UltimoPedidoExame.class)
	private String descricaoTipoAmostra;
	
	/**
	 * Contrutor padrão.
	 */
	public UltimoPedidoExameDTO() {
	}
	
	/**
	 * @return the rmr
	 */
	public Long getRmr() {
		return rmr;
	}
	
	/**
	 * @param rmr the rmr to set
	 */
	public void setRmr(Long rmr) {
		this.rmr = rmr;
	}
	
	/**
	 * @return the municipioEnderecoPaciente
	 */
	public String getMunicipioEnderecoPaciente() {
		return municipioEnderecoPaciente;
	}
	
	/**
	 * @param municipioEnderecoPaciente the municipioEnderecoPaciente to set
	 */
	public void setMunicipioEnderecoPaciente(String municipioEnderecoPaciente) {
		this.municipioEnderecoPaciente = municipioEnderecoPaciente;
	}
	
	/**
	 * @return the ufEnderecoPaciente
	 */
	public String getUfEnderecoPaciente() {
		return ufEnderecoPaciente;
	}
	
	/**
	 * @param ufEnderecoPaciente the ufEnderecoPaciente to set
	 */
	public void setUfEnderecoPaciente(String ufEnderecoPaciente) {
		this.ufEnderecoPaciente = ufEnderecoPaciente;
	}
	
	/**
	 * @return the laboratorioDePrefencia
	 */
	public Laboratorio getLaboratorioDePrefencia() {
		return laboratorioDePrefencia;
	}
	
	/**
	 * @param laboratorioDePrefencia the laboratorioDePrefencia to set
	 */
	public void setLaboratorioDePrefencia(Laboratorio laboratorioDePrefencia) {
		this.laboratorioDePrefencia = laboratorioDePrefencia;
	}
	
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
	 * @return the descricaoTipoAmostra
	 */
	public String getDescricaoTipoAmostra() {
		return descricaoTipoAmostra;
	}

	/**
	 * @param descricaoTipoAmostra the descricaoTipoAmostra to set
	 */
	public void setDescricaoTipoAmostra(String descricaoTipoAmostra) {
		this.descricaoTipoAmostra = descricaoTipoAmostra;
	}
}

package br.org.cancer.modred.controller.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.GenotipoView;
import br.org.cancer.modred.model.Exame;
import br.org.cancer.modred.model.PedidoExame;
import br.org.cancer.modred.model.domain.TiposDoador;

/**
 * Class utilizada para a tela de genótipo divergente.
 * Contém o genótipo do doador, todos os exames e o id(s) do pedido de CT com o id do exame.  
 * 
 * @author brunosousa
 *
 */
public class GenotipoDivergenteDTO implements Serializable {
	
	private static final long serialVersionUID = 4743597377980366219L;
	
	@JsonView(GenotipoView.Divergente.class)
	private TiposDoador tipoDoador;
	
	@JsonView(GenotipoView.Divergente.class)
	private List<GenotipoDTO> genotiposDto;
	
	@JsonView(GenotipoView.Divergente.class)
	private List<Exame> exames;
	
	@JsonView(GenotipoView.Divergente.class)
	private List<PedidoExame> pedidos;
	
	@JsonView(GenotipoView.Divergente.class)
	private Boolean existeSolicitacaoFase3EmAberto;
	
	@JsonView(GenotipoView.Divergente.class)
	private String textoEmailDivergencia;
	
	/**
	 * @return the tipoDoador
	 */
	public TiposDoador getTipoDoador() {
		return tipoDoador;
	}
	/**
	 * @param tipoDoador the tipoDoador to set
	 */
	public void setTipoDoador(TiposDoador tipoDoador) {
		this.tipoDoador = tipoDoador;
	}
	/**
	 * @return the genotipoDto
	 */
	public List<GenotipoDTO> getGenotiposDto() {
		return genotiposDto;
	}
	/**
	 * @param genotipoDto the genotipoDto to set
	 */
	public void setGenotiposDto(List<GenotipoDTO> genotiposDto) {
		this.genotiposDto = genotiposDto;
	}
	/**
	 * @return the exames
	 */
	public List<Exame> getExames() {
		return exames;
	}
	/**
	 * @param list the exames to set
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void setExames(List exames) {
		this.exames = exames;
	}
	/**
	 * @return the pedidos
	 */
	public List<PedidoExame> getPedidos() {
		return pedidos;
	}
	/**
	 * @param pedidos the pedidos to set
	 */
	public void setPedidos(List<PedidoExame> pedidos) {
		this.pedidos = pedidos;
	}
	/**
	 * @return the existeSolicitacaoFase3EmAberto
	 */
	public Boolean getExisteSolicitacaoFase3EmAberto() {
		return existeSolicitacaoFase3EmAberto;
	}
	/**
	 * @param existeSolicitacaoFase3EmAberto the existeSolicitacaoFase3EmAberto to set
	 */
	public void setExisteSolicitacaoFase3EmAberto(Boolean existeSolicitacaoFase3EmAberto) {
		this.existeSolicitacaoFase3EmAberto = existeSolicitacaoFase3EmAberto;
	}
	/**
	 * @return the textoEmailDivergencia
	 */
	public String getTextoEmailDivergencia() {
		return textoEmailDivergencia;
	}
	/**
	 * @param textoEmailDivergencia the textoEmailDivergencia to set
	 */
	public void setTextoEmailDivergencia(String textoEmailDivergencia) {
		this.textoEmailDivergencia = textoEmailDivergencia;
	}
	
	

}

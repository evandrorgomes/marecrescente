package br.org.cancer.modred.controller.dto;

import java.io.Serializable;
import java.util.List;

import br.org.cancer.modred.model.Locus;

/**
 * Dto utilizado pelo cadastro de doador internacional para solictar um pedido quando o doador é cadastrado.
 * 
 * @author brunosousa
 *
 */
public class PedidoDto implements Serializable {

	private static final long serialVersionUID = 2971653487820163193L;
	
	private Long tipoExame;
	
	private List<Locus> locus;

	/**
	 * @return the tipoExame
	 */
	public Long getTipoExame() {
		return tipoExame;
	}

	/**
	 * @param tipoExame the tipoExame to set
	 */
	public void setTipoExame(Long tipoExame) {
		this.tipoExame = tipoExame;
	}

	/**
	 * @return the locus
	 */
	public List<Locus> getLocus() {
		return locus;
	}

	/**
	 * @param locus the locus to set
	 */
	public void setLocus(List<Locus> locus) {
		this.locus = locus;
	}
	

}

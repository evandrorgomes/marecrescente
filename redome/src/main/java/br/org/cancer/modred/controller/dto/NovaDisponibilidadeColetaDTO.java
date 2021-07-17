package br.org.cancer.modred.controller.dto;

/**
 * Classe referente a nova disponibilidade de centro de coleta.
 * 
 * @author Fillipe Queiroz
 *
 */
public class NovaDisponibilidadeColetaDTO {

	private Long idCentroColeta;
	private String data;
	private Long idDisponibilidadeCentroColeta;
	private String resultado;

	/**
	 * Identificador do centro de coleta.
	 * 
	 * @return
	 */
	public Long getIdCentroColeta() {
		return idCentroColeta;
	}

	/**
	 * Identificador do centro de coleta.
	 * 
	 * @param idCentroColeta
	 */
	public void setIdCentroColeta(Long idCentroColeta) {
		this.idCentroColeta = idCentroColeta;
	}

	/**
	 * Data em formato string adicionado pelo analista de workup.
	 * 
	 * @return
	 */
	public String getData() {
		return data;
	}

	/**
	 * Data em formato string adicionado pelo analista de workup.
	 * 
	 * @param data
	 */
	public void setData(String data) {
		this.data = data;
	}

	/**
	 * Identificador da disponibilidade do centro de coleta.
	 * 
	 * @return idDisponibilidadeCentroColeta
	 */
	public Long getIdDisponibilidadeCentroColeta() {
		return idDisponibilidadeCentroColeta;
	}

	/**
	 * Identificador da disponibilidade do centro de coleta.
	 * 
	 * @param idDisponibilidadeCentroColeta
	 */
	public void setIdDisponibilidadeCentroColeta(Long idDisponibilidadeCentroColeta) {
		this.idDisponibilidadeCentroColeta = idDisponibilidadeCentroColeta;
	}

	/**
	 * Mensagem de sucesso ou erro.
	 * 
	 * @return
	 */
	public String getResultado() {
		return resultado;
	}

	/**
	 * Mensagem de sucesso ou erro.
	 * 
	 * @param resultado
	 */
	public void setResultado(String resultado) {
		this.resultado = resultado;
	}

}

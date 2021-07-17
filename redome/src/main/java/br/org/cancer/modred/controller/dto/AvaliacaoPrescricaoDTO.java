package br.org.cancer.modred.controller.dto;

/**
 * Classe VO para dados da tela de Avaliacao de prescricao.
 * 
 * @author fillipe.queiroz
 *
 */
public class AvaliacaoPrescricaoDTO {

	private String justificativaDescarteFonteCelula;
	private Long idFonteCelulaDescartada;

	/**
	 * @return the justificativaDescarteFonteCelula
	 */
	public String getJustificativaDescarteFonteCelula() {
		return justificativaDescarteFonteCelula;
	}

	/**
	 * @param justificativaDescarteFonteCelula the justificativaDescarteFonteCelula to set
	 */
	public void setJustificativaDescarteFonteCelula(String justificativaDescarteFonteCelula) {
		this.justificativaDescarteFonteCelula = justificativaDescarteFonteCelula;
	}

	/**
	 * @return the idFonteCelulaDescartada
	 */
	public Long getIdFonteCelulaDescartada() {
		return idFonteCelulaDescartada;
	}

	/**
	 * @param idFonteCelulaDescartada the idFonteCelulaDescartada to set
	 */
	public void setIdFonteCelulaDescartada(Long idFonteCelulaDescartada) {
		this.idFonteCelulaDescartada = idFonteCelulaDescartada;
	}

}

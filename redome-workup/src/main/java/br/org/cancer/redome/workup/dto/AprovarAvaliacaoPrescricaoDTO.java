package br.org.cancer.redome.workup.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Classe VO para dados da tela de Avaliacao de prescricao.
 * 
 * @author fillipe.queiroz
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AprovarAvaliacaoPrescricaoDTO {

	private String justificativaDescarteFonteCelula;
	private Long idFonteCelulaDescartada;


}

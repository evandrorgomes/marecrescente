package br.org.cancer.redome.workup.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.org.cancer.redome.workup.model.Prescricao;
import br.org.cancer.redome.workup.model.PrescricaoMedula;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para prescrição.
 * 
 * @author ergomes
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class PrescricaoMedulaDTO {

	private LocalDate dataColeta1;
	private LocalDate dataColeta2;
	private LocalDate dataLimiteWorkup1;
	private LocalDate dataLimiteWorkup2;
	private FonteCelulaDTO fonteCelulaOpcao1;
	private FonteCelulaDTO fonteCelulaOpcao2;
	private BigDecimal quantidadeTotalOpcao1;
	private BigDecimal quantidadeTotalOpcao2;
	private BigDecimal quantidadePorKgOpcao1;	
	private BigDecimal quantidadePorKgOpcao2;
	private Boolean fazerColeta;
	private List<TipoAmostraPrescricaoDTO> amostras;
	
	public static PrescricaoMedulaDTO parse(Prescricao prescricao) {

		PrescricaoMedula prescricaoMedula = (PrescricaoMedula) prescricao;
		
		List<TipoAmostraPrescricaoDTO> lsAmostras = prescricaoMedula.getAmostras().stream()
				.map(TipoAmostraPrescricaoDTO::new).collect(Collectors.toList());
		
		PrescricaoMedulaDTO prescricaoMedulaDTO = PrescricaoMedulaDTO.builder()
			.dataColeta1(prescricaoMedula.getDataColeta1())
			.dataColeta2(prescricaoMedula.getDataColeta2())
			.dataLimiteWorkup1(prescricaoMedula.getDataResultadoWorkup1())
			.dataLimiteWorkup2(prescricaoMedula.getDataResultadoWorkup2())
			.fonteCelulaOpcao1(FonteCelulaDTO.parse(prescricaoMedula.getFonteCelulaOpcao1()))
			.fonteCelulaOpcao2(FonteCelulaDTO.parse(prescricaoMedula.getFonteCelulaOpcao2()))
			.quantidadeTotalOpcao1(prescricaoMedula.getQuantidadeTotalOpcao1())
			.quantidadeTotalOpcao2(prescricaoMedula.getQuantidadeTotalOpcao2())
			.quantidadePorKgOpcao1(prescricaoMedula.getQuantidadePorKgOpcao1())
			.quantidadePorKgOpcao2(prescricaoMedula.getQuantidadePorKgOpcao2())
			.fazerColeta(prescricaoMedula.getFazerColeta())
			.amostras(lsAmostras)
			.build();
		return prescricaoMedulaDTO;
	}
	
}

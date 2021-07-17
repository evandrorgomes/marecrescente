package br.org.cancer.redome.workup.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.org.cancer.redome.workup.model.Prescricao;
import br.org.cancer.redome.workup.model.PrescricaoCordao;
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
public class PrescricaoCordaoDTO {

	private LocalDate dataInfusao;
	private LocalDate dataReceber1;
	private LocalDate dataReceber2;
	private LocalDate dataReceber3;
	private Boolean armazenaCordao;
	private String nomeContatoParaReceber;
	private String nomeContatoUrgente;
	private Integer codigoAreaUrgente;
	private Long telefoneUrgente;
	
	public static PrescricaoCordaoDTO parse(Prescricao prescricao) {

		PrescricaoCordao prescricaoCordao = (PrescricaoCordao) prescricao;
		
		PrescricaoCordaoDTO prescricaoCordaoDTO = PrescricaoCordaoDTO.builder()
			.dataInfusao(prescricaoCordao.getDataInfusao())
			.dataReceber1(prescricaoCordao.getDataReceber1())
			.dataReceber2(prescricaoCordao.getDataReceber2())
			.dataReceber3(prescricaoCordao.getDataReceber3())
			.nomeContatoParaReceber(prescricaoCordao.getNomeContatoParaReceber())
			.nomeContatoUrgente(prescricaoCordao.getNomeContatoUrgente())
			.codigoAreaUrgente(prescricaoCordao.getCodigoAreaUrgente())
			.telefoneUrgente(prescricaoCordao.getTelefoneUrgente())
			.armazenaCordao(prescricaoCordao.getArmazenaCordao())
			.build();
		
		return prescricaoCordaoDTO;
	}
}

package br.org.cancer.redome.workup.dto;

import java.time.LocalDate;

import br.org.cancer.redome.workup.model.domain.TiposDoador;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class CriarPrescricaoCordaoDTO {
	
	private Long rmr;
	private Long idMatch;
	private Long idTipoDoador;
	private LocalDate dataInfusao;
	private LocalDate dataReceber1;
	private LocalDate dataReceber2;
	private LocalDate dataReceber3;
	private Boolean armazenaCordao;
	private String nomeContatoParaReceber;
	private String nomeContatoUrgente;
	private Integer codigoAreaUrgente;
	private Long telefoneUrgente;

	public Boolean isNacional() {
		return idTipoDoador != null ? TiposDoador.CORDAO_NACIONAL.getId().equals(idTipoDoador) : null;
	}
	
	public Boolean isInternacional() {
		return idTipoDoador != null ? TiposDoador.CORDAO_INTERNACIONAL.getId().equals(idTipoDoador) : null;
	}
	
}

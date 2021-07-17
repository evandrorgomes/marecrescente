package br.org.cancer.redome.feign.client.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class EvolucaoDTO {

	private Long id;
	
	private List<TipoTransplanteDTO> tiposTransplante;

	private LocalDate dataUltimoTransplante;
	
	private BigDecimal peso;
}

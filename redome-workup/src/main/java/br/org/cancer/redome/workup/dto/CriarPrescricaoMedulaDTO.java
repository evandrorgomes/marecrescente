package br.org.cancer.redome.workup.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import br.org.cancer.redome.workup.model.TipoAmostraPrescricao;
import br.org.cancer.redome.workup.model.domain.TiposDoador;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor()
@AllArgsConstructor
@Getter
@Builder
public class CriarPrescricaoMedulaDTO {
	
	private Long rmr;
	private Long idMatch;
	private Long idTipoDoador;
	private LocalDate dataColeta1;
	private LocalDate dataColeta2;
	private LocalDate dataLimiteWorkup1;
	private LocalDate dataLimiteWorkup2;
	private Long fonteCelulaOpcao1;
	private BigDecimal quantidadeTotalOpcao1;
	private BigDecimal quantidadePorKgOpcao1;	
	private Long fonteCelulaOpcao2;
	private BigDecimal quantidadeTotalOpcao2;
	private BigDecimal quantidadePorKgOpcao2;
	private List<TipoAmostraPrescricao> tiposAmostraPrescricao;
	private Boolean fazerColeta;
	
	
	public Boolean isNacional() {
		return idTipoDoador != null ? TiposDoador.NACIONAL.getId().equals(idTipoDoador) : null;
	}
	
	public Boolean isInternacional() {
		return idTipoDoador != null ? TiposDoador.INTERNACIONAL.getId().equals(idTipoDoador) : null;
	}

}

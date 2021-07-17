package br.org.cancer.redome.workup.dto;

import java.util.List;
import java.util.stream.Collectors;

import br.org.cancer.redome.workup.model.ResultadoWorkup;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
public class ResultadoWorkupInternacionalDTO {
	
	private Boolean coletaInviavel;
	private List<ArquivoResultadoWorkupDTO> arquivosResultadoWorkup;
	

	public ResultadoWorkupInternacionalDTO(ResultadoWorkup resultadoWorkup) {

		this.coletaInviavel = resultadoWorkup.getColetaInviavel();
		if(!resultadoWorkup.getArquivosResultadoWorkup().isEmpty()) {
			this.arquivosResultadoWorkup = resultadoWorkup.getArquivosResultadoWorkup().stream()
				.map(ArquivoResultadoWorkupDTO::new).collect(Collectors.toList());
		}
	}
}

package br.org.cancer.redome.workup.dto;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class ConsultaTarefasAvaliacaoPedidoColetaDTO {
	
	private Long idTarefa;
	private Long idStatusTarefa;
	private Long rmr;
	private String nomePaciente;
	private Long idDoador;
	private String identificacaoDoador;
	private LocalDate dataColeta;
	private Long idAvaliacaoResultadoWorkup;

	
	public Integer getDiasRestantes() {
		if(this.dataColeta != null){
			if(LocalDate.now().isAfter(this.dataColeta)){
				return (int) this.dataColeta.until(LocalDate.now(),ChronoUnit.DAYS) * -1;	
			}
			else{
				return (int) LocalDate.now().until(this.dataColeta,ChronoUnit.DAYS);
			}
			
		}
		return null;
	}

	

}

package br.org.cancer.redome.workup.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO para listar os pacientes com prescrição.
 * 
 * @author ergomes
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ConsultaPrescricaoDTO {

	private Long rmr;
	
	private String nomePaciente;
	
	private String status;
	
	private String agingDaTarefa;
	
	private Long idPrescricao;
	
	private Long idDoador;
	
	private String identificadorDoador;
	
	private Long idTipoDoador;
	
	private BigDecimal peso;
	
}

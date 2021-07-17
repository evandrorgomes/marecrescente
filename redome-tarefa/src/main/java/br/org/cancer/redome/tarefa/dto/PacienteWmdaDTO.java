package br.org.cancer.redome.tarefa.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Dto para dados do Wmda de paciente.
 * @author Filipe Paes
 *
 */
@Data 
@Builder
@NoArgsConstructor 
@AllArgsConstructor
public class PacienteWmdaDTO {

	private Long rmr;
	private BigDecimal peso;
	private LocalDate dataNascimento;
	private String abo;
	private String sexo;
	List<LocusExameHlaWmdaDTO> locusExame;
	private Long wmdaId;


}

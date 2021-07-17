package br.org.cancer.redome.workup.dto;

import java.io.Serializable;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@NoArgsConstructor
@AllArgsConstructor
public abstract class PlanoWorkupDTO implements Serializable {
	
	private static final long serialVersionUID = 3410935151790569304L;

	private LocalDate dataExame;

	private LocalDate dataResultado;
	
	private LocalDate dataColeta;
	
	private Long idCentroTransplante;	

}

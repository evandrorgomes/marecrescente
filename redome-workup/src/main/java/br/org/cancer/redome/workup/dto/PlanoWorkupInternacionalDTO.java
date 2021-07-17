package br.org.cancer.redome.workup.dto;

import java.io.Serializable;
import java.time.LocalDate;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;


//@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Getter
public class PlanoWorkupInternacionalDTO extends PlanoWorkupDTO implements Serializable {

	private static final long serialVersionUID = 8814050491745118764L;
	
	private String observacaoPlanoWorkup;
	
	/*
	 * public PlanoWorkupNacionalDTO() { super(); }
	 */
	
	@Builder
	public PlanoWorkupInternacionalDTO(LocalDate dataExame, LocalDate dataResultado,  LocalDate dataColeta, Long idCentroTransplante, String observacaoPlanoWorkup ) {
		super(dataExame, dataResultado, dataColeta, idCentroTransplante);
		this.observacaoPlanoWorkup = observacaoPlanoWorkup;
	}
	
	
	
}

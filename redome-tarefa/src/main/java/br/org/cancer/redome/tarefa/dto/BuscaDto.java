package br.org.cancer.redome.tarefa.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe de persistencia de Busca.
 * 
 * @author Filipe Paes
 */
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class BuscaDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private Long paciente;

	private Long status;

	private Long usuario;
	
	private Integer aceitaMismatch;
	
	private Long idCentroTransplante;
	
	private LocalDateTime dataUltimaAnalise;
	
	private Long wmdaId;

	/**
	 * @param id
	 */
	public BuscaDto(Long id) {
		super();
		this.id = id;
	}

}
package br.org.cancer.redome.tarefa.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe DTO que representa a pesquisa das informações do doador compativel no WMDA.
 * 
 * @author ergomes
 *
 */
@Data @NoArgsConstructor @Builder @AllArgsConstructor
public class DoadorWmdaDTO implements Serializable {

	private static final long serialVersionUID = 9154266415191455142L;

	private Long id;
	private String grid;
	private String donPool;
	private String jsonDoador;
	
	/* ###### DOADOR ###### */
	private String identificacao;
	private Long tipoDoador;
	private Long registroOrigem;
	private String sexo;
	private String abo;
	private LocalDate dataNascimento;
	private BigDecimal peso;
	private BigDecimal quantidadeTotalTCN;
	private BigDecimal quantidadeTotalCD34;
	private BigDecimal volume;
	private Integer idade;
	private List<LocusExameWmdaDTO> locusExame;
	
	/* ###### MATCH ###### */
	private MatchWmdaDTO matchWmdaDto;
	
}
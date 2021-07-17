package br.org.cancer.redome.tarefa.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

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
@Data @NoArgsConstructor @Builder(toBuilder = true) @AllArgsConstructor
public class PesquisaWmdaDTO implements Serializable {

	private static final long serialVersionUID = -7095835687209235838L;
	
	private Long id;
	private Long buscaId;
	private Long searchId;
	private Long searchResultId;
	private String wmdaId;
	private String tipo;
	private String algorithm;
	private LocalDateTime dtEnvio;
	private LocalDateTime dtResultado;
	private Integer status;
	private Integer qtdMatchWmda;
	private Integer qtdMatchWmdaImportado;
	private String logPesquisaWmda;
	private String jsonPesquisaWmda;
	private Integer qtdValorBlank;
	
	private PesquisaWmdaDoadorDTO pesquisaWmdaDoadorDto;
}

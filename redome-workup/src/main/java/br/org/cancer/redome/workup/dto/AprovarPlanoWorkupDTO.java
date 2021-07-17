package br.org.cancer.redome.workup.dto;

import java.io.Serializable;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AprovarPlanoWorkupDTO implements Serializable {
	
	private static final long serialVersionUID = -2089129585399019687L;

	private LocalDate dataCondicionamento;
	private LocalDate dataInfusao;
	private LocalDate dataColeta;
	private Boolean criopreservacao;
	private String observacaoAprovaPlanoWorkup;
	
}

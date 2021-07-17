package br.org.cancer.redome.workup.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Getter
public class PlanoWorkupNacionalDTO extends PlanoWorkupDTO implements Serializable {

	private static final long serialVersionUID = 8814050491745118764L;

	private LocalDate dataInternacao;
	private LocalDateTime dataExameMedico1;
	private LocalDateTime dataExameMedico2;
	private LocalDateTime dataRepeticaoBthcg;
	private String setorAndar;
	private String procurarPor;
	private Boolean doadorEmJejum;
	private Integer horasEmJejum;
	private String observacaoPlanoWorkup;
	private String informacoesAdicionais;
	
	
	/*
	 * public PlanoWorkupNacionalDTO() { super(); }
	 */
	/**
	 * @param dataInternacao
	 * @param dataExameMedico1
	 * @param dataExameMedico2
	 * @param dataRepeticaoBthcg
	 * @param setorAndar
	 * @param nomeProcurar
	 * @param doadorJejum
	 * @param horasJejum
	 * @param observacaoPlanoWorkup
	 * @param informacoesAdicionais
	 */
	@Builder
	public PlanoWorkupNacionalDTO(LocalDate dataExame, LocalDate dataResultado,  LocalDate dataColeta, Long idCentroTransplante, LocalDate dataInternacao, LocalDateTime dataExameMedico1,
			LocalDateTime dataExameMedico2, LocalDateTime dataRepeticaoBthcg, String setorAndar, String procurarPor,
			Boolean doadorEmJejum, Integer horasEmJejum, String observacaoPlanoWorkup, String informacoesAdicionais) {
		super(dataExame, dataResultado, dataColeta, idCentroTransplante);
		this.dataInternacao = dataInternacao;
		this.dataExameMedico1 = dataExameMedico1;
		this.dataExameMedico2 = dataExameMedico2;
		this.dataRepeticaoBthcg = dataRepeticaoBthcg;
		this.setorAndar = setorAndar;
		this.procurarPor = procurarPor;
		this.doadorEmJejum = doadorEmJejum;
		this.horasEmJejum = horasEmJejum;
		this.observacaoPlanoWorkup = observacaoPlanoWorkup;
		this.informacoesAdicionais = informacoesAdicionais;
	}
	
}

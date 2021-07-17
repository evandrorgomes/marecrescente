package br.org.cancer.redome.workup.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import br.org.cancer.redome.workup.model.domain.TiposPrescricao;
import br.org.cancer.redome.workup.model.interfaces.ISolicitacao;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@DiscriminatorValue("1")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@ToString(includeFieldNames = true, onlyExplicitlyIncluded = true, callSuper = true)
public class PrescricaoCordao extends Prescricao implements ISolicitacao, Serializable {

	private static final long serialVersionUID = -531605939902959432L;
	
	@Column(name = "PRES_DT_INFUSAO_CORDAO")
	@NotNull
	private LocalDate dataInfusao;
	
	@Column(name = "PRES_DT_RECEBER_CORDAO_1")
	@NotNull	
	private LocalDate dataReceber1;
	
	@Column(name = "PRES_DT_RECEBER_CORDAO_2")
	private LocalDate dataReceber2;
	
	@Column(name = "PRES_DT_RECEBER_CORDAO_3")
	private LocalDate dataReceber3;

	@Column(name = "PRES_IN_ARMAZENA_CORDAO")
	@NotNull
	private Boolean armazenaCordao;
	
	@Column(name = "PRES_TX_CONTATO_RECEBER")
	@NotNull
	private String nomeContatoParaReceber;
	
	@Column(name = "PRES_TX_CONTATO_URGENTE")
	@NotNull
	private String nomeContatoUrgente;
	
	@Column(name = "PRES_NR_COD_AREA_URGENTE")
	private Integer codigoAreaUrgente;
	
	@Column(name = "PRES_NR_TELEFONE_URGENTE")
	private Long telefoneUrgente;
	
	@Builder
	public PrescricaoCordao(Long id, Long solicitacao, @NotNull Long evolucao, @NotNull Long usuario,
			List<ArquivoPrescricao> arquivosPrescricao) {
		super(id, TiposPrescricao.PRESCRICAO_CORDAO.getId(), solicitacao, evolucao, usuario, arquivosPrescricao);
	}
	
	@Override
	public LocalDate menorDataColeta() {
		if (dataReceber1 == null && dataReceber2 == null && dataReceber3 == null) {
			return null;
		}
		
		final boolean dataReceber1MenorDataReceber2 = dataReceber2 != null ? dataReceber1.isBefore(dataReceber2): true;
		final boolean dataReceber1MenorDataReceber3 = dataReceber3 != null ? dataReceber1.isBefore(dataReceber3): true;
		final boolean dataReceber2MenorDataReceber3 = dataReceber2 != null && dataReceber3 != null ? dataReceber2.isBefore(dataReceber3) : 
			dataReceber2 != null && dataReceber3 == null ? true : false;
		
		if (dataReceber1MenorDataReceber2 && dataReceber1MenorDataReceber3) {
			return dataReceber1;
		}
		else if (dataReceber2MenorDataReceber3) {
			return dataReceber2;
		}
		return dataReceber3;
		
	}
	
	

}

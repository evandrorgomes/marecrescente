package br.org.cancer.redome.workup.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import br.org.cancer.redome.workup.model.domain.TiposPrescricao;
import br.org.cancer.redome.workup.model.interfaces.ISolicitacao;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Classe de persistencia para Prescrição.
 * 
 * @author Filipe Paes
 */
@Entity
@DiscriminatorValue("0")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@ToString(includeFieldNames = true, onlyExplicitlyIncluded = true, callSuper = true)
public class PrescricaoMedula extends Prescricao implements ISolicitacao, Serializable {

	private static final long serialVersionUID = 7927766527992845073L;

	/**
	 * Primeira sugestão para data coleta.
	 */
	@Column(name = "PRES_DT_COLETA_1")
	@NotNull
	private LocalDate dataColeta1;

	/**
	 * Segunda sugestão para data de coleta.
	 */
	@Column(name = "PRES_DT_COLETA_2")
	@NotNull
	private LocalDate dataColeta2;

	/**
	 * Primeira sugestão para resultado de coleta.
	 */
	@Column(name = "PRES_DT_RESULTADO_WORKUP_1")
	private LocalDate dataResultadoWorkup1;

	/**
	 * Segunda sugestão para resultado de workup.
	 */
	@Column(name = "PRES_DT_RESULTADO_WORKUP_2")
	private LocalDate dataResultadoWorkup2;

	@ManyToOne
	@JoinColumn(name = "FOCE_ID_OPCAO_1")
	@NotNull
	private FonteCelula fonteCelulaOpcao1;

	@Column(name = "PRES_VL_QUANT_TOTAL_OPCAO_1", precision = 6, scale = 2)
	@NotNull
	private BigDecimal quantidadeTotalOpcao1;

	@Column(name = "PRES_VL_QUANT_KG_OPCAO_1", precision = 6, scale = 2)
	private BigDecimal quantidadePorKgOpcao1;

	@ManyToOne
	@JoinColumn(name = "FOCE_ID_OPCAO_2")
	private FonteCelula fonteCelulaOpcao2;

	@Column(name = "PRES_VL_QUANT_TOTAL_OPCAO_2", precision = 6, scale = 2)
	private BigDecimal quantidadeTotalOpcao2;

	@Column(name = "PRES_VL_QUANT_KG_OPCAO_2", precision = 6, scale = 2)
	private BigDecimal quantidadePorKgOpcao2;

	@OneToMany(mappedBy = "prescricao", cascade = CascadeType.ALL)
	private List<TipoAmostraPrescricao> amostras;
	
	@Column(name = "PRES_IN_FAZER_COLETA")
	private Boolean fazerColeta;
	
	public PrescricaoMedula(Long id, Long solicitacao, @NotNull Long evolucao, @NotNull Long usuario,
			List<ArquivoPrescricao> arquivosPrescricao) {
		super(id, TiposPrescricao.PRESCRICAO_MEDULA.getId(), solicitacao, evolucao, usuario, arquivosPrescricao);
	}
	
	@Override
	public LocalDate menorDataColeta() {
		if (dataColeta1 == null && dataColeta2 == null) { 
			return null;
		}
		if (dataColeta1.isBefore(dataColeta2)) {
			return dataColeta1;
		}
		return dataColeta2;		
	}	

	
}
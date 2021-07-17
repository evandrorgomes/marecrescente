package br.org.cancer.redome.workup.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.org.cancer.redome.workup.exception.BusinessException;
import br.org.cancer.redome.workup.model.domain.TiposPrescricao;
import br.org.cancer.redome.workup.model.interfaces.ISolicitacao;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "PRESCRICAO")
@SequenceGenerator(name = "SQ_PRES_ID", sequenceName = "SQ_PRES_ID", allocationSize = 1)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "PRES_IN_TIPO", discriminatorType = DiscriminatorType.INTEGER)
@Data
@NoArgsConstructor
@ToString(includeFieldNames = true, onlyExplicitlyIncluded = true)
public abstract class Prescricao implements ISolicitacao, Serializable {
	
	private static final long serialVersionUID = -6308901615436817073L;

	/**
	 * Id para prescrição.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_PRES_ID")
	@Column(name = "PRES_ID")
	@ToString.Include
	private Long id;
	
	@Column(name = "PRES_IN_TIPO", insertable = false, updatable = false)
	@ToString.Include
	private Long tipoPrescricao;
	
	/**
	 * Referência para solicitação.
	 */
	@Column(name = "SOLI_ID")
	@ToString.Include
	private Long solicitacao;
	
	@Column(name = "EVOL_ID")
	@NotNull
	private Long evolucao;
	
	@Column(name = "MEDI_ID")
	@NotNull
	private Long medico;
	
	@OneToMany(mappedBy = "prescricao", cascade = CascadeType.ALL)
	private List<ArquivoPrescricao> arquivosPrescricao;
	
	@Column(name = "PRES_DT_CRIACAO")	
	@ToString.Include	
	private LocalDateTime dataCriacao = LocalDateTime.now();
		
	@Column(name = "CETR_ID")
	private Long centroTransplante;
	
	public Prescricao(Long id, Long tipoPrescricao, Long solicitacao, @NotNull Long evolucao, @NotNull Long medico,
			List<ArquivoPrescricao> arquivosPrescricao) {
		this.id = id;
		this.tipoPrescricao = tipoPrescricao;
		this.solicitacao = solicitacao;
		this.evolucao = evolucao;
		this.medico = medico;
		this.arquivosPrescricao = arquivosPrescricao;
	}	
	
	/**
	 * Verifica se tem algum arquivo de autorização do paciente.
	 * @return true se tem um arquivo de autorização do paciente.
	 */
	public Boolean temArquivoAutorizacao() {
		ArquivoPrescricao arquivo =  this.arquivosPrescricao.stream().filter(c -> c.getAutorizacaoPaciente()).findAny().orElse(null);
		return arquivo != null? true:false;
	}
	
	private static LocalDateTime $default$dataCriacao() {
		// TODO Auto-generated method stub
		return null;
	}

	public Boolean isPrescricaoMedula() {
		if (tipoPrescricao == null) {
			return null;
		}
		return TiposPrescricao.PRESCRICAO_MEDULA.getId().equals(tipoPrescricao);
	}
	
	public Boolean isPrescricaoCordao() {
		if (tipoPrescricao == null) {
			return null;
		}
		return TiposPrescricao.PRESCRICAO_CORDAO.getId().equals(tipoPrescricao);
	}
	
	public abstract LocalDate menorDataColeta();
	
	@Override
	@JsonIgnore
	public Long getIdCentroTransplante() {
		return getCentroTransplante();
	}
	
	@Override
	@JsonIgnore
	public Long getIdCentroColeta() {
		throw new BusinessException("erro.nao.implementado");
	}


}

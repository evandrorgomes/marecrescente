package br.org.cancer.redome.workup.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.org.cancer.redome.workup.exception.BusinessException;
import br.org.cancer.redome.workup.model.domain.TiposPedidoLogistica;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@DiscriminatorValue("3") 
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(includeFieldNames = true, onlyExplicitlyIncluded = true, callSuper = true)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PedidoLogisticaMaterialColetaNacional extends PedidoLogistica {
	
	private static final long serialVersionUID = 6599503358174558028L;

	@Column(name = "PELO_DT_EMBARQUE")
	private LocalDate dataEmbarque;
	
	@Column(name = "PELO_TX_JUSTIFICA_NAO_PROSSEGUIMENTO")
	private String justificativa;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "PECL_ID")
	private PedidoColeta pedidoColeta;

	@Column(name = "TRAN_ID")
	private Long transportadora;
	
	@Column(name = "PELO_HR_PREVISTA_RETIRADA")
	private LocalDateTime horaPrevistaRetirada;
	
	@Column(name = "PELO_IN_AEREO", insertable = false, updatable = false)
	private Boolean tipoAereo;
	
	@Column(name = "PELO_IN_AEREO")
	private Boolean aereo;
	
	/**
	 * @param id
	 * @param dataCriacao
	 * @param dataAtualizacao
	 * @param usuarioResponsavel
	 * @param tipo
	 * @param status
	 * @param solicitacao
	 */
	@Builder(toBuilder = true)
	public PedidoLogisticaMaterialColetaNacional(Long id, @NotNull LocalDateTime dataCriacao, LocalDateTime dataAtualizacao,
			Long usuarioResponsavel, StatusPedidoLogistica status, Long solicitacao,
			LocalDate dataEmbarque, PedidoColeta pedidoColeta, String justificativa, Long transportadora, LocalDateTime horaPrevistaRetirada,
			Boolean tipoAereo, Boolean aereo) {
		super(id, dataCriacao, dataAtualizacao, usuarioResponsavel, TiposPedidoLogistica.MATERIAL_NACIONAL.getId(), status, solicitacao);
		this.dataEmbarque = dataEmbarque;
		this.pedidoColeta = pedidoColeta;
		this.justificativa = justificativa;
		this.transportadora = transportadora;
		this.horaPrevistaRetirada = horaPrevistaRetirada;
		this.tipoAereo = tipoAereo;		
		this.aereo = aereo;
		if (this.aereo == null) {
			this.aereo = false;
		}
	}
	
	@Override
	public Long getSolicitacao() {
		if (pedidoColeta != null) {
			return pedidoColeta.getSolicitacao();
		}
		return null;
	}

	@Override
	@JsonIgnore
	public Long getIdCentroColeta() {
		throw new BusinessException("erro.nao.implementado");
	}
	
	@Override
	@JsonIgnore
	public Long getIdCentroTransplante() {
		throw new BusinessException("erro.nao.implementado");
	}
	
}

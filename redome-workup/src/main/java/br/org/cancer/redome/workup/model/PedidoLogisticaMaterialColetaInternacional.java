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
@DiscriminatorValue("4") 
@EqualsAndHashCode(callSuper = true)
@ToString(includeFieldNames = true, onlyExplicitlyIncluded = true, callSuper = true)
@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PedidoLogisticaMaterialColetaInternacional extends PedidoLogistica {
	
	private static final long serialVersionUID = -1793680394907029014L;

	@Column(name = "PELO_DT_EMBARQUE")
	private LocalDate dataEmbarque;

	@Column(name = "PELO_DT_CHEGADA")
	private LocalDate dataChegada;
	
	@Column(name = "PELO_TX_LOCAL_RETIRADA")
	private String localRetirada;

	@Column(name = "PELO_TX_HAWB")
	private String hawbInternacional;
	
	@Column(name = "PELO_TX_NOME_COURIER")
	private String nomeCourier;
	
	@Column(name = "PELO_TX_PASSAPORTE_COURIER")
	private String passaporteCourier;

	@Column(name = "PELO_TX_ID_DOADOR_LOCAL")
	private String identificacaLocalInternacional;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PECL_ID")
	private PedidoColeta pedidoColeta;
	
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

	/**
	 * @param id
	 * @param dataCriacao
	 * @param dataAtualizacao
	 * @param usuarioResponsavel
	 * @param tipo
	 * @param status
	 * @param solicitacao
	 * @param dataEmbarque
	 * @param dataChegada
	 * @param localRetirada
	 * @param hawbInternacional
	 * @param nomeCourier
	 * @param passaporteCourier
	 * @param identificacaLocalInternacional
	 * @param pedidoColeta
	 */
	@Builder
	public PedidoLogisticaMaterialColetaInternacional(Long id, @NotNull LocalDateTime dataCriacao,
			LocalDateTime dataAtualizacao, Long usuarioResponsavel, StatusPedidoLogistica status,
			Long solicitacao, LocalDate dataEmbarque, LocalDate dataChegada, String localRetirada,
			String hawbInternacional, String nomeCourier, String passaporteCourier,
			String identificacaLocalInternacional, PedidoColeta pedidoColeta) {
		super(id, dataCriacao, dataAtualizacao, usuarioResponsavel, TiposPedidoLogistica.MATERIAL_INTERNACIONAL.getId(), status, solicitacao);
		this.dataEmbarque = dataEmbarque;
		this.dataChegada = dataChegada;
		this.localRetirada = localRetirada;
		this.hawbInternacional = hawbInternacional;
		this.nomeCourier = nomeCourier;
		this.passaporteCourier = passaporteCourier;
		this.identificacaLocalInternacional = identificacaLocalInternacional;
		this.pedidoColeta = pedidoColeta;
	}
	
	
	
}

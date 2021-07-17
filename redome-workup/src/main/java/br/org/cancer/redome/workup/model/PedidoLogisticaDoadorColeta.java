package br.org.cancer.redome.workup.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

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
@DiscriminatorValue("2") 
@EqualsAndHashCode(callSuper = true)
@ToString(includeFieldNames = true, onlyExplicitlyIncluded = true, callSuper = true)
@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PedidoLogisticaDoadorColeta extends PedidoLogistica {
	
	private static final long serialVersionUID = -1793680394907029014L;

	@Column(name = "PELO_TX_OBSERVACAO")
	private String observacao;
	
	/**
	 * Transportes terrestre que foram necessários para a logística do doador.
	 */
	@OneToMany(mappedBy = "pedidoLogistica", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@Fetch(FetchMode.SUBSELECT)
	private List<TransporteTerrestre> transporteTerrestre;	
	
	/**
	 * Vouchers de viagem ou hospedagem que foram necessários para a logística do doador ou material.
	 */
	@OneToMany(mappedBy = "pedidoLogistica", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@Fetch(FetchMode.SUBSELECT)
	private List<ArquivoVoucherLogistica> vouchers;	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PECL_ID")
	private PedidoColeta pedidoColeta;
	
	@Column(name = "PELO_TX_JUSTIFICA_NAO_PROSSEGUIMENTO")
	private String justificativa;
	
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
	 * @param status
	 * @param solicitacao
	 */
	@Builder(toBuilder = true)
	public PedidoLogisticaDoadorColeta(Long id, @NotNull LocalDateTime dataCriacao, LocalDateTime dataAtualizacao,
			Long usuarioResponsavel, StatusPedidoLogistica status, Long solicitacao,
			String observacao, List<TransporteTerrestre> transporteTerrestre, List<ArquivoVoucherLogistica> vouchers,
			PedidoColeta pedidoColeta) {
		super(id, dataCriacao, dataAtualizacao, usuarioResponsavel, TiposPedidoLogistica.DOADOR_COLETA.getId(), status, solicitacao);
		this.observacao = observacao;
		this.transporteTerrestre = transporteTerrestre;
		this.vouchers = vouchers;
		this.pedidoColeta = pedidoColeta;
		
	}

	
	
	
}

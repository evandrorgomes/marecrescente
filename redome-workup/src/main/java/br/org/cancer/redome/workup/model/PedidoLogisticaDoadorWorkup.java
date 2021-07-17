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
@DiscriminatorValue("1") 
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(includeFieldNames = true, onlyExplicitlyIncluded = true, callSuper = true)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PedidoLogisticaDoadorWorkup extends PedidoLogistica {
	
	private static final long serialVersionUID = -5327210261765939361L;
	
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
	@JoinColumn(name = "PEWO_ID")
	private PedidoWorkup pedidoWorkup;

	/**
	 * @param id
	 * @param dataCriacao
	 * @param dataAtualizacao
	 * @param usuarioResponsavel
	 * @param tipo
	 * @param status
	 * @param observacao
	 * @param transporteTerrestre
	 * @param vouchers
	 * @param pedidoWorkup
	 */
	@Builder
	public PedidoLogisticaDoadorWorkup(Long id, @NotNull LocalDateTime dataCriacao, LocalDateTime dataAtualizacao,
			Long usuarioResponsavel, StatusPedidoLogistica status, String observacao,
			List<TransporteTerrestre> transporteTerrestre, List<ArquivoVoucherLogistica> vouchers,
			PedidoWorkup pedidoWorkup) {
		super(id, dataCriacao, dataAtualizacao, usuarioResponsavel, TiposPedidoLogistica.DOADOR_WORKUP.getId(), status,
				pedidoWorkup != null?pedidoWorkup.getSolicitacao():null);
		this.observacao = observacao;
		this.transporteTerrestre = transporteTerrestre;
		this.vouchers = vouchers;
		this.pedidoWorkup = pedidoWorkup;
	}
	
	@Override
	@JsonIgnore
	public Long getIdCentroColeta() {
		throw new BusinessException("erro.nao.implementado");
	}
	
	@Override
	@JsonIgnore
	public Long getIdCentroTransplante() {
		if (pedidoWorkup != null) {
			return pedidoWorkup.getCentroTransplante();
		}
		return null;
	}

	
	
}

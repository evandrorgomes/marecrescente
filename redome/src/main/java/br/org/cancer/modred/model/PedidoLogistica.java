package br.org.cancer.modred.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.PedidoTransporteView;
import br.org.cancer.modred.controller.view.TarefaLogisticaView;
import br.org.cancer.modred.controller.view.TarefaView;
import br.org.cancer.modred.controller.view.TransportadoraView;
import br.org.cancer.modred.model.domain.OrigemLogistica;
import br.org.cancer.modred.model.security.Usuario;

/**
 * Pedido de logística associado ao workup do doador.
 * 
 * @author Pizão
 */
@Entity
@Table(name = "PEDIDO_LOGISTICA")
@SequenceGenerator(name = "SQ_PELO_ID", sequenceName = "SQ_PELO_ID", allocationSize = 1)
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
public class PedidoLogistica implements Serializable {

	private static final long serialVersionUID = -8200479769690099391L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_PELO_ID")
	@Column(name = "PELO_ID")
	@JsonView(value = { TarefaView.Listar.class, TarefaLogisticaView.Listar.class })
	private Long id;

	@Column(name = "PELO_DT_CRIACAO")
	@NotNull
	private LocalDateTime dataCriacao;

	@Column(name = "PELO_DT_ATUALIZACAO")
	private LocalDateTime dataAtualizacao;

	@Column(name = "PELO_TX_OBSERVACAO")
	private String observacao;

	@Column(name = "PELO_DT_EMBARQUE")
	private LocalDate dataEmbarque;

	@Column(name = "PELO_DT_CHEGADA")
	private LocalDate dataChegada;
	
	@Column(name = "PELO_TX_LOCAL_RETIRADA")
	private String localRetirada;
	
	@ManyToOne
	@JoinColumn(name = "USUA_ID_RESPONSAVEL")
	@JsonView(value = { TarefaView.Listar.class, TarefaLogisticaView.Listar.class })
	private Usuario usuarioResponsavel;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PEWO_ID")
	@JsonView(value = { TarefaView.Listar.class, TarefaLogisticaView.Listar.class})
	private PedidoWorkup pedidoWorkup;

	@ManyToOne
	@JoinColumn(name = "PELO_IN_TIPO")
	@JsonView(value=TarefaLogisticaView.Listar.class)
	private TipoPedidoLogistica tipo;

	@ManyToOne
	@JoinColumn(name = "STPL_ID")
	@JsonView(value=TarefaLogisticaView.Listar.class)
	private StatusPedidoLogistica status;
	
	@Column(name = "TIPL_IN_ORIGEM")
	@NotNull
	@Enumerated(EnumType.STRING)
	private OrigemLogistica origem;
	

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

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	@JoinColumn(name = "PECL_ID")
	@JsonView(value = { TarefaLogisticaView.Listar.class, TransportadoraView.Listar.class, 
			PedidoTransporteView.Detalhe.class, TransportadoraView.AgendarTransporte.class })
	private PedidoColeta pedidoColeta;

	@OneToOne(mappedBy = "pedidoLogistica")
	@JsonView(value = { TransportadoraView.Listar.class, PedidoTransporteView.Detalhe.class,
			TransportadoraView.AgendarTransporte.class })
	//@NotNull
//	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private PedidoTransporte pedidoTransporte;

	
	@JsonView(value = {PedidoTransporteView.Detalhe.class, TransportadoraView.AgendarTransporte.class })
	@Column(name = "PELO_TX_ID_DOADOR_LOCAL")
	private String identificacaLocalInternacional;
	
	@JsonView(value = {PedidoTransporteView.Detalhe.class, TransportadoraView.AgendarTransporte.class })
	@Column(name = "PELO_TX_HAWB")
	private String hawbInternacional;
	
	@JsonView(value = {PedidoTransporteView.Detalhe.class, TransportadoraView.AgendarTransporte.class })
	@Column(name = "PELO_TX_NOME_COURIER")
	private String nomeCourier;
	
	@JsonView(value = {PedidoTransporteView.Detalhe.class, TransportadoraView.AgendarTransporte.class })
	@Column(name = "PELO_TX_PASSAPORTE_COURIER")
	private String passaporteCourier;
	
	
	public PedidoLogistica() {
		super();
		this.dataCriacao = LocalDateTime.now();
	}

	/**
	 * Construtor para facilitar a criação do pedido logística, já com o ID informado.
	 * 
	 * @param id ID do pedido logística.
	 */
	public PedidoLogistica(Long id) {
		super();
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(LocalDateTime dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public LocalDateTime getDataAtualizacao() {
		return dataAtualizacao;
	}

	public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}

	public Usuario getUsuarioResponsavel() {
		return usuarioResponsavel;
	}

	public void setUsuarioResponsavel(Usuario usuarioResponsavel) {
		this.usuarioResponsavel = usuarioResponsavel;
	}

	public PedidoWorkup getPedidoWorkup() {
		return pedidoWorkup;
	}

	public void setPedidoWorkup(PedidoWorkup pedidoWorkup) {
		this.pedidoWorkup = pedidoWorkup;
	}

	public TipoPedidoLogistica getTipo() {
		return tipo;
	}

	public void setTipo(TipoPedidoLogistica tipo) {
		this.tipo = tipo;
	}

	public StatusPedidoLogistica getStatus() {
		return status;
	}

	public void setStatus(StatusPedidoLogistica status) {
		this.status = status;
	}

	public List<ArquivoVoucherLogistica> getVouchers() {
		return vouchers;
	}

	public void setVouchers(List<ArquivoVoucherLogistica> vouchers) {
		this.vouchers = vouchers;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public List<TransporteTerrestre> getTransporteTerrestre() {
		return transporteTerrestre;
	}

	public void setTransporteTerrestre(List<TransporteTerrestre> transporteTerrestre) {
		this.transporteTerrestre = transporteTerrestre;
	}

	/**
	 * @return the pedidoColeta
	 */
	public PedidoColeta getPedidoColeta() {
		return pedidoColeta;
	}

	/**
	 * @param pedidoColeta the pedidoColeta to set
	 */
	public void setPedidoColeta(PedidoColeta pedidoColeta) {
		this.pedidoColeta = pedidoColeta;
	}

	/**
	 * @return the identificacaLocalInternacional
	 */
	public String getIdentificacaLocalInternacional() {
		return identificacaLocalInternacional;
	}

	/**
	 * @param identificacaLocalInternacional the identificacaLocalInternacional to set
	 */
	public void setIdentificacaLocalInternacional(String identificacaLocalInternacional) {
		this.identificacaLocalInternacional = identificacaLocalInternacional;
	}

	/**
	 * @return the hawbInternacional
	 */
	public String getHawbInternacional() {
		return hawbInternacional;
	}

	/**
	 * @param hawbInternacional the hawbInternacional to set
	 */
	public void setHawbInternacional(String hawbInternacional) {
		this.hawbInternacional = hawbInternacional;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ( ( dataAtualizacao == null ) ? 0 : dataAtualizacao.hashCode() );
		result = prime * result + ( ( dataCriacao == null ) ? 0 : dataCriacao.hashCode() );
		result = prime * result + ( ( id == null ) ? 0 : id.hashCode() );
		result = prime * result + ( ( observacao == null ) ? 0 : observacao.hashCode() );
		result = prime * result + ( ( pedidoColeta == null ) ? 0 : pedidoColeta.hashCode() );
		result = prime * result + ( ( pedidoWorkup == null ) ? 0 : pedidoWorkup.hashCode() );
		result = prime * result + ( ( status == null ) ? 0 : status.hashCode() );
		result = prime * result + ( ( tipo == null ) ? 0 : tipo.hashCode() );
		result = prime * result + ( ( transporteTerrestre == null ) ? 0 : transporteTerrestre.hashCode() );
		result = prime * result + ( ( usuarioResponsavel == null ) ? 0 : usuarioResponsavel.hashCode() );
		result = prime * result + ( ( vouchers == null ) ? 0 : vouchers.hashCode() );
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!( obj instanceof PedidoLogistica )) {
			return false;
		}
		PedidoLogistica other = (PedidoLogistica) obj;
		if (dataAtualizacao == null) {
			if (other.dataAtualizacao != null) {
				return false;
			}
		}
		else
			if (!dataAtualizacao.equals(other.dataAtualizacao)) {
				return false;
			}
		if (dataCriacao == null) {
			if (other.dataCriacao != null) {
				return false;
			}
		}
		else
			if (!dataCriacao.equals(other.dataCriacao)) {
				return false;
			}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		}
		else
			if (!id.equals(other.id)) {
				return false;
			}
		if (observacao == null) {
			if (other.observacao != null) {
				return false;
			}
		}
		else
			if (!observacao.equals(other.observacao)) {
				return false;
			}
		if (pedidoColeta == null) {
			if (other.pedidoColeta != null) {
				return false;
			}
		}
		else
			if (!pedidoColeta.equals(other.pedidoColeta)) {
				return false;
			}
		if (pedidoWorkup == null) {
			if (other.pedidoWorkup != null) {
				return false;
			}
		}
		else
			if (!pedidoWorkup.equals(other.pedidoWorkup)) {
				return false;
			}
		if (status == null) {
			if (other.status != null) {
				return false;
			}
		}
		else
			if (!status.equals(other.status)) {
				return false;
			}
		if (tipo == null) {
			if (other.tipo != null) {
				return false;
			}
		}
		else
			if (!tipo.equals(other.tipo)) {
				return false;
			}
		if (transporteTerrestre == null) {
			if (other.transporteTerrestre != null) {
				return false;
			}
		}
		else
			if (!transporteTerrestre.equals(other.transporteTerrestre)) {
				return false;
			}
		if (usuarioResponsavel == null) {
			if (other.usuarioResponsavel != null) {
				return false;
			}
		}
		else
			if (!usuarioResponsavel.equals(other.usuarioResponsavel)) {
				return false;
			}
		if (vouchers == null) {
			if (other.vouchers != null) {
				return false;
			}
		}
		else
			if (!vouchers.equals(other.vouchers)) {
				return false;
			}
		return true;
	}

	/**
	 * Verifica se a logística é de workup ou de coleta para o doador.
	 * 
	 * @return TRUE se for logística de workup, FALSE se de coleta.
	 */
	public boolean isLogisticaWorkup() {
		if (pedidoWorkup != null) {
			return Boolean.TRUE;
		}
		else
			if (pedidoColeta != null) {
				return Boolean.FALSE;
			}
		throw new IllegalStateException("Logística não possui workup e nem coleta informados.");
	}

	/**
	 * @return the pedidoTransporte
	 */
	public PedidoTransporte getPedidoTransporte() {
		return pedidoTransporte;
	}

	/**
	 * @param pedidoTransporte the pedidoTransporte to set
	 */
	public void setPedidoTransporte(PedidoTransporte pedidoTransporte) {
		this.pedidoTransporte = pedidoTransporte;
	}

	/**
	 * Define a origem do tipo de logística.
	 * 
	 * @return enum da origem.
	 */
	public OrigemLogistica getOrigem() {
		return origem;
	}

	public void setOrigem(OrigemLogistica origem) {
		this.origem = origem;
	}

	/**
	 * Nome do courier responsável pelo retirada do material internacional.
	 * @return nome do courier.
	 */
	public String getNomeCourier() {
		return nomeCourier;
	}

	public void setNomeCourier(String nomeCourier) {
		this.nomeCourier = nomeCourier;
	}

	/**
	 * Número do passaporte do courier responsável pelo retirada do 
	 * material internacional.
	 * @return número do passaporte.
	 */
	public String getPassaporteCourier() {
		return passaporteCourier;
	}

	public void setPassaporteCourier(String passaporteCourier) {
		this.passaporteCourier = passaporteCourier;
	}

	/**
	 * @return the dataEmbarque
	 */
	public LocalDate getDataEmbarque() {
		return dataEmbarque;
	}

	/**
	 * @param dataEmbarque the dataEmbarque to set
	 */
	public void setDataEmbarque(LocalDate dataEmbarque) {
		this.dataEmbarque = dataEmbarque;
	}

	/**
	 * @return the dataChegada
	 */
	public LocalDate getDataChegada() {
		return dataChegada;
	}

	/**
	 * @param dataChegada the dataChegada to set
	 */
	public void setDataChegada(LocalDate dataChegada) {
		this.dataChegada = dataChegada;
	}

	/**
	 * @return the localRetirada
	 */
	public String getLocalRetirada() {
		return localRetirada;
	}

	/**
	 * @param localRetirada the localRetirada to set
	 */
	public void setLocalRetirada(String localRetirada) {
		this.localRetirada = localRetirada;
	}
	
}
package br.org.cancer.modred.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Proxy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.PedidoTransporteView;
import br.org.cancer.modred.controller.view.TarefaLogisticaView;
import br.org.cancer.modred.controller.view.TransportadoraView;
import br.org.cancer.modred.model.security.Usuario;

/**
 * Pedido de transporte de material gerado para um processo de logística. Neste pedido são definidas a data de retirada e a
 * transportadora responsável por levar o material até o centro de transplante.
 * 
 * @author Pizão.
 */
@Entity
@SequenceGenerator(name = "SQ_PETR_ID", sequenceName = "SQ_PETR_ID", allocationSize = 1)
@Table(name = "PEDIDO_TRANSPORTE")
@Proxy(lazy = false)
public class PedidoTransporte implements Serializable {

	private static final long serialVersionUID = -6199054113331385050L;

	@Id
	@Column(name = "PETR_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_PETR_ID")
	@JsonView(value = { TransportadoraView.Listar.class, PedidoTransporteView.Detalhe.class,
						TransportadoraView.AgendarTransporte.class })
	private Long id;

	@NotNull
	@Column(name = "PETR_DT_ATUALIZACAO")
	private LocalDateTime dataAtualizacao;

//	@CustomFuture(today = true)
	@Column(name = "PETR_HR_PREVISTA_RETIRADA")
	@JsonView(value = { TransportadoraView.Listar.class, PedidoTransporteView.Detalhe.class,
			TransportadoraView.AgendarTransporte.class, TarefaLogisticaView.Listar.class })
	private LocalDateTime horaPrevistaRetirada;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USUA_ID_RESPONSAVEL")
	private Usuario usuarioResponsavel;

	//@ManyToOne(fetch = FetchType.LAZY)
	@Column(name = "TRAN_ID")
	//@JsonView(value = { PedidoTransporteView.Detalhe.class, TarefaLogisticaView.Listar.class })
	private Long transportadora;

	@OneToMany(mappedBy = "pedidoTransporte", cascade = CascadeType.ALL)
	@JsonView(value = { TransportadoraView.AgendarTransporte.class, PedidoTransporteView.Detalhe.class })
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private List<ArquivoPedidoTransporte> arquivoPedidoTransporte;

	@OneToOne
	@JoinColumn(name = "PELO_ID")
	@JsonView(TarefaLogisticaView.Listar.class)
	private PedidoLogistica pedidoLogistica;
	
	@ManyToOne
	@JoinColumn(name = "STPT_ID")
	@JsonView(value = { TransportadoraView.Listar.class, TransportadoraView.AgendarTransporte.class,
						TarefaLogisticaView.Listar.class })
	@NotNull
	private StatusPedidoTransporte status;

	@Column(name = "PETR_DADOS_VOO")
	@JsonView(value = { TransportadoraView.AgendarTransporte.class })
	private String dadosVoo;

	//@ManyToOne(fetch = FetchType.EAGER)
	@Column(name = "COUR_ID")
	@JsonView(value = { TransportadoraView.AgendarTransporte.class })
	private Long courier;

	@Column(name = "PETR_DT_RETIRADA")
	@JsonView(value = { TransportadoraView.Listar.class, TransportadoraView.AgendarTransporte.class })
	private LocalDateTime dataRetirada;

	@Column(name = "PETR_DT_ENTREGA")
	@JsonView(value = { TransportadoraView.Listar.class, TransportadoraView.AgendarTransporte.class })
	private LocalDateTime dataEntrega;
	
	
	/**
	 * Propriedade utilizada no método de confirmar o transporte e no metodo de criar o log 
	 *  para informar o status anterior. 
	 */
	@Transient
	private StatusPedidoTransporte statusAnterior;
	
	
	

	public PedidoTransporte(Long id, LocalDateTime dataAtualizacao, LocalDateTime horaPrevistaRetirada,
			Long idUsuario, Long idTransportadora,  Long idPedidoLogistica,
			Long idStatus, String dadosVoo, Long idCourier, LocalDateTime dataRetirada,
			LocalDateTime dataEntrega) {
		super();
		this.id = id;
		this.dataAtualizacao = dataAtualizacao;
		this.horaPrevistaRetirada = horaPrevistaRetirada;
		this.usuarioResponsavel = new Usuario(idUsuario);
		this.transportadora = idTransportadora;
		this.pedidoLogistica = new PedidoLogistica(idPedidoLogistica);
		this.status = new StatusPedidoTransporte(idStatus);
		this.dadosVoo = dadosVoo;
		this.courier = idCourier;
		this.dataRetirada = dataRetirada;
		this.dataEntrega = dataEntrega;
	}

	
	
	public PedidoTransporte() {
	}



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public LocalDateTime getDataAtualizacao() {
		return dataAtualizacao;
	}



	public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}



	public LocalDateTime getHoraPrevistaRetirada() {
		return horaPrevistaRetirada;
	}



	public void setHoraPrevistaRetirada(LocalDateTime horaPrevistaRetirada) {
		this.horaPrevistaRetirada = horaPrevistaRetirada;
	}



	public Usuario getUsuarioResponsavel() {
		return usuarioResponsavel;
	}



	public void setUsuarioResponsavel(Usuario usuarioResponsavel) {
		this.usuarioResponsavel = usuarioResponsavel;
	}



	public Long getTransportadora() {
		return transportadora;
	}



	public void setTransportadora(Long transportadora) {
		this.transportadora = transportadora;
	}



	public List<ArquivoPedidoTransporte> getArquivoPedidoTransporte() {
		return arquivoPedidoTransporte;
	}



	public void setArquivoPedidoTransporte(List<ArquivoPedidoTransporte> arquivoPedidoTransporte) {
		this.arquivoPedidoTransporte = arquivoPedidoTransporte;
	}



	public PedidoLogistica getPedidoLogistica() {
		return pedidoLogistica;
	}



	public void setPedidoLogistica(PedidoLogistica pedidoLogistica) {
		this.pedidoLogistica = pedidoLogistica;
	}



	public StatusPedidoTransporte getStatus() {
		return status;
	}



	public void setStatus(StatusPedidoTransporte status) {
		this.status = status;
	}



	public String getDadosVoo() {
		return dadosVoo;
	}



	public void setDadosVoo(String dadosVoo) {
		this.dadosVoo = dadosVoo;
	}



	public Long getCourier() {
		return courier;
	}



	public void setCourier(Long courier) {
		this.courier = courier;
	}



	public LocalDateTime getDataRetirada() {
		return dataRetirada;
	}



	public void setDataRetirada(LocalDateTime dataRetirada) {
		this.dataRetirada = dataRetirada;
	}



	public LocalDateTime getDataEntrega() {
		return dataEntrega;
	}



	public void setDataEntrega(LocalDateTime dataEntrega) {
		this.dataEntrega = dataEntrega;
	}
	
	/**
	 * @return the statusAnterior
	 */
	public StatusPedidoTransporte getStatusAnterior() {
		return statusAnterior;
	}

	/**
	 * @param statusAnterior the statusAnterior to set
	 */
	public void setStatusAnterior(StatusPedidoTransporte statusAnterior) {
		this.statusAnterior = statusAnterior;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((courier == null) ? 0 : courier.hashCode());
		result = prime * result + ((dadosVoo == null) ? 0 : dadosVoo.hashCode());
		result = prime * result + ((dataAtualizacao == null) ? 0 : dataAtualizacao.hashCode());
		result = prime * result + ((dataEntrega == null) ? 0 : dataEntrega.hashCode());
		result = prime * result + ((dataRetirada == null) ? 0 : dataRetirada.hashCode());
		result = prime * result + ((horaPrevistaRetirada == null) ? 0 : horaPrevistaRetirada.hashCode());
		result = prime * result + ((pedidoLogistica == null) ? 0 : pedidoLogistica.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((transportadora == null) ? 0 : transportadora.hashCode());
		result = prime * result + ((usuarioResponsavel == null) ? 0 : usuarioResponsavel.hashCode());
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PedidoTransporte other = (PedidoTransporte) obj;
		if (courier == null) {
			if (other.courier != null)
				return false;
		} else if (!courier.equals(other.courier))
			return false;
		if (dadosVoo == null) {
			if (other.dadosVoo != null)
				return false;
		} else if (!dadosVoo.equals(other.dadosVoo))
			return false;
		if (dataAtualizacao == null) {
			if (other.dataAtualizacao != null)
				return false;
		} else if (!dataAtualizacao.equals(other.dataAtualizacao))
			return false;
		if (dataEntrega == null) {
			if (other.dataEntrega != null)
				return false;
		} else if (!dataEntrega.equals(other.dataEntrega))
			return false;
		if (dataRetirada == null) {
			if (other.dataRetirada != null)
				return false;
		} else if (!dataRetirada.equals(other.dataRetirada))
			return false;
		if (horaPrevistaRetirada == null) {
			if (other.horaPrevistaRetirada != null)
				return false;
		} else if (!horaPrevistaRetirada.equals(other.horaPrevistaRetirada))
			return false;
		if (pedidoLogistica == null) {
			if (other.pedidoLogistica != null)
				return false;
		} else if (!pedidoLogistica.equals(other.pedidoLogistica))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (transportadora == null) {
			if (other.transportadora != null)
				return false;
		} else if (!transportadora.equals(other.transportadora))
			return false;
		if (usuarioResponsavel == null) {
			if (other.usuarioResponsavel != null)
				return false;
		} else if (!usuarioResponsavel.equals(other.usuarioResponsavel))
			return false;
		return true;
	}
	
	
	
}
package br.org.cancer.modred.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.envers.NotAudited;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.BuscaView;
import br.org.cancer.modred.controller.view.PedidoColetaView;
import br.org.cancer.modred.controller.view.PedidoTransporteView;
import br.org.cancer.modred.controller.view.TarefaLogisticaView;
import br.org.cancer.modred.controller.view.TarefaView;
import br.org.cancer.modred.controller.view.TransportadoraView;
import br.org.cancer.modred.model.security.Usuario;
import br.org.cancer.modred.service.impl.custom.EntityObservable;
import br.org.cancer.modred.service.impl.observers.PedidoColetaAgendadoObserver;

/**
 * Classe de persistencia de um pedido de coleta.
 * 
 * @author bruno.sousa
 *
 */
@Entity
@SequenceGenerator(name = "SQ_PECL_ID", sequenceName = "SQ_PECL_ID", allocationSize = 1)
@Table(name="PEDIDO_COLETA")
public class PedidoColeta extends EntityObservable implements Serializable {

	private static final long serialVersionUID = 5170101253604507919L;

	/**
	 * Identificador do pedido de coleta.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_PECL_ID")
	@Column(name = "PECL_ID")
	@JsonView(value = {TarefaView.Listar.class, PedidoColetaView.DetalheColeta.class, TarefaLogisticaView.Listar.class, 
			PedidoColetaView.AgendamentoColeta.class, PedidoColetaView.SugerirDataColeta.class})
	private Long id;

	/**
	 * Usuário responsável pelo pedido de coleta.
	 */
	@ManyToOne
	@JoinColumn(name = "USUA_ID")
	private Usuario usuario;

	/**
	 * Data da criação do pedido.
	 */
	@Column(name = "PECL_DT_CRIACAO")
	@NotNull
	private LocalDateTime dataCriacao;

	/**
	 * Data em que o doador deverá estar disponível no centro de coleta.
	 */
	@Column(name = "PECL_DT_DISPONIBILIDADE_DOADOR")
	@JsonView(value = {PedidoColetaView.DetalheColeta.class, TarefaLogisticaView.Listar.class,  
			TarefaView.Listar.class})
	private LocalDate dataDisponibilidadeDoador;

	/**
	 * Data em que o doador será liberado pelo centro de coleta.
	 */
	@Column(name = "PECL_DT_LIBERACAO_DOADOR")
	@JsonView(value = {PedidoColetaView.DetalheColeta.class})
	private LocalDate dataLiberacaoDoador;

	/**
	 * Indica se necessita de logistica para o doador.
	 */
	@Column(name = "PECL_IN_LOGISTICA_DOADOR")
	@JsonView(PedidoColetaView.DetalheColeta.class)
	private Boolean necessitaLogisticaDoador;

	/**
	 * Indica se necessita de logistica para o material.
	 */
	@Column(name = "PECL_IN_LOGISTICA_MATERIAL")
	@JsonView(PedidoColetaView.DetalheColeta.class)
	private Boolean necessitaLogisticaMaterial;

	/**
	 * Identificador do pedido de workup.
	 */
	@ManyToOne
	@JoinColumn(name = "PEWO_ID")
	@JsonView({	TarefaView.Listar.class, TarefaLogisticaView.Listar.class, 
				PedidoColetaView.DetalheColeta.class, 
				TransportadoraView.Listar.class,
				TransportadoraView.AgendarTransporte.class, PedidoColetaView.AgendamentoColeta.class})
	private PedidoWorkup pedidoWorkup;

	/**
	 * Centro de transplante responsável pela coleta.
	 */
	@ManyToOne
	@JoinColumn(name = "CETR_ID_COLETA")
	@JsonView(value = { PedidoColetaView.DetalheColeta.class, TarefaView.Listar.class, 
			PedidoTransporteView.Detalhe.class })
	private CentroTransplante centroColeta;
	
	/**
	 * Data da ultima alteração do status.
	 */
	@Column(name = "PECL_DT_ULTIMO_STATUS")
	@NotNull
	private LocalDate dataUltimoStatus;
	
	/**
	 * Status do pedido.
	 */
	@ManyToOne
	@JoinColumn(name = "STPC_ID")
	@JsonView(value = { TarefaView.Listar.class, PedidoColetaView.AgendamentoColeta.class,
			PedidoColetaView.DetalheColeta.class})
	private StatusPedidoColeta statusPedidoColeta;
	
	@ManyToOne
	@JoinColumn(name = "SOLI_ID")
	@JsonView({ PedidoColetaView.AgendamentoColeta.class, PedidoColetaView.DetalheColeta.class,
			PedidoColetaView.SugerirDataColeta.class, TarefaLogisticaView.Listar.class, 
			TransportadoraView.Listar.class, TransportadoraView.AgendarTransporte.class })
	private Solicitacao solicitacao;
	
	@Column(name = "PECL_DT_COLETA")
	@JsonView(value = {PedidoColetaView.DetalheColeta.class, TarefaLogisticaView.Listar.class
			, TarefaView.Listar.class, TransportadoraView.Listar.class
			, BuscaView.AvaliacaoPedidoColeta.class})
	private LocalDate dataColeta;
	
	/**
	 * Referência para disponibilidades.
	 */
	@OneToMany(mappedBy = "pedidoColeta")
	@NotAudited
	private List<Disponibilidade> disponibilidades;
	
	/**
	 * Guarda a última disponibilidade disponível.
	 * 
	 */
	@Transient
	@JsonView(PedidoColetaView.DetalheColeta.class)
	private Disponibilidade ultimaDisponibilidade;
	
	/**
	 * Motivo do cancelamento, caso ocorra.
	 */
	@ManyToOne
	@JoinColumn(name = "MOCC_TX_CODIGO")
	private MotivoCancelamentoColeta motivoCancelamento;

	
	/**
	 * Doador envolvido na coleta.
	 */
	@Transient
	@JsonView(TarefaLogisticaView.Listar.class)
	private Doador doador;

	public PedidoColeta() {
		super();
		addObserver(PedidoColetaAgendadoObserver.class);
	}

	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	
	/**
	 * @return the usuario
	 */
	public Usuario getUsuario() {
		return usuario;
	}

	
	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	
	/**
	 * @return the dataCriacao
	 */
	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}

	
	/**
	 * @param dataCriacao the dataCriacao to set
	 */
	public void setDataCriacao(LocalDateTime dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	
	/**
	 * @return the dataDisponibilidadeDoador
	 */
	public LocalDate getDataDisponibilidadeDoador() {
		return dataDisponibilidadeDoador;
	}

	
	/**
	 * @param dataDisponibilidadeDoador the dataDisponibilidadeDoador to set
	 */
	public void setDataDisponibilidadeDoador(LocalDate dataDisponibilidadeDoador) {
		this.dataDisponibilidadeDoador = dataDisponibilidadeDoador;
	}

	
	/**
	 * @return the dataLiberacaoDoador
	 */
	public LocalDate getDataLiberacaoDoador() {
		return dataLiberacaoDoador;
	}

	
	/**
	 * @param dataLiberacaoDoador the dataLiberacaoDoador to set
	 */
	public void setDataLiberacaoDoador(LocalDate dataLiberacaoDoador) {
		this.dataLiberacaoDoador = dataLiberacaoDoador;
	}

	
	/**
	 * @return the necessitaLogisticaDoador
	 */
	public Boolean getNecessitaLogisticaDoador() {
		return necessitaLogisticaDoador;
	}

	
	/**
	 * @param necessitaLogisticaDoador the necessitaLogisticaDoador to set
	 */
	public void setNecessitaLogisticaDoador(Boolean necessitaLogisticaDoador) {
		this.necessitaLogisticaDoador = necessitaLogisticaDoador;
	}

	
	/**
	 * @return the necessitaLogisticaMaterial
	 */
	public Boolean getNecessitaLogisticaMaterial() {
		return necessitaLogisticaMaterial;
	}

	
	/**
	 * @param necessitaLogisticaMaterial the necessitaLogisticaMaterial to set
	 */
	public void setNecessitaLogisticaMaterial(Boolean necessitaLogisticaMaterial) {
		this.necessitaLogisticaMaterial = necessitaLogisticaMaterial;
	}
	
	/**
	 * @return the centroColeta
	 */
	public CentroTransplante getCentroColeta() {
		return centroColeta;
	}

	
	/**
	 * @param centroColeta the centroColeta to set
	 */
	public void setCentroColeta(CentroTransplante centroColeta) {
		this.centroColeta = centroColeta;
	}

	
	/**
	 * @return the dataUltimoStatus
	 */
	public LocalDate getDataUltimoStatus() {
		return dataUltimoStatus;
	}

	
	/**
	 * @param dataUltimoStatus the dataUltimoStatus to set
	 */
	public void setDataUltimoStatus(LocalDate dataUltimoStatus) {
		this.dataUltimoStatus = dataUltimoStatus;
	}

	
	/**
	 * @return the statusPedidoColeta
	 */
	public StatusPedidoColeta getStatusPedidoColeta() {
		return statusPedidoColeta;
	}

	
	/**
	 * @param statusPedidoColeta the statusPedidoColeta to set
	 */
	public void setStatusPedidoColeta(StatusPedidoColeta statusPedidoColeta) {
		this.statusPedidoColeta = statusPedidoColeta;
	}

	public Doador getDoador() {
		return doador;
	}


	public void setDoador(Doador doador) {
		this.doador = doador;
	}


	public Solicitacao getSolicitacao() {
		return solicitacao;
	}


	public void setSolicitacao(Solicitacao solicitacao) {
		this.solicitacao = solicitacao;
	}


	public PedidoWorkup getPedidoWorkup() {
		return pedidoWorkup;
	}


	public void setPedidoWorkup(PedidoWorkup pedidoWorkup) {
		this.pedidoWorkup = pedidoWorkup;
	}
	
	/**
	 * @return the dataCoelta
	 */
	public LocalDate getDataColeta() {
		return dataColeta;
	}
	
	/**
	 * @param dataCoelta the dataCoelta to set
	 */
	public void setDataColeta(LocalDate dataColeta) {
		this.dataColeta = dataColeta;
	}
	
	/**
	 * @return the disponibilidades
	 */
	public List<Disponibilidade> getDisponibilidades() {
		return disponibilidades;
	}
	
	/**
	 * @param disponibilidades the disponibilidades to set
	 */
	public void setDisponibilidades(List<Disponibilidade> disponibilidades) {
		this.disponibilidades = disponibilidades;
	}
	
	/**
	 * @return the ultimaDisponibilidade
	 */
	public Disponibilidade getUltimaDisponibilidade() {
		return ultimaDisponibilidade;
	}
	
	/**
	 * @param ultimaDisponibilidade the ultimaDisponibilidade to set
	 */
	public void setUltimaDisponibilidade(Disponibilidade ultimaDisponibilidade) {
		this.ultimaDisponibilidade = ultimaDisponibilidade;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ( ( centroColeta == null ) ? 0 : centroColeta.hashCode() );
		result = prime * result + ( ( dataColeta == null ) ? 0 : dataColeta.hashCode() );
		result = prime * result + ( ( dataCriacao == null ) ? 0 : dataCriacao.hashCode() );
		result = prime * result + ( ( dataDisponibilidadeDoador == null ) ? 0 : dataDisponibilidadeDoador.hashCode() );
		result = prime * result + ( ( dataLiberacaoDoador == null ) ? 0 : dataLiberacaoDoador.hashCode() );
		result = prime * result + ( ( dataUltimoStatus == null ) ? 0 : dataUltimoStatus.hashCode() );
		result = prime * result + ( ( disponibilidades == null ) ? 0 : disponibilidades.hashCode() );
		result = prime * result + ( ( id == null ) ? 0 : id.hashCode() );
		result = prime * result + ( ( necessitaLogisticaDoador == null ) ? 0 : necessitaLogisticaDoador.hashCode() );
		result = prime * result + ( ( necessitaLogisticaMaterial == null ) ? 0 : necessitaLogisticaMaterial.hashCode() );
		result = prime * result + ( ( pedidoWorkup == null ) ? 0 : pedidoWorkup.hashCode() );
		result = prime * result + ( ( solicitacao == null ) ? 0 : solicitacao.hashCode() );
		result = prime * result + ( ( statusPedidoColeta == null ) ? 0 : statusPedidoColeta.hashCode() );
		result = prime * result + ( ( usuario == null ) ? 0 : usuario.hashCode() );
		return result;
	}


	/* (non-Javadoc)
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
		if (!( obj instanceof PedidoColeta )) {
			return false;
		}
		PedidoColeta other = (PedidoColeta) obj;
		if (centroColeta == null) {
			if (other.centroColeta != null) {
				return false;
			}
		}
		else
			if (!centroColeta.equals(other.centroColeta)) {
				return false;
			}
		if (dataColeta == null) {
			if (other.dataColeta != null) {
				return false;
			}
		}
		else
			if (!dataColeta.equals(other.dataColeta)) {
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
		if (dataDisponibilidadeDoador == null) {
			if (other.dataDisponibilidadeDoador != null) {
				return false;
			}
		}
		else
			if (!dataDisponibilidadeDoador.equals(other.dataDisponibilidadeDoador)) {
				return false;
			}
		if (dataLiberacaoDoador == null) {
			if (other.dataLiberacaoDoador != null) {
				return false;
			}
		}
		else
			if (!dataLiberacaoDoador.equals(other.dataLiberacaoDoador)) {
				return false;
			}
		if (dataUltimoStatus == null) {
			if (other.dataUltimoStatus != null) {
				return false;
			}
		}
		else
			if (!dataUltimoStatus.equals(other.dataUltimoStatus)) {
				return false;
			}
		if (disponibilidades == null) {
			if (other.disponibilidades != null) {
				return false;
			}
		}
		else
			if (!disponibilidades.equals(other.disponibilidades)) {
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
		if (necessitaLogisticaDoador == null) {
			if (other.necessitaLogisticaDoador != null) {
				return false;
			}
		}
		else
			if (!necessitaLogisticaDoador.equals(other.necessitaLogisticaDoador)) {
				return false;
			}
		if (necessitaLogisticaMaterial == null) {
			if (other.necessitaLogisticaMaterial != null) {
				return false;
			}
		}
		else
			if (!necessitaLogisticaMaterial.equals(other.necessitaLogisticaMaterial)) {
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
		if (solicitacao == null) {
			if (other.solicitacao != null) {
				return false;
			}
		}
		else
			if (!solicitacao.equals(other.solicitacao)) {
				return false;
			}
		if (statusPedidoColeta == null) {
			if (other.statusPedidoColeta != null) {
				return false;
			}
		}
		else
			if (!statusPedidoColeta.equals(other.statusPedidoColeta)) {
				return false;
			}
		if (usuario == null) {
			if (other.usuario != null) {
				return false;
			}
		}
		else
			if (!usuario.equals(other.usuario)) {
				return false;
			}
		return true;
	}


	public MotivoCancelamentoColeta getMotivoCancelamento() {
		return motivoCancelamento;
	}

	
	public void setMotivoCancelamento(MotivoCancelamentoColeta motivoCancelamento) {
		this.motivoCancelamento = motivoCancelamento;
	}

	
}
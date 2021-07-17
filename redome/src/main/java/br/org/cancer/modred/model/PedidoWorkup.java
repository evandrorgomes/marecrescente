package br.org.cancer.modred.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.AvaliacaoPedidoColetaView;
import br.org.cancer.modred.controller.view.AvaliacaoResultadoWorkupView;
import br.org.cancer.modred.controller.view.BuscaView;
import br.org.cancer.modred.controller.view.DisponibilidadeView;
import br.org.cancer.modred.controller.view.DoadorView;
import br.org.cancer.modred.controller.view.PedidoColetaView;
import br.org.cancer.modred.controller.view.PedidoTransporteView;
import br.org.cancer.modred.controller.view.PedidoWorkupView;
import br.org.cancer.modred.controller.view.TarefaLogisticaView;
import br.org.cancer.modred.controller.view.TarefaView;
import br.org.cancer.modred.controller.view.TransportadoraView;
import br.org.cancer.modred.controller.view.WorkupView;
import br.org.cancer.modred.model.annotations.CustomFuture;
import br.org.cancer.modred.model.security.Usuario;

/**
 * Pedido de workup representa o pedido dos exames realizados para o doador
 * saber se está em condições de realizar a doação, ou seja, é uma espécie de 
 * check-up atualizado do doador escolhido como compatível para determinado 
 * paciente.
 * 
 * @author Pizão
 */
@Entity
@Table(name = "PEDIDO_WORKUP")
@SequenceGenerator(name = "SQ_PEWO_ID", sequenceName = "SQ_PEWO_ID", allocationSize = 1)
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
public class PedidoWorkup implements Serializable {

	private static final long serialVersionUID = -9083410770403234584L;

	public static final int DATA_DA_PRESCRICAO = 1;
	public static final int DATA_DA_DISPONIBILIDADE = 2;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_PEWO_ID")
	@Column(name = "PEWO_ID")
	@JsonView({ PedidoWorkupView.DetalheWorkup.class, TarefaView.Listar.class, PedidoWorkupView.AgendamentoWorkup.class, WorkupView.Resultado.class,
			PedidoColetaView.DetalheColeta.class, AvaliacaoResultadoWorkupView.ListarAvaliacao.class, PedidoColetaView.AgendamentoColeta.class,
			PedidoWorkupView.SugerirDataWorkup.class})
	private Long id;
	
	@Transient
	private LocalDate dataPrevistaLiberacaoDoador;
	
	@Transient
	private LocalDate dataPrevistaDisponibilidadeDoador;

	@CustomFuture
	@Column(name = "PEWO_DT_COLETA")
	@JsonView(value = { PedidoWorkupView.DetalheWorkup.class, WorkupView.Resultado.class,
			PedidoColetaView.DetalheColeta.class, BuscaView.AvaliacaoPedidoColeta.class,
			TarefaLogisticaView.Listar.class })
	private LocalDate dataColeta;

	@Transient
	private LocalDate dataLimiteWorkup;

	@Transient
	private LocalDate dataInicioWorkup;

	@Transient
	private LocalDate dataFinalWorkup;

	@Transient
	private LocalDateTime dataUltimoStatus;

	/**
	 * Data da criação do pedido de workup.
	 */
	@Column(name = "PEWO_DT_CRIACAO")
	@JsonView(value = { TarefaView.Listar.class, PedidoWorkupView.AgendamentoWorkup.class, TarefaView.Consultar.class, DoadorView.ContatoPassivo.class })
	@NotNull
	private LocalDateTime dataCriacao;

	@Transient
	private Integer tentativasAgendamento;

	@Transient
	private Integer tipoUtilizado;

	@Transient
	private List<Disponibilidade> disponibilidades;

	/**
	 * Solicitação do pedido.
	 */
	@ManyToOne
	@JoinColumn(name = "SOLI_ID")
	@JsonView(value = { TarefaView.Listar.class, PedidoWorkupView.AgendamentoWorkup.class, TarefaView.Consultar.class, PedidoWorkupView.DetalheWorkup.class,
			WorkupView.Resultado.class, PedidoColetaView.DetalheColeta.class, BuscaView.AvaliacaoWorkupDoador.class,
			TarefaLogisticaView.Listar.class, 
			TransportadoraView.Listar.class,
			PedidoTransporteView.Detalhe.class, TransportadoraView.AgendarTransporte.class,
			DisponibilidadeView.VisualizacaoCentroTransplante.class, AvaliacaoResultadoWorkupView.ListarAvaliacao.class,
			PedidoWorkupView.CadastrarResultado.class, PedidoColetaView.AgendamentoColeta.class,
			PedidoWorkupView.SugerirDataWorkup.class, WorkupView.ResultadoParaAvaliacao.class, AvaliacaoPedidoColetaView.Detalhe.class})
	private Solicitacao solicitacao;

	@ManyToOne
	@JoinColumn(name = "CETR_ID_TRANSP")
	@JsonView(value = { PedidoWorkupView.DetalheWorkup.class, WorkupView.Resultado.class,
			PedidoColetaView.DetalheColeta.class, TarefaView.Listar.class,  TransportadoraView.Listar.class,
			TransportadoraView.AgendarTransporte.class })
	private CentroTransplante centroTransplante;

	@ManyToOne
	@JoinColumn(name = "CETR_ID_COLETA")
	@JsonView(value = { PedidoWorkupView.DetalheWorkup.class, WorkupView.Resultado.class,
			PedidoColetaView.DetalheColeta.class, TarefaView.Listar.class,  TransportadoraView.Listar.class,
			TransportadoraView.AgendarTransporte.class })
	private CentroTransplante centroColeta;
	
	@Transient
	private Usuario usuarioResponsavel;

	/**
	 * Guarda a última disponibilidade disponível.
	 * 
	 */
	@Transient
	@JsonView(PedidoWorkupView.DetalheWorkup.class)
	private Disponibilidade ultimaDisponibilidade;

	/**
	 * Define se o pedido de workup necessitou de logística para o doador.
	 */
	@Transient
	private Boolean necessitaLogistica;

	@Transient
	private ResultadoWorkup resultadoWorkup;

	@Transient
	private FonteCelula fonteCelula;
	
	@Column(name = "PEWO_IN_STATUS")
	private Long status;
	
	/**
	 * Indica se o workup é para um doador internacional (medula ou cordão).
	 * Isso é utilizado no front-end para esconder algumas informações exclusivas do nacional.
	 */
	@Transient
	@JsonView(PedidoWorkupView.DetalheWorkup.class)
	private Boolean isDoadorInternacional;


	@JsonView(value = BuscaView.AvaliacaoPedidoColeta.class)
	@Transient
	private Integer diasRestantes;
	

	public PedidoWorkup() {
		super();
	}

	public PedidoWorkup(Long id) {
		this();
		this.id = id;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return this.id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the dataUltimoStatus
	 */
	public LocalDateTime getDataUltimoStatus() {
		return dataUltimoStatus;
	}

	/**
	 * @param dataUltimoStatus
	 *            the dataUltimoStatus to set
	 */
	public void setDataUltimoStatus(LocalDateTime dataUltimoStatus) {
		this.dataUltimoStatus = dataUltimoStatus;
	}

	/**
	 * @return the dataCriacao
	 */
	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}

	/**
	 * @param dataCriacao
	 *            the dataCriacao to set
	 */
	public void setDataCriacao(LocalDateTime dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	/**
	 * @return the tipoUtilizado
	 */
	public Integer getTipoUtilizado() {
		return tipoUtilizado;
	}

	/**
	 * @param tipoUtilizado
	 *            the tipoUtilizado to set
	 */
	public void setTipoUtilizado(Integer tipoUtilizado) {
		this.tipoUtilizado = tipoUtilizado;
	}

	/**
	 * @return the disponibilidades
	 */
	public List<Disponibilidade> getDisponibilidades() {
		return disponibilidades;
	}

	/**
	 * @param disponibilidades
	 *            the disponibilidades to set
	 */
	public void setDisponibilidades(List<Disponibilidade> disponibilidades) {
		this.disponibilidades = disponibilidades;
	}

	/**
	 * @return the motivoCancelamentoWorkup
	 */
	public MotivoCancelamentoWorkup getMotivoCancelamentoWorkup() {
		return null;
	}

	/**
	 * @param motivoCancelamentoWorkup
	 *            the motivoCancelamentoWorkup to set
	 */
	public void setMotivoCancelamentoWorkup(MotivoCancelamentoWorkup motivoCancelamentoWorkup) {
	}

	/**
	 * @return the statusPedidoWorkup
	 */
	public StatusPedidoWorkup getStatusPedidoWorkup() {
		return null;
	}

	/**
	 * @param statusPedidoWorkup
	 *            the statusPedidoWorkup to set
	 */
	public void setStatusPedidoWorkup(StatusPedidoWorkup statusPedidoWorkup) {
	}

	/**
	 * @return the solicitacao
	 */
	public Solicitacao getSolicitacao() {
		return solicitacao;
	}

	/**
	 * @param solicitacao
	 *            the solicitacao to set
	 */
	public void setSolicitacao(Solicitacao solicitacao) {
		this.solicitacao = solicitacao;
	}

	/**
	 * Centro de coleta do pedido de workup.
	 * 
	 * @return
	 */
	public CentroTransplante getCentroColeta() {
		return centroColeta;
	}

	/**
	 * Centro de coleta do pedido de workup.
	 * 
	 * @param centroColeta
	 */
	public void setCentroColeta(CentroTransplante centroColeta) {
		this.centroColeta = centroColeta;
	}

	public Usuario getUsuarioResponsavel() {
		return usuarioResponsavel;
	}

	public void setUsuarioResponsavel(Usuario usuarioResponsavel) {
		this.usuarioResponsavel = usuarioResponsavel;
	}

	public Disponibilidade getUltimaDisponibilidade() {
		return ultimaDisponibilidade;
	}

	public void setUltimaDisponibilidade(Disponibilidade ultimaDisponibilidade) {
		this.ultimaDisponibilidade = ultimaDisponibilidade;
	}

	public LocalDate getDataInicioWorkup() {
		return dataInicioWorkup;
	}

	public void setDataInicioWorkup(LocalDate dataInicioWorkup) {
		this.dataInicioWorkup = dataInicioWorkup;
	}

	public LocalDate getDataFinalWorkup() {
		return dataFinalWorkup;
	}

	public void setDataFinalWorkup(LocalDate dataFinalWorkup) {
		this.dataFinalWorkup = dataFinalWorkup;
	}

	public LocalDate getDataLimiteWorkup() {
		return dataLimiteWorkup;
	}

	public void setDataLimiteWorkup(LocalDate dataLimiteWorkup) {
		this.dataLimiteWorkup = dataLimiteWorkup;
	}

	public Boolean getNecessitaLogistica() {
		return necessitaLogistica;
	}

	public void setNecessitaLogistica(Boolean necessitaLogistica) {
		this.necessitaLogistica = necessitaLogistica;
	}

	/**
	 * @return the dataPrevistaLiberacaoDoador
	 */
	public LocalDate getDataPrevistaLiberacaoDoador() {
		return dataPrevistaLiberacaoDoador;
	}

	/**
	 * @param dataPrevistaLiberacaoDoador
	 *            the dataPrevistaLiberacaoDoador to set
	 */
	public void setDataPrevistaLiberacaoDoador(LocalDate dataPrevistaLiberacaoDoador) {
		this.dataPrevistaLiberacaoDoador = dataPrevistaLiberacaoDoador;
	}

	/**
	 * @return the dataPrevistaDisponibilidadeDoador
	 */
	public LocalDate getDataPrevistaDisponibilidadeDoador() {
		return dataPrevistaDisponibilidadeDoador;
	}

	/**
	 * @param dataPrevistaDisponibilidadeDoador
	 *            the dataPrevistaDisponibilidadeDoador to set
	 */
	public void setDataPrevistaDisponibilidadeDoador(LocalDate dataPrevistaDisponibilidadeDoador) {
		this.dataPrevistaDisponibilidadeDoador = dataPrevistaDisponibilidadeDoador;
	}

	/**
	 * @return the dataColeta
	 */
	public LocalDate getDataColeta() {
		return dataColeta;
	}

	/**
	 * @param dataColeta
	 *            the dataColeta to set
	 */
	public void setDataColeta(LocalDate dataColeta) {
		this.dataColeta = dataColeta;
	}

	/**
	 * @return the resultadoWorkup
	 */
	public ResultadoWorkup getResultadoWorkup() {
		return resultadoWorkup;
	}

	/**
	 * @param resultadoWorkup
	 *            the resultadoWorkup to set
	 */
	public void setResultadoWorkup(ResultadoWorkup resultadoWorkup) {
		this.resultadoWorkup = resultadoWorkup;
	}

	/**
	 * @return the fonteCelula
	 */
	public FonteCelula getFonteCelula() {
		return fonteCelula;
	}

	/**
	 * @param fonteCelula
	 *            the fonteCelula to set
	 */
	public void setFonteCelula(FonteCelula fonteCelula) {
		this.fonteCelula = fonteCelula;
	}

	public Integer getTentativasAgendamento() {
		return tentativasAgendamento;
	}

	public void setTentativasAgendamento(Integer tentativasAgendamento) {
		this.tentativasAgendamento = tentativasAgendamento;
	}

	/**
	 * @return the centroTransplante
	 */
	public CentroTransplante getCentroTransplante() {
		return centroTransplante;
	}

	/**
	 * @param centroTransplante the centroTransplante to set
	 */
	public void setCentroTransplante(CentroTransplante centroTransplante) {
		this.centroTransplante = centroTransplante;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((solicitacao == null) ? 0 : solicitacao.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		PedidoWorkup other = (PedidoWorkup) obj;
		if (solicitacao == null) {
			if (other.solicitacao != null) {
				return false;
			}
		}
		else if (!solicitacao.equals(other.solicitacao)) {
			return false;
		}
		return true;
	}

	@JsonView(value = PedidoWorkupView.DetalheWorkup.class)
	public Boolean getIsDoadorInternacional() {
		return isDoadorInternacional;
	}

	public void setIsDoadorInternacional(Boolean isDoadorInternacional) {
		this.isDoadorInternacional = isDoadorInternacional;
	}

	/**
	 * @return the diasRestantes
	 */
	public Integer getDiasRestantes() {
		if(this.dataColeta != null){
			if(LocalDate.now().isAfter(this.dataColeta)){
				return (int) this.dataColeta.until(LocalDateTime.now(),ChronoUnit.DAYS) * -1;	
			}
			else{
				return (int) this.dataColeta.until(LocalDateTime.now(),ChronoUnit.DAYS);
			}
			
		}
		return null;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}
	
	
	

}
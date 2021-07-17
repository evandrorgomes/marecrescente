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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.hibernate.envers.RelationTargetAuditMode;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.BuscaView;
import br.org.cancer.modred.controller.view.GenotipoView;
import br.org.cancer.modred.controller.view.LaboratorioView;
import br.org.cancer.modred.controller.view.PedidoExameView;
import br.org.cancer.modred.model.domain.OwnerPedidoExame;
import br.org.cancer.modred.service.impl.custom.EntityObservable;
import br.org.cancer.modred.service.impl.observers.PedidoExameCtCanceladoObserver;
import br.org.cancer.modred.service.impl.observers.PedidoExameCtCriadoObserver;

/**
 * Representa o pedido de exame solicitado para determinado paciente ou doador,
 * podendo estes serem nacionais ou internacionais.
 * 
 * @author Pizão
 * 
 */
@Entity
@SequenceGenerator(name = "SQ_PEEX_ID", sequenceName = "SQ_PEEX_ID", allocationSize = 1)
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
@Table(name = "PEDIDO_EXAME")
public class PedidoExame extends EntityObservable implements Serializable {
	private static final long serialVersionUID = 7774495959241636184L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_PEEX_ID")
	@Column(name = "PEEX_ID")
	@JsonView({LaboratorioView.ListarReceberExame.class, PedidoExameView.Detalhe.class, BuscaView.UltimoPedidoExame.class,
		PedidoExameView.ListarTarefas.class,PedidoExameView.ObterParaEditar.class, GenotipoView.Divergente.class})
	private Long id;

	@Column(name = "PEEX_DT_CRIACAO")
	@NotNull
	@JsonView({PedidoExameView.ListarTarefas.class, PedidoExameView.ObterParaEditar.class})
	private LocalDateTime dataCriacao;
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "SOLI_ID")
    @JsonView({LaboratorioView.ListarReceberExame.class, PedidoExameView.ListarTarefas.class, PedidoExameView.ObterParaEditar.class, BuscaView.UltimoPedidoExame.class})
    private Solicitacao solicitacao;
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "STPX_ID")
	@NotNull
	@JsonView({BuscaView.UltimoPedidoExame.class, PedidoExameView.ListarTarefas.class})
	private StatusPedidoExame statusPedidoExame;
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "LABO_ID")
	@JsonView({LaboratorioView.ListarReceberExame.class,PedidoExameView.Detalhe.class, BuscaView.UltimoPedidoExame.class, PedidoExameView.ListarTarefas.class})
	private Laboratorio laboratorio;
	
	@Column(name = "PEEX_DT_COLETA_AMOSTRA")
	@JsonView({BuscaView.UltimoPedidoExame.class, PedidoExameView.Detalhe.class})
	private LocalDate dataColetaAmostra;
	
	@Column(name = "PEEX_DT_RECEBIMENTO_AMOSTRA")
	@JsonView(BuscaView.UltimoPedidoExame.class)
	private LocalDate dataRecebimentoAmostra;
	
	@Column(name = "PEEX_DT_CANCELAMENTO")
	private LocalDate dataCancelamento;
	
	@ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "EXAM_ID")
	@JsonView({GenotipoView.Divergente.class, BuscaView.UltimoPedidoExame.class})	
	private Exame exame;
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "TIEX_ID")
	@JsonView({PedidoExameView.Detalhe.class, BuscaView.UltimoPedidoExame.class, LaboratorioView.ListarReceberExame.class})
	private TipoExame tipoExame;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "PEEX_IN_OWNER")
	@NotNull
	private OwnerPedidoExame owner;
	
	@NotEmpty
	@ManyToMany(cascade={CascadeType.ALL})
	@JoinTable(name = "LOCUS_PEDIDO_EXAME",
		joinColumns = { @JoinColumn(name = "PEEX_ID") },
		inverseJoinColumns = { @JoinColumn(name = "LOCU_ID") })
	@JsonView({PedidoExameView.ListarTarefas.class,PedidoExameView.Detalhe.class,PedidoExameView.ObterParaEditar.class})
	private List<Locus> locusSolicitados;
	
	@Column(name = "PEEX_TX_JUSTIFICATIVA")
	@Length(max = 255)
	@JsonView(BuscaView.UltimoPedidoExame.class)
	private String justificativa;
	
	@OneToMany(mappedBy="pedidoExame", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@OrderBy("dataCriacao DESC")
	@Fetch(FetchMode.SUBSELECT)
	@NotAudited
	private List<SolicitacaoRedomeweb> solicitacoesRedomeweb;
	
	@Column(name = "PEEX_DT_ABERTURA_DIVERGENCIA")
	private LocalDateTime dataAberturaResolverDivergencia;
	
	@Column(name = "PEEX_DT_CONCLUSAO_DIVERGENCIA")
	private LocalDateTime dataConclusaoResolverDivergencia;
	
	/**
	 * Construtor para definição dos observers envolvendo
	 * esta entidade.
	 */
	public PedidoExame() {
		super();
		addObserver(PedidoExameCtCriadoObserver.class);
		addObserver(PedidoExameCtCanceladoObserver.class);
	}
	
	/**
	 * Construtor para facilitar a instanciação do 
	 * pedido já com o ID preenchido.
	 * 
	 * @param pedidoExameId ID do pedido de exame a ser instanciado.
	 */
	public PedidoExame(Long pedidoExameId) {
		super();
		this.id = pedidoExameId;
	}
	
	/**
	 * Identificador da entidade.
	 * 
	 * @return ID da entidade.
	 */
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Data de realização da tentativa de contato.
	 * 
	 * @return data de criação da tentativa.
	 */
	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(LocalDateTime dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public Solicitacao getSolicitacao() {
		return solicitacao;
	}

	public void setSolicitacao(Solicitacao solicitacao) {
		this.solicitacao = solicitacao;
	}

	
	/**
	 * @return the statusPedidoExame
	 */
	public StatusPedidoExame getStatusPedidoExame() {
		return statusPedidoExame;
	}

	
	/**
	 * @param statusPedidoExame the statusPedidoExame to set
	 */
	public void setStatusPedidoExame(StatusPedidoExame statusPedidoExame) {
		this.statusPedidoExame = statusPedidoExame;
	}

	
	/**
	 * @return the laboratorio
	 */
	public Laboratorio getLaboratorio() {
		return laboratorio;
	}

	
	/**
	 * @param laboratorio the laboratorio to set
	 */
	public void setLaboratorio(Laboratorio laboratorio) {
		this.laboratorio = laboratorio;
	}

	
	/**
	 * @return the dataColetaAmostra
	 */
	public LocalDate getDataColetaAmostra() {
		return dataColetaAmostra;
	}

	
	/**
	 * @param dataColetaAmostra the dataColetaAmostra to set
	 */
	public void setDataColetaAmostra(LocalDate dataColetaAmostra) {
		this.dataColetaAmostra = dataColetaAmostra;
	}

	
	/**
	 * @return the dataRecebimentoAmostra
	 */
	public LocalDate getDataRecebimentoAmostra() {
		return dataRecebimentoAmostra;
	}

	
	/**
	 * @param dataRecebimentoAmostra the dataRecebimentoAmostra to set
	 */
	public void setDataRecebimentoAmostra(LocalDate dataRecebimentoAmostra) {
		this.dataRecebimentoAmostra = dataRecebimentoAmostra;
	}
	/**
	 * @return the exame
	 */
	public Exame getExame() {
		return exame;
	}

	/**
	 * @param exame the exame to set
	 */
	public void setExame(Exame exame) {
		this.exame = exame;
	}

	/**
	 * @return the tipoExame
	 */
	public TipoExame getTipoExame() {
		return tipoExame;
	}

	/**
	 * @param tipoExame the tipoExame to set
	 */
	public void setTipoExame(TipoExame tipoExame) {
		this.tipoExame = tipoExame;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ( ( id == null ) ? 0 : id.hashCode() );
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
		if (!( obj instanceof PedidoExame )) {
			return false;
		}
		PedidoExame other = (PedidoExame) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		}
		else
			if (!id.equals(other.id)) {
				return false;
			}
		return true;
	}

	/**
	 * Indica quem é o owner do pedido de exame.
	 * 
	 * @return enum informando se é paciente ou doador.
	 */
	public OwnerPedidoExame getOwner() {
		return owner;
	}

	public void setOwner(OwnerPedidoExame owner) {
		this.owner = owner;
	}

	/**
	 * Lista de lócus selecionados no pedido de exame.
	 * 
	 * @return lócus solicitados para o pedido.
	 */
	public List<Locus> getLocusSolicitados() {
		return locusSolicitados;
	}

	public void setLocusSolicitados(List<Locus> locusSolicitados) {
		this.locusSolicitados = locusSolicitados;
	}
	
	/**
	 * @return the justificativa
	 */
	public String getJustificativa() {
		return justificativa;
	}
	
	/**
	 * @param justificativa the justificativa to set
	 */
	public void setJustificativa(String justificativa) {
		this.justificativa = justificativa;
	}

	
	/**
	 * @return the dataCancelamento
	 */
	public LocalDate getDataCancelamento() {
		return dataCancelamento;
	}

	
	/**
	 * @param dataCancelamento the dataCancelamento to set
	 */
	public void setDataCancelamento(LocalDate dataCancelamento) {
		this.dataCancelamento = dataCancelamento;
	}

	/**
	 * @return the solicitacoesRedomeweb
	 */
	public List<SolicitacaoRedomeweb> getSolicitacoesRedomeweb() {
		return solicitacoesRedomeweb;
	}

	/**
	 * @param solicitacoesRedomeweb the solicitacoesRedomeweb to set
	 */
	public void setSolicitacoesRedomeweb(List<SolicitacaoRedomeweb> solicitacoesRedomeweb) {
		this.solicitacoesRedomeweb = solicitacoesRedomeweb;
	}

	/**
	 * @return the dataAberturaResolverDivergencia
	 */
	public LocalDateTime getDataAberturaResolverDivergencia() {
		return dataAberturaResolverDivergencia;
	}

	/**
	 * @param dataAberturaResolverDivergencia the dataAberturaResolverDivergencia to set
	 */
	public void setDataAberturaResolverDivergencia(LocalDateTime dataAberturaResolverDivergencia) {
		this.dataAberturaResolverDivergencia = dataAberturaResolverDivergencia;
	}

	/**
	 * @return the dataConclusaoResolverDivergencia
	 */
	public LocalDateTime getDataConclusaoResolverDivergencia() {
		return dataConclusaoResolverDivergencia;
	}

	/**
	 * @param dataConclusaoResolverDivergencia the dataConclusaoResolverDivergencia to set
	 */
	public void setDataConclusaoResolverDivergencia(LocalDateTime dataConclusaoResolverDivergencia) {
		this.dataConclusaoResolverDivergencia = dataConclusaoResolverDivergencia;
	}
	

}
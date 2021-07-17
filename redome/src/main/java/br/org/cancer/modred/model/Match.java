package br.org.cancer.modred.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.OneToMany;
import javax.persistence.ParameterMode;
import javax.persistence.SequenceGenerator;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.AnaliseMedicaView;
import br.org.cancer.modred.controller.view.AvaliacaoPedidoColetaView;
import br.org.cancer.modred.controller.view.AvaliacaoPrescricaoView;
import br.org.cancer.modred.controller.view.AvaliacaoResultadoWorkupView;
import br.org.cancer.modred.controller.view.ComentarioMatchView;
import br.org.cancer.modred.controller.view.DisponibilidadeView;
import br.org.cancer.modred.controller.view.FormularioView;
import br.org.cancer.modred.controller.view.PedidoColetaView;
import br.org.cancer.modred.controller.view.PedidoExameView;
import br.org.cancer.modred.controller.view.PedidoWorkupView;
import br.org.cancer.modred.controller.view.PrescricaoView;
import br.org.cancer.modred.controller.view.SolicitacaoView;
import br.org.cancer.modred.controller.view.TransportadoraView;
import br.org.cancer.modred.controller.view.WorkupView;
import br.org.cancer.modred.model.interfaces.IMatch;
import br.org.cancer.modred.model.interfaces.IQualificacaoMatch;
import br.org.cancer.modred.service.impl.custom.EntityObservable;

/**
 * Classe de representação do match realizado entre o doador e o paciente.
 * 
 * @author Filipe Paes
 */
@Entity
@Table(name = "\"MATCH\"")
@SequenceGenerator(name = "SQ_MATC_ID", sequenceName = "SQ_MATC_ID", allocationSize = 1)
@NamedStoredProcedureQuery(name = "proc_match_doador", procedureName = "proc_match_doador", parameters = {
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "rmr", type = Long.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "idDoador", type = Long.class) })
public class Match extends EntityObservable implements IMatch {

	private static final long serialVersionUID = -447675528563397904L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_MATC_ID")
	@Column(name = "MATC_ID")
	@JsonView(value = {ComentarioMatchView.Lista.class, SolicitacaoView.detalhe.class})
	private Long id;

	@ManyToOne
	@JoinColumn(name = "BUSC_ID")
	@JsonView(value = { TransportadoraView.Listar.class, TransportadoraView.AgendarTransporte.class,
			PrescricaoView.AutorizacaoPacienteListar.class, PedidoWorkupView.SugerirDataWorkup.class,
			PedidoColetaView.SugerirDataColeta.class, AvaliacaoResultadoWorkupView.ListarAvaliacao.class, 
			PedidoWorkupView.AgendamentoWorkup.class, AvaliacaoPrescricaoView.Detalhe.class, SolicitacaoView.detalhe.class,
			SolicitacaoView.listar.class})
	private Busca busca;

	@ManyToOne
	@JoinColumn(name = "DOAD_ID")
	@JsonView(value = { TransportadoraView.Listar.class, TransportadoraView.AgendarTransporte.class, PedidoExameView.ListarTarefas.class,
			PedidoExameView.ObterParaEditar.class, AvaliacaoPrescricaoView.Detalhe.class, DisponibilidadeView.VisualizacaoCentroTransplante.class, 
			PedidoWorkupView.CadastrarResultado.class, PedidoColetaView.AgendamentoColeta.class,
			PedidoColetaView.DetalheColeta.class, AnaliseMedicaView.ListarTarefas.class, AvaliacaoResultadoWorkupView.ListarAvaliacao.class,
			WorkupView.Resultado.class, WorkupView.ResultadoParaAvaliacao.class, AvaliacaoPedidoColetaView.Detalhe.class, 
			SolicitacaoView.detalhe.class, SolicitacaoView.listar.class, FormularioView.DetalheDoador.class, PedidoWorkupView.DetalheWorkup.class})
	private Doador doador;

	@Column(name = "MATC_IN_STATUS")
	private Boolean status;

	@OneToMany(mappedBy = "match")
	private List<ComentarioMatch> comentarios;

	@OneToMany(mappedBy = "match")
	private List<Solicitacao> solicitacoes;

	@OneToMany(mappedBy = "match", fetch = FetchType.LAZY)
	private List<QualificacaoMatch> qualificacoes;
	
	@Column(name = "MATC_TX_GRADE")
	private String grade;
	
	@Column(name = "MATC_TX_MISMATCH")
    private String mismatch;
	
	@Column(name = "MATC_TX_GRADE_ANTES")
	private String gradeAnterior;
	
	@Column(name = "MATC_TX_MISMATCH_ANTES")
    private String mismatchAnterior;
	
	@Column(name = "MATC_DT_MATCH")
	private LocalDateTime dataCriacao;
	
	@Column(name = "MATC_DT_ALTERACAO ")
	private LocalDateTime dataAtualizacao;
	
	@Column(name = "MATC_IN_DISPONIBILIZADO", insertable = false, updatable = true)
    private Boolean disponibilizado;

	@Column(name = "MATC_NR_ORDENACAO_WMDA")
    private Integer ordenacaoWmdaMatch;
	
	@Column(name = "MATC_TX_PROBABILIDADE0")
    private String probabilidade0;
	
	@Column(name = "MATC_TX_PROBABILIDADE1")
    private String probabilidade1;

	@Column(name = "MATC_TX_PROBABILIDADE2")
    private String probabilidade2;
	
	@ManyToOne
	@JoinColumn(name = "TIPM_ID_DPB1", referencedColumnName="TIPM_ID")
	private TipoPermissividade tipoPermissividade;
	
	public Match() {
	}
	
	/**
	 * Construtor especializado para instanciar, já com ID,
	 * um novo match.
	 * 
	 * @param id ID do match.
	 */
	public Match(Long id) {
		this();
		this.id = id;
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
	 * @return the busca
	 */
	public Busca getBusca() {
		return busca;
	}

	/**
	 * @param busca the busca to set
	 */
	public void setBusca(Busca busca) {
		this.busca = busca;
	}

	/**
	 * @return the doador
	 */
	public Doador getDoador() {
		return doador;
	}

	/**
	 * @param doador the doador to set
	 */
	public void setDoador(Doador doador) {
		this.doador = doador;
	}

	/**
	 * @return the status
	 */
	public Boolean getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(Boolean status) {
		this.status = status;
	}

	/**
	 * Indica a fase que o doador está e, caso ocorra match,
	 * define a coluna que será exibido no carrousel.
	 *  
	 * @return Fase que encontra-se o usuário.
	 */
	public String getSituacao() {
		if(doador != null){
			return doador.getFase();
		}
		return null;
	}

	public List<ComentarioMatch> getComentarios() {
		return comentarios;
	}

	public void setComentarios(List<ComentarioMatch> comentarios) {
		this.comentarios = comentarios;
	}

	public List<Solicitacao> getSolicitacoes() {
		return solicitacoes;
	}

	public void setSolicitacoes(List<Solicitacao> solicitacoes) {
		this.solicitacoes = solicitacoes;
	}

	/**
	 * @return the qualificacoes
	 */
	public List<? extends IQualificacaoMatch> getQualificacoes() {
		return qualificacoes;
	}

	/**
	 * @param qualificacoes the qualificacoes to set
	 */
	public void setQualificacoes(List<QualificacaoMatch> qualificacoes) {
		this.qualificacoes = qualificacoes;
	}

	/**
	 * @return the grade
	 */
	public String getGrade() {
		return grade;
	}

	/**
	 * @param grade the grade to set
	 */
	public void setGrade(String grade) {
		this.grade = grade;
	}

	/**
	 * @return the mismatch
	 */
	public String getMismatch() {
		return mismatch;
	}

	/**
	 * @param mismatch the mismatch to set
	 */
	public void setMismatch(String mismatch) {
		this.mismatch = mismatch;
	}

	/**
	 * @return the gradeAnterior
	 */
	public String getGradeAnterior() {
		return gradeAnterior;
	}

	/**
	 * @param gradeAnterior the gradeAnterior to set
	 */
	public void setGradeAnterior(String gradeAnterior) {
		this.gradeAnterior = gradeAnterior;
	}

	/**
	 * @return the mismatchAnterior
	 */
	public String getMismatchAnterior() {
		return mismatchAnterior;
	}

	/**
	 * @param mismatchAnterior the mismatchAnterior to set
	 */
	public void setMismatchAnterior(String mismatchAnterior) {
		this.mismatchAnterior = mismatchAnterior;
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
	 * @return the dataAtualizacao
	 */
	public LocalDateTime getDataAtualizacao() {
		return dataAtualizacao;
	}

	/**
	 * @param dataAtualizacao the dataAtualizacao to set
	 */
	public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}

	/**
	 * @return the tipoPermissividade
	 */
	public TipoPermissividade getTipoPermissividade() {
		return tipoPermissividade;
	}

	/**
	 * @param tipoPermissividade the tipoPermissividade to set
	 */
	public void setTipoPermissividade(TipoPermissividade tipoPermissividade) {
		this.tipoPermissividade = tipoPermissividade;
	}
	
	/**
	 * @return the disponibilizado
	 */
	public Boolean getDisponibilizado() {
		return disponibilizado;
	}

	/**
	 * @param disponibilizado the disponibilizado to set
	 */
	public void setDisponibilizado(Boolean disponibilizado) {
		this.disponibilizado = disponibilizado;
	}

	/**
	 * @return the ordenacaoWmdaMatch
	 */
	public Integer getOrdenacaoWmdaMatch() {
		return ordenacaoWmdaMatch;
	}

	/**
	 * @param ordenacaoWmdaMatch the ordenacaoWmdaMatch to set
	 */
	public void setOrdenacaoWmdaMatch(Integer ordenacaoWmdaMatch) {
		this.ordenacaoWmdaMatch = ordenacaoWmdaMatch;
	}

	/**
	 * @return the probabilidade0
	 */
	public String getProbabilidade0() {
		return probabilidade0;
	}

	/**
	 * @param probabilidade0 the probabilidade0 to set
	 */
	public void setProbabilidade0(String probabilidade0) {
		this.probabilidade0 = probabilidade0;
	}

	/**
	 * @return the probabilidade1
	 */
	public String getProbabilidade1() {
		return probabilidade1;
	}

	/**
	 * @param probabilidade1 the probabilidade1 to set
	 */
	public void setProbabilidade1(String probabilidade1) {
		this.probabilidade1 = probabilidade1;
	}

	/**
	 * @return the probabilidade2
	 */
	public String getProbabilidade2() {
		return probabilidade2;
	}

	/**
	 * @param probabilidade2 the probabilidade2 to set
	 */
	public void setProbabilidade2(String probabilidade2) {
		this.probabilidade2 = probabilidade2;
	}

	@JsonView({SolicitacaoView.detalhe.class, SolicitacaoView.listar.class})
	@JsonProperty(value = "doador", access = Access.READ_ONLY)
	public IDoador getDoadorPelaInterface() {
		return doador != null ? (IDoador) doador : null;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((busca == null) ? 0 : busca.hashCode());
		result = prime * result + ((doador == null) ? 0 : doador.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
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
		Match other = (Match) obj;
		if (busca == null) {
			if (other.busca != null) {
				return false;
			}
		} 
		else if (!busca.equals(other.busca)) {
			return false;
		}
		if (doador == null) {
			if (other.doador != null) {
				return false;
			}
		} 
		else if (!doador.equals(other.doador)) {
			return false;
		}
		if (status == null) {
			if (other.status != null) {
				return false;
			}
		} 
		else if (!status.equals(other.status)) {
			return false;
		}
		return true;
	}
	
}
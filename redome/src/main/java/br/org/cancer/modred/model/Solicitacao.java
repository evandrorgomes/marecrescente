package br.org.cancer.modred.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.AnaliseMedicaView;
import br.org.cancer.modred.controller.view.AvaliacaoPedidoColetaView;
import br.org.cancer.modred.controller.view.AvaliacaoPrescricaoView;
import br.org.cancer.modred.controller.view.AvaliacaoResultadoWorkupView;
import br.org.cancer.modred.controller.view.BuscaView;
import br.org.cancer.modred.controller.view.DisponibilidadeView;
import br.org.cancer.modred.controller.view.DoadorView;
import br.org.cancer.modred.controller.view.FormularioView;
import br.org.cancer.modred.controller.view.LaboratorioView;
import br.org.cancer.modred.controller.view.PedidoColetaView;
import br.org.cancer.modred.controller.view.PedidoExameView;
import br.org.cancer.modred.controller.view.PedidoTransporteView;
import br.org.cancer.modred.controller.view.PedidoWorkupView;
import br.org.cancer.modred.controller.view.PrescricaoView;
import br.org.cancer.modred.controller.view.SolicitacaoView;
import br.org.cancer.modred.controller.view.TarefaLogisticaView;
import br.org.cancer.modred.controller.view.TarefaView;
import br.org.cancer.modred.controller.view.TransportadoraView;
import br.org.cancer.modred.controller.view.WorkupView;
import br.org.cancer.modred.model.annotations.EnumValues;
import br.org.cancer.modred.model.domain.StatusSolicitacao;
import br.org.cancer.modred.model.security.CriacaoAuditavel;
import br.org.cancer.modred.model.security.Usuario;

/**
 * Classe de persistencia para Solicitacao.
 * 
 * @author Fillipe Queiroz
 */
@Entity
@SequenceGenerator(name = "SQ_SOLI_ID", sequenceName = "SQ_SOLI_ID", allocationSize = 1)
@Table(name = "SOLICITACAO")
public class Solicitacao extends CriacaoAuditavel implements Serializable {

	private static final long serialVersionUID = -98575266789449732L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_SOLI_ID")
	@Column(name = "SOLI_ID")
	@JsonView({ BuscaView.Enriquecimento.class, DoadorView.AtualizacaoFase2.class, TarefaView.Listar.class, PedidoWorkupView.AgendamentoWorkup.class,
			WorkupView.Resultado.class, TarefaLogisticaView.Listar.class, PedidoExameView.ListarTarefas.class,
			PedidoColetaView.AgendamentoColeta.class, BuscaView.UltimoPedidoExame.class, SolicitacaoView.detalhe.class, SolicitacaoView.listar.class})
	private Long id;

	@Transient
	@JsonView({ BuscaView.Enriquecimento.class, DoadorView.AtualizacaoFase2.class, TarefaView.Listar.class, PedidoWorkupView.AgendamentoWorkup.class,
			PedidoWorkupView.DetalheWorkup.class, WorkupView.Resultado.class, BuscaView.AvaliacaoWorkupDoador.class, 
			TarefaLogisticaView.Listar.class,AvaliacaoPrescricaoView.ListarAvaliacao.class,
			PedidoTransporteView.Detalhe.class, TransportadoraView.AgendarTransporte.class,
			PedidoExameView.ListarTarefas.class})
	private Doador doador;

	@ManyToOne
	@JoinColumn(name = "PACI_NR_RMR")
	@JsonView({ TarefaView.Listar.class, PedidoWorkupView.AgendamentoWorkup.class, BuscaView.AvaliacaoPedidoColeta.class, AvaliacaoPrescricaoView.ListarAvaliacao.class, 
				TransportadoraView.AgendarTransporte.class
				,LaboratorioView.ListarReceberExame.class, BuscaView.AvaliacaoPedidoColeta.class, PedidoColetaView.SugerirDataColeta.class,
				PedidoWorkupView.SugerirDataWorkup.class,TarefaLogisticaView.Listar.class})
	@Fetch(FetchMode.SELECT)
	private Paciente paciente;

	@ManyToOne
	@JoinColumn(name = "TISO_ID")
	@JsonView({ DoadorView.ContatoPassivo.class, PedidoExameView.ListarTarefas.class, AnaliseMedicaView.ListarTarefas.class, AnaliseMedicaView.Detalhe.class, SolicitacaoView.detalhe.class})
	private TipoSolicitacao tipoSolicitacao;

	@ManyToOne
	@JoinColumn(name = "USUA_ID")
	private Usuario usuario;

	@Column(name = "SOLI_DT_CRIACAO")
	@JsonView({ DoadorView.ContatoPassivo.class })
	private LocalDateTime dataCriacao;

	@EnumValues(StatusSolicitacao.class)
	@Column(name = "SOLI_NR_STATUS")
	@JsonView(value = { TarefaView.Listar.class, PedidoWorkupView.AgendamentoWorkup.class, PedidoWorkupView.DetalheWorkup.class, 
			DoadorView.ContatoPassivo.class, SolicitacaoView.detalhe.class, SolicitacaoView.listar.class})
	@NotNull
	private Integer status;

	//	@OneToOne(mappedBy = "solicitacao")
	//	private PedidoContato pedidoContato;
	
	/**
	 * Referência para prescricao.
	 */
	@OneToOne(mappedBy = "solicitacao")
	@JsonView(value = { TarefaView.Listar.class, PedidoWorkupView.AgendamentoWorkup.class, PedidoWorkupView.DetalheWorkup.class, 
			PedidoColetaView.AgendamentoColeta.class, PedidoColetaView.DetalheColeta.class, SolicitacaoView.listar.class })
	private Prescricao prescricao;

	@OneToOne(mappedBy = "solicitacao")
	@JsonView(value = { DoadorView.ContatoPassivo.class, BuscaView.AvaliacaoPedidoColeta.class,
			AvaliacaoPedidoColetaView.Detalhe.class, BuscaView.AvaliacaoPedidoColeta.class})
	private PedidoWorkup pedidoWorkup;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="MATC_ID")
	@JsonView(value = { TransportadoraView.Listar.class, TransportadoraView.AgendarTransporte.class, PedidoExameView.ListarTarefas.class,
			PedidoExameView.ObterParaEditar.class, AvaliacaoPrescricaoView.Detalhe.class, DisponibilidadeView.VisualizacaoCentroTransplante.class,
			PedidoWorkupView.CadastrarResultado.class, PedidoColetaView.AgendamentoColeta.class, PedidoColetaView.AgendamentoColeta.class,
			PedidoColetaView.DetalheColeta.class, PrescricaoView.AutorizacaoPacienteListar.class,
			PedidoWorkupView.SugerirDataWorkup.class, PedidoColetaView.SugerirDataColeta.class,
			AvaliacaoResultadoWorkupView.ListarAvaliacao.class, AnaliseMedicaView.ListarTarefas.class, AnaliseMedicaView.Detalhe.class, 
			WorkupView.Resultado.class, WorkupView.ResultadoParaAvaliacao.class, AvaliacaoPedidoColetaView.Detalhe.class, PedidoWorkupView.AgendamentoWorkup.class,
			SolicitacaoView.detalhe.class, SolicitacaoView.listar.class, FormularioView.DetalheDoador.class, PedidoWorkupView.DetalheWorkup.class})
	private Match match;
	
	/**
	 * Para os testes confirmatórios, o pedido de exame é criado e associado diretamente a
	 * busca do paciente.
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="BUSC_ID")
	@JsonView(value = {	PedidoColetaView.SugerirDataColeta.class, TransportadoraView.Listar.class, 
						TransportadoraView.AgendarTransporte.class, PedidoWorkupView.SugerirDataWorkup.class, LaboratorioView.ListarReceberExame.class})
	private Busca busca;
	
	@ManyToOne
	@JoinColumn(name = "TIEX_ID")
	@JsonView(PedidoExameView.ListarTarefas.class)
	private TipoExame tipoExame;
	
	@ManyToOne
	@JoinColumn(name = "LABO_ID")
	@JsonView(PedidoExameView.ListarTarefas.class)
	private Laboratorio laboratorio;
	
	@Column(name = "SOLI_IN_RESOLVER_DIVERGENCIA")
	private Boolean resolverDivergencia;
	
	@ManyToOne
	@JoinColumn(name = "USUA_ID_RESPONSAVEL")
	@JsonView({SolicitacaoView.detalheWorkup.class})
	private Usuario usuarioResponsavel;
	
	@Column(name ="FAWO_ID")
	@JsonView({SolicitacaoView.detalhe.class})
	private Long faseWorkup;
	
	@ManyToOne
	@JoinColumn(name = "MOCS_ID")
	private MotivoCancelamentoSolicitacao motivoCancelamentoSolicitacao;
	
	@ManyToOne
	@JoinColumn(name = "CETR_ID_TRANSPLANTE")
	@JsonView({SolicitacaoView.detalheWorkup.class})
	private CentroTransplante centroTransplante;
	
	@ManyToOne
	@JoinColumn(name = "CETR_ID_COLETA")
	@JsonView({SolicitacaoView.detalheWorkup.class})
	private CentroTransplante centroColeta;
	
	
	@Column(name = "SOLI_IN_POSCOLETA")
	private Long posColeta;	
	
	@Column(name = "SOLI_IN_CONTAGEM_CELULA")
	private Long contagemCelula;
	

	public Solicitacao() {
		super();
		this.status = StatusSolicitacao.ABERTA.getId();
	}

	/**
	 * Chave primaria da solicitacao.
	 * 
	 * @return
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Chave primaria da solicitacao.
	 * 
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Doador da solicitacao.
	 * 
	 * @deprecated utilize o relacionamento com match através do atributo match.
	 * @return doador do match
	 */
	@Deprecated
	public Doador getDoador() {
		if(match == null){
			return null;
		}
		return match.getDoador();
	}

	/**
	 * Tipo de solicitacao.
	 * 
	 * @return
	 */
	public TipoSolicitacao getTipoSolicitacao() {
		return tipoSolicitacao;
	}

	/**
	 * Tipo de solicitacao.
	 * 
	 * @param tipoSolicitacao
	 */
	public void setTipoSolicitacao(TipoSolicitacao tipoSolicitacao) {
		this.tipoSolicitacao = tipoSolicitacao;
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
	protected void setUsuario(Usuario usuario) {
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
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * Retorna o paciente associado a solicitação.
	 * 
	 * @deprecated utilize o relacionamento com match através do atributo busca.
	 * @return paciente associado.
	 */
	@Deprecated
	public Paciente getPaciente() {
		if(match == null || match.getBusca() == null){
			if (busca != null) {
				return busca.getPaciente();
			}
			else {
				return null;
			}
		}
		return match.getBusca().getPaciente();
	}

	/**
	 * @return the prescricao
	 */
	public Prescricao getPrescricao() {
		return prescricao;
	}

	/**
	 * @param prescricao the prescricao to set
	 */
	public void setPrescricao(Prescricao prescricao) {
		this.prescricao = prescricao;
	}
	
	/**
	 * @return the match
	 */
	public Match getMatch() {
		return match;
	}

	/**
	 * @param match the match to set
	 */
	public void setMatch(Match match) {
		this.match = match;
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
		result = prime * result + ( ( doador == null ) ? 0 : doador.hashCode() );
		result = prime * result + ( ( id == null ) ? 0 : id.hashCode() );
		result = prime * result + ( ( tipoSolicitacao == null ) ? 0 : tipoSolicitacao.hashCode() );
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
		if (getClass() != obj.getClass()) {
			return false;
		}
		Solicitacao other = (Solicitacao) obj;
		if (doador == null) {
			if (other.doador != null) {
				return false;
			}
		}
		else
			if (!doador.equals(other.doador)) {
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
		if (tipoSolicitacao == null) {
			if (other.tipoSolicitacao != null) {
				return false;
			}
		}
		else
			if (!tipoSolicitacao.equals(other.tipoSolicitacao)) {
				return false;
			}
		return true;
	}

	/**
	 * @return
	 */
	public PedidoWorkup getPedidoWorkup() {
		return pedidoWorkup;
	}

	/**
	 * @param pedidoWorkup
	 */
	public void setPedidoWorkup(PedidoWorkup pedidoWorkup) {
		this.pedidoWorkup = pedidoWorkup;
	}

	/**
	 * Retorna a busca associado do pedido de exame CT.
	 * @return referência para entidade de busca.
	 */
	public Busca getBusca() {
		return busca;
	}

	public void setBusca(Busca busca) {
		this.busca = busca;
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
	 * @return the resolverDivergencia
	 */
	public Boolean getResolverDivergencia() {
		return resolverDivergencia;
	}

	/**
	 * @param resolverDivergencia the resolverDivergencia to set
	 */
	public void setResolverDivergencia(Boolean resolverDivergencia) {
		this.resolverDivergencia = resolverDivergencia;
	}

	/**
	 * @param paciente the paciente to set
	 */
	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	public Usuario getUsuarioResponsavel() {
		return usuarioResponsavel;
	}

	public void setUsuarioResponsavel(Usuario usuarioResponsavel) {
		this.usuarioResponsavel = usuarioResponsavel;
	}

	public Long getFaseWorkup() {
		return faseWorkup;
	}

	public void setFaseWorkup(Long faseWorkup) {
		this.faseWorkup = faseWorkup;
	}

	public MotivoCancelamentoSolicitacao getMotivoCancelamentoSolicitacao() {
		return motivoCancelamentoSolicitacao;
	}

	public void setMotivoCancelamentoSolicitacao(MotivoCancelamentoSolicitacao motivoCancelamentoSolicitacao) {
		this.motivoCancelamentoSolicitacao = motivoCancelamentoSolicitacao;
	}

	public CentroTransplante getCentroTransplante() {
		return centroTransplante;
	}

	public void setCentroTransplante(CentroTransplante centroTransplante) {
		this.centroTransplante = centroTransplante;
	}

	public CentroTransplante getCentroColeta() {
		return centroColeta;
	}

	public void setCentroColeta(CentroTransplante centroColeta) {
		this.centroColeta = centroColeta;
	}

	public Long getPosColeta() {
		return posColeta;
	}

	public void setPosColeta(Long posColeta) {
		this.posColeta = posColeta;
	}

	public Long getContagemCelula() {
		return contagemCelula;
	}

	public void setContagemCelula(Long contagemCelula) {
		this.contagemCelula = contagemCelula;
	}




	
	

}
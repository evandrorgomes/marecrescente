package br.org.cancer.modred.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.hibernate.envers.RelationTargetAuditMode;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.AnaliseMedicaView;
import br.org.cancer.modred.controller.view.AvaliacaoPedidoColetaView;
import br.org.cancer.modred.controller.view.AvaliacaoPrescricaoView;
import br.org.cancer.modred.controller.view.AvaliacaoResultadoWorkupView;
import br.org.cancer.modred.controller.view.BuscaView;
import br.org.cancer.modred.controller.view.DisponibilidadeView;
import br.org.cancer.modred.controller.view.DoadorView;
import br.org.cancer.modred.controller.view.FormularioView;
import br.org.cancer.modred.controller.view.PedidoColetaView;
import br.org.cancer.modred.controller.view.PedidoExameView;
import br.org.cancer.modred.controller.view.PedidoWorkupView;
import br.org.cancer.modred.controller.view.PrescricaoView;
import br.org.cancer.modred.controller.view.SolicitacaoView;
import br.org.cancer.modred.controller.view.TarefaLogisticaView;
import br.org.cancer.modred.controller.view.TarefaView;
import br.org.cancer.modred.controller.view.TransportadoraView;
import br.org.cancer.modred.controller.view.WorkupView;
import br.org.cancer.modred.model.annotations.CustomPast;
import br.org.cancer.modred.model.annotations.EnumValues;
import br.org.cancer.modred.model.domain.Abo;
import br.org.cancer.modred.model.domain.FasesMatch;
import br.org.cancer.modred.model.domain.Sexo;
import br.org.cancer.modred.model.domain.TiposDoador;
import br.org.cancer.modred.model.security.CriacaoAuditavel;
import br.org.cancer.modred.model.security.Usuario;
import br.org.cancer.modred.util.EnumUtil;

/**
 * Classe de persistencia de doador para a tabela doador.
 * 
 * @author Fillipe Queiroz
 * 
 */
@Entity
@SequenceGenerator(name = "SQ_DOAD_ID", sequenceName = "SQ_DOAD_ID", allocationSize = 1)
@Table(name = "DOADOR")
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "DOAD_IN_TIPO", discriminatorType = DiscriminatorType.INTEGER)
public abstract class Doador extends CriacaoAuditavel implements IProprietarioHla {

	private static final long serialVersionUID = -6876894252962171944L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_DOAD_ID")
	@Column(name = "DOAD_ID")
	@JsonView({ BuscaView.Enriquecimento.class, DoadorView.AtualizacaoFase2.class, DoadorView.ContatoPassivo.class,
			DoadorView.ContatoFase2.class,
			DoadorView.IdentificacaoCompleta.class, PedidoWorkupView.DetalheWorkup.class, TarefaView.Listar.class, PedidoWorkupView.AgendamentoWorkup.class,
			BuscaView.AvaliacaoPedidoColeta.class, WorkupView.Resultado.class,
			PedidoColetaView.DetalheColeta.class, BuscaView.AvaliacaoWorkupDoador.class, TarefaLogisticaView.Listar.class,
			TransportadoraView.Listar.class,
			TransportadoraView.AgendarTransporte.class, SolicitacaoView.detalhe.class, 
			TransportadoraView.Listar.class, TransportadoraView.AgendarTransporte.class, TarefaLogisticaView.Listar.class,
			PedidoExameView.ListarTarefas.class, PedidoColetaView.AgendamentoColeta.class, AvaliacaoPrescricaoView.Detalhe.class,
			SolicitacaoView.listar.class, FormularioView.DetalheDoador.class, DoadorView.LogisticaDoador.class})
	private Long id;

	@Column(name = "DOAD_DT_CADASTRO")
	private LocalDateTime dataCadastro;

	@Column(name = "DOAD_IN_SEXO")
	@NotNull
	@EnumValues(value = Sexo.class, message = "{sexo.invalido}")
	@JsonView({ BuscaView.Enriquecimento.class, DoadorView.AtualizacaoFase2.class, DoadorView.ContatoPassivo.class,
			DoadorView.ContatoFase2.class, DoadorView.IdentificacaoCompleta.class, WorkupView.Resultado.class,
			DoadorView.Internacional.class, AvaliacaoPrescricaoView.Detalhe.class, PrescricaoView.DetalheDoador.class,
			SolicitacaoView.detalhe.class})
	@Length(max = 1)
	private String sexo;

	@Column(name = "DOAD_TX_ABO")
	@Length(min=0, max = 3)
	@EnumValues(value = Abo.class, message = "{abo.invalido}")
	@JsonView({ DoadorView.AtualizacaoFase2.class, DoadorView.ContatoPassivo.class, DoadorView.Internacional.class,
				AvaliacaoPrescricaoView.Detalhe.class, PrescricaoView.DetalheDoador.class, SolicitacaoView.detalhe.class})
	private String abo;

	@Column(name = "DOAD_DT_NASCIMENTO")
	@CustomPast
	@JsonView({ BuscaView.Enriquecimento.class, DoadorView.AtualizacaoFase2.class, DoadorView.ContatoPassivo.class,
			DoadorView.ContatoFase2.class, DoadorView.IdentificacaoCompleta.class, WorkupView.Resultado.class,
			DoadorView.Internacional.class })
	private LocalDate dataNascimento;

	@NotNull
	@Column(name = "DOAD_DT_ATUALIZACAO")
	private LocalDateTime dataAtualizacao;

	@ManyToOne
	@JoinColumn(name = "STDO_ID")
	@NotNull
	@JsonView({ DoadorView.IdentificacaoCompleta.class, TarefaView.Consultar.class, BuscaView.Enriquecimento.class,
			WorkupView.Resultado.class, DoadorView.ContatoPassivo.class, DoadorView.Internacional.class })
	private StatusDoador statusDoador;

	@Column(name = "DOAD_DT_RETORNO_INATIVIDADE")
	@JsonView({ DoadorView.ContatoPassivo.class })
	private LocalDate dataRetornoInatividade;

	@ManyToOne
	@JoinColumn(name = "MOSD_ID")
	@Fetch(FetchMode.JOIN)
	@JsonView({ DoadorView.ContatoPassivo.class })
	private MotivoStatusDoador motivoStatusDoador;

	@OneToMany(mappedBy = "doador", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonView({ DoadorView.AtualizacaoFase2.class, DoadorView.ContatoPassivo.class })
	@NotAudited
	private List<RessalvaDoador> ressalvas;

	@OneToMany(mappedBy = "doador", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonView({ DoadorView.AtualizacaoFase2.class, DoadorView.ContatoPassivo.class })
	@NotAudited
	private List<PedidoContato> pedidos;
	
	@OneToMany(mappedBy = "doador", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@NotAudited
	private List<Match> matchs;

	@OneToOne(cascade = { CascadeType.ALL, CascadeType.REFRESH}, fetch = FetchType.LAZY, mappedBy="doador")
	//@JoinColumn(name = "GEDO_ID")
	@NotFound(action=NotFoundAction.IGNORE)
	private GenotipoDoador genotipo;

	@JsonView({ DoadorView.Internacional.class, PedidoExameView.ListarTarefas.class, 
				DoadorView.IdentificacaoCompleta.class, DoadorView.IdentificacaoParcial.class,
				PedidoWorkupView.CadastrarResultado.class, PedidoColetaView.AgendamentoColeta.class,
				TarefaLogisticaView.Listar.class, TransportadoraView.Listar.class, AvaliacaoPrescricaoView.Detalhe.class,
				PrescricaoView.DetalheDoador.class, SolicitacaoView.detalhe.class})
	@ManyToOne
	@JoinColumn(name = "REGI_ID_ORIGEM")
	@NotNull
	private Registro registroOrigem;

	@ManyToOne
	@JoinColumn(name = "USUA_ID")
	private Usuario usuario;

	@JsonView(DoadorView.Internacional.class)
	@Column(name = "DOAD_TX_GRID")
	@Length(min = 0, max = 25)
	private String grid;

	@EnumValues(TiposDoador.class)
	@Column(name = "DOAD_IN_TIPO", insertable = false, updatable = false)
	@JsonView({	PedidoExameView.ListarTarefas.class, PedidoExameView.ObterParaEditar.class, 
				AvaliacaoPrescricaoView.Detalhe.class, PedidoColetaView.AgendamentoColeta.class,
				PedidoWorkupView.CadastrarResultado.class, PedidoColetaView.DetalheColeta.class,
				TarefaLogisticaView.Listar.class, TransportadoraView.Listar.class, 
				TransportadoraView.AgendarTransporte.class,PedidoWorkupView.DetalheWorkup.class, AnaliseMedicaView.ListarTarefas.class,
				BuscaView.Enriquecimento.class, DoadorView.ContatoPassivo.class, DoadorView.AtualizacaoFase2.class,
				DoadorView.ContatoFase2.class, AvaliacaoResultadoWorkupView.ListarAvaliacao.class, AvaliacaoPrescricaoView.ListarAvaliacao.class,
				WorkupView.Resultado.class, WorkupView.ResultadoParaAvaliacao.class, AvaliacaoPedidoColetaView.Detalhe.class,
				PrescricaoView.DetalheDoador.class, SolicitacaoView.detalhe.class, SolicitacaoView.listar.class})
	private Long tipoDoador;
	
	/**
	 * Indica em que fase está o doador.
	 * 1° fase: Match inicial com paciente.
	 * 2° fase: A, B, C, DR e DQ de média pra cima (Alelos e grupos NMDP, G e P)
	 */
	@EnumValues(FasesMatch.class)
	@Column(name = "DOAD_TX_FASE")
	private String fase;
	
	public Doador() {
		super();
	}

	/**
	 * Método construtor utilizado para a identificação do doador.
	 * 
	 * @param id - identificador do doador
	 * @param sexo - sexo do doador
	 * @param dataNascimento - Data de Nascimento do doador
	 * @param statusDoador - Status do doador
	 */
	public Doador(Long id, String sexo, LocalDate dataNascimento, StatusDoador statusDoador) {
		super();
		this.id = id;
		this.sexo = sexo;
		this.dataNascimento = dataNascimento;
		this.statusDoador = statusDoador;
	}

	/**
	 * Construtor para facilitar a instanciação do objeto, a partir do seu identificador.
	 * 
	 * @param idDoador identificador da entidade.
	 */
	public Doador(Long id) {
		super();
		this.id = id;
	}

	public Doador(Long id, Long tipoDoador) {
		super();
		this.id = id;
		this.setTipoDoador(TiposDoador.NACIONAL);
	}

	
	/**
	 * Data de cadastro do doador.
	 * 
	 * @return
	 */
	public LocalDateTime getDataCadastro() {
		return dataCadastro;
	}

	/**
	 * Data de cadastro do doador.
	 * 
	 * @param dataCadastro
	 */
	protected void setDataCriacao(LocalDateTime dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	/**
	 * Sexo do doador.
	 * 
	 * @return
	 */
	public String getSexo() {
		return sexo;
	}

	/**
	 * Sexo do doador.
	 * 
	 * @param sexo
	 */
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	/**
	 * Abo do doador.
	 * 
	 * @return
	 */
	public String getAbo() {
		return abo;
	}

	/**
	 * Abo do doador.
	 * 
	 * @param abo
	 */
	public void setAbo(String abo) {
		this.abo = abo;
	}

	/**
	 * Data de nascimento do doador.
	 * 
	 * @return
	 */
	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	/**
	 * Data de nascimento do doador.
	 * 
	 * @param dataNascimento
	 */
	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	/**
	 * Data de atualizacao do doador.
	 * 
	 * @return
	 */
	public LocalDateTime getDataAtualizacao() {
		return dataAtualizacao;
	}

	/**
	 * Data de atualizacao do doador.
	 * 
	 * @param dataAtualizacao
	 */
	public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}

	/**
	 * Status do doador.
	 * 
	 * @return
	 */
	public StatusDoador getStatusDoador() {
		return statusDoador;
	}

	/**
	 * Status do doador.
	 * 
	 * @param statusDoador
	 */
	public void setStatusDoador(StatusDoador statusDoador) {
		this.statusDoador = statusDoador;
	}

	/**
	 * Motivo do status do doador.
	 * 
	 * @return
	 */
	public MotivoStatusDoador getMotivoStatusDoador() {
		return motivoStatusDoador;
	}

	/**
	 * Motivo do status do doador.
	 * 
	 * @param motivoStatusDoador
	 */
	public void setMotivoStatusDoador(MotivoStatusDoador motivoStatusDoador) {
		this.motivoStatusDoador = motivoStatusDoador;
	}

	/**
	 * Ressalvas do doador.
	 * 
	 * @return
	 */
	public List<RessalvaDoador> getRessalvas() {
		return ressalvas;
	}

	/**
	 * Ressalvas do doador.
	 * 
	 * @param ressalvas
	 */
	public void setRessalvas(List<RessalvaDoador> ressalvas) {
		this.ressalvas = ressalvas;
	}

	public LocalDate getDataRetornoInatividade() {
		return dataRetornoInatividade;
	}

	public void setDataRetornoInatividade(LocalDate dataRetornoInatividade) {
		this.dataRetornoInatividade = dataRetornoInatividade;
	}

	/**
	 * @return the genotipo
	 */
	public GenotipoDoador getGenotipo() {
		return genotipo;
	}

	/**
	 * @param genotipo the genotipo to set
	 */
	public void setGenotipo(GenotipoDoador genotipo) {
		this.genotipo = genotipo;
	}

	/**
	 * @return the matchs
	 */
	public List<Match> getMatchs() {
		return matchs;
	}

	/**
	 * @param matchs the matchs to set
	 */
	public void setMatchs(List<Match> matchs) {
		this.matchs = matchs;
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
	 * @return the registroOrigem
	 */
	public Registro getRegistroOrigem() {
		return registroOrigem;
	}

	/**
	 * @param registroOrigem the registroOrigem to set
	 */
	public void setRegistroOrigem(Registro registroOrigem) {
		this.registroOrigem = registroOrigem;
	}

	/**
	 * @return the pedidos
	 */
	public List<PedidoContato> getPedidos() {
		return pedidos;
	}

	/**
	 * @param pedidos the pedidos to set
	 */
	public void setPedidos(List<PedidoContato> pedidos) {
		this.pedidos = pedidos;
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
		result = prime * result + ( ( id == null ) ? 0 : id.hashCode() );
		
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
		if (!( obj instanceof Doador )) {
			return false;
		}
		Doador other = (Doador) obj;
		if (abo == null) {
			if (other.abo != null) {
				return false;
			}
		}
		else
			if (!abo.equals(other.abo)) {
				return false;
			}
		if (dataAtualizacao == null) {
			if (other.dataAtualizacao != null) {
				return false;
			}
		}
		else
			if (!dataAtualizacao.equals(other.dataAtualizacao)) {
				return false;
			}
		if (dataCadastro == null) {
			if (other.dataCadastro != null) {
				return false;
			}
		}
		else
			if (!dataCadastro.equals(other.dataCadastro)) {
				return false;
			}
		if (dataNascimento == null) {
			if (other.dataNascimento != null) {
				return false;
			}
		}
		else
			if (!dataNascimento.equals(other.dataNascimento)) {
				return false;
			}
		if (dataRetornoInatividade == null) {
			if (other.dataRetornoInatividade != null) {
				return false;
			}
		}
		else
			if (!dataRetornoInatividade.equals(other.dataRetornoInatividade)) {
				return false;
			}
		if (genotipo == null) {
			if (other.genotipo != null) {
				return false;
			}
		}
		else
			if (!genotipo.equals(other.genotipo)) {
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
		if (matchs == null) {
			if (other.matchs != null) {
				return false;
			}
		}
		else
			if (!matchs.equals(other.matchs)) {
				return false;
			}
		if (motivoStatusDoador == null) {
			if (other.motivoStatusDoador != null) {
				return false;
			}
		}
		else
			if (!motivoStatusDoador.equals(other.motivoStatusDoador)) {
				return false;
			}
		if (ressalvas == null) {
			if (other.ressalvas != null) {
				return false;
			}
		}
		else
			if (!ressalvas.equals(other.ressalvas)) {
				return false;
			}
		if (sexo == null) {
			if (other.sexo != null) {
				return false;
			}
		}
		else
			if (!sexo.equals(other.sexo)) {
				return false;
			}
		if (statusDoador == null) {
			if (other.statusDoador != null) {
				return false;
			}
		}
		else
			if (!statusDoador.equals(other.statusDoador)) {
				return false;
			}
		if (registroOrigem == null) {
			if (other.registroOrigem != null) {
				return false;
			}
		}
		else
			if (!registroOrigem.equals(other.registroOrigem)) {
				return false;
			}
		return true;
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
	 * Identificador único internacional do doador.
	 * 
	 * @return valor string do código internacional.
	 */
	public String getGrid() {
		return grid;
	}

	public void setGrid(String grid) {
		this.grid = grid;
	}

	/**
	 * @return the tipoDoador
	 */
	public TiposDoador getTipoDoador() {
		if (tipoDoador != null) {
			return (TiposDoador) EnumUtil.valueOf(TiposDoador.class, tipoDoador);
		}
		return null;
	}
	
	/**
	 * @return the tipoDoador
	 */
	@JsonView({ AvaliacaoPrescricaoView.Detalhe.class, DisponibilidadeView.VisualizacaoCentroTransplante.class,
				DoadorView.IdentificacaoCompleta.class, DoadorView.IdentificacaoParcial.class, 
				TarefaLogisticaView.Listar.class, TransportadoraView.Listar.class, 
				TransportadoraView.AgendarTransporte.class, SolicitacaoView.detalhe.class, SolicitacaoView.listar.class})
	public Long getIdTipoDoador() {
		return tipoDoador;
	}

	/**
	 * @param tipoDoador the tipoDoador to set
	 */
	public void setTipoDoador(TiposDoador tipoDoador) {
		if (tipoDoador != null) {
			this.tipoDoador = tipoDoador.getId();
		}

	}

	/**
	 * @param dataCadastro the dataCadastro to set
	 */
	public void setDataCadastro(LocalDateTime dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	/**
	 * Indica se o doador é nacional.
	 * 
	 * @return TRUE se for nacional.
	 */
	public boolean isNacional(){
		return !isInternacional();
	}
	
	/**
	 * Indica se o doador é internacional.
	 * 
	 * @return TRUE se for internacional. 
	 */
	public boolean isInternacional(){
		return TiposDoador.INTERNACIONAL.getId().equals(this.getIdTipoDoador()) || 
					TiposDoador.CORDAO_INTERNACIONAL.getId().equals(this.getIdTipoDoador());
	}
	
	public boolean isMedula(){
		return (TiposDoador.NACIONAL.getId().equals(this.getIdTipoDoador()) || 
					TiposDoador.INTERNACIONAL.getId().equals(this.getIdTipoDoador()));
	}
	
	public boolean isCordao(){
		return !isMedula();
	}

	/**
	 * Indica a fase que o doador está e, caso ocorra match,
	 * define a coluna que será exibido no carrousel.
	 *  
	 * @return Fase que encontra-se o usuário.
	 */
	public String getFase() {
		return fase;
	}

	public void setFase(String fase) {
		this.fase = fase;
	}
	
	/**
	 * Verifica se o doador é inativo.
	 * 
	 * @return Boolean - retorna a verificacao se o doador é inativo.
	 */
	public	Boolean isInativo() {
		return this.getStatusDoador() != null && 
				(this.getStatusDoador().getId().equals(StatusDoador.INATIVO_PERMANENTE) 
			|| this.getStatusDoador().getId().equals(StatusDoador.INATIVO_TEMPORARIO));  
	}
	
	/**
	 * Método Bean Validation para verificar a obrigatoriedade de ABO.
	 * 
	 * @return boolean true se o ABO não for informado para dodor internacional 
	 */
//	@AssertTrueCustom(message = "{abo.invalido}", field = "abo")
//	@JsonIgnore
//	public boolean isAboObrigatorio() {
//		if (StringUtils.isEmpty(this.abo) && TiposDoador.INTERNACIONAL.getId().equals(this.getTipoDoador().getId())) {
//			return true;
//		}
//		else if (StringUtils.isEmpty(this.abo)) {
//			return false;
//		}
//	
//		return true;
//	}
		
	
}
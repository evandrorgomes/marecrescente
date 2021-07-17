package br.org.cancer.modred.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.hibernate.envers.RelationTargetAuditMode;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.configuration.ConversaoFonetica;
import br.org.cancer.modred.controller.view.AvaliacaoCamaraTecnicaView;
import br.org.cancer.modred.controller.view.AvaliacaoNovaBuscaView;
import br.org.cancer.modred.controller.view.AvaliacaoPrescricaoView;
import br.org.cancer.modred.controller.view.AvaliacaoResultadoWorkupView;
import br.org.cancer.modred.controller.view.AvaliacaoView;
import br.org.cancer.modred.controller.view.BuscaView;
import br.org.cancer.modred.controller.view.EncontrarCentroTransplanteView;
import br.org.cancer.modred.controller.view.EvolucaoView;
import br.org.cancer.modred.controller.view.ExameView;
import br.org.cancer.modred.controller.view.LaboratorioView;
import br.org.cancer.modred.controller.view.NotificacaoView;
import br.org.cancer.modred.controller.view.PacienteView;
import br.org.cancer.modred.controller.view.PageView;
import br.org.cancer.modred.controller.view.PedidoColetaView;
import br.org.cancer.modred.controller.view.PedidoTransferenciaCentroView;
import br.org.cancer.modred.controller.view.PedidoWorkupView;
import br.org.cancer.modred.controller.view.PrescricaoView;
import br.org.cancer.modred.controller.view.ProcessoView;
import br.org.cancer.modred.controller.view.SolicitacaoView;
import br.org.cancer.modred.controller.view.TarefaLogisticaView;
import br.org.cancer.modred.controller.view.TarefaView;
import br.org.cancer.modred.controller.view.TransportadoraView;
import br.org.cancer.modred.model.annotations.AssertTrueCustom;
import br.org.cancer.modred.model.annotations.CustomPast;
import br.org.cancer.modred.model.annotations.EnumValues;
import br.org.cancer.modred.model.domain.Abo;
import br.org.cancer.modred.model.domain.AceiteMismatch;
import br.org.cancer.modred.model.domain.Sexo;
import br.org.cancer.modred.model.security.Usuario;
import br.org.cancer.modred.util.AppUtil;

/**
 * Classe de persistencia de paciente para a tabela paciente.
 * 
 * @author Filipe Paes
 * 
 */
@Entity
@SequenceGenerator(name = "SQ_PACI_NR_RMR", sequenceName = "SQ_PACI_NR_RMR", allocationSize = 1)
@Table(name = "PACIENTE")
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Paciente implements IProprietarioHla {

	private static final long serialVersionUID = -2156041591313391511L;

	/**
	 * Chave que identifica com exclusividade uma instância desta classe.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_PACI_NR_RMR")
	@Column(name = "PACI_NR_RMR")
	@JsonView(value = { PacienteView.Detalhe.class, PacienteView.Consulta.class,
			PacienteView.IdentificacaoCompleta.class,
			PacienteView.IdentificacaoParcial.class,
			EvolucaoView.ListaEvolucao.class,
			ExameView.ListaExame.class,
			AvaliacaoView.Avaliacao.class,
			ExameView.ConferirExame.class,
			ProcessoView.Listar.class,
			PacienteView.DadosPessoais.class,
			BuscaView.Busca.class,
			TarefaView.Listar.class,
			BuscaView.AvaliacaoPedidoColeta.class,
			AvaliacaoPrescricaoView.ListarAvaliacao.class,
			EncontrarCentroTransplanteView.ListarBuscas.class,
			TransportadoraView.Listar.class, TransportadoraView.AgendarTransporte.class,
			LaboratorioView.ListarReceberExame.class,
			PedidoWorkupView.AgendamentoWorkup.class,
			AvaliacaoResultadoWorkupView.ListarAvaliacao.class,
			PageView.ListarPaginacao.class,
			BuscaView.AvaliacaoPedidoColeta.class,
			PedidoColetaView.SugerirDataColeta.class,
			PrescricaoView.AutorizacaoPacienteListar.class,
			PedidoTransferenciaCentroView.ListarTarefas.class,
			PedidoTransferenciaCentroView.Detalhe.class,
			AvaliacaoCamaraTecnicaView.ListarTarefas.class,
			PedidoWorkupView.SugerirDataWorkup.class,
			AvaliacaoCamaraTecnicaView.Detalhe.class,
			NotificacaoView.ListarNotificacao.class,
			TarefaLogisticaView.Listar.class,
			AvaliacaoPrescricaoView.Detalhe.class,
			SolicitacaoView.detalhe.class, SolicitacaoView.listar.class
	})
	private Long rmr;

	/**
	 * Data e hora de cadastro do usuario.
	 */
	@Column(name = "PACI_DT_CADASTRO")
	@NotNull
	@JsonView({PacienteView.Detalhe.class, PacienteView.Rascunho.class })
	private LocalDateTime dataCadastro;

	/**
	 * Data e hora de nascimento do usuario.
	 */
	@Column(name = "PACI_DT_NASCIMENTO")
	@NotNull
	@CustomPast
	@JsonView(value = { PacienteView.Detalhe.class, PacienteView.Consulta.class,
			PacienteView.IdentificacaoCompleta.class, PacienteView.IdentificacaoParcial.class,
			PacienteView.DadosPessoais.class, PrescricaoView.AutorizacaoPacienteListar.class,
			PedidoTransferenciaCentroView.ListarTarefas.class, PacienteView.Diagnostico.class,
			PacienteView.Rascunho.class, SolicitacaoView.detalhe.class})
	private LocalDate dataNascimento;

	/**
	 * Sexo.
	 */
	@Column(name = "PACI_IN_SEXO")
	@NotNull
	@EnumValues(value = Sexo.class, message = "{sexo.invalido}")
	@JsonView(value = { PacienteView.Detalhe.class, PacienteView.Consulta.class,
			PacienteView.IdentificacaoCompleta.class, PacienteView.IdentificacaoParcial.class,
			PacienteView.DadosPessoais.class, PacienteView.Rascunho.class })
	private String sexo;

	/**
	 * Tipo sanguinio.
	 */
	@Column(name = "PACI_TX_ABO")
	@NotNull
	@EnumValues(value = Abo.class, message = "{abo.invalido}")
	@JsonView({ PacienteView.Detalhe.class, PacienteView.IdentificacaoCompleta.class,
			PacienteView.IdentificacaoParcial.class, PacienteView.DadosPessoais.class,
			PacienteView.Rascunho.class})
	private String abo;

	/**
	 * Código CNS.
	 */
	@Column(name = "PACI_TX_CNS")	
	@Length(max = 15)
	@JsonView({ PacienteView.Detalhe.class, PacienteView.DadosPessoais.class, PacienteView.Rascunho.class })
	private String cns;

	/**
	 * CPF.
	 */
	@Column(name = "PACI_TX_CPF")
	@CPF
	@JsonView({ PacienteView.Detalhe.class, PacienteView.DadosPessoais.class, PacienteView.Rascunho.class })
	private String cpf;

	/**
	 * Email para contato.
	 */
	@Column(name = "PACI_TX_EMAIL")
	@Email
	@Length(max = 100)
	@JsonView({ PacienteView.Detalhe.class, PacienteView.Rascunho.class})
	private String email;

	/**
	 * Nome do paciente.
	 */
	@Column(name = "PACI_TX_NOME")
	@NotNull
	@Pattern(regexp = "^[^\\d\\.]+$", message = "{nome.caracteresInvalidos}")
	@Length(max = 255)
	@JsonView(value = { PacienteView.Detalhe.class, PacienteView.Consulta.class, PacienteView.IdentificacaoCompleta.class,
			PacienteView.DadosPessoais.class, TarefaView.Listar.class, BuscaView.AvaliacaoPedidoColeta.class,
			AvaliacaoPrescricaoView.ListarAvaliacao.class,
			EncontrarCentroTransplanteView.ListarBuscas.class
			,PedidoWorkupView.AgendamentoWorkup.class
			, AvaliacaoResultadoWorkupView.ListarAvaliacao.class
			,BuscaView.AvaliacaoPedidoColeta.class, PedidoColetaView.SugerirDataColeta.class,
			PrescricaoView.AutorizacaoPacienteListar.class, ExameView.ListaExame.class,
			AvaliacaoCamaraTecnicaView.ListarTarefas.class, PedidoWorkupView.SugerirDataWorkup.class,
			PacienteView.Rascunho.class, AvaliacaoNovaBuscaView.ListarTarefas.class, SolicitacaoView.detalhe.class,
			SolicitacaoView.listar.class})
	private String nome;

	@Column(name = "PACI_TX_NOME_FONETIZADO", nullable = false)
	@JsonIgnore
	private String nomeFonetizado;

	/**
	 * Nome da mãe do paciente.
	 */
	@Column(name = "PACI_TX_NOME_MAE")
	@Pattern(regexp = "^[^\\d\\.]+$", message = "{nome.caracteresInvalidos}")
	@Length(max = 255)
	@JsonView({ PacienteView.Detalhe.class, PacienteView.DadosPessoais.class, PacienteView.Rascunho.class })
	private String nomeMae;

	/**
	 * Referencia de etnia.
	 */
	@ManyToOne
	@JoinColumn(name = "ETNI_ID")
	@JsonView({ PacienteView.Detalhe.class, PacienteView.IdentificacaoCompleta.class,
			PacienteView.IdentificacaoParcial.class, PacienteView.DadosPessoais.class,
			PacienteView.Rascunho.class})
	private Etnia etnia;

	/**
	 * Referencia do país.
	 */
	@ManyToOne
	@JoinColumn(name = "PAIS_ID")
	@NotNull
	@JsonView({ PacienteView.Detalhe.class, PacienteView.DadosPessoais.class, PacienteView.Rascunho.class })
	private Pais pais;

	/**
	 * Referencia de raça.
	 */
	@ManyToOne
	@JoinColumn(name = "RACA_ID")
	@NotNull
	@JsonView({ PacienteView.Detalhe.class, PacienteView.IdentificacaoCompleta.class,
			PacienteView.IdentificacaoParcial.class, PacienteView.DadosPessoais.class,
			PacienteView.Rascunho.class})
	private Raca raca;

	/**
	 * Referencia do usuário que cadastrou.
	 */
	@ManyToOne
	@JoinColumn(name = "USUA_ID")
	@NotNull
	@Fetch(FetchMode.JOIN)
	private Usuario usuario;

	/**
	 * Lista de endereços do paciente.
	 */
	@OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@NotEmpty
	@Valid
	@JsonView({PacienteView.Detalhe.class, PacienteView.Rascunho.class})
	@Fetch(FetchMode.SUBSELECT)
	private List<EnderecoContatoPaciente> enderecosContato;

	/**
	 * Lista de telefones do paciente.
	 */
	@OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@NotEmpty
	@Valid
	@JsonView({PacienteView.Detalhe.class, PacienteView.Rascunho.class})
	@Fetch(FetchMode.SUBSELECT)
	private List<ContatoTelefonicoPaciente> contatosTelefonicos;

	/**
	 * Naturalidade.
	 */
	@ManyToOne
	@JoinColumn(name = "UF_SIGLA")
	@JsonView({ PacienteView.Detalhe.class, PacienteView.DadosPessoais.class, PacienteView.Rascunho.class })
	@Fetch(FetchMode.JOIN)
	private Uf naturalidade;
	/**
	 * Referencia do responsavel do paciente.
	 */

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "RESP_ID")
	@Valid
	@JsonView({ PacienteView.Detalhe.class, PacienteView.DadosPessoais.class, PacienteView.Rascunho.class })
	@Fetch(FetchMode.JOIN)
	private Responsavel responsavel;

	/**
	 * Referencia do diagnostico do paciente.
	 */
	@NotNull
	@Valid
	@OneToOne(	cascade = CascadeType.ALL, mappedBy = "paciente",
				orphanRemoval = true)
	@JsonView({ PacienteView.IdentificacaoCompleta.class, PacienteView.DadosPessoais.class,
			PacienteView.IdentificacaoParcial.class, PacienteView.Detalhe.class,
			PedidoTransferenciaCentroView.ListarTarefas.class, PacienteView.Rascunho.class})
	@Fetch(FetchMode.JOIN)
	@JsonIgnoreProperties("paciente")
	private Diagnostico diagnostico;

	/**
	 * Referencia as evoluçoes do paciente.
	 */
	@NotEmpty
	@Valid
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "paciente", fetch = FetchType.LAZY)
	@NotAudited
	@JsonView({PacienteView.Rascunho.class,PacienteView.IdentificacaoCompleta.class,PacienteView.IdentificacaoParcial.class})
	private List<Evolucao> evolucoes;

	/**
	 * Referencia as evoluçoes do paciente.
	 */
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "paciente", fetch = FetchType.LAZY)
	@NotAudited
	@JsonView(PacienteView.Detalhe.class)
	@Fetch(FetchMode.SUBSELECT)
	private List<Busca> buscas;

	/**
	 * Referência aos exames do paciente.
	 */
	@NotEmpty
	@Valid
	@OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	@JoinColumn(name="PACI_NR_RMR", referencedColumnName="PACI_NR_RMR")
	@NotAudited
	@JsonView({ PacienteView.Detalhe.class, PacienteView.Rascunho.class })
	@Fetch(FetchMode.SUBSELECT)
	@JsonIgnoreProperties("paciente")
	private List<ExamePaciente> exames;
	
	/**
	 * Caso o cadastro paciente seja definido como não aparentado (veja o campo motivoCadastro) o centro avaliador passa a ser
	 * obrigatório.
	 */
	@NotNull
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "CETR_ID_AVALIADOR")
	@JsonView({PacienteView.Consulta.class, PacienteView.Detalhe.class, PacienteView.Rascunho.class})
	private CentroTransplante centroAvaliador;

	/**
	 * Médico responsável pelo paciente.
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MEDI_ID_RESPONSAVEL")
	@NotNull
	@JsonView(value = { PacienteView.Detalhe.class, PacienteView.Consulta.class, PacienteView.IdentificacaoParcial.class,
			AvaliacaoView.Avaliacao.class, EvolucaoView.ListaEvolucao.class, ExameView.ListaExame.class })
	@Fetch(FetchMode.JOIN)
	private Medico medicoResponsavel;

	/**
	 * Aceita Mismatch. 0 - Não Aceita Mismach 1 - Aceita.
	 */
	@NotNull
	@Column(name = "PACI_IN_ACEITA_MISMATCH")
	@EnumValues(AceiteMismatch.class)
	@JsonView({PacienteView.Detalhe.class, PacienteView.Rascunho.class})
	private Integer aceiteMismatch;

	@OneToMany(cascade = CascadeType.REFRESH, mappedBy = "paciente", fetch = FetchType.LAZY)
	private List<Avaliacao> avaliacoes;

	/**
	 * Indica a quantidade de notificações que existem associadas a este paciente. FIXME: Este é um atributo provisório, intenção
	 * é que isso seja exibido para todos os pacientes de um determinado médico e seja exibido na tela inicial.
	 */
	@Transient
	@JsonView({ PacienteView.Detalhe.class, PacienteView.Consulta.class })
	private Long quantidadeNotificacoes;
	
	/**
	 * Indica a quantidade de notificações, não lidas, que existem associadas a este paciente. FIXME: Este é um atributo provisório, intenção
	 * é que isso seja exibido para todos os pacientes de um determinado médico e seja exibido na tela inicial.
	 */
	@Transient
	@JsonView({ PacienteView.Detalhe.class, PacienteView.Consulta.class })
	private Long quantidadeNotificacoesNaoLidas;

	@Column(name = "PACI_NR_SCORE", insertable = false, updatable = false)
	@JsonView({ TarefaView.Listar.class, PedidoWorkupView.AgendamentoWorkup.class, PedidoColetaView.SugerirDataColeta.class,
				PedidoWorkupView.SugerirDataWorkup.class})
	private Float score;

	/**
	 * Flag que indica, somente no front-end, que o nome da mãe
	 * é inexistente no cadastro do paciente.
	 * Criamos esse atributo para guardar no rascunho a informação
	 * e conseguir utilizá-lo na recuperação.
	 */
	@Transient
	@JsonView(PacienteView.Rascunho.class)
	private Boolean maeDesconhecida;
	
	/**
	 * Esse atributo deve ser populado na consulta de paciente.
	 */
	@Transient
	@JsonView({ PacienteView.Detalhe.class, PacienteView.IdentificacaoCompleta.class})
	private GenotipoPaciente genotipo;
	
	
	/**
	 * Atributo que guarda o relacionamento entre o paciente e 
	 * os locus em que é aceito o Mismatch para ele.
	 */
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(	name = "PACIENTE_MISMATCH",
				joinColumns =
	{ @JoinColumn(name = "PACI_NR_RMR") },
				inverseJoinColumns =
	{ @JoinColumn(name = "LOCU_ID") })
	@NotAudited
	@JsonView({PacienteView.Detalhe.class, PacienteView.Rascunho.class})
	@OrderBy("ordem asc")
	private List<Locus> locusMismatch;
	
	
	
	/**
	 * Quando o paciente possui um pedido de transferência em aberto.
	 */
	@Transient
	@JsonView({ PacienteView.Detalhe.class })
	private boolean temPedidoTransferenciaCentroAvaliadorEmAberto;
	
	/**
	 * Quando o paciente possui uma avaliação em aberto.
	 */
	@Transient
	@JsonView({ PacienteView.Detalhe.class, PacienteView.DadosPessoais.class })
	private boolean temAvaliacaoEmAberto;
	
	/**
	 * Quando o paciente possui uma avaliação em andamento (iniciada).
	 */
	@Transient
	@JsonView({ PacienteView.Detalhe.class, PacienteView.DadosPessoais.class })
	private boolean temAvaliacaoIniciada;
	
	/**
	 * Quando o paciente possui uma avaliação aprovada.
	 */
	@Transient
	@JsonView({ PacienteView.Detalhe.class, PacienteView.DadosPessoais.class })
	private boolean temAvaliacaoAprovada;
	

	/**
	 * Quando o paciente possui um pedido de exama fase3 ativo.
	 */
	@Transient
	@JsonView({ PacienteView.Detalhe.class, PacienteView.DadosPessoais.class })
	private Long idTipoExameFase3;
	
	@NotAudited
	@ManyToOne
	@JoinColumn(name="STPA_ID")
	@JsonView({ PacienteView.IdentificacaoCompleta.class, PacienteView.IdentificacaoParcial.class, PacienteView.Consulta.class })
	private StatusPaciente status;
	
	@Column(name="PACI_NR_TEMPO_TRANSPLANTE")
	private Integer tempoParaTransplante;
	
	/**
	 * Indica se paciente possui busca ativa.
	 */
	@Transient
	@JsonView({ PacienteView.Detalhe.class, PacienteView.Consulta.class})
	private boolean temBuscaAtiva;
	
	
	@Column(name = "PACI_TX_WMDA_ID")
	private String wmdaId;

	/**
	 * Construtor padrão.
	 */
	public Paciente() {
		super();
	}

	/**
	 * Construtor com sobrecarga. Cria um paciente com o RMR informado.
	 *
	 * @param rmr identificação do paciente
	 */
	public Paciente(Long rmr) {
		this();
		this.rmr = rmr;
	}

	/**
	 * Construtor com sobrecarga. Cria um paciente com o RMR informado.
	 *
	 * @param rmr identificação do paciente
	 * @param email email do paciente
	 */
	public Paciente(Long rmr, String email) {
		this(rmr);
		this.email = email;
	}

	/**
	 * Construtor sobrecarregado para pegar apenas os contatos do paciente.
	 * 
	 * @param rmr do paciente.
	 * @param email do paciente.
	 * @param enderecos endereços de contato do paciente.
	 * @param telefones de contato informados para o paciente.
	 */
	public Paciente(Long rmr, String email, Collection<EnderecoContatoPaciente> enderecos,
			Collection<ContatoTelefonicoPaciente> telefones) {
		this();
		this.rmr = rmr;
		this.email = email;
		this.enderecosContato = (List<EnderecoContatoPaciente>) enderecos;
		this.contatosTelefonicos = (List<ContatoTelefonicoPaciente>) telefones;
	}

	/**
	 * Construtor sobrecarregado.
	 * 
	 * @param rmr identificação do paciente
	 * @param nome do paciente
	 * @param sexo do paciente
	 * @param dataNascimento do paciente
	 * @param medicoResponsavel do paciente
	 */
	public Paciente(Long rmr, String nome, String sexo, LocalDate dataNascimento,
			Medico medicoResponsavel) {
		this(rmr);
		this.nome = nome;
		this.sexo = sexo;
		this.dataNascimento = dataNascimento;
		this.medicoResponsavel = medicoResponsavel;
	}

	/**
	 * Construtor sobrecarregado.
	 * 
	 * @param rmr rmr
	 * @param nome nome
	 * @param sexo sexo
	 * @param abo abo
	 * @param raca raca
	 * @param dataNascimento dt
	 * @param diagnostico diagnostico
	 * @param medicoResponsavel medico responsavel
	 * @param etnia etnia
	 * @param status do paciente
	 */
	public Paciente(Long rmr, String nome, String sexo, String abo, Raca raca,
			LocalDate dataNascimento,
			Diagnostico diagnostico,
			Medico medicoResponsavel,
			Etnia etnia, StatusPaciente status) {
		this(rmr, nome, sexo, dataNascimento, medicoResponsavel);
		this.abo = abo;
		this.raca = raca;
		this.etnia = etnia;
		this.diagnostico = diagnostico;
		this.status = status;
	}

	/**
	 * Construtor sobrecarregado para que seja possível criar instâncias a partir do hql.
	 * 
	 * @param rmr identificador do paciente.
	 * @param cpf CPF do paciente.
	 * @param cns CNS do paciente.
	 * @param nome nome do paciente.
	 * @param nomeMae nome da mãe do paciente.
	 * @param sexo sexo do paciente.
	 * @param abo ABO do paciente.
	 * @param raca Raça do paciente.
	 * @param dataNascimento Data de nascimento do paciente.
	 * @param etnia Etnia do paciente.
	 * @param pais Pais de origem do paciente.
	 * @param naturalidade UF de origem do paciente, caso brasileiro.
	 * @param responsavel responsável do paciente, caso menor de idade.
	 */
	public Paciente(Long rmr, String cpf, String cns, String nome, String nomeMae, String sexo, String abo, Raca raca,
			LocalDate dataNascimento, Etnia etnia, Pais pais, Uf naturalidade, Responsavel responsavel) {
		this(rmr, nome, sexo, abo, raca, dataNascimento, null, null, etnia, null);
		this.cpf = cpf;
		this.cns = cns;
		this.nomeMae = nomeMae;
		this.pais = pais;
		this.naturalidade = naturalidade;
		this.responsavel = responsavel;
	}
	
	/**
	 * Construtor sobrecarregado para que seja possível criar instâncias a partir do hql.
	 * 
	 * @param rmr
	 * @param cpf
	 * @param cns
	 * @param nome
	 * @param nomeMae
	 * @param sexo
	 * @param abo
	 * @param raca
	 * @param dataNascimento
	 * @param etnia
	 * @param pais
	 * @param naturalidade
	 * @param responsavel
	 * @param diagnostico
	 */
	public Paciente(Long rmr, String cpf, String cns, String nome, String nomeMae, String sexo, String abo, Raca raca,
			LocalDate dataNascimento, Etnia etnia, Pais pais, Uf naturalidade, Responsavel responsavel, Diagnostico diagnostico) {
		this(rmr, cpf, cns, nome, nomeMae, sexo, abo, raca, dataNascimento, etnia, pais, naturalidade, responsavel);
		this.diagnostico = diagnostico;
	}

	/**
	 * Construtor sobrecarregado para facilitar a recuperação simples do paciente informado apenas se o mesmo é nacional ou
	 * internacional.
	 * 
	 * @param rmr identificação do paciente.
	 * @param nacionalidade país de origem do paciente.
	 */
	public Paciente(Long rmr, Pais nacionalidade) {
		this(rmr);
		this.pais = nacionalidade;
	}

	/**
	 * @return the rmr
	 */
	public Long getRmr() {
		return rmr;
	}

	/**
	 * @param rmr the rmr to set
	 */
	public void setRmr(Long rmr) {
		this.rmr = rmr;
	}

	/**
	 * @return the dataCadastro
	 */
	public LocalDateTime getDataCadastro() {
		return dataCadastro;
	}

	/**
	 * @param dataCadastro the dataCadastro to set
	 */
	public void setDataCadastro(LocalDateTime dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	/**
	 * @return the dataNascimento
	 */
	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	/**
	 * @param dataNascimento the dataNascimento to set
	 */
	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	/**
	 * @return the sexo
	 */
	public String getSexo() {
		return sexo;
	}

	/**
	 * @param sexo the sexo to set
	 */
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	/**
	 * @return the abo
	 */
	public String getAbo() {
		return abo;
	}

	/**
	 * @param abo the abo to set
	 */
	public void setAbo(String abo) {
		this.abo = abo;
	}

	/**
	 * @return the cns
	 */
	public String getCns() {
		return cns;
	}

	/**
	 * @param cns the cns to set
	 */
	public void setCns(String cns) {
		this.cns = cns;
	}

	/**
	 * @return the cpf
	 */
	public String getCpf() {
		return cpf;
	}

	/**
	 * @param cpf the cpf to set
	 */
	public void setCpf(String cpf) {
		if ("".equals(cpf)) {
			this.cpf = null;
		}
		else {
			this.cpf = cpf;
		}
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @param nome the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
		if (this.nome != null) {
			this.nome = this.nome.toUpperCase();
		}
	}

	/**
	 * @return the nomeMae
	 */
	public String getNomeMae() {
		return nomeMae;
	}

	/**
	 * @param nomeMae the nomeMae to set
	 */
	public void setNomeMae(String nomeMae) {
		this.nomeMae = nomeMae;
		if (this.nomeMae != null) {
			this.nomeMae = this.nomeMae.toUpperCase();
		}
		
	}

	/**
	 * @return the etnia
	 */
	public Etnia getEtnia() {
		return etnia;
	}

	/**
	 * @param etnia the etnia to set
	 */
	public void setEtnia(Etnia etnia) {
		if (etnia != null && etnia.getId() == null) {
			this.etnia = null;
		}
		else {
			this.etnia = etnia;
		}
	}

	/**
	 * @return the pais
	 */
	public Pais getPais() {
		return pais;
	}

	/**
	 * @param pais the pais to set
	 */
	public void setPais(Pais pais) {
		this.pais = pais;
	}

	/**
	 * @return the raca
	 */
	public Raca getRaca() {
		return raca;
	}

	/**
	 * @param raca the raca to set
	 */
	public void setRaca(Raca raca) {
		this.raca = raca;
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
	 * @return the contatosTelefonicos
	 */
	public List<ContatoTelefonicoPaciente> getContatosTelefonicos() {

		return contatosTelefonicos;
	}

	/**
	 * @param contatosTelefonicos the contatosTelefonicos to set
	 */
	public void setContatosTelefonicos(List<ContatoTelefonicoPaciente> contatosTelefonicos) {
		if (this.contatosTelefonicos != null && !this.contatosTelefonicos.isEmpty()) {
			this.contatosTelefonicos.stream().forEach(c -> c.setPaciente(this));
		}
		this.contatosTelefonicos = contatosTelefonicos;
	}

	/**
	 * @return the uf
	 */
	public Uf getNaturalidade() {
		return naturalidade;
	}

	/**
	 * @param naturalidade the naturalidade to set
	 */
	public void setNaturalidade(Uf naturalidade) {
		if (naturalidade != null && naturalidade.getSigla() == null) {
			naturalidade = null;
		}
		else {
			this.naturalidade = naturalidade;
		}
	}

	/**
	 * @return the responsavel
	 */
	public Responsavel getResponsavel() {
		return responsavel;
	}

	/**
	 * @param responsavel the responsavel to set
	 */
	public void setResponsavel(Responsavel responsavel) {
		this.responsavel = responsavel;
	}

	/**
	 * @return the diagnostico
	 */
	public Diagnostico getDiagnostico() {
		return diagnostico;
	}

	/**
	 * @param diagnostico the diagnostico to set
	 */
	public void setDiagnostico(Diagnostico diagnostico) {
		if (diagnostico != null) {
			diagnostico.setPaciente(this);
		}
		this.diagnostico = diagnostico;
	}

	/**
	 * Retorna as evoluções associadas ao paciente.
	 * 
	 * @return lista de evoluções
	 */
	public List<Evolucao> getEvolucoes() {
		return evolucoes;
	}

	/**
	 * Insere as evoluções associadas ao paciente.
	 * 
	 * @param evolucoes do paciente
	 */
	public void setEvolucoes(List<Evolucao> evolucoes) {
		this.evolucoes = evolucoes;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		if (!StringUtils.isEmpty(cpf)) {
			result = prime * result + ( ( cpf == null ) ? 0 : cpf.hashCode() );
		}
		else
			if (!StringUtils.isEmpty(cns)) {
				result = prime * result + ( ( cns == null ) ? 0 : cns.hashCode() );
			}
			else {
				result = prime * result + ( ( dataNascimento == null ) ? 0
						: dataNascimento.hashCode() );
				result = prime * result + ( ( nome == null ) ? 0 : nome.hashCode() );
				result = prime * result + ( ( nomeMae == null ) ? 0 : nomeMae.hashCode() );
			}
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		Paciente other = (Paciente) obj;
		if (cpf != null && cpf.equals(other.getCpf())) {
			return true;
		}
		else
			if (cns != null && cns.equals(other.cns)) {
				return true;
			}
			else
				if (nome != null && nome.equals(other.getNome())
						&& dataNascimento != null && dataNascimento.equals(other
								.getDataNascimento())
						&& ( nomeMae == null || nomeMae.equals(other.getNomeMae()) )) {
					return true;
				}
				else {
					return false;
				}
	}

	/**
	 * Método Bean Validation para verficar obrigatoriedade de CNS.
	 * 
	 * @return boolean true se o CNS não precisar ser preenchido e false caso necessite que seja preenchido
	 */
	@AssertTrueCustom(message = "{javax.validation.constraints.NotNull.message}", field = "cns")
	@JsonIgnore
	public boolean isPermitidoCNSNulo() {
		return validaCPFECNS();
	}

	/**
	 * Método Bean Validation para verficar obrigatoriedade de CPF.
	 * 
	 * @return boolean true se o CPF não precisar ser preenchido e false caso necessite que seja preenchido
	 */
	@AssertTrueCustom(message = "{javax.validation.constraints.NotNull.message}", field = "cpf")
	@JsonIgnore
	public boolean isPermitidoCPFNulo() {
		return validaCPFECNS();
	}

	/**
	 * Método Bean Validation para verificar a validade de CPF.
	 * 
	 * @return boolean true se o CPF é válido
	 */
	@AssertTrueCustom(message = "{org.hibernate.validator.constraints.br.CPF.message}", field = "cpf")
	@JsonIgnore
	public boolean isCPFValido() {
		if (StringUtils.isEmpty(this.cpf)) {
			return true;
		}
		return AppUtil.validarCPF(this.cpf);
	}

	private boolean validaCPFECNS() {
		if (!StringUtils.isEmpty(this.cns) || !StringUtils.isEmpty(this.cpf)) {
			return true;
		}
		else {
			if (isMaiorDeIdade()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Método Bean Validation para verficar obrigatoriedade de Nome de Responsavel.
	 * 
	 * @return boolean true se o Nome de Responsável não precisar ser preenchido e false caso necessite que seja preenchido
	 */
	@AssertTrueCustom(	message = "{javax.validation.constraints.NotNull.message}",
						field = "responsavel.nome")
	@JsonIgnore
	public boolean isPermitidoNomeResponsavelNulo() {
		if (!isObrigatorioResponsavel()) {
			return true;
		}
		else {
			if (this.responsavel == null || StringUtils.isEmpty(this.responsavel.getNome())) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Método Bean Validation para verficar obrigatoriedade de CPF de Responsavel.
	 * 
	 * @return boolean true se o CPF de Responsável não precisar ser preenchido e false caso necessite que seja preenchido
	 */
	@AssertTrueCustom(	message = "{javax.validation.constraints.NotNull.message}",
						field = "responsavel.cpf")
	@JsonIgnore
	public boolean isPermitidoCPFResponsavelNulo() {
		if (!isObrigatorioResponsavel()) {
			return true;
		}
		else {
			if (this.responsavel == null || StringUtils.isEmpty(this.responsavel.getCpf())) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Método Bean Validation para verficar obrigatoriedade de Parentesco de Responsavel.
	 * 
	 * @return boolean true se o Parentesco de Responsável não precisar ser preenchido e false caso necessite que seja preenchido
	 */
	@AssertTrueCustom(	message = "{javax.validation.constraints.NotNull.message}",
						field = "responsavel.parentesco")
	@JsonIgnore
	public boolean isPermitidoParentescoResponsavelNulo() {
		if (!isObrigatorioResponsavel()) {
			return true;
		}
		else {
			if (this.responsavel == null || StringUtils.isEmpty(this.responsavel.getParentesco())) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Método Bean Validation para verificar a validade de CNS.
	 * 
	 * @return boolean true se o CNS é válido
	 */
	@AssertTrueCustom(message = "{cns.invalido}", field = "cns")
	@JsonIgnore
	public boolean isCNSValido() {
		if (StringUtils.isEmpty(this.cns)) {
			return true;
		}
		return validaCNS(this.cns);
	}

	/**
	 * Método Bean Validation para verificar obrigatoriedade de Responsavel.
	 * 
	 * @return boolean true se o Responsável não precisar ser preenchido e false caso necessite que seja preenchido
	 */
	@AssertTrueCustom(	message = "{javax.validation.constraints.NotNull.message}",
						field = "responsavel")
	@JsonIgnore
	public boolean isPermitidoResponsavelNulo() {
		return !isObrigatorioResponsavel();
	}

	/**
	 * Método Bean Validation para verificar obrigatoriedade de etnia.
	 * 
	 * @return boolean true se o valor for válido e false se for inválido
	 */
	@AssertTrueCustom(message = "{javax.validation.constraints.NotNull.message}", field = "etnia")
	@JsonIgnore
	public boolean isPermitidoEtniaNula() {
		if (this.raca != null && this.raca.getId() != null) {
			if (this.raca.getId().equals(Raca.INDIGENA)) {
				return this.etnia != null;
			}
		}
		return true;
	}

	/**
	 * Método Bean Validation para verificar se a etnia foi preeenchida com uma raça diferente da indígina.
	 * 
	 * @return boolean true se o valor for válido e false se for inválido
	 */
	@AssertTrueCustom(message = "{etnia.invalido}", field = "etnia")
	@JsonIgnore
	public boolean isEtniaParaNaoIndigena() {
		if (this.raca != null && this.raca.getId() != null) {
			if (!this.raca.getId().equals(Raca.INDIGENA) && this.etnia != null && this.etnia
					.getId() != null) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Método Bean Validation para verificar se a data de nascimento é antes do dia atual.
	 * 
	 * @return boolean true se o valor for válido e false se for inválido
	 */
	@AssertTrueCustom(message = "{dataNascimento.invalido}", field = "dataNascimento")
	@JsonIgnore
	public boolean isDataNascimentoValida() {
		return this.dataNascimento != null && this.dataNascimento.isBefore(LocalDate.now());
	}

	/**
	 * Método Bean Validation para verificar obrigatoriedade de naturalidade.
	 * 
	 * @return boolean true se o valor for válido e false se for inválido
	 */
	@AssertTrueCustom(	message = "{naturalidade.obrigatorio.quando.nacionalidade.brasil}",
						field = "naturalidade")
	@JsonIgnore
	public boolean isNaturalidadeValida() {
		if (this.pais == null) {
			// Se país não foi informado ainda, SUCESSO.
			return true;
		}
		else
			if (!this.pais.isBrasil()
					&& naturalidade != null
					&& StringUtils.isNotEmpty(naturalidade.getSigla())) {
				// ... se país é estrangeiro e naturalidade foi informada,
				// FALHA.
				// naturalidade somente será informada se o país for BRASIL.
				return false;
			}
			else
				if (this.pais.isBrasil()
						&& ( this.naturalidade == null || StringUtils.isEmpty(this.naturalidade
								.getSigla()) )) {
					return false;
				}
		return true;
	}

	/**
	 * Método para verificar obrigatoriedade de Responsavel.
	 * 
	 * @return boolean true se o paciente for menor de idade
	 */
	@JsonIgnore
	public boolean isObrigatorioResponsavel() {
		if (!isMaiorDeIdade() && StringUtils.isEmpty(this.cpf) && StringUtils.isEmpty(this.cns)
				&& ( responsavel == null || StringUtils.isEmpty(responsavel.getCpf()) )) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Método para verificar se o paciente é maior de idade.
	 * 
	 * @return boolean true se o paciente for maior de idade
	 */
	@JsonIgnore
	private boolean isMaiorDeIdade() {
		if (this.dataNascimento == null) {
			return true;
		}
		long anos = ChronoUnit.YEARS.between(this.dataNascimento, LocalDate.now());
		return anos >= 18;
	}

	/**
	 * Método para validar CNS.
	 * 
	 * @param String CNS a ser validado
	 * @return boolean true se o CNS for válido
	 */
	private static boolean validaCNS(String stringCNS) {
		if (stringCNS.matches("[1-2]\\d{10}00[0-1]\\d") || stringCNS.matches("[7-9]\\d{14}")) {
			return somaPonderada(stringCNS) % 11 == 0;
		}
		return false;
	}
	
	/**
	 * Método Bean Validation para verificar obrigatoriedade do nome da mãe.
	 * 
	 * @return boolean true se o CPF, CNS ou nome da mãe forem informados. Caso contrário, deve retornar FALSE.
	 */
	@AssertTrueCustom(message = "{javax.validation.constraints.NotNull.message}", field = "nomeMae")
	@JsonIgnore
	public boolean isNomeMaeObrigatorio() {
		return (StringUtils.isNotEmpty(this.cpf) || StringUtils.isNotEmpty(this.cns) || StringUtils
				.isNotEmpty(nomeMae)) || (this.maeDesconhecida != null && this.maeDesconhecida && !isMaiorDeIdade());
	}

	/**
	 * Método utilizado pelo validaCNS que realiza a soma ponderada.
	 * 
	 * @param String cns a ser calculado
	 * @return int Resultado da soma
	 */
	private static int somaPonderada(String cns) {
		char[] cs = cns.toCharArray();
		int soma = 0;
		for (int i = 0; i < cs.length; i++) {
			soma += Character.digit(cs[i], 10) * ( 15 - i );
		}
		return soma;
	}

	/**
	 * Método para adicionar contato.
	 * 
	 * @param contatoTelefonico con
	 */
	public void adicionarContatoTelefonico(ContatoTelefonicoPaciente contatoTelefonico) {
		if (this.contatosTelefonicos == null) {
			this.contatosTelefonicos = new ArrayList<ContatoTelefonicoPaciente>();
		}
		this.contatosTelefonicos.add(contatoTelefonico);
	}

	public List<ExamePaciente> getExames() {
		return exames;
	}

	/**
	 * Define exames.
	 * 
	 * @param exames exames
	 */
	public void setExames(List<ExamePaciente> exames) {
		if (exames != null) {
			exames.forEach(exame -> exame.setPaciente(this));
		}
		this.exames = exames;
	}

	public CentroTransplante getCentroAvaliador() {
		return centroAvaliador;
	}

	public void setCentroAvaliador(CentroTransplante centroAvaliador) {
		this.centroAvaliador = centroAvaliador;
	}

	/**
	 * Retorna o médico responsável pelo paciente.
	 * 
	 * @return médico responsável
	 */
	public Medico getMedicoResponsavel() {
		return medicoResponsavel;
	}

	/**
	 * Setar o médico responsável pelo paciente.
	 * 
	 * @param medicoResponsavel paciente a ser associado ao paciente.
	 */
	public void setMedicoResponsavel(Medico medicoResponsavel) {
		this.medicoResponsavel = medicoResponsavel;
	}

	/**
	 * @return the aceitaMismatch
	 */
	public Integer getAceiteMismatch() {
		return aceiteMismatch;
	}

	/**
	 * @param aceitaMismatch the aceitaMismatch to set
	 */
	public void setAceiteMismatch(Integer aceitaMismatch) {
		this.aceiteMismatch = aceitaMismatch;
	}

	/**
	 * @return the nomeFonetizado
	 */
	public String getNomeFonetizado() {
		return nomeFonetizado;
	}

	/**
	 * @param nomeFonetizado the nomeFonetizado to set
	 */
	public void setNomeFonetizado(String nomeFonetizado) {
		this.nomeFonetizado = nomeFonetizado;
	}

	/**
	 * retorna a Lista de avaliações de um paciente.
	 * 
	 * @return
	 */
	public List<Avaliacao> getAvaliacoes() {
		return avaliacoes;
	}

	/**
	 * Seta a lista de avaliações de um paciente.
	 * 
	 * @param avaliacoes
	 */
	public void setAvaliacoes(List<Avaliacao> avaliacoes) {
		this.avaliacoes = avaliacoes;
	}

	/**
	 * Lista de buscas para match de um paciente.
	 * 
	 * @return
	 */
	public List<Busca> getBuscas() {
		return buscas;
	}

	/**
	 * Lista de buscas para match de um paciente.
	 * 
	 * @param buscas
	 */
	public void setBuscas(List<Busca> buscas) {
		this.buscas = buscas;
	}

	/**
	 * Método que é chamado antes de persistir ou alterar o paciente para coloca o nome fonetizado.
	 */
	@PrePersist
	@PreUpdate
	private void fonetizarNome() {
		this.nomeFonetizado = "";
		if (this.nome != null) {
			Stream.of(this.nome.split(" ")).forEach(palavra -> {
				this.nomeFonetizado += ConversaoFonetica.getInstance().converter(palavra) + " ";
			});
		}
		this.nomeFonetizado = this.nomeFonetizado.trim();
	}

	@Transactional
	@JsonView({PacienteView.IdentificacaoParcial.class,LaboratorioView.ListarReceberExame.class,
		PedidoTransferenciaCentroView.ListarTarefas.class})
	public String nomeAbreviado() {
		return AppUtil.obterIniciais(this.nome);
	}

	/**
	 * Retorna a quantidade de notificações associadas a este paciente.
	 * 
	 * @return quantidade de notificações.
	 */
	public Long getQuantidadeNotificacoes() {
		return quantidadeNotificacoes;
	}

	public void setQuantidadeNotificacoes(Long quantidadeNotificacoes) {
		this.quantidadeNotificacoes = quantidadeNotificacoes;
	}

	/**
	 * @return the enderecosContato
	 */
	public List<EnderecoContatoPaciente> getEnderecosContato() {
		return enderecosContato;
	}

	/**
	 * @param enderecosContato the enderecosContato to set
	 */
	public void setEnderecosContato(List<EnderecoContatoPaciente> enderecosContato) {
		this.enderecosContato = enderecosContato;
	}

	/**
	 * Verifica se o paciente é estrangeiro ou não (País de origem diferente de Brasil).
	 * 
	 * @return TRUE se for estrangeiro, FALSE caso contrário.
	 */
	public Boolean isEstrangeiro() {
		if (pais == null) {
			return null;
		}
		return !Pais.BRASIL.equals(pais.getId());
	}

	/**
	 * @return the score
	 */
	public Float getScore() {
		return score;
	}

	/**
	 * Método que retorna se o paciente possui uma busca ativa.
	 * 
	 * @return the temBuscaAtiva
	 */
	@Transient
	@JsonView({ PacienteView.Consulta.class, PacienteView.Detalhe.class })
	public Busca getBuscaAtiva() {
		Busca[] buscaAtiva = { null };
		if (buscas != null && !buscas.isEmpty()) {
			buscas.forEach(busca -> {
				if (busca != null && busca.getStatus().getBuscaAtiva()) {
					buscaAtiva[0] = busca;
				}
			});
		}
		return buscaAtiva[0];
	}

	/**
	 * @return the genotipo
	 */
	public GenotipoPaciente getGenotipo() {
		return genotipo;
	}

	/**
	 * @param genotipo the genotipo to set
	 */
	public void setGenotipo(GenotipoPaciente genotipo) {
		this.genotipo = genotipo;
	}

	/**
	 * Indica se o nome da mãe é desconhecido pelo paciente.
	 * Atributo não é persistido, existe somente para controle de rascunho entre front e back-end.
	 * 
	 * @return TRUE caso seja desconhecido.
	 */
	public Boolean getMaeDesconhecida() {
		return maeDesconhecida;
	}

	public void setMaeDesconhecida(Boolean maeDesconhecida) {
		this.maeDesconhecida = maeDesconhecida;
	}

	public List<Locus> getLocusMismatch() {
		return locusMismatch;
	}

	public void setLocusMismatch(List<Locus> locusMismatch) {
		this.locusMismatch = locusMismatch;
	}

	public boolean isTemPedidoTransferenciaCentroAvaliadorEmAberto() {
		return temPedidoTransferenciaCentroAvaliadorEmAberto;
	}

	public void setTemPedidoTransferenciaCentroAvaliadorEmAberto(boolean temPedidoTransferenciaCentroAvaliadorEmAberto) {
		this.temPedidoTransferenciaCentroAvaliadorEmAberto = temPedidoTransferenciaCentroAvaliadorEmAberto;
	}

	public boolean isTemAvaliacaoEmAberto() {
		return temAvaliacaoEmAberto;
	}

	public void setTemAvaliacaoEmAberto(boolean temAvaliacaoEmAberto) {
		this.temAvaliacaoEmAberto = temAvaliacaoEmAberto;
	}

	public boolean isTemAvaliacaoIniciada() {
		return temAvaliacaoIniciada;
	}
	
	public boolean isTemAvaliacaoAprovada() {
		return temAvaliacaoAprovada;
	}

	public void setTemAvaliacaoIniciada(boolean temAvaliacaoIniciada) {
		this.temAvaliacaoIniciada = temAvaliacaoIniciada;
	}
	
	public void setTemAvaliacaoAprovada(boolean temAvaliacaoAprovada) {
		this.temAvaliacaoAprovada = temAvaliacaoAprovada;
	}

	/**
	 * @return the quantidadeNotificacoesNaoLidas
	 */
	public Long getQuantidadeNotificacoesNaoLidas() {
		return quantidadeNotificacoesNaoLidas;
	}

	/**
	 * @param quantidadeNotificacoesNaoLidas the quantidadeNotificacoesNaoLidas to set
	 */
	public void setQuantidadeNotificacoesNaoLidas(Long quantidadeNotificacoesNaoLidas) {
		this.quantidadeNotificacoesNaoLidas = quantidadeNotificacoesNaoLidas;
	}
	
	

	/**
	 * @return the status
	 */
	public StatusPaciente getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(StatusPaciente status) {
		this.status = status;
	}

	public boolean isTemBuscaAtiva() {
		return temBuscaAtiva;
	}

	public void setTemBuscaAtiva(boolean temBuscaAtiva) {
		this.temBuscaAtiva = temBuscaAtiva;
	}

	/**
	 * @return the tempoParaTransplante
	 */
	public Integer getTempoParaTransplante() {
		return tempoParaTransplante;
	}

	/**
	 * @param tempoParaTransplante the tempoParaTransplante to set
	 */
	public void setTempoParaTransplante(Integer tempoParaTransplante) {
		this.tempoParaTransplante = tempoParaTransplante;
	}

	/**
	 * @return the idTipoExameFase3
	 */
	public Long getIdTipoExameFase3() {
		return idTipoExameFase3;
	}

	/**
	 * @param idTipoExameFase3 the idTipoExameFase3 to set
	 */
	public void setIdTipoExameFase3(Long idTipoExameFase3) {
		this.idTipoExameFase3 = idTipoExameFase3;
	}

	/**
	 * @return the wmdaId
	 */
	public String getWmdaId() {
		return wmdaId;
	}

	/**
	 * @param wmdaId the wmdaId to set
	 */
	public void setWmdaId(String wmdaId) {
		this.wmdaId = wmdaId;
	}
	
	

	/**
	 * @param score the score to set
	 */
	public void setScore(Float score) {
		this.score = score;
	}

	@Override
	public Paciente clone() {
		Paciente clone = new Paciente();
		clone.setRmr(this.rmr);
		clone.setDataCadastro(this.dataCadastro);
		clone.setDataNascimento(this.dataNascimento);
		clone.setSexo(this.sexo);
		clone.setAbo(this.abo);
		clone.setCns(this.cns);
		clone.setCpf(this.cpf);
		clone.setEmail(this.email);
		clone.setNome(this.nome);
		clone.setNomeFonetizado(this.nomeFonetizado);
		clone.setNomeMae(this.nomeMae);
		clone.setEtnia(this.etnia);
		clone.setPais(this.pais);
		clone.setRaca(this.raca);
		clone.setUsuario(this.usuario);
		clone.setEnderecosContato(this.enderecosContato);
		clone.setContatosTelefonicos(this.contatosTelefonicos);
		clone.setNaturalidade(this.naturalidade);
		clone.setResponsavel(this.responsavel);
		clone.setDiagnostico(this.diagnostico);
		clone.setEvolucoes(this.evolucoes);
		clone.setBuscas(this.buscas);
		clone.setExames(this.exames);
		clone.setCentroAvaliador(this.centroAvaliador);
		clone.setMedicoResponsavel(this.medicoResponsavel);
		clone.setAceiteMismatch(this.aceiteMismatch);
		clone.setAvaliacoes(this.avaliacoes);
		clone.setQuantidadeNotificacoes(this.quantidadeNotificacoes);
		clone.setQuantidadeNotificacoesNaoLidas(this.quantidadeNotificacoesNaoLidas);
		clone.setScore(this.score);
		clone.setMaeDesconhecida(this.maeDesconhecida);
		clone.setGenotipo(this.genotipo);
		clone.setLocusMismatch(this.locusMismatch);
		clone.setTemPedidoTransferenciaCentroAvaliadorEmAberto(this.temPedidoTransferenciaCentroAvaliadorEmAberto);
		clone.setTemAvaliacaoEmAberto(this.temAvaliacaoEmAberto);
		clone.setTemAvaliacaoIniciada(this.temAvaliacaoIniciada);
		clone.setTemAvaliacaoAprovada(this.temAvaliacaoAprovada);
		clone.setIdTipoExameFase3(this.idTipoExameFase3);
		clone.setStatus(this.status);
		clone.setTempoParaTransplante(this.tempoParaTransplante);
		clone.setTemBuscaAtiva(this.temBuscaAtiva);
		clone.setWmdaId(this.wmdaId);
		
		return clone;
	}
	
}

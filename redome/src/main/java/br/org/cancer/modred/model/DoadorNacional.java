package br.org.cancer.modred.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.hibernate.envers.RelationTargetAuditMode;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.br.CPF;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.AnaliseMedicaView;
import br.org.cancer.modred.controller.view.AvaliacaoPedidoColetaView;
import br.org.cancer.modred.controller.view.AvaliacaoPrescricaoView;
import br.org.cancer.modred.controller.view.AvaliacaoResultadoWorkupView;
import br.org.cancer.modred.controller.view.BuscaView;
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
import br.org.cancer.modred.model.domain.TipoBoolean;
import br.org.cancer.modred.model.domain.TiposDoador;

/**
 * Classe de persistencia de doador para a tabela doador.
 * 
 * @author Fillipe Queiroz
 * 
 */
@Entity
@DiscriminatorValue("0")
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
public class DoadorNacional extends Doador implements IDoador<Long>, IDoadorNacional {

	private static final long serialVersionUID = 8284749875777582204L;

	@JsonView({ BuscaView.Enriquecimento.class, DoadorView.AtualizacaoFase2.class, DoadorView.ContatoPassivo.class,
		DoadorView.ContatoFase2.class,
		DoadorView.IdentificacaoCompleta.class, PedidoWorkupView.DetalheWorkup.class, TarefaView.Listar.class,
		BuscaView.AvaliacaoPedidoColeta.class, WorkupView.Resultado.class,
		PedidoColetaView.DetalheColeta.class, BuscaView.AvaliacaoWorkupDoador.class, TarefaLogisticaView.Listar.class, 
		TransportadoraView.AgendarTransporte.class, TransportadoraView.Listar.class, 
		TarefaLogisticaView.Listar.class, PedidoColetaView.AgendamentoColeta.class, PedidoWorkupView.AgendamentoWorkup.class,
		PedidoExameView.ListarTarefas.class, AnaliseMedicaView.ListarTarefas.class, AnaliseMedicaView.Detalhe.class,
		AvaliacaoResultadoWorkupView.ListarAvaliacao.class, WorkupView.ResultadoParaAvaliacao.class,  
		AvaliacaoPedidoColetaView.Detalhe.class, AvaliacaoPrescricaoView.Detalhe.class, AvaliacaoPrescricaoView.ListarAvaliacao.class,
		FormularioView.DetalheDoador.class})
	@Column(name = "DOAD_NR_DMR")
	private Long dmr;

	@Column(name = "DOAD_TX_CPF")
	@CPF
	@JsonView({ BuscaView.Enriquecimento.class, DoadorView.AtualizacaoFase2.class, DoadorView.ContatoPassivo.class,
			DoadorView.ContatoFase2.class })
	@Length(max = 11)
	private String cpf;

	@Column(name = "DOAD_TX_NOME")
	@Pattern(regexp = "^[^\\d\\.]+$", message = "{nome.caracteresInvalidos}")
	@JsonView({ BuscaView.Enriquecimento.class, DoadorView.AtualizacaoFase2.class, DoadorView.ContatoPassivo.class,
			DoadorView.ContatoFase2.class,
			DoadorView.IdentificacaoCompleta.class, PedidoWorkupView.DetalheWorkup.class, WorkupView.Resultado.class,
			BuscaView.AvaliacaoPedidoColeta.class, TarefaView.Listar.class,
			TarefaLogisticaView.Listar.class, TarefaLogisticaView.Listar.class,
			TransportadoraView.AgendarTransporte.class, PedidoColetaView.AgendamentoColeta.class,
			TransportadoraView.Listar.class, PedidoExameView.ListarTarefas.class,
			AnaliseMedicaView.ListarTarefas.class, AvaliacaoResultadoWorkupView.ListarAvaliacao.class,
			SolicitacaoView.detalhe.class})
	@Length(max = 255)
	private String nome;

	@Column(name = "DOAD_TX_NOME_SOCIAL")
	@Pattern(regexp = "^[^\\d\\.]+$", message = "{nome.caracteresInvalidos}")
	@JsonView({ BuscaView.Enriquecimento.class, DoadorView.AtualizacaoFase2.class, DoadorView.ContatoPassivo.class,
			DoadorView.ContatoFase2.class })
	@Length(max = 255)
	private String nomeSocial;

	@Column(name = "DOAD_TX_NOME_MAE")
	@Pattern(regexp = "^[^\\d\\.]+$", message = "{nome.caracteresInvalidos}")
	@Length(max = 255)
	@JsonView({ BuscaView.Enriquecimento.class, DoadorView.AtualizacaoFase2.class, DoadorView.ContatoPassivo.class, DoadorView.ContatoFase2.class })
	private String nomeMae;

	@ManyToOne
	@JoinColumn(name = "RACA_ID")
	@JsonView({ DoadorView.AtualizacaoFase2.class, DoadorView.ContatoPassivo.class,
		DoadorView.ContatoFase2.class })
	private Raca raca;

	@ManyToOne
	@JoinColumn(name = "ETNI_ID")
	@JsonView({ DoadorView.AtualizacaoFase2.class, DoadorView.ContatoPassivo.class, DoadorView.ContatoFase2.class })
	private Etnia etnia;

	@ManyToOne
	@JoinColumn(name = "PAIS_ID")
	@JsonView({ DoadorView.AtualizacaoFase2.class, DoadorView.ContatoPassivo.class, DoadorView.ContatoFase2.class })
	private Pais pais;

	@ManyToOne
	@JoinColumn(name = "MUNI_ID_NATURALIDADE")
	@Fetch(FetchMode.JOIN)
	@JsonView({ DoadorView.AtualizacaoFase2.class, DoadorView.ContatoPassivo.class, DoadorView.ContatoFase2.class })
	private Municipio naturalidade;

	@Column(name = "DOAD_TX_RG")
	@JsonView({ BuscaView.Enriquecimento.class, DoadorView.AtualizacaoFase2.class, DoadorView.ContatoPassivo.class,
		DoadorView.ContatoFase2.class})
	private String rg;

	@Column(name = "DOAD_TX_ORGAO_EXPEDIDOR")
	@JsonView({ BuscaView.Enriquecimento.class, DoadorView.AtualizacaoFase2.class, DoadorView.ContatoPassivo.class,
		DoadorView.ContatoFase2.class})
	private String orgaoExpedidor;

	@JsonView({ DoadorView.AtualizacaoFase2.class, DoadorView.ContatoPassivo.class, DoadorView.ContatoFase2.class })
	@Column(name = "DOAD_VL_ALTURA", precision = 3, scale = 2)
	@DecimalMin(value = "0", inclusive = false, message = "paciente.altura.maior.zero")
	@Digits(integer = 1, fraction = 2)
	private BigDecimal altura;

	@Column(name = "DOAD_TX_NOME_PAI")
	@Pattern(regexp = "^[^\\d\\.]+$", message = "{nome.caracteresInvalidos}")
	@Length(max = 255)
	@JsonView({ DoadorView.AtualizacaoFase2.class, DoadorView.ContatoPassivo.class, DoadorView.ContatoFase2.class })
	private String nomePai;

	/**
	 * Lista de telefones do doador.
	 */
	@OneToMany(cascade = {CascadeType.ALL, CascadeType.MERGE}, fetch = FetchType.LAZY)
	@JoinColumn(name = "DOAD_ID")
	@NotEmpty
	@Valid
	@NotAudited
	@JsonView({ BuscaView.Enriquecimento.class, DoadorView.AtualizacaoFase2.class, DoadorView.ContatoPassivo.class,
			DoadorView.ContatoFase2.class, FormularioView.DetalheDoador.class, DoadorView.LogisticaDoador.class})
	@Fetch(FetchMode.SUBSELECT)
	private List<ContatoTelefonicoDoador> contatosTelefonicos;

	@OneToMany(cascade = {CascadeType.ALL, CascadeType.MERGE}, fetch = FetchType.LAZY)	
	@JoinColumn(name = "DOAD_ID")
	@NotEmpty
	@JsonView({ BuscaView.Enriquecimento.class, DoadorView.AtualizacaoFase2.class, DoadorView.ContatoPassivo.class,
		DoadorView.ContatoFase2.class, DoadorView.LogisticaDoador.class})
	@Valid
	@NotAudited
	private List<EnderecoContatoDoador> enderecosContato;

	@OneToMany(cascade = {CascadeType.ALL, CascadeType.MERGE}, fetch = FetchType.LAZY)
	@JoinColumn(name = "DOAD_ID")
	@JsonView({ BuscaView.Enriquecimento.class, DoadorView.AtualizacaoFase2.class, DoadorView.ContatoPassivo.class, 
		DoadorView.ContatoFase2.class, DoadorView.LogisticaDoador.class})
	@Valid
	@NotAudited
	private List<EmailContatoDoador> emailsContato;

	@ManyToOne
	@JoinColumn(name = "LABO_ID")
	private Laboratorio laboratorio;
	
	@NotEmpty
	@Valid
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name="DOAD_ID", referencedColumnName="DOAD_ID")
	@NotAudited
	@Fetch(FetchMode.SUBSELECT)
	private List<ExameDoadorNacional> exames;
	
	@JsonView({ DoadorView.AtualizacaoFase2.class, DoadorView.ContatoPassivo.class,
		DoadorView.ContatoFase2.class, AvaliacaoPrescricaoView.Detalhe.class, PrescricaoView.DetalheDoador.class,
		SolicitacaoView.detalhe.class})
	@Column(name = "DOAD_VL_PESO", precision = 4, scale = 1)
	@DecimalMin(value = "0", inclusive = false, message = "paciente.peso.maior.zero")
	@Digits(integer = 3, fraction = 1)
	private BigDecimal peso;
	
	@ManyToOne
	@JoinColumn(name = "HEEN_ID")
	private HemoEntidade hemoEntidade;
		
	@Column(name = "DOAD_TX_NUMERO_HEMOCENTRO")
	@JsonView({ BuscaView.Enriquecimento.class })
	private String  numeroHemocentro;
	
	@Column(name = "DOAD_IN_FUMANTE")
	@JsonView({ BuscaView.Enriquecimento.class, DoadorView.ContatoFase2.class })
	private Boolean fumante;

	@ManyToOne
	@JoinColumn(name = "ESCI_ID")
	@JsonView({ BuscaView.Enriquecimento.class, DoadorView.ContatoFase2.class })
	private EstadoCivil estadoCivil;
		
	@Column(name = "DOAD_NR_ID_CAMPANHA_REDOMEWEB")
	@JsonView({ BuscaView.Enriquecimento.class })
	private Long  campanha;
	
	@Column(name = "DOAD_DT_COLETA")
	@JsonView({ BuscaView.Enriquecimento.class })
	private LocalDate dataColeta;
	
	public DoadorNacional() {
		super();
		this.setTipoDoador(TiposDoador.NACIONAL);
	}
	
	/**
	 * Construtor para facilitar a instanciação do objeto, a partir do seu identificador.
	 * 
	 * @param idDoador identificador da entidade.
	 */
	public DoadorNacional(Long id) {
		super(id);
		this.setTipoDoador(TiposDoador.NACIONAL);
	}

	/**
	 * Método construtor utilizado para a identificação do doador.
	 * 
	 * @param id - identificador do doador
	 * @param nome - nome do doador
	 * @param sexo - sexo do doador
	 * @param dataNascimento - Data de Nascimento do doador
	 * @param statusDoador - Status do doador
	 */
	public DoadorNacional(Long id, String nome, String sexo, LocalDate dataNascimento, StatusDoador statusDoador) {
		super(id, sexo, dataNascimento, statusDoador);
		this.nome = nome;
		this.setTipoDoador(TiposDoador.NACIONAL);
	}
	
	/**
	 * Método construtor utilizado para a identificação do doador.
	 * 
	 * @param id - identificador do doador
	 * @param dmr - dmr doador
	 * @param nome - nome do doador
	 * @param sexo - sexo do doador
	 * @param dataNascimento - Data de Nascimento do doador
	 * @param statusDoador - Status do doador
	 */
	public DoadorNacional(Long id, String nome, String sexo, LocalDate dataNascimento, StatusDoador statusDoador, Long dmr) {
		super(id, sexo, dataNascimento, statusDoador);
		this.nome = nome;
		this.dmr = dmr;
		this.setTipoDoador(TiposDoador.NACIONAL);
	}

	/**
	 * Cpf do doador.
	 * 
	 * @return
	 */
	public String getCpf() {
		return cpf;
	}

	/**
	 * Cpf do doador.
	 * 
	 * @param cpf
	 */
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	/**
	 * Nome do doador.
	 * 
	 * @return
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * Nome do doador.
	 * 
	 * @param nome - Nome do doador.
	 */
	public void setNome(String nome) {
		this.nome = nome;
		if (this.nome != null) {
			this.nome = this.nome.toUpperCase();
		}
	}

	/**
	 * Nome social do doador (opcional).
	 * 
	 * @return
	 */
	public String getNomeSocial() {
		return nomeSocial;
	}

	/**
	 * Nome social do doador (opcional).
	 * 
	 * @param nomeSocial - Nome Social do doador.
	 */
	public void setNomeSocial(String nomeSocial) {
		this.nomeSocial = nomeSocial;
		if (this.nomeSocial != null) {
			this.nomeSocial = this.nomeSocial.toUpperCase();
		}
	}

	/**
	 * Nome da mae do doador.
	 * 
	 * @return
	 * 
	 */
	public String getNomeMae() {
		return nomeMae;
	}

	/**
	 * Nome da mae do doador.
	 * 
	 * @param nomeMae - Nome da mãe do doador.
	 */
	public void setNomeMae(String nomeMae) {
		this.nomeMae = nomeMae;
		if (this.nomeMae != null) {
			this.nomeMae = this.nomeMae.toUpperCase();
		}
	}

	/**
	 * Raca do doador.
	 * 
	 * @return
	 */
	public Raca getRaca() {
		return raca;
	}

	/**
	 * Raca do doador.
	 * 
	 * @param raca
	 */
	public void setRaca(Raca raca) {
		this.raca = raca;
	}

	/**
	 * Etnia do doador.
	 * 
	 * @return
	 */
	public Etnia getEtnia() {
		return etnia;
	}

	/**
	 * Etnia do doador.
	 * 
	 * @param etnia
	 */
	public void setEtnia(Etnia etnia) {
		this.etnia = etnia;
	}

	/**
	 * Nacionalidade do doador.
	 * 
	 * @return
	 */
	public Pais getPais() {
		return pais;
	}

	/**
	 * Nacionalidade do doador.
	 * 
	 * @param pais
	 */
	public void setPais(Pais pais) {
		this.pais = pais;
	}

	/**
	 * Naturalidade do doador.
	 * 
	 * @return
	 */
	public Municipio getNaturalidade() {
		return naturalidade;
	}

	/**
	 * Naturalidade do doador.
	 * 
	 * @param naturalidade
	 */
	public void setNaturalidade(Municipio naturalidade) {
		this.naturalidade = naturalidade;
	}

	/**
	 * @return the rg
	 */
	public String getRg() {
		return rg;
	}

	/**
	 * @param rg the rg to set
	 */
	public void setRg(String rg) {
		this.rg = rg;
	}

	/**
	 * @return the orgaoExpedidor
	 */
	public String getOrgaoExpedidor() {
		return orgaoExpedidor;
	}

	/**
	 * @param orgaoExpedidor the orgaoExpedidor to set
	 */
	public void setOrgaoExpedidor(String orgaoExpedidor) {
		this.orgaoExpedidor = orgaoExpedidor;
	}

	/**
	 * @return the contatosTelefonicos
	 */
	public List<ContatoTelefonicoDoador> getContatosTelefonicos() {
		return contatosTelefonicos;
	}

	/**
	 * @param contatosTelefonicos the contatosTelefonicos to set
	 */
	public void setContatosTelefonicos(List<ContatoTelefonicoDoador> contatosTelefonicos) {
		this.contatosTelefonicos = contatosTelefonicos;
	}

	/**
	 * @return the enderecosContato
	 */
	public List<EnderecoContatoDoador> getEnderecosContato() {
		return enderecosContato;
	}

	/**
	 * @param enderecosContato the enderecosContato to set
	 */
	public void setEnderecosContato(List<EnderecoContatoDoador> enderecosContato) {
		this.enderecosContato = enderecosContato;
	}

	/**
	 * @return the emailsContato
	 */
	public List<EmailContatoDoador> getEmailsContato() {
		return emailsContato;
	}

	/**
	 * @param emailsContato the emailsContato to set
	 */
	public void setEmailsContato(List<EmailContatoDoador> emailsContato) {
		this.emailsContato = emailsContato;
	}

	/**
	 * Altura do doador.
	 * 
	 * @return
	 */
	public BigDecimal getAltura() {
		return altura;
	}

	/**
	 * Altura do doador.
	 * 
	 * @param altura
	 */
	public void setAltura(BigDecimal altura) {
		this.altura = altura;
	}

	/**
	 * Pai do doador.
	 * 
	 * @return
	 */
	public String getNomePai() {
		return nomePai;
	}

	/**
	 * Pai do doador.
	 * 
	 * @param nomePai - Nome do pai do doador.
	 */
	public void setNomePai(String nomePai) {
		this.nomePai = nomePai;
		if (this.nomePai != null) {
			this.nomePai = this.nomePai.toUpperCase();
		}
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
	 * @return the dmr
	 */
	public Long getDmr() {
		return dmr;
	}

	/**
	 * @param dmr the dmr to set
	 */
	public void setDmr(Long dmr) {
		this.dmr = dmr;
	}
	
	/**
	 * @return the exames
	 */
	public List<ExameDoadorNacional> getExames() {
		return exames;
	}
	
	/**
	 * @param exames the exames to set
	 */
	public void setExames(List<ExameDoadorNacional> exames) {
		this.exames = exames;
	}
	
	/**
	 * Peso do doador.
	 * 
	 * @return
	 */
	public BigDecimal getPeso() {
		return peso;
	}

	/**
	 * Peso do doador.
	 * 
	 * @param peso
	 */
	public void setPeso(BigDecimal peso) {
		this.peso = peso;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((dmr == null) ? 0 : dmr.hashCode());
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
		if (!super.equals(obj)) {
			return false;
		}
		if (!( obj instanceof DoadorNacional )) {
			return false;
		}
		DoadorNacional other = (DoadorNacional) obj;
		if (altura == null) {
			if (other.altura != null) {
				return false;
			}
		}
		else
			if (!altura.equals(other.altura)) {
				return false;
			}
		if (contatosTelefonicos == null) {
			if (other.contatosTelefonicos != null) {
				return false;
			}
		}
		else
			if (!contatosTelefonicos.equals(other.contatosTelefonicos)) {
				return false;
			}
		if (cpf == null) {
			if (other.cpf != null) {
				return false;
			}
		}
		else
			if (!cpf.equals(other.cpf)) {
				return false;
			}
		if (dmr == null) {
			if (other.dmr != null) {
				return false;
			}
		}
		else
			if (!dmr.equals(other.dmr)) {
				return false;
			}
		if (emailsContato == null) {
			if (other.emailsContato != null) {
				return false;
			}
		}
		else
			if (!emailsContato.equals(other.emailsContato)) {
				return false;
			}
		if (enderecosContato == null) {
			if (other.enderecosContato != null) {
				return false;
			}
		}
		else
			if (!enderecosContato.equals(other.enderecosContato)) {
				return false;
			}
		if (etnia == null) {
			if (other.etnia != null) {
				return false;
			}
		}
		else
			if (!etnia.equals(other.etnia)) {
				return false;
			}
		if (laboratorio == null) {
			if (other.laboratorio != null) {
				return false;
			}
		}
		else
			if (!laboratorio.equals(other.laboratorio)) {
				return false;
			}
		if (naturalidade == null) {
			if (other.naturalidade != null) {
				return false;
			}
		}
		else
			if (!naturalidade.equals(other.naturalidade)) {
				return false;
			}
		if (nome == null) {
			if (other.nome != null) {
				return false;
			}
		}
		else
			if (!nome.equals(other.nome)) {
				return false;
			}
		if (nomeMae == null) {
			if (other.nomeMae != null) {
				return false;
			}
		}
		else
			if (!nomeMae.equals(other.nomeMae)) {
				return false;
			}
		if (nomePai == null) {
			if (other.nomePai != null) {
				return false;
			}
		}
		else
			if (!nomePai.equals(other.nomePai)) {
				return false;
			}
		if (nomeSocial == null) {
			if (other.nomeSocial != null) {
				return false;
			}
		}
		else
			if (!nomeSocial.equals(other.nomeSocial)) {
				return false;
			}
		if (orgaoExpedidor == null) {
			if (other.orgaoExpedidor != null) {
				return false;
			}
		}
		else
			if (!orgaoExpedidor.equals(other.orgaoExpedidor)) {
				return false;
			}
		if (pais == null) {
			if (other.pais != null) {
				return false;
			}
		}
		else
			if (!pais.equals(other.pais)) {
				return false;
			}
		if (raca == null) {
			if (other.raca != null) {
				return false;
			}
		}
		else
			if (!raca.equals(other.raca)) {
				return false;
			}
		if (rg == null) {
			if (other.rg != null) {
				return false;
			}
		}
		else
			if (!rg.equals(other.rg)) {
				return false;
			}
		if (peso == null) {
			if (other.peso != null) {
				return false;
			}
		}
		else
			if (!peso.equals(other.peso)) {
				return false;
			}
		if (numeroHemocentro == null) {
			if (other.numeroHemocentro != null) {
				return false;
			}
		}
		else
			if (!numeroHemocentro.equals(other.numeroHemocentro)) {
				return false;
			}
		if (!fumante) {
			if (other.fumante) {
				return false;
			}
		}
		else
			if (!fumante == other.fumante) {
				return false;
			}
		if (estadoCivil == null) {
			if (other.estadoCivil != null) {
				return false;
			}
		}
		else
			if (!estadoCivil.equals(other.estadoCivil)) {
				return false;
			}

		return true;
	}


	@Override
	public Long getIdentificacao() {
		return getDmr();
	}
	

	/**
	 * @return the hemoEntidade
	 */
	public HemoEntidade getHemoEntidade() {
		return hemoEntidade;
	}

	/**
	 * @param hemoEntidade the hemoEntidade to set
	 */
	public void setHemoEntidade(HemoEntidade hemoEntidade) {
		this.hemoEntidade = hemoEntidade;
	}

	/**
	 * @return the numeroHemocentro
	 */
	public String getNumeroHemocentro() {
		return numeroHemocentro;
	}

	/**
	 * @param numeroHemocentro the numeroHemocentro to set
	 */
	public void setNumeroHemocentro(String numeroHemocentro) {
		this.numeroHemocentro = numeroHemocentro;
	}

	/**
	 * @return the fumante
	 */
	public Boolean getFumante() {
		return fumante;
	}

	/**
	 * @param fumante the fumante to set
	 */
	public void setFumante(Boolean fumante) {
		this.fumante = fumante;
	}

	public String getFumanteRedomeWeb() {
		return fumante != null && fumante?TipoBoolean.SIM.getSigla():TipoBoolean.NAO.getSigla();
	}
	
	/**
	 * @return the estadoCivil
	 */
	public EstadoCivil getEstadoCivil() {
		return estadoCivil;
	}

	/**
	 * @param estadoCivil the estadoCivil to set
	 */
	public void setEstadoCivil(EstadoCivil estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	/**
	 * @return the campanha
	 */
	public Long getCampanha() {
		return campanha;
	}

	/**
	 * @param campanha the campanha to set
	 */
	public void setCampanha(Long campanha) {
		this.campanha = campanha;
	}

	/**
	 * @return the dataColeta
	 */
	public LocalDate getDataColeta() {
		return dataColeta;
	}

	/**
	 * @param dataColeta the dataColeta to set
	 */
	public void setDataColeta(LocalDate dataColeta) {
		this.dataColeta = dataColeta;
	}
	
}

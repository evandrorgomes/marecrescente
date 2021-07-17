package br.org.cancer.modred.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Where;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.AvaliacaoView;
import br.org.cancer.modred.controller.view.BuscaView;
import br.org.cancer.modred.controller.view.DoadorView;
import br.org.cancer.modred.controller.view.EvolucaoView;
import br.org.cancer.modred.controller.view.ExameView;
import br.org.cancer.modred.controller.view.MedicoView;
import br.org.cancer.modred.controller.view.PacienteView;
import br.org.cancer.modred.controller.view.TarefaView;
import br.org.cancer.modred.model.security.Usuario;

/**
 * Classe que representa a entidade médico no modelo.
 * 
 * @author Rafael Pizão
 *
 */
@Entity
@SequenceGenerator(name = "SQ_MEDI_ID", sequenceName = "SQ_MEDI_ID", allocationSize = 1)
@Table(name = "MEDICO")
public class Medico implements Serializable {

	private static final long serialVersionUID = 4329251705835470154L;

	@Id
	@Column(name = "MEDI_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_MEDI_ID")
	@JsonView(value = { PacienteView.Consulta.class, AvaliacaoView.Avaliacao.class, TarefaView.Listar.class,
						MedicoView.Listar.class, MedicoView.Detalhe.class, MedicoView.Logado.class})
	private Long id;

	@Column(name = "MEDI_TX_CRM")
	@JsonView({MedicoView.Listar.class, MedicoView.Detalhe.class})
	private String crm;

	@Column(name = "MEDI_TX_NOME")
	@JsonView(value = { PacienteView.Consulta.class, PacienteView.IdentificacaoParcial.class,
						MedicoView.Listar.class, MedicoView.Detalhe.class})
	private String nome;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USUA_ID")
	@JsonView(value = { PacienteView.Consulta.class, PacienteView.IdentificacaoParcial.class, AvaliacaoView.Avaliacao.class,
			EvolucaoView.ListaEvolucao.class, ExameView.ListaExame.class, PacienteView.Detalhe.class,MedicoView.Listar.class})
	@Fetch(FetchMode.JOIN)
	private Usuario usuario;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CETR_ID")
	@Fetch(FetchMode.JOIN)
	private CentroTransplante centroAvaliador;
	
	@NotNull
	@Column(name = "MEDI_TX_ARQUIVO_CRM")
	@JsonView(MedicoView.Detalhe.class)
	private String arquivoCrm;

	/**
	 * Lista de telefones do medico.
	 */
	@OneToMany(mappedBy = "medico", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@NotEmpty
	@Valid
	@JsonView({ BuscaView.Enriquecimento.class, DoadorView.AtualizacaoFase2.class, DoadorView.ContatoPassivo.class, DoadorView.ContatoFase2.class,
				MedicoView.Detalhe.class})
	@Fetch(FetchMode.SUBSELECT)
	@Where(clause="COTE_IN_EXCLUIDO = 0")
	private List<ContatoTelefonicoMedico> contatosTelefonicos;
	
	@NotNull
	@OneToOne(mappedBy="medico", cascade = CascadeType.ALL)
	@JsonView(MedicoView.Detalhe.class)
	private EnderecoContatoMedico endereco;
	
	@OneToMany(mappedBy = "medico", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@NotEmpty
	@Valid
	@Fetch(FetchMode.SUBSELECT)
	@Where(clause="EMCO_IN_EXCLUIDO = 0")
	@JsonView(MedicoView.Detalhe.class)
	private List<EmailContatoMedico> emails;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(	name = "MEDICO_CT_REFERENCIA", 
	joinColumns = @JoinColumn(name = "MEDI_ID", insertable = true), 
	inverseJoinColumns = @JoinColumn(name = "CETR_ID", insertable = true))
	@JsonView(MedicoView.Detalhe.class)
	private List<CentroTransplante> centrosReferencia;
	

	public Medico() {
		super();
	}

	public Medico(Long id) {
		this();
		this.id = id;
	}
	
	/**
	 * Construtor utilizado para criar uma instância de Medico a partir do PreCadastroMedico.  
	 * 
	 * @param preCadastro - Pré Cadastro Médico
	 */
	public Medico(PreCadastroMedico preCadastro){
		this();
		setNome(preCadastro.getNome());
		setCrm(preCadastro.getCrm());
		setArquivoCrm(preCadastro.getArquivoCrm());
		setContatosTelefonicosPreCadastro(preCadastro.getTelefones());
		setEnderecoPreCadastro(preCadastro.getEndereco());
		setEmailsPreCadastro(preCadastro.getEmails());
		setCentrosReferenciaPreCadastro(preCadastro.getCentrosReferencia());
	}

	/**
	 * Retorna o id do médico na base (sequencial).
	 * 
	 * @return
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Seta o id do médico (sequence gerado pelo banco).
	 * 
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Retorna o CRM do médico cadastrado.
	 * 
	 * @return
	 */
	public String getCrm() {
		return crm;
	}

	/**
	 * Seta o CRM do médico.
	 * 
	 * @param crm
	 */
	public void setCrm(String crm) {
		this.crm = crm;
	}

	/**
	 * Retorna o nome do médico cadastrado.
	 * 
	 * @return
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * Seta o nome do médico a ser cadastrado.
	 * 
	 * @param nome string - nome do médico.
	 */
	public void setNome(String nome) {
		this.nome = nome;
		if (this.nome != null) {
			this.nome = this.nome.toUpperCase();
		}
	}

	/**
	 * Retorna o usuário associado ao médico (caso o mesmo também seja usuário do sistema).
	 * 
	 * @return
	 */
	public Usuario getUsuario() {
		return usuario;
	}

	/**
	 * Seta o usuário associado ao médico.
	 * 
	 * @param usuario
	 */
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	/**
	 * Retorna o centro avaliador do médico.
	 * 
	 * @return CentroTransplante
	 */
	public CentroTransplante getCentroAvaliador() {
		return centroAvaliador;
	}

	/**
	 * Seta o centro avaliador do médico.
	 * 
	 * @param centroAvaliador
	 */
	public void setCentroAvaliador(CentroTransplante centroAvaliador) {
		this.centroAvaliador = centroAvaliador;
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
		result = prime * result + ( ( crm == null ) ? 0 : crm.hashCode() );
		result = prime * result + ( ( id == null ) ? 0 : id.hashCode() );
		result = prime * result + ( ( nome == null ) ? 0 : nome.hashCode() );
		result = prime * result + ( ( usuario == null ) ? 0 : usuario.hashCode() );
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
		if (!( obj instanceof Medico )) {
			return false;
		}
		Medico other = (Medico) obj;
		if (crm == null) {
			if (other.crm != null) {
				return false;
			}
		}
		else
			if (!crm.equals(other.crm)) {
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
		if (nome == null) {
			if (other.nome != null) {
				return false;
			}
		}
		else
			if (!nome.equals(other.nome)) {
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

	/**
	 * Telefones do médico.
	 * 
	 * @return
	 */
	public List<ContatoTelefonicoMedico> getContatosTelefonicos() {
		return contatosTelefonicos;
	}

	/**
	 * Telefones do médico.
	 * 
	 * @param contatosTelefonicos
	 */
	public void setContatosTelefonicos(List<ContatoTelefonicoMedico> contatosTelefonicos) {
		this.contatosTelefonicos = contatosTelefonicos;
	}
	
	/**
	 * Setter preparado para conversão entre o pré cadastro e o médico,
	 * que ocorre após a aprovação do pré cadastro.
	 * 
	 * @param telefones telefones definidos no pré cadastro.
	 */
	public void setContatosTelefonicosPreCadastro(List<PreCadastroMedicoTelefone> telefones){
		setContatosTelefonicos(
			telefones.stream().map(preTel -> {
				ContatoTelefonicoMedico tel = new ContatoTelefonicoMedico();
				tel.setCodArea(preTel.getCodArea());
				tel.setMedico(this);
				tel.setNome(preTel.getNome());
				tel.setNumero(preTel.getNumero());
				tel.setPrincipal(preTel.getPrincipal());
				tel.setTipo(preTel.getTipo());
				tel.setComplemento(preTel.getComplemento());
				return tel;
			}).collect(Collectors.toList())
		);
	}

	/**
	 * Endereço de contato do médico no Redome.
	 * 
	 * @return endereço de contato médico.
	 */
	public EnderecoContatoMedico getEndereco() {
		return endereco;
	}

	public void setEndereco(EnderecoContatoMedico endereco) {
		this.endereco = endereco;
	}
	
	/**
	 * Setter preparado para conversão entre o pré cadastro e o médico,
	 * que ocorre após a aprovação do pré cadastro.
	 * 
	 * @param preCadEndereco endereço definido no pré cadastro.
	 */
	public void setEnderecoPreCadastro(PreCadastroMedicoEndereco preCadEndereco) {
		EnderecoContatoMedico enderecoMedico = new EnderecoContatoMedico();
		enderecoMedico.setCep(preCadEndereco.getCep());
		enderecoMedico.setPais(preCadEndereco.getPais());
		enderecoMedico.setNumero(preCadEndereco.getNumero());
		enderecoMedico.setBairro(preCadEndereco.getBairro());
		enderecoMedico.setComplemento(preCadEndereco.getComplemento());
		enderecoMedico.setMunicipio(preCadEndereco.getMunicipio());
		enderecoMedico.setNomeLogradouro(preCadEndereco.getNomeLogradouro());
		enderecoMedico.setTipoLogradouro(preCadEndereco.getTipoLogradouro());
		enderecoMedico.setPrincipal(true);
		enderecoMedico.setMedico(this);		
		setEndereco(enderecoMedico);
	}

	/**
	 * Arquivo com o comprovação do CRM informado no pré cadastro.
	 * Após aprovação, o mesmo é associado ao médico criado.
	 * @return
	 */
	public String getArquivoCrm() {
		return arquivoCrm;
	}

	public void setArquivoCrm(String arquivoCrm) {
		this.arquivoCrm = arquivoCrm;
	}

	public List<EmailContatoMedico> getEmails() {
		return emails;
	}

	public void setEmails(List<EmailContatoMedico> emails) {
		this.emails = emails;
	}
	
	/**
	 * Setter preparado para conversão entre o pré cadastro e o médico,
	 * que ocorre após a aprovação do pré cadastro.
	 * 
	 * @param preCadEmails lista de e-mails pré cadastrados para o médico.
	 */
	public void setEmailsPreCadastro(List<PreCadastroMedicoEmail> preCadEmails){
		setEmails(
			preCadEmails.stream().map(preEmail -> {
				EmailContatoMedico email = new EmailContatoMedico();
				email.setEmail(preEmail.getEmail());
				email.setPrincipal(preEmail.getPrincipal());
				email.setMedico(this);
				return email;
			}).collect(Collectors.toList())
		);
	}

	public List<CentroTransplante> getCentrosReferencia() {
		return centrosReferencia;
	}
	
	public void setCentrosReferencia(List<CentroTransplante> centrosReferencia) {
		this.centrosReferencia = centrosReferencia;
	}
	
	/**
	 * Recria a coleção para evitar erro de referência compartilhada com o pré cadastro de médico.
	 * 
	 * @param centrosReferencia lista de centros de referência que o médico atende.
	 */
	public void setCentrosReferenciaPreCadastro(List<CentroTransplante> centrosReferencia){
		setCentrosReferencia(
			new ArrayList<CentroTransplante>(centrosReferencia)
		);
	}

}

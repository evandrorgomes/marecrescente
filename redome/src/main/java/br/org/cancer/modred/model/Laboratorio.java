package br.org.cancer.modred.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.BuscaView;
import br.org.cancer.modred.controller.view.CentroTransplanteView;
import br.org.cancer.modred.controller.view.ExameView;
import br.org.cancer.modred.controller.view.GenotipoView;
import br.org.cancer.modred.controller.view.LaboratorioView;
import br.org.cancer.modred.controller.view.PacienteView;
import br.org.cancer.modred.controller.view.PedidoExameView;
import br.org.cancer.modred.model.security.Usuario;


/**
 * Classe de representação de laboratórios vinculados a doadores.
 * @author Filipe Paes
 * 
 */
@Entity
public class Laboratorio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="LABO_ID")
	@JsonView({LaboratorioView.Detalhe.class, LaboratorioView.Listar.class, ExameView.ListaExame.class, ExameView.ConferirExame.class 
		,LaboratorioView.ListarReceberExame.class, PedidoExameView.Detalhe.class, LaboratorioView.ListarCT.class,
		CentroTransplanteView.Detalhe.class, PacienteView.Rascunho.class, GenotipoView.ListaExame.class, 
		GenotipoView.Divergente.class})	
	private Long id;

	@Column(name="LABO_TX_NOME")
	@JsonView({LaboratorioView.Detalhe.class, LaboratorioView.Listar.class, ExameView.ListaExame.class, ExameView.ConferirExame.class,PedidoExameView.Detalhe.class,
		BuscaView.UltimoPedidoExame.class, LaboratorioView.ListarCT.class, CentroTransplanteView.Detalhe.class, PedidoExameView.ListarTarefas.class, 
		GenotipoView.ListaExame.class, GenotipoView.Divergente.class })
	private String nome;
	
	@Column(name="LABO_IN_FAZ_CT")
	private Boolean fazExameCT;
	
	@Column(name="LABO_NR_QUANT_EXAME_CT")
	@JsonView(LaboratorioView.ListarCT.class)
	private Integer quantidadeExamesCT;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "laboratorio", fetch = FetchType.LAZY)
	@Fetch(FetchMode.SUBSELECT)
	private List<InstrucaoColeta> instrucoesColeta;
	
	@Column(name="LABO_TX_NOME_CONTATO")
	private String nomeContato;
	
	@NotNull
	@OneToOne(mappedBy = "laboratorio", fetch = FetchType.LAZY)
	@JsonView({LaboratorioView.Detalhe.class, LaboratorioView.ListarCT.class, LaboratorioView.Listar.class})
	private EnderecoContatoLaboratorio endereco;
	
	@Transient
	@JsonView(LaboratorioView.ListarCT.class)
	private Long quantidadeAtual;
	
	@Column(name="LABO_NR_ID_REDOMEWEB")
	private Long idRedomeWeb;
		
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "laboratorio", fetch = FetchType.LAZY)
	@Fetch(FetchMode.SUBSELECT)
	@JsonView({ LaboratorioView.Detalhe.class, GenotipoView.Divergente.class })
	private List<EmailContatoLaboratorio> emails;
	
	@JsonView({LaboratorioView.Detalhe.class, LaboratorioView.ListarCT.class, LaboratorioView.Listar.class, LaboratorioView.Detalhe.class})
	@OneToMany(mappedBy="laboratorio", cascade={CascadeType.ALL}, fetch = FetchType.LAZY)
	private List<Usuario> usuarios;
	
	
	//Propriedade criada como transiente para utilização na criação do historico da busca
	@Transient
	private Match match;
	
	/**
	 * Contrutor padrão.
	 */
	public Laboratorio() {
	}

	/**
	 * contrutor sobrecarregado para inicição da entity com os parametros abaixo.
	 * 
	 * 
	 * @param id identificador da entiry
	 * @param nome nome do laboratorio.
	 * @param quantidadeAtual quantidade Atual de pedidos de fase 3 em aberto.
	 * @param quantidadeExamesCT Quantidade máxima que o laboratório pode fazer de exames de ct
	 */
	public Laboratorio(Long id, String nome, Long quantidadeAtual, Integer quantidadeExamesCT, String descricaoMunicipio, String siglaUf) {
		this.id = id;
		this.nome = nome;
		this.quantidadeAtual = quantidadeAtual;
		this.quantidadeExamesCT = quantidadeExamesCT;
		this.endereco = new EnderecoContatoLaboratorio(descricaoMunicipio, siglaUf);
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
	}
	
	/**
	 * @return the fazExameCT
	 */
	public Boolean getFazExameCT() {
		return fazExameCT;
	}

	/**
	 * @param fazExameCT the fazExameCT to set
	 */
	public void setFazExameCT(Boolean fazExameCT) {
		this.fazExameCT = fazExameCT;
	}

	/**
	 * @return the quantidadeExamesCT
	 */
	public Integer getQuantidadeExamesCT() {
		return quantidadeExamesCT;
	}
	
	/**
	 * @param quantidadeExamesCT the quantidadeExamesCT to set
	 */
	public void setQuantidadeExamesCT(Integer quantidadeExamesCT) {
		this.quantidadeExamesCT = quantidadeExamesCT;
	}
	
	/**
	 * @return the instrucoesColeta
	 */
	public List<InstrucaoColeta> getInstrucoesColeta() {
		return instrucoesColeta;
	}
	
	/**
	 * @param instrucoesColeta the instrucoesColeta to set
	 */
	public void setInstrucoesColeta(List<InstrucaoColeta> instrucoesColeta) {
		this.instrucoesColeta = instrucoesColeta;
	}
	
	
	/**
	 * @return the nomeContato
	 */
	public String getNomeContato() {
		return nomeContato;
	}


	
	/**
	 * @param nomeContato the nomeContato to set
	 */
	public void setNomeContato(String nomeContato) {
		this.nomeContato = nomeContato;
	}
	
	public EnderecoContatoLaboratorio getEndereco() {
		return endereco;
	}
	
	public void setEndereco(EnderecoContatoLaboratorio endereco) {
		this.endereco = endereco;
	}
	
	/**
	 * @return the quantidadeAtual
	 */
	public Long getQuantidadeAtual() {
		return quantidadeAtual;
	}

	
	/**
	 * @param quantidadeAtual the quantidadeAtual to set
	 */
	public void setQuantidadeAtual(Long quantidadeAtual) {
		this.quantidadeAtual = quantidadeAtual;
	}
	
	/**
	 * @return the idRedomeWeb
	 */
	public Long getIdRedomeWeb() {
		return idRedomeWeb;
	}

	/**
	 * @param idRedomeWeb the idRedomeWeb to set
	 */
	public void setIdRedomeWeb(Long idRedomeWeb) {
		this.idRedomeWeb = idRedomeWeb;
	}

	/**
	 * @return the emails
	 */
	public List<EmailContatoLaboratorio> getEmails() {
		return emails;
	}

	/**
	 * @param emails the emails to set
	 */
	public void setEmails(List<EmailContatoLaboratorio> emails) {
		this.emails = emails;
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
	
	/**
	 * @return the usuarios
	 */
	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	/**
	 * @param usuarios the usuarios to set
	 */
	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		if (getClass() != obj.getClass()) {
			return false;
		}
		Laboratorio other = (Laboratorio) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} 
		else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}
	
	
}
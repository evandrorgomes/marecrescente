package br.org.cancer.modred.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.AvaliacaoView;
import br.org.cancer.modred.controller.view.EvolucaoView;
import br.org.cancer.modred.controller.view.PacienteView;
import br.org.cancer.modred.controller.view.PedidoTransferenciaCentroView;

/**
 * Classe de persistencia de CID.
 * 
 * @author bruno.sousa
 *
 */
@Entity
@Table(name = "CID")
public class Cid implements IDominioInternacionalizado<CidIdioma> {

	private static final long serialVersionUID = -1556811471747568988L;

	/**
	 * Chave que identifica com exclusividade uma instância desta classe.
	 */
	@Id
	@Column(name = "CID_ID")
	@NotNull
	@JsonView({ EvolucaoView.ListaEvolucao.class, PacienteView.Detalhe.class,
			PacienteView.IdentificacaoCompleta.class, PacienteView.IdentificacaoParcial.class, PacienteView.Diagnostico.class,
			PacienteView.Rascunho.class})
	private Long id;
	/**
	 * Flag que informa se a cid está contemplada pela portaria.
	 */
	@Column(name = "CID_IN_TRANSPLANTE")
	@Type(type = "numeric_boolean")
	@JsonView({ EvolucaoView.ListaEvolucao.class, PacienteView.Detalhe.class,
			PacienteView.IdentificacaoCompleta.class, PacienteView.IdentificacaoParcial.class, AvaliacaoView.ListarAvaliacoesPacientes.class,
			PedidoTransferenciaCentroView.ListarTarefas.class, PacienteView.DadosPessoais.class, PacienteView.Diagnostico.class,
			PacienteView.Rascunho.class})
	private Boolean transplante;
	/**
	 * Código alfanumerico que representa a cid.
	 */
	@Column(name = "CID_TX_CODIGO")
	@JsonView({ EvolucaoView.ListaEvolucao.class, PacienteView.Detalhe.class,
			PacienteView.IdentificacaoCompleta.class, PacienteView.IdentificacaoParcial.class, AvaliacaoView.ListarAvaliacoesPacientes.class,
			PedidoTransferenciaCentroView.ListarTarefas.class, PacienteView.Diagnostico.class,
			PacienteView.Rascunho.class})
	private String codigo;

	/**
	 * Código alfanumerico que representa a sigla da cid no Emdis.
	 */
	@Column(name = "CID_TX_SIGLA_CRIR")
	@JsonView({ EvolucaoView.ListaEvolucao.class, PacienteView.Detalhe.class,
			PacienteView.IdentificacaoCompleta.class, PacienteView.IdentificacaoParcial.class, AvaliacaoView.ListarAvaliacoesPacientes.class,
			PedidoTransferenciaCentroView.ListarTarefas.class, PacienteView.Diagnostico.class,
			PacienteView.Rascunho.class})
	private String siglaCrir;
	
	/**
	 * lista de diagnosticos.
	 */
	@OneToMany(mappedBy = "cid", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Diagnostico> diagnosticos;

	/**
	 * lista de estagios dessa cid.
	 */
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(	name = "CID_ESTAGIO_DOENCA",
				joinColumns =
	{ @JoinColumn(name = "CID_ID") },
				inverseJoinColumns =
	{ @JoinColumn(name = "ESDO_ID") })
	@JsonIgnore
	private List<EstagioDoenca> estagioDoencas;

	@MapKeyColumn(name = "IDIO_ID")
	@Fetch(FetchMode.JOIN)
	@OneToMany(mappedBy = "id.cid", fetch = FetchType.LAZY)
	@JsonIgnore
	private Map<Integer, CidIdioma> internacionalizacao;
	
	@Column(name = "CID_NR_IDADE_MINIMA")
	@JsonView({PacienteView.DadosPessoais.class, PacienteView.Rascunho.class})
	private Integer idadeMinima;
	
	@Column(name = "CID_NR_IDADE_MAXIMA")
	@JsonView({PacienteView.DadosPessoais.class, PacienteView.Rascunho.class})
	private Integer idadeMaxima;

	/**
	 * Contrutor padrão.
	 */
	public Cid() {
		super();
	}

	/**
	 * Método construtor para popular previamente a CID.
	 * 
	 * @param id id
	 * @param transplante tra
	 * @param codigo codi
	 * @param descricao des
	 */
	public Cid(Long id, Boolean transplante, String codigo, String descricao, Idioma idioma) {
		this(id, codigo, descricao, idioma);
		this.transplante = transplante;
	}

	/**
	 * Construtor para popular a CID com informações básicas.
	 * 
	 * @param id da cid
	 * @param codigo da cid
	 * @param descricao da cid no idioma abaixo
	 * @param idioma da descrição da cid
	 */
	public Cid(Long id, String codigo, String descricao, Idioma idioma) {
		this.id = id;
		this.transplante = false;
		this.codigo = codigo;
		this.adicionarDescricao(idioma, descricao);
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Seta o atributo id.
	 * 
	 * @param id Primary key da cid
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Recupera o atributo que diz se contempla transplante.
	 * 
	 * @return transplante
	 */
	public Boolean getTransplante() {
		return transplante;
	}

	/**
	 * Seta o atributo que diz se contempla transplante.
	 * 
	 * @param transplante
	 */
	public void setTransplante(Boolean transplante) {
		this.transplante = transplante;
	}

	/**
	 * Recupera o código do cid.
	 * 
	 * @return the codigo
	 */
	public String getCodigo() {
		return codigo;
	}

	/**
	 * Seta o código do cid.
	 * 
	 * @param codigo
	 */
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	/**
	 * Recupera a descrição do cid.
	 * 
	 * @return the descricao
	 */
	@JsonView({ EvolucaoView.ListaEvolucao.class, PacienteView.Detalhe.class, PacienteView.IdentificacaoCompleta.class,
			PacienteView.IdentificacaoParcial.class, AvaliacaoView.ListarAvaliacoesPacientes.class,
			PedidoTransferenciaCentroView.ListarTarefas.class, PacienteView.Diagnostico.class,
			PacienteView.Rascunho.class})
	@JsonProperty(access = Access.READ_ONLY)
	public String getDescricao() {

		return getValorPropriedadeInternacionalizada("Descricao");
	}

	/**
	 * Recupera a lista de diagnosticos de 1 cid.
	 * 
	 * @return diagnosticos
	 */
	public List<Diagnostico> getDiagnosticos() {
		return diagnosticos;
	}

	/**
	 * Seta a lista de diagnosticos de um CID.
	 * 
	 * @param diagnosticos
	 */
	public void setDiagnosticos(List<Diagnostico> diagnosticos) {
		this.diagnosticos = diagnosticos;
	}

	/**
	 * Recupera a lista de estagios de doença de um cid.
	 * 
	 * @return estagioDoencas
	 */
	public List<EstagioDoenca> getEstagioDoencas() {
		return estagioDoencas;
	}

	/**
	 * Seta a lista de estagios de doenca de um cid.
	 * 
	 * @param estagioDoencas
	 */
	public void setEstagioDoencas(List<EstagioDoenca> estagioDoencas) {
		this.estagioDoencas = estagioDoencas;
	}

	/**
	 * @return the camposTraduzidos
	 */
	public Map<Integer, CidIdioma> getInternacionalizacao() {
		return this.internacionalizacao;
	}

	/**
	 * @return the idadeMinima
	 */
	public Integer getIdadeMinima() {
		return idadeMinima;
	}

	/**
	 * @param idadeMinima the idadeMinima to set
	 */
	public void setIdadeMinima(Integer idadeMinima) {
		this.idadeMinima = idadeMinima;
	}

	/**
	 * @return the idadeMaxima
	 */
	public Integer getIdadeMaxima() {
		return idadeMaxima;
	}

	/**
	 * @param idadeMaxima the idadeMaxima to set
	 */
	public void setIdadeMaxima(Integer idadeMaxima) {
		this.idadeMaxima = idadeMaxima;
	}

	private void adicionarDescricao(Idioma idioma, String descricao) {
		final CidIdioma cidIdioma = new CidIdioma(this, idioma, descricao);

		if (this.internacionalizacao == null) {
			this.internacionalizacao = new HashMap<Integer, CidIdioma>();
		}

		this.internacionalizacao.put(idioma.getId(), cidIdioma);
	}
	
	/**
	 * @return the siglaCrir
	 */
	public String getSiglaCrir() {
		return siglaCrir;
	}

	/**
	 * @param siglaCrir the siglaCrir to set
	 */
	public void setSiglaCrir(String siglaCrir) {
		this.siglaCrir = siglaCrir;
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
		result = prime * result + ( ( codigo == null ) ? 0 : codigo.hashCode() );
		result = prime * result + ( ( id == null ) ? 0 : id.hashCode() );
		result = prime * result + ( ( transplante == null ) ? 0 : transplante.hashCode() );
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
		if (!( obj instanceof Cid )) {
			return false;
		}
		Cid other = (Cid) obj;
		if (codigo == null) {
			if (other.codigo != null) {
				return false;
			}
		}
		else
			if (!codigo.equals(other.codigo)) {
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
		if (transplante == null) {
			if (other.transplante != null) {
				return false;
			}
		}
		else
			if (!transplante.equals(other.transplante)) {
				return false;
			}
		return true;
	}

	@Override
	public String toString() {
		return "ID " + this.id + " codigo " + this.codigo;
	}
	
}
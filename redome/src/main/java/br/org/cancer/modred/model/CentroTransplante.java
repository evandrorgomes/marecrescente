package br.org.cancer.modred.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Where;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.BuscaView;
import br.org.cancer.modred.controller.view.CentroTransplanteView;
import br.org.cancer.modred.controller.view.DoadorView;
import br.org.cancer.modred.controller.view.MedicoView;
import br.org.cancer.modred.controller.view.PacienteView;
import br.org.cancer.modred.controller.view.PedidoColetaView;
import br.org.cancer.modred.controller.view.PedidoTransferenciaCentroView;
import br.org.cancer.modred.controller.view.PedidoWorkupView;
import br.org.cancer.modred.controller.view.PreCadastroMedicoView;
import br.org.cancer.modred.controller.view.SolicitacaoView;
import br.org.cancer.modred.controller.view.TarefaView;
import br.org.cancer.modred.controller.view.TransportadoraView;
import br.org.cancer.modred.controller.view.UsuarioView;
import br.org.cancer.modred.controller.view.WorkupView;
import br.org.cancer.modred.model.domain.EntityStatus;
import net.sf.ehcache.pool.sizeof.annotations.IgnoreSizeOf;

/**
 * Classe que representa o centro transplante (avaliadores e/ou transplantadores) do cadastro do paciente. Selecionado apennas
 * quando o mesmo é não aparentado {@see Paciente}.
 * 
 * @author Rafael Pizão
 *
 */
@Entity
@Table(name = "CENTRO_TRANSPLANTE")
@SequenceGenerator(name = "SQ_CETR_ID", sequenceName = "SQ_CETR_ID", allocationSize = 1)
public class CentroTransplante implements Serializable, Comparable<CentroTransplante> {

	private static final long serialVersionUID = -3749821347178713784L;

	public static final String HOSPITAL_DAS_CLINICAS_DE_PORTO_ALEGRE_UFRGS = "HOSPITAL DAS CLÍNICAS DE PORTO ALEGRE - UFRGS";

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_CETR_ID")
	@Column(name = "CETR_ID")
	@JsonView(value = { TarefaView.Listar.class, PedidoWorkupView.DetalheWorkup.class, WorkupView.Resultado.class,
			PedidoColetaView.DetalheColeta.class, CentroTransplanteView.Detalhe.class, UsuarioView.ListaBasica.class,
			MedicoView.Detalhe.class, PacienteView.Consulta.class, PacienteView.Detalhe.class, PedidoTransferenciaCentroView.Detalhe.class,
			PacienteView.Rascunho.class, SolicitacaoView.detalhe.class, SolicitacaoView.detalheWorkup.class})
	private Long id;

	@Column(name = "CETR_TX_NOME")
	@JsonView({ TarefaView.Listar.class, PedidoWorkupView.DetalheWorkup.class, 
				WorkupView.Resultado.class, PedidoColetaView.DetalheColeta.class, 
				PacienteView.Consulta.class, TransportadoraView.Listar.class, 
				TransportadoraView.AgendarTransporte.class, PedidoWorkupView.AgendamentoWorkup.class, 
				PedidoColetaView.SugerirDataColeta.class, CentroTransplanteView.Detalhe.class, UsuarioView.ListaBasica.class,
				PreCadastroMedicoView.Detalhe.class, MedicoView.Detalhe.class,PacienteView.Detalhe.class, 
				PedidoTransferenciaCentroView.Detalhe.class, PedidoWorkupView.SugerirDataWorkup.class, SolicitacaoView.detalheWorkup.class})
	private String nome;

	/**
	 * CNPJ do centro avaliador. Na integração com REREME poderá ser utilizado. como chave.
	 */
	@Column(name = "CETR_TX_CNPJ")
	@JsonView(CentroTransplanteView.Detalhe.class)
	private String cnpj;

	/**
	 * CNES (Cadastro Nacional de Estabelecimentos de Saúde) ao qual está inserido o centro avaliador. Na integração com REREME
	 * poderá ser utilizado como chave.
	 */
	@Column(name = "CETR_TX_CNES")
	@JsonView(CentroTransplanteView.Detalhe.class)
	private String cnes;

	/**
	 * Estado atual da entidade.
	 */
	@Column(name = "CETR_NR_ENTITY_STATUS")
	@NotNull
	@JsonIgnore
	private Long entityStatus;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(	name = "FUNCAO_CENTRO_TRANSPLANTE",
				joinColumns =
	{ @JoinColumn(name = "CETR_ID") },
				inverseJoinColumns =
	{ @JoinColumn(name = "FUTR_ID") })
	@JsonView(CentroTransplanteView.Detalhe.class)
	@IgnoreSizeOf
	private List<FuncaoTransplante> funcoes;

	/**
	 * Lista de telefones do centro de transplante.
	 */
	@OneToMany(mappedBy = "centroTransplante", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@NotEmpty
	@Valid
	@JsonView({ BuscaView.Enriquecimento.class, DoadorView.AtualizacaoFase2.class, DoadorView.ContatoPassivo.class,
			DoadorView.ContatoFase2.class, CentroTransplanteView.Detalhe.class })
	@Fetch(FetchMode.SUBSELECT)
	@Where(clause="COTE_IN_EXCLUIDO = 0")
	private List<ContatoTelefonicoCentroTransplante> contatosTelefonicos;

	@OneToMany(mappedBy = "centroTransplante", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@Where(clause="ENCO_IN_EXCLUIDO = 0")
	@NotNull
	@JsonView({ TransportadoraView.AgendarTransporte.class, CentroTransplanteView.Detalhe.class, SolicitacaoView.detalhe.class })
	private List<EnderecoContatoCentroTransplante> enderecos;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LABO_ID")	
	@JsonView(CentroTransplanteView.Detalhe.class)
	private Laboratorio laboratorioPreferencia;
	
	@OneToMany(mappedBy="centroTransplante", fetch = FetchType.LAZY)
	@JsonView(CentroTransplanteView.Detalhe.class)
	private List<CentroTransplanteUsuario> centroTransplanteUsuarios;
	
	@OneToMany(mappedBy = "centroTransplante", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@Where(clause="EMCO_IN_EXCLUIDO = 0")
	@NotNull
	@JsonView({ CentroTransplanteView.Detalhe.class })
	private List<EmailContatoCentroTransplante> emails;

	public CentroTransplante() {
		this.centroTransplanteUsuarios = new ArrayList<CentroTransplanteUsuario>();
	}

	public CentroTransplante(Long id, String nome) {
		this.id = id;
		this.nome = nome;
	}

	public CentroTransplante(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getCnes() {
		return cnes;
	}

	public void setCnes(String cnes) {
		this.cnes = cnes;
	}

	/**
	 * Método para obter o estado atual da entidade.
	 * 
	 * @return EntityStatus - o estado atual da entidade.
	 */
	public EntityStatus getEntityStatus() {
		return EntityStatus.valueOf(entityStatus);
	}

	/**
	 * Método para definir o estado atual da entidade.
	 * 
	 * @param entityStatus - o estado atual da entidade.
	 */
	public void setEntityStatus(EntityStatus entityStatus) {
		if (entityStatus != null) {
			this.entityStatus = entityStatus.getId();
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ( ( id == null ) ? 0 : id.hashCode() );
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
		CentroTransplante other = (CentroTransplante) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		}
		else {
			if (!id.equals(other.id)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Funções de um centro de transplante.
	 * 
	 * @return
	 */
	public List<FuncaoTransplante> getFuncoes() {
		return funcoes;
	}

	/**
	 * Funções de um centro de transplante.
	 * 
	 * @param funcoes
	 */
	public void setFuncoes(List<FuncaoTransplante> funcoes) {
		this.funcoes = funcoes;
	}

	/**
	 * Telefones do centro de transplante.
	 * 
	 * @return
	 */
	public List<ContatoTelefonicoCentroTransplante> getContatosTelefonicos() {
		return contatosTelefonicos;
	}

	/**
	 * Telefones do centro de transplante.
	 * 
	 * @param contatosTelefonicos
	 */
	public void setContatosTelefonicos(List<ContatoTelefonicoCentroTransplante> contatosTelefonicos) {
		this.contatosTelefonicos = contatosTelefonicos;
	}

	/**
	 * @return the enderecos
	 */
	public List<EnderecoContatoCentroTransplante> getEnderecos() {
		return enderecos;
	}

	/**
	 * @param enderecos the enderecos to set
	 */
	public void setEnderecos(List<EnderecoContatoCentroTransplante> enderecos) {
		this.enderecos = enderecos;
	}
	
	/**
	 * @return the laboratorioPreferencia
	 */
	public Laboratorio getLaboratorioPreferencia() {
		return laboratorioPreferencia;
	}
	
	/**
	 * @param laboratorioPreferencia the laboratorioPreferencia to set
	 */
	public void setLaboratorioPreferencia(Laboratorio laboratorioPreferencia) {
		this.laboratorioPreferencia = laboratorioPreferencia;
	}
	
	/**
	 * Formatar a lista de contatos telefônicos associados ao centro de transplante.
	 * 
	 * @return lista de textos formatados dos contatos.
	 */
	public List<String> getContatosFormatadosParaExibicao(){
		final List<String> contatosCentroTransplante = new ArrayList<String>();
		if (CollectionUtils.isNotEmpty(getContatosTelefonicos())) {
			getContatosTelefonicos().forEach(contato -> {
				contatosCentroTransplante.add(contato.toString());
			});
		}
		return contatosCentroTransplante;
	}

	@Override
	public int compareTo(CentroTransplante other) {		
		return this.getId().compareTo(other.getId());
	}

	/**
	 * @return the centroTransplanteUsuarios
	 */
	public List<CentroTransplanteUsuario> getCentroTransplanteUsuarios() {
		return centroTransplanteUsuarios;
	}

	/**
	 * @param centroTransplanteUsuarios the centroTransplanteUsuarios to set
	 */
	public void setCentroTransplanteUsuarios(List<CentroTransplanteUsuario> centroTransplanteUsuarios) {
		this.centroTransplanteUsuarios = centroTransplanteUsuarios;
	}

	/**
	 * @return the emails
	 */
	public List<EmailContatoCentroTransplante> getEmails() {
		return emails;
	}

	/**
	 * @param emails the emails to set
	 */
	public void setEmails(List<EmailContatoCentroTransplante> emails) {
		this.emails = emails;
	}
	

}
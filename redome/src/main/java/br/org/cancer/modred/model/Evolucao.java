package br.org.cancer.modred.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.envers.NotAudited;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.AvaliacaoCamaraTecnicaView;
import br.org.cancer.modred.controller.view.AvaliacaoPrescricaoView;
import br.org.cancer.modred.controller.view.AvaliacaoView;
import br.org.cancer.modred.controller.view.EvolucaoView;
import br.org.cancer.modred.controller.view.PacienteView;
import br.org.cancer.modred.controller.view.PedidoTransferenciaCentroView;
import br.org.cancer.modred.model.security.CriacaoAuditavel;
import br.org.cancer.modred.model.security.Usuario;

/**
 * Classe de persistencia de Evolução do paciente.
 * 
 * @author bruno.sousa
 *
 */
@Entity
@SequenceGenerator(	name = "EVOLUCAO_EVOLID_GENERATOR", sequenceName = "SQ_EVOL_ID",
					allocationSize = 1)
@Table(name = "EVOLUCAO")

public class Evolucao extends CriacaoAuditavel implements Serializable {

	private static final long serialVersionUID = 4495027634419461121L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EVOLUCAO_EVOLID_GENERATOR")
	@Column(name = "EVOL_ID")
	@JsonView({ EvolucaoView.ListaEvolucao.class, EvolucaoView.ConsultaEvolucao.class, AvaliacaoView.ListarPendencias.class })
	private Long id;

	@Column(name = "EVOL_DT_CRIACAO")
	@JsonView({EvolucaoView.ListaEvolucao.class, EvolucaoView.NovaEvolucao.class})
	private LocalDateTime dataCriacao;

	@JsonView({ EvolucaoView.NovaEvolucao.class, AvaliacaoView.Avaliacao.class, 
		EvolucaoView.ListaEvolucao.class, PedidoTransferenciaCentroView.Detalhe.class,AvaliacaoCamaraTecnicaView.Detalhe.class,
		PacienteView.Rascunho.class})
	@Column(name = "EVOL_IN_CMV")
	private Boolean cmv;

	@Column(name = "EVOL_TX_CONDICAO_ATUAL")
	@NotNull
	@Length(max = 4000)
	@JsonView({ EvolucaoView.ListaEvolucao.class, AvaliacaoView.Avaliacao.class, PedidoTransferenciaCentroView.Detalhe.class
		,AvaliacaoCamaraTecnicaView.Detalhe.class, PacienteView.Rascunho.class })
	private String condicaoAtual;

	@Column(name = "EVOL_TX_TRATAMENTO_ANTERIOR")
	@NotNull
	@Length(max = 4000)
	@JsonView({ EvolucaoView.ListaEvolucao.class, AvaliacaoView.Avaliacao.class, PedidoTransferenciaCentroView.Detalhe.class
		,AvaliacaoCamaraTecnicaView.Detalhe.class, PacienteView.Rascunho.class })
	private String tratamentoAnterior;

	@Column(name = "EVOL_TX_TRATAMENTO_ATUAL")
	@NotNull
	@Length(max = 4000)
	@JsonView({ EvolucaoView.ListaEvolucao.class, AvaliacaoView.Avaliacao.class, PedidoTransferenciaCentroView.Detalhe.class
		,AvaliacaoCamaraTecnicaView.Detalhe.class, PacienteView.Rascunho.class })
	private String tratamentoAtual;

	@JsonView({ EvolucaoView.ListaEvolucao.class, EvolucaoView.NovaEvolucao.class, PacienteView.Rascunho.class,
			AvaliacaoView.Avaliacao.class, PedidoTransferenciaCentroView.Detalhe.class,AvaliacaoCamaraTecnicaView.Detalhe.class })
	@Column(name = "EVOL_VL_ALTURA", precision = 3, scale = 2)
	@NotNull
	@DecimalMin(value = "0", inclusive = false, message = "paciente.altura.maior.zero")
	@Digits(integer = 1, fraction = 2)
	private BigDecimal altura;

	@Column(name = "EVOL_VL_PESO", precision = 4, scale = 1)
	@NotNull
	@DecimalMin(value = "0", inclusive = false, message = "paciente.peso.maior.zero")
	@Digits(integer = 3, fraction = 1)
	@JsonView({ EvolucaoView.ListaEvolucao.class, AvaliacaoView.Avaliacao.class, PedidoTransferenciaCentroView.Detalhe.class,
				AvaliacaoCamaraTecnicaView.Detalhe.class, PacienteView.Rascunho.class, EvolucaoView.NovaEvolucao.class
				,PacienteView.IdentificacaoCompleta.class,PacienteView.IdentificacaoParcial.class, EvolucaoView.ConsultaEvolucao.class})
	private BigDecimal peso;

	@ManyToOne
	@JoinColumn(name = "COPA_ID")
	@NotNull
	@JsonView({ EvolucaoView.ListaEvolucao.class, AvaliacaoView.Avaliacao.class, PedidoTransferenciaCentroView.Detalhe.class
		,AvaliacaoCamaraTecnicaView.Detalhe.class, PacienteView.Rascunho.class })
	private CondicaoPaciente condicaoPaciente;

	@ManyToOne
	@JoinColumn(name = "ESDO_ID")
	@JsonView({ EvolucaoView.ListaEvolucao.class, AvaliacaoView.Avaliacao.class, PedidoTransferenciaCentroView.Detalhe.class
		,AvaliacaoCamaraTecnicaView.Detalhe.class, PacienteView.Rascunho.class })
	private EstagioDoenca estagioDoenca;

	@ManyToOne
	@JoinColumn(name = "MOTI_ID")
	@NotNull
	@JsonView({ EvolucaoView.ListaEvolucao.class, AvaliacaoView.Avaliacao.class, PedidoTransferenciaCentroView.Detalhe.class
		,AvaliacaoCamaraTecnicaView.Detalhe.class, PacienteView.Rascunho.class })
	private Motivo motivo;

	@ManyToOne
	@JoinColumn(name = "PACI_NR_RMR")
	@NotNull
	@JsonView({ EvolucaoView.ListaEvolucao.class })
	private Paciente paciente;

	@ManyToOne
	@JoinColumn(name = "USUA_ID")
	private Usuario usuario;
	
	
	@Valid
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "evolucao", fetch = FetchType.LAZY)
	@NotAudited
	@Fetch(FetchMode.SUBSELECT)
	@JsonView({ EvolucaoView.ListaEvolucao.class, AvaliacaoView.Avaliacao.class, PacienteView.Rascunho.class })
	private List<ArquivoEvolucao> arquivosEvolucao;
	
	/**
	 * Se o paciente já fez exame de anticorpo ou não.
	 */
	@NotNull
	@Column(name = "EVOL_IN_EXAME_ANTICORPO")
	@JsonView({ EvolucaoView.ListaEvolucao.class, AvaliacaoView.Avaliacao.class, PedidoTransferenciaCentroView.Detalhe.class
		,AvaliacaoCamaraTecnicaView.Detalhe.class, PacienteView.Rascunho.class })
	private Boolean exameAnticorpo;
	
	/**
	 * Data do resultado de exame de anticorpo.
	 */
	@Column(name = "EVOL_DT_EXAME_ANTICORPO")
	@JsonView({ EvolucaoView.ListaEvolucao.class, AvaliacaoView.Avaliacao.class, PedidoTransferenciaCentroView.Detalhe.class
		,AvaliacaoCamaraTecnicaView.Detalhe.class, PacienteView.Rascunho.class })
	private LocalDate dataExameAnticorpo;
	
	/**
	 * Data do resultado de exame de anticorpo.
	 */
	@Column(name = "EVOL_TX_EXAME_ANTICORPO")
	@JsonView({ EvolucaoView.ListaEvolucao.class, AvaliacaoView.Avaliacao.class, PedidoTransferenciaCentroView.Detalhe.class
		,AvaliacaoCamaraTecnicaView.Detalhe.class, PacienteView.Rascunho.class })
	private String resultadoExameAnticorpo;
	
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(	name = "TIPO_TRANSPLANTE_EVOLUCAO", 
	joinColumns = @JoinColumn(name = "EVOL_ID", insertable = true), 
	inverseJoinColumns = @JoinColumn(name = "TITR_ID", insertable = true))
	@JsonView({ EvolucaoView.ListaEvolucao.class, AvaliacaoView.Avaliacao.class, PedidoTransferenciaCentroView.Detalhe.class
		,AvaliacaoCamaraTecnicaView.Detalhe.class, PacienteView.Rascunho.class, EvolucaoView.NovaEvolucao.class, 
		AvaliacaoPrescricaoView.Detalhe.class, EvolucaoView.ConsultaEvolucao.class})
	private List<TipoTransplante> tiposTransplante;
	
	/**
	 * Data do último transplate.
	 */
	@Column(name = "EVOL_DT_ULTIMO_TRANSPLANTE")
	@JsonView({EvolucaoView.ListaEvolucao.class, AvaliacaoView.Avaliacao.class, PedidoTransferenciaCentroView.Detalhe.class
		,AvaliacaoCamaraTecnicaView.Detalhe.class, PacienteView.Rascunho.class, EvolucaoView.NovaEvolucao.class, 
		AvaliacaoPrescricaoView.Detalhe.class, EvolucaoView.ConsultaEvolucao.class})
	private LocalDate dataUltimoTransplante;
	

	/**
	 * Construtor padrão da classe.
	 */
	public Evolucao() {
		super();
	}

	/**
	 * Retorna a primary key de evolucao.
	 * 
	 * @return id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Seta a primary key de evolucao.
	 * 
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Retorna a data e hora de criação.
	 * 
	 * @return dataCriacao
	 */
	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}

	/**
	 * Seta a data e a hora de criacao da evolucao.
	 * 
	 * @param dataCriacao
	 */
	@Override
	public void setDataCriacao(LocalDateTime dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	/**
	 * Recupera o CMV.
	 * 
	 * @return cmv - resultado exame cmv
	 */
	public Boolean getCmv() {
		return cmv;
	}

	/**
	 * Seta o CMV.
	 * 
	 * @param cmv - resultado exame cmv
	 */
	public void setCmv(Boolean cmv) {
		this.cmv = cmv;
	}

	/**
	 * @return the tiposTransplante
	 */
	public List<TipoTransplante> getTiposTransplante() {
		return tiposTransplante;
	}

	/**
	 * @param tiposTransplante the tiposTransplante to set
	 */
	public void setTiposTransplante(List<TipoTransplante> tiposTransplante) {
		this.tiposTransplante = tiposTransplante;
	}

	/**
	 * Recupera a condicao atual.
	 * 
	 * @return condicaoAtual
	 */
	public String getCondicaoAtual() {
		return condicaoAtual;
	}

	/**
	 * Seta a condicao atual.
	 * 
	 * @param condicaoAtual
	 */
	public void setCondicaoAtual(String condicaoAtual) {
		this.condicaoAtual = condicaoAtual;
	}

	/**
	 * Recupera a descricao do tratamento anterior.
	 * 
	 * @return tratamentoAnterior
	 */
	public String getTratamentoAnterior() {
		return tratamentoAnterior;
	}

	/**
	 * Seta a descricao do tratamento anterior.
	 * 
	 * @param tratamentoAnterior
	 */
	public void setTratamentoAnterior(String tratamentoAnterior) {
		this.tratamentoAnterior = tratamentoAnterior;
	}

	/**
	 * Recupera a descricao do tratamento atual.
	 * 
	 * @return tratamentoAtual
	 */
	public String getTratamentoAtual() {
		return tratamentoAtual;
	}

	/**
	 * Seta a descricao do tratamento atual.
	 * 
	 * @param tratamentoAtual
	 */
	public void setTratamentoAtual(String tratamentoAtual) {
		this.tratamentoAtual = tratamentoAtual;
	}

	/**
	 * Retorna a altura do paciente.
	 * 
	 * @return altura
	 */
	public BigDecimal getAltura() {
		return altura;
	}

	/**
	 * Seta a altura do paciente.
	 * 
	 * @param altura
	 */
	public void setAltura(BigDecimal altura) {
		this.altura = altura;
	}

	/**
	 * Retorna o peso do paciente.
	 * 
	 * @return peso
	 */
	public BigDecimal getPeso() {
		return peso;
	}

	/**
	 * Seta o peso do paciente.
	 * 
	 * @param peso
	 */
	public void setPeso(BigDecimal peso) {
		this.peso = peso;
	}

	/**
	 * Retorna a condicao do paciente.
	 * 
	 * @return condicaoPaciente
	 */
	public CondicaoPaciente getCondicaoPaciente() {
		return condicaoPaciente;
	}

	/**
	 * Seta a condicao do paciente.
	 * 
	 * @param condicaoPaciente
	 */
	public void setCondicaoPaciente(CondicaoPaciente condicaoPaciente) {
		this.condicaoPaciente = condicaoPaciente;
	}

	/**
	 * Retorna o estagio da doenca.
	 * 
	 * @return estagioDoenca
	 */
	public EstagioDoenca getEstagioDoenca() {
		return estagioDoenca;
	}

	/**
	 * Seta o estagio da doenca.
	 * 
	 * @param estagioDoenca
	 */
	public void setEstagioDoenca(EstagioDoenca estagioDoenca) {
		this.estagioDoenca = estagioDoenca;
	}

	/**
	 * Recupera o motivo.
	 * 
	 * @return motivo
	 */
	public Motivo getMotivo() {
		return motivo;
	}

	/**
	 * Seta o motivo.
	 * 
	 * @param motivo
	 */
	public void setMotivo(Motivo motivo) {
		this.motivo = motivo;
	}

	/**
	 * Recupera o paciente.
	 * 
	 * @return paciente
	 */
	public Paciente getPaciente() {
		return paciente;
	}

	/**
	 * Seta o paciente.
	 * 
	 * @param paciente
	 */
	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	@Override
	protected void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public List<ArquivoEvolucao> getArquivosEvolucao() {
		return arquivosEvolucao;
	}

	public void setArquivosEvolucao(List<ArquivoEvolucao> arquivosEvolucao) {
		this.arquivosEvolucao = arquivosEvolucao;
	}

	/**
	 * @return the exameAnticorpo
	 */
	public Boolean getExameAnticorpo() {
		return exameAnticorpo;
	}

	/**
	 * @param exameAnticorpo the exameAnticorpo to set
	 */
	public void setExameAnticorpo(Boolean exameAnticorpo) {
		this.exameAnticorpo = exameAnticorpo;
	}

	/**
	 * @return the dataExameAnticorpo
	 */
	public LocalDate getDataExameAnticorpo() {
		return dataExameAnticorpo;
	}

	/**
	 * @param dataExameAnticorpo the dataExameAnticorpo to set
	 */
	public void setDataExameAnticorpo(LocalDate dataExameAnticorpo) {
		this.dataExameAnticorpo = dataExameAnticorpo;
	}

	/**
	 * @return the resultadoExameAnticorpo
	 */
	public String getResultadoExameAnticorpo() {
		return resultadoExameAnticorpo;
	}

	/**
	 * @param resultadoExameAnticorpo the resultadoExameAnticorpo to set
	 */
	public void setResultadoExameAnticorpo(String resultadoExameAnticorpo) {
		this.resultadoExameAnticorpo = resultadoExameAnticorpo;
	}
	

	/**
	 * @return the dataUltimoTransplante
	 */
	public LocalDate getDataUltimoTransplante() {
		return dataUltimoTransplante;
	}

	/**
	 * @param dataUltimoTransplante the dataUltimoTransplante to set
	 */
	public void setDataUltimoTransplante(LocalDate dataUltimoTransplante) {
		this.dataUltimoTransplante = dataUltimoTransplante;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ( ( dataCriacao == null ) ? 0 : dataCriacao.hashCode() );
		result = prime * result + ( ( paciente == null ) ? 0 : paciente.hashCode() );
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
		Evolucao other = (Evolucao) obj;
		if (dataCriacao == null) {
			if (other.dataCriacao != null) {
				return false;
			}
		}
		else
			if (!dataCriacao.equals(other.dataCriacao)) {
				return false;
			}
		if (paciente == null) {
			if (other.paciente != null) {
				return false;
			}
		}
		else
			if (!paciente.equals(other.paciente)) {
				return false;
			}
		return true;
	}

	/**
	 * Adiciona um arquivo de evolução a lista de arquivos.
	 * @param arquivoEvolucao objeto a ser inserido na lista.
	 */
	public void adicionarArquivoEvolucao(ArquivoEvolucao arquivoEvolucao) {
		if(this.arquivosEvolucao == null){
			this.arquivosEvolucao = new ArrayList<ArquivoEvolucao>();
		}
		this.arquivosEvolucao.add(arquivoEvolucao);
	}

}
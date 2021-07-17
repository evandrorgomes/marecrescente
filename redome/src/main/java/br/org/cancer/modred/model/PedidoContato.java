package br.org.cancer.modred.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.AnaliseMedicaView;
import br.org.cancer.modred.controller.view.DoadorView;
import br.org.cancer.modred.controller.view.PedidoExameView;
import br.org.cancer.modred.controller.view.RegistroContatoView;
import br.org.cancer.modred.controller.view.TentativaContatoDoadorView;
import br.org.cancer.modred.model.annotations.EnumValues;
import br.org.cancer.modred.model.domain.AcaoPedidoContato;
import br.org.cancer.modred.model.security.Usuario;

/**
 * Representa o pedido de contato para determinada solicitação.
 * 
 * @author Pizão
 * 
 */
@Entity
@SequenceGenerator(name = "SQ_PECO_ID", sequenceName = "SQ_PECO_ID", allocationSize = 1)
@Table(name = "PEDIDO_CONTATO")
public class PedidoContato implements Serializable, Cloneable {

	private static final long serialVersionUID = 7774495959241636184L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_PECO_ID")
	@Column(name = "PECO_ID")
	@JsonView({TentativaContatoDoadorView.Consultar.class, PedidoExameView.ListarTarefas.class
		, RegistroContatoView.Consulta.class, DoadorView.ContatoFase2.class, AnaliseMedicaView.ListarTarefas.class,AnaliseMedicaView.Detalhe.class})
	private Long id;

	@Column(name = "PECO_DT_CRIACAO")
	@NotNull
	@JsonView({TentativaContatoDoadorView.Consultar.class, DoadorView.ContatoPassivo.class, PedidoExameView.ListarTarefas.class,
		DoadorView.ContatoFase2.class, AnaliseMedicaView.ListarTarefas.class, AnaliseMedicaView.Detalhe.class})
	private LocalDateTime dataCriacao;

	@Column(name = "PECO_IN_ABERTO")
	@NotNull
	@JsonView({TentativaContatoDoadorView.Consultar.class, DoadorView.ContatoPassivo.class })
	private boolean aberto;

	@ManyToOne
	@JoinColumn(name = "SOLI_ID")
	@JsonView({PedidoExameView.ListarTarefas.class,AnaliseMedicaView.ListarTarefas.class, AnaliseMedicaView.Detalhe.class})
	private Solicitacao solicitacao;

	@ManyToOne
	@JoinColumn(name = "HEEN_ID")
	private HemoEntidade hemoEntidade;

	@ManyToOne
	@JoinColumn(name = "DOAD_ID")
	@NotNull
	private DoadorNacional doador;
	
	@OneToMany(mappedBy = "pedidoContato", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@Fetch(FetchMode.SUBSELECT)
	@JsonView({ DoadorView.ContatoPassivo.class })
	private List<TentativaContatoDoador> tentativasContato;
	
	@Column(name = "PECO_DT_FINALIZACAO")
	private LocalDateTime dataFinalizacao;
	
	@Column(name = "PECO_IN_CONTACTADO")
	private Boolean contactado;
	
	@Column(name = "PECO_IN_CONTACTADO_TERCEIRO")
	private Boolean contactadoPorTerceiro;

	@Column(name = "PECO_IN_PASSIVO")
	private Boolean passivo;
	
	@EnumValues(AcaoPedidoContato.class)
	@Column(name = "PECO_NR_ACAO")
	private Long acao;
	
	@ManyToOne
	@JoinColumn(name = "MOSD_ID")
	private MotivoStatusDoador motivoStatusDoador;

	@Column(name = "PECO_NR_TEMPO_INATIVACAO_TEMP")	
	private Long tempoInativacaoTemporaria;
	
	@Column(name = "PECO_IN_FINALIZACAO_AUTOMATICA")
	private Boolean finalizacaoAutomatica;
	
	@ManyToOne
	@JoinColumn(name = "USUA_ID")
	private Usuario usuario;

	@Column(name = "PECO_IN_TIPO_CONTATO")
	private Long tipoContato;
	
	public PedidoContato() {
		super();
	}

	/**
	 * Identificador da entidade.
	 * 
	 * @return ID da entidade.
	 */
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Data de realização da tentativa de contato.
	 * 
	 * @return data de criação da tentativa.
	 */
	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(LocalDateTime dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public boolean isAberto() {
		return aberto;
	}

	public void setAberto(boolean aberto) {
		this.aberto = aberto;
	}

	public Solicitacao getSolicitacao() {
		return solicitacao;
	}

	public void setSolicitacao(Solicitacao solicitacao) {
		this.solicitacao = solicitacao;
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
	public void setDoador(DoadorNacional doador) {
		this.doador = doador;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ( aberto ? 1231 : 1237 );
		result = prime * result + ( ( dataCriacao == null ) ? 0 : dataCriacao.hashCode() );
		result = prime * result + ( ( id == null ) ? 0 : id.hashCode() );
		result = prime * result + ( ( solicitacao == null ) ? 0 : solicitacao.hashCode() );
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
		if (!( obj instanceof PedidoContato )) {
			return false;
		}
		PedidoContato other = (PedidoContato) obj;
		if (aberto != other.aberto) {
			return false;
		}
		if (dataCriacao == null) {
			if (other.dataCriacao != null) {
				return false;
			}
		}
		else
			if (!dataCriacao.equals(other.dataCriacao)) {
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
		if (solicitacao == null) {
			if (other.solicitacao != null) {
				return false;
			}
		}
		else
			if (!solicitacao.equals(other.solicitacao)) {
				return false;
			}
		return true;
	}

	/**
	 * Entidade de hemocentro.
	 * 
	 * @return
	 */
	public HemoEntidade getHemoEntidade() {
		return hemoEntidade;
	}

	/**
	 * Entidade de hemocentro.
	 * 
	 * @param hemoEntidade
	 */
	public void setHemoEntidade(HemoEntidade hemoEntidade) {
		this.hemoEntidade = hemoEntidade;
	}

	/**
	 * Tentativas de contato com o doador.
	 * 
	 * @return
	 */
	public List<TentativaContatoDoador> getTentativasContato() {
		return tentativasContato;
	}

	/**
	 * Tentativas de contato com o doador.
	 * 
	 * @param tentativasContato
	 */
	public void setTentativasContato(List<TentativaContatoDoador> tentativasContato) {
		this.tentativasContato = tentativasContato;
	}

	/**
	 * @return the dataFinalizacao
	 */
	public LocalDateTime getDataFinalizacao() {
		return dataFinalizacao;
	}

	/**
	 * @param dataFinalizacao the dataFinalizacao to set
	 */
	public void setDataFinalizacao(LocalDateTime dataFinalizacao) {
		this.dataFinalizacao = dataFinalizacao;
	}

	/**
	 * @return the contactado
	 */
	public Boolean getContactado() {
		return contactado;
	}

	/**
	 * @param contactado the contactado to set
	 */
	public void setContactado(Boolean contactado) {
		this.contactado = contactado;
	}

	/**
	 * @return the contactadoPorTerceiro
	 */
	public Boolean getContactadoPorTerceiro() {
		return contactadoPorTerceiro;
	}

	/**
	 * @param contactadoPorTerceiro the contactadoPorTerceiro to set
	 */
	public void setContactadoPorTerceiro(Boolean contactadoPorTerceiro) {
		this.contactadoPorTerceiro = contactadoPorTerceiro;
	}

	/**
	 * @return the acao
	 */
	public Long getAcao() {
		return acao;
	}

	/**
	 * @param acao the acao to set
	 */
	public void setAcao(Long acao) {
		this.acao = acao;
	}

	//	/**
	//	 * @return the acao
	//	 */
	//	public AcaoPedidoContato getAcao() {
	//		if (acao != null) {
	//			return AcaoPedidoContato.valueById(acao);
	//		}
	//		return null;
	//	}
	//
	//	/**
	//	 * @param acaoPedidoContato the acao to set
	//	 */
	//	public void setAcao(AcaoPedidoContato acaoPedidoContato) {
	//		this.acao = null;
	//		if (acaoPedidoContato != null) {
	//			this.acao = acaoPedidoContato.getId();
	//		}
	//	}
	
	/**
	 * @return the motivoStatusDoador
	 */
	public MotivoStatusDoador getMotivoStatusDoador() {
		return motivoStatusDoador;
	}

	/**
	 * @param motivoStatusDoador the motivoStatusDoador to set
	 */
	public void setMotivoStatusDoador(MotivoStatusDoador motivoStatusDoador) {
		this.motivoStatusDoador = motivoStatusDoador;
	}

	/**
	 * @return the tempoInativacaoTemporaria
	 */
	public Long getTempoInativacaoTemporaria() {
		return tempoInativacaoTemporaria;
	}

	/**
	 * @param tempoInativacaoTemporaria the tempoInativacaoTemporaria to set
	 */
	public void setTempoInativacaoTemporaria(Long tempoInativacaoTemporaria) {
		this.tempoInativacaoTemporaria = tempoInativacaoTemporaria;
	}

	public Boolean getFinalizacaoAutomatica() {
		return finalizacaoAutomatica;
	}

	public void setFinalizacaoAutomatica(Boolean finalizacaoAutomatica) {
		this.finalizacaoAutomatica = finalizacaoAutomatica;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	/**
	 * @return the passivo
	 */
	public Boolean getPassivo() {
		return passivo;
	}

	/**
	 * @param passivo the passivo to set
	 */
	public void setPassivo(Boolean passivo) {
		this.passivo = passivo;
	}

	/**
	 * @return the tipoContato
	 */
	public Long getTipoContato() {
		return tipoContato;
	}

	/**
	 * @param tipoContato the tipoContato to set
	 */
	public void setTipoContato(Long tipoContato) {
		this.tipoContato = tipoContato;
	}
	
	@Override
	public PedidoContato clone() {
		PedidoContato clone = new PedidoContato();
		clone.setContactado(this.contactado);
		clone.setContactadoPorTerceiro(this.contactadoPorTerceiro);
		clone.setUsuario(this.usuario);
		clone.setTipoContato(this.tipoContato);
		return clone;
	}
	
}

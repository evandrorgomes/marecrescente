package br.org.cancer.modred.model;

import java.math.BigDecimal;
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
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.hibernate.envers.RelationTargetAuditMode;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.AvaliacaoPedidoColetaView;
import br.org.cancer.modred.controller.view.AvaliacaoPrescricaoView;
import br.org.cancer.modred.controller.view.AvaliacaoResultadoWorkupView;
import br.org.cancer.modred.controller.view.DoadorView;
import br.org.cancer.modred.controller.view.PedidoColetaView;
import br.org.cancer.modred.controller.view.PedidoExameView;
import br.org.cancer.modred.controller.view.PedidoWorkupView;
import br.org.cancer.modred.controller.view.PrescricaoView;
import br.org.cancer.modred.controller.view.TarefaLogisticaView;
import br.org.cancer.modred.controller.view.TransportadoraView;
import br.org.cancer.modred.controller.view.WorkupView;
import br.org.cancer.modred.model.domain.TiposDoador;

/**
 * Classe de persistencia de doador internacional para a tabela doador.
 * 
 * @author bruno.sousa
 * 
 */
@Entity
@DiscriminatorValue("1")
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
public class DoadorInternacional extends Doador implements IDoador<String>, IDoadorInternacional {

	private static final long serialVersionUID = 6132876455035818521L;

	@NotNull
	@JsonView({	DoadorView.IdentificacaoCompleta.class, DoadorView.IdentificacaoParcial.class, 
				DoadorView.Internacional.class, PedidoExameView.ListarTarefas.class, AvaliacaoPrescricaoView.Detalhe.class,
				PedidoWorkupView.CadastrarResultado.class, PedidoColetaView.AgendamentoColeta.class, AvaliacaoPrescricaoView.ListarAvaliacao.class,
				TarefaLogisticaView.Listar.class, TransportadoraView.Listar.class, PedidoWorkupView.AgendamentoWorkup.class,
				AvaliacaoResultadoWorkupView.ListarAvaliacao.class, 
				WorkupView.Resultado.class, WorkupView.ResultadoParaAvaliacao.class, AvaliacaoPedidoColetaView.Detalhe.class})
	@Column(name = "DOAD_TX_ID_REGISTRO")
	@Length(min = 1, max = 30)
	private String idRegistro;

	@JsonView({	DoadorView.IdentificacaoCompleta.class, DoadorView.IdentificacaoParcial.class,
				DoadorView.Internacional.class, AvaliacaoPedidoColetaView.Detalhe.class})
	@Column(name = "DOAD_NR_IDADE")
	private Integer idade;

	@JsonView({DoadorView.Internacional.class, PedidoExameView.ListarTarefas.class, AvaliacaoPedidoColetaView.Detalhe.class})
	@ManyToOne
	@JoinColumn(name = "REGI_ID_PAGAMENTO")
	private Registro registroPagamento;
	
	@NotEmpty
	@Valid
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name="DOAD_ID", referencedColumnName="DOAD_ID")
	@NotAudited
	@Fetch(FetchMode.SUBSELECT)
	private List<ExameDoadorInternacional> exames;
	
	/**
	 * Indica se o cadastro foi feito no EMDIS ou não.
	 */
	@JsonView({DoadorView.Internacional.class, PedidoExameView.ListarTarefas.class, AvaliacaoPedidoColetaView.Detalhe.class})
	@Column(name = "DOAD_IN_EMDIS")
	private Boolean cadastradoEmdis;
	
	@JsonView({ DoadorView.AtualizacaoFase2.class, DoadorView.ContatoPassivo.class, 
				DoadorView.Internacional.class, AvaliacaoPedidoColetaView.Detalhe.class, AvaliacaoPrescricaoView.Detalhe.class,
				PrescricaoView.DetalheDoador.class})
	@Column(name = "DOAD_VL_PESO", precision = 4, scale = 1)
	//@DecimalMin(value = "0", inclusive = false, message = "paciente.peso.maior.zero")
	@Digits(integer = 3, fraction = 1)
	private BigDecimal peso;
	
	
	public DoadorInternacional() {
		super();
		this.setTipoDoador(TiposDoador.INTERNACIONAL);
	}
	
	/**
	 * Construtor para facilitar a instanciação do objeto, a partir do seu identificador.
	 * 
	 * @param idDoador identificador da entidade.
	 */
	public DoadorInternacional(Long id) {
		super(id);
		this.setTipoDoador(TiposDoador.INTERNACIONAL);
	}
	
	/**
	 * @return the idRegistro
	 */
	public String getIdRegistro() {
		return idRegistro;
	}
	
	/**
	 * @param idRegistro the idRegistro to set
	 */
	public void setIdRegistro(String idRegistro) {
		this.idRegistro = idRegistro;
	}
	
	/**
	 * @return the idade
	 */
	public Integer getIdade() {
		return idade;
	}
	
	/**
	 * @param idade the idade to set
	 */
	public void setIdade(Integer idade) {
		this.idade = idade;
	}
	
	/**
	 * @return the registroPagamento
	 */
	public Registro getRegistroPagamento() {
		return registroPagamento;
	}
	
	/**
	 * @param registroPagamento the registroPagamento to set
	 */
	public void setRegistroPagamento(Registro registroPagamento) {
		this.registroPagamento = registroPagamento;
	}
	
	/**
	 * @return the exames
	 */
	public List<ExameDoadorInternacional> getExames() {
		return exames;
	}
	
	/**
	 * @param exames the exames to set
	 */
	public void setExames(List<ExameDoadorInternacional> exames) {
		this.exames = exames;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ( ( idRegistro == null ) ? 0 : idRegistro.hashCode() );
		result = prime * result + ( ( idade == null ) ? 0 : idade.hashCode() );
		result = prime * result + ( ( registroPagamento == null ) ? 0 : registroPagamento.hashCode() );
		result = prime * result + ( ( peso == null ) ? 0 : peso.hashCode() );
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
		if (!( obj instanceof DoadorInternacional )) {
			return false;
		}
		DoadorInternacional other = (DoadorInternacional) obj;
		if (idRegistro == null) {
			if (other.idRegistro != null) {
				return false;
			}
		}
		else
			if (!idRegistro.equals(other.idRegistro)) {
				return false;
			}
		if (idade == null) {
			if (other.idade != null) {
				return false;
			}
		}
		else
			if (!idade.equals(other.idade)) {
				return false;
			}
		if (registroPagamento == null) {
			if (other.registroPagamento != null) {
				return false;
			}
		}
		else
			if (!registroPagamento.equals(other.registroPagamento)) {
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
		return true;
	}

	/**
	 * Flag que diz se o cadastro foi feito no EMDIS.
	 * 
	 * @return TRUE caso seja do EMDIS, FALSE caso não.
	 */
	public Boolean getCadastradoEmdis() {
		return cadastradoEmdis;
	}

	public void setCadastradoEmdis(Boolean emdis) {
		this.cadastradoEmdis = emdis;
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

	@Override
	public String getIdentificacao() {
		return getIdRegistro();
	}
	
}
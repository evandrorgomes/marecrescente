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
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.hibernate.envers.RelationTargetAuditMode;
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
 * Classe de persistencia de Cordao Internacional para a tabela doador.
 * 
 * @author bruno.sousa
 * 
 */
@Entity
@DiscriminatorValue(TiposDoador.CORDAO_INTERNACIONAL_ID)
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
public class CordaoInternacional extends Doador implements IDoador<String>, ICordaoInternacional {

	private static final long serialVersionUID = 6132876455035818521L;

	@NotNull
	@JsonView({ DoadorView.Internacional.class, PedidoExameView.ListarTarefas.class, PedidoColetaView.AgendamentoColeta.class,
				DoadorView.IdentificacaoCompleta.class, DoadorView.IdentificacaoParcial.class, TarefaLogisticaView.Listar.class,
				WorkupView.Resultado.class, WorkupView.ResultadoParaAvaliacao.class, AvaliacaoPedidoColetaView.Detalhe.class,
				AvaliacaoResultadoWorkupView.ListarAvaliacao.class, TransportadoraView.Listar.class, PedidoWorkupView.AgendamentoWorkup.class,
				AvaliacaoPrescricaoView.Detalhe.class, AvaliacaoPrescricaoView.ListarAvaliacao.class})
	@Column(name = "DOAD_TX_ID_REGISTRO")
	private String idRegistro;

	@JsonView({ DoadorView.Internacional.class, PedidoExameView.ListarTarefas.class })
	@ManyToOne
	@JoinColumn(name = "REGI_ID_PAGAMENTO")
	private Registro registroPagamento;

	@NotEmpty
	@Valid
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "DOAD_ID", referencedColumnName = "DOAD_ID")
	@NotAudited
	@Fetch(FetchMode.SUBSELECT)
	private List<ExameCordaoInternacional> exames;

	/**
	 * Indica se o cadastro foi feito no EMDIS ou não.
	 */
	@JsonView({ DoadorView.Internacional.class, PedidoExameView.ListarTarefas.class })
	@Column(name = "DOAD_IN_EMDIS")
	private Boolean cadastradoEmdis;

	@JsonView({DoadorView.Internacional.class, PrescricaoView.DetalheDoador.class})
	@Column(name = "DOAD_VL_QUANT_TOTAL_TCN", precision = 4, scale = 2)
	@DecimalMin(value = "0", inclusive = false, message = "doador.total.tcn.maior.zero")
	@Digits(integer = 4, fraction = 2)
	private BigDecimal quantidadeTotalTCN;

	@JsonView({DoadorView.Internacional.class, PrescricaoView.DetalheDoador.class})
	@Column(name = "DOAD_VL_QUANT_TOTAL_CD34", precision = 4, scale = 2)
	@DecimalMin(value = "0", inclusive = false, message = "doador.total.cd34.maior.zero")
	@Digits(integer = 4, fraction = 2)
	private BigDecimal quantidadeTotalCD34;

	@JsonView({DoadorView.Internacional.class, PrescricaoView.DetalheDoador.class})
	@Column(name = "DOAD_VL_VOLUME", precision = 4, scale = 2)
	@DecimalMin(value = "0", inclusive = false, message = "doador.total.volume.maior.zero")
	@Digits(integer = 4, fraction = 2)
	private BigDecimal volume;

	
	public CordaoInternacional() {
		super();
		this.setTipoDoador(TiposDoador.CORDAO_INTERNACIONAL);
	}

	/**
	 * Construtor para facilitar a instanciação do objeto, a partir do seu identificador.
	 * 
	 * @param idDoador identificador da entidade.
	 */
	public CordaoInternacional(Long id) {
		super(id);
		this.setTipoDoador(TiposDoador.CORDAO_INTERNACIONAL);
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
	public List<ExameCordaoInternacional> getExames() {
		return exames;
	}

	/**
	 * @param exames the exames to set
	 */
	public void setExames(List<ExameCordaoInternacional> exames) {
		this.exames = exames;
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
	 * @return the quantidadeTotalTCN
	 */
	public BigDecimal getQuantidadeTotalTCN() {
		return quantidadeTotalTCN;
	}

	/**
	 * @param quantidadeTotalTCN the quantidadeTotalTCN to set
	 */
	public void setQuantidadeTotalTCN(BigDecimal quantidadeTotalTCN) {
		this.quantidadeTotalTCN = quantidadeTotalTCN;
	}

	/**
	 * @return the quantidadeTotalCD34
	 */
	public BigDecimal getQuantidadeTotalCD34() {
		return quantidadeTotalCD34;
	}

	/**
	 * @param quantidadeTotalCD34 the quantidadeTotalCD34 to set
	 */
	public void setQuantidadeTotalCD34(BigDecimal quantidadeTotalCD34) {
		this.quantidadeTotalCD34 = quantidadeTotalCD34;
	}

	/**
	 * @return the volume
	 */
	public BigDecimal getVolume() {
		return volume;
	}

	/**
	 * @param volume the volume to set
	 */
	public void setVolume(BigDecimal volume) {
		this.volume = volume;
	}

	@Override
	public String getIdentificacao() {
		return getIdRegistro();
	}

}
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

import br.org.cancer.modred.controller.view.DoadorView;
import br.org.cancer.modred.controller.view.PedidoColetaView;
import br.org.cancer.modred.controller.view.PrescricaoView;
import br.org.cancer.modred.controller.view.SolicitacaoView;
import br.org.cancer.modred.controller.view.TarefaLogisticaView;
import br.org.cancer.modred.controller.view.TransportadoraView;
import br.org.cancer.modred.model.domain.TiposDoador;

/**
 * Classe de persistencia de Cordao Internacional para a tabela doador.
 * 
 * @author bruno.sousa
 * 
 */
@Entity
@DiscriminatorValue("2")
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
public class CordaoNacional extends Doador implements IDoador<String>, ICordaoNacional {

	private static final long serialVersionUID = 6132876455035818521L;

	@NotNull
	@Column(name = "DOAD_TX_ID_BANCO_SANGUE_CORDAO")
	@JsonView({	DoadorView.IdentificacaoCompleta.class, DoadorView.IdentificacaoParcial.class, 
				PedidoColetaView.AgendamentoColeta.class,
				TarefaLogisticaView.Listar.class, TransportadoraView.Listar.class, 
				TransportadoraView.AgendarTransporte.class, SolicitacaoView.detalhe.class })
	private String idBancoSangueCordao;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "BASC_ID")
	@JsonView({	DoadorView.IdentificacaoCompleta.class, DoadorView.IdentificacaoParcial.class, 
				PedidoColetaView.AgendamentoColeta.class, 
				TarefaLogisticaView.Listar.class, TransportadoraView.Listar.class,
				TransportadoraView.AgendarTransporte.class, PrescricaoView.DetalheDoador.class, SolicitacaoView.detalhe.class})
	private BancoSangueCordao bancoSangueCordao;

	@NotEmpty
	@Valid
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "DOAD_ID", referencedColumnName = "DOAD_ID")
	@NotAudited
	@Fetch(FetchMode.SUBSELECT)
	private List<ExameCordaoNacional> exames;
	
	@NotNull
	@Column(name = "DOAD_VL_QUANT_TOTAL_TCN", precision = 4, scale = 2)
	@DecimalMin(value = "0", inclusive = false, message = "doador.total.tcn.maior.zero")
	@Digits(integer = 4, fraction = 2)
	@JsonView({PrescricaoView.DetalheDoador.class})
	private BigDecimal quantidadeTotalTCN;

	@NotNull
	@Column(name = "DOAD_VL_QUANT_TOTAL_CD34", precision = 4, scale = 2)
	@DecimalMin(value = "0", inclusive = false, message = "doador.total.cd34.maior.zero")
	@Digits(integer = 4, fraction = 2)
	@JsonView({PrescricaoView.DetalheDoador.class})
	private BigDecimal quantidadeTotalCD34;

	@Column(name = "DOAD_VL_VOLUME", precision = 4, scale = 2)
	@DecimalMin(value = "0", inclusive = false, message = "doador.total.volume.maior.zero")
	@Digits(integer = 4, fraction = 2)
	@JsonView({PrescricaoView.DetalheDoador.class})
	private BigDecimal volume;
	
	@NotNull
	@Column(name = "DOAD_VL_TOT_LINFOCITOS", precision = 4, scale = 2)
	@DecimalMin(value = "0", inclusive = false, message = "doador.total.lifocitos.maior.zero") 
	@Digits(integer = 4, fraction = 2)
	private BigDecimal totalLinfocitos;

	@NotNull
	@Column(name = "DOAD_VL_TOT_MONOCITOS", precision = 4, scale = 2)
	@DecimalMin(value = "0", inclusive = false, message = "doador.total.monocitos.maior.zero")
	@Digits(integer = 4, fraction = 2)
	private BigDecimal totalMonocitos;
	
	@NotNull
	@Column(name = "DOAD_VL_TOT_GRANULOCITOS", precision = 4, scale = 2)
	@DecimalMin(value = "0", inclusive = false, message = "doador.total.granulocitos.maior.zero")
	@Digits(integer = 4, fraction = 2)
	private BigDecimal totalGranulocitos;

	@NotNull
	@Column(name = "DOAD_VL_TOT_PER_HEMATOCRITOS", precision = 4, scale = 2)
	@DecimalMin(value = "0", inclusive = false, message = "doador.total.hematocritos.maior.zero")
	@Digits(integer = 4, fraction = 2)
	private BigDecimal totalPercentualHematocritos;

	
	@NotNull
	@Column(name = "DOAD_VL_TOT_ANTES", precision = 4, scale = 2)
	@DecimalMin(value = "0", inclusive = false, message = "doador.total.antes.maior.zero")
	@Digits(integer = 4, fraction = 2)
	private BigDecimal volumeTotalAntes;

	@NotNull
	@Column(name = "DOAD_VL_TOT_DEPOIS", precision = 4, scale = 2)
	@DecimalMin(value = "0", inclusive = false, message = "doador.total.depois.maior.zero")
	@Digits(integer = 4, fraction = 2)
	private BigDecimal volumeTotalDepois;

	@NotNull
	@Column(name = "DOAD_VL_TOT_REAL", precision = 4, scale = 2)
	@DecimalMin(value = "0", inclusive = false, message = "doador.total.real.maior.zero")
	@Digits(integer = 4, fraction = 2)
	private BigDecimal volumeTotalReal;

	@NotNull
	@Column(name = "DOAD_VL_QUANT_TOTAL_TCN_ANTES", precision = 4, scale = 2)
	@DecimalMin(value = "0", inclusive = false, message = "doador.total.tcnantes.maior.zero")
	@Digits(integer = 4, fraction = 2)
	private BigDecimal volumeTotalTCNAntes;

	
	public CordaoNacional() {
		super();
		this.setTipoDoador(TiposDoador.CORDAO_NACIONAL);
	}

	/**
	 * @return the idBancoSangueCordao
	 */
	public String getIdBancoSangueCordao() {
		return idBancoSangueCordao;
	}

	/**
	 * @param idBancoSangueCordao the idBancoSangueCordao to set
	 */
	public void setIdBancoSangueCordao(String idBancoSangueCordao) {
		this.idBancoSangueCordao = idBancoSangueCordao;
	}

	/**
	 * Construtor para facilitar a instanciação do objeto, a partir do seu identificador.
	 * 
	 * @param idDoador identificador da entidade.
	 */
	public CordaoNacional(Long id) {
		super(id);
	}

	/**
	 * @return the bancoSangueCordao
	 */
	public BancoSangueCordao getBancoSangueCordao() {
		return bancoSangueCordao;
	}

	/**
	 * @param bancoSangueCordao the bancoSangueCordao to set
	 */
	public void setBancoSangueCordao(BancoSangueCordao bancoSangueCordao) {
		this.bancoSangueCordao = bancoSangueCordao;
	}

	/**
	 * @return the exames
	 */
	public List<ExameCordaoNacional> getExames() {
		return exames;
	}

	/**
	 * @param exames the exames to set
	 */
	public void setExames(List<ExameCordaoNacional> exames) {
		this.exames = exames;
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
	 * @return the totalLinfocitos
	 */
	public BigDecimal getTotalLinfocitos() {
		return totalLinfocitos;
	}

	/**
	 * @param totalLinfocitos the totalLinfocitos to set
	 */
	public void setTotalLinfocitos(BigDecimal totalLinfocitos) {
		this.totalLinfocitos = totalLinfocitos;
	}

	/**
	 * @return the totalMonocitos
	 */
	public BigDecimal getTotalMonocitos() {
		return totalMonocitos;
	}

	/**
	 * @param totalMonocitos the totalMonocitos to set
	 */
	public void setTotalMonocitos(BigDecimal totalMonocitos) {
		this.totalMonocitos = totalMonocitos;
	}

	/**
	 * @return the totalGranulocitos
	 */
	public BigDecimal getTotalGranulocitos() {
		return totalGranulocitos;
	}

	/**
	 * @param totalGranulocitos the totalGranulocitos to set
	 */
	public void setTotalGranulocitos(BigDecimal totalGranulocitos) {
		this.totalGranulocitos = totalGranulocitos;
	}

	/**
	 * @return the totalPercentualHematocritos
	 */
	public BigDecimal getTotalPercentualHematocritos() {
		return totalPercentualHematocritos;
	}

	/**
	 * @param totalPercentualHematocritos the totalPercentualHematocritos to set
	 */
	public void setTotalPercentualHematocritos(BigDecimal totalPercentualHematocritos) {
		this.totalPercentualHematocritos = totalPercentualHematocritos;
	}

	/**
	 * @return the volumeTotalAntes
	 */
	public BigDecimal getVolumeTotalAntes() {
		return volumeTotalAntes;
	}

	/**
	 * @param volumeTotalAntes the volumeTotalAntes to set
	 */
	public void setVolumeTotalAntes(BigDecimal volumeTotalAntes) {
		this.volumeTotalAntes = volumeTotalAntes;
	}

	/**
	 * @return the volumeTotalDepois
	 */
	public BigDecimal getVolumeTotalDepois() {
		return volumeTotalDepois;
	}

	/**
	 * @param volumeTotalDepois the volumeTotalDepois to set
	 */
	public void setVolumeTotalDepois(BigDecimal volumeTotalDepois) {
		this.volumeTotalDepois = volumeTotalDepois;
	}

	/**
	 * @return the volumeTotalReal
	 */
	public BigDecimal getVolumeTotalReal() {
		return volumeTotalReal;
	}

	/**
	 * @param volumeTotalReal the volumeTotalReal to set
	 */
	public void setVolumeTotalReal(BigDecimal volumeTotalReal) {
		this.volumeTotalReal = volumeTotalReal;
	}

	/**
	 * @return the volumeTotalTCNAntes
	 */
	public BigDecimal getVolumeTotalTCNAntes() {
		return volumeTotalTCNAntes;
	}

	/**
	 * @param volumeTotalTCNAntes the volumeTotalTCNAntes to set
	 */
	public void setVolumeTotalTCNAntes(BigDecimal volumeTotalTCNAntes) {
		this.volumeTotalTCNAntes = volumeTotalTCNAntes;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ( ( bancoSangueCordao == null ) ? 0 : bancoSangueCordao.hashCode() );
		result = prime * result + ( ( idBancoSangueCordao == null ) ? 0 : idBancoSangueCordao.hashCode() );
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
		if (!super.equals(obj)) {
			return false;
		}
		if (!( obj instanceof CordaoNacional )) {
			return false;
		}
		CordaoNacional other = (CordaoNacional) obj;
		if (bancoSangueCordao == null) {
			if (other.bancoSangueCordao != null) {
				return false;
			}
		}
		else
			if (!bancoSangueCordao.equals(other.bancoSangueCordao)) {
				return false;
			}
		if (exames == null) {
			if (other.exames != null) {
				return false;
			}
		}
		else
			if (!exames.equals(other.exames)) {
				return false;
			}
		if (idBancoSangueCordao == null) {
			if (other.idBancoSangueCordao != null) {
				return false;
			}
		}
		else
			if (!idBancoSangueCordao.equals(other.idBancoSangueCordao)) {
				return false;
			}
		return true;
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

	/**
	 * Retorna o id do banco de sangue formatado para exibição.
	 * 
	 * @return id do registro formatado
	 */
	public String obterIdBancoSangueCordaoFormatado() {
		if (bancoSangueCordao != null) {
			return bancoSangueCordao.getSigla() + idBancoSangueCordao;
		}
		
		return null;		
	}

	@Override
	public String getIdentificacao() {
		return this.getIdBancoSangueCordao();
	}
	
	
}
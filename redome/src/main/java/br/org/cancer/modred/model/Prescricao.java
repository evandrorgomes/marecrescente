package br.org.cancer.modred.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.AvaliacaoPrescricaoView;
import br.org.cancer.modred.controller.view.PedidoColetaView;
import br.org.cancer.modred.controller.view.PedidoWorkupView;
import br.org.cancer.modred.controller.view.PrescricaoView;
import br.org.cancer.modred.controller.view.SolicitacaoView;
import br.org.cancer.modred.controller.view.TarefaView;

/**
 * Classe de persistencia para Prescrição.
 * 
 * @author Filipe Paes
 */
@Entity
@Table(name = "PRESCRICAO")
@SequenceGenerator(name = "SQ_PRES_ID", sequenceName = "SQ_PRES_ID", allocationSize = 1)
public class Prescricao implements Serializable {

	private static final long serialVersionUID = 7927766527992845073L;

	/**
	 * Id para prescrição.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_PRES_ID")
	@Column(name = "PRES_ID")
	@JsonView({PrescricaoView.AutorizacaoPacienteListar.class, SolicitacaoView.listar.class})
	private Long id;

	/**
	 * Primeira sugestão para data coleta.
	 */
	@Column(name = "PRES_DT_COLETA_1")
	@JsonView(value = { TarefaView.Listar.class, PedidoWorkupView.DetalheWorkup.class,
			AvaliacaoPrescricaoView.ListarAvaliacao.class, AvaliacaoPrescricaoView.Detalhe.class,
			PedidoColetaView.AgendamentoColeta.class, PedidoColetaView.DetalheColeta.class, SolicitacaoView.detalhe.class})
	@NotNull
	private LocalDate dataColeta1;

	/**
	 * Segunda sugestão para data de coleta.
	 */
	@Column(name = "PRES_DT_COLETA_2")
	@JsonView(value = { TarefaView.Listar.class, PedidoWorkupView.DetalheWorkup.class,
			AvaliacaoPrescricaoView.ListarAvaliacao.class, AvaliacaoPrescricaoView.Detalhe.class,
			PedidoColetaView.AgendamentoColeta.class, PedidoColetaView.DetalheColeta.class, SolicitacaoView.detalhe.class})
	@NotNull
	private LocalDate dataColeta2;

	/**
	 * Primeira sugestão para resultado de coleta.
	 */
	@Column(name = "PRES_DT_RESULTADO_WORKUP_1")
	@JsonView(value = { TarefaView.Listar.class, PedidoWorkupView.DetalheWorkup.class,
			AvaliacaoPrescricaoView.ListarAvaliacao.class, AvaliacaoPrescricaoView.Detalhe.class, 
			PedidoWorkupView.AgendamentoWorkup.class, SolicitacaoView.detalhe.class})
	private LocalDate dataResultadoWorkup1;

	/**
	 * Segunda sugestão para resultado de workup.
	 */
	@Column(name = "PRES_DT_RESULTADO_WORKUP_2")
	@JsonView(value = { TarefaView.Listar.class, PedidoWorkupView.DetalheWorkup.class,
			AvaliacaoPrescricaoView.ListarAvaliacao.class, AvaliacaoPrescricaoView.Detalhe.class, 
			PedidoWorkupView.AgendamentoWorkup.class, SolicitacaoView.detalhe.class})
	private LocalDate dataResultadoWorkup2;

	/**
	 * Referência para solicitação.
	 */
	@OneToOne
	@JoinColumn(name = "SOLI_ID")
	@JsonView(value = { AvaliacaoPrescricaoView.ListarAvaliacao.class, AvaliacaoPrescricaoView.Detalhe.class,
			PrescricaoView.AutorizacaoPacienteListar.class})
	private Solicitacao solicitacao;

	@ManyToOne
	@JoinColumn(name = "FOCE_ID_OPCAO_1")
	@JsonView(value = { PedidoWorkupView.DetalheWorkup.class, AvaliacaoPrescricaoView.Detalhe.class })
	@NotNull
	private FonteCelula fonteCelulaOpcao1;

	@Column(name = "PRES_VL_QUANT_TOTAL_OPCAO_1", precision = 6, scale = 2)
	@NotNull
	@JsonView(value = { AvaliacaoPrescricaoView.Detalhe.class })
	private BigDecimal quantidadeTotalOpcao1;

	@Column(name = "PRES_VL_QUANT_KG_OPCAO_1", precision = 6, scale = 2)
	@JsonView(value = { AvaliacaoPrescricaoView.Detalhe.class })
	private BigDecimal quantidadePorKgOpcao1;

	@ManyToOne
	@JoinColumn(name = "FOCE_ID_OPCAO_2")
	@JsonView(value = { PedidoWorkupView.DetalheWorkup.class, AvaliacaoPrescricaoView.Detalhe.class })
	private FonteCelula fonteCelulaOpcao2;

	@Column(name = "PRES_VL_QUANT_TOTAL_OPCAO_2", precision = 6, scale = 2)
	@JsonView(value = { AvaliacaoPrescricaoView.Detalhe.class })
	private BigDecimal quantidadeTotalOpcao2;

	@Column(name = "PRES_VL_QUANT_KG_OPCAO_2", precision = 6, scale = 2)
	@JsonView(value = { AvaliacaoPrescricaoView.Detalhe.class })
	private BigDecimal quantidadePorKgOpcao2;

	@OneToMany(mappedBy = "prescricao", cascade = CascadeType.ALL)
	@JsonView(value = { AvaliacaoPrescricaoView.Detalhe.class })
	private List<ArquivoPrescricao> arquivosPrescricao;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "EVOL_ID")
	@NotNull
	private Evolucao evolucao;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MEDI_ID")
	@NotNull
	private Medico medicoResponsavel;
	
	@OneToMany(mappedBy = "prescricao", cascade = CascadeType.ALL)
	@JsonView(value = {AvaliacaoPrescricaoView.Detalhe.class})
	private List<TipoAmostraPrescricao> amostras;

	public Prescricao(Solicitacao solicitacao) {
		this.id = solicitacao.getId();
		this.solicitacao = solicitacao;
	}

	public Prescricao() {
		super();
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the dataColeta1
	 */
	public LocalDate getDataColeta1() {
		return dataColeta1;
	}

	/**
	 * @param dataColeta1
	 *            the dataColeta1 to set
	 */
	public void setDataColeta1(LocalDate dataColeta1) {
		this.dataColeta1 = dataColeta1;
	}

	/**
	 * @return the dataColeta2
	 */
	public LocalDate getDataColeta2() {
		return dataColeta2;
	}

	/**
	 * @param dataColeta2
	 *            the dataColeta2 to set
	 */
	public void setDataColeta2(LocalDate dataColeta2) {
		this.dataColeta2 = dataColeta2;
	}

	/**
	 * @return the dataResultadoWorkup1
	 */
	public LocalDate getDataResultadoWorkup1() {
		return dataResultadoWorkup1;
	}

	/**
	 * @param dataResultadoWorkup1
	 *            the dataResultadoWorkup1 to set
	 */
	public void setDataResultadoWorkup1(LocalDate dataResultadoWorkup1) {
		this.dataResultadoWorkup1 = dataResultadoWorkup1;
	}

	/**
	 * @return the dataResultadoWorkup2
	 */
	public LocalDate getDataResultadoWorkup2() {
		return dataResultadoWorkup2;
	}

	/**
	 * @param dataResultadoWorkup2
	 *            the dataResultadoWorkup2 to set
	 */
	public void setDataResultadoWorkup2(LocalDate dataResultadoWorkup2) {
		this.dataResultadoWorkup2 = dataResultadoWorkup2;
	}

	/**
	 * @return the solicitacao
	 */
	public Solicitacao getSolicitacao() {
		return solicitacao;
	}

	/**
	 * @param solicitacao
	 *            the solicitacao to set
	 */
	public void setSolicitacao(Solicitacao solicitacao) {
		this.solicitacao = solicitacao;
	}

	/**
	 * @return the fonteCelulaOpcao1
	 */
	public FonteCelula getFonteCelulaOpcao1() {
		return fonteCelulaOpcao1;
	}

	/**
	 * @param fonteCelulaOpcao1
	 *            the fonteCelulaOpcao1 to set
	 */
	public void setFonteCelulaOpcao1(FonteCelula fonteCelulaOpcao1) {
		this.fonteCelulaOpcao1 = fonteCelulaOpcao1;
	}

	/**
	 * @return the quantidadeTotalOpcao1
	 */
	public BigDecimal getQuantidadeTotalOpcao1() {
		return quantidadeTotalOpcao1;
	}

	/**
	 * @param quantidadeTotalOpcao1
	 *            the quantidadeTotalOpcao1 to set
	 */
	public void setQuantidadeTotalOpcao1(BigDecimal quantidadeTotalOpcao1) {
		this.quantidadeTotalOpcao1 = quantidadeTotalOpcao1;
	}

	/**
	 * @return the quantidadePorKgOpcao1
	 */
	public BigDecimal getQuantidadePorKgOpcao1() {
		return quantidadePorKgOpcao1;
	}

	/**
	 * @param quantidadePorKgOpcao1
	 *            the quantidadePorKgOpcao1 to set
	 */
	public void setQuantidadePorKgOpcao1(BigDecimal quantidadePorKgOpcao1) {
		this.quantidadePorKgOpcao1 = quantidadePorKgOpcao1;
	}

	/**
	 * @return the fonteCelulaOpcao2
	 */
	public FonteCelula getFonteCelulaOpcao2() {
		return fonteCelulaOpcao2;
	}

	/**
	 * @param fonteCelulaOpcao2
	 *            the fonteCelulaOpcao2 to set
	 */
	public void setFonteCelulaOpcao2(FonteCelula fonteCelulaOpcao2) {
		this.fonteCelulaOpcao2 = fonteCelulaOpcao2;
	}

	/**
	 * @return the quantidadeTotalOpcao2
	 */
	public BigDecimal getQuantidadeTotalOpcao2() {
		return quantidadeTotalOpcao2;
	}

	/**
	 * @param quantidadeTotalOpcao2
	 *            the quantidadeTotalOpcao2 to set
	 */
	public void setQuantidadeTotalOpcao2(BigDecimal quantidadeTotalOpcao2) {
		this.quantidadeTotalOpcao2 = quantidadeTotalOpcao2;
	}

	/**
	 * @return the quantidadePorKgOpcao2
	 */
	public BigDecimal getQuantidadePorKgOpcao2() {
		return quantidadePorKgOpcao2;
	}

	/**
	 * @param quantidadePorKgOpcao2
	 *            the quantidadePorKgOpcao2 to set
	 */
	public void setQuantidadePorKgOpcao2(BigDecimal quantidadePorKgOpcao2) {
		this.quantidadePorKgOpcao2 = quantidadePorKgOpcao2;
	}

	/**
	 * @return the arquivosPrescricao
	 */
	public List<ArquivoPrescricao> getArquivosPrescricao() {
		return arquivosPrescricao;
	}

	/**
	 * @param arquivosPrescricao
	 *            the arquivosPrescricao to set
	 */
	public void setArquivosPrescricao(List<ArquivoPrescricao> arquivosPrescricao) {
		this.arquivosPrescricao = arquivosPrescricao;
	}

	/**
	 * @return the evolucao
	 */
	public Evolucao getEvolucao() {
		return evolucao;
	}

	/**
	 * @param evolucao
	 *            the evolucao to set
	 */
	public void setEvolucao(Evolucao evolucao) {
		this.evolucao = evolucao;
	}

	/**
	 * @return the avaliacaoPrescricao
	 */
	public AvaliacaoPrescricao getAvaliacaoPrescricao() {
		return null;
	}

	/**
	 * @param avaliacaoPrescricao
	 *            the avaliacaoPrescricao to set
	 */
	public void setAvaliacaoPrescricao(AvaliacaoPrescricao avaliacaoPrescricao) {
		
	}
	
	/**
	 * @return the medicoResponsavel
	 */
	public Medico getMedicoResponsavel() {
		return medicoResponsavel;
	}
	
	/**
	 * @param medicoResponsavel the medicoResponsavel to set
	 */
	public void setMedicoResponsavel(Medico medicoResponsavel) {
		this.medicoResponsavel = medicoResponsavel;
	}

	public List<TipoAmostraPrescricao> getAmostras() {
		return amostras;
	}

	public void setAmostras(List<TipoAmostraPrescricao> amostras) {
		this.amostras = amostras;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dataColeta1 == null) ? 0 : dataColeta1.hashCode());
		result = prime * result + ((dataColeta2 == null) ? 0 : dataColeta2.hashCode());
		result = prime * result + ((solicitacao == null) ? 0 : solicitacao.hashCode());
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
		Prescricao other = (Prescricao) obj;
		if (dataColeta1 == null) {
			if (other.dataColeta1 != null) {
				return false;
			}
		}
		else if (!dataColeta1.equals(other.dataColeta1)) {
			return false;
		}
		if (dataColeta2 == null) {
			if (other.dataColeta2 != null) {
				return false;
			}
		}
		else if (!dataColeta2.equals(other.dataColeta2)) {
			return false;
		}
		if (solicitacao == null) {
			if (other.solicitacao != null) {
				return false;
			}
		}
		else if (!solicitacao.equals(other.solicitacao)) {
			return false;
		}
		return true;
	}
	
	/**
	 * Verifica se tem algum arquivo de autorização do paciente.
	 * @return true se tem um arquivo de autorização do paciente.
	 */
	public Boolean temArquivoAutorizacao(){
		ArquivoPrescricao arquivo =  this.arquivosPrescricao.stream().filter(c -> c.getAutorizacaoPaciente()).findAny().orElse(null);
		return arquivo != null? true:false;
	}
	
}
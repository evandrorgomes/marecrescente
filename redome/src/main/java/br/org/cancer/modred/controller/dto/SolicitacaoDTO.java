package br.org.cancer.modred.controller.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import br.org.cancer.modred.model.TipoAmostraPrescricao;

/**
 * Dto para criar uma solicitação para o doador.
 * 
 * @author Fillipe Queiroz
 *
 */
public class SolicitacaoDTO {

	private Long rmr;
	private Long dmr;
	private Long idDoador;
	private Long tipo;
	private LocalDate dataColeta1;
	private LocalDate dataColeta2;
	private LocalDate dataLimiteWorkup1;
	private LocalDate dataLimiteWorkup2;
	private Long fonteCelulaOpcao1;
	private BigDecimal quantidadeTotalOpcao1;
	private BigDecimal quantidadePorKgOpcao1;	
	private Long fonteCelulaOpcao2;
	private BigDecimal quantidadeTotalOpcao2;
	private BigDecimal quantidadePorKgOpcao2;
	private LocalDateTime dataRecebimento;
	private Long idMotivoCancelamentoWorkup;
	private Long idSolicitacao;
	private Long idMatch;
	private Long idBusca;
	private List<TipoAmostraPrescricao> tiposAmostraPrescricao;
	
	/**
	 * Identificador do paciente .
	 * 
	 * @return
	 */
	public Long getRmr() {
		return rmr;
	}

	/**
	 * Identificador do paciente.
	 * 
	 * @param rmr
	 */
	public void setRmr(Long rmr) {
		this.rmr = rmr;
	}

	/**
	 * Identificador do doador.
	 * 
	 * @return
	 */
	public Long getIdDoador() {
		return idDoador;
	}

	/**
	 * Identificador do doador.
	 * 
	 * @param idDoador
	 */
	public void setIdDoador(Long idDoador) {
		this.idDoador = idDoador;
	}

	/**
	 * @return the tipo
	 */
	public Long getTipo() {
		return tipo;
	}

	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(Long tipo) {
		this.tipo = tipo;
	}

	
	/**
	 * @return the dataColeta1
	 */
	public LocalDate getDataColeta1() {
		return dataColeta1;
	}

	
	/**
	 * @param dataColeta1 the dataColeta1 to set
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
	 * @param dataColeta2 the dataColeta2 to set
	 */
	public void setDataColeta2(LocalDate dataColeta2) {
		this.dataColeta2 = dataColeta2;
	}

	
	/**
	 * @return the dataResultadoWorkup1
	 */
	public LocalDate getDataLimiteWorkup1() {
		return dataLimiteWorkup1;
	}

	
	/**
	 * @param dataResultadoWorkup1 the dataResultadoWorkup1 to set
	 */
	public void setDataLimiteWorkup1(LocalDate dataResultadoWorkup1) {
		this.dataLimiteWorkup1 = dataResultadoWorkup1;
	}

	
	/**
	 * @return the dataResultadoWorkup2
	 */
	public LocalDate getDataLimiteWorkup2() {
		return dataLimiteWorkup2;
	}

	
	/**
	 * @param dataResultadoWorkup2 the dataResultadoWorkup2 to set
	 */
	public void setDataLimiteWorkup2(LocalDate dataResultadoWorkup2) {
		this.dataLimiteWorkup2 = dataResultadoWorkup2;
	}

	
	/**
	 * @return the fonteCelulaOpcao1
	 */
	public Long getFonteCelulaOpcao1() {
		return fonteCelulaOpcao1;
	}

	
	/**
	 * @param fonteCelulaOpcao1 the fonteCelulaOpcao1 to set
	 */
	public void setFonteCelulaOpcao1(Long fonteCelulaOpcao1) {
		this.fonteCelulaOpcao1 = fonteCelulaOpcao1;
	}

	
	/**
	 * @return the quantidadeTotalOpcao1
	 */
	public BigDecimal getQuantidadeTotalOpcao1() {
		return quantidadeTotalOpcao1;
	}

	
	/**
	 * @param quantidadeTotalOpcao1 the quantidadeTotalOpcao1 to set
	 */
	public void setQuantidadeTotalOpcao1(BigDecimal quantidadeTotalOpcao1) {
		this.quantidadeTotalOpcao1 = quantidadeTotalOpcao1;
	}

	/**
	 * @return the dataRecebimento
	 */
	public LocalDateTime getDataRecebimento() {
		return dataRecebimento;
	}

	/**
	 * @param dataRecebimento the dataRecebimento to set
	 */
	public void setDataRecebimento(LocalDateTime dataRecebimento) {
		this.dataRecebimento = dataRecebimento;
	}

	/**
	 * @return the idMotivoCancelamentoWorkup
	 */
	public Long getIdMotivoCancelamentoWorkup() {
		return idMotivoCancelamentoWorkup;
	}

	/**
	 * @param idMotivoCancelamentoWorkup the idMotivoCancelamentoWorkup to set
	 */
	public void setIdMotivoCancelamentoWorkup(Long idMotivoCancelamentoWorkup) {
		this.idMotivoCancelamentoWorkup = idMotivoCancelamentoWorkup;
	}
	
	/**
	 * @return the quantidadePorKgOpcao1
	 */
	public BigDecimal getQuantidadePorKgOpcao1() {
		return quantidadePorKgOpcao1;
	}

	
	/**
	 * @param quantidadePorKgOpcao1 the quantidadePorKgOpcao1 to set
	 */
	public void setQuantidadePorKgOpcao1(BigDecimal quantidadePorKgOpcao1) {
		this.quantidadePorKgOpcao1 = quantidadePorKgOpcao1;
	}

	
	/**
	 * @return the fonteCelulaOpcao2
	 */
	public Long getFonteCelulaOpcao2() {
		return fonteCelulaOpcao2;
	}

	
	/**
	 * @param fonteCelulaOpcao2 the fonteCelulaOpcao2 to set
	 */
	public void setFonteCelulaOpcao2(Long fonteCelulaOpcao2) {
		this.fonteCelulaOpcao2 = fonteCelulaOpcao2;
	}

	
	/**
	 * @return the quantidadeTotalOpcao2
	 */
	public BigDecimal getQuantidadeTotalOpcao2() {
		return quantidadeTotalOpcao2;
	}

	
	/**
	 * @param quantidadeTotalOpcao2 the quantidadeTotalOpcao2 to set
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
	 * @param quantidadePorKgOpcao2 the quantidadePorKgOpcao2 to set
	 */
	public void setQuantidadePorKgOpcao2(BigDecimal quantidadePorKgOpcao2) {
		this.quantidadePorKgOpcao2 = quantidadePorKgOpcao2;
	}

	
	/**
	 * @return the dmr
	 */
	public Long getDmr() {
		return dmr;
	}

	
	/**
	 * @param dmr the dmr to set
	 */
	public void setDmr(Long dmr) {
		this.dmr = dmr;
	}

	/**
	 * @return the idSolicitacao
	 */
	public Long getIdSolicitacao() {
		return idSolicitacao;
	}

	/**
	 * @param idSolicitacao the idSolicitacao to set
	 */
	public void setIdSolicitacao(Long idSolicitacao) {
		this.idSolicitacao = idSolicitacao;
	}

	/**
	 * @return the idMatch
	 */
	public Long getIdMatch() {
		return idMatch;
	}

	/**
	 * @param idMatch the idMatch to set
	 */
	public void setIdMatch(Long idMatch) {
		this.idMatch = idMatch;
	}

	/**
	 * @return the idBusca
	 */
	public Long getIdBusca() {
		return idBusca;
	}

	/**
	 * @param idBusca the idBusca to set
	 */
	public void setIdBusca(Long idBusca) {
		this.idBusca = idBusca;
	}

	/**
	 * @return the tiposAmostraPrescricao
	 */
	public List<TipoAmostraPrescricao> getTiposAmostraPrescricao() {
		return tiposAmostraPrescricao;
	}

	/**
	 * @param tiposAmostraPrescricao the tiposAmostraPrescricao to set
	 */
	public void setTiposAmostraPrescricao(List<TipoAmostraPrescricao> tiposAmostraPrescricao) {
		this.tiposAmostraPrescricao = tiposAmostraPrescricao;
	}
	
}

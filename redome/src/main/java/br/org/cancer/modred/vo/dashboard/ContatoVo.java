package br.org.cancer.modred.vo.dashboard;

import java.io.Serializable;
import java.util.List;

/**
 * Classe vo para envio de dados estatisticos de enriquecimento e contato para o front.
 * 
 * @author brunosousa
 *
 */
public class ContatoVo implements Serializable {

	private static final long serialVersionUID = -6175339473811357942L;
	
	private Integer totalEnriquecimentoFase2 = 0;
	private Integer totalFechadoEnriquecimentoFase2 = 0;
	private Integer totalContatoFase2 = 0;
	private Integer totalFechadoContatoFase2 = 0;
	private Integer totalEnriquecimentoFase3 = 0;
	private Integer totalFechadoEnriquecimentoFase3 = 0;
	private Integer totalContatoFase3 = 0;
	private Integer totalFechadoContatoFase3 = 0;
	
	private List<DetalheContatoVo> detalhes;
	
	/**
	 * @return the totalEnriquecimentoFase2
	 */
	public Integer getTotalEnriquecimentoFase2() {
		return totalEnriquecimentoFase2;
	}
	/**
	 * @param totalEnriquecimentoFase2 the totalEnriquecimentoFase2 to set
	 */
	public void setTotalEnriquecimentoFase2(Integer totalEnriquecimentoFase2) {
		this.totalEnriquecimentoFase2 = totalEnriquecimentoFase2;
	}
	/**
	 * @return the totalFechadoEnriquecimentoFase2
	 */
	public Integer getTotalFechadoEnriquecimentoFase2() {
		return totalFechadoEnriquecimentoFase2;
	}
	/**
	 * @param totalFechadoEnriquecimentoFase2 the totalFechadoEnriquecimentoFase2 to set
	 */
	public void setTotalFechadoEnriquecimentoFase2(Integer totalFechadoEnriquecimentoFase2) {
		this.totalFechadoEnriquecimentoFase2 = totalFechadoEnriquecimentoFase2;
	}
	/**
	 * @return the totalContatoFase2
	 */
	public Integer getTotalContatoFase2() {
		return totalContatoFase2;
	}
	/**
	 * @param totalContatoFase2 the totalContatoFase2 to set
	 */
	public void setTotalContatoFase2(Integer totalContatoFase2) {
		this.totalContatoFase2 = totalContatoFase2;
	}
	/**
	 * @return the totalFechadoContatoFase2
	 */
	public Integer getTotalFechadoContatoFase2() {
		return totalFechadoContatoFase2;
	}
	/**
	 * @param totalFechadoContatoFase2 the totalFechadoContatoFase2 to set
	 */
	public void setTotalFechadoContatoFase2(Integer totalFechadoContatoFase2) {
		this.totalFechadoContatoFase2 = totalFechadoContatoFase2;
	}
	/**
	 * @return the totalEnriquecimentoFase3
	 */
	public Integer getTotalEnriquecimentoFase3() {
		return totalEnriquecimentoFase3;
	}
	/**
	 * @param totalEnriquecimentoFase3 the totalEnriquecimentoFase3 to set
	 */
	public void setTotalEnriquecimentoFase3(Integer totalEnriquecimentoFase3) {
		this.totalEnriquecimentoFase3 = totalEnriquecimentoFase3;
	}
	/**
	 * @return the totalFechadoEnriquecimentoFase3
	 */
	public Integer getTotalFechadoEnriquecimentoFase3() {
		return totalFechadoEnriquecimentoFase3;
	}
	/**
	 * @param totalFechadoEnriquecimentoFase3 the totalFechadoEnriquecimentoFase3 to set
	 */
	public void setTotalFechadoEnriquecimentoFase3(Integer totalFechadoEnriquecimentoFase3) {
		this.totalFechadoEnriquecimentoFase3 = totalFechadoEnriquecimentoFase3;
	}
	/**
	 * @return the totalContatoFase3
	 */
	public Integer getTotalContatoFase3() {
		return totalContatoFase3;
	}
	/**
	 * @param totalContatoFase3 the totalContatoFase3 to set
	 */
	public void setTotalContatoFase3(Integer totalContatoFase3) {
		this.totalContatoFase3 = totalContatoFase3;
	}
	/**
	 * @return the totalFechadoContatoFase3
	 */
	public Integer getTotalFechadoContatoFase3() {
		return totalFechadoContatoFase3;
	}
	/**
	 * @param totalFechadoContatoFase3 the totalFechadoContatoFase3 to set
	 */
	public void setTotalFechadoContatoFase3(Integer totalFechadoContatoFase3) {
		this.totalFechadoContatoFase3 = totalFechadoContatoFase3;
	}
	/**
	 * @return the detalhes
	 */
	public List<DetalheContatoVo> getDetalhes() {
		return detalhes;
	}
	/**
	 * @param detalhes the detalhes to set
	 */
	public void setDetalhes(List<DetalheContatoVo> detalhes) {
		this.detalhes = detalhes;
	}
	
}

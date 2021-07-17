package br.org.cancer.modred.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * Classe Vo para comunicação com o serviço de BrasilCord.
 * 
 * @author Filipe Paes
 *
 */
public class CordaoNacionalVo implements Serializable{
	 
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Id do cordão gravado.
	 */
	private Long idCordao;

	/**
	 * Numero da ficha no BSCUP.
	 */
	private String numeroFicha;
	/**
	 * ID do BSCUP.
	 */
	private Long idBanco;
	/**
	 * ABO do cordão. Ex.: B+, AB-, A+...
	 */
	private String abo;

	/**
	 * Quantidae total CD34.
	 */
	private BigDecimal cd34Final;

	/**
	 * Quantidade total TCN.
	 */
	private BigDecimal quantidadeTotalTcn;
	/**
	 * Sexo do RN.
	 */
	private String sexo;
	/**
	 * Total de lifocitos.
	 */
	private BigDecimal totalLinfocitos;
	/**
	 * Total de monocitos.
	 */
	private BigDecimal totalMonocitos;
	/**
	 * Total de granulocitos.
	 */
	private BigDecimal totalGranulocitos;
	/**
	 * Total percentual hematocritos.
	 */
	private BigDecimal totalPercentualHematocritos;
	/**
	 * Total de volume da bolsa antes.
	 */
	private BigDecimal volumeTotalAntes;
	/**
	 * Total de volume da bolsa depois.
	 */
	private BigDecimal volumeTotalDepois;
	/**
	 * Total de volume da bolsa real.
	 */
	private BigDecimal volumeTotalReal;
	/**
	 * Total TCN antes.
	 */
	private BigDecimal volumeTotalTCNAntes;
	/**
	 * Valores do resultado de exame HLA.
	 */
	private List<LocusValorVo> resultadoHla;
	
	
	
	
	

	/**
	 * @return the idCordao
	 */
	public Long getIdCordao() {
		return idCordao;
	}

	/**
	 * @param idCordao the idCordao to set
	 */
	public void setIdCordao(Long idCordao) {
		this.idCordao = idCordao;
	}

	/**
	 * @return the numeroFicha
	 */
	public String getNumeroFicha() {
		return numeroFicha;
	}

	/**
	 * @param numeroFicha
	 *            the numeroFicha to set
	 */
	public void setNumeroFicha(String numeroFicha) {
		this.numeroFicha = numeroFicha;
	}

	/**
	 * @return the idBanco
	 */
	public Long getIdBanco() {
		return idBanco;
	}

	/**
	 * @param idBanco
	 *            the idBanco to set
	 */
	public void setIdBanco(Long idBanco) {
		this.idBanco = idBanco;
	}

	/**
	 * @return the abo
	 */
	public String getAbo() {
		return abo;
	}

	/**
	 * @param abo
	 *            the abo to set
	 */
	public void setAbo(String abo) {
		this.abo = abo;
	}

	

	/**
	 * @return the cd34Final
	 */
	public BigDecimal getCd34Final() {
		return cd34Final;
	}

	/**
	 * @param cd34Final the cd34Final to set
	 */
	public void setCd34Final(BigDecimal cd34Final) {
		this.cd34Final = cd34Final;
	}

	/**
	 * @return the quantidadeTotalTcn
	 */
	public BigDecimal getQuantidadeTotalTcn() {
		return quantidadeTotalTcn;
	}

	/**
	 * @param quantidadeTotalTcn
	 *            the quantidadeTotalTcn to set
	 */
	public void setQuantidadeTotalTcn(BigDecimal quantidadeTotalTcn) {
		this.quantidadeTotalTcn = quantidadeTotalTcn;
	}

	/**
	 * @return the sexo
	 */
	public String getSexo() {
		return sexo;
	}

	/**
	 * @param sexo
	 *            the sexo to set
	 */
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	/**
	 * @return the totalLinfocitos
	 */
	public BigDecimal getTotalLinfocitos() {
		return totalLinfocitos;
	}

	/**
	 * @param totalLinfocitos
	 *            the totalLinfocitos to set
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
	 * @param totalMonocitos
	 *            the totalMonocitos to set
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
	 * @param totalGranulocitos
	 *            the totalGranulocitos to set
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
	 * @param totalPercentualHematocritos
	 *            the totalPercentualHematocritos to set
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
	 * @param volumeTotalAntes
	 *            the volumeTotalAntes to set
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
	 * @param volumeTotalDepois
	 *            the volumeTotalDepois to set
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
	 * @param volumeTotalReal
	 *            the volumeTotalReal to set
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
	 * @param volumeTotalTCNAntes
	 *            the volumeTotalTCNAntes to set
	 */
	public void setVolumeTotalTCNAntes(BigDecimal volumeTotalTCNAntes) {
		this.volumeTotalTCNAntes = volumeTotalTCNAntes;
	}

	/**
	 * @return the resultadoHla
	 */
	public List<LocusValorVo> getResultadoHla() {
		return resultadoHla;
	}

	/**
	 * @param resultadoHla
	 *            the resultadoHla to set
	 */
	public void setResultadoHla(List<LocusValorVo> resultadoHla) {
		this.resultadoHla = resultadoHla;
	}

}

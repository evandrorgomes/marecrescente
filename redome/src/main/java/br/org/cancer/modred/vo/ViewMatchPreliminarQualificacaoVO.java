package br.org.cancer.modred.vo;

import java.io.Serializable;

/**
 * Classe VO utiliz\ada na criação do match preliminar.
 * 
 * @author brunosousa
 *
 */
public class ViewMatchPreliminarQualificacaoVO implements Serializable {

	private static final long serialVersionUID = -2168475643328701444L;
		
	private Long idBuscaPreliminar;
	private Long idGenotipoDoador;
	private String codigoLocus;
	private Long troca;
	private Long tipoPosicao1;
	private Long tipoPosicao2;
	private String letraPosicao1;
	private String letraPosicao2;
	/**
	 * @return the idBuscaPreliminar
	 */
	public Long getIdBuscaPreliminar() {
		return idBuscaPreliminar;
	}
	/**
	 * @param idBuscaPreliminar the idBuscaPreliminar to set
	 */
	public void setIdBuscaPreliminar(Long idBuscaPreliminar) {
		this.idBuscaPreliminar = idBuscaPreliminar;
	}
	/**
	 * @return the idGenotipoDoador
	 */
	public Long getIdGenotipoDoador() {
		return idGenotipoDoador;
	}
	/**
	 * @param idGenotipoDoador the idGenotipoDoador to set
	 */
	public void setIdGenotipoDoador(Long idGenotipoDoador) {
		this.idGenotipoDoador = idGenotipoDoador;
	}
	/**
	 * @return the codigoLocus
	 */
	public String getCodigoLocus() {
		return codigoLocus;
	}
	/**
	 * @param codigoLocus the codigoLocus to set
	 */
	public void setCodigoLocus(String codigoLocus) {
		this.codigoLocus = codigoLocus;
	}
	/**
	 * @return the troca
	 */
	public Long getTroca() {
		return troca;
	}
	/**
	 * @param troca the troca to set
	 */
	public void setTroca(Long troca) {
		this.troca = troca;
	}
	/**
	 * @return the tipoPosicao1
	 */
	public Long getTipoPosicao1() {
		return tipoPosicao1;
	}
	/**
	 * @param tipoPosicao1 the tipoPosicao1 to set
	 */
	public void setTipoPosicao1(Long tipoPosicao1) {
		this.tipoPosicao1 = tipoPosicao1;
	}
	/**
	 * @return the tipoPosicao2
	 */
	public Long getTipoPosicao2() {
		return tipoPosicao2;
	}
	/**
	 * @param tipoPosicao2 the tipoPosicao2 to set
	 */
	public void setTipoPosicao2(Long tipoPosicao2) {
		this.tipoPosicao2 = tipoPosicao2;
	}
	/**
	 * @return the letraPosicao1
	 */
	public String getLetraPosicao1() {
		return letraPosicao1;
	}
	/**
	 * @param letraPosicao1 the letraPosicao1 to set
	 */
	public void setLetraPosicao1(String letraPosicao1) {
		this.letraPosicao1 = letraPosicao1;
	}
	/**
	 * @return the letraPosicao2
	 */
	public String getLetraPosicao2() {
		return letraPosicao2;
	}
	/**
	 * @param letraPosicao2 the letraPosicao2 to set
	 */
	public void setLetraPosicao2(String letraPosicao2) {
		this.letraPosicao2 = letraPosicao2;
	}
	
	/**
	 * Método que cria uma instância de ViewMatchPreliminarQualificacaoVO com os dados de um vetor. 
	 * 
	 * @param object - dados da view VW_MATCH_PRELIMINAR_QUALIF
	 * @return ViewMatchPreliminarQualificacaoVO
	 */
	public static ViewMatchPreliminarQualificacaoVO popularViewMatchPreliminarQualificacao(Object[] object) {
		
		final ViewMatchPreliminarQualificacaoVO view = new ViewMatchPreliminarQualificacaoVO();
		if (object[0] != null) {
			view.setIdBuscaPreliminar(Long.parseLong(object[0].toString()));
		}
		if (object[1] != null) {
			view.setIdGenotipoDoador(Long.parseLong(object[1].toString()));			
		}
		if (object[2] != null) {
			view.setCodigoLocus(object[2].toString());
		}
		if (object[3] != null) {
			view.setTroca(Long.parseLong(object[3].toString()));			
		}
		if (object[4] != null) {
			view.setTipoPosicao1(Long.parseLong(object[4].toString()));			
		}
		if (object[5] != null) {
			view.setTipoPosicao2(Long.parseLong(object[5].toString()));			
		}
		if (object[6] != null) {
			view.setLetraPosicao1(object[6].toString());
		}
		if (object[7] != null) {
			view.setLetraPosicao2(object[7].toString());
		}
				
		return view;		
	}
	

}

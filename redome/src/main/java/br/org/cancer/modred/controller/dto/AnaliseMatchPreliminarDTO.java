package br.org.cancer.modred.controller.dto;

import java.util.List;

import br.org.cancer.modred.model.BuscaPreliminar;

/**
 * DTO utilizado para popular o carrousel de matchs preliminares.
 * 
 * @author Pizão
 */
public class AnaliseMatchPreliminarDTO {
	
	private BuscaPreliminar buscaPreliminar;
	private List<MatchDTO> listaFase1;
	private List<MatchDTO> listaFase2;
	private List<MatchDTO> listaFase3;
	private Long totalMedula;
	private Long totalCordao;
	
	
	public BuscaPreliminar getBuscaPreliminar() {
		return buscaPreliminar;
	}
	public void setBuscaPreliminar(BuscaPreliminar buscaPreliminar) {
		this.buscaPreliminar = buscaPreliminar;
	}
	public List<MatchDTO> getListaFase1() {
		return listaFase1;
	}
	public void setListaFase1(List<MatchDTO> listaFase1) {
		this.listaFase1 = listaFase1;
	}
	public List<MatchDTO> getListaFase2() {
		return listaFase2;
	}
	public void setListaFase2(List<MatchDTO> listaFase2) {
		this.listaFase2 = listaFase2;
	}
	public List<MatchDTO> getListaFase3() {
		return listaFase3;
	}
	public void setListaFase3(List<MatchDTO> listaFase3) {
		this.listaFase3 = listaFase3;
	}
	public Long getTotalMedula() {
		return totalMedula;
	}
	public void setTotalMedula(Long totalMedula) {
		this.totalMedula = totalMedula;
	}
	public Long getTotalCordao() {
		return totalCordao;
	}
	public void setTotalCordao(Long totalCordao) {
		this.totalCordao = totalCordao;
	}

}

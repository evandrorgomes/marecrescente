package br.org.cancer.modred.controller.dto;

import java.util.List;

public class CriarLogEvolucaoDTO {
	
	private Long rmr;
	private String[] parametros;
	private String tipo;
	private List<Long> perfisExcluidos;
	
	public Long getRmr() {
		return rmr;
	}
	public void setRmr(Long rmr) {
		this.rmr = rmr;
	}
	public String[] getParametros() {
		return parametros;
	}
	public void setParametros(String[] parametros) {
		this.parametros = parametros;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public List<Long> getPerfisExcluidos() {
		return perfisExcluidos;
	}
	public void setPerfisExcluidos(List<Long> perfisExcluidos) {
		this.perfisExcluidos = perfisExcluidos;
	}
	

}

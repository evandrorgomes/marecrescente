package br.org.cancer.modred.redomelib.vo;

public class ValorDnaNmdpVo {
	
	private String locus;
	private String valor;
	private Boolean ativo;
	
	public ValorDnaNmdpVo(String locus, String valor, Boolean ativo) {
		this.locus = locus;
		this.valor = valor;
		this.ativo = ativo;		
	}

	/**
	 * @return the locus
	 */
	public String getLocus() {
		return locus;
	}

	/**
	 * @return the valor
	 */
	public String getValor() {
		return valor;
	}

	/**
	 * @return the ativo
	 */
	public Boolean estaAtivo() {
		return ativo;
	}
	

}

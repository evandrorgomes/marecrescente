package br.org.cancer.modred.controller.dto.doador;

/**
 * DTO que representa cada valor genótipo do doador.
 * 
 * @author Pizão
 */
public class ValorGenotipoDTO {

	private String locus;
	private String primeiroAlelo;
	private String segundoAlelo;
	private Integer ordem;

	public String getPrimeiroAlelo() {
		return primeiroAlelo;
	}

	public void setPrimeiroAlelo(String primeiroAlelo) {
		this.primeiroAlelo = primeiroAlelo;
	}

	public String getSegundoAlelo() {
		return segundoAlelo;
	}

	public void setSegundoAlelo(String segundoAlelo) {
		this.segundoAlelo = segundoAlelo;
	}

	public String getLocus() {
		return locus;
	}

	public void setLocus(String locus) {
		this.locus = locus;
	}
	public Integer getOrdem() {
		return ordem;
	}
	public void setOrdem(Integer ordem) {
		this.ordem = ordem;
	}
}

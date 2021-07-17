package br.org.cancer.modred.controller.dto;

/**
 * Classe dto para transporte de valores de HLA.
 * @author Filipe Paes
 *
 */
public class LocusExameDto {

	private String locus;
	private String primeiroAlelo;
	private String segundoAlelo;
	
	
	
	public LocusExameDto() {
		// TODO Auto-generated constructor stub
	}

	public LocusExameDto(String locus, String primeiroAlelo, String segundoAlelo) {
		super();
		this.locus = locus;
		this.primeiroAlelo = primeiroAlelo;
		this.segundoAlelo = segundoAlelo;
	}

	public String getLocus() {
		return locus;
	}

	public void setLocus(String locus) {
		this.locus = locus;
	}

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

}

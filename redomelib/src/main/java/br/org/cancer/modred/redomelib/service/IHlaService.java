package br.org.cancer.modred.redomelib.service;

/**
 * @author brunosousa
 *
 */
public interface IHlaService {
	
	public Boolean validarHla(String codigoLocusId, String valor, Boolean revalidar);
	public Boolean validarHla(String codigoLocusId, String alelo1, String alelo2);

}

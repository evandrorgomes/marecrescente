package br.org.cancer.modred.controller.dto.doador;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * Classe qu representa um exame.
 * 
 * @author ergomes
 *
 */
@JsonTypeName("exameDoadorInternacionalDTO")
public class ExameDoadorInternacionalDTO extends ExameDTO implements Serializable {

	private static final long serialVersionUID = 8383988109355164174L;

	private DoadorCordaoInternacionalDTO doador;
		
	/**
	 * Construtor padrão.
	 */
	public ExameDoadorInternacionalDTO() {}
	
	/**
	 * Construtor opcional.
	 * Cria uma instância de exame apenas com o ID informado.
	 * 
	 * @param exameId ID do exame.
	 */
	public ExameDoadorInternacionalDTO(Long exameId) {
		super(exameId);
	}

	/**
	 * @return the doador
	 */
	public DoadorCordaoInternacionalDTO getDoador() {
		return doador;
	}

	/**
	 * @param doador the doador to set
	 */
	public void setDoador(DoadorCordaoInternacionalDTO doador) {
		this.doador = doador;
	}
	
}

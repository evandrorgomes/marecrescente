package br.org.cancer.modred.controller.dto.doador;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * Classe qu representa um exame.
 * 
 * @author ergomes
 *
 */
@JsonTypeName("exameDoadorNacionalDTO")
public class ExameDoadorNacionalDTO extends ExameDTO implements Serializable {

	private static final long serialVersionUID = 8383988109355164174L;

	private DoadorNacionalDTO doador; 
	private LocalDate dataExame;
	private LaboratorioDTO laboratorio;
	protected List<MetodologiaDTO> metodologias;
		
	/**
	 * Construtor padrão.
	 */
	public ExameDoadorNacionalDTO() {}
	
	/**
	 * Construtor opcional.
	 * Cria uma instância de exame apenas com o ID informado.
	 * 
	 * @param exameId ID do exame.
	 */
	public ExameDoadorNacionalDTO(Long exameId) {
		super(exameId);
	}
	
	/**
	 * @return the doador
	 */
	public DoadorNacionalDTO getDoador() {
		return doador;
	}
	
	/**
	 * @param doador the doador to set
	 */
	public void setDoador(DoadorNacionalDTO doador) {
		this.doador = doador;
	}
	
	/**
	 * @return the dataExame
	 */
	public LocalDate getDataExame() {
		return dataExame;
	}
	
	/**
	 * @param dataExame the dataExame to set
	 */
	public void setDataExame(LocalDate dataExame) {
		this.dataExame = dataExame;
	}

	/**
	 * @return the laboratorio
	 */
	public LaboratorioDTO getLaboratorio() {
		return laboratorio;
	}
	
	/**
	 * @param laboratorio the laboratorio to set
	 */
	public void setLaboratorio(LaboratorioDTO laboratorio) {
		this.laboratorio = laboratorio;
	}
		
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ( ( dataExame == null ) ? 0 : dataExame.hashCode() );
		result = prime * result + ( ( doador == null ) ? 0 : doador.hashCode() );
		result = prime * result + ( ( laboratorio == null ) ? 0 : laboratorio.hashCode() );
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (!( obj instanceof ExameDoadorNacionalDTO )) {
			return false;
		}
		ExameDoadorNacionalDTO other = (ExameDoadorNacionalDTO) obj;
		if (dataExame == null) {
			if (other.dataExame != null) {
				return false;
			}
		}
		else
			if (!dataExame.equals(other.dataExame)) {
				return false;
			}
		if (doador == null) {
			if (other.doador != null) {
				return false;
			}
		}
		else
			if (!doador.equals(other.doador)) {
				return false;
			}
		if (laboratorio == null) {
			if (other.laboratorio != null) {
				return false;
			}
		}
		else
			if (!laboratorio.equals(other.laboratorio)) {
				return false;
			}
		return true;
	}

	public List<MetodologiaDTO> getMetodologias() {
		return metodologias;
	}

	public void setMetodologias(List<MetodologiaDTO> metodologias) {
		this.metodologias = metodologias;
	}
	
}

package br.org.cancer.modred.controller.dto.doador;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * Classe de persistencia de EnderecoContato para Laboratorio.
 * 
 * @author ergomes
 * 
 */
@JsonTypeName("enderecoContatoLaboratorioDTO")
public class EnderecoContatoLaboratorioDTO extends EnderecoContatoDTO implements Serializable {

	private static final long serialVersionUID = 1850783784405550302L;

	private LaboratorioDTO laboratorio;

	public EnderecoContatoLaboratorioDTO() {
		super();
	}
	
	public EnderecoContatoLaboratorioDTO(EnderecoContatoDTO enderecoContato) {
		super(enderecoContato);
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
		if (!( obj instanceof EnderecoContatoLaboratorioDTO )) {
			return false;
		}
		EnderecoContatoLaboratorioDTO other = (EnderecoContatoLaboratorioDTO) obj;
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
	
}
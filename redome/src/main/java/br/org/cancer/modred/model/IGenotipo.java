package br.org.cancer.modred.model;

import java.io.Serializable;
import java.util.List;

import br.org.cancer.modred.model.domain.TiposDoador;

/**
 * 
 * @author user
 *
 */
public interface IGenotipo extends Serializable {
	Long getId();
	void setId(Long id);
	
	List<? extends IValorGenotipo> getValores();
	void setValores(List<? extends IValorGenotipo> valores);
	
	IProprietarioHla getProprietario();
	void setProprietario(IProprietarioHla proprietario);
	
	TiposDoador getTipoDoador();
	
}

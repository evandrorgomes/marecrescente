package br.org.cancer.modred.service;

import java.util.List;

import br.org.cancer.modred.model.Municipio;
import br.org.cancer.modred.service.custom.IService;

/**
 * Interface de serviçoes para a entidade municipio.
 * 
 * @author brunosousa
 *
 */
public interface IMunicipioService extends IService<Municipio, Long> {
	
	/**
	 * Retorna o municipio por descrição.
	 * 
	 * @param descricao
	 * @return municipio caso exista
	 */
	Municipio obterMunicipioPorDescricao(String descricao);

	/**
	 * Lista todos os municipios ordernados por descricao.
	 * 
	 * @return lista de municipios
	 */
	List<Municipio> listarMunicipiosOrdenadoPorDescricao();

	/**
	 * Lista os municipios de uma determinada uf.
	 * 
	 * @param sigla
	 * @return
	 */
	List<Municipio> listarMunicipioPorUf(String sigla);

}

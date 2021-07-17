package br.org.cancer.modred.service;

import java.util.List;

import br.org.cancer.modred.controller.dto.GenotipoDTO;
import br.org.cancer.modred.model.IGenotipo;

/**
 * Interface para disponibilizar funcionalidades para a classe de serviço da
 * entidade de ValorGenótipo.
 * 
 * @author Pizão
 * @param <T>
 *            Tipo da classe valor genótipo, se é de paciente ou doador.
 *
 */

public interface IValorGenotipoService<T> {

	/**
	 * Retorna a lista de genotipo.
	 * 
	 * @param genotipo
	 * @return lista de dto preenchido com o genotipo
	 */
	List<GenotipoDTO> obterGenotipoDto(IGenotipo genotipo);

	/**
	 * Salva os valores que compõem o genótipo de forma fragmentada facilitar o 
	 * processo de match. Os registros são salvos na ValorGenotipoBusca e ValorGenotipoExpandido.
	 * 
	 * @param valoresGenotipos lista de valores do genótipo.
	 * 
	 * @param proprietario
	 *            proprietário do genótipo.
	 */
	//void salvarValoresFragmentados(List<T> valoresGenotipos, IProprietarioHla proprietario);

	/**
	 * Identificar genótipo a partir de exames de um determinado paciente.
	 * 
	 * @param Genotipo de um paciente ou doador.
	 * @return listagem contendo o genótipo do paciente (separado por locus).
	 */
	List<T> obterValoresGenotipo(IGenotipo genotipo);

	/**
	 * Retorna a lista de genotipo.
	 * 
	 * @param valoresGenotipo
	 *            lista com valores de genotipo
	 * @return lista de dto preenchido com o genotipo
	 */
	List<GenotipoDTO> obterGenotipoDtoPorValorGenotipo(List<T> valoresGenotipo);

}

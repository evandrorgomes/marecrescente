package br.org.cancer.modred.service;

import java.util.List;

import br.org.cancer.modred.controller.dto.PesquisaWmdaDoadorDTO;

/**
 * Interface para disponibilizar métodos de Pesquisa Wmda Doador.
 * 
 * @author ergomes
 *
 */
public interface IPesquisaWmdaDoadorService {

	/**
	 * Atualizar Doador Wmda.
	 * 
	 * @param pesquisaWmdaDoadorDto Objeto PesquisaWmdaDoadorDTO.
	 * @return PesquisaWmdaDoadorDTO - pesquisa de doador para criação ou atualização do match.
	 */
	PesquisaWmdaDoadorDTO manterRotinaPesquisaWmdaDoador(PesquisaWmdaDoadorDTO pesquisaWmdaDoadorDto);
	
	/**
	 * Obtem a lista de doadores já existente na pesquisaWmdaDoador.
	 * 
	 * @param pesquisaWmdaId - Identificador do campo id da pesquisaWmda
	 * @return List<String> - lista com a identificação da pesquisa wmda doador existente.
	 */
	List<String> obterListaDeIdentificacaoDoadorWmdaExistente(Long pesquisaWmdaId);
}

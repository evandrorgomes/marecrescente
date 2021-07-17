package br.org.cancer.modred.service;

import java.util.List;

import br.org.cancer.modred.controller.dto.MatchWmdaDTO;
import br.org.cancer.modred.controller.dto.PesquisaWmdaDTO;

/**
 * Interface para disponibilizar métodos de Pesquisa Wmda.
 * 
 * @author ergomes
 *
 */
public interface IPesquisaWmdaService {

	/**
	 * Criar Pesquisa Wmda.
	 * 
	 * @param PesquisaWmda pesquisaWmda.
	 * @return PesquisaWmda
	 */
	PesquisaWmdaDTO criarPesquisaWmda(PesquisaWmdaDTO pesquisaWmdaDto);
	
	/**
	 * Obter Pesquisa Wmda.
	 * 
	 * @param Id da pesquisaWmda.
	 * @return PesquisaWmdaDto
	 */
	PesquisaWmdaDTO obterPesquisaWmda(Long id);

	/**
	 * Mater rotina match da Pesquisa Wmda.
	 * 
	 * @param MatchWmdaDTO matchWmdaDto.
	 */
	void manterRotinaMatchWmda(MatchWmdaDTO matchWmdaDto);
	
	/**
	 * Obter Pesquisa Wmda com buscaId e status aberto
	 * 
	 * @param buscaId Id de identificação da busca.
	 * 
	 * @return PesquisaWmdaDto
	 */
	PesquisaWmdaDTO obterPesquisaWmdaPorBuscaIdEStatusAbertoEProcessadoErro(Long buscaId);

	/**
	 * Obter lista de Pesquisas Wmda de medula com buscaId 
	 * 
	 * @param buscaId Id de identificação da busca.
	 * 
	 * @return List<PesquisaWmdaDto>
	 */
	List<PesquisaWmdaDTO> obterListaDePesquisaWmdaDeMedulaParaBusca(Long buscaId);

	/**
	 * Obter lista de Pesquisas Wmda de cordão com buscaId 
	 * 
	 * @param buscaId Id de identificação da busca.
	 * 
	 * @return List<PesquisaWmdaDto>
	 */
	List<PesquisaWmdaDTO> obterListaDePesquisaWmdaDeCordaoParaBusca(Long buscaId);
	
	/**
	 * Alterar Pesquisa Wmda com status aberta.
	 * 
	 * @param pesquisaWmdaId Id de identificação da pesquisa wmda.
	 * @param pesquisaWmdaDto Objeto de identificação da pesquisa wmda Dto.
	 * 
	 */
	void atualizarPesquisaWmda(Long pesquisaWmdaId, PesquisaWmdaDTO pesquisaWmdaDto);
}

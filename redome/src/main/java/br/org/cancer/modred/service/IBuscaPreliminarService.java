package br.org.cancer.modred.service;

import br.org.cancer.modred.controller.dto.AnaliseMatchPreliminarDTO;
import br.org.cancer.modred.controller.dto.RetornoInclusaoDTO;
import br.org.cancer.modred.model.BuscaPreliminar;
import br.org.cancer.modred.model.domain.FiltroMatch;
import br.org.cancer.modred.service.custom.IService;

/**
 * Define os métodos necessários para acesso a entidade BuscaPreliminar.
 * 
 * @author Pizão
 */
public interface IBuscaPreliminarService extends IService<BuscaPreliminar, Long> {
	
	/**
	 * Método que salva a busca preliminar e gera o match com os doadores.
	 * 
	 * @param buscaPreliminar - Busca Preliminar a ser salva
	 * @return Retorna um DTO contendo o id da busca,
	 *		a mensagem o qual deverá ser exibida para o usuário e
	 *		uma string de retorno contendo a palavra 'possui' quando a mesma possuir match,
	 *		caso não possua matchs naõ deverá ser retornado nada nesta string.. 			
	 */
	RetornoInclusaoDTO realizarBuscaPreliminar(BuscaPreliminar buscaPreliminar);
	
	/**
	 * Obtém as listas de matchs preliminares ocorridas para a busca preliminar cadastrada.
	 * 
	 * @param idBuscaPreliminar ID da busca preliminar.
	 * @param filtro filtro para listar somente medula ou cordão.
	 * @return objeto contendo as listas de fase 1, 2 e 3 em que ocorreram match.
	 */
	AnaliseMatchPreliminarDTO obterListasMatchsPreliminares(Long idBuscaPreliminar, FiltroMatch filtro);

}

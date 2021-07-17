package br.org.cancer.modred.service;

import java.util.List;

import br.org.cancer.modred.controller.dto.MatchDTO;
import br.org.cancer.modred.model.MatchPreliminar;
import br.org.cancer.modred.model.domain.FasesMatch;
import br.org.cancer.modred.model.domain.FiltroMatch;
import br.org.cancer.modred.service.custom.IService;

/**
 * Interface que define os métodos de acesso as funcionalidades 
 * envolvendo a entidade MatchPreliminar.
 * 
 * @author Pizão
 *
 */
public interface IMatchPreliminarService extends IService<MatchPreliminar, Long> {
	
	/**
	 * Executa a procedure para criação do match entre o 
	 * paciente e o doador informados.
	 * 
	 * @param idBusca ID da busca preliminar.
	 */
	void executarProcedureMatch(Long idBusca);
	
	/**
	 * Obtém o total de matchs de medula ocorridos com o ID
	 * da busca preliminar.
	 * 
	 * @param idBuscaPreliminar ID da busca prelimminar.
	 * @return contador com a quantidade de matchs.
	 */
	Long obterQuantidadeMatchsMedula(Long idBuscaPreliminar);
	
	/**
	 * Obtém o total de matchs de cordão ocorridos com o ID
	 * da busca preliminar.
	 * 
	 * @param idBuscaPreliminar ID da busca prelimminar.
	 * @return contador com a quantidade de matchs.
	 */
	Long obterQuantidadeMatchsCordao(Long idBuscaPreliminar);
	
	/**
	 * Lista todos os matchs preliminares em que foram incluídos como Fase 1.
	 * 
	 * @param idBuscaPreliminar ID da busca preliminar.
	 * @param faseMatch Fase do match que se deseja filtrar.
	 * @param filtroMatch filtro do match.
	 * @para, fases fase em que o dodor se encontra
	 * 
	 * @return lista de matchs preliminares associados a busca na fase informada.
	 */
	List<MatchDTO> listarMatchsPreliminares(Long idBuscaPreliminar, FiltroMatch filtroMatch, List<FasesMatch> fases);
	
	/**
	 * Obtém a lista de matchs por id da busca preliminar e lista de idDoador.
	 * @param idBuscaPreliminar - id da busca preliminar
	 * @param listaIdsDoador - lista de idDoador de doadores
	 * @return lista de match preliminar
	 */
	List<MatchDTO> listarMatchsAtivosPorIDBuscaPreliminarAndListaIdsDoador(Long idBuscaPreliminar, List<Long> listaIdsDoador );
	
	/**
	 * Criar a match da busca preliminar.
	 * 
	 * @param idBuscaPreliminar
	 */
	void criarMatchPreliminar(Long idBuscaPreliminar);
	
}
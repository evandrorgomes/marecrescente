package br.org.cancer.modred.service;

import java.util.List;

import br.org.cancer.modred.controller.dto.QualificacaoMatchDTO;
import br.org.cancer.modred.model.Match;
import br.org.cancer.modred.model.QualificacaoMatch;
import br.org.cancer.modred.model.interfaces.IQualificacaoMatch;
import br.org.cancer.modred.service.custom.IService;

/**
 * Interface de métodos de negócio relacionados a Match.
 * 
 * @author Filipe Paes
 *
 */
public interface IQualificacaoMatchService extends IService<QualificacaoMatch, Long> {

	/**
	 * Lista as qualificações do match.
	 * 
	 * @param matchId
	 * @return
	 */
	List<IQualificacaoMatch> listarQualificacaoMatchPorMatchId(Long matchId);
	
	/**
	 * Criar qualificação - Lista as qualificações do match.
	 * 
	 * @param match - Objeto Match.
	 * @param lista - List<QualificacaoMatchDTO> Lista as qualificações do match. 
	 */
	void criarListaDeQualificacaoMatch(Match match, List<QualificacaoMatchDTO> qualificacoes);

	/**
	 * Atualizar qualificação - Lista as qualificações do match.
	 * 
	 * @param match - Objeto Match.
	 * @param lista - List<QualificacaoMatchDTO> Lista as qualificações do match. 
	 */
	void atualizarListaDeQualificacaoMatch(Match match, List<QualificacaoMatchDTO> qualificacoes);
}
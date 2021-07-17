package br.org.cancer.redome.workup.persistence;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import br.org.cancer.redome.workup.model.Prescricao;

/**
 * Repositório para acesso a base de dados ligadas a entidade Prescrição.
 * 
 * @author ergomes
 *
 */
@Repository
public interface IPrescricaoRepository extends IRepository<Prescricao, Long> {

	/**
	 * Obtém a prescrição associadas a solicitação passada por parâmetro.
	 * 
	 * @param id da solicitacao - solicitação a ser utilizada no filtro.
	 * @return lista de prescrição, se houver.
	 */
//	List<Prescricao> findBySolicitacaoOrderByIdAsc(Long solicitacao);
	
	
	/**
	 * Obtém a prescrição associada a solicitação passada por parâmetro.
	 * 
	 * @param idSolicitacao Identificador da solicitação a ser utilizada no filtro.
	 * @return Optional<Prescricao>
	 */
	Optional<Prescricao> findBySolicitacao(Long idSolicitacao);

}


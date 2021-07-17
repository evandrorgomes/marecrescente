package br.org.cancer.redome.workup.persistence;

import org.springframework.stereotype.Repository;

import br.org.cancer.redome.workup.model.AvaliacaoPrescricao;

/**
 * Interface de persistencia para avaliação de prescrição.
 * 
 * @author Fillipe Queiroz
 *
 */
@Repository
public interface IAvaliacaoPrescricaoRepository extends IRepository<AvaliacaoPrescricao, Long> {

	AvaliacaoPrescricao findByPrescricaoId(Long idPrescricao);
}

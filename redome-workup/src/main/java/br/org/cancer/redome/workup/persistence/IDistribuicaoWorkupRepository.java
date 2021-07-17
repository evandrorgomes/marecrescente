package br.org.cancer.redome.workup.persistence;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import br.org.cancer.redome.workup.model.DistribuicaoWorkup;

/**
 * Repositório para acesso a base de dados ligadas a entidade DistribuicaoWorkup.
 * 
 * @author bruno.sousa
 *
 */
@Repository
public interface IDistribuicaoWorkupRepository extends IRepository<DistribuicaoWorkup, Long> {

	Optional<DistribuicaoWorkup> findBySolicitacao(Long idSolicitacao);}

package br.org.cancer.modred.persistence;

import java.util.List;

import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.QualificacaoMatch;
import br.org.cancer.modred.model.interfaces.IQualificacaoMatch;

/**
 * Camada de acesso a base dados de LogEvolucao.
 * 
 * @author Pizão
 *
 */
@Repository
public interface IQualificacaoMatchRepository extends IRepository<QualificacaoMatch, Long>{
	
	List<IQualificacaoMatch> findByMatchId(Long idMatch);
	
}
package br.org.cancer.modred.persistence;

import java.util.List;

import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.QualificacaoMatchPreliminar;
import br.org.cancer.modred.model.interfaces.IQualificacaoMatch;

/**
 * Camada de acesso a base dados de QualificacaoMatchPreliminar.
 * 
 * @author bruno.sousa
 *
 */
@Repository
public interface IQualificacaoMatchPreliminarRepository extends IRepository<QualificacaoMatchPreliminar, Long>{
	
	List<IQualificacaoMatch> findByMatchId(Long idMatchPreliminar);
	
}
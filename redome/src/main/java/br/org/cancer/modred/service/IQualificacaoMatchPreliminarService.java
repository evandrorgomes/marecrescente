package br.org.cancer.modred.service;

import java.util.List;

import br.org.cancer.modred.model.QualificacaoMatchPreliminar;
import br.org.cancer.modred.model.interfaces.IQualificacaoMatch;
import br.org.cancer.modred.service.custom.IService;

/**
 * Interface de métodos de negócio relacionados a Match.
 * 
 * @author bruno.sousa
 *
 */
public interface IQualificacaoMatchPreliminarService extends IService<QualificacaoMatchPreliminar, Long> {


	/**
	 * Lista as qualificações do match preliminar.
	 * 
	 * @param matchPreliminarId
	 * @return
	 */
	List<IQualificacaoMatch> listarQualificacaoMatchPreliminarPorMatchPreliminarId(Long matchPreliminarId);
	
	
	
}
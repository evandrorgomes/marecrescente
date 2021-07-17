package br.org.cancer.modred.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.org.cancer.modred.model.QualificacaoMatchPreliminar;
import br.org.cancer.modred.model.interfaces.IQualificacaoMatch;
import br.org.cancer.modred.persistence.IQualificacaoMatchPreliminarRepository;
import br.org.cancer.modred.persistence.IRepository;
import br.org.cancer.modred.service.IQualificacaoMatchPreliminarService;
import br.org.cancer.modred.service.impl.custom.AbstractService;

/**
 * Implementação de classe de negócios de Match.
 * 
 * @author Filipe Paes
 *
 */
@Service
@Transactional
public class QualificacaoMatchPreliminarService extends AbstractService<QualificacaoMatchPreliminar, Long> implements IQualificacaoMatchPreliminarService {

	@Autowired
	private IQualificacaoMatchPreliminarRepository qualificacaoMatchPreliminarRepository;

	/**
	 * Construtor definido para que seja informado as estratégias
	 * para os eventos de criação de notificação.
	 */
	public QualificacaoMatchPreliminarService() {
		super();		
	}
	
	@Override
	public IRepository<QualificacaoMatchPreliminar, Long> getRepository() {
		return qualificacaoMatchPreliminarRepository;
	}
	
	@Override
	public List<IQualificacaoMatch> listarQualificacaoMatchPreliminarPorMatchPreliminarId(Long matchPreliminarId) {
		return qualificacaoMatchPreliminarRepository.findByMatchId(matchPreliminarId);
	}


}

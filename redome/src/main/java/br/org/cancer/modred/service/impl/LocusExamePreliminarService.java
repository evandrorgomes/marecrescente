package br.org.cancer.modred.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.org.cancer.modred.model.LocusExamePreliminar;
import br.org.cancer.modred.persistence.ILocusExamePreliminarRepository;
import br.org.cancer.modred.persistence.IRepository;
import br.org.cancer.modred.service.ILocusExamePreliminarService;
import br.org.cancer.modred.service.impl.custom.AbstractService;

/**
 * Implementa os métodos e as regras de negócio envolvendo a entidade LocusExamePreliminar.
 * 
 * @author Pizão
 */
@Service
@Transactional
public class LocusExamePreliminarService extends AbstractService<LocusExamePreliminar, Long> 
	implements ILocusExamePreliminarService{

	@Autowired
	private ILocusExamePreliminarRepository locusExamePreliminarRepository;

	@Override
	public IRepository<LocusExamePreliminar, Long> getRepository() {
		return locusExamePreliminarRepository;
	}
}

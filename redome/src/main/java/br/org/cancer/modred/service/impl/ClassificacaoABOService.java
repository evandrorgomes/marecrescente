package br.org.cancer.modred.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.modred.model.ClassificacaoABO;
import br.org.cancer.modred.persistence.IClassificacaoABORepository;
import br.org.cancer.modred.persistence.IRepository;
import br.org.cancer.modred.service.IClassificacaoABOService;
import br.org.cancer.modred.service.impl.custom.AbstractService;

/**
 * Classe de acesso as funcionalidades que envolvem a entidade Prescrição.
 * 
 * @author bruno.sousa
 *
 */
@Service
@Transactional
public class ClassificacaoABOService extends AbstractService<ClassificacaoABO, Long> implements IClassificacaoABOService {

	private static final Logger LOG = LoggerFactory.getLogger(ClassificacaoABOService.class);

	@Autowired
	private IClassificacaoABORepository classificacaoABORepository;

	@Override
	public IRepository<ClassificacaoABO, Long> getRepository() {
		return classificacaoABORepository;
	}

}

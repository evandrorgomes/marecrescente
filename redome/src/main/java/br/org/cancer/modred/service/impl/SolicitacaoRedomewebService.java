
package br.org.cancer.modred.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.modred.model.SolicitacaoRedomeweb;
import br.org.cancer.modred.persistence.IRepository;
import br.org.cancer.modred.persistence.ISolicitacaoRedomewebRepository;
import br.org.cancer.modred.service.ISolicitacaoRedomewebService;
import br.org.cancer.modred.service.impl.custom.AbstractService;

/**
 * Acesso as funcionalidades para criação de solicitações Redomeweb.
 * 
 * @author bruno.sousa
 *
 */
@Service
@Transactional
public class SolicitacaoRedomewebService extends AbstractService<SolicitacaoRedomeweb, Long> implements ISolicitacaoRedomewebService {

	private static final Logger LOG = LoggerFactory.getLogger(SolicitacaoRedomewebService.class);

	@Autowired
	private ISolicitacaoRedomewebRepository solicitacaoRedomewebRepository;

	
	@Override
	public IRepository<SolicitacaoRedomeweb, Long> getRepository() {
		return solicitacaoRedomewebRepository;
	}
}
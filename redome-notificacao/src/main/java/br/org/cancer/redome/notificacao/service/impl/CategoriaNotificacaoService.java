package br.org.cancer.redome.notificacao.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.redome.notificacao.exception.BusinessException;
import br.org.cancer.redome.notificacao.model.CategoriaNotificacao;
import br.org.cancer.redome.notificacao.persistence.ICategoriaNotificacaoRepository;
import br.org.cancer.redome.notificacao.persistence.IRepository;
import br.org.cancer.redome.notificacao.service.ICategoriaNotificacaoService;
import br.org.cancer.redome.notificacao.service.impl.custom.AbstractService;

/**
 * Classe de implementacao de negócio de Notificacao.
 * 
 * @author ergomes
 *
 */
@Service
@Transactional
public class CategoriaNotificacaoService extends AbstractService<CategoriaNotificacao, Long> implements ICategoriaNotificacaoService {

	@Autowired
	private ICategoriaNotificacaoRepository categoriaNotificacaoRepository;

	@Autowired
	private MessageSource messageSource;

	@Override
	public IRepository<CategoriaNotificacao, Long> getRepository() {
		return categoriaNotificacaoRepository;
	}
	
	@Override
	public CategoriaNotificacao obterCategoriaNotificacao(Long id) {
		return categoriaNotificacaoRepository.findById(id)
				.orElseThrow(() -> new BusinessException("erro.categoria.notificacao.nao.encontrada"));
	}

		
}

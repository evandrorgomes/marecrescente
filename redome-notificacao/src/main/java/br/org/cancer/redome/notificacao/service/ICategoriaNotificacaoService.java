package br.org.cancer.redome.notificacao.service;

import br.org.cancer.redome.notificacao.model.CategoriaNotificacao;
import br.org.cancer.redome.notificacao.service.custom.IService;

/**
 * Interface de negócios para notificacao.
 * 
 * @author ergomes
 *
 */
public interface ICategoriaNotificacaoService extends IService<CategoriaNotificacao, Long> {

	CategoriaNotificacao obterCategoriaNotificacao(Long id);
	
}

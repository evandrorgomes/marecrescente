package br.org.cancer.redome.notificacao.persistence;

import org.springframework.stereotype.Repository;

import br.org.cancer.redome.notificacao.model.CategoriaNotificacao;

/**
 * Interface de persistencia para notificaçao.
 * 
 * @author Fillipe Queiroz
 *
 */
@Repository
public interface ICategoriaNotificacaoRepository extends IRepository<CategoriaNotificacao, Long> {
	
}

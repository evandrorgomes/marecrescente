package br.org.cancer.redome.notificacao.persistence;

import org.springframework.stereotype.Repository;

import br.org.cancer.redome.notificacao.model.Notificacao;

/**
 * Interface de persistencia para notificaçao.
 * 
 * @author Fillipe Queiroz
 *
 */
@Repository
public interface INotificacaoRepository extends IRepository<Notificacao, Long>, INotificacaoRepositoryCustom {
	
}

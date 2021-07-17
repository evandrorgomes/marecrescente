package br.org.cancer.redome.notificacao.persistence;

import org.springframework.data.domain.Page;

import br.org.cancer.redome.notificacao.dto.ListaNotificacaoDTO;
import br.org.cancer.redome.notificacao.model.Notificacao;

/**
 * Interface de persistencia para notificaçao customizada.
 * 
 * @author brunosousa
 *
 */
public interface INotificacaoRepositoryCustom {
	
	/**
	 * Lista as notificações.
	 * 
	 * @param ListaNotificacaoDTO parametros
	 * @return Lista de Notificações paginada.
	 */
	Page<Notificacao> listarNotificacoes(ListaNotificacaoDTO parametros);

	/**
	 * Quantidade de notificações.
	 * 
	 * @param ListaNotificacaoDTO parametros
	 * @return quantidade de notificações.
	 */
	Long contarNotificacoes(ListaNotificacaoDTO parametros);

}

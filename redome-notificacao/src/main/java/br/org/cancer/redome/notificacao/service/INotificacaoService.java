package br.org.cancer.redome.notificacao.service;

import java.util.List;

import org.springframework.data.domain.Page;

import br.org.cancer.redome.notificacao.dto.ListaNotificacaoDTO;
import br.org.cancer.redome.notificacao.dto.NotificacaoDTO;
import br.org.cancer.redome.notificacao.model.Notificacao;
import br.org.cancer.redome.notificacao.service.custom.IService;
import br.org.cancer.redome.notificacao.util.CampoMensagem;

/**
 * Interface de negócios para notificacao.
 * 
 * @author ergomes
 *
 */
public interface INotificacaoService extends IService<Notificacao, Long> {

	/**
	 * Marca uma notificação como lida.
	 * 
	 * @param idNotificacao identificador da notificação
	 * @return mensagem de sucesso.
	 */
	CampoMensagem marcarNotificacaoComoLida(Long idNotificacao);

	/**
	 * Serviço para listar as notificações do usuario logado.
	 * 
	 * @param ListaNotificacaoDTO notificacao
	 * @return Lista de notificação
	 */
	Page<Notificacao> listarNotificacoes(ListaNotificacaoDTO parametros);

	/**
	 * Serviço para totalizar as notificações do pacientes.
	 * 
	 * @param ListaNotificacaoDTO parametros
	 * @return Long - Total de notificacoes.
	 */
	Long contarNotificacoes(ListaNotificacaoDTO parametros);

	/**
	 * Método para criar uma nova notificacao.
	 * 
	 * @param notificacao - a notificacao que será criada.
	 * @return NotificacaoDTO - Notificacao criada.
	 */
	List<NotificacaoDTO> criarNotificacao(NotificacaoDTO notificacaoDto);
}

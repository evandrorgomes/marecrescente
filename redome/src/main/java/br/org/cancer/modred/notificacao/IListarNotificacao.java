package br.org.cancer.modred.notificacao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import br.org.cancer.modred.feign.dto.NotificacaoDTO;

/**
 * Classe para centralizar a listagem de notificações.
 * 
 * @author brunosousa
 *
 */
public interface IListarNotificacao {
	
	IListarNotificacao comId(Long id);
	
	IListarNotificacao comPaginacao(PageRequest pageRequest);

	IListarNotificacao somenteLidas();
	
	IListarNotificacao somenteNaoLidas();
	
	IListarNotificacao somentePacientesDoUsuario();

	IListarNotificacao comParceiros(List<Long> parceiros);

	IListarNotificacao comRmr(Long rmr);
	
	/**
	 * Este método é utilizado quando a listagem não é feita dentro de uma categoria.
	 * 
	 * @param categoriaNotificacao
	 * @return
	 */
	IListarNotificacao comCategoria(Long categoriaNotificacao);
	
	/**
	 * Executar a consulta da lista de notificações.
	 * @return Page<Notificacao> lista de notificações
	 */
	Page<NotificacaoDTO> apply();

	
}

package br.org.cancer.modred.notificacao;

import java.util.List;

import br.org.cancer.modred.feign.dto.NotificacaoDTO;

/**
 * Classe responsável por obter uma notificação.
 * 
 * @author brunosousa
 *
 */
public interface IObterNotificacao {
	
	IObterNotificacao comId(Long id);
	
	IObterNotificacao comRmr(Long rmr);
	
	IObterNotificacao comParceiros(List<Long> parceiros);
	
	/**
	 * Este método é utilizado quando a listagem não é feita dentro de uma categoria.
	 * 
	 * @param categoriaNotificacao
	 * @return
	 */
	IObterNotificacao comCategoria(Long categoriaNotificacao);
	
	/**
	 * Executar a consulta para obter notificação.
	 * @return Notificacao
	 */
	NotificacaoDTO apply();

}

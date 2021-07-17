package br.org.cancer.modred.notificacao;

import java.util.List;

import br.org.cancer.modred.feign.dto.NotificacaoDTO;

/**
 * Classe reponsável por marcar uma notificação como lida.
 * 
 * @author brunosousa
 *
 */
public interface ILerNotificacao {
	
	ILerNotificacao comId(Long id);
	
	ILerNotificacao comRmr(Long rmr);
	
	ILerNotificacao comParceiros(List<Long> parceiros);
	
	/**
	 * Este método é utilizado quando a listagem não é feita dentro de uma categoria.
	 * 
	 * @param categoriaNotificacao
	 * @return
	 */
	ILerNotificacao comCategoria(Long categoriaNotificacao);
	
	/**
	 * Executar a leitura da notificação.
	 * @return Notificacao
	 */
	NotificacaoDTO apply();

}

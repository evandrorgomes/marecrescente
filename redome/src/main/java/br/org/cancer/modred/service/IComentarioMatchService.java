package br.org.cancer.modred.service;

import br.org.cancer.modred.model.ComentarioMatch;
import br.org.cancer.modred.service.custom.IService;

/**
 * Interface para métodos de negócio de Comentários de Match.
 * @author Filipe Paes
 *
 */
public interface IComentarioMatchService extends IService<ComentarioMatch, Long> {
	
	/**
	 * Salva comentário de match.
	 * @param comentario - objeto de comentário.
	 */
	void salvarComentario(ComentarioMatch comentario);
}

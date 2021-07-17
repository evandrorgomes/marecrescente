package br.org.cancer.modred.service.impl;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.org.cancer.modred.exception.BusinessException;
import br.org.cancer.modred.model.ComentarioMatch;
import br.org.cancer.modred.persistence.IComentarioMatchRepository;
import br.org.cancer.modred.persistence.IRepository;
import br.org.cancer.modred.service.IComentarioMatchService;
import br.org.cancer.modred.service.impl.custom.AbstractService;

/**
 * Classe de implementação de métodos de comentários de match.
 * @author Filipe Paes
 *
 */
@Transactional
@Service
public class ComentarioMatchService extends AbstractService<ComentarioMatch, Long> implements IComentarioMatchService {

	@Autowired
	private IComentarioMatchRepository comentarioMatchRepository;

	@Override
	public IRepository<ComentarioMatch, Long> getRepository() {
		return comentarioMatchRepository;
	}

	@Override
	public void salvarComentario(ComentarioMatch comentario) {
		if(comentario == null || StringUtils.isEmpty(comentario.getComentario())){
			throw new BusinessException("erro.comentario.match.comentario_obrigatorio");
		}
		this.save(comentario);
	}

}

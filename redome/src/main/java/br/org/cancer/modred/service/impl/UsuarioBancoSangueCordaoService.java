package br.org.cancer.modred.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.modred.model.UsuarioBancoSangueCordao;
import br.org.cancer.modred.persistence.IRepository;
import br.org.cancer.modred.persistence.IUsuarioBancoSangueCordaoRepository;
import br.org.cancer.modred.service.IUsuarioBancoSangueCordaoService;
import br.org.cancer.modred.service.impl.custom.AbstractService;

/**
 * Implementacao da classe de negócios de UsuarioBancoSangueCordao.
 * 
 * @author Pizão
 *
 */
@Service
@Transactional
public class UsuarioBancoSangueCordaoService extends AbstractService<UsuarioBancoSangueCordao, Long> implements IUsuarioBancoSangueCordaoService {

	@Autowired
	private IUsuarioBancoSangueCordaoRepository usuarioBancoSangueCordaoRepository;
	

	@Override
	public IRepository<UsuarioBancoSangueCordao, Long> getRepository() {
		return usuarioBancoSangueCordaoRepository;
	}


	@Override
	public int associarUsuarioBanco(Long idUsuario, Long idBancoSangue) {
		return usuarioBancoSangueCordaoRepository.associarUsuarioBanco(idUsuario, idBancoSangue);
	}

}

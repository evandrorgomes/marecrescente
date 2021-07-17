package br.org.cancer.modred.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import br.org.cancer.modred.model.CentroTransplanteUsuario;
import br.org.cancer.modred.persistence.ICentroTransplanteUsuarioRepository;
import br.org.cancer.modred.persistence.IRepository;
import br.org.cancer.modred.service.ICentroTransplanteUsuarioService;
import br.org.cancer.modred.service.impl.custom.AbstractService;

/**
 * Classe de negócio de centro de transplante usuário. 
 * @author Filipe Paes
 *
 */
public class CentroTransplanteUsuarioService extends AbstractService<CentroTransplanteUsuario, Long>
implements ICentroTransplanteUsuarioService  {

	@Autowired
	private ICentroTransplanteUsuarioRepository centroTransplanteUsuarioRepository;
	
	@Override
	public IRepository<CentroTransplanteUsuario, Long> getRepository() {
		return centroTransplanteUsuarioRepository;
	}

}
